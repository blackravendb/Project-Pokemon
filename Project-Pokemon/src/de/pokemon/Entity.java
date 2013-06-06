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
	private int moveSpeed = 2;
	
	private static LastDir lastDir = LastDir.DOWN; /** Angabe ueber letzte Bewegungsrichtung, wobei 0 = down, 1 = right, 2 = top, 3 = left entspricht */

	
	private enum LastDir {DOWN, LEFT, UP, RIGHT};
	
	private Image image;
	
	private Boolean isRunning = false;
	
	private Image charImages[][] = new Image[4][4];
	private Animation aniLeft, aniUp, aniRight, aniDown;
	
	Entity (int posX, int posY, int width, int height, String imagePath) throws SlickException{
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		
		image = new Image(imagePath);
		
			tileY = posY / Core.tileSize + 1;

			tileX = posX / Core.tileSize;
		
		//Animationsbilder laden
		//Down
		charImages[0][0]= image.getSubImage(2*width, 2*height, width, height);
		charImages[0][1]= image.getSubImage(2*width, 1*height, width, height);
		charImages[0][2]= image.getSubImage(2*width, 3*height, width, height);
		charImages[0][3]= charImages[0][1];
		
		//LEFT
		charImages[1][0]= image.getSubImage(0*width, 1*height, width, height);
		charImages[1][1]= image.getSubImage(0*width, 2*height, width, height);
		charImages[1][2]= image.getSubImage(0*width, 3*height, width, height);
		charImages[1][3]= charImages[1][1];
		
		//UP
		charImages[2][0]= image.getSubImage(2*width, 0*height, width, height);
		charImages[2][1]= image.getSubImage(0*width, 0*height, width, height);
		charImages[2][2]= image.getSubImage(1*width, 3*height, width, height);
		charImages[2][3]= charImages[2][1];
		
		//RIGHT
		charImages[3][0]= image.getSubImage(1*width, 1*height, width, height);
		charImages[3][1]= image.getSubImage(1*width, 0*height, width, height);
		charImages[3][2]= image.getSubImage(1*width, 2*height, width, height);
		charImages[3][3]= charImages[3][1];
		
		aniLeft = new Animation(charImages[1],200);
		aniRight = new Animation(charImages[3],200);
		aniUp = new Animation(charImages[2],200);
		aniDown = new Animation(charImages[0],200);

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
	
	public int getTileX(){
		return tileX;
	}
	
	public int getTileY(){
		return tileY;
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
				aniUp.draw(posX, posY);
				
			}
			else if(lastDir == LastDir.RIGHT){
				aniRight.draw(posX, posY);
				
			}
			else if(lastDir == LastDir.DOWN){
				aniDown.draw(posX, posY);
				
				
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
		if(isRunning){
			if(lastDir == LastDir.DOWN){
				if(posY % Core.tileSize == 0){
					isRunning = false;
					tileY++;
				}
				else {
					posY += moveSpeed;
				}
			}
			else if(lastDir == LastDir.UP){
				if(posY % Core.tileSize == 0){
					isRunning = false;
					tileY--;
				}
				else {
					posY -= moveSpeed;
				}
				
			}
			else if (lastDir == LastDir.LEFT){
				if(posX % Core.tileSize == 0){
					isRunning = false;
					tileX--;
				}
				else {
					posX -= moveSpeed;
				}
			}
			else if(lastDir == LastDir.RIGHT){
				if(posX % Core.tileSize == 0){
					isRunning = false;
					tileX++;
				}
				else {
					posX += moveSpeed;
				}
			}
		}
		else if(input.isKeyDown(Input.KEY_A)){
			lastDir = LastDir.LEFT;
			isRunning = true;
			posX -= moveSpeed;
		}
		
		else if(input.isKeyDown(Input.KEY_W)){
			lastDir = LastDir.UP;
			isRunning = true;
			posY -= moveSpeed;
		}
		
		else if(input.isKeyDown(Input.KEY_D)){
			lastDir = LastDir.RIGHT;
			isRunning = true;
			posX += moveSpeed;
		}
		
		else if(input.isKeyDown(Input.KEY_S)){
			lastDir = LastDir.DOWN;
			isRunning = true;
			posY += moveSpeed;
		}
	}
}