package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import entity.Entity;
import entity.MonsterI;
import entity.MonsterII;
import entity.MonsterIII;
import entity.MonsterIV;
import entity.MonsterV;
import entity.MonsterVI;
import entity.Spaceship;

public class GamePanel extends JPanel {
	// 16x16 TILES
	public final int ORIGINAL_TILE_SIZE = 16;
	public final int SCALE = 4;
	
	// 64x64 TILES
	public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; 
	
	public final int MAX_SCREEN_COL = 16;
	public final int MAX_SCREEN_ROW = 12;
	
	// 1024 PIXELS
	public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
	 // 768 PIXELS
	public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
	
	// FPS
	public final int FPS = 60;
	
	// SYSTEM
	public KeyHandler keyHandler = new KeyHandler(this);
	Sound backgroundMusic = new Sound();
	Sound soundEffect = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	private final long updateInterval = 1000000000L / FPS;
	private final Timer gameTimer = new Timer(1000 / FPS, event -> tick());
	private long lastUpdateTime;
	private long accumulatedTime;
	
	// BACKGROUND
	Background background = new Background(this);
	
	// ENTITY AND OBJECT
	public Spaceship spaceship = new Spaceship(this, keyHandler);
	public Entity monster[] = new Entity[20];
	public Entity obj[] = new Entity[10];
	public ArrayList<Entity> projectileList = new ArrayList<Entity>();
	private static final int MAX_MONSTERS = 12;
    private int monsterCount = 0;
	private final Random random = new Random();
    
	// GAME STATE
	public int gameState;
	public final int TITLE_STATE = 0;
	public final int PLAY_STATE = 1;
	public final int PAUSE_STATE = 2;
	public final int OPTION_STATE = 3;
	public final int GAMEOVER_STATE = 4;
	// OPTIONS
	public boolean music;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
		this.setupGame();
	}
	
	public void setupGame() {
		aSetter.setMonster();
		gameState = TITLE_STATE;
	}
	
	public void startGameLoop() {
		if(!SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeLater(this::startGameLoop);
			return;
		}
		if(gameTimer.isRunning()) {
			return;
		}
		// Load audio and the bullet sprite before gameplay can start.
		backgroundMusic.preload(0);
		soundEffect.preload(1, 2, 3, 4, 5, 6);
		UtilityTool.loadImage("/bullets/normalBullet.png", TILE_SIZE/2, TILE_SIZE/2);
		accumulatedTime = 0;
		lastUpdateTime = System.nanoTime();
		gameTimer.start();
	}

	public void stopGameLoop() {
		if(!SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeLater(this::stopGameLoop);
			return;
		}
		gameTimer.stop();
		backgroundMusic.close();
		soundEffect.close();
	}

	@Override
	public void removeNotify() {
		stopGameLoop();
		super.removeNotify();
	}

	private void tick() {
		long now = System.nanoTime();
		advanceFrame(now - lastUpdateTime);
		lastUpdateTime = now;
		repaint();
	}

	void advanceFrame(long elapsedNanos) {
		// Fixed 60 Hz simulation; discard excess backlog after a long stall.
		accumulatedTime = Math.min(accumulatedTime + elapsedNanos, updateInterval * 5);
		while(accumulatedTime >= updateInterval) {
			update();
			accumulatedTime -= updateInterval;
		}
	}
	
	public void update() {
		if(gameState != TITLE_STATE) {
			background.update();
		}
		if(gameState == PLAY_STATE) {
			spaceship.update();
			ui.update();
	        
			for(int i = 0; i < monster.length; i++) {
				if(monster[i] != null) {
					if(monster[i].alive && !monster[i].dying) {
						monster[i].update();
					}
					if(!monster[i].alive || monster[i].dying) {
						monster[i] = null;
					}
				}
			}
			
	        monsterCount = countAliveMonsters();
	        if (monsterCount < MAX_MONSTERS) {
	            createNewMonster();
	        }

			for(int i = 0; i < projectileList.size(); i++) {
				Entity projectile = projectileList.get(i);
				if(projectile != null && projectile.alive) {
					projectile.update();
				}
			}
			// Compact once, preserving shot order and updating every projectile.
			projectileList.removeIf(projectile -> projectile == null || !projectile.alive);
		}
		if(gameState == PAUSE_STATE) {
			// NOTHING
		}
	}
	
	private int countAliveMonsters() {
	    int count = 0;
	    for (int i = 0; i < monster.length; i++) {
	        if (monster[i] != null && monster[i].alive) {
	            count++;
	        }
	    }
	    return count;
	}
	
	private void createNewMonster() {
        // CHECK THE NUMBER OF MONSTERS IN THE GAME, IT MUST NOT EXCEED MAX_MONSTERS
        if (monsterCount >= MAX_MONSTERS) {
            return;
        }

        // CREATE A NEW MONSTER (SELECT A MONSTER FROM THE MONSTER CLASS YOU CREATED)
        Entity newMonster = null;
        int monsterType = random.nextInt(6) + 1;

        switch (monsterType) {
            case 1:
                newMonster = new MonsterI(this);
                break;
            case 2:
                newMonster = new MonsterII(this);
                break;
            case 3:
                newMonster = new MonsterIII(this);
                break;
            case 4:
                newMonster = new MonsterIV(this);
                break;
            case 5:
                newMonster = new MonsterV(this);
                break;
            case 6:
                newMonster = new MonsterVI(this);
                break;
        }

        if (newMonster != null) {
            newMonster.x = (1024 / 2) -  TILE_SIZE/2;;
            newMonster.y = TILE_SIZE + TILE_SIZE;

            for (int i = 0; i < monster.length; i++) {
                if (monster[i] == null) {
                    monster[i] = newMonster;
                    monsterCount++;
                    break;
                }
            }
        }
    }
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g.create();
		
		// DEBUG
		long drawStart = 0;
		if(keyHandler.checkDrawTime == true) {
			drawStart = System.nanoTime();
		}
		
		// TITLE SCREEN
		if(gameState == TITLE_STATE) {
			ui.draw(g2);
		}
		
		// OTHERS
		else {
			// BACKGROUND
			background.draw(g2);
			
			// Draw directly in layer order without building a second entity list.
			for(int i = 0; i < monster.length; i++) {
				if(monster[i] != null && monster[i].alive && !monster[i].dying) {
					monster[i].draw(g2);
				}
			}
			
			// PLAYER
			spaceship.draw(g2);
			
			for(int i = 0; i < obj.length; i++) {
				if(obj[i] != null) {
					obj[i].draw(g2);
				}
			}
			
			for(int i = 0; i < projectileList.size(); i++) {
				if(projectileList.get(i) != null) {
					projectileList.get(i).draw(g2);
				}
			}
			
			// UI
			ui.draw(g2);
		}
		
		// DEBUG
		if(keyHandler.checkDrawTime == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Draw Time: " + passed, 10, 400);
		}
		g2.dispose();
	}
	
	public void playMusic(int i) {
		backgroundMusic.setFile(i);
		backgroundMusic.play();
		backgroundMusic.loop();
	}
	
	public void stopMusic() {
		backgroundMusic.stop();
	}
	
	 // SOUND EFFECT
	public void playSE(int i) {
		soundEffect.setFile(i);
		soundEffect.play();
	}
}
