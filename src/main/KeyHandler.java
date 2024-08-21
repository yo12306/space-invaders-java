package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	GamePanel gamePanel;
	public boolean upPressed, downPressed, leftPressed, rightPressed, shotPressed, enterPressed;
	
	// SHOT DELAY
	public long lastShotTime;
	// DEBUG
	public boolean checkDrawTime;
	
	public KeyHandler(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		// TITLE STATE
		if(gamePanel.gameState == gamePanel.TITLE_STATE) {
			if(code == KeyEvent.VK_UP) {
				gamePanel.ui.commandNum--;
				gamePanel.playSE(5);
				if(gamePanel.ui.commandNum < 0) {
					gamePanel.ui.commandNum = 1;
				}
			}
			if(code == KeyEvent.VK_DOWN) {
				gamePanel.ui.commandNum++;
				gamePanel.playSE(5);
				if(gamePanel.ui.commandNum > 1) {
					gamePanel.ui.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_ENTER) {
				if(gamePanel.ui.commandNum == 0) {
					gamePanel.gameState = gamePanel.PLAY_STATE;
					gamePanel.playMusic(0);
				}
				if(gamePanel.ui.commandNum == 1) {
					System.exit(0);
				}
			}
		}
		
		// PLAY STATE
		else if(gamePanel.gameState == gamePanel.PLAY_STATE) {
			if(code == KeyEvent.VK_UP) {
				upPressed = true;
			}
			if(code == KeyEvent.VK_DOWN) {
				downPressed = true;
			}
			if(code == KeyEvent.VK_LEFT) {
				leftPressed = true;
			}
			if(code == KeyEvent.VK_RIGHT) {
				rightPressed = true;
			}
			if(code == KeyEvent.VK_SPACE) {
				shotPressed = true;
			}
			if(code == KeyEvent.VK_ESCAPE) {
				gamePanel.gameState = gamePanel.OPTION_STATE;
			}
		}
		
		// OPTION STATE
		else if(gamePanel.gameState == gamePanel.OPTION_STATE) {
			if(code == KeyEvent.VK_ESCAPE) {
				gamePanel.gameState = gamePanel.PLAY_STATE;
			}
			if(code == KeyEvent.VK_ENTER) {
				enterPressed = true;
			}
			
			int maxCommandNum = 0;
			switch(gamePanel.ui.subState) {
			case 0:
				maxCommandNum = 4;
				break;
			case 2:
				maxCommandNum = 1;
				break;
			}
			if(code == KeyEvent.VK_UP) {
				gamePanel.ui.commandNum--;
				gamePanel.playSE(5);
				if(gamePanel.ui.commandNum < 0) {
					gamePanel.ui.commandNum = maxCommandNum;
				}
			}
			if(code == KeyEvent.VK_DOWN) {
				gamePanel.ui.commandNum++;
				gamePanel.playSE(5);
				if(gamePanel.ui.commandNum > maxCommandNum) {
					gamePanel.ui.commandNum = 0;
				}
			}
			if(code == KeyEvent.VK_LEFT) {
				if(gamePanel.ui.subState == 0) {
					if(gamePanel.ui.commandNum == 0 && gamePanel.backgroundMusic.volumeScale > 0) {
						gamePanel.backgroundMusic.volumeScale--;
						gamePanel.backgroundMusic.checkVolume();
						gamePanel.playSE(5);
					}
					if(gamePanel.ui.commandNum == 1 && gamePanel.soundEffect.volumeScale > 0) {
						gamePanel.soundEffect.volumeScale--;
						gamePanel.playSE(5);
					}
				}
			}
			if(code == KeyEvent.VK_RIGHT) {
				if(gamePanel.ui.subState == 0) {
					if(gamePanel.ui.commandNum == 0 && gamePanel.backgroundMusic.volumeScale < 5) {
						gamePanel.backgroundMusic.volumeScale++;
						gamePanel.backgroundMusic.checkVolume();
						gamePanel.playSE(5);
					}
					if(gamePanel.ui.commandNum == 1 && gamePanel.soundEffect.volumeScale < 5) {
						gamePanel.soundEffect.volumeScale++;
						gamePanel.playSE(5);
					}
				}
			}
		}
		
		// GAMEOVER STATE
		else if(gamePanel.gameState == gamePanel.GAMEOVER_STATE) {
			if(code == KeyEvent.VK_UP) {
				gamePanel.ui.commandNum--;
				if(gamePanel.ui.commandNum < 0) {
					gamePanel.ui.commandNum = 1;
				}
				gamePanel.playSE(5);
			}
			if(code == KeyEvent.VK_DOWN) {
				gamePanel.ui.commandNum++;
				if(gamePanel.ui.commandNum > 1) {
					gamePanel.ui.commandNum = 0;
				}
				gamePanel.playSE(5);
			}
			if(code == KeyEvent.VK_ENTER) {
				if(gamePanel.ui.commandNum == 0) {
					gamePanel.gameState = gamePanel.PLAY_STATE;
					gamePanel.spaceship.retry();
				}
				else if(gamePanel.ui.commandNum == 1) {
					gamePanel.stopMusic();
					gamePanel.spaceship.retry();
					gamePanel.gameState = gamePanel.TITLE_STATE;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_UP) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_DOWN) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_LEFT) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}
		if(code == KeyEvent.VK_SPACE) {
			shotPressed = false;
		}
	}
}