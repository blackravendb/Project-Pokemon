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

	public static void main(String[] args) throws SlickException {
		
		container = new AppGameContainer(new Core("Project Pokemon"));
		container.setTargetFrameRate(60);
		container.start();
		
	}

	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new freeWorldState());
	}

}
