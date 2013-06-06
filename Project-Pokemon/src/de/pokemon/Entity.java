package de.pokemon;

import org.newdawn.slick.Animation;
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
	private static LastDir lastDir = LastDir.DOWN; /** Angabe ueber letzte Bewegungsrichtung, wobei 0 = down, 1 = right, 2 = top, 3 = left entspricht */
	
	private enum LastDir {DOWN, LEFT, UP, RIGHT};
	
	private Image image;
	
	private Boolean isRunning = false;
	
	private Image charImages[][] = new Image[4][3];
	private Animation aniLeft;
	private Animation aniUp = new Animation();
	private Animation aniRight = new Animation();
	private Animation aniDown = new Animation();;
	
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
		
		//Animationsbilder laden
		for(int i = 0; i < 4; i++){
			for (int j = 0; j < 3; j++){
				charImages[i][j]= image.getSubImage(i*width, j*height, width, height);
			}
		}
		aniLeft = new Animation(charImages[0],200);
		aniLeft.setPingPong(true);

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
		//TODO
		if(isRunning){
			//Bewegungsanimation
			if(lastDir == LastDir.LEFT){
				aniLeft.draw(posX, posY);
			}
			else if(lastDir == LastDir.UP){
				
			}
			else if(lastDir == LastDir.RIGHT){
				
			}
			else if(lastDir == LastDir.DOWN){
				
				
			}
		}
		else{
			//Standanimation
			if(lastDir.equals(LastDir.LEFT)){
				image.getSubImage(0, 2*height, width, height).draw(posX,posY);
			}
			else if (lastDir.equals(LastDir.UP)){
				image.getSubImage(0, 0, width, height).draw(posX,posY);
			}
			else if (lastDir.equals(LastDir.RIGHT)){
				image.getSubImage(1*width, 0, width, height).draw(posX,posY);
			}
			else if (lastDir.equals(LastDir.DOWN)){
				image.getSubImage(2* width, 1*height, width, height).draw(posX,posY);
			}
		}
	}
	
	public void updateEntity (Input input){
		if(input.isKeyDown(Input.KEY_A)){
			lastDir = LastDir.LEFT;
			isRunning = true;
			posX -= 1;
		}
		
		else if(input.isKeyDown(Input.KEY_W)){
			lastDir = LastDir.UP;
			isRunning = true;
			posY -= 1;
		}
		
		else if(input.isKeyDown(Input.KEY_D)){
			lastDir = LastDir.RIGHT;
			isRunning = true;
			posX += 1;
		}
		
		else if(input.isKeyDown(Input.KEY_S)){
			lastDir = LastDir.DOWN;
			isRunning = true;
			posY += 1;
		}
		else{
			isRunning = false;
		}
	}
}