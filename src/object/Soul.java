package object;

import java.awt.image.BufferedImage;

public class Soul extends Object {
	public BufferedImage dark, light;

	public Soul(int row, int col, BufferedImage dark, BufferedImage light) {
		this.row = row;
		this.col = col;
		this.dark = dark;
		this.light = light;
		
		name = "soul";
		image = dark;
	}
	
	public void changeSolidPos(int r) {
		solidArea.x += r;
		solidArea.y += r;
	}
}
