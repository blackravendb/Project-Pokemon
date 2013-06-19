package test;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;

/**
 * A test for the GUI components available in Slick. Very simple stuff
 * 
 * @author kevin
 */
public class GUITest extends BasicGame implements ComponentListener {

	/** The message to display */
	private String message = "Demo Menu System with stock images";
	/** The text field */
	private TextField field;

	/**
	 * Create a new test of GUI rendering
	 */
	public GUITest() {
		super("GUI Test");
	}

	/**
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		field = new TextField(container, container.getDefaultFont(), 150, 20, 500, 35);
		field.addListener(this);
		field.setMaxLength(20);
	}

	/**
	 * @see org.newdawn.slick.BasicGame#render(org.newdawn.slick.GameContainer,
	 *      org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) {

		field.render(container, g);

		g.drawString(message, 200, 550);
	}

	/**
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer,
	 *      int)
	 */
	public void update(GameContainer container, int delta) {
	}

	/**
	 * Entry point to our test
	 * 
	 * @param argv
	 *            The arguments passed to the test
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new GUITest());
			container.setDisplayMode(800, 600, false);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	@Override
	public void componentActivated(AbstractComponent source) {
		message = "Entered1: " + field.getText();
	}

}