package entity;

import main.GamePanel;

public class MonsterI extends Monster {
	
	public MonsterI(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "MonsterI";
		type = 2;
		maxHp = 2;
		hp = maxHp;
		
		getImage();
	}
	
	public void getImage() {
		entity = setupImage("/monster/monsterI.png");
		entity = uTool.scaleImage(entity, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE);
	}
}
