package entity;

import main.GamePanel;

public class MonsterV extends Monster {
	
	public MonsterV(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "MonsterV";
		type = 2;
		maxHp = 6;
		hp = maxHp;
		
		getImage();
	}
	
	public void getImage() {
		entity = setupImage("/monster/monsterV.png");
		entity = uTool.scaleImage(entity, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE);
	}
}
