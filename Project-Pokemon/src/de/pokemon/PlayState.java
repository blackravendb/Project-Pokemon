package de.pokemon;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;



public class PlayState extends BasicGameState {

	/** ID of the state*/
	public static int ID; 
	/** reference for the map*/
	private Map map;
	/** reference for the camera*/
	private Camera camera;
	/** reference for player */
	private Player player;
	/** reference for the inGameMenu */
	private InGameMenu menu;
	/** reference for the input*/
	private Input input;
	/** eventmanager reference*/
	private EventManager event;

	/**Sets the ID of this state
	 * 
	 * @param id
	 */
	public PlayState(int id){
		ID = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		
		ResourceManager.init();
		event = new EventManager(this);
		map = new Map("House", event);
		player = new Player(map.getSpawn("player").x, map.getSpawn("player").y, event);

		camera = new Camera(gc, map);
		camera.centerOn(player);
		menu = new InGameMenu(gc,game);
		input = gc.getInput();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {

		camera.drawMap();

		camera.translateGraphics();
		if(map.hasWater){
			map.renderWater();
		}
		//draw player and all entities here
		player.renderPlayerBody();
		map.renderNpcs();
		player.renderPlayerHead();
		camera.untranslateGraphics();
		camera.drawForeground();

		camera.translateGraphics();
		//DRAW EVERYTHING AT NORMAL POSITION ON THE MAP AFTER TRANSLATEGRAPHICS
		gc.setShowFPS(menu.showFps);

		if(menu.showGrid){
			map.showGrid(g);	
		}

		if(menu.showBlocked){
			map.showBlocked(g);
		}

		camera.untranslateGraphics();
		// DRAW THE HUD AND MENU AFTER UNTRANSLATEGRAPHICS	

		if(menu.showPosition){
			map.showPlayerPosition(g, player.getPosX(), player.getPosY(),player.getTileX(),player.getTileY());
		}
		if(menu.showMenu || menu.sliding){
			menu.render(g);
		}
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {

		//if menu is not open and not sliding in or out process normal input 
		if(!menu.showMenu && !menu.sliding){
			player.updatePlayer(input);			
			map = map.update(player,event);
			if (map != camera.map) {
				camera = new Camera(gc, map);
			}
			camera.centerOn(player);
			
			if(player.isStanding){
				if(input.isKeyPressed(Input.KEY_ESCAPE)){
					Sound.audioTextBox.playAsSoundEffect(1.0f, 3.0f, false);
					menu.sliding = true;
				}
			}
		}
		map.updateNpcs(delta,!menu.showMenu && !menu.sliding);

		menu.update(input);

		input.clearKeyPressedRecord();	
	}

	@Override
	public int getID() {
		return ID;
	}

}
