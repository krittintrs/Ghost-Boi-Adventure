package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import customComponent.CustomButton;
import customComponent.CustomLabel;
import customComponent.CustomPanel;
import customComponent.GraphicTools;

public class HowToPanel extends CustomPanel implements ActionListener{

	int topPanelH = 48*2;
	int howtoPanelH = 48*8;
	int buttonPanelH = 48*2;
	public boolean goBackToStart = false;
	
	CustomLabel howtoLB = new CustomLabel("How  To  Play", "big");
	CustomButton backBT = new CustomButton("Back");
	CustomPanel topPanel = new CustomPanel(new Dimension(w, topPanelH), true);
	CustomPanel howtoPanel = new CustomPanel(new Dimension(w, howtoPanelH), true);
	CustomPanel buttonPanel = new CustomPanel(new Dimension(w, buttonPanelH), true);

	public HowToPanel(int w, int h) {
		super(new Dimension(w, h), false);
		this.w = w;
		this.h = h;
		centerX = w/2;
		centerY = h/2;
		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.add(howtoPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		topPanel.add(howtoLB);
		buttonPanel.add(backBT);
		
		howtoLB.setCenteredPanel(this, (topPanelH - 48)/2);
		
		int center = w/2;
		int buttonW = w/6;
		int buttonH = 40;
		int panelH = h/12*2;
		backBT.setBounds(center - buttonW/2, (panelH - buttonH)/2, buttonW, buttonH);
		backBT.addActionListener(this);
		
//		System.out.println(howtoLB.getBounds());
//		System.out.println(backBT.getBounds());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == backBT) {
			goBackToStart = true;
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// BG Circle
		int r = 150;
		g.setColor(GraphicTools.lightBrown1);
		g.fillOval(centerX - r, centerY - r, 2*r, 2*r);
	
		// Button Detail
		int keySize = 66;
		int interval = 4;
		int wholeW = keySize*3 + interval*3;
		
		String[][] keyBinds = {{"Q", "W", "E"}, 
							   {"A", "S", "D"}};
		String[][] keyCommand = {{"Transform", "Up", "Interact"}, 
				   				 {"Left", "Down", "Right"}};
				
		int upY = centerY - keySize - interval/2;
		int lowY = centerY + interval/2;		

		int[] centerUpX = new int[3];
		int[] centerLowX = new int[3];
		int[][] commandH = new int[3][3];
		int[][] commandW = new int[3][3];
		
		for(int i=0; i<3; i++) {
			// Keyboard above
			int upX = centerX - wholeW/2 + (keySize + interval)*i;
			drawKeyButton(g, upX, upY);
			
			// Keyboard below
			int lowX = centerX - wholeW/2 + interval + (keySize + interval)*i;
			drawKeyButton(g, lowX, lowY);
			
			// Keyboard Text
			g.setFont(GraphicTools.arcadeFontMedium);
			int keyBindW = (int)g.getFontMetrics().getStringBounds(keyBinds[0][0], g).getWidth(); //24
			int keyBindH = 30;
			int textShiftX = (keySize - keyBindW)/2;
			int textShiftY = (50 - keyBindH)/2 + keyBindH;
			g.setColor(Color.white);
			g.drawString(keyBinds[0][i], upX + textShiftX, upY + textShiftY);
			g.drawString(keyBinds[1][i], lowX + textShiftX, lowY + textShiftY);
			
			centerUpX[i] = upX + keySize/2;
			centerLowX[i] = lowX + keySize/2;			
			
			// String Detail
			g.setFont(GraphicTools.arcadeFontSmall);
			commandW[0][i] = (int)g.getFontMetrics().getStringBounds(keyCommand[0][i], g).getWidth();
			commandH[0][i] = (int)g.getFontMetrics().getStringBounds(keyCommand[0][i], g).getHeight();
			commandW[1][i] = (int)g.getFontMetrics().getStringBounds(keyCommand[1][i], g).getWidth();
			commandH[1][i] = (int)g.getFontMetrics().getStringBounds(keyCommand[1][i], g).getHeight();
		}
		
		// Line
		int miniCircleR = 6;
		int borderLong = 40;
		int borderThick = 3;
		g.fillOval(centerUpX[1] - miniCircleR, upY - miniCircleR, miniCircleR*2, miniCircleR*2);
		g.fillOval(centerLowX[1] - miniCircleR, (lowY + 50) - miniCircleR, miniCircleR*2, miniCircleR*2);
		g.fillRect(centerUpX[1] - borderThick/2, upY - borderLong, borderThick, borderLong);
		g.fillRect(centerLowX[1] - borderThick/2, lowY + 50, borderThick, borderLong);
		
		g.fillOval(centerUpX[0] - 25 - miniCircleR, upY + 25 - miniCircleR, miniCircleR*2, miniCircleR*2);
		g.fillOval(centerLowX[0] - 25 - miniCircleR, (lowY + 25) - miniCircleR, miniCircleR*2, miniCircleR*2);
		g.fillRect(centerUpX[0] - 25 - borderLong, upY + 25 - borderThick/2, borderLong, borderThick);
		g.fillRect(centerLowX[0] - 25 - borderLong, (lowY + 25) - borderThick/2, borderLong, borderThick);
		
		g.fillOval(centerUpX[2] + 25 - miniCircleR, upY + 25 - miniCircleR, miniCircleR*2, miniCircleR*2);
		g.fillOval(centerLowX[2] + 25 - miniCircleR, (lowY + 25) - miniCircleR, miniCircleR*2, miniCircleR*2);
		g.fillRect(centerUpX[2] + 25, upY + 25 - borderThick/2, borderLong, borderThick);
		g.fillRect(centerLowX[2] + 25, (lowY + 25) - borderThick/2, borderLong, borderThick);
		
		// Key Command
		g.setFont(GraphicTools.arcadeFontSmall);
		int intervalY = 5;
		int intervalX = 8;
		g.drawString(keyCommand[0][1], centerUpX[1] - commandW[0][1]/2, upY - borderLong - intervalY);
		g.drawString(keyCommand[1][1], centerLowX[1] - commandW[1][1]/2, (lowY + 50) + borderLong + intervalY + commandH[1][1]/15*10);
		
		g.drawString(keyCommand[0][0], centerUpX[0] - 25 - borderLong - commandW[0][0] - intervalX, upY + 25 + commandH[0][0]/15*10 - 10);
		g.drawString(keyCommand[1][0], centerLowX[0] - 25 - borderLong - commandW[1][0] - intervalX, (lowY + 25) + commandH[1][0]/15*10 - 10);
		
		g.drawString(keyCommand[0][2], centerUpX[2] + 25 + borderLong + intervalX, upY + 25 + commandH[0][0]/15*10 - 10);
		g.drawString(keyCommand[1][2], centerLowX[2] + 25 + borderLong + intervalX, (lowY + 25) + commandH[0][0]/15*10 - 10);
	}
	
	private void drawKeyButton(Graphics g, int x, int y) {
		int keySize = 50;
		int sideX = 8;
		int sideY = 16;
		int keyX = x + sideX;
		int keyY = y;
		
		g.setColor(GraphicTools.lightBrown3);
		g.fillRect(keyX, keyY, keySize, keySize);
		
		g.setColor(GraphicTools.darkBrown1);
		int[] side1X = {keyX, keyX, keyX - sideX, keyX - sideX};
		int[] side1Y = {keyY, keyY + keySize, keyY + keySize + sideY, keyY + sideY};
		g.fillPolygon(side1X, side1Y, 4);
		int[] side2X = {keyX + keySize, keyX + keySize, keyX + keySize + sideX, keyX + keySize + sideX};
		int[] side2Y = {keyY, keyY + keySize, keyY + keySize + sideY, keyY + sideY};
		g.fillPolygon(side2X, side2Y, 4);
		int[] side3X = {keyX, keyX - sideX, keyX + keySize + sideX, keyX + keySize};
		int[] side3Y = {keyY + keySize, keyY + keySize + sideY, keyY + keySize + sideY, keyY + keySize};
		g.fillPolygon(side3X, side3Y, 4);
		
		g.setColor(GraphicTools.lightBrown1);
		g.drawRect(keyX, keyY, keySize, keySize);
		g.drawPolygon(side1X, side1Y, 4);
		g.drawPolygon(side2X, side2Y, 4);
		g.drawPolygon(side3X, side3Y, 4);
	}
}
