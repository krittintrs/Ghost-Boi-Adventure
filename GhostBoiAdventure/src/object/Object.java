package object;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import mainGame.GamePanel;

public class Object {
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public boolean holy = false;
	public int row, col;
	public Rectangle solidArea;
	
	public void draw(Graphics g, GamePanel gp) {
		int panelLeft = gp.upperLeftWorldCol;
		int panelRight = gp.upperLeftWorldCol + gp.maxCol;
		int panelTop = gp.upperLeftWorldRow;
		int panelBottom = gp.upperLeftWorldRow + gp.maxRow;
		
		// obj.col = Position on width  = x = Check btw left & right
		// obj.row = Position on height = y = Check btw top & bottom
		if (col >= panelLeft && col <= panelRight && row >= panelTop && row <= panelBottom) {
			int paintX = (col - panelLeft)*gp.tileSize;
			int paintY = (row - panelTop)*gp.tileSize;
			//System.out.println(paintX + " " + paintY);
			g.drawImage(image, paintX, paintY, gp.tileSize, gp.tileSize, null);
			
//			// Check Solid Area
//			g.setColor(Color.red);
//			g.drawRect(solidArea.x, solidArea.y, solidArea.width, solidArea.height);
		}
	}
}
