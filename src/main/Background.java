package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {
	
	GamePanel gamePanel;
	BufferedImage background;
	BufferedImage blackHole1, blackHole2, blackHole3, blackHole4, blackHole5, blackHole6, blackHole7, blackHole8, blackHole9, blackHole10, blackHole11, blackHole12, blackHole13, blackHole14;
	
	int blackHoleSize = 250;
	int blackHoleX = (1024 / 2) - (blackHoleSize / 2);
	int blackHoleY = 0;
	int render;
	
	public Background(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		getBackgroundImage();
	}
	
	public void getBackgroundImage() {
		
		UtilityTool uTool = new UtilityTool();
		
		try {
			// BACKGROUND
			background = ImageIO.read(getClass().getResourceAsStream("/background/background.jpg"));
			background = uTool.scaleImage(background, gamePanel.SCREEN_WIDTH, gamePanel.SCREEN_HEIGHT);
			
			// BLACK HOLE
			blackHole1 = ImageIO.read(getClass().getResourceAsStream("/blackhole/backHole1.png"));
			blackHole1 = uTool.scaleImage(blackHole1, blackHoleSize, blackHoleSize);
			blackHole2 = ImageIO.read(getClass().getResourceAsStream("/blackhole/backHole2.png"));
			blackHole2 = uTool.scaleImage(blackHole2, blackHoleSize, blackHoleSize);
			blackHole3 = ImageIO.read(getClass().getResourceAsStream("/blackhole/backHole3.png"));
			blackHole3 = uTool.scaleImage(blackHole3, blackHoleSize, blackHoleSize);
			blackHole4 = ImageIO.read(getClass().getResourceAsStream("/blackhole/backHole4.png"));
			blackHole4 = uTool.scaleImage(blackHole4, blackHoleSize, blackHoleSize);
			blackHole5 = ImageIO.read(getClass().getResourceAsStream("/blackhole/backHole5.png"));
			blackHole5 = uTool.scaleImage(blackHole5, blackHoleSize, blackHoleSize);
			blackHole6 = ImageIO.read(getClass().getResourceAsStream("/blackhole/backHole6.png"));
			blackHole6 = uTool.scaleImage(blackHole6, blackHoleSize, blackHoleSize);
			blackHole7 = ImageIO.read(getClass().getResourceAsStream("/blackhole/backHole7.png"));
			blackHole7 = uTool.scaleImage(blackHole7, blackHoleSize, blackHoleSize);
			blackHole8 = ImageIO.read(getClass().getResourceAsStream("/blackhole/backHole8.png"));
			blackHole8 = uTool.scaleImage(blackHole8, blackHoleSize, blackHoleSize);
			blackHole9 = ImageIO.read(getClass().getResourceAsStream("/blackhole/backHole9.png"));
			blackHole9 = uTool.scaleImage(blackHole9, blackHoleSize, blackHoleSize);
			blackHole10 = ImageIO.read(getClass().getResourceAsStream("/blackhole/backHole10.png"));
			blackHole10 = uTool.scaleImage(blackHole10, blackHoleSize, blackHoleSize);
			blackHole11 = ImageIO.read(getClass().getResourceAsStream("/blackhole/backHole11.png"));
			blackHole11 = uTool.scaleImage(blackHole11, blackHoleSize, blackHoleSize);
			blackHole12 = ImageIO.read(getClass().getResourceAsStream("/blackhole/backHole12.png"));
			blackHole12 = uTool.scaleImage(blackHole12, blackHoleSize, blackHoleSize);
			blackHole13 = ImageIO.read(getClass().getResourceAsStream("/blackhole/backHole13.png"));
			blackHole13 = uTool.scaleImage(blackHole13, blackHoleSize, blackHoleSize);
			blackHole14 = ImageIO.read(getClass().getResourceAsStream("/blackhole/backHole14.png"));
			blackHole14 = uTool.scaleImage(blackHole14, blackHoleSize, blackHoleSize);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, gamePanel);
		
		BufferedImage image = null;
		
		if(render >= 0 && render < 5) {
			image = blackHole1;
			g2.drawImage(image, blackHoleX, blackHoleY, gamePanel);
			render++;
		}
		else if(render >= 5 && render < 10) {
			image = blackHole2;
			g2.drawImage(image, blackHoleX, blackHoleY, gamePanel);
			render++;
		}
		else if(render >= 10 && render < 15) {
			image = blackHole3;
			g2.drawImage(image, blackHoleX, blackHoleY, gamePanel);
			render++;
		}
		else if(render >= 15 && render < 20) {
			image = blackHole4;
			g2.drawImage(image, blackHoleX, blackHoleY, gamePanel);
			render++;
		}
		else if(render >= 20 && render < 25) {
			image = blackHole5;
			g2.drawImage(image, blackHoleX, blackHoleY, gamePanel);
			render++;
		}
		else if(render >= 25 && render < 30) {
			image = blackHole6;
			g2.drawImage(image, blackHoleX, blackHoleY, gamePanel);
			render++;
		}
		else if(render >= 30 && render < 35) {
			image = blackHole7;
			g2.drawImage(image, blackHoleX, blackHoleY, gamePanel);
			render++;
		}
		else if(render >= 35 && render < 40) {
			image = blackHole8;
			g2.drawImage(image, blackHoleX, blackHoleY, gamePanel);
			render++;
		}
		else if(render >= 40 && render < 45) {
			image = blackHole9;
			g2.drawImage(image, blackHoleX, blackHoleY, gamePanel);
			render++;
		}
		else if(render >= 45 && render < 50) {
			image = blackHole10;
			g2.drawImage(image, blackHoleX, blackHoleY, gamePanel);
			render++;
		}
		else if(render >= 50 && render < 55) {
			image = blackHole11;
			g2.drawImage(image, blackHoleX, blackHoleY, gamePanel);
			render++;
		}
		else if(render >= 55 && render < 60) {
			image = blackHole12;
			g2.drawImage(image, blackHoleX, blackHoleY, gamePanel);
			render++;
		}
		else if(render >= 60 && render < 65) {
			image = blackHole13;
			g2.drawImage(image, blackHoleX, blackHoleY, gamePanel);
			render++;
		}
		else if(render >= 65 && render < 70){
			image = blackHole14;
			g2.drawImage(image, blackHoleX, blackHoleY, gamePanel);
			render++;
		}
		else {
			render = 0;
		}
	}
}
