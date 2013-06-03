package de.pokemon;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Core extends StateBasedGame {

	static AppGameContainer container;
	
	public Core(String name) throws SlickException {
		super(name);
		
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
		this.addState(new freeWorldState());
	}

}
