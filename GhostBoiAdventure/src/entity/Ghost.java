package entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import mainGame.GamePanel;
import mainGame.KeyInput;

public class Ghost extends Player{
	
	boolean changeImage = false;
	public boolean transformed = false;
	int[] moveDist = new int[4];
	
	
	public Ghost(GamePanel gp, KeyInput key) {
		super(gp, key);
	}
	
	public void transform() {
		getPlayerImage();
		
		// Move player off the Solid Area & Check Hole
		moveDist = gp.collisionCheck.moveTransform(this); // Up Down Left Right
		// If inside hole
		if (moveDist[0] == 999) {
			// Dead
			//System.out.println("YOU DEAD");
			gp.deadCount++;
			gp.gameState = gp.deadState;
		} 
		// If not inside hole -> move away
		else {
			super.setPosition(x - moveDist[2] + moveDist[3], y - moveDist[0] + moveDist[1]);
//			// If new position is a door (for bug)
//			int solidLeft = solidArea.x;
//			int solidRight = solidArea.x + solidArea.width - speed;
//			int solidTop = solidArea.y;
//			int solidBottom = solidArea.y + solidArea.height;
//			int nearbyLeftCol = solidLeft/gp.tileSize + gp.upperLeftWorldCol;
//			int nearbyRightCol = solidRight/gp.tileSize + gp.upperLeftWorldCol;
//			int nearbyTopRow = solidTop/gp.tileSize + gp.upperLeftWorldRow;
//			int nearbyBottomRow = solidBottom/gp.tileSize + gp.upperLeftWorldRow;
//			boolean isDoor = false;
//			//System.out.println("My pos =" +col + " " + row);
//			for(int i=0; i<gp.obj.doorList.length;i++) {
//				if(gp.obj.doorList[i] != null && gp.obj.doorList[i].collision) {
//					//System.out.println(gp.obj.doorList[i].col + " " + gp.obj.doorList[i].row);
//					if(gp.obj.doorList[i].col == nearbyLeftCol && gp.obj.doorList[i].row == nearbyTopRow ) isDoor = true;
//					if(gp.obj.doorList[i].col == nearbyLeftCol && gp.obj.doorList[i].row == nearbyBottomRow ) isDoor = true;
//					if(gp.obj.doorList[i].col == nearbyRightCol && gp.obj.doorList[i].row == nearbyTopRow ) isDoor = true;
//					if(gp.obj.doorList[i].col == nearbyRightCol && gp.obj.doorList[i].row == nearbyBottomRow ) isDoor = true;
//				}
//			}
//			if (isDoor) {
//				// Dead
//				//System.out.println("YOU DEAD");
//				gp.deadCount++;
//				gp.gameState = gp.deadState;
//			}
		}
		// Reset Cool down
		cooldownTime = soulNum*cdTime - transformTime;
		// Change Map if need
		changeMap();
	}
	
	private void getGhostImage() {
		try {
			spriteImage = ImageIO.read(getClass().getResourceAsStream("/entity/ghost.png"));
			picture = spriteM.spriteCutter(spriteImage, 4, 2, 16, 16);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateGhost() {
		
		if (!transformed) {
			// Transform to ghost
			getGhostImage();
			transformed = true;
			
			// Set Transform Time
			transformTime = soulNum*tfTime;
			
			// Set Previous Position
			setPreviousPosition();
		}
		
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
			holyOn = false;
			gp.collisionCheck.checkCollision(this);
			gp.obj.checkAllObject(this);
					
//			System.out.println("Holy : " + holyOn);
//			System.out.println("Collision : " + collisionOn);
			
			// If not HOLY, then walk
			if(!holyOn) {
				
//				System.out.println("GHOSTING");
				super.changeMap();
							
				switch(direction) {
				case "down": y += speed; break;
				case "up": y -= speed; break;
				case "left": x -= speed; break;
				case "right": x += speed; break;
				}
			} else {
				//System.out.println("I can't go through this");
			}
			
			// Draw Walking Posture
			standPose = false;
			movementCounter++;
				
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
		
		// Check if transform time is running out
		// System.out.println("TF T: " + transformTime + "/" + soulNum*tfTime);
		if (transformTime == 0) {
			key.ghostOn = false;
			// Prevent Error
			movement = 1;
		}
		transformTime--; 
		
	}

	public void drawGhost(Graphics g) {
		
		BufferedImage image = null;
		
		switch(direction) {
		case "down":
			if (standPose) {
				image = picture[0][0];
			} else {
				image = picture[0][1];
			}
			break;
		case "up":
			if (standPose) {
				image = picture[1][0];
			} else {
				image = picture[1][1];
			}
			break;
		case "left":
			if (standPose) {
				image = picture[2][0];
			} else{
				image = picture[2][1];
			}
			break;
		case "right":
			if (standPose) {
				image = picture[3][0];
			} else {
				image = picture[3][1];
			}
			break;
		}
		
		g.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
	}
}
