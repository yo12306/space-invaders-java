package entity;

import main.GamePanel;

public class MonsterVI extends Monster {
	
	public MonsterVI(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "MonsterVI";
		type = 2;
		maxHp = 7;
		hp = maxHp;
		
		getImage();
	}
	
	public void getImage() {
		entity = setupImage("/monster/monsterVI.png");
		entity = uTool.scaleImage(entity, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE);
	}
}
