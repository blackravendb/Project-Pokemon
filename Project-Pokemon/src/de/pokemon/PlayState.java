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
	/** reference for the*/
	Input input;

	public PlayState(int id){
		ID = id;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		//player = new Rectangle(map.getSpawn("player").x,map.getSpawn("player").y, 32, 32);
		player = new Player(32,32);
		map = new Map("res/world/testmap.tmx");
		camera = new Camera(container, map);
		camera.centerOn(player);
		menu = new InGameMenu(container,game);
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {

		camera.drawMap();
		
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
		// DRAW THE HUD AND MENU AFTER UNTRANSLATEGRAPHIS
		
		if(menu.showPosition){
			map.showPlayerPosition(g, player.getPosX(), player.getPosY(),player.getTileX(),player.getTileY());
			//g.drawString(map.getCoordinates(player.getPosX(), player.getPosY(),player.getTileX(),player.getTileY()), 10, 30);
		}
		if(menu.showMenu){
			menu.render(g);
		}

	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		input = gc.getInput();

		//if menu is not open yet process normal input 
		if(!menu.showMenu){
			player.updatePlayer(input);			
			camera.centerOn(player);

			//TODO check boolean if not moving 
//			if(map.getName().equals("Alabasta")){
//				if(map.getEntrance("house").x == (int) player.getX() && map.getEntrance("house").y == (int) player.getY() ){
//					System.out.println("ENTERED HOUSE");
//					map = new Map("res/world/Level_1.tmx");
//					camera = new Camera(gc,map);
//					player = new Rectangle(96,96,32,32);
//				}
//			}

			if(input.isKeyPressed(Input.KEY_ESCAPE)){
				menu.showMenu = true;
			}
			
		}else{
			menu.update(input);
			
		}
		
		input.clearKeyPressedRecord();	
	}

	@Override
	public int getID() {
		return ID;
	}

}
