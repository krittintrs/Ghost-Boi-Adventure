package mainGame;

import java.awt.image.BufferedImage;

import map.Tile;

public class SpriteManager {
	// Sprite Cutter
	// Picture format down up left right
	public BufferedImage[][] spriteCutter(BufferedImage spriteImage, int row, int col, int width, int height) {
		BufferedImage[][] img = new BufferedImage[row][col];
		for (int i=0; i<row; i++) {
	    	for (int j=0; j<col; j++) {
	    		img[i][j] = spriteImage.getSubimage(j*16, i*16, width, height);
	    	}
	    } 
		return img;
	}
	
	public void tileCutter(Tile[][] tiles, BufferedImage spriteImage, int row, int col, int width, int height, int starterRow) {
		for (int i=0; i<row; i++) { 
			for (int j=0; j<col; j++) {
				tiles[i+starterRow][j] = new Tile();
				tiles[i+starterRow][j].image = spriteImage.getSubimage(j*16, i*16, width, height);
			}
		}
	}
}
