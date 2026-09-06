package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Background {

	GamePanel gamePanel;
	BufferedImage background;
	private final BufferedImage[] blackHoleFrames = new BufferedImage[14];

	int blackHoleSize = 250;
	int blackHoleX = (1024 / 2) - (blackHoleSize / 2);
	int blackHoleY = 0;
	int render;

	public Background(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		getBackgroundImage();
	}

	public void getBackgroundImage() {
		background = UtilityTool.loadImage("/background/background.jpg", gamePanel.SCREEN_WIDTH, gamePanel.SCREEN_HEIGHT);
		for(int i = 0; i < blackHoleFrames.length; i++) {
			blackHoleFrames[i] = UtilityTool.loadImage("/blackhole/backHole" + (i + 1) + ".png", blackHoleSize, blackHoleSize);
		}
	}

	public void update() {
		render = (render + 1) % (blackHoleFrames.length * 5);
	}

	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, gamePanel);
		g2.drawImage(blackHoleFrames[render / 5], blackHoleX, blackHoleY, gamePanel);
	}
}
