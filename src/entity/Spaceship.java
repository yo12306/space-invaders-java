package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Spaceship extends Entity{

	KeyHandler keyHandler;
	public long bulletDelay = 20;
	
	// ADDED A VARIABLE TO STORE THE TIME THE SPACESHIP LAST SHOOTED
    private long lastSpaceshipShotTime;
    
    // SPACESHIP SHOOTING DELAY TIME (DELAY BETWEEN SHOOTING)
    private final long spaceshipBulletDelay = 50;
	
    private Projectile projectile;
    private boolean isShooting;
    
    public int score = 0;
    public int kill = 0;
    
	public Spaceship(GamePanel gamePanel, KeyHandler keyHandler) {
		super(gamePanel);
		this.keyHandler = keyHandler;
		type = 1;
		isShooting = false;
		
		solidArea = new Rectangle();
		solidArea.x = 0;
		solidArea.y = 0;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = gamePanel.TILE_SIZE;
		solidArea.height = gamePanel.TILE_SIZE;
		
		setDefaultValue();
		getImage();
	}
	
	public void setDefaultValue() {
		x = gamePanel.SCREEN_WIDTH/2 - (gamePanel.TILE_SIZE/2);
		y = gamePanel.SCREEN_HEIGHT - (gamePanel.TILE_SIZE);
		velocity = 8;
		direction = "up";
		
		// PLAYER STATUS
		maxHp = 6;
		hp = maxHp;
	}
	
	public void getImage() {
		UtilityTool uTool = new UtilityTool();
		entity = setupImage("/spaceship/SpaceshipI.png");
		entity = uTool.scaleImage(entity, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE);
	}
	
	public void update() {
		if(keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
			if(keyHandler.upPressed == true) {
				direction = "up";
			}
			else if (keyHandler.downPressed == true) {
				direction = "down";
			}
			else if (keyHandler.leftPressed == true) {
				direction = "left";
			}
			else if (keyHandler.rightPressed == true) {
				direction = "right";	
			}
			
			// CHECK TILE COLLISION
			collisionOn = false;
			gamePanel.cChecker.checkTile(this);
			
			// CHECK MONSTER COLLISION
			int monsterIndex = gamePanel.cChecker.checkEntity(this, gamePanel.monster);
			getDamaged(monsterIndex);
			
			// IF COLLISION IS FALSE, SPACESHIP CAN MOVE
			if(collisionOn == false) {
				switch(direction) {
				case "up":
					y -= velocity;
					break;
				case "down":
					y += velocity;
					break;
				case "left":
					x -= velocity;
					break;
				case "right":
					x += velocity;
					break;
				}
			}
		}
		
		if(invincible == true ) {
			invincibleCounter++;
			if(invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		
		if (gamePanel.keyHandler.shotPressed) {
            shootBullet();
        } else {
            isShooting = false;
        }
		
		if(hp <= 0) {
			gamePanel.gameState = gamePanel.GAMEOVER_STATE;
			gamePanel.playSE(6);
		}
	}
	
	public void getDamaged(int i) {
		if(i != 999) {
			if(invincible == false) {
				gamePanel.playSE(3);
				hp -= 1;
				invincible = true;
			}
		}
	}
	
	public void damageMonster(int i, int attack) {
		if(i != 999) {
			gamePanel.monster[i].hpBarOn = true;
			gamePanel.monster[i].hpBarCounter = 0;
			gamePanel.playSE(2);
			gamePanel.monster[i].hp -= 1;
			gamePanel.ui.addMessage("+20");
			score += 20;
			if(gamePanel.monster[i].hp <= 0) {
				if(hp < maxHp) {
					hp++;
				}
				gamePanel.monster[i].dying = true;
				gamePanel.ui.addMessage("+100");
				score += 100;
				kill++;
				gamePanel.playSE(4);
			}
		}		
	}
	
    private void shootBullet() {
        if (!isShooting) {
            Projectile projectile = new NormalBullet(gamePanel);
            projectile.set(x, y, true);
            gamePanel.projectileList.add(projectile);
            gamePanel.playSE(1);
            isShooting = true;
            lastSpaceshipShotTime = System.currentTimeMillis();
        }
    }
    
    public void retry() {
    	hp = maxHp;
    	x = gamePanel.SCREEN_WIDTH/2 - (gamePanel.TILE_SIZE/2);
		y = gamePanel.SCREEN_HEIGHT - (gamePanel.TILE_SIZE);
		score = 0;
		kill = 0;
		
		for(int i = 0; i < 12; i++) {
			gamePanel.monster[i] = null;
		}
    }
	
	public void draw(Graphics2D g2) {
		if(invincible == true) {
			
			// DRAW 50% TRANSPARENT
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		}
		g2.drawImage(entity, x, y, null);
	
		// RESET ALPHA
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}	
}
