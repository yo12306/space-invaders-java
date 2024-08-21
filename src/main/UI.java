package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import entity.Entity;
import entity.Heart;

public class UI {

	GamePanel gamePanel;
	Graphics2D g2;
	Font gamerBold;
	
	BufferedImage heart_full, heart_half, heart_blank;
	
	public int commandNum = 0;
	double playTime;
	
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	
	public int subState = 0;
	
	public UI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		InputStream is = getClass().getResourceAsStream("/font/Gamer Bold.ttf");
		try {
			gamerBold = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// CREATE HP OBJECT
		Entity heart = new Heart(gamePanel);
		heart_blank = heart.heart_blank;
		heart_full = heart.heart_full;
		heart_half = heart.heart_half;
	}
	
	public void addMessage(String text) {
		message.add(text);
		messageCounter.add(0);
	}
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		// NAME OF FONT, FONT STYLE, FONT SIZE
		g2.setFont(gamerBold);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
		g2.setColor(Color.white);
		
		//TITLE STATE
		if(gamePanel.gameState == gamePanel.TITLE_STATE) {
			drawTitleScreen();
		}
		
		// PLAY STATE
		if(gamePanel.gameState == gamePanel.PLAY_STATE) {
			g2.setColor(Color.white);
			g2.drawString("SCORE : " + gamePanel.spaceship.score, 25, 50);
			g2.drawString("KILL : " + gamePanel.spaceship.kill, 25, 110);
			drawSpaceshipHp();
			drawMessage();
		}
		
		// PAUSE STATE
		if(gamePanel.gameState == gamePanel.PAUSE_STATE) {
			drawSpaceshipHp();
			drawPauseScreen();
		}
		
		// OPTION STATE
		if(gamePanel.gameState == gamePanel.OPTION_STATE) {
			drawPauseScreen();
			drawOptionScreen();
		}
		
