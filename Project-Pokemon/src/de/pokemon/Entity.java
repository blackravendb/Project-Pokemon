package de.pokemon;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Entity {

	private int posX;
	private int posY;
	private int width;
	private int height;
	private int tileX;
	private int tileY;
	private int lastDir = 0; /** Angabe ueber letzte Bewegungsrichtung, wobei 0 = down, 1 = right, 2 = top, 3 = left entspricht */
	
	private Image image;
	
	Entity (int posX, int posY, int width, int height, String imagePath) throws SlickException{
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		
		image = new Image(imagePath);
		
		if(tileY % Core.tileSize == 0)
			tileY %= Core.tileSize;

		if(tileX % Core.tileSize == 0)
			tileX %= Core.tileSize;

	}
	
	public int getPosX(){
		return posX;
	}
	
	public int getPosY(){
		return posY;
	}
	
	public void setPosX(int posX){
		this.posX = posX;
	}
	
	public void setPosY(int posY){
		this.posY = posY;
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public void setHeight (int height){
		this.height = height;
	}
	
	public int getWidth (){
		return width;
	}
	
	public int getHeight (){
		return height;
	}
	
	public boolean moveX (int tileX){
		//TODO
		return true;
	}
	
	public boolean moveY (int tileY){
		//TODO
		return true;
	}
	
	private boolean isBlocked (int tileX, int tileY){
		//TODO
		return false;
	}
	
	public boolean collideWith (Entity me, Entity him){ //Fraglich ob benoetigt
		//TODO
		return false;
	}
	
	protected void renderEntity (){
		image.getSubImage(0, 0, width, height).draw(posX,posY);
	}
	
	public void updateEntity (Input input){
		
	}
	
	
}