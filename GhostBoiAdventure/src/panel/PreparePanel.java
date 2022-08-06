package panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;

import customComponent.CustomButton;
import customComponent.CustomLabel;
import customComponent.CustomPanel;
import customComponent.CustomRadioBT;
import customComponent.CustomTextField;
import customComponent.GraphicTools;
import mainGame.GamePanel;

public class PreparePanel extends CustomPanel implements ActionListener, ItemListener{

	GamePanel gp;
	int characterPanelH = 48*7;
	int namePanelH = 48*3;
	int buttonPanelH = 48*2;
	public boolean goBackToStart = false, gameOn = false;
	
	String character = "Choose Your Character";
	CustomLabel characterLB = new CustomLabel(character, "big");
	
	CustomRadioBT blueRBT = new CustomRadioBT("Blue");
	CustomRadioBT redRBT = new CustomRadioBT("Red");
	CustomRadioBT greenRBT = new CustomRadioBT("Green");
	
	CustomButton readyBT = new CustomButton(" Ready!");
	CustomButton backBT = new CustomButton("Back");
	CustomLabel nameLB = new CustomLabel("Insert   Your   Name", "medium");
	CustomTextField nameTF = new CustomTextField("Boi");
	
	CustomPanel characterPanel = new CustomPanel(new Dimension(w, characterPanelH), true);
	CustomPanel namePanel = new CustomPanel(new Dimension(w, namePanelH), true);
	CustomPanel buttonPanel = new CustomPanel(new Dimension(w, buttonPanelH), true);
	
	ImageIcon playerBlue, playerGreen, playerRed;
	int scalingImage = 100;
	int imageX = (48*16 - scalingImage*3)/4;
	int imageY = 48*3;
	
	public String playerColor = null;
	String previous = null;
	
	boolean alert = false;
	
	int alertY;
		 	
