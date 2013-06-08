package de.pokemon;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import java.awt.Color;
import java.awt.Font;

/* Credit goes to Brandon Reid from http://slickrpg.blogspot.de/2011/07/unicode-font.html for this handy font class.
 * 
 */

public class SimpleFont {
	private UnicodeFont font;

	/**Creates a Unicode Font based on the arguments.
	 * 
	 * @param fontName name of the awt font
	 * @param style e.g.  Font.ITALIC
	 * @param size of the font
	 * @param color to be used for the font
	 * @throws SlickException
	 */
	public SimpleFont(String fontName, int style, int size, Color color)
			throws SlickException {
		this(new Font(fontName, style, size), color);
	}
	
	/**Creates a Unicode Font based on the arguments.
	 * 
	 * @param fontName name of the awt.font
	 * @param style e.g.  Font.ITALIC
	 * @param size of the font     
	 * @throws SlickException
	 */
	public SimpleFont(String fontName, int style, int size)
			throws SlickException {
		this(new Font(fontName, style, size));
	}

	/**Creates a Unicode Font based on the font using the color white.
	 * 
	 * @param font awt Font
	 * @throws SlickException
	 */
	public SimpleFont(Font font) throws SlickException {
		this(font, Color.white);
	}

	/**Creats a Unicode Font based on the arguments.
	 * 
	 * @param font to be used for the unicode font
	 * @param color to be used for the font
	 * @throws SlickException
	 */
	@SuppressWarnings("unchecked")
	public SimpleFont(Font font, Color color) throws SlickException {
		this.font = new UnicodeFont(font);
		ColorEffect colorEffect = new ColorEffect(color);
		this.font.getEffects().add(colorEffect);
		this.font.addAsciiGlyphs();
		this.font.loadGlyphs();
	}

	/**Sets the color for the current Font
	 * 
	 * @param color
	 * @throws SlickException
	 */
	@SuppressWarnings("unchecked")
	public void setColor(Color color) throws SlickException {
		font.getEffects().clear();
		font.getEffects().add(new ColorEffect(color));
		font.clearGlyphs();
		font.addAsciiGlyphs();
		font.loadGlyphs();
	}
	/** 
	 * 
	 * @return the current unicode font
	 */
	public UnicodeFont get() {
		return font;
	}
}