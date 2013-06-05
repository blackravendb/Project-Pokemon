package de.pokemon;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class Menu extends BasicGameState{
	public static int ID;
	Image pokemon;
	
	public int imagex;
	public int imagey;
	
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
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		pokemon = new Image("res/url2.jpg");
		cursor = new Polygon(cursorPoints); 
		
		imagex = gc.getWidth()/2 - pokemon.getWidth()/2;
		imagey = 0;
		
		menüx = (gc.getWidth() - gc.getGraphics().getFont().getWidth("Menu"))/2;
		menüy = pokemon.getHeight() + 5;
		
		rectbreite = 150;
		recthöhe = gc.getHeight()/2;
		rectx = (gc.getWidth() - rectbreite)/2;
		recty = pokemon.getHeight() + 20;
		
		spielfortsetzenx = rectx + 15;
		spielfortsetzeny = recty + 10;;
		
		neuesspielx = spielfortsetzenx;
		neuesspiely = spielfortsetzeny + 40;
		
		pointx = spielfortsetzenx - 8;
		pointy = spielfortsetzeny + 4;
		
		cursor.setLocation(pointx, pointy);
		
		spielladenx = spielfortsetzenx;
		spielladeny = neuesspiely + 40;
		
		spielbeendenx = spielfortsetzenx;
		spielbeendeny = spielladeny + 40;
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		g.setColor(Color.white);
		g.drawString("Menu", menüx, menüy);
		g.drawString("Continue Game", spielfortsetzenx, spielfortsetzeny);
		g.drawString("New Game", neuesspielx, neuesspiely);
		g.drawString("Load Game", spielladenx, spielladeny);
		g.drawString("Quit Game", spielbeendenx, spielbeendeny);
		g.fill(cursor);
		g.drawRect(rectx, recty, rectbreite, recthöhe); //x,y, breite, höhe
		g.drawImage(pokemon, imagex, imagey);
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		
		Input input = gc.getInput();
		
			if(pointy >= spielbeendeny + 4){
				if(input.isKeyPressed(Input.KEY_S)){
				pointy += 0;
				}
			}
			else if(input.isKeyPressed(Input.KEY_S)){
				pointy += 40;
			}
		
			if(pointy <= spielfortsetzeny + 4){
				if(input.isKeyPressed(Input.KEY_W)){
					pointy += 0;
				}
			}
			else if(input.isKeyPressed(Input.KEY_W)){
				pointy -= 40; 
			
				}
			
			cursor.setLocation(pointx, pointy);
		
			if(pointy == spielfortsetzeny + 4){ //Spiel fortsetzen
				if(input.isKeyPressed(Input.KEY_ENTER)){
					
				sbg.enterState(Core.play);
				
				}
				
			}

			else if(pointy == neuesspiely + 4){ // neues Spiel
				if(input.isKeyPressed(Input.KEY_ENTER)){
					sbg.getState(Core.play).init(gc, sbg); // init state to create a new game
					sbg.enterState(Core.play);	
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
