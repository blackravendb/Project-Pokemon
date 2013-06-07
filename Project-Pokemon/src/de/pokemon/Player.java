package de.pokemon;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player extends Entity {
	
	private int standCounterUp = 0; //Counter für standanimationen; sobald 5 Durchläufe abgefangen wurden wird das Ergebnis an Entity weitergeleitet
	private int standCounterDown = 0;
	private int standCounterLeft = 0;
	private int standCounterRight = 0;
	
	private enum standAnimationRet {STAND, RUN, WAIT}; //Rückgabewert für Methode standAnimation
	private standAnimationRet move; //Variable zum Zwischenspeichern des Rückgabewerts standAnimation
	
	Player(int posX, int posY) throws SlickException {
		super(posX, posY, Core.tileSize, Core.tileSize*2, "res/player2.png", 1, 1);
	}
	/**Methode für überprüfung ob eine Standanimation oder eine Laufanimtaion ausgeführt werden soll*/
	private standAnimationRet standAnimation (Input input){
		if(input.isKeyDown(Input.KEY_A)){
			if(standCounterLeft < 5){
				standCounterLeft++;
				return standAnimationRet.WAIT;
			}
			else{
				standCounterLeft = 0;
				return standAnimationRet.RUN;
			}
		}
		
		if(input.isKeyDown(Input.KEY_S)){
			if(standCounterDown < 5){
				standCounterDown++;
				return standAnimationRet.WAIT;
			}
			else{
				standCounterDown = 0;
				return standAnimationRet.RUN;
			}
		}
		
		if(input.isKeyDown(Input.KEY_D)){
			if(standCounterRight < 5){
				standCounterRight++;
				return standAnimationRet.WAIT;
			}
			else{
				standCounterRight = 0;
				return standAnimationRet.RUN;
			}
		}
		
		if(input.isKeyDown(Input.KEY_W)){
			if(standCounterUp < 5){
				standCounterUp++;
				return standAnimationRet.WAIT;
			}
			else{
				standCounterUp = 0;
				return standAnimationRet.RUN;
			}
		}
		return standAnimationRet.STAND;
	}
	
	public void renderPlayer() {
		super.renderEntity();
	}
	
	public void updatePlayer(Input input) {
/*		if(!(super.isRunning)){
			move = standAnimation(input);
			if(move == standAnimationRet.STAND){
				super.updateEntity(input, true);
			}
			else if(move == standAnimationRet.RUN){
				super.updateEntity(input);
			}
		}
		//Bewegung wird im Moment ausgeführt, Eingabe zu Entity durchschleifen
		else{
			super.updateEntity(input);
		}*/
		super.updateEntity(input);
		
	}
	
	



}
