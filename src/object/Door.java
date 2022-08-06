package object;

import java.awt.image.BufferedImage;

public class Door extends Object {
	
	public BufferedImage close, open;
	public boolean isOpen = false;
	private int doorKey;
	
	public Door(int row, int col, BufferedImage close, BufferedImage open) {
		this.row = row;
		this.col = col;
		this.close = close;
		this.open = open;
		
		name = "door";
		collision = true;
		holy = true;
		image = close;
	}
	
	public boolean changeDoor(int leverKey) {
		//System.out.println("Openning the door: DK=" + doorKey + " LK=" + leverKey);
		if(leverKey == doorKey) {
			// Open the door
			if(isOpen == false) {
				//System.out.println("OPEN!!!!!!!!!!!!!!!!!!!!");
				image = open;
				collision = false;
				holy = false;
				isOpen = true;
			// Close the door
			} else {
				//System.out.println("CLOSE!!!!!!!!!!!!!!!!!!!!");
				image = close;
				collision = true;
				holy = true;
				isOpen = false;
			}
			return true;
		} 
		return false;
	}

	public int getDoorKey() {
		return doorKey;
	}

	public void setDoorKey(int keyCode) {
		this.doorKey = keyCode;
	}
}