	public PreparePanel(int w, int h, GamePanel gp) {
		super(new Dimension(w, h), false);
		this.w = w;
		this.h = h;
		centerX = w/2;
		centerY = h/2;
		this.gp = gp;
		playerColor = gp.player.getColorState();
		int centerX = w/2;
		this.setPreferredSize(new Dimension(w, h));
		this.setLayout(new BorderLayout());
		this.add(characterPanel, BorderLayout.NORTH);
		this.add(namePanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		// Character Panel	
		characterPanel.add(characterLB);
		characterPanel.add(blueRBT);
		characterPanel.add(greenRBT);
		characterPanel.add(redRBT);
		
		characterLB.setCenteredPanel(this, 48);
		
		int rbtW = 130;
		int rbtH = 50;
		int rbtY = 270;
		
		blueRBT.setBounds(imageX + scalingImage/2 - rbtW/2, rbtY, rbtW, rbtH);
		greenRBT.setBounds((imageX*2 + scalingImage) + scalingImage/2 - rbtW/2, rbtY, rbtW, rbtH);
		redRBT.setBounds((imageX*3 + scalingImage*2) + scalingImage/2 - rbtW/2, rbtY, rbtW, rbtH);
		
		blueRBT.addItemListener(this);
		greenRBT.addItemListener(this);
		redRBT.addItemListener(this);
		
		blueRBT.setSelected(true);
				
		// Name Panel
		namePanel.add(nameLB);
		namePanel.add(nameTF);
		namePanel.add(readyBT);
		
		int buttonW = w/6;
		int buttonH = 40;
		int tfW = w/2;
		int horiInterval = (namePanelH - buttonH - nameLB.labelH)/2;
		int verticalInterval = (w - (buttonW + 10) - w/2)/2;
		
		nameLB.setCenteredPanel(this, horiInterval);
		nameTF.setBounds(verticalInterval, horiInterval + nameLB.labelH, tfW, buttonH);
		readyBT.setBounds(verticalInterval + tfW, horiInterval + nameLB.labelH, buttonW + 10, buttonH);
		alertY = horiInterval + nameLB.labelH + buttonH;
		
		readyBT.addActionListener(this);
		
		// Button Panel
		buttonPanel.setPreferredSize(new Dimension(w, buttonPanelH));
		buttonPanel.setLayout(null);
		buttonPanel.add(backBT);
		
		backBT.setBounds(centerX - buttonW/2, (buttonPanelH - buttonH)/2, buttonW, buttonH);
		backBT.addActionListener(this);
		
		// Get Image For Character Selection
		getPlayerImage();
		
//		System.out.println(characterLB.getBounds());
//		System.out.println(imageX + " " + imageY);
//		System.out.println((imageX*2 + scalingImage) + " " + imageY);
//		System.out.println((imageX*3 + scalingImage*2) + " " + imageY);
//		System.out.println(blueRBT.getBounds());
//		System.out.println(greenRBT.getBounds());
//		System.out.println(redRBT.getBounds());
//		System.out.println(nameLB.getBounds());
//		System.out.println(nameTF.getBounds());
//		System.out.println(readyBT.getBounds());
//		System.out.println(backBT.getBounds());
		
	}
	
	private void getPlayerImage() {
		// Get Player Gif
		Image bluePic, greenPic, redPic;
		
		playerBlue = new ImageIcon(getClass().getResource("/entity/boi_moving_blue.gif"));
		bluePic = playerBlue.getImage();
		bluePic = bluePic.getScaledInstance(scalingImage, scalingImage, java.awt.Image.SCALE_DEFAULT);
		playerBlue = new ImageIcon(bluePic);
		
		playerGreen = new ImageIcon(getClass().getResource("/entity/boi_moving_green.gif"));
		greenPic = playerGreen.getImage();
		greenPic = greenPic.getScaledInstance(scalingImage, scalingImage, java.awt.Image.SCALE_DEFAULT);
		playerGreen = new ImageIcon(greenPic);
		
		playerRed = new ImageIcon(getClass().getResource("/entity/boi_moving_red.gif"));
		redPic = playerRed.getImage();
		redPic = redPic.getScaledInstance(scalingImage, scalingImage, java.awt.Image.SCALE_DEFAULT);
		playerRed = new ImageIcon(redPic);		
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
				
		playerBlue.paintIcon(this, g, imageX, imageY);
		playerGreen.paintIcon(this, g, imageX*2 + scalingImage, imageY);
		playerRed.paintIcon(this, g, imageX*3 + scalingImage*2, imageY);
		
		if (alert) {
			g.setColor(GraphicTools.red);
			g.setFont(GraphicTools.arcadeFontSmall);
			String alertmsg = "Only   3   to   10   characters";
			int width = (int)g.getFontMetrics().getStringBounds(alertmsg, g).getWidth();
			int height = (int)g.getFontMetrics().getStringBounds(alertmsg, g).getHeight();
			g.drawString(alertmsg, centerX - width/2, characterPanelH + alertY + height/15*10 + 5);
			//System.out.println((centerX - width/2) +" "+ (characterPanelH + alertY + height/15*10 + 5));
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == backBT) {
			goBackToStart = true;
		}
		if (e.getSource() == readyBT) {
			if (nameTF.getText().length() > 10 || nameTF.getText().length() < 3) {
				alert = true;
				repaint();
			} else {
				gameOn = true;
				gp.player.name = nameTF.getText();
				// Random row 1-5
				int firstSoulRow = (int)(Math.random()*5) + 1;
				// Random col 6-10
				int firstSoulCol = (int)(Math.random()*5) + 6;
				// Create first soul with random
				gp.obj.createSoul(0, firstSoulRow, firstSoulCol);
				gp.dialogueCount = gp.dialogueTime;
			}	
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		int check = 1;
		int uncheck = 2;
		if(e.getSource() == blueRBT) {
			if (e.getStateChange() == check) {
				playerColor = "blue";
				previous = playerColor;
				gp.player.setColorState(playerColor);
				blueRBT.setSelected(true);
				greenRBT.setSelected(false);
				redRBT.setSelected(false);
			} else if(e.getStateChange() == uncheck && previous == "blue") {
				blueRBT.setSelected(true);
			}
			
		} else if(e.getSource() == greenRBT) {
			if (e.getStateChange() == check) {
				playerColor = "green";
				previous = playerColor;
				gp.player.setColorState(playerColor);
				blueRBT.setSelected(false);
				greenRBT.setSelected(true);
				redRBT.setSelected(false);
			} else if(e.getStateChange() == uncheck && previous == "green") {
				greenRBT.setSelected(true);
			}
		} else if(e.getSource() == redRBT) {
			if (e.getStateChange() == check) {
				playerColor = "red";
				previous = playerColor;
				gp.player.setColorState(playerColor);
				blueRBT.setSelected(false);
				greenRBT.setSelected(false);
				redRBT.setSelected(true);
			} else if(e.getStateChange() == uncheck && previous == "red") {
				redRBT.setSelected(true);
			}
		} 
	}

}
