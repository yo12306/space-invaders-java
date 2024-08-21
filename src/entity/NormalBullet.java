package entity;

import java.awt.Graphics2D;

import main.GamePanel;

public class NormalBullet extends Projectile {
	
	public NormalBullet(GamePanel gamePanel) {
		super(gamePanel);
		name = "NormalBullet";
		velocity = 15;
		alive = false;
		solidArea.width = gamePanel.TILE_SIZE/2;
		solidArea.height = gamePanel.TILE_SIZE/2;
		getImage();
	}
	
	public void getImage() {
		entity = setupImage("/bullets/normalBullet.png");
	}
	
	public void draw(Graphics2D g2) {	
		g2.drawImage(entity, x, y, gamePanel.TILE_SIZE/2, gamePanel.TILE_SIZE/2, null);
	}
}
