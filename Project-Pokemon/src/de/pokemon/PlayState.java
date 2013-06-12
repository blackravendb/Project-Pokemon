package de.pokemon;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;



public class PlayState extends BasicGameState {

	/** ID of the state*/
	public static int ID; // ID of this state

	//public static boolean debug; // if debug option is activated
	/** reference for the map*/
	Map map;
	/** reference for the camera*/
	Camera camera;
	/** reference for player */
	Player player;
	/** reference for the inGameMenu */
	InGameMenu menu;
	/** reference for the input*/
	Input input;

	public PlayState(int id){
		ID = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		map = new Map("House");
		
		player = new Player(map.getSpawn("player").x,map.getSpawn("player").y);

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
		player.renderPlayerHead();
		map.renderNpcs();
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
			//g.drawString(map.getCoordinates(player.getPosX(), player.getPosY(),player.getTileX(),player.getTileY()), 10, 30);
			g.drawLine(0, gc.getHeight()/2, gc.getWidth(), gc.getHeight()/2);
			g.drawLine(gc.getWidth()/2,0, gc.getWidth()/2,gc.getHeight());
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
			map = map.update(player);
			if (map != camera.map) {
				camera = new Camera(gc, map);
			}
			camera.centerOn(player);
			map.updateNpcs();
			if(player.isStanding){
				if(input.isKeyPressed(Input.KEY_ESCAPE)){
					menu.sliding = true;
				}
			}
		}

		menu.update(input);

		input.clearKeyPressedRecord();	
	}

	@Override
	public int getID() {
		return ID;
	}

}
