package entity;

import java.util.Random;

import main.GamePanel;

public class Monster extends Entity {

	public Monster(GamePanel gamePanel) {
		super(gamePanel);
		
		velocityX = new Random().nextInt(6) + 1;
		velocityY = new Random().nextInt(6) + 1;
		
		solidArea.x = 0;
		solidArea.y = 0;
		solidArea.width = gamePanel.TILE_SIZE;
		solidArea.height = gamePanel.TILE_SIZE;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		collision = true;
	}
	
	public void setAction() {
		if((x + velocityX) < 0 || (x + velocityX + super.gamePanel.TILE_SIZE) > super.gamePanel.SCREEN_WIDTH) {
			velocityX = -velocityX;
		}
		if((y + velocityY) <0 || (y + velocityY + super.gamePanel.TILE_SIZE) > super.gamePanel.SCREEN_HEIGHT) {
			velocityY = - velocityY;
		}
		x += velocityX;
		y += velocityY;
	}
}
