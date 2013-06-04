package de.pokemon;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class DevTools extends BasicGameState{

	public int pointx = 10;
	public int pointy = 54;

	public int pointx1 = 25;
	public int pointy1 = 54;

	boolean transparent = true;
	boolean an = false;

	public DevTools(int state){

	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{


	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{

		g.setColor(Color.white);
		g.drawString("Dev-Tools:", 270, 10);
		g.drawString("FPS anzeigen ", 40, 50);
		g.drawString("Spielerkoordinaten anzeigen ", 40, 90);
		g.setColor(Color.red);
		g.fillOval(25, 54, 10, 10, 10);
		g.fillOval(25, 94, 10, 10, 10);
		if(transparent == true){
			g.setColor(Color.transparent);
		}
		else g.setColor(Color.green);
		g.fillOval(pointx1, pointy1, 10, 10, 10);
		g.setColor(Color.white);
		g.fillOval(pointx, pointy, 10, 10, 10);


	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{

		Input input = gc.getInput();

		if(input.isKeyPressed(Input.KEY_ESCAPE)){

			sbg.enterState(0);

		}

		if(pointy >= 130){
			if(input.isKeyPressed(Input.KEY_DOWN)){
				pointy += 0;
			}
		}
		else if(input.isKeyPressed(Input.KEY_DOWN)){
			pointy += 40;
		}

		if(pointy <= 54){
			if(input.isKeyPressed(Input.KEY_UP)){
				pointy += 0;
			}
		}
		else if(input.isKeyPressed(Input.KEY_UP)){
			pointy -= 40; 

		}

		if(pointy == 54 && an == false){ // FPS anzeigen

			if(input.isKeyPressed(Input.KEY_ENTER)){

				transparent = false;
				gc.setShowFPS(true);
				an = true;

			}
		}

		if(pointy == 54 && an == true){ //FPS ausblenden

			if(input.isKeyPressed(Input.KEY_ENTER) && an == true){ 

				transparent = true;
				gc.setShowFPS(false);
				an = false;

			}

		}

		if(pointy == 90){ //Koordinaten anzeigen
			if(input.isKeyPressed(Input.KEY_ENTER)){



			}
		}

	}

	public int getID(){
		return 2;
	}

}
