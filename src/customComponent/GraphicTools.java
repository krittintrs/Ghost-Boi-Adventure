package customComponent;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;

import mainGame.GamePanel;

public class GraphicTools {

	public static Font arcadeFontSuperSmall;
	public static Font arcadeFontSmall;
	public static Font arcadeFontDialogue;
	public static Font arcadeFontMedium;
	public static Font arcadeFontBig;
	public static Font arcadeFontSuperBig;
	public static Color lightBrown1 = new Color(210,165,109);
	public static Color lightBrown2 = new Color(206,139,84);
	public static Color lightBrown3 = new Color(189,126,74);
	public static Color darkBrown1 = new Color(150,97,61);
	public static Color darkBrown2 = new Color(131,80,46);
	public static Color red = new Color(255, 0, 0, 255);
	public static Color lightBlue = new Color(168, 202, 228);
	public static Color darkBlue = new Color(68, 160, 228);
	
	public GraphicTools() {
		arcadeFontSuperSmall = createFont("/font/ARCADECLASSIC.TTF", 20F);
		arcadeFontSmall = createFont("/font/ARCADECLASSIC.TTF", 30F);
		arcadeFontDialogue = createFont("/font/ARCADECLASSIC.TTF", 36F);
		arcadeFontMedium = createFont("/font/ARCADECLASSIC.TTF", 45F);
		arcadeFontBig = createFont("/font/ARCADECLASSIC.TTF", 60F);
		arcadeFontSuperBig = createFont("/font/ARCADECLASSIC.TTF", 80F);
	}
	
	public Font createFont(String path, float size) {
		Font inputFont = null;
		try {
			InputStream is = getClass().getResourceAsStream(path);
			inputFont = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		Font sizedFont = inputFont.deriveFont(size);
		return sizedFont;
	}
	
	public static void drawCenterTextX(String text, Graphics g, GamePanel gp, int y) {
		int textW = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
		int centerX = gp.panelX/2;
		int drawX = centerX - textW/2;
		g.drawString(text, drawX, y);
	}
}
