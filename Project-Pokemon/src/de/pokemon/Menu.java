package de.pokemon;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class Menu extends BasicGameState{
	public static int ID;
	
	Image pokemon;
	Image bild;
	
	public int imagex;
	public int imagey;
	
	public int imagebildx;
	public int imagebildy;
	
	public int rectx;
	public int recty;
	public int rectbreite;
	public int recthöhe;
	
	public int pointx;
	public int pointy;
	
	public int menüx;
	public int menüy;
	
	public int neuesspielx;
	public int neuesspiely;
	
	public int spielladenx;
	public int spielladeny;
	
	public int spielfortsetzenx;
	public int spielfortsetzeny;
	
	public int spielbeendenx;
	public int spielbeendeny;
	
	private Polygon cursor;
	private final float[] cursorPoints = new float[]{0,0,6,6,0,12};
	
	public Menu(int state){
		ID = state;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		Sound.init();
		gc.setMusicVolume(0.05f);
		gc.setSoundVolume(0.05f);
		pokemon = new Image("res/Intro/Pokemon-Logo.png");
		cursor = new Polygon(cursorPoints); 
		bild = new Image("res/Intro/Pokemon-Bild.jpg");
		
		imagex = (gc.getWidth() - pokemon.getWidth())/2;
		imagey = 0;
		
		imagebildx = gc.getWidth() - bild.getWidth();
		imagebildy = gc.getHeight() - bild.getHeight();
				
		menüx = (gc.getWidth() - gc.getGraphics().getFont().getWidth("Menu"))/2;
		menüy = pokemon.getHeight() + 5;
		
		rectbreite = 150;
		recthöhe = gc.getHeight()/3;
		rectx = (gc.getWidth() - rectbreite)/2;
		recty = pokemon.getHeight() + 30;
		
		spielfortsetzenx = (gc.getWidth() - gc.getGraphics().getFont().getWidth("Continue Game"))/2;
		spielfortsetzeny = recty + 10;
		
		neuesspielx = (gc.getWidth() - gc.getGraphics().getFont().getWidth("New Game"))/2;
		neuesspiely = spielfortsetzeny + 40;
		
		pointx = spielfortsetzenx - 8;
		pointy = spielfortsetzeny + 4;
		
		cursor.setLocation(pointx, pointy);
		
		spielladenx = (gc.getWidth() - gc.getGraphics().getFont().getWidth("Load Game"))/2;
		spielladeny = neuesspiely + 40;
		
		spielbeendenx = (gc.getWidth() - gc.getGraphics().getFont().getWidth("Quit Game"))/2;;
		spielbeendeny = spielladeny + 40;
		
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		g.setColor(Color.white);
		g.drawImage(bild, imagebildx, imagebildy);
		g.drawString("Menu", menüx, menüy);
		g.drawString("Continue Game", spielfortsetzenx, spielfortsetzeny);
		g.drawString("New Game", neuesspielx, neuesspiely);
		g.drawString("Load Game", spielladenx, spielladeny);
		g.drawString("Quit Game", spielbeendenx, spielbeendeny);
		g.fill(cursor);
		g.drawRect(rectx, recty, rectbreite, recthöhe); //x,y, breite, höhe
		g.drawImage(pokemon, imagex, imagey);
		
		
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		
		Input input = gc.getInput();
		
		if(input.isKeyPressed(Input.KEY_S)){
			if(pointy == spielbeendeny + 4){
				pointy = spielfortsetzeny - 36;
			}
			if(pointy < spielbeendeny + 4){
				pointy += 40;
			}
		}
		
		if(input.isKeyPressed(Input.KEY_W)){
			if(pointy == spielfortsetzeny + 4){
				pointy = spielbeendeny + 44;
			}
			if(pointy > spielfortsetzeny + 4){
				pointy -= 40; 
			}
		}
		
		cursor.setLocation(pointx, pointy);
		
			if(pointy == spielfortsetzeny + 4){ //Spiel fortsetzen
				if(input.isKeyPressed(Input.KEY_ENTER)){
					
				sbg.enterState(Core.play);
				
				}
				
			}

			else if(pointy == neuesspiely + 4){ // neues Spiel
				if(input.isKeyPressed(Input.KEY_ENTER)){
					sbg.getState(Core.intro).init(gc, sbg);
					sbg.enterState(Core.intro);
					
					/*sbg.getState(Core.play).init(gc, sbg); // init state to create a new game
					sbg.enterState(Core.play);	*/
				}
			}
			
			else if(pointy == spielladeny + 4){ //Spiel laden
				if(input.isKeyPressed(Input.KEY_ENTER)){
					
					//TODO
					
				}
			}
	
			else if(pointy == spielbeendeny + 4){ //Spiel beenden
				if(input.isKeyPressed(Input.KEY_ENTER)){
				
					System.exit(0);
				
				}
			}
			input.clearKeyPressedRecord();
}
	
	public int getID(){
		return ID;
	}
}
