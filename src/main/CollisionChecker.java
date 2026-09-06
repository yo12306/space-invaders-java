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
		boolean contactSpaceship = intersects(entity, nextX(entity), nextY(entity), gamePanel.spaceship);
		if(contactSpaceship) {
			entity.collisionOn = true;
		}
		return contactSpaceship;
	}

	public int checkEntity(Entity entity, Entity[] target) {
		int index = 999;
		int nextX = nextX(entity);
		int nextY = nextY(entity);
		for(int i = 0; i < target.length; i++) {
			Entity candidate = target[i];
			if(candidate != null && candidate != entity && candidate.alive && !candidate.dying
					&& intersects(entity, nextX, nextY, candidate)) {
				index = i;
			}
		}
		return index;
	}

	private int nextX(Entity entity) {
		int x = entity.x + entity.solidArea.x;
		if("left".equals(entity.direction)) x -= entity.velocity;
		if("right".equals(entity.direction)) x += entity.velocity;
		return x;
	}

	private int nextY(Entity entity) {
		int y = entity.y + entity.solidArea.y;
		if("up".equals(entity.direction)) y -= entity.velocity;
		if("down".equals(entity.direction)) y += entity.velocity;
		return y;
	}

	private boolean intersects(Entity entity, int x, int y, Entity target) {
		int targetX = target.x + target.solidArea.x;
		int targetY = target.y + target.solidArea.y;
		return entity.solidArea.width > 0 && entity.solidArea.height > 0
				&& target.solidArea.width > 0 && target.solidArea.height > 0
				&& x < targetX + target.solidArea.width && x + entity.solidArea.width > targetX
				&& y < targetY + target.solidArea.height && y + entity.solidArea.height > targetY;
	}
}
