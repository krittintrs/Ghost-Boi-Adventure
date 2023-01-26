package mainGame;

import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import panel.HowToPanel;
import panel.PreparePanel;
import panel.StartPanel;

public class Main {
	
//	StartPanel start;
//	GamePanel gp;
//	HowToPanel howto;
//	PreparePanel prepare;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean programLoop = true;
		int width = 768;
		int height = 576;
		
		JFrame Frame = new JFrame();
		
		GamePanel gp = new GamePanel();
		StartPanel start = new StartPanel(width, height, gp);
		HowToPanel howto = new HowToPanel(width, height);
		PreparePanel prepare = new PreparePanel(width, height, gp);

		Frame.getContentPane().add(start);
		Frame.pack();
		Frame.setVisible(true);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame.setResizable(false);
		Frame.setLocationRelativeTo(null);
		Frame.setTitle("Ghost Boi Adventure");
		
		while(programLoop) {
			
			// Click start from main
			if (start.prepareOn) {
				//System.out.println("Preparing");
				Frame.setContentPane(prepare);
				Frame.revalidate();
				Frame.pack();
				start.prepareOn = false;
			}
			// Click how to from main
			if (start.howToOn) {
				Frame.setContentPane(howto);
				Frame.revalidate();
				Frame.pack();
				start.howToOn = false;
				//break;
			}
			// Click back from prepare
			if (prepare.goBackToStart) {
				//System.out.println("Back To Main");
				gp.player.setColorState(prepare.playerColor);
				Frame.setContentPane(start);
				start.repaint();
				Frame.revalidate();
				Frame.pack();
				prepare.goBackToStart = false;
				//break;
			}
			// Click back from how to
			if (howto.goBackToStart) {
				//System.out.println("Back To Main");
				Frame.setContentPane(start);
				Frame.revalidate();
				Frame.pack();
				howto.goBackToStart = false;
			}	
			
			// Click ready from prepare
			if (prepare.gameOn) {
				//System.out.println("Game On!!!");
				// Choose Character
				gp.player.setColorState(prepare.playerColor);
				Frame.removeAll();
				Frame.dispose();
				break;
			}	
			
			// Set some delay
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		JFrame gameFrame = new JFrame();

		gameFrame.add(gp);
		gameFrame.pack();
		gameFrame.setVisible(true);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setTitle("Ghost Boi Adventure");
	}
}
