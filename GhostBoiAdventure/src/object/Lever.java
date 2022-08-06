package object;

import java.awt.image.BufferedImage;

public class Lever extends Object{

	public BufferedImage off, on;
	boolean oneTime = true;
	public boolean isOn = false;
	private int[] leverKey = new int[5];
	private int counter = 0;
	
	public Lever(int row, int col, BufferedImage off, BufferedImage on) {
		this.row = row;
		this.col = col;
		this.off = off;
		this.on = on;
		
		name = "lever";
		collision = true;
		holy = false;
		image = off;
	}
	
	public void turnOn() {
		// one time only
		if (oneTime) {
			if (isOn == false) {
				image = on;
				isOn = true;
			}
		}
		// Multiple time
		else {
			if (isOn == false) {
				//System.out.println(name + " ON");
				image = on;
				isOn = true;
			} else {
				//System.out.println(name + " OFF");
				image = off;
				isOn = false;
			}
			//System.out.println("finish");
		}
		
	}
 	
	public int[] getLeverKey() {
		return leverKey;
	}

	public void setLeverKey(int keyCode) {
		this.leverKey[counter] = keyCode;
		counter++;
	}
	
}
