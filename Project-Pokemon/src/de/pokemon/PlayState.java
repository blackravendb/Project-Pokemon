package de.pokemon;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

public class PlayState extends BasicGameState {
	/** does anyone actually read this? */
	private GameContainer gc;
	
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
	
	/** used to sum up delta times and to run the game stable at FPS != 60 */
	private int sum = 0;
	
	/** the textbox used for dialogues*/
	private TextBox textBox;
	
	/** if the textbox is currently displayed */
	private boolean showTextBox = false;


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
		this.gc = gc;
		ResourceManager.init();
		event = new EventManager(this);
		map = new Map("House", event);
		player = new Player(map.getSpawn("player").x, map.getSpawn("player").y, event);
		camera = new Camera(gc, map);
		camera.centerOn(player);
		menu = new InGameMenu(gc,game);
		textBox = new TextBox("",Color.white,Color.black,gc);
		input = gc.getInput();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {

		camera.drawMap();

		camera.translateGraphics(g);
		if(map.hasWater()){
			map.renderWater();
		}
		//draw player and all entities here
		player.renderPlayerBody();
		map.renderNpcs();
		player.renderPlayerHead();

		camera.untranslateGraphics(g);
		camera.drawForeground();
		camera.translateGraphics(g);
		//DRAW EVERYTHING AT NORMAL POSITION ON THE MAP AFTER TRANSLATEGRAPHICS
		gc.setShowFPS(menu.isShowFps());

		if(menu.isShowGrid()){
			map.showGrid(g);	
		}

		if(menu.isShowBlocked()){
			map.showBlocked(g);
		}

		camera.untranslateGraphics(g);
		// DRAW THE HUD-TEXT, MENU AND TEXTBOX AFTER UNTRANSLATEGRAPHICS	

		if(menu.isShowPosition()){
			map.showPlayerPosition(g, player.getPosX(), player.getPosY(),player.getTileX(),player.getTileY());
		}
		if(menu.isShowMenu() || menu.isSliding()){
			menu.render(g);
		}
		if(isShowTextBox()){ //TODO
			textBox.render(g);
		}

	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		if(nextUpdate(delta)){
			//if menu is not open and not sliding in or out process normal input
			if(!showTextBox){//TODO
				if(!menu.isShowMenu() && !menu.isSliding()){
					player.updatePlayer(input);			
					map = map.update(player,event);
					if (map != camera.map) {
						camera = new Camera(gc, map);
					}
					camera.centerOn(player);

					if(player.isStanding){
						if(input.isKeyPressed(Input.KEY_ESCAPE)){
							Sound.audioTextBox.playAsSoundEffect(1.0f, 3.0f, false);
							menu.setSliding(true);
						}
					}
				}
				menu.update(input);
			} else {
				textBox.update(input, delta);
				if(textBox.end()){
					setShowTextBox(false);
				}
			}
			map.updateNpcs(delta,!menu.isShowMenu() && !menu.isSliding() && !showTextBox);
			input.clearKeyPressedRecord();	
		}
	}

	@Override
	public int getID() {
		return ID;
	}

	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		Log.info("play is called");
		if(!Sound.audioInGame.playing()){
			Sound.audioInGame.loop();
		}
	}

	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		Sound.audioInGame.stop();
	}

	/**This method ensures that the game runs correct even when fps != 60
	 * 
	 * @param delta
	 * @return true, if the game should update
	 */
	private boolean nextUpdate(int delta){
		sum += delta;
		if(sum > 15){
			sum = 0;
			return true;
		}
		return false;
	}

	/**updates the textbox with new text 
	 * 
	 * @param text used for the textbox
	 */
	public void setDialogString(String text){
		textBox.setText(text,gc);
		setShowTextBox(true);
	}

	/**
	 * 
	 * @return true, if textbox is currently displayed
	 */
	public boolean isShowTextBox() {
		return showTextBox;
	}

	/** set the visibility of the textbox
	 * 
	 * @param showTextBox 
	 */
	public void setShowTextBox(boolean showTextBox) {
		this.showTextBox = showTextBox;
	}


}
