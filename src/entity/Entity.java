package entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import mainGame.GamePanel;
import mainGame.SpriteManager;

public abstract class Entity {
	
	GamePanel gp;
	public int x;
	public int y;
	public int previousX, previousY, previousUpperLeftWorldCol,previousUpperLeftWorldRow;
	public int speed;
	BufferedImage spriteImage;
	public BufferedImage[][] picture; // Picture format down up left right
	public String direction;
	SpriteManager spriteM = new SpriteManager();
	public Rectangle solidArea;
	public boolean collisionOn = false;
	public boolean holyOn = false; 
	
	// Draw an entity
	public abstract void update();
	public abstract void draw(Graphics g);
	
}
