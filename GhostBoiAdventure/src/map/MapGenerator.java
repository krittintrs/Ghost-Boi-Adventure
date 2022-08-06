package map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import customComponent.GraphicTools;
import mainGame.GamePanel;
import mainGame.SpriteManager;
import object.ObjectGenerator;

public class MapGenerator {
	
	GamePanel gp;
	public Tile[][] tiles;
	SpriteManager spriteM = new SpriteManager();
	public static String[][] mapTileKey;
	public static int[] objCounter = {0, 0, 1};
	
	public MapGenerator(GamePanel gp) {
		this.gp = gp;
		tiles = new Tile[10][10];
		mapTileKey = new String[gp.maxWorldRow][gp.maxWorldCol];
		getTilesImage();
		loadMap(); // Save to mapTileKey
		
		// Set Tile Detail
		// Floors
		int type = 1;
		tiles[type][0].name = "floor";
		tiles[type][1].name = "floor w/ scracth";
		type++;
	
		// Border Floors
		tiles[type][0].name = "borderfloor";
		tiles[type][1].name = "borderfloor w/ scracth";
		type++;
				
		// Walls
		tiles[type][0].name = "wall";
		tiles[type][1].name = "wall highlighted";
		for(int i=0; i<tiles[type].length; i++) {
			if(tiles[type][i] != null) {
				tiles[type][i].collision = true;
			}
		}
		type++;
		
		// Holy Walls
		tiles[type][0].name = "holy wall";
		tiles[type][1].name = "holy wall highlighted";
		for(int i=0; i<tiles[type].length; i++) {
			if(tiles[type][i] != null) {
				tiles[type][i].collision = true;
				tiles[type][i].holy = true;
			}
		}
		type++;
		
		// Holes
		tiles[type][0].name = "hole U-L";
		tiles[type][1].name = "hole U-M";
		tiles[type][2].name = "hole U-R";
		tiles[type][3].name = "hole M-L";
		tiles[type][4].name = "hole M-M";
		tiles[type][5].name = "hole M-R";
		tiles[type][6].name = "hole B-L";
		tiles[type][7].name = "hole B-M";		
		tiles[type][8].name = "hole B-R";
		for(int i=0; i<tiles[type].length; i++) {
			if(tiles[type][i] != null) {
				tiles[type][i].collision = true;
				tiles[type][i].hole = true;
			}
		}
		type++;
	
		// Holes (Angle)
		tiles[type][0].name = "hole angle UL";
		tiles[type][1].name = "hole angle UR";
		tiles[type][2].name = "hole angle BL";
		tiles[type][3].name = "hole angle BR";
		for(int i=0; i<tiles[type].length; i++) {
			if(tiles[type][i] != null) {
				tiles[type][i].collision = true;
				tiles[type][i].hole = true;
			}
		}
		type++;		
	}
	
	public void getTilesImage() {
		try {
			BufferedImage sprite = ImageIO.read(getClass().getResourceAsStream("/tile/tiles.png"));
			spriteM.tileCutter(tiles, sprite, 4, 2, 16, 16, 1);
			sprite = ImageIO.read(getClass().getResourceAsStream("/tile/hole.png"));
			spriteM.tileCutter(tiles, sprite, 2, 9, 16, 16, 5);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap() {
		try {
			InputStream input = getClass().getResourceAsStream("/maps/Map.csv");
			BufferedReader br = new BufferedReader(new InputStreamReader(input));
	
			for(int i=0; i < gp.maxWorldRow; i++) {
				// read line by line
				String line = br.readLine();
				
				for(int j=0; j < gp.maxWorldCol; j++) {
					// split each number by , (csv)
					String[] mapKeys = line.split(",");
			
					if (mapKeys[j].equals("10")) {
						int rand = (int)(Math.random()*10);
						if(rand > 3) mapKeys[j] = "11";
					}
					
					// add to map tile key
					mapTileKey[i][j] = mapKeys[j];
					
					// send object to ObjectGenerator
					if (mapKeys[j].contains("_") || mapKeys[j].length() == 1) {
						int type, code = 999;
						// door & lever
						if (mapKeys[j].contains("_")) {
							String[] obj = mapKeys[j].split("_");
							type = Integer.parseInt(obj[0]);
							code = Integer.parseInt(obj[1]);
							// Door type
							if (type == 0) {
								if (j>0 && (mapKeys[j-1].equals("10") || mapKeys[j-1].equals("11"))) {
									ObjectGenerator.doorType[objCounter[type]] = ObjectGenerator.vertiDoor; 
								} else {
									ObjectGenerator.doorType[objCounter[type]] = ObjectGenerator.horiDoor; 
								}
							}
							//System.out.println(mapKeys[j] + " " + obj[0] + " " + obj[1] + " " + objCounter[type]);
						}
						// soul
						else {
							type = Integer.parseInt(mapKeys[j]);
						}
						int row = i;
						int col = j;
						// Add Position & Type & Code of the object
						ObjectGenerator.objCreatorList[type][objCounter[type]][0] = row;
						ObjectGenerator.objCreatorList[type][objCounter[type]][1] = col;
						ObjectGenerator.objCreatorList[type][objCounter[type]][2] = code;
						
						// count number of each object
						objCounter[type]++;
						
						// Change the tile back to floor
						mapTileKey[i][j] = "10";
					}
				}
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int[] getTileKey(int x, int y) {
		String tileKeyString = mapTileKey[x][y];
		int tileKey[] = new int[2];
		
		if(tileKeyString.length() > 1 ) {
			// Normal tiles
			tileKey = Tile.splitKey(tileKeyString);			
		} else {
			// Object -> start with 0
			tileKey[0] = 0;
			tileKey[1] = Integer.parseInt(tileKeyString);
		}
		return tileKey;
	}
	
	public void draw(Graphics g) {
		int x=0, y=0;
		
		for(int i=0; i < gp.maxRow; i++) {
			for(int j=0; j < gp.maxCol; j++) {
				
				int tileKey[] = getTileKey(i + gp.upperLeftWorldRow, j + gp.upperLeftWorldCol);
				//System.out.println("Tile: " + i + " " + j + " " + tileKeyString + " len " + tileKeyString.length());
				
				if(tileKey[0] != 0) {
					g.drawImage(tiles[tileKey[0]][tileKey[1]].image, x, y, gp.tileSize, gp.tileSize, null);
				} else {
					// For Object -> draw normal floor
					g.drawImage(tiles[1][0].image, x, y, gp.tileSize, gp.tileSize, null);
				}
				
				x += gp.tileSize;
			}
			x = 0;
			y += gp.tileSize;
		}
	}
}
