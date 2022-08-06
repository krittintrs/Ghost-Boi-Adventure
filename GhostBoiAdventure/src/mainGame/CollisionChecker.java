package mainGame;

import java.awt.Rectangle;

import entity.Entity;
import map.MapGenerator;

import object.Object;

public class CollisionChecker {

	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
		
	}
	
	public void checkCollision(Entity entity) {
		
		// Solid Area Coordinate
		int solidLeft = entity.solidArea.x;
		int solidRight = entity.solidArea.x + entity.solidArea.width;
		int solidTop = entity.solidArea.y;
		int solidBottom = entity.solidArea.y + entity.solidArea.height;
		
		// Entity Nearby Row & Col
		int nearbyLeftCol = solidLeft/gp.tileSize + gp.upperLeftWorldCol;
		int nearbyRightCol = solidRight/gp.tileSize + gp.upperLeftWorldCol;
		int nearbyTopRow = solidTop/gp.tileSize + gp.upperLeftWorldRow;
		int nearbyBottomRow = solidBottom/gp.tileSize + gp.upperLeftWorldRow;
		
		// Nearby Tile Key
		int[] nearbyTileKey1, nearbyTileKey2;
		
//		System.out.println("Player: " + entity.x + " " + entity.y);
//		System.out.println("Solid:  " + solidLeft + " " + solidRight + " " + solidTop + " " + solidBottom);
//		System.out.println("Nearby: " + nearbyLeftCol + " " + nearbyRightCol + " " + nearbyTopRow + " " + nearbyBottomRow);
		
		switch(entity.direction) {
		case "down":
			nearbyBottomRow = (solidBottom + entity.speed)/gp.tileSize + gp.upperLeftWorldRow;
			nearbyTileKey1 = MapGenerator.getTileKey(nearbyBottomRow, nearbyLeftCol);
			nearbyTileKey2 = MapGenerator.getTileKey(nearbyBottomRow, nearbyRightCol);
			// For Object
			if (nearbyTileKey1[0] == 0 || nearbyTileKey2[0] == 0) {
				entity.collisionOn = false;
			} else {
				// For Player
				if (gp.map.tiles[nearbyTileKey1[0]][nearbyTileKey1[1]].collision || gp.map.tiles[nearbyTileKey2[0]][nearbyTileKey2[1]].collision) {
					entity.collisionOn = true;
				}
				// For Ghost
				if (gp.map.tiles[nearbyTileKey1[0]][nearbyTileKey1[1]].holy || gp.map.tiles[nearbyTileKey2[0]][nearbyTileKey2[1]].holy) {
					entity.holyOn = true;
				}
			}
			break;
		case "up":
			nearbyTopRow = (solidTop - entity.speed)/gp.tileSize + gp.upperLeftWorldRow;
			nearbyTileKey1 = MapGenerator.getTileKey(nearbyTopRow, nearbyLeftCol);
			nearbyTileKey2 = MapGenerator.getTileKey(nearbyTopRow, nearbyRightCol);
			// For Object
			if (nearbyTileKey1[0] == 0 || nearbyTileKey2[0] == 0) {
				entity.collisionOn = false;
			} else {
				// For Player
				if (gp.map.tiles[nearbyTileKey1[0]][nearbyTileKey1[1]].collision || gp.map.tiles[nearbyTileKey2[0]][nearbyTileKey2[1]].collision) {
					entity.collisionOn = true;
				}
				// For Ghost
				if (gp.map.tiles[nearbyTileKey1[0]][nearbyTileKey1[1]].holy || gp.map.tiles[nearbyTileKey2[0]][nearbyTileKey2[1]].holy) {
					entity.holyOn = true;
				}
			}	
			break;
		case "left":
			nearbyLeftCol = (solidLeft - entity.speed)/gp.tileSize + gp.upperLeftWorldCol;
			nearbyTileKey1 = MapGenerator.getTileKey(nearbyTopRow, nearbyLeftCol);
			nearbyTileKey2 = MapGenerator.getTileKey(nearbyBottomRow, nearbyLeftCol);
			// For Object
			if (nearbyTileKey1[0] == 0 || nearbyTileKey2[0] == 0) {
				entity.collisionOn = false;
			} else {
				// For Player
				if (gp.map.tiles[nearbyTileKey1[0]][nearbyTileKey1[1]].collision || gp.map.tiles[nearbyTileKey2[0]][nearbyTileKey2[1]].collision) {
					entity.collisionOn = true;
				}
				// For Ghost
				if (gp.map.tiles[nearbyTileKey1[0]][nearbyTileKey1[1]].holy || gp.map.tiles[nearbyTileKey2[0]][nearbyTileKey2[1]].holy) {
					entity.holyOn = true;
				}
			}
			break;
		case "right":
			nearbyRightCol = (solidRight + entity.speed)/gp.tileSize + gp.upperLeftWorldCol;
			nearbyTileKey1 = MapGenerator.getTileKey(nearbyTopRow, nearbyRightCol);
			nearbyTileKey2 = MapGenerator.getTileKey(nearbyBottomRow, nearbyRightCol);
			// For Object
			if (nearbyTileKey1[0] == 0 || nearbyTileKey2[0] == 0) {
				entity.collisionOn = false;
			} else {
				// For Player
				if (gp.map.tiles[nearbyTileKey1[0]][nearbyTileKey1[1]].collision || gp.map.tiles[nearbyTileKey2[0]][nearbyTileKey2[1]].collision) {
					entity.collisionOn = true;
				}
				// For Ghost
				if (gp.map.tiles[nearbyTileKey1[0]][nearbyTileKey1[1]].holy || gp.map.tiles[nearbyTileKey2[0]][nearbyTileKey2[1]].holy) {
					entity.holyOn = true;
				}
			}
			break;
		}
	}
	
	public void checkObjectCollision(Entity entity, Object obj) {
		
		int panelLeft = gp.upperLeftWorldCol;
		int panelRight = gp.upperLeftWorldCol + gp.maxCol;
		int panelTop = gp.upperLeftWorldRow;
		int panelBottom = gp.upperLeftWorldRow + gp.maxRow;
		
		// obj.col = Position on width  = Check btw left & right
		// obj.row = Position on height = Check btw top & bottom
		// check if object is on the panel
		if (obj.col >= panelLeft && obj.col <= panelRight && obj.row >= panelTop && obj.row <= panelBottom) {
			
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
			
//			System.out.println("CHECKER " + obj.name);
//			System.out.println("Entity " + entity.solidArea);
//			System.out.println("Checkr " + checkerSolid);
//			System.out.println("Object " + obj.solidArea);
//			System.out.println();
			
			
			if (checkerSolid.intersects(obj.solidArea)) {
				if (obj.collision == true) {
					entity.collisionOn = true;
				}
				if (obj.holy == true) {
					entity.holyOn = true;
				}
			}
		} 	
	}
	
	public int[] moveTransform(Entity entity) {
		
		// Instantiate
		boolean[][] solidTile = {{false, false}, {false, false}};
		
		// Solid Area Coordinate
		int solidLeft = entity.solidArea.x;
		int solidRight = entity.solidArea.x + entity.solidArea.width;
		int solidTop = entity.solidArea.y;
		int solidBottom = entity.solidArea.y + entity.solidArea.height;
		
		// Entity Center
		int entityCenterX = entity.x + gp.tileSize/2;
		int entityCenterY = entity.solidArea.y + entity.solidArea.height/2;
		
		// Entity Nearby Row & Col
		int nearbyLeftCol = solidLeft/gp.tileSize + gp.upperLeftWorldCol;
		int nearbyRightCol = solidRight/gp.tileSize + gp.upperLeftWorldCol;
		int nearbyTopRow = solidTop/gp.tileSize + gp.upperLeftWorldRow;
		int nearbyBottomRow = solidBottom/gp.tileSize + gp.upperLeftWorldRow;
		
		boolean horizontal = (nearbyTopRow == nearbyBottomRow);
		boolean vertical = (nearbyLeftCol == nearbyRightCol);
		
		// Nearby Tile Key
		int[] nearbyTileTLKey =  MapGenerator.getTileKey(nearbyTopRow,nearbyLeftCol);
		int[] nearbyTileTRKey =  MapGenerator.getTileKey(nearbyTopRow,nearbyRightCol);
		int[] nearbyTileBLKey =  MapGenerator.getTileKey(nearbyBottomRow,nearbyLeftCol);
		int[] nearbyTileBRKey =  MapGenerator.getTileKey(nearbyBottomRow,nearbyRightCol);
		
//		System.out.println();
//		System.out.println("Player: " + entity.x + " " + entity.y);
//		System.out.println("Solid:  " + solidLeft + " " + solidRight + " " + solidTop + " " + solidBottom);
//		System.out.println("Left: " + nearbyLeftCol + " Right: " + nearbyRightCol + " Top: " + nearbyTopRow + " Bottom: " + nearbyBottomRow);
		
		// CHECK HOLE 
		if (nearbyTileTLKey[0] == 5 || nearbyTileTRKey[0] == 5 || nearbyTileBLKey[0] == 5 || nearbyTileBRKey[0] == 5 || 
				nearbyTileTLKey[0] == 6 || nearbyTileTRKey[0] == 6 || nearbyTileBLKey[0] == 6 || nearbyTileBRKey[0] == 6) {
			//System.out.println("Near hole");
			// Draw Rectangle of each hole
			Rectangle[] holeRect = new Rectangle[4];
			if(nearbyTileTLKey[0] == 5 || nearbyTileTLKey[0] == 6) {
				int y = (nearbyTopRow%11)*gp.tileSize;
				int x = (nearbyLeftCol%15)*gp.tileSize;
				holeRect[0] = new Rectangle(x, y, gp.tileSize, gp.tileSize);
			}
			if(nearbyTileTRKey[0] == 5 || nearbyTileTRKey[0] == 6) {
				int y = (nearbyTopRow%11)*gp.tileSize;
				int x = (nearbyRightCol%15)*gp.tileSize;
				holeRect[1] = new Rectangle(x, y, gp.tileSize, gp.tileSize);
			}
			if(nearbyTileBLKey[0] == 5 || nearbyTileTRKey[0] == 6) {
				int y = (nearbyBottomRow%11)*gp.tileSize;
				int x = (nearbyLeftCol%15)*gp.tileSize;
				holeRect[2] = new Rectangle(x, y, gp.tileSize, gp.tileSize);
			}
			if(nearbyTileBRKey[0] == 5 || nearbyTileTRKey[0] == 6) {
				int y = (nearbyBottomRow%11)*gp.tileSize;
				int x = (nearbyRightCol%15)*gp.tileSize;
				holeRect[3] = new Rectangle(x, y, gp.tileSize, gp.tileSize);
			}
			
			// Loop Check If inside the hole
			for(int i=0; i<4; i++) {
				if(holeRect[i] != null) {
//					System.out.println("RECT OF HOLE " + holeRect[i]);
//					System.out.println("MY CENTER " + entityCenterX + " " + entityCenterY);
					if(holeRect[i].inside(entityCenterX, entityCenterY)) {
						int[] dead = {999, 0, 0, 0};
						return dead;
					}
				}
			}
			//System.out.println("not in the hole");
		}
		
		// Check 4 points of Solid Area
		if (nearbyTileTLKey[0] != 0 && gp.map.tiles[nearbyTileTLKey[0]][nearbyTileTLKey[1]].collision) {
			solidTile[0][0] = true;
		} 
		if (nearbyTileTRKey[0] != 0 && gp.map.tiles[nearbyTileTRKey[0]][nearbyTileTRKey[1]].collision) {
			solidTile[0][1] = true;
		} 
		if (nearbyTileBLKey[0] != 0 && gp.map.tiles[nearbyTileBLKey[0]][nearbyTileBLKey[1]].collision) {
			solidTile[1][0] = true;
		}
		if (nearbyTileBRKey[0] != 0 && gp.map.tiles[nearbyTileBRKey[0]][nearbyTileBRKey[1]].collision) {
			solidTile[1][1] = true;
		}
		
//		System.out.println(solidTile[0][0] + " " + solidTile[0][1]);
//		System.out.println(solidTile[1][0] + " " + solidTile[1][1]);
		
		// Check how many solid
		int solidNum = 0;
		for (int i=0; i<2; i++) {
			for (boolean isSolid: solidTile[i]) {
				if(isSolid) solidNum++;
			}
		}
		//System.out.println("solid num = " + solidNum);
		
		// Where to go
		boolean up = false; 
		boolean down = false;
		boolean left = false;
		boolean right = false;
		
		// Solid All Vertices
		if (solidNum == 4) {
			//System.out.println("all vertices");
			// 4 Tiles -> impossible
			
			// 1 Tiles 
			if (horizontal & vertical) {
				//System.out.println("1 tile");
				int currentRow = nearbyTopRow, currentCol = nearbyLeftCol;
				// Check which direction to go
				// 00 01 02
				// 10 -- 12
				// 20 21 22
				int[][][] nearbyMapKey = new int[3][3][2];
				for (int i=0; i<3; i++) {
					for (int j=0; j<3; j++) {
						int[] thisKey = MapGenerator.getTileKey(currentRow + (i-1), currentCol + (j-1));
						nearbyMapKey[i][j] = thisKey;
						//System.out.print(thisKey + " ");
					}
					//System.out.println();
				}
				// Vertical is not solid
				if (!gp.map.tiles[nearbyMapKey[0][1][0]][nearbyMapKey[0][1][1]].collision && !gp.map.tiles[nearbyMapKey[2][1][0]][nearbyMapKey[2][1][1]].collision) {
					// Go Up
					if (entityCenterY < currentRow*gp.tileSize + gp.tileSize/2) {
						//System.out.println("go up");
						up = true;
					} 
					// Go Down
					else {
						//System.out.println("go down");
						down = true;
					}
				}
				// Horizontal is not solid
				else if (!gp.map.tiles[nearbyMapKey[1][0][0]][nearbyMapKey[1][0][1]].collision && !gp.map.tiles[nearbyMapKey[1][2][0]][nearbyMapKey[1][2][1]].collision) {	
					// Go Left
					if (entityCenterX < currentCol*gp.tileSize + gp.tileSize/2) {
						//System.out.println("go left");
						left = true;
					}
					// Go Right
					else {
						//System.out.println("go right");
						right = true;
					}
				} 
				// Only corner is not solid
//				else {
//					int tileX = currentCol*gp.tileSize;
//					int tileY = currentRow*gp.tileSize;
//					int step = gp.tileSize/2;
//					Rectangle topLeft = new Rectangle(tileX, tileY, step, step);
//					Rectangle topRight = new Rectangle(tileX + step, tileY, step, step);
//					Rectangle bottomLeft  = new Rectangle(tileX, tileY + step, step, step);
//					Rectangle bottomRight  = new Rectangle(tileX + step, tileY + step, step, step);
//					
//					System.out.println("Solid:  " + solidLeft + " " + solidRight + " " + solidTop + " " + solidBottom);
//					System.out.println("Current" + entityCenterX + " " + entityCenterY);
//					System.out.println("Tile: " + tileX + " " + tileY);
//					
//					if (topLeft.contains(entityCenterX, entityCenterY)) {
//						up = true; left = true;
//					} else if (topRight.contains(entityCenterX, entityCenterY)) {
//						up = true; right = true;
//					} else if (bottomLeft.contains(entityCenterX, entityCenterY)) {
//						down = true; left = true;
//					} else if (bottomRight.contains(entityCenterX, entityCenterY)) {
//						down = true; right = true;
//					} 
//				}
				
			} else {
				// 2 Tiles horizontal
				if (horizontal) {
					//System.out.println("hori");	
					// Go Up
					if (entityCenterY < nearbyTopRow*gp.tileSize + gp.tileSize/2) {
						//System.out.println("go up");
						up = true;
					} 
					// Go Down
					else {
						//System.out.println("go down");
						down = true;
					}
				}
				// 2 Tiles vertical
				else if (vertical) {
					//System.out.println("verti");
					// Go Left
					if (entityCenterX < nearbyLeftCol*gp.tileSize + gp.tileSize/2) {
						//System.out.println("go left");
						left = true;
					}
					// Go Right
					else {
						//System.out.println("go right");
						right = true;
					}
				}
			}
		}
		// Solid 2-3 Vertices
		else if (solidNum > 1) {
			// Collide from above -> go down
			if(solidTile[0][0] && solidTile[0][1]) {
				down = true;
			}
			// Collide from below -> go up
			if(solidTile[1][0] && solidTile[1][1]) {
				up = true;
			} 
			// Collide from left -> go right
			if(solidTile[0][0] && solidTile[1][0]) {
				right = true;
			}
			// Collide from right -> go left
			if(solidTile[0][1] && solidTile[1][1]) {
				left = true;	
			}
		}
		// Solid Only 1 Vertices
		else if (solidNum == 1) {
			int leftDif, rightDif, topDif, bottomDif;	
			// Bottom Right of Tile
			if(solidTile[0][0]) {
				rightDif = (nearbyLeftCol*gp.tileSize + gp.tileSize) - solidLeft;
				bottomDif = (nearbyTopRow*gp.tileSize + gp.tileSize) - solidTop;
				//System.out.println("Dif r " + rightDif + " b " + bottomDif);
				
				if (rightDif < bottomDif) {
					right = true;
				} else {
					down = true;
				}
			// Bottom Left of Tile
			} else if (solidTile[0][1]) {
				leftDif = solidRight - nearbyRightCol*gp.tileSize;
				bottomDif = (nearbyTopRow*gp.tileSize + gp.tileSize) - solidTop;
				//System.out.println("Dif l " + leftDif + " b " + bottomDif);
				
				if (leftDif < bottomDif) {
					left = true;
				} else {
					down = true;
				}
			// Top Right of Tile
			} else if (solidTile[1][0]) {
				rightDif = (nearbyLeftCol*gp.tileSize + gp.tileSize) - solidLeft;
				topDif = solidBottom - nearbyBottomRow*gp.tileSize;
				//System.out.println("Dif r " + rightDif + " t " + topDif);

				if (rightDif < topDif) {
					right = true;
				} else {
					up = true;
				}
			// Top Left of Tile
			} else if (solidTile[1][1]) {
				leftDif = solidRight - nearbyRightCol*gp.tileSize;
				topDif = solidBottom - nearbyBottomRow*gp.tileSize;
				//System.out.println("Dif l " + leftDif + " t " + topDif);

				if (leftDif < topDif) {
					left = true;
				} else {
					up = true;
				}
			}
		}
		
		boolean direction[] = {up, down, left, right}; 

		// Check whether player need to move or not
		boolean needMove = false;
		for (boolean move: direction) {
			if(move) {
				needMove = true;
			}
		}
				
		// Move player if needed
		int moveUp = 0, moveDown = 0, moveLeft = 0, moveRight = 0;
		if (needMove) {
			int addUp = 7;
			if (up && left) {
				moveUp = solidBottom - (nearbyBottomRow - gp.upperLeftWorldRow)*gp.tileSize + addUp;
				moveLeft = solidRight - (nearbyRightCol - gp.upperLeftWorldCol)*gp.tileSize + addUp;
				//System.out.println("Move up " +moveUp+ " left " +moveLeft);
			} else if (up & right) {
				moveUp = solidBottom - (nearbyBottomRow - gp.upperLeftWorldRow)*gp.tileSize + addUp;
				moveRight = ((nearbyLeftCol - gp.upperLeftWorldCol)*gp.tileSize + gp.tileSize) - solidLeft + addUp;
				//System.out.println("Move up " +moveUp+ " right " +moveRight);
			} else if (down & left) {
				moveDown = ((nearbyTopRow - gp.upperLeftWorldRow)*gp.tileSize + gp.tileSize) - solidTop + addUp;
				moveLeft = solidRight - (nearbyRightCol - gp.upperLeftWorldCol)*gp.tileSize + addUp;
				//System.out.println("Move down " +moveDown+ " left " +moveLeft);
			} else if (down & right) {
				moveDown = ((nearbyTopRow - gp.upperLeftWorldRow)*gp.tileSize + gp.tileSize) - solidTop + addUp;
				moveRight = ((nearbyLeftCol - gp.upperLeftWorldCol)*gp.tileSize + gp.tileSize) - solidLeft + addUp;
				//System.out.println("Move down " +moveDown+ " right " +moveRight);
			} else if (up) {
				moveUp = solidBottom - (nearbyBottomRow - gp.upperLeftWorldRow)*gp.tileSize + addUp;
				//System.out.println("Move up " +moveUp);
			} else if (down) {
				moveDown = ((nearbyTopRow - gp.upperLeftWorldRow)*gp.tileSize + gp.tileSize) - solidTop + addUp;
				//System.out.println("Move down " +moveDown);
			} else if (left) {
				moveLeft = solidRight - (nearbyRightCol - gp.upperLeftWorldCol)*gp.tileSize + addUp;
				//System.out.println("Move left " +moveLeft);
			} else if (right) {
				moveRight = ((nearbyLeftCol - gp.upperLeftWorldCol)*gp.tileSize + gp.tileSize) - solidLeft + addUp;
				//System.out.println("Move right " +moveRight);
			}
		}
		int[] moveDistance = {moveUp, moveDown, moveLeft, moveRight};
		//System.out.println(moveUp + " " + moveDown + " " + moveLeft + " " + moveRight);
		return moveDistance;
	}
}
