package customComponent;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class CustomPanel extends JPanel{
	
	public int w, h, centerX, centerY;
	
	public CustomPanel() {
		super();
		new GraphicTools();
		this.setBackground(GraphicTools.lightBrown2);
		this.setLayout(null);
	}
	
	public CustomPanel(Dimension d, boolean transparent) {
		super();
		new GraphicTools();
		this.setPreferredSize(d);
		if (transparent) {
			this.setBackground(new Color(0,0,0,0));
		} else {
			this.setBackground(GraphicTools.lightBrown2);
		}
		this.setLayout(null);
		this.setBorder(new LineBorder(GraphicTools.lightBrown2));
		//this.setBorder(new LineBorder(GraphicTools.darkBrown2));
	}
}
