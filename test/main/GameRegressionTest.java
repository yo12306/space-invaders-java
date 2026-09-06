package main;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Random;

import javax.swing.SwingUtilities;

import entity.Entity;
import entity.MonsterVI;
import entity.NormalBullet;

/** Dependency-free regression checks; run with the resources on the classpath. */
public class GameRegressionTest {
	private static class SilentPanel extends GamePanel {
		@Override public void playSE(int i) {}
		@Override public void playMusic(int i) {}
	}

	private static class CountingEntity extends Entity {
		int updates;
		int draws;
		boolean expire;

		CountingEntity(GamePanel panel) {
			super(panel);
			x = 800;
			y = 100;
		}

		@Override public void update() {
			updates++;
			if(expire) alive = false;
		}

		@Override public void draw(Graphics2D g) {
			draws++;
		}
	}

	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeAndWait(() -> {
			cachedSprites();
			collisionBounds();
			projectileCleanup();
			killIsAwardedOnce();
			fixedStepTiming();
			paintingDoesNotAdvanceGameplay();
			messageLifetime();
			monsterLimitAndRetry();
			controls();
			audioResourcesAndUnavailableAudio();
		});
		System.out.println("10 regression checks passed.");
	}

	private static SilentPanel playingPanel() {
		SilentPanel panel = new SilentPanel();
		panel.gameState = panel.PLAY_STATE;
		for(int i = 0; i < 12; i++) panel.monster[i] = new CountingEntity(panel);
		return panel;
	}

	private static void cachedSprites() {
		GamePanel panel = new SilentPanel();
		check(new MonsterVI(panel).entity == new MonsterVI(panel).entity, "Monster sprites must be shared");
		NormalBullet first = new NormalBullet(panel);
		NormalBullet second = new NormalBullet(panel);
		check(first.entity == second.entity, "Bullet sprites must be shared");
		check(first.entity.getWidth() == 32 && first.entity.getHeight() == 32, "Bullets must be pre-scaled");
		BufferedImage larger = UtilityTool.loadImage("/bullets/normalBullet.png", 64, 64);
		check(larger != first.entity && larger.getWidth() == 64, "Cache must distinguish sprite sizes");
	}

	private static void collisionBounds() {
		GamePanel panel = new SilentPanel();
		Random random = new Random(42);
		String[] directions = {"up", "down", "left", "right", ""};
		for(int trial = 0; trial < 1000; trial++) {
			Entity moving = new Entity(panel);
			Entity target = new Entity(panel);
			moving.x = random.nextInt(100);
			moving.y = random.nextInt(100);
			target.x = random.nextInt(100);
			target.y = random.nextInt(100);
			moving.solidArea = new Rectangle(3, 7, 32, 32);
			target.solidArea = new Rectangle(5, 2, 64, 64);
			moving.direction = directions[trial % directions.length];
			moving.velocity = 8;
			Rectangle bounds = new Rectangle(moving.solidArea);
			bounds.translate(moving.x, moving.y);
			int direction = trial % directions.length;
			int[] dx = {0, 0, -8, 8, 0};
			int[] dy = {-8, 8, 0, 0, 0};
			bounds.translate(dx[direction], dy[direction]);
			Rectangle targetBounds = new Rectangle(target.solidArea);
			targetBounds.translate(target.x, target.y);
			int expected = bounds.intersects(targetBounds) ? 1 : 999;
			check(panel.cChecker.checkEntity(moving, new Entity[] {moving, target}) == expected, "Collision differs from Rectangle bounds");
			check(moving.solidArea.equals(new Rectangle(3, 7, 32, 32)), "Moving hitbox was mutated");
			check(target.solidArea.equals(new Rectangle(5, 2, 64, 64)), "Target hitbox was mutated");
			target.dying = true;
			check(panel.cChecker.checkEntity(moving, new Entity[] {target}) == 999, "Dying targets must be ignored");
		}
	}

	private static void projectileCleanup() {
		GamePanel panel = playingPanel();
		CountingEntity first = new CountingEntity(panel);
		CountingEntity second = new CountingEntity(panel);
		CountingEntity third = new CountingEntity(panel);
		CountingEntity dead = new CountingEntity(panel);
		first.expire = true;
		dead.alive = false;
		panel.projectileList.addAll(Arrays.asList(first, second, null, dead, third));
		panel.update();
		check(first.updates == 1 && second.updates == 1 && third.updates == 1 && dead.updates == 0, "Cleanup skipped or double-updated a projectile");
		check(panel.projectileList.equals(Arrays.asList(second, third)), "Cleanup must preserve surviving shot order");
	}

	private static void killIsAwardedOnce() {
		GamePanel panel = playingPanel();
		Entity target = panel.monster[0];
		target.x = 300;
		target.y = 300;
		target.hp = 1;
		for(int i = 0; i < 2; i++) {
			NormalBullet bullet = new NormalBullet(panel);
			bullet.x = 300;
			bullet.y = 300;
			bullet.alive = true;
			panel.projectileList.add(bullet);
		}
		panel.update();
		check(panel.spaceship.kill == 1 && panel.spaceship.score == 120, "Same-frame shots must award a kill once");
		check(panel.projectileList.size() == 1, "A dead monster must not absorb a second shot");
	}

	private static void fixedStepTiming() {
		GamePanel panel = playingPanel();
		CountingEntity counter = (CountingEntity)panel.monster[0];
		// Uneven timer callbacks totaling exactly one second must produce 60 updates.
		for(int i = 0; i < 50; i++) {
			panel.advanceFrame(7000000L);
			panel.advanceFrame(13000000L);
		}
		check(counter.updates == 60, "Simulation speed depends on timer callback frequency");
		panel.advanceFrame(10000000000L);
		check(counter.updates == 65, "A stall must not create unbounded catch-up work");
		panel.advanceFrame(0);
		check(counter.updates == 65, "Stale backlog survived the catch-up limit");
		panel.gameState = panel.OPTION_STATE;
		panel.advanceFrame(20000000L);
		check(counter.updates == 65, "Options must pause gameplay");
	}

	private static void paintingDoesNotAdvanceGameplay() {
		GamePanel panel = playingPanel();
		panel.setSize(panel.SCREEN_WIDTH, panel.SCREEN_HEIGHT);
		panel.ui.addMessage("+20");
		Entity monster = new MonsterVI(panel);
		monster.x = 200;
		monster.y = 200;
		monster.hpBarOn = true;
		panel.monster[1] = monster;
		BufferedImage image = new BufferedImage(panel.SCREEN_WIDTH, panel.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		try {
			panel.paint(g);
			int[] first = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			panel.paint(g);
			int[] second = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			check(Arrays.equals(first, second), "Repaint changed the scene without an update");
			check(((CountingEntity)panel.monster[0]).draws == 2, "An entity was drawn more than once per paint");
			check(panel.ui.messageCounter.get(0) == 0 && panel.background.render == 0, "Paint advanced animation counters");
		} finally {
			g.dispose();
		}
	}

	private static void messageLifetime() {
		GamePanel panel = playingPanel();
		panel.ui.addMessage("+20");
		panel.ui.addMessage("+100");
		for(int i = 0; i < 50; i++) panel.update();
		check(panel.ui.message.size() == 2, "Messages expired too early");
		panel.update();
		check(panel.ui.message.isEmpty() && panel.ui.messageCounter.isEmpty(), "Adjacent messages did not expire together");
	}

	private static void monsterLimitAndRetry() {
		GamePanel panel = playingPanel();
		Arrays.fill(panel.monster, null);
		for(int i = 0; i < 30; i++) panel.update();
		check(Arrays.stream(panel.monster).filter(monster -> monster != null).count() == 12, "Monster limit changed");
		panel.projectileList.add(new NormalBullet(panel));
		panel.monster[19] = new CountingEntity(panel);
		panel.spaceship.invincible = true;
		panel.spaceship.score = 123;
		panel.spaceship.retry();
		check(panel.projectileList.isEmpty(), "Retry left old bullets behind");
		check(Arrays.stream(panel.monster).allMatch(monster -> monster == null), "Retry left monsters behind");
		check(panel.spaceship.hp == panel.spaceship.maxHp && panel.spaceship.score == 0 && !panel.spaceship.invincible, "Retry did not reset player state");
	}

	private static void controls() {
		GamePanel panel = playingPanel();
		int startX = panel.spaceship.x;
		panel.keyHandler.keyPressed(new KeyEvent(panel, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED));
		panel.update();
		check(panel.spaceship.x == startX - 8, "Movement speed changed");
		panel.keyHandler.keyReleased(new KeyEvent(panel, KeyEvent.KEY_RELEASED, 0, 0, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED));
		panel.keyHandler.shotPressed = true;
		panel.update();
		panel.update();
		check(panel.projectileList.size() == 1, "Holding fire must preserve one shot per press");
		panel.keyHandler.shotPressed = false;
		panel.update();
		panel.keyHandler.shotPressed = true;
		panel.update();
		check(panel.projectileList.size() == 2, "Releasing fire must allow another shot");
	}

	private static void audioResourcesAndUnavailableAudio() {
		try(Sound sound = new Sound()) {
			for(int i = 0; i < sound.soundURL.length; i++) {
				check(sound.soundURL[i] != null, "Missing sound resource " + i);
			}
			// Simulate an unavailable asset without requiring audio hardware.
			sound.soundURL[1] = null;
			PrintStream original = System.err;
			ByteArrayOutputStream errors = new ByteArrayOutputStream();
			try(PrintStream capture = new PrintStream(errors)) {
				System.setErr(capture);
				for(int i = 0; i < 10; i++) {
					sound.setFile(1);
					sound.play();
					sound.loop();
					sound.stop();
					sound.checkVolume();
				}
			} finally {
				System.setErr(original);
			}
			check(errors.toString().lines().count() == 1, "Unavailable sound retried or logged on every play");
		}
	}

	private static void check(boolean condition, String message) {
		if(!condition) throw new AssertionError(message);
	}
}
