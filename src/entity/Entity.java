package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	public GamePanel gamePanel;
	public int x, y;
	public int velocity;
	public int velocityX, velocityY;
	public String direction = "";
	public String name;

	public BufferedImage normalBullet;
	public BufferedImage entity;
	public BufferedImage heart_blank, heart_half, heart_full;
	
	public Rectangle solidArea = new Rectangle(0, 0, 64, 64);
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public int solidAreaDefaultX, solidAreaDefaultY;
	
	// STATE
	public boolean collisionOn = false;
	public boolean collision = false;
	public boolean invincible;
	public boolean alive = true;
	public boolean dying = false;
	public boolean hpBarOn = false;
	
	
	// COUNTER
	public int invincibleCounter = 0;
	int dyingCounter = 0;
	int hpBarCounter = 0;
	
	// CHARACTER ATTRIBUTES
	public int type;
	public int maxHp;
	public int hp;
	public int attack;
	
	// UTOOL SCALE IMAGE
	public UtilityTool uTool = new UtilityTool();
	
	public Entity(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void setAction() {}
	
	public void update() {
		setAction();
		
		collisionOn = false;
		gamePanel.cChecker.checkTile(this);
		gamePanel.cChecker.checkEntity(this, gamePanel.monster);
		boolean contackSpaceship = gamePanel.cChecker.checkSpaceship(this);
		
		if(contackSpaceship == true) {
			if(gamePanel.spaceship.invincible == false) {
				gamePanel.playSE(3);
				gamePanel.spaceship.hp -= 1;
				gamePanel.spaceship.invincible = true;
			}
		}
	}
	
	public BufferedImage setupImage(String pathName) {
		BufferedImage image = null;	
		try {
			image = ImageIO.read(getClass().getResourceAsStream(pathName));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public void draw(Graphics2D g2) {
		if(dying == true) {
			dyingAnimation(g2);
		}
		
		// MONSTER HP BAR
		if(type == 2 && hpBarOn == true) {
			double oneScale = (double) gamePanel.TILE_SIZE/maxHp;
			double hpBarValue = oneScale*hp;
			g2.setColor(Color.black);
			g2.fillRect(x - 1, y - 16, gamePanel.TILE_SIZE + 2, 7);
			g2.setColor(new Color(255, 0, 30));
			g2.fillRect(x, y - 15, (int)hpBarValue, 5);
			hpBarCounter++;
			
			if(hpBarCounter > 600) {
				hpBarCounter = 0;
				hpBarOn = false;
			}
		}		
		g2.drawImage(entity, x, y, null);
	}
	
	public void dyingAnimation(Graphics2D g2) {
		dyingCounter++;
		
		int i = 5;
		
		if(dyingCounter <= i) {
			changeAlpha(g2, 0f);
		}
		if(dyingCounter > i && dyingCounter <= i*2) {
			changeAlpha(g2, 1f);
		}
		if(dyingCounter > i*2 && dyingCounter <= i*3) {
			changeAlpha(g2, 0f);
		}
		if(dyingCounter > i*3 && dyingCounter <= i*4) {
			changeAlpha(g2, 1f);
		}
		if(dyingCounter > i*4 && dyingCounter <= i*5) {
			changeAlpha(g2, 0f);
		}
		if(dyingCounter > i*5 && dyingCounter <= i*6) {
			changeAlpha(g2, 1f);
		}
		if(dyingCounter > i*6 && dyingCounter <= i*7) {
			changeAlpha(g2, 0f);
		}
		if(dyingCounter > i*7 && dyingCounter <= i*8) {
			changeAlpha(g2, 1f);
		}
		if(dyingCounter > i*8) {
			gamePanel.monster[i].dying = false;
			gamePanel.monster[i].alive = false;
		}
	}
	
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}
}