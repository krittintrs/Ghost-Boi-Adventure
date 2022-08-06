package object;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import mainGame.GamePanel;
import mainGame.SpriteManager;
import map.MapGenerator;

public class ObjectGenerator {

	GamePanel gp;
	SpriteManager spriteM = new SpriteManager();
	private static int objMaxNum = 25;
	public BufferedImage[][] objectPic;
	public static int[][][] objCreatorList = new int[3][objMaxNum][3];
	public Object[] doorList = new Door[objMaxNum];
	public Object[] leverList = new Lever[objMaxNum];
	public Object[] soulList = new Soul[objMaxNum];
	public Object[][] objList = {doorList, leverList, soulList};
	public static int[] doorType = new int[objMaxNum];
	public static int vertiDoor = 0, horiDoor = 1;
	
	public ObjectGenerator(GamePanel gp) {
		this.gp = gp;
		getObjectImage();
		
		// Generate Object
		for(int i=0; i<objMaxNum; i++) {
			if (objCreatorList[0][i][0] != 0) {
				createDoor(i, objCreatorList[0][i][0], objCreatorList[0][i][1], objCreatorList[0][i][2]);
			}
			if (objCreatorList[1][i][0] != 0) {
				createLever(i, objCreatorList[1][i][0], objCreatorList[1][i][1], objCreatorList[1][i][2]);
			}
			if (i > 0 && objCreatorList[2][i][0] != 0) {
				createSoul(i, objCreatorList[2][i][0], objCreatorList[2][i][1]);
			}
		}
		
		// Set Puzzle
		for(int i=0; i < objMaxNum; i++) {
			// Lever
			if(leverList[i] != null) {
				// 4 5 6
				if(leverList[i].name.contains("_4")) {
					((Lever) leverList[i]).oneTime = false;
					((Lever) leverList[i]).setLeverKey(5);
				}
				if(leverList[i].name.contains("_5")) {
					((Lever) leverList[i]).oneTime = false;
					((Lever) leverList[i]).setLeverKey(6);
				}	
				if(leverList[i].name.contains("_6")) {
					((Lever) leverList[i]).oneTime = false;
				}
				
				// 9 12 13 14 15 16
				if(leverList[i].name.contains("_9")) {
					((Lever) leverList[i]).oneTime = false;
				}
				if(leverList[i].name.contains("_12")) {
					((Lever) leverList[i]).oneTime = false;
					((Lever) leverList[i]).setLeverKey(15);
				}
				if(leverList[i].name.contains("_13")) {
					((Lever) leverList[i]).oneTime = false;
					((Lever) leverList[i]).setLeverKey(15);
				}
				if(leverList[i].name.contains("_14")) {
					((Lever) leverList[i]).oneTime = false;
					((Lever) leverList[i]).setLeverKey(13);
				}
				if(leverList[i].name.contains("_15")) {
					((Lever) leverList[i]).oneTime = false;
					((Lever) leverList[i]).setLeverKey(16);
					((Lever) leverList[i]).setLeverKey(12);
				}
				if(leverList[i].name.contains("_16")) {
					((Lever) leverList[i]).oneTime = false;
					((Lever) leverList[i]).setLeverKey(13);
					((Lever) leverList[i]).setLeverKey(14);
				}
				
				// 17 18 19
				if(leverList[i].name.contains("_17")) {
					((Lever) leverList[i]).oneTime = false;
					((Lever) leverList[i]).setLeverKey(19);
				}
				if(leverList[i].name.contains("_18")) {
					((Lever) leverList[i]).oneTime = false;
					((Lever) leverList[i]).setLeverKey(19);
				}
				if(leverList[i].name.contains("_19")) {
					((Lever) leverList[i]).oneTime = false;
				}
			}
		}	
	}
	
	public void createDoor(int i, int row, int col, int code) {
		int solidX = col%15;
		int solidY = row%11;
		if(solidX==0 && col>=15) solidX = 15;
		if(solidY==0 && row>=11) solidY = 11;
		if (doorType[i] == vertiDoor) {
			doorList[i] = new Door(row, col, objectPic[0][2], objectPic[0][3]);
		} else if (doorType[i] == horiDoor) {
			doorList[i] = new Door(row, col, objectPic[0][0], objectPic[0][1]);
		}
		if(code == 0) code = -1;
		doorList[i].name += ("_" + code);
		((Door) doorList[i]).setDoorKey(code);
		doorList[i].solidArea = new Rectangle(solidX*gp.tileSize, solidY*gp.tileSize, gp.tileSize, gp.tileSize);
		//System.out.println(doorList[i].name + " " + doorList[i].row + " " + doorList[i].col);
	}
	
	public void createLever(int i, int row, int col, int code) {
		int solidX = col%15;
		int solidY = row%11;
		if(solidX==0 && col>=15) solidX = 15;
		if(solidY==0 && row>=11) solidY = 11;
		if(code == 0) code = -1;
		leverList[i] = new Lever(row, col, objectPic[1][0], objectPic[1][1]);
		leverList[i].name += ("_" + code);
		((Lever) leverList[i]).setLeverKey(code);
		leverList[i].solidArea = new Rectangle(solidX*gp.tileSize, solidY*gp.tileSize, gp.tileSize, gp.tileSize);
		//System.out.println(leverList[i].name + " " + leverList[i].row + " " + leverList[i].col);
	}
	
	public void createSoul(int i, int row, int col) {
		int solidX = col%15;
		int solidY = row%11;
		if(solidX==0 && col>=15) solidX = 15;
		if(solidY==0 && row>=11) solidY = 11;
		int r = 15;
		soulList[i] = new Soul(row, col, objectPic[2][0], objectPic[2][1]);
		soulList[i].name += ("_" + i);
		soulList[i].solidArea = new Rectangle(solidX*gp.tileSize, solidY*gp.tileSize, gp.tileSize - 2*r, gp.tileSize - 2*r);
		((Soul) soulList[i]).changeSolidPos(r);
		//System.out.println(soulList[i].name + " " + i + " " + soulList[i].row + " " + soulList[i].col);
	}
	
	public void update() {
		for(int i=0; i < objList.length; i++) {
			// Loop through each list
			for(int j=0; j < objList[i].length; j++) {
				// Change Solid Area of an object
				if(objList[i][j] != null) {
					objList[i][j].solidArea.x = (objList[i][j].col - gp.upperLeftWorldCol)*gp.tileSize;
					objList[i][j].solidArea.y = (objList[i][j].row - gp.upperLeftWorldRow)*gp.tileSize;
					if(i==2) ((Soul) objList[i][j]).changeSolidPos(15);
				}
			}
		}
	}
 	
