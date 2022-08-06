package customComponent;

import javax.swing.JTextField;

public class CustomTextField extends JTextField {
	
	public CustomTextField(String title) {
		super(title);
		new GraphicTools();
		this.setFont(GraphicTools.arcadeFontSmall);
		this.setForeground(GraphicTools.darkBrown1);
	}
}
