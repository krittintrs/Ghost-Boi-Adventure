package map;

import java.awt.image.BufferedImage;

public class Tile {
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public boolean holy = false;
	public boolean hole = false;
	
	public static int[] splitKey(String tileKey) {
		String[] keyList = tileKey.split("");
		int[] keyListInt = {Integer.parseInt(keyList[0]), Integer.parseInt(keyList[1])};
		return keyListInt;
	}
}
