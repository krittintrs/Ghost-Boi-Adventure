package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import customComponent.CustomButton;
import customComponent.CustomLabel;
import customComponent.CustomPanel;
import customComponent.GraphicTools;
import mainGame.GamePanel;
import mainGame.SpriteManager;

public class StartPanel extends CustomPanel implements ActionListener {
	
	int w, h;
	GamePanel gp;
	public boolean prepareOn = false, howToOn = false;
	
	int topH = 48*3;
	int middleH = 48*4;
	int downH = 48*5;
	String titleText = "  Ghost   Boi   Adventure!";
	CustomLabel title = new CustomLabel(titleText, "big");
	CustomButton startBT = new CustomButton("Start");
	CustomButton howToBT = new CustomButton("How  To  Play");
	CustomButton exitBT = new CustomButton("Exit");
	CustomPanel panelMiddle = new CustomPanel(new Dimension(w, middleH), true);
	CustomPanel panelDown = new CustomPanel(new Dimension(w, downH), false);
	CustomPanel buttonPanel = new CustomPanel();
	CustomPanel leftBlank = new CustomPanel();
	CustomPanel rightBlank = new CustomPanel();	
	SpriteManager spriteM = new SpriteManager();
	BufferedImage ghostPic = null;
	int titleW = title.getPreferredSize().width;
	int titleH = title.getPreferredSize().height;

	public StartPanel(int w, int h, GamePanel gp) {
		super(new Dimension(w, h), false);
		this.w = w;
		this.h = h;
		centerX = w/2;
		centerY = h/2;
		this.gp = gp;
		this.setLayout(new BorderLayout());
		this.add(title, BorderLayout.NORTH);
		this.add(panelMiddle, BorderLayout.CENTER);
		this.add(panelDown, BorderLayout.SOUTH);
		
		title.setPreferredSize(new Dimension(w, topH));

		panelDown.setLayout(new GridLayout(1, 3));
		panelDown.add(leftBlank);
		panelDown.add(buttonPanel);
		panelDown.add(rightBlank);

		buttonPanel.add(startBT);
		buttonPanel.add(howToBT);
		buttonPanel.add(exitBT);
		
		int panelWidth = w/3;
		int panelHeight = h/12*5;
		
		int interval = 20;
		int buttonWidth = w/4;
		int buttonHeight = 50;
		int sideInterval = (panelWidth - buttonWidth)/2;
		int firstInterval = (panelHeight - (buttonHeight*3 + interval*2))/2;
		
		startBT.setBounds(sideInterval, firstInterval, buttonWidth, buttonHeight);
		startBT.addActionListener(this);
		
		howToBT.setBounds(sideInterval, firstInterval + buttonHeight + interval, buttonWidth, buttonHeight);
		howToBT.addActionListener(this);
		
		exitBT.setBounds(sideInterval, firstInterval + buttonHeight*2 + interval*2, buttonWidth, buttonHeight);
		exitBT.addActionListener(this);
		
//		System.out.println(startBT.getBounds());
//		System.out.println(howToBT.getBounds());
//		System.out.println(exitBT.getBounds());
		
		// Get Picture
		try {
			BufferedImage spriteImage = ImageIO.read(getClass().getResourceAsStream("/entity/ghost.png"));
			ghostPic = spriteImage.getSubimage(0, 0, 16, 16);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == startBT) {
			prepareOn = true;
			//this.setVisible(false);
		}
		if (e.getSource() == howToBT) {
			howToOn = true;
		}
		if (e.getSource() == exitBT) {
			System.exit(0);
		}
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int centerX = w/2;
		int centerY = h/12*5;
		
		// Title Shadow
		g.setFont(GraphicTools.arcadeFontBig);
		g.setColor(GraphicTools.darkBrown2);
		int textH = (int)g.getFontMetrics().getStringBounds(titleText, g).getHeight();
		int upperLeftX = (w - titleW)/2 + 2;
		int upperLeftY = (topH - titleH)/2 + textH; 
		g.drawString(titleText, upperLeftX + 6, upperLeftY - 4);
		//System.out.println((upperLeftX + 6) + " " +  (upperLeftY - 4));
		
		// Player & Ghost 		
		BufferedImage[][] playerPic = gp.player.picture; // Picture format down up left right
		g.drawImage(playerPic[0][0], centerX - gp.tileSize, centerY - gp.tileSize/2, gp.tileSize, gp.tileSize, null);
		g.drawImage(ghostPic, centerX, centerY - gp.tileSize/2, gp.tileSize, gp.tileSize, null);
		//System.out.println((centerX - gp.tileSize) + " " +  (centerY - gp.tileSize/2));
		//System.out.println((centerX) + " " +  (centerY - gp.tileSize/2));
 	}
}
