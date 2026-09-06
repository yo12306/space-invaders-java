package entity;

import main.GamePanel;

public class Heart extends Entity {
	
	public Heart(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "Hp";
		
		heart_blank = setupImage("/objects/heart_blank.png");
		heart_half = setupImage("/objects/heart_half.png");
		heart_full = setupImage("/objects/heart_full.png");
	}
}
