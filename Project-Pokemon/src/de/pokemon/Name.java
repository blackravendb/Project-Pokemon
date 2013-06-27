package de.pokemon;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;


public class Name implements ComponentListener {

	/** The text field */
	public TextField field;
	/** the name of the player*/
	public String string;
	/** true if the player chose his name*/
	public boolean stringFilled;
	public boolean showTextField;
	/**Constructor of the NameMenu
	 *
	 */
	public Name() {
		
	}

	/**Initialises Name
	 * 
	 * @param container the container holding the game
	 * 
	 */
	public void init(GameContainer container) throws SlickException {
		field = new TextField(container, container.getDefaultFont(), 250, 80, 100, 20);
		field.addListener(this); //Was ist das this?
		field.setBackgroundColor(Color.white);
		field.setTextColor(Color.black);
		field.setMaxLength(8);
		stringFilled = false;
		showTextField = true;
	}

	/** Renders Name
	 * 
	 * @param container the container holding the game
	 * @param g the current graphics context
	 */
	public void render(GameContainer container, Graphics g) {
		
			field.render(container, g);
	}

	/** Updates Name
	 * 
	 * @param container the container holding the game
	 * @param delta the number of milliseconds between frames
	 * 
	 */
	public void update(GameContainer container, int delta) {
		Input input = container.getInput();
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			showTextField = false;
		}
		
	}

	
	
	/**is called when the Enter_Key is pressed and saves the input of the Textfield
	 *
	 * @param source
	 */
	@Override
	public void componentActivated(AbstractComponent source) { 
		if(showTextField == true){
		string = field.getText(); 
		stringFilled = true;
		}
	}

}