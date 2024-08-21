package entity;

import main.GamePanel;

public class MonsterIII extends Monster {
	
	public MonsterIII(GamePanel gamePanel) {
		super(gamePanel);
		
		name = "MonsterIII";
		type = 2;
		maxHp = 4;
		hp = maxHp;
		
		getImage();
	}
	
	public void getImage() {
		entity = setupImage("/monster/monsterIII.png");
		entity = uTool.scaleImage(entity, gamePanel.TILE_SIZE, gamePanel.TILE_SIZE);
	}
}
