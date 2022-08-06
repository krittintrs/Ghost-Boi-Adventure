package mainGame;

import java.awt.Color;
import java.awt.Graphics;

import customComponent.GraphicTools;

public class UI {
	
	GamePanel gp;
	String spaceMsg;
	String[] dialogue = new String[10];
	int windowW, windowH, windowX, windowY;
	int arc = 40;
	int border = 5;
	public int dialogueCounter = 0;
	
	public int door = 0;
	public int lever = 1;
	public int specialDoor = 2;
	public int dialogueState = -1;

	public UI(GamePanel gp) {
		this.gp = gp;
		windowW = gp.tileSize*15;
		windowH = gp.tileSize*3;
		windowX = gp.panelX/2 - windowW/2;
		windowY = gp.panelY - windowH - gp.tileSize*3/2;
		new GraphicTools();
		
		setDialogue();
	}
	
	public void setDialogue() {
		dialogue[0] = "Use   WASD   to   walk   around";
		dialogue[1] = "Use   E   to   interact   with   an   object";
		dialogue[2] = "Try   to   pull   that   lever!";
		dialogue[3] = "You   can   pick   the   soul   up\nto   gain   superpower";
		dialogue[4] = "Now   you   can   use   Q\nto   transform   into   a   ghost";
		dialogue[5] = "Some   walls   and   holes\nmight   let   you   pass   through";
		dialogue[6] = "Enjoy   the   Adventure!";
	}
	
	public void draw(Graphics g) {
		
		int gap = 4;
		int w = gp.player.soulNum*gp.tileSize;
		int outerW = w + gap*(gp.player.soulNum+1);
		int innerW = w + gap*(gp.player.soulNum-1);
		int totalcdTime = gp.player.soulNum*gp.player.cdTime;
		int totaltfTime = gp.player.soulNum*gp.player.tfTime;
		
		// Transform bar
		if(gp.player.tfTime > 0 && gp.key.ghostOn) {
			g.setColor(Color.black);
			g.fillRect(gp.tileSize/2 - gap, gp.panelY - gp.tileSize/4*3, outerW, gp.tileSize/2);
			
			g.setColor(GraphicTools.darkBlue);
			g.fillRect(gp.tileSize/2, gp.panelY - gp.tileSize/4*3 + gap, innerW*gp.player.transformTime/totaltfTime, gp.tileSize/2 - gap*2);
			
			g.setColor(Color.black);
			for(int i=1; i<gp.player.soulNum; i++) {
				g.fillRect(gp.tileSize/2 - gap + (gp.tileSize + gap)*(i) - gap/2, gp.panelY - gp.tileSize/4*3, gap, gp.tileSize/2);
			}
		}
		
		// Cooldown bar
		if(gp.player.cooldownTime > 0) {
			g.setColor(Color.black);
			g.fillRect(gp.tileSize/2 - gap, gp.panelY - gp.tileSize/4*3, outerW, gp.tileSize/2);
			
			g.setColor(GraphicTools.lightBlue);
			g.fillRect(gp.tileSize/2, gp.panelY - gp.tileSize/4*3 + gap, innerW*(totalcdTime - gp.player.cooldownTime)/totalcdTime, gp.tileSize/2 - gap*2);
			
			g.setColor(Color.black);
			for(int i=1; i<gp.player.soulNum; i++) {
				g.fillRect(gp.tileSize/2 - gap + (gp.tileSize + gap)*(i) - gap/2, gp.panelY - gp.tileSize/4*3, gap, gp.tileSize/2);
			}
		}
		
		// Dead Scene
		if (gp.gameState == gp.deadState) {
			// Background
			drawBackground(g);
			
			// Dead Text
			g.setColor(Color.white);
			g.setFont(GraphicTools.arcadeFontSuperBig);
			String deadMsg = "   You   Died!";
			GraphicTools.drawCenterTextX(deadMsg, g, gp, gp.panelY/4);
			
			// Continue Text
			spaceMsg = "Press   Spacebar   to   Try   Again";
			drawPressedSpacebar(g, spaceMsg, gp.panelY/4*3);
		}
		// Pause Scene
		else if (gp.gameState == gp.pauseState) {
			
		}
		// End Scene
		else if (gp.gameState == gp.endState) {
			// Background
			drawBackground(g);
			
			// Congratulation Text
			g.setColor(Color.white);
			g.setFont(GraphicTools.arcadeFontSuperBig);
			String congratMsg = "   Congratulations!";
			GraphicTools.drawCenterTextX(congratMsg, g, gp, gp.panelY/4);
			
			// Dead Num Text
			g.setFont(GraphicTools.arcadeFontMedium);
			String deadNumMsg = "Number   Of   Death   " + gp.deadCount;
			GraphicTools.drawCenterTextX(deadNumMsg, g, gp, gp.panelY/4 + 100);
			
			// Exit Text
			spaceMsg = "Press   Spacebar   to   Exit   The   Game";
			drawPressedSpacebar(g, spaceMsg, gp.panelY/4*3);
		}
		// Dialogue Scene 
		else if (gp.gameState == gp.dialogueState) {
			if (dialogueCounter < 7) drawDialogue(g, dialogue[dialogueCounter]);
			else if (dialogueState == lever) drawDialogue(g, "Some   Door   Just   Open");
			else if (dialogueState == door) drawDialogue(g, "knock   knock\nnothing   happens");
			else if (dialogueState ==  specialDoor) drawDialogue(g, "go   back   to   previous   room\nand   try   again");
		}
	}
	
	public void drawDialogue(Graphics g, String dialogue) {
		drawBackground(g);
		drawWindow(g);
		
		// Dialogue Text
		g.setColor(Color.white);
		g.setFont(GraphicTools.arcadeFontDialogue);
		int count = 0;
		for (String line : dialogue.split("\n")) {
			int nextLine = g.getFontMetrics().getHeight() * count;
			g.drawString(line, windowX + 20, windowY + 40 + nextLine);
			count++;
		}
		
		// Continue Text
		spaceMsg = "Press   Spacebar   to   Continue";
		drawPressedSpacebar(g, spaceMsg, gp.panelY - gp.tileSize/2);
	}
	
	public void drawWindow(Graphics g) {
		
		g.setColor(Color.white);
		g.fillRoundRect(windowX - border, windowY - border, windowW + border*2, windowH + border*2, arc, arc);
		
		g.setColor(Color.black);
		g.fillRoundRect(windowX , windowY, windowW, windowH, arc, arc);
	}
	
	public void drawBackground(Graphics g) {
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(0, 0, gp.panelX,	gp.panelY);
	}
	
	public void drawPressedSpacebar(Graphics g, String text, int y) {
		g.setColor(Color.white);
		g.setFont(GraphicTools.arcadeFontSmall);
		GraphicTools.drawCenterTextX(text, g, gp, y);
	}
}
