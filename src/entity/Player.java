package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import customComponent.GraphicTools;
import mainGame.GamePanel;
import mainGame.KeyInput;

public class Player extends Entity {
	
	KeyInput key;
	int standCounter = 0;
	boolean standPose = true;
	
	int movementCounter = 0;
	public int movement = 1;
	
	int initialSolidAreaX;
	int initialSolidAreaY;
	int solidAreaWidth;
	int solidAreaHeight;
	
	private String colorState = "blue";
	public String name = "boi";
	
	public int cdTime = 30;
	public int tfTime = 35;
	public int soulNum = 0;
	public int cooldownTime = soulNum*cdTime;
	public int transformTime = soulNum*tfTime;
	
	public boolean cooldown = false;
	
	public String getColorState() {
		return colorState;
	}
	
	public void setColorState(String newColor) {
		colorState = newColor;
		getPlayerImage();
	}
	
	public Player(GamePanel gp, KeyInput key) {
		this.gp = gp;
		this.key = key;
		
		// Set Initial Speed & Direction
		speed = 5;
		direction = "down";
		
		// Solid Area
		initialSolidAreaX = 4*gp.scale;
		initialSolidAreaY = 8*gp.scale;
		solidAreaWidth = 7*gp.scale;
		solidAreaHeight = 7*gp.scale;
		
		// Set Initial Position
		setPosition(gp.tileSize, gp.panelY/2 - gp.tileSize/2);
		
		// Set Solid Area
		solidArea = new Rectangle(x + initialSolidAreaX, y + initialSolidAreaY, solidAreaWidth, solidAreaHeight);
		
		// Get Image
		getPlayerImage();		
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setPosition(int x, int y, int upperLeftWorldCol, int upperLeftWorldRow) {
		this.x = x;
		this.y = y;
		gp.upperLeftWorldCol = upperLeftWorldCol;
		gp.upperLeftWorldRow = upperLeftWorldRow;
		setPreviousPosition();
	}
	
	public void setPreviousPosition() {
		previousX = x;
		previousY = y;
		previousUpperLeftWorldCol = gp.upperLeftWorldCol;
		previousUpperLeftWorldRow = gp.upperLeftWorldRow;
	}
	
	
	protected void getPlayerImage() {
		try {
			spriteImage = ImageIO.read(getClass().getResourceAsStream("/entity/boi_" + colorState + ".png"));
			picture = spriteM.spriteCutter(spriteImage, 4, 3, 16, 16);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void changeMap() {
		
		switch(direction) {
		case "down":			
			if (y + speed + gp.tileSize/2 > gp.panelY) {
				//System.out.println("Map Changed Down!");
				setPosition(x, 0 + gp.tileSize/2);
//				gp.drawNewMap = true;
				gp.upperLeftWorldRow += gp.maxRow - 1;
			}
			break;
		case "up":
			if (y - speed + gp.tileSize/2 < 0) {
				//System.out.println("Map Changed Up!");
				setPosition(x, gp.panelY - gp.tileSize/2);
//				gp.drawNewMap = true;
				gp.upperLeftWorldRow -= gp.maxRow - 1;
			}
			break;
		case "left":
			if (x - speed + gp.tileSize/2 < 0) {
				if(gp.upperLeftWorldCol == 0) {
					gp.gameState = gp.endState;
					break;
				}
				//System.out.println("Map Changed Left!");
				setPosition(gp.panelX - gp.tileSize/2, y);
//				gp.drawNewMap = true;
				gp.upperLeftWorldCol -= gp.maxCol - 1;
			}
			break;
		case "right":
			if (x + speed + gp.tileSize/2 > gp.panelX) {
				//System.out.println("Map Changed Right!");
				setPosition(0 + gp.tileSize/2, y);
//				gp.drawNewMap = true;
				gp.upperLeftWorldCol += gp.maxCol - 1;
			}
			break;
		}
	}
	
	public void revive() {
		setPosition(previousX, previousY, previousUpperLeftWorldCol, previousUpperLeftWorldRow);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub	
		if (key.down || key.up || key.left || key.right) {
			// Change Direction
			if (key.down) { 
				direction = "down"; 
			} else if (key.up) { 
				direction = "up"; 
			} else if (key.left) { 
				direction = "left"; 
			} else if (key.right) { 
				direction = "right"; 
			}
			
			// Check Collision
			collisionOn = false;
			gp.collisionCheck.checkCollision(this);

			// Check Object
			gp.obj.checkAllObject(this);

			// If not collide, then walk
			if(!collisionOn) {
				
				changeMap();
				
				switch(direction) {
				case "down": y += speed; break;
				case "up": y -= speed; break;
				case "left": x -= speed; break;
				case "right": x += speed; break;
				}
			}
			
			// Draw Walking Posture
			standPose = false;
			movementCounter++;
				
			// Make Walking Motion
			if (movementCounter > 7) {
				if (movement == 1) {
					movement = 2;
				} else if (movement == 2) {
					movement = 1;
				}
				movementCounter = 0;
			}
		} else {
			// Draw Standing Posture
			standCounter++;
			if (standCounter > 5) {
				standCounter = 0;
				standPose = true;
			}
		}
		
		// Solid Area Position
		solidArea.x = x + initialSolidAreaX;
		solidArea.y = y + initialSolidAreaY;
		
		// Reduce Cooldown Time
		if (cooldownTime > 0) {
			//System.out.println("COOLDOWN: " + cooldownTime + "/" + soulNum*cdTime);
			cooldownTime--;
			cooldown = true;
		} else {
			cooldown = false;
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		BufferedImage image = null;
		int shiftName = 0;
		
		switch(direction) {
		case "down":
			if (standPose) {
				image = picture[0][0];
			} else if (movement == 1) {
				image = picture[0][1];
			} else if (movement == 2) {
				image = picture[0][2];
			}
			shiftName = -3;
			break;
		case "up":
			if (standPose) {
				image = picture[1][0];
			} else if (movement == 1) {
				image = picture[1][1];
			} else if (movement == 2) {
				image = picture[1][2];
			}
			shiftName = -3;
			break;
		case "left":
			if (standPose) {
				image = picture[2][0];
			} else if (movement == 1) {
				image = picture[2][1];
			} else if (movement == 2) {
				image = picture[2][0];
			}
			shiftName = -3;
			break;
		case "right":
			if (standPose) {
				image = picture[3][0];
			} else if (movement == 1) {
				image = picture[3][1];
			} else if (movement == 2) {
				image = picture[3][0];
			}
			shiftName = 3;
			break;
		}
		
		g.setFont(GraphicTools.arcadeFontSuperSmall);
		g.setColor(Color.white);
		int nameH = (int) g.getFontMetrics().getStringBounds(name, g).getHeight();
		int nameW = (int) g.getFontMetrics().getStringBounds(name, g).getWidth();
		g.drawString(name, x + gp.tileSize/2 - nameW/2  + shiftName, y - 3);
		g.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
	}
}
