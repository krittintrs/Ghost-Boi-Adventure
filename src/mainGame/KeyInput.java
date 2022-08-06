package mainGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener{

	GamePanel gp;
	public boolean up, down, left, right;
	public boolean ghostOn;
	public boolean interact;
	
	public KeyInput(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		// TODO Auto-generated method stub		
		if(e.getKeyCode() == KeyEvent.VK_W) {
			up = true;
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			left = true;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			down = true;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			right = true;
		}
		
		//System.out.println(up + " " + down + " " + left + " " +right);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_W) {
			up = false;
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			left = false;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			down = false;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			right = false;
		} else if (e.getKeyCode() == KeyEvent.VK_Q) {
			if(gp.player.soulNum > 0 && !gp.player.cooldown) {
				ghostOn = !ghostOn;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_E) {
			if (gp.ui.dialogueCounter > 2) {
				// Interaction
				interact = true;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_P) {
			// PAUSE
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			// GO BACK FROM DEAD STATE
			if (gp.gameState == gp.deadState) {
				gp.gameState = gp.playState;
				gp.player.revive();
			}
			if (gp.gameState == gp.endState) {
				System.exit(0);
			}
			if (gp.gameState == gp.dialogueState) {
				if (gp.ui.dialogueCounter < 2) gp.ui.dialogueCounter++;
				else if (gp.ui.dialogueCounter == 4) gp.ui.dialogueCounter++;
				else if (gp.ui.dialogueCounter > 7) gp.gameState = gp.playState;
				else {
					gp.gameState = gp.playState;
					gp.ui.dialogueCounter++;
				}
			}
		} 
	}

}
