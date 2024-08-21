package entity;

import main.GamePanel;

public class Heart extends Entity {
	
	public Heart(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Hp";
		
		heart_blank = setupImage("/objects/heart_blank.png");
		heart_half = setupImage("/objects/heart_half.png");
		heart_full = setupImage("/objects/heart_full.png");
		
		heart_blank = uTool.scaleImage(heart_blank, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE);
		heart_half = uTool.scaleImage(heart_half, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE);
		heart_full = uTool.scaleImage(heart_full, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE);
	}
}
