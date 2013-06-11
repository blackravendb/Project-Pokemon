package de.pokemon;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player extends Entity {
	
	private int standCounterUp = 0; //Counter für standanimationen; sobald standCounterDelta Durchläufe abgefangen wurden wird das Ergebnis an Entity weitergeleitet
	private int standCounterDown = 0;
	private int standCounterLeft = 0;
	private int standCounterRight = 0;
	
	private int standCounterDelta = 7; //Counter wie viele Frames gewartet wird, bis Bewegung der figur startet
	
	private enum standAnimationRet {STAND, RUN, WAIT}; //Rückgabewert für Methode standAnimation
	private standAnimationRet move; //Variable zum Zwischenspeichern des Rückgabewerts standAnimation
	
	Player(int posX, int posY) throws SlickException {
		super(posX, posY, Core.tileSize, Core.tileSize*2, "res/player2.png");
	}
	/**Methode für überprüfung ob eine Standanimation oder eine Laufanimtaion ausgeführt werden soll*/
	private standAnimationRet standAnimation (Input input){
		if(input.isKeyDown(Input.KEY_A)){
			//Steht der Player bereits in der richtigen Richtung?
			if(super.lastDir == LastDir.LEFT)
				return standAnimationRet.RUN;
			if(standCounterLeft < standCounterDelta){
				standCounterLeft++;
				super.standDir = LastDir.LEFT;
				return standAnimationRet.WAIT;
			}
			else{
				standCounterLeft = 0;
				return standAnimationRet.RUN;
			}
		}
		
		if(input.isKeyDown(Input.KEY_S)){
			//Steht der Player bereits in der richtigen Richtung?
			if(super.lastDir == LastDir.DOWN)
				return standAnimationRet.RUN;
			if(standCounterDown < standCounterDelta){
				standCounterDown++;
				super.standDir = LastDir.DOWN;
				return standAnimationRet.WAIT;
			}
			else{
				standCounterDown = 0;
				return standAnimationRet.RUN;
			}
		}
		
		if(input.isKeyDown(Input.KEY_D)){
			//Steht der Player bereits in der richtigen Richtung?
			if(super.lastDir == LastDir.RIGHT)
				return standAnimationRet.RUN;
			if(standCounterRight < standCounterDelta){
				standCounterRight++;
				super.standDir = LastDir.RIGHT;
				return standAnimationRet.WAIT;
			}
			else{
				standCounterRight = 0;
				return standAnimationRet.RUN;
			}
		}
		
		if(input.isKeyDown(Input.KEY_W)){
			//Steht der Player bereits in der richtigen Richtung?
			if(super.lastDir == LastDir.UP)
				return standAnimationRet.RUN;
			if(standCounterUp < standCounterDelta){
				standCounterUp++;
				super.standDir = LastDir.UP;
				return standAnimationRet.WAIT;
			}
			else{
				standCounterUp = 0;
				return standAnimationRet.RUN;
			}
		}
		//Überprüfen, ob zuvor eine Bewegungstaste gedrückt wurde, oder Spieler einfach nur Pausiert
		if(standCounterUp > 0 || standCounterDown > 0 || standCounterLeft > 0 || standCounterRight > 0){
			standCounterUp = 0;
			standCounterDown = 0;
			standCounterLeft = 0;
			standCounterRight = 0;
			return standAnimationRet.STAND;
		}
		return standAnimationRet.WAIT;
	}
	
	public void renderPlayer() {
		super.renderEntity();
	}
	
	public void updatePlayer(Input input) {
		if(super.isStanding){
			move = standAnimation(input);
			if(move == standAnimationRet.STAND){
				super.updateEntity(input, true, 0);
			}
			else if(move == standAnimationRet.RUN){
				super.updateEntity(input, 1);
			}
		}
		//Bewegung wird im Moment ausgeführt, Eingabe zu Entity durchschleifen
		else{
			super.updateEntity(input, 1);
		}
	//	super.updateEntity(input);	
	}
}