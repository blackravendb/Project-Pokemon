package de.pokemon;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

public class InGameMenu {

	Rectangle cursor, background;
	boolean showMenu;
	private GameContainer gc;
	private final String[] items = {"Menu", "Resume", "Debug", "Save", "Exit"};

	public InGameMenu(GameContainer gc){
		this.gc = gc;
		cursor = new Rectangle(gc.getWidth()-gc.getWidth()/5,32,128,32);
		background = new Rectangle(gc.getWidth()-gc.getWidth()/5,0,gc.getWidth()/5,items.length*32);

		showMenu = false;
	}

	public void update(Input input){

		if(input.isKeyPressed(Input.KEY_W)){
			if(cursor.getMinY() > 32){
				cursor.setY(cursor.getY() - 32);
			}
		}else if(input.isKeyPressed(Input.KEY_S)){
			if(cursor.getMaxY() < background.getMaxY()){
				cursor.setY(cursor.getY() + 32);
			}	
		}

		if(input.isKeyPressed(Input.KEY_ENTER)){
			if(cursor.getY() == 32){
				showMenu = false;
			}else if(cursor.getY() == 64){
				if(!freeWorldState.debug){
					freeWorldState.debug = true;
					gc.setShowFPS(true);
				}
				else{
					freeWorldState.debug = false;
					gc.setShowFPS(false);
				}
			}else if(cursor.getY() == 96){
				//TODO Saving the game
			}else if(cursor.getY() == 128){
				//TODO Dialog for exit
				gc.exit();
			}
		}
		

	}

	public void render(Graphics g){
		g.draw(background);
		g.fill(background);
		g.setColor(Color.gray);
		g.draw(cursor);
		g.fill(cursor);
		g.setColor(Color.black);
		for(int i = 0, j = 0; i < items.length; i++, j += 32){
			g.drawString(items[i], background.getCenterX()-g.getFont().getWidth(items[i])/2, j);
		}
	}
}
