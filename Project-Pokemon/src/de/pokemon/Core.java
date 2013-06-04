package de.pokemon;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Core extends StateBasedGame {

	static AppGameContainer container;
	public static final int menu = 0;
	public static final int play = 1;
	public static final int devtools = 2;
	
	public Core(String name) throws SlickException {
		super(name);
		this.addState(new Menu(menu));
		this.addState(new PlayState(play));
		//this.addState(new DevTools(devtools));

	}
	
	/** Entry point for game
	 * 
	 * @param args
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException {
		
		container = new AppGameContainer(new Core("Project Pokemon"));
		container.setDisplayMode(640, 640, false);
		container.setTargetFrameRate(60);
		container.setShowFPS(false);
		container.start();
		
	}

	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(menu).init(gc, this);
		this.getState(play).init(gc, this);
		//this.getState(devtools).init(gc, this);
		this.enterState(menu);
	}

}
