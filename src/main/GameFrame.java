package main;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
	
	public GamePanel gamePanel = new GamePanel();
	
	public GameFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Space Invaders");
		this.add(gamePanel);	
		this.pack();	
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		gamePanel.startGameThread();
	}
}
