package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import entity.Entity;
import entity.MonsterI;
import entity.MonsterII;
import entity.MonsterIII;
import entity.MonsterIV;
import entity.MonsterV;
import entity.MonsterVI;
import entity.Spaceship;

public class GamePanel extends JPanel implements Runnable{
	// 16x16 TILES
	public final int ORIGINAL_TILE_SIZE = 16;
	public final int SCALE = 4;
	
	// 64x64 TILES
	public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; 
	
	public final int MAX_SCREEN_COL = 16;
	public final int MAX_SCREEN_ROW = 12;
	
	// 1024 PIXELS
	public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
	 // 768 PIXELS
	public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
	
	// FPS
	public final int FPS = 60;
	
	// SYSTEM
	public KeyHandler keyHandler = new KeyHandler(this);
	Sound backgroundMusic = new Sound();
	Sound soundEffect = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread;
	
	// BACKGROUND
	Background background = new Background(this);
	
	// ENTITY AND OBJECT
	public Spaceship spaceship = new Spaceship(this, keyHandler);
	public Entity monster[] = new Entity[20];
	public Entity obj[] = new Entity[10];
	public ArrayList<Entity> entityList = new ArrayList<Entity>();
	public ArrayList<Entity> projectileList = new ArrayList<Entity>();
	private static final int MAX_MONSTERS = 12;
    private int monsterCount = 0;
    
	// GAME STATE
	public int gameState;
	public final int TITLE_STATE = 0;
	public final int PLAY_STATE = 1;
	public final int PAUSE_STATE = 2;
	public final int OPTION_STATE = 3;
	public final int GAMEOVER_STATE = 4;
	// OPTIONS
	public boolean music;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
		this.setupGame();
	}
	
	public void setupGame() {
		aSetter.setMonster();
		gameState = TITLE_STATE;
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		// DRAWING 1 ROUND TAKES TIME
		// 0.01666 SECONDS
		double drawInterval = 1000000000 / FPS;
		
		// SUPPOSE THAT YOU HAVE JUST DRAW. THE NEXT TIME YOU DRAW IS (THE TIME YOU DREW + THE TIME YOU DREW 1 ROUND TAKES)
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while(gameThread != null) {
			// UPDATE INFORMATION SUCH AS CHARACTER POSITION
			update();
			
			// DRAW THE SCREEN WITH THE UPDATED INFORMATION
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				// CHANGE TO MILLISECONDS
				remainingTime = remainingTime/1000000;
				
				if(remainingTime < 0) {
					// IF UPDATE AND REPAINT TAKE LONGER THAN DRAWINTERVAL (YOU CAN SEE THAT REMAININGTIME IS NEGATIVE, IT MEANS THAT THE CURRENT TIME IS PAST THE EXPECTED REDRAW TIME, SO SLEEP = 0)
					remainingTime = 0;
				}
				Thread.sleep((long) remainingTime);
				nextDrawTime += drawInterval;
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update() {
		if(gameState == PLAY_STATE) {
			spaceship.update();
			
	        monsterCount = countAliveMonsters();
	        if (monsterCount < MAX_MONSTERS) {
	            createNewMonster();
	        }
	        
			for(int i = 0; i < monster.length; i++) {
				if(monster[i] != null) {
					if(monster[i].dying == false) {
						monster[i].update();
					}
					if(monster[i].dying == true) {
						monster[i] = null;
					}
				}
			}
			
			for(int i = 0; i < projectileList.size(); i++) {
				if(projectileList.get(i) != null) {
					if(projectileList.get(i).alive == true) {
						projectileList.get(i).update();
					}
					if(projectileList.get(i).alive == false) {
						projectileList.remove(i);
					}
				}
			}
			
			// CHECK IF THERE IS A DEAD MONSTER, IF DEAD REMOVE IT FROM ENTITYLIST
		    for (int i = 0; i < monster.length; i++) {
		        if (monster[i] != null && !monster[i].alive) {
		            entityList.remove(monster[i]);
		        }
		    }
		}
		if(gameState == PAUSE_STATE) {
			// NOTHING
		}
	}
	
	private int countAliveMonsters() {
	    int count = 0;
	    for (int i = 0; i < monster.length; i++) {
	        if (monster[i] != null && monster[i].alive) {
	            count++;
	        }
	    }
	    return count;
	}
	
	private void createNewMonster() {
        // CHECK THE NUMBER OF MONSTERS IN THE GAME, IT MUST NOT EXCEED MAX_MONSTERS
        if (monsterCount >= MAX_MONSTERS) {
            return;
        }

        // CREATE A NEW MONSTER (SELECT A MONSTER FROM THE MONSTER CLASS YOU CREATED)
        Entity newMonster = null;
        Random random = new Random();
        int monsterType = random.nextInt(6) + 1;

        switch (monsterType) {
            case 1:
                newMonster = new MonsterI(this);
                break;
            case 2:
                newMonster = new MonsterII(this);
                break;
            case 3:
                newMonster = new MonsterIII(this);
                break;
            case 4:
                newMonster = new MonsterIV(this);
                break;
            case 5:
                newMonster = new MonsterV(this);
                break;
            case 6:
                newMonster = new MonsterVI(this);
                break;
        }

        if (newMonster != null) {
            newMonster.x = (1024 / 2) -  TILE_SIZE/2;;
            newMonster.y = TILE_SIZE + TILE_SIZE;

            for (int i = 0; i < monster.length; i++) {
                if (monster[i] == null) {
                    monster[i] = newMonster;
                    entityList.add(newMonster);
                    monsterCount++;
                    break;
                }
            }
        }
    }
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		// DEBUG
		long drawStart = 0;
		if(keyHandler.checkDrawTime == true) {
			drawStart = System.nanoTime();
		}
		
		// TITLE SCREEN
		if(gameState == TITLE_STATE) {
			ui.draw(g2);
		}
		
		// OTHERS
		else {
			// BACKGROUND
			background.draw(g2);
			
			// ADD MONSTER TO THE LIST
			for(int i = 0; i < monster.length; i++) {
				if(monster[i] != null) {
					entityList.add(monster[i]);
				}
			}
			
			// PLAYER
			entityList.add(spaceship);
			
			for(int i = 0; i < obj.length; i++) {
				if(obj[i] != null) {
					entityList.add(obj[i]);
				}
			}
			
			for(int i = 0; i < projectileList.size(); i++) {
				if(projectileList.get(i) != null) {
					entityList.add(projectileList.get(i));
				}
			}
			
			// DRAW ENTITY
			for(int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}
			
			// EMPTY ENTITY LIST (OTHERWISE THE ENTIT LIST GETS LARGER IN EVERY LOOP)
			entityList.clear();
			
			// UI
			ui.draw(g2);
		}
		
		// DEBUG
		if(keyHandler.checkDrawTime == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Draw Time: " + passed, 10, 400);
			System.out.println("Draw Time: " + passed);
		}
		g2.dispose();
	}
	
	public void playMusic(int i) {
		backgroundMusic.setFile(i);
		backgroundMusic.play();
		backgroundMusic.loop();
	}
	
	public void stopMusic() {
		backgroundMusic.stop();
	}
	
	 // SOUND EFFECT
	public void playSE(int i) {
		soundEffect.setFile(i);
		soundEffect.play();
	}
}
