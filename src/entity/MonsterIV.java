package entity;

import main.GamePanel;

public class MonsterIV extends Monster {
	
	public MonsterIV(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "MonsterIV";
		type = 2;
		maxHp = 5;
		hp = maxHp;
		
		getImage();
	}
	
	public void getImage() {
		entity = setupImage("/monster/monsterIV.png");
		entity = uTool.scaleImage(entity, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE);
	}
}
