package mainGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import entity.Ghost;
import entity.Player;
import map.MapGenerator;
import object.ObjectGenerator;

public class GamePanel extends JPanel implements ActionListener{
	
	// Panel Setting
	public int scale = 3;
	public int tileSize = 16*scale;
	public int maxCol = 16;
	public int maxRow = 12;
	public int panelX = maxCol*tileSize; // 768
	public int panelY = maxRow*tileSize; // 576
	int fps = 60;
	
	// World Setting
	public int maxWorldCol = 16*3 - 2;
	public int maxWorldRow = 12*2 - 1;
	public int upperLeftWorldCol = 0;
	public int upperLeftWorldRow = 0;
	
	// System
	Timer gameLoop = new Timer(1000/fps, this);
	public KeyInput key = new KeyInput(this);
	
	// Map
	MapGenerator map = new MapGenerator(this);
	
	// Object
	public ObjectGenerator obj = new ObjectGenerator(this);
	
	// Collision Checker
	public CollisionChecker collisionCheck = new CollisionChecker(this); 
	
	// Player
	public Player player = new Ghost(this, key);
	
	// UI
	public UI ui = new UI(this);
	
	// Game State
	public int playState = 1;
	public int pauseState = 2;
	public int deadState = 3;
	public int dialogueState = 4;
	public int endState = 0;
	public int gameState = playState;
	public int deadCount = 0;
	public int dialogueCount = -1;
	public int dialogueTime = 120;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(panelX, panelY));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);	
		
		// Add Keyboard Input
		this.addKeyListener(key);
		this.setFocusable(true);
		
		// Start Game Loop
		gameLoop.start();		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Game Loop
		if(e.getSource() == gameLoop) {
			// update detail
			update();
			// redraw the game
			repaint();
		}
		
	}
	
	private void update() {
		
		// Play Game
		if (gameState == playState) {
	
			// Update Object Solid Area
			obj.update();
			
			// Update Ghost
			if (key.ghostOn) {
				((Ghost) player).updateGhost();			
			} 
			// Changed Back to Player
			else {
				if (((Ghost) player).transformed) {
					((Ghost) player).transform();
					((Ghost) player).transformed = false;
				}
				// Check Interaction
				obj.checkInteraction(player);
				key.interact = false;
				
				// Update Person
				player.update();
			}			
			//System.out.println(dialogueCount);
			if (dialogueCount == 0) {
				//System.out.println("DIALOGUE");
				gameState = dialogueState;
				dialogueCount = -1;
			}
			if (dialogueCount > -1) dialogueCount--;
		}
		else if (gameState == pauseState) {
			// Pause Game
		}
		else if (gameState == deadState) {
			// Player Died
		}
		else if (gameState == endState) {
			// End Game
		}
		else if (gameState == dialogueState) {
			// Dialogue Page
		}
		
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(new Color(235, 140, 103));
		
		// Draw Map
		map.draw(g);
		
		// Draw Object
		obj.drawAllObject(g);
		
		if (key.ghostOn) {
			// Draw Ghost
			((Ghost) player).drawGhost(g);
		} else {
			// Draw Person
			player.draw(g);
		}
		
		// Draw UI
		ui.draw(g);
		
		// Solid Area Check
//		g.setColor(Color.red);
//		g.drawRect(player.solidArea.x, player.solidArea.y, player.solidArea.width, player.solidArea.height);
//		// Center Check
//		g.setColor(Color.yellow);
//		g.drawLine(player.x + tileSize/2, player.y, player.x + tileSize/2, player.solidArea.y + player.solidArea.height/2);
//		g.drawLine(player.x, player.solidArea.y + player.solidArea.height/2, player.x + tileSize/2, player.solidArea.y + player.solidArea.height/2);
	}
	
	public void getPosition() {
		int worldCol = (player.x + tileSize/2)/tileSize + upperLeftWorldCol;
		int worldRow = (player.y + tileSize/2)/tileSize + upperLeftWorldRow;
		System.out.println("My Pos: " + worldRow + " " + worldCol);
	}
}
