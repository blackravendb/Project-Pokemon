package de.pokemon;

import java.awt.Font;
import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.gui.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;

public class NameMenu {
	
	public boolean showMenu;
	public boolean showTextField;
	public boolean update;
	
	TextField textField;
	
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
	
	/** height of one item in the menu*/
	private final int itemHeight = 32;
	
	private String[] mainItems = new String[5];
	/** the debug submenu items */
	private Polygon cursor;
	/** the points for the triangle in x,y order*/
	private final float[] cursorPoints = new float[]{0,0,6,6,0,12};
	
	public NameMenu(GameContainer gc,StateBasedGame game, String name1, String name2, String name3){
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
		showTextField = false;
		update = true;
		textField = new TextField(gc, gc.getDefaultFont(), 250, 86 - 10, 100, 20);
		mainItems[0] = "NAME";
		mainItems[1] = "NEUER NAME";
		mainItems[2] = name1;
		mainItems[3] = name2;
		mainItems[4] = name3;
		background = new Rectangle(x,y,width,mainItems.length*32);
		name = null;
		
	}
	
	public void update(Input input){
		
		if(update){
		if(showMenu){
			background.setHeight(mainItems.length*32);
		}
		if(input.isKeyPressed(Input.KEY_W)){ // nach oben
			if(cursor.getCenterY() > 86){
				cursor.setY(cursor.getY() - 32);
			}
			else{
				cursor.setCenterY(182);
			}
		}else if(input.isKeyPressed(Input.KEY_S)){ // nach unten
			if(cursor.getCenterY() < background.getMaxY()- 64){
				cursor.setY(cursor.getY() + 32);
			}
			else{
				resetCursor();
			}
		}
		if(input.isKeyPressed(Input.KEY_ENTER)){
		if(showMenu){
			if(cursor.getCenterY() == 86){ //Name
				showTextField = true;
				textField.setInput(input);
				textField.setAcceptingInput(true);
			}else if(cursor.getCenterY() == 118){ //Rot
				name = mainItems[2];
				resetCursor();
			}else if(cursor.getCenterY() == 150){ //Ash
				name = mainItems[3];
				resetCursor();
			}else if(cursor.getCenterY() == 182){ //Jack
				name = mainItems[4];
				resetCursor();
			}
		}
	}
	}
	}
	
	public void render(Graphics g){
	
		if(showMenu){
			g.setColor(Color.white);
			//g.drawString("Cursor: X = " + cursor.getCenterX(), 50, 400);
			//g.drawString("Cursor: Y = " + cursor.getCenterY(), 50, 450);
			g.drawRect(x, y, width, height);
			for(int i = 0, j = y - 15; i < mainItems.length; i++, j += 32){
				g.drawString(mainItems[i], background.getCenterX()-g.getFont().getWidth(mainItems[i])/2, j-g.getFont().getHeight(mainItems[i])/2);
			}
		}
		if(showTextField){
			textField.render(gc, g);
			textField.setBackgroundColor(Color.white);
			textField.setTextColor(Color.black);
			//update = false;
		}
		g.setColor(Color.white);
		g.fill(cursor);
	}

private void resetCursor(){
	cursor.setLocation(x+16, 80);
	}
}
