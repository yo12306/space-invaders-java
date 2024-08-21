package main;

import entity.Entity;

public class CollisionChecker {
	
	GamePanel gamePanel;
	
	public CollisionChecker(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void checkTile(Entity entity) {
		int entityLeftX = entity.x + entity.solidArea.x;
		int entityRightX = entity.x + entity.solidArea.x + entity.solidArea.width;
		int entityTopY = entity.y + entity.solidArea.y;
		int entityBottomY = entity.y + entity.solidArea.y + entity.solidArea.height;
		
		switch(entity.direction) {
		case "up":
			if(entityTopY - entity.velocity < 0) entity.collisionOn = true;
			break;
		case "down":
			if(entityBottomY + entity.velocity > gamePanel.SCREEN_HEIGHT) entity.collisionOn = true;
			break;
		case "left":
			if(entityLeftX - entity.velocity < 0) entity.collisionOn = true;
			break;
		case "right":
			if(entityRightX + entity.velocity > gamePanel.SCREEN_WIDTH) entity.collisionOn = true;
			break;
		}
	}
	
	public boolean checkSpaceship(Entity entity) {
		boolean contactSpaceship = false;
		
		entity.solidArea.x = entity.x + entity.solidArea.x;
		entity.solidArea.y = entity.y + entity.solidArea.y;
		
		gamePanel.spaceship.solidArea.x = gamePanel.spaceship.x + gamePanel.spaceship.solidArea.x;
		gamePanel.spaceship.solidArea.y = gamePanel.spaceship.y + gamePanel.spaceship.solidArea.y;
		
		switch(entity.direction) {
		case "up":
			entity.solidArea.y -= entity.velocity;
			break;
		case "down":
			entity.solidArea.y += entity.velocity;
			break;
		case "left":
			entity.solidArea.x -= entity.velocity;
			break;
		case "right":
			entity.solidArea.x += entity.velocity;
			break;
		}
		
		if(entity.solidArea.intersects(gamePanel.spaceship.solidArea)) {
			entity.collisionOn = true;
			contactSpaceship = true;
		}
		
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gamePanel.spaceship.solidArea.x = gamePanel.spaceship.solidAreaDefaultX;
		gamePanel.spaceship.solidArea.y = gamePanel.spaceship.solidAreaDefaultY;
	
		return contactSpaceship;
	}
	public int checkEntity(Entity entity, Entity[] target) {
		int index = 999;
		
		for(int i = 0; i < target.length; i++) {
			if(target[i] != null) {
				entity.solidArea.x = entity.x + entity.solidArea.x;
				entity.solidArea.y = entity.y + entity.solidArea.y;
				
				target[i].solidArea.x = target[i].x + target[i].solidArea.x;
				target[i].solidArea.y = target[i].y + target[i].solidArea.y;
				
				switch(entity.direction) {
				case "up":
					entity.solidArea.y -= entity.velocity;
					break;
				case "down":
					entity.solidArea.y += entity.velocity;
					break;
				case "left":
					entity.solidArea.x -= entity.velocity;
					break;
				case "right":
					entity.solidArea.x += entity.velocity;
					break;
				}
				
				if(entity.solidArea.intersects(target[i].solidArea)) {
					index = i;
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;
			}
		}
		return index;
	}
}
