package de.pokemon;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class Menu extends BasicGameState{
	
	/** ID of the IntroState */
	public static int ID;
	
	/** bavarian flag which you see in the Menu */
	private Image logo;
	/** writing in the Menu*/
	private Image pokemon;
	/** image in the right bottom corner of the Menu*/
	private Image imagePokemon;
	
	/** true if logo is sliding*/
	private boolean sliding;
	/** */
	private int logoX;
	/** */
	private int logoY;
	/** */
	private int logoXEnd;
	
	/** x-coordinate of pokemon*/
	private int pokemonX;
	/** y-coordinate of pokemon*/
	private int pokemonY;
	
	/** x-coordinate of imagePokemon*/
	private int imagePokemonX;
	/** y-coordinate of imagePokemon*/
	private int imagePokemonY;
	
	/** x-coordinate of the frame which contains the menu items*/
	private int rectX;
	/** y-coordinate of the frame which contains the menu items*/
	private int rectY;
	/** width of the frame which contains the menu items*/
	private int rectWidth;
	/** height of the frame which contains the menu items */
	private int rectHeight;
	
	/** triangle in the menu*/
	private Polygon cursor;
	/** x-coordinate of cursor*/
	private int cursorX;
	/** y-coordinate of cursor*/
	private int cursorY;
	/** the points for the triangle in x,y order*/
	private final float[] cursorPoints = new float[]{0,0,6,6,0,12};
	
	/** x-coordinate of the string menu*/
	private int menuX;
	/** y-coordinate of the string menu*/
	private int menuY;
	
	/** x-coordinate of the string "Weida"*/
	private int continueX;
	/** y-coordinate of the string "Weida"*/
	private int continueY;
	
	/** x-coordinate of the string "Neis Spui"*/
	private int newGameX;
	/** y-coordinate of the string "Neis Spui"*/
	private int newGameY;
	
	/** x-coordinate of the string "Lon"*/
	private int loadGameX;
	/** y-coordinate of the string "Lon"*/
	private int loadGameY;
	
	/** x-coordinate of the string "Servus!"*/
	private int quitX;
	/** y-coordinate of the string "Servus!"*/
	private int quitY;
	
	/** true if music should start*/
	private boolean musicStart;
	
	public Menu(int state){
		ID = state;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		Sound.init();
		gc.setMusicVolume(0.05f);
		gc.setSoundVolume(0.05f);
		
		logo = new Image("res/Intro/BayrischLogo.png");
		pokemon = new Image("res/Intro/Pokemon-Logo.png");
		cursor = new Polygon(cursorPoints); 
		imagePokemon = new Image("res/Intro/Pokemon-Bild.jpg");
		
		sliding = true;
		
		pokemonX = (gc.getWidth() - pokemon.getWidth())/2;
		pokemonY = 0;
		
		imagePokemonX = gc.getWidth() - imagePokemon.getWidth();
		imagePokemonY = gc.getHeight() - imagePokemon.getHeight();
		
		logoX = gc.getWidth() + 50;
		logoY = pokemonY + 90;
		logoXEnd = pokemonX + imagePokemon.getWidth() - 190;
		
		menuX = (gc.getWidth() - gc.getGraphics().getFont().getWidth("Menu"))/2;
		menuY = pokemon.getHeight() + 5;
		
		rectWidth = 150;
		rectHeight = gc.getHeight()/3;
		rectX = (gc.getWidth() - rectWidth)/2;
		rectY = pokemon.getHeight() + 30;
		
		continueX = (gc.getWidth() - gc.getGraphics().getFont().getWidth("Weida"))/2;
		continueY = rectY + 10;
		
		newGameX = (gc.getWidth() - gc.getGraphics().getFont().getWidth("Neis Spui"))/2;
		newGameY = continueY + 40;
		
		cursorX = continueX - 45;
		cursorY = continueY + 4;
		
		cursor.setLocation(cursorX, cursorY);
		
		loadGameX = (gc.getWidth() - gc.getGraphics().getFont().getWidth("Lon"))/2;
		loadGameY = newGameY + 40;
		
		quitX = (gc.getWidth() - gc.getGraphics().getFont().getWidth("Servus!"))/2;;
		quitY = loadGameY + 40;
		
		musicStart = true;
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		g.setColor(Color.white);
		g.drawImage(imagePokemon, imagePokemonX, imagePokemonY);
		g.drawString("Menu", menuX, menuY);
		g.drawString("Weida", continueX, continueY);
		g.drawString("Neis Spui", newGameX, newGameY);
		g.drawString("Lon", loadGameX, loadGameY);
		g.drawString("Servus!", quitX, quitY);
		g.fill(cursor);
		g.drawRect(rectX, rectY, rectWidth, rectHeight); //x,y, breite, höhe
		g.drawImage(pokemon, pokemonX, pokemonY);
		g.drawImage(logo, logoX, logoY);
		
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		
		Input input = gc.getInput();
		
		if(musicStart){
			Sound.audioMenu.playAsMusic(1.0f, 1.0f, true);
			musicStart = false;
		}
		
		if(logoX > logoXEnd && sliding == true){ // Logo Animation
			logoX -= 4;
		}
		else{
			sliding = false;
		}
		
		if(sliding == false){
		if(input.isKeyPressed(Input.KEY_S)){
			if(cursorY == quitY + 4){
				cursorY = continueY - 36;
			}
			if(cursorY < quitY + 4){
				cursorY += 40;
			}
		}
		
		if(input.isKeyPressed(Input.KEY_W)){
			if(cursorY == continueY + 4){
				cursorY = quitY + 44;
			}
			if(cursorY > continueY + 4){
				cursorY -= 40; 
			}
		}
		
		cursor.setLocation(cursorX, cursorY);
		
		if(input.isKeyPressed(Input.KEY_ENTER)){
			Sound.audioMenu.stop();
			if(cursorY == continueY + 4){ //Spiel fortsetzen
				Sound.audioTextBox.playAsSoundEffect(1.0f, 3.0f, false);	
				sbg.enterState(Core.play);
				}

			else if(cursorY == newGameY + 4){ // neues Spiel
					Sound.audioTextBox.playAsSoundEffect(1.0f, 3.0f, false);
					sbg.getState(Core.intro).init(gc, sbg);
					sbg.enterState(Core.intro);
				}
			
			else if(cursorY == loadGameY + 4){ //Spiel laden
					Sound.audioTextBox.playAsSoundEffect(1.0f, 3.0f, false);
					//TODO
			}
	
			else if(cursorY == quitY + 4){ //Spiel beenden
					Sound.audioTextBox.playAsSoundEffect(1.0f, 3.0f, false);
					System.exit(0);
			}
		}
			input.clearKeyPressedRecord();
	}
}
	
	public int getID(){
		return ID;
	}
}
