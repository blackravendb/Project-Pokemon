package de.pokemon;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;


public class Name implements ComponentListener {

	/** The text field */
	private TextField field;
	public String string;
	public boolean stringFilled;
	
	public Name() {
		
	}

	/**
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		field = new TextField(container, container.getDefaultFont(), 250, 80, 100, 20);
		field.addListener(this);
		field.setBackgroundColor(Color.white);
		field.setTextColor(Color.black);
		field.setMaxLength(8);
		stringFilled = false;
	}

	/**
	 * @see org.newdawn.slick.BasicGame#render(org.newdawn.slick.GameContainer,
	 *      org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) {
		
			field.render(container, g);
	}

	/**
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer,
	 *      int)
	 */
	public void update(GameContainer container, int delta) {
		
	}

	
	@Override
	public void componentActivated(AbstractComponent source) {
		string = field.getText(); //funktioniert!
		stringFilled = true;
	}

}