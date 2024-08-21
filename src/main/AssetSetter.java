package main;

import entity.MonsterI;
import entity.MonsterII;
import entity.MonsterIII;
import entity.MonsterIV;
import entity.MonsterV;
import entity.MonsterVI;

public class AssetSetter {
	
	GamePanel gamePanel;
	
	public AssetSetter(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void setMonster() {
		gamePanel.monster[0] = new MonsterI(gamePanel);
		gamePanel.monster[0].x = (1024 / 2) -  gamePanel.TILE_SIZE/2;
		gamePanel.monster[0].y = gamePanel.TILE_SIZE + gamePanel.TILE_SIZE;
		
		gamePanel.monster[1] = new MonsterI(gamePanel);
		gamePanel.monster[1].x = (1024 / 2) -  gamePanel.TILE_SIZE/2;
		gamePanel.monster[1].y = gamePanel.TILE_SIZE + gamePanel.TILE_SIZE;
		
		gamePanel.monster[2] = new MonsterII(gamePanel);
		gamePanel.monster[2].x = (1024 / 2) -  gamePanel.TILE_SIZE/2;
		gamePanel.monster[2].y = gamePanel.TILE_SIZE + gamePanel.TILE_SIZE;
		
		gamePanel.monster[3] = new MonsterII(gamePanel);
		gamePanel.monster[3].x = (1024 / 2) -  gamePanel.TILE_SIZE/2;
		gamePanel.monster[3].y = gamePanel.TILE_SIZE + gamePanel.TILE_SIZE;
		
		gamePanel.monster[4] = new MonsterIII(gamePanel);
		gamePanel.monster[4].x = (1024 / 2) -  gamePanel.TILE_SIZE/2;
		gamePanel.monster[4].y = gamePanel.TILE_SIZE + gamePanel.TILE_SIZE;
		
		gamePanel.monster[5] = new MonsterIII(gamePanel);
		gamePanel.monster[5].x = (1024 / 2) -  gamePanel.TILE_SIZE/2;
		gamePanel.monster[5].y = gamePanel.TILE_SIZE + gamePanel.TILE_SIZE;
		
		gamePanel.monster[6] = new MonsterIV(gamePanel);
		gamePanel.monster[6].x = (1024 / 2) -  gamePanel.TILE_SIZE/2;
		gamePanel.monster[6].y = gamePanel.TILE_SIZE + gamePanel.TILE_SIZE;
		
		gamePanel.monster[7] = new MonsterIV(gamePanel);
		gamePanel.monster[7].x = (1024 / 2) -  gamePanel.TILE_SIZE/2;
		gamePanel.monster[7].y = gamePanel.TILE_SIZE + gamePanel.TILE_SIZE;
		
		gamePanel.monster[8] = new MonsterV(gamePanel);
		gamePanel.monster[8].x = (1024 / 2) -  gamePanel.TILE_SIZE/2;
		gamePanel.monster[8].y = gamePanel.TILE_SIZE + gamePanel.TILE_SIZE;
		
		gamePanel.monster[9] = new MonsterV(gamePanel);
		gamePanel.monster[9].x = (1024 / 2) -  gamePanel.TILE_SIZE/2;
		gamePanel.monster[9].y = gamePanel.TILE_SIZE + gamePanel.TILE_SIZE;
		
		gamePanel.monster[10] = new MonsterVI(gamePanel);
		gamePanel.monster[10].x = (1024 / 2) -  gamePanel.TILE_SIZE/2;
		gamePanel.monster[10].y = gamePanel.TILE_SIZE + gamePanel.TILE_SIZE;
		
		gamePanel.monster[11] = new MonsterVI(gamePanel);
		gamePanel.monster[11].x = (1024 / 2) -  gamePanel.TILE_SIZE/2;
		gamePanel.monster[11].y = gamePanel.TILE_SIZE + gamePanel.TILE_SIZE;
	}
}
