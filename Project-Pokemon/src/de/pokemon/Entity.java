package de.pokemon;

import java.io.IOException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Entity {
	private int posX;
	private int posY;
	private int width;
	private int height;
	private int blockedX;
	private int blockedY;
	private int tileX;
	private int tileY;
	private int moveSpeed = 2;
	
	/** Angabe ueber letzte Bewegungsrichtung*/
	protected enum LastDir {DOWN, LEFT, UP, RIGHT, NULL};
	protected LastDir lastDir = LastDir.DOWN;
	protected LastDir standDir; //Variable für Standanimation letzte bewegungsrichtung
	
	private Image image;
	
	private boolean isRunning = false;
	protected boolean isStanding = true;
	private boolean standAnimation = false; //Boolean ob Standanimation ausgeführt werden soll
	
	private Image charImages[][] = new Image[4][4];
	/**Laufanimationen für verschiedene Richtungen*/
	private Animation aniLeft, aniUp, aniRight, aniDown;
	/**Animationsgeschwindigkeit für Laufanimation*/
	private int aniDelta = 150;
	/**Standanimationen für verschiedene Richtungen*/
	private Animation aniStandLeft = new Animation();
	private Animation aniStandUp = new Animation();
	private Animation aniStandRight = new Animation();
	private Animation aniStandDown = new Animation();
	private int standAniDelta = 200; //Dauer der Standanimation
	
	private Audio audioSolid;
	private Audio audioBackground;
	
	private boolean blockedDelta = true;
	
	Entity (int posX, int posY, int width, int height, String imagePath, int blockedX, int blockedY) throws SlickException, IOException{
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.blockedX = blockedX;
		this.blockedY = blockedY;
		
		image = new Image(imagePath);
		
		tileY = posY / Core.tileSize + 1;

		tileX = posX / Core.tileSize;
		System.out.println(tileX +" blub "+ tileY);
		//Aktuelle Position blocken
		Map.setBlocked(tileX, tileY, true);
			
		//Animationsbilder laden (Laufanimationen)
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
		
		aniLeft = new Animation(charImages[1],aniDelta);
		aniRight = new Animation(charImages[3],aniDelta);
		aniUp = new Animation(charImages[2],aniDelta);
		aniDown = new Animation(charImages[0],aniDelta);
		
		//Animatinsbilder Laden (Standanimationen)
		//LEFT
		aniStandLeft.addFrame(charImages[1][0], standAniDelta);
		aniStandLeft.addFrame(charImages[1][1], standAniDelta);
		aniStandLeft.setLooping(false);
		
		//RIGHT
		aniStandRight.addFrame(charImages[3][0],standAniDelta);
		aniStandRight.addFrame(charImages[3][1],standAniDelta);
		aniStandRight.setLooping(false);
		
		//UP
		aniStandUp.addFrame(charImages[2][0],standAniDelta);
		aniStandUp.addFrame(charImages[2][1],standAniDelta);
		aniStandUp.setLooping(false);
		
		//DOWN
		aniStandDown.addFrame(charImages[0][0],standAniDelta);
		aniStandDown.addFrame(charImages[0][1],standAniDelta);
		aniStandDown.setLooping(false);
		
		//Sounds
		audioSolid =  AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/solid.wav"));
		audioBackground = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/105_mishiro_town.wav"));
		
	//	audioBackground.playAsMusic(1.0f, 1.0f, true);
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
	
	public void moveX (int tileX){
		//Überprüft, ob Player bereits in Bewegung ist. Wenn ja wird Bewegung bis zum nächsten Tile vortgesetzt
		if(isRunning){
			if(posX % Core.tileSize == 0){
				isRunning = false;
				Map.setBlocked(this.tileX, tileY, false);
				this.tileX=(tileX < 0) ? this.tileX-1 : this.tileX+1;
			}
			else{
				posX = (tileX<0) ? posX - moveSpeed : posX + moveSpeed;
			}
		}
		//Player noch nicht in Bewegung, soll aber Schritte machen
		else if (tileX != 0){
			//Überprüfen ob nächster Tile blocked ist
			if(!Map.isBlocked(this.tileX+tileX, tileY)){
				Map.setBlocked(this.tileX+tileX, tileY, true);
				lastDir = (tileX<0) ? LastDir.LEFT : LastDir.RIGHT;
				isRunning = true;
				isStanding = false;
				posX = (tileX<0) ? posX-moveSpeed : posX+moveSpeed;
			}
			//Entity will gegen einen Soliden Block laufen
			else{
				lastDir = (tileX<0) ? LastDir.LEFT : LastDir.RIGHT;
				isStanding = false;

				if(aniLeft.getFrame() == 1 || aniRight.getFrame() == 1){
					blockedDelta =true;
				}
				if(aniLeft.getFrame() == 2 && blockedDelta == true || aniRight.getFrame() == 2 && blockedDelta == true){
					audioSolid.playAsSoundEffect(1.0f, 1.0f, false);
					blockedDelta = false;
				}
			}
		}
		//Standanimation
		else {
			aniLeft.restart();
			aniRight.restart();
			isStanding = true;
		}
	}
	
	public void moveY (int tileY){
		//Überprüft, ob Player bereits in Bewegung ist. Wenn ja wird Bewegung bis zum nächsten Tile vortgesetzt
		if(isRunning){
			if(posY % Core.tileSize == 0){
				isRunning = false;
				Map.setBlocked(tileX, this.tileY, false);
				this.tileY=(tileY < 0) ? this.tileY-1 : this.tileY+1;
			}
			else{
				posY = (tileY<0) ? posY - moveSpeed : posY + moveSpeed;
			}
		}
		//Entity noch nicht in Bewegung, soll aber Schritte machen
		else if (tileY != 0){
			//Überprüfen ob nächster Tile blocked ist
			if(!Map.isBlocked(tileX, this.tileY+tileY)){
				Map.setBlocked(tileX, tileY+this.tileY, true);
				lastDir = (tileY<0) ? LastDir.UP : LastDir.DOWN;
				isRunning = true;
				isStanding = false;
				posY = (tileY<0) ? posY-moveSpeed : posY+moveSpeed;
			}
			//Entity will gegen einen Soliden Block laufen
			else{
				lastDir = (tileY<0) ? LastDir.UP : LastDir.DOWN;
				isStanding = false;
				
				if(aniDown.getFrame() == 1 || aniUp.getFrame() == 1){
					blockedDelta =true;
				}
				if(aniDown.getFrame() == 2 && blockedDelta == true || aniUp.getFrame() == 2 && blockedDelta == true){
					audioSolid.playAsSoundEffect(1.0f, 1.0f, false);
					blockedDelta = false;
				}

			}
		}
		//Standanimation
		else {
			aniLeft.restart();
			aniRight.restart();
			isStanding = true;
		}
	}
	
	private boolean isBlocked (int tileX, int tileY){
		//TODO
		return false;
	}
	
	public boolean collideWith (Entity me, Entity him){ //Fraglich ob benoetigt
		//TODO
		return false;
	}
	/**Wo kann ich die Methode nutzen -.-*/
	//TODO
	private int getDirection(Input input){
		if(input.isKeyPressed(Input.KEY_A))
			return 30;
		if(input.isKeyPressed(Input.KEY_W))
			return 17;
		if(input.isKeyPressed(Input.KEY_D))
			return 32;
		if(input.isKeyPressed(Input.KEY_S))
			return 31;
		return 0;
	}
	
	protected void renderEntity (){
		if(!isStanding){
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
		else if (standAnimation){
			if (standDir == LastDir.LEFT)
				aniStandLeft.draw(posX, posY);
			else if (standDir == LastDir.UP)
				aniStandUp.draw(posX, posY);
			else if (standDir == LastDir.RIGHT)
				aniStandRight.draw(posX, posY);
			else if (standDir == LastDir.DOWN)
				aniStandDown.draw(posX, posY);
			
			if(standDir == LastDir.UP && aniStandUp.isStopped() || standDir == LastDir.DOWN && aniStandDown.isStopped() || standDir == LastDir.LEFT && aniStandLeft.isStopped() || standDir == LastDir.RIGHT && aniStandRight.isStopped()){
				standAnimation = false;
				aniStandUp.restart();
				aniStandDown.restart();
				aniStandRight.restart();
				aniStandLeft.restart();
			}
		}
		else{
			//Stehbild
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
	
	public void updateEntity (Input input, boolean standAnimation, int steps){
		this.standAnimation = standAnimation;
		
		if(standDir == LastDir.LEFT)
			lastDir = LastDir.LEFT;
		else if(standDir == LastDir.RIGHT)
			lastDir = LastDir.RIGHT;
		else if (standDir == LastDir.UP)
			lastDir = LastDir.UP;
		else if (standDir == LastDir.DOWN)
			lastDir = LastDir.DOWN;

		this.updateEntity(input, steps);
	}
	
	/**Update Methode des Objektes Entity
	 * @param input (Input) Key Listener Variable
	 * @param steps (int) Wie viele Tiles soll die Entity laufen
	 * @return void*/
	public void updateEntity (Input input, int steps){
		//Ist Entity bereits in bewegung
		if(isRunning){
			if(lastDir == LastDir.LEFT)
				moveX(-steps);
			else if (lastDir == LastDir.RIGHT)
				moveX(steps);
			else if (lastDir == LastDir.DOWN)
				moveY(steps);
			else if(lastDir == LastDir.UP)
				moveY(-steps);
		}
		//Noch nicht in Bewegung jedoch Key Listener eingabe
		else {
			if(input.isKeyDown(Input.KEY_A))
				moveX(-steps);
			else if(input.isKeyDown(Input.KEY_D))
				moveX(steps);
			else if(input.isKeyDown(Input.KEY_S))
				moveY(steps);
			else if(input.isKeyDown(Input.KEY_W))
				moveY(-steps);
			//Keine Key Listener eingabe, Animationen werden zurückgesetzt; Bewegungszustand auf stehen gesetzt
			else{
				isRunning = false;
				isStanding = true;
				aniLeft.restart();
				aniUp.restart();
				aniRight.restart();
				aniDown.restart();
			}
		}
/*		//Überprüft, ob Player bereits in Bewegung ist. Wenn ja wird bewegung bis zum nächsten Tile vortgesetzt
		if(isRunning){//TODO
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
			isStanding = false;
			posX -= moveSpeed;
		}
		
		else if(input.isKeyDown(Input.KEY_W)){
			lastDir = LastDir.UP;
			isRunning = true;
			isStanding = false;
			posY -= moveSpeed;
		}
		
		else if(input.isKeyDown(Input.KEY_D)){
			lastDir = LastDir.RIGHT;
			isRunning = true;
			isStanding = false;
			posX += moveSpeed;
		}
		
		else if(input.isKeyDown(Input.KEY_S)){
			lastDir = LastDir.DOWN;
			isRunning = true;
			isStanding = false;
			posY += moveSpeed;
		}
		else {
			aniLeft.restart();
			aniUp.restart();
			aniRight.restart();
			aniDown.restart();
			isStanding = true;
		}*/
	}
}