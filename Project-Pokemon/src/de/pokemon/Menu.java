package de.pokemon;

import org.newdawn.slick.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class Menu extends BasicGameState{
	public static int ID;
	Image pokemon;
	public int pointx = 222;
	public int pointy = 265;
	
	public Menu(int state){
		ID = state;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		pokemon = new Image("res/url2.jpg");
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		g.setColor(Color.white);
		g.drawString("Menü", 270, 225);
		g.drawString("Neues Spiel", 235, 260);
		//Laden
		//Speichern
		//CREDITS!!!!!
		g.drawString("Spiel beenden", 235, 300);
		//g.drawString("Dev-Tools", 235, 340);
		g.fillOval(pointx, pointy, 10, 10, 10);
		g.drawRect(220, 250, 150, 250);//x,y, breite, höhe
		g.drawImage(pokemon, 100, 0);
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		
		Input input = gc.getInput();
		
			if(pointy >= 345){
				if(input.isKeyPressed(Input.KEY_DOWN)){
				pointy += 0;
				}
			}
			else if(input.isKeyPressed(Input.KEY_DOWN)){
				pointy += 40;
			}
		
			if(pointy <= 265){
				if(input.isKeyPressed(Input.KEY_UP)){
					pointy += 0;
				}
			}
			else if(input.isKeyPressed(Input.KEY_UP)){
				pointy -= 40; 
			
				}
		
			if(pointy == 265){ // Spiel starten
				if(input.isKeyPressed(Input.KEY_ENTER)){
					sbg.getState(Core.play).init(gc, sbg); // init state to create a new game
					sbg.enterState(Core.play);	
				}
			}
		
		
	
			if(pointy == 305){ //Spiel beenden
				if(input.isKeyPressed(Input.KEY_ENTER)){
				
					System.exit(0);
				
				}
			}
		
			if(pointy == 345){ //Dev-Tools einblenden
				if(input.isKeyPressed(Input.KEY_ENTER)){
				
					//sbg.enterState(2);
					
			}
		}		
}
	
	public int getID(){
		return ID;
	}
}
