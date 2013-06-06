package de.pokemon;

import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

public class NameMenu {
	
	public boolean showMenu;
	public String name;
	/** Position of the Rect*/
	public int x;
	public int y;
	
	public int cursorx;
	public int cursory;
	/** width and height of the Rect.*/
	private int width;
	private int height;
	/** the game, needed to enter states*/
	private StateBasedGame sbg;
	/** the container holding the game*/
	private GameContainer gc;
	/** the main menu items */
	/** the main-menu background*/
	private Rectangle background;
	
	private final String[] mainItems = {"NAME", "NEUER NAME", "ROT", "ASH", "JACK"};
	/** the debug submenu items */
	private Polygon cursor;
	/** the points for the triangle in x,y order*/
	private final float[] cursorPoints = new float[]{0,0,6,6,0,12};
	
	public NameMenu(GameContainer gc,StateBasedGame game){
		this.gc = gc;
		this.sbg = game;
		x = gc.getWidth()/8;
		y = gc.getHeight()/7;
		width = gc.getWidth()/4;
		height = gc.getHeight()/3;
		cursor = new Polygon(cursorPoints);
		cursorx = x+16;
		cursory = 80;
		cursor.setLocation(cursorx , cursory);
		showMenu = false;
		background = new Rectangle(x,y,width,mainItems.length*32);
		
	}
	
	public void update(Input input){
		if(showMenu){
			background.setHeight(mainItems.length*32);
		}
		if(input.isKeyPressed(Input.KEY_W)){
			if(cursor.getCenterY() > 86){
				cursor.setY(cursor.getY() - 32);
			} 
		}else if(input.isKeyPressed(Input.KEY_S)){
			if(cursor.getCenterY() < background.getMaxY()- 64){
				cursor.setY(cursor.getY() + 32);
			}
		}
		if(input.isKeyPressed(Input.KEY_ENTER)){
		if(showMenu){
			if(cursor.getCenterY() == 48){ //Name
				//sbg.enterState(Core.name);
			}else if(cursor.getCenterY() == 80){ //Rot
				name = "Rot";
				//TODO weiter?
			}else if(cursor.getCenterY() == 112){ //Ash
				name = "Ash";
				//TODO weiter?
			}else if(cursor.getCenterY() == 144){ //Jack
				name = "Jack";
				//TODO weiter?
			}
		}
	}
	}
	
	public void render(Graphics g){
		if(showMenu){
			g.setColor(Color.white);
			g.drawString("Cursor: X = " + cursor.getCenterX(), 50, 400);
			g.drawString("Cursor: Y = " + cursor.getCenterY(), 50, 450);
			g.drawRect(x, y, width, height);
			for(int i = 0, j = y - 15; i < mainItems.length; i++, j += 32){
				g.drawString(mainItems[i], background.getCenterX()-g.getFont().getWidth(mainItems[i])/2, j-g.getFont().getHeight(mainItems[i])/2);
			}
		}
		g.setColor(Color.white);
		g.fill(cursor);
	}
}
