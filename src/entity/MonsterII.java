package entity;

import main.GamePanel;

public class MonsterII extends Monster {
	
	public MonsterII(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "MonsterII";
		type = 2;
		maxHp = 3;
		hp = maxHp;
		
		getImage();
	}
	
	public void getImage() {
		entity = setupImage("/monster/monsterII.png");
		entity = uTool.scaleImage(entity, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE);
	}
}
