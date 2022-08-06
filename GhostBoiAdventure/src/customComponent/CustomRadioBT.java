package customComponent;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JRadioButton;

public class CustomRadioBT extends JRadioButton{
	
	public CustomRadioBT(String title) {
		super();
		new GraphicTools();
		this.setText(title);
		this.setFont(GraphicTools.arcadeFontSmall);
		this.setForeground(Color.white);
		this.setBackground(GraphicTools.lightBrown3);
		this.setFocusPainted(false);
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.setHorizontalAlignment(JRadioButton.CENTER);		
	}
}
