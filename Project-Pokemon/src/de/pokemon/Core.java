package de.pokemon;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Core extends StateBasedGame {

	/** ID of the menu state */
	public static final int menu = 0;
	/** ID of the play state */
	public static final int play = 1;
	/** ID of the intro state */
	public static final int intro = 2;
	/** ID of the credit state */
	public static final int credits = 3;
	/** size of a single tile */
	public static final int tileSize = 32;
	/** width of the game canvas*/
	public static final int width = 640;
	/** height of the game canvas*/
	public static final int height = 480;
	/** path to the 16x16 and 32x32 icons*/
	public static final String[] icons = {"res/icons/16.png", "res/icons/32.png"};
	/**
	 * 
	 * @param name
	 * @throws SlickException
	 */
	public Core(String name) throws SlickException {
		super(name);
	}
	
	/** Entry point for game
	 * 
	 * @param args
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException {
		AppGameContainer container = new AppGameContainer(new Core("Project Pokemon"));
		container.setDisplayMode(width, height, false);
		container.setTargetFrameRate(60);
		container.setShowFPS(false);
		container.setIcons(Core.icons);
		container.start();	
	}

	public void initStatesList(GameContainer gc) throws SlickException { 
		this.addState(new MenuState(menu)); // init() is also called when adding a state
		this.addState(new PlayState(play));
		this.addState(new IntroState(intro));
		this.addState(new CreditsState(credits));
		this.enterState(menu);
	}

}