	public void getObjectImage() {
		try {
			BufferedImage spriteImage = ImageIO.read(getClass().getResourceAsStream("/object/object.png"));
			objectPic = spriteM.spriteCutter(spriteImage, 3, 4, 16, 16);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void drawAllObject(Graphics g) {
		for(int i=0; i < objList.length; i++) {
			// Loop through each list
			for(int j=0; j < objList[i].length; j++) {
				if(objList[i][j] != null) {
					objList[i][j].draw(g, gp);
				}
			}
		}
	}

	public void checkAllObject(Entity entity) {
		for(int i=0; i < objList.length; i++) {
			// Loop through each list
			for(int j=0; j < objList[i].length; j++) {
				if(objList[i][j] != null) {
					gp.collisionCheck.checkObjectCollision(entity, objList[i][j]);
				}
			}
		}
	}
	
	public void checkInteraction(Entity entity) {
		
		int panelLeft = gp.upperLeftWorldCol;
		int panelRight = gp.upperLeftWorldCol + gp.maxCol;
		int panelTop = gp.upperLeftWorldRow;
		int panelBottom = gp.upperLeftWorldRow + gp.maxRow;
		
		for(int i=0; i < objList.length; i++) {
			// Loop through each list
			for(int j=0; j < objList[i].length; j++) {
				if(objList[i][j] != null) {
					// obj.col = Position on width  = Check btw left & right
					// obj.row = Position on height = Check btw top & bottom
					// check if object is on the panel
					if (objList[i][j].col >= panelLeft && objList[i][j].col <= panelRight && objList[i][j].row >= panelTop && objList[i][j].row <= panelBottom) {
						
						int x = entity.solidArea.x;
						int y = entity.solidArea.y;
						switch (entity.direction) {
						case "down":
							y += entity.speed; break;
						case "up":
							y -= entity.speed; break;
						case "left":
							x -= entity.speed; break;
						case "right":
							x += entity.speed; break;
						}
						
						Rectangle checkerSolid = new Rectangle(x, y, entity.solidArea.width, entity.solidArea.height);
						
						if (checkerSolid.intersects(objList[i][j].solidArea)) {
							String[] obj = objList[i][j].name.split("_");
							String objType = obj[0];
							
							switch(objType) {
							case "door":
								// door interaction -> need E key
								if (gp.key.interact && !((Door)objList[i][j]).isOpen) {
									gp.gameState = gp.dialogueState;
									if (objList[i][j].name.contains("16")) gp.ui.dialogueState = gp.ui.specialDoor;
									else gp.ui.dialogueState = gp.ui.door;
								}
								break;
							case "lever":
								// lever interaction -> need E key
								if (gp.key.interact) {
									//System.out.println("try to pull " + objList[i][j].name);
									pullLever((Lever) objList[i][j]);
									
									// For the first two lever
									if (objList[i][j].name.contains("-1") && gp.ui.dialogueCounter == 3) gp.gameState = gp.dialogueState;
									if (objList[i][j].name.equals("lever_1") && gp.ui.dialogueCounter == 6) gp.gameState = gp.dialogueState;
									
									// For some lever
									if (objList[i][j].name.contains("9")) {
										gp.gameState = gp.dialogueState;
										gp.ui.dialogueState = gp.ui.lever;
									}
									if (objList[i][j].name.contains("11")) {
										gp.gameState = gp.dialogueState;
										gp.ui.dialogueState = gp.ui.lever;
									}
									if (objList[i][j].name.contains("17")) {
										gp.gameState = gp.dialogueState;
										gp.ui.dialogueState = gp.ui.lever;
									}
									if (objList[i][j].name.contains("18")) {
										gp.gameState = gp.dialogueState;
										gp.ui.dialogueState = gp.ui.lever;
									}
									if (objList[i][j].name.contains("19")) {
										gp.gameState = gp.dialogueState;
										gp.ui.dialogueState = gp.ui.lever;
									}
								}
								break;
							case "soul":
								//System.out.println("SOUl " + objList[i][j].name +  " " + i);
								// For the first soul
								if (objList[i][j].name.contains("0")) gp.gameState = gp.dialogueState;
								
								// soul interaction
								objList[i][j] = null;
								gp.player.soulNum++;
								gp.player.cooldownTime = gp.player.soulNum * gp.player.cdTime;
								gp.player.cdTime -= 2;
								gp.player.tfTime -= 2;
							}
						}
					}
				}
			}
		}
	}
	
	private void pullLever(Lever lever) {
		// If one time only & already pulled
		if (lever.isOn && lever.oneTime) {
			//System.out.println("Already PULLED");
		} 
		else {
			boolean alreadyTurnOn = false;
			for(int i=0; i < doorList.length; i++) {
				// If door exist
				if (doorList[i] != null) {
					// Loop through all lever key
					int[] leverKey = lever.getLeverKey();
					for(int j=0; j < leverKey.length; j++) {
						// If the key is correct
						if ( ((Door) doorList[i]).changeDoor(leverKey[j]) ) {
							// If have not turned on the lever
							if (!alreadyTurnOn) {
								//System.out.println("Turn1");
								lever.turnOn();
								alreadyTurnOn = true;
							}
						}
					}
				}
			}
		}
	}
}
