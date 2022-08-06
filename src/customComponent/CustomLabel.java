package customComponent;

import java.awt.Color;

import javax.swing.JLabel;

public class CustomLabel extends JLabel{
	
	public int labelW, labelH;
	
	public CustomLabel(String title, String size) {
		super();
		new GraphicTools();
		this.setText(title);
		this.setHorizontalAlignment(JLabel.CENTER);		
		this.setForeground(Color.white);
		this.setOpaque(false);
		if (size == "big") {
			this.setFont(GraphicTools.arcadeFontBig);
		} else if (size == "medium") {
			this.setFont(GraphicTools.arcadeFontMedium);
		} else if (size == "small") {
			this.setFont(GraphicTools.arcadeFontSmall);
		}
		labelW = this.getPreferredSize().width;
		labelH = this.getPreferredSize().height;
		//this.setBorder(BorderFactory.createRaisedBevelBorder());
	}
	
	public void setCenteredPanel(CustomPanel cp, int y) {
		this.setBounds(cp.centerX - labelW/2, y, labelW, labelH);
	}
}