		// GAMEOVER STATE
		if(gamePanel.gameState == gamePanel.GAMEOVER_STATE) {
			drawGameOverScreen();
		}
	}
	
	public void drawGameOverScreen() {
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0, 0, gamePanel.SCREEN_WIDTH, gamePanel.SCREEN_HEIGHT);
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
		
		text = "GAME OVER";
		
		// SHADOW
		g2.setColor(Color.gray);
		x = getXforCenteredText(text);
		y = gamePanel.TILE_SIZE*4;
		g2.drawString(text, x, y);
		
		// MAIN
		g2.setColor(Color.white);
		g2.drawString(text, x - 5, y - 5);
		
		// SCORE
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
		text = "YOUR SCORE:";
		x = getXforCenteredText(text);
		y += gamePanel.TILE_SIZE*2;
		g2.drawString(text + gamePanel.spaceship.score, x, y);
		
		// RETRY
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f));
		text = "RETRY";
		x = getXforCenteredText(text);
		y  += gamePanel.TILE_SIZE*3;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x - 40, y);
		}
		
		// BACK TO TITLE SCREEN
		text = "BACK TO TITLE";
		x = getXforCenteredText(text);
		y  += gamePanel.TILE_SIZE;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x - 40, y);
		}
		
	}
		
	public void drawSpaceshipHp() {
		int x = gamePanel.TILE_SIZE/4;
		int y = gamePanel.TILE_SIZE*2;
		int i = 0;
		
		// DRAW MAX HP
		while(i < gamePanel.spaceship.maxHp / 2) {
			g2.drawImage(heart_blank, x, y, gamePanel);
			i++;
			x += gamePanel.TILE_SIZE;
		}
		
		// RESET
		x = gamePanel.TILE_SIZE/4;
		y = gamePanel.TILE_SIZE*2;
		i = 0;
		
		// DRAW CURRENT HP
		while(i < gamePanel.spaceship.hp) {
			g2.drawImage(heart_half, x, y, gamePanel);
			i++;
			if(i < gamePanel.spaceship.hp) {
				g2.drawImage(heart_full, x, y, gamePanel);
			}
			i++;
			x += gamePanel.TILE_SIZE; 
		}
	}
	
	public void drawMessage() {
		int messageX = gamePanel.TILE_SIZE;
		int messageY = gamePanel.TILE_SIZE*4;
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
		for(int i = 0; i < message.size(); i++) {
			if(message.get(i) != null) {
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
			
				int counter = messageCounter.get(i) + 1;
				messageCounter.set(i, counter);
				messageY += 50;
				
				if(messageCounter.get(i) > 50) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}
	}
	
	public void drawTitleScreen() {
		g2.drawImage(gamePanel.background.background, 0, 0, gamePanel.SCREEN_WIDTH, gamePanel.SCREEN_HEIGHT, gamePanel);
		
		// TITLE NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
		String text = "Space Invaders";
		int x = getXforCenteredText(text);
		int y = gamePanel.TILE_SIZE * 2;
		
		// SHADOW
		g2.setColor(Color.gray);
		g2.drawString(text, x + 5, y + 5);
		
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		// MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
		text = "START GAME";
		x = getXforCenteredText(text);
		y += gamePanel.TILE_SIZE*6;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x - gamePanel.TILE_SIZE, y);
		}
		
		text = "EXIT";
		x = getXforCenteredText(text);
		y += gamePanel.TILE_SIZE*2;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x - gamePanel.TILE_SIZE, y);
		}
	}
	
	public void drawOptionScreen() {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		// SUB WINDOW
		int frameX = gamePanel.TILE_SIZE * 4;
		int frameY = gamePanel.TILE_SIZE*2;
		int frameWidth = gamePanel.TILE_SIZE*8;
		int frameHeight = gamePanel.TILE_SIZE*8;
		
		// DRAW SUB WINDOW
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		switch(subState) {
		case 0:
			options_Top(frameX, frameY);
			break;
		case 1:
			options_Control(frameX, frameY);
			break;
		case 2:
			options_EndGame(frameX, frameY);
			break;
		}
		gamePanel.keyHandler.enterPressed = false;
	}
	
	public void options_Top(int frameX, int frameY) {
		int textX;
		int textY;
		
		// TITLE
		String text = "OPTIONS";
		textX = getXforCenteredText(text);
		textY = frameY + gamePanel.TILE_SIZE;
		g2.drawString(text, textX, textY);
		
		// MUSIC
		textX = frameX + gamePanel.TILE_SIZE;
		textY += gamePanel.TILE_SIZE;
		g2.drawString("MUSIC", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
		}
		
		// SOUND EFFECT
		textY += gamePanel.TILE_SIZE;
		g2.drawString("SOUND EFFECT", textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX - 25, textY);
		}
		
		// CONTROL
		textY += gamePanel.TILE_SIZE;
		g2.drawString("CONTROL", textX, textY);
		if(commandNum == 2) {
			g2.drawString(">", textX - 25, textY);
			if(gamePanel.keyHandler.enterPressed == true) {
				subState = 1;
				commandNum = 0;
			}
		}
		
		// END GAME
		textY += gamePanel.TILE_SIZE;
		g2.drawString("EXIT", textX, textY);
		if(commandNum == 3) {
			g2.drawString(">", textX - 25, textY);
			if(gamePanel.keyHandler.enterPressed == true) {
				subState = 2;
				commandNum = 0;
			}
		}
		
		// BACK TO PLAY SCREEN
		textY += gamePanel.TILE_SIZE*2;
		g2.drawString("BACK", textX, textY);
		if(commandNum == 4) {
			g2.drawString(">", textX - 25, textY);
			if(gamePanel.keyHandler.enterPressed == true) {
				gamePanel.gameState = gamePanel.PLAY_STATE;
				commandNum = 0;
			}
		}
		
		// MUSIC
		textX = frameX + gamePanel.TILE_SIZE*5 + gamePanel.TILE_SIZE/2 + gamePanel.TILE_SIZE/4;
		textY = frameY + gamePanel.TILE_SIZE + gamePanel.TILE_SIZE/2 + gamePanel.TILE_SIZE/8;
		g2.drawRect(textX, textY, 120, 24);
		int volumeWidth = 24 * gamePanel.backgroundMusic.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		// SE
		textY += gamePanel.TILE_SIZE;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24 * gamePanel.soundEffect.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
	}
	
	public void options_Control(int frameX, int frameY) {
		int textX;
		int textY;
		
		// TITLE
		String text = "CONTROL";
		textX = getXforCenteredText(text);
		textY = frameY + gamePanel.TILE_SIZE;
		g2.drawString(text, textX, textY);
		
		textX = frameX + gamePanel.TILE_SIZE;
		textY += gamePanel.TILE_SIZE;
		g2.drawString("MOVE", textX, textY);
		
		textY += gamePanel.TILE_SIZE;
		g2.drawString("CONFIRM", textX, textY);
		
		textY += gamePanel.TILE_SIZE;
		g2.drawString("SHOOT", textX, textY);
		
		textY += gamePanel.TILE_SIZE;
		g2.drawString("OPTIONS", textX, textY);
		
		textX = frameX + gamePanel.TILE_SIZE*5;
		textY = frameY + gamePanel.TILE_SIZE*2;
		g2.drawString("WASD", textX, textY);
		
		textY += gamePanel.TILE_SIZE;
		g2.drawString("ENTER", textX, textY);
		
		textY += gamePanel.TILE_SIZE;
		g2.drawString("SPACEBAR", textX - gamePanel.TILE_SIZE/2, textY);
		
		textY += gamePanel.TILE_SIZE;
		g2.drawString("ESC", textX, textY);
		
		// BACK
		textX = frameX + gamePanel.TILE_SIZE;
		textY = frameY + gamePanel.TILE_SIZE*7;
		g2.drawString("BACK", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if(gamePanel.keyHandler.enterPressed == true) {
				subState = 0;
				commandNum = 2;
			}
		}
	}
	
	public void options_EndGame(int frameX, int frameY) {
		int textX = frameX + gamePanel.TILE_SIZE;
		int textY = frameY + gamePanel.TILE_SIZE*2;
		
		String confirm = "QUIT THE GAME?";	
		g2.drawString(confirm, textX, textY);
		
		String text = "YES";
		textX = getXforCenteredText(text);
		textY += gamePanel.TILE_SIZE*3;
		g2.drawString(text, textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if(gamePanel.keyHandler.enterPressed == true) {
				System.exit(0);
			}
		}
		
		text = "NO";
		textX = getXforCenteredText(text);
		textY += gamePanel.TILE_SIZE;
		g2.drawString(text, textX, textY);
		if (commandNum == 1) {
			g2.drawString(">", textX - 25, textY);
			if(gamePanel.keyHandler.enterPressed == true) {
				subState = 0;
				commandNum = 3;
			}
		}
		
		
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		Color c = new Color(0, 0, 0, 200);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width - 10, height - 10, 25, 25);
	}
	
	public void drawPauseScreen() {
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gamePanel.TILE_SIZE;
		g2.drawString(text, x, y);
	}
	
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gamePanel.SCREEN_WIDTH/2 - length/2;
		return x;
	}
}
