package entity;

import main.GamePanel;

public class Projectile extends Entity {
	
	public Projectile(GamePanel gamePanel) {
		super(gamePanel);
	}
	
	public void set(int x, int y, boolean alive) {	
		this.x = x + gamePanel.TILE_SIZE/4;
		this.y = y - gamePanel.TILE_SIZE/4;
		this.alive = alive;
		this.hp = maxHp;
		this.attack = 2;
	}
	
	public void update() {
		int monsterIndex = gamePanel.cChecker.checkEntity(this, gamePanel.monster);
		if(monsterIndex != 999) {
			gamePanel.spaceship.damageMonster(monsterIndex, attack);
			alive = false;
		}
		y -= velocity;
		if(y < -gamePanel.TILE_SIZE) {
			alive = false;
		}
		System.out.println(gamePanel.projectileList.size());
	}
}