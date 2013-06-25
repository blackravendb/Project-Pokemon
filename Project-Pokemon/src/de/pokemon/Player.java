package de.pokemon;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player extends Entity {
	/**
	 * Counter für getAnimationen; sobald standCounterDelta-Durchläufe
	 * abgefangen wurden wird das Ergebnis an Entity weitergeleitet
	 */
	private int standCounterUp = 0;
	/**
	 * Counter für getAnimationen; sobald standCounterDelta-Durchläufe
	 * abgefangen wurden wird das Ergebnis an Entity weitergeleitet
	 */
	private int standCounterDown = 0;
	/**
	 * Counter für getAnimationen; sobald standCounterDelta-Durchläufe
	 * abgefangen wurden wird das Ergebnis an Entity weitergeleitet
	 */
	private int standCounterLeft = 0;
	/**
	 * Counter für getAnimationen; sobald standCounterDelta-Durchläufe
	 * abgefangen wurden wird das Ergebnis an Entity weitergeleitet
	 */
	private int standCounterRight = 0;
	/**
	 * Counter, nach wie vielen Frames eine Bewegungsanimation an Entity
	 * weitergegeben wird. Sollte die Bewegungstaste vor standCunterDelta
	 * losgelassen werden, wird nur eine Drehung Gerendert
	 */
	private int standCounterDelta = 7;
	/** Variable für Key ID, des Input Objektes */
	private int keyInput;

	/**
	 * Temporärer Wert für Turn Animation. Gibt an welche Taste im vorherigem
	 * Loop gedrückt wurde. Muss nicht vorinitialisiert werden.
	 */
	private int turnDirection;

	/**
	 * Wert, welche Aktion im aktuellem Tick ausgeführt werden soll TURN =
	 * Entity wird auf der Stelle gerendert RUN = Entity bewegt sich zum
	 * nächsten Tile WAIT = Spezialfall, sollte Key Eingabe unter
	 * standCounterDelta-Durchläufe sein Dient zusätzlich als Rückgabewert für
	 * getAnimation
	 */
	private enum animationRet {
		TURN, RUN, WAIT
	};

	/**
	 * Variable zum Zwischenspeichern des Rückgabewerts getAnimation
	 */
	private animationRet move;

	/**
	 * boolean Wert, ob Solid Sound abgespielt werden soll. Dieser wird nur
	 * einmal pro Bewegungsablauf abgespielt
	 */
	private boolean playSolidSound = false;

	/**
	 * Player Objekt Hauptfigur im Spiel. Wird über Key Eingaben gesteuert.
	 * Übergabeparameter werden an Entity weitergegeben.
	 * 
	 * @param posX
	 *            (int) X-Position, wo der Player gerendert wird
	 * @param posY
	 *            (int) Y-Position, wo der Player gerendert wird
	 * @return void
	 */
	Player(int posX, int posY, EventManager event) throws SlickException {
		super(posX, posY, Core.tileSize, Core.tileSize * 2, "res/character/player.png", event);
	}

	/*
	 * private animationRet getAnimation(Input input) { if
	 * (input.isKeyDown(Input.KEY_A)) { // Steht der Player bereits in der
	 * richtigen Richtung? if (super.lastDir == LastDir.LEFT) return
	 * animationRet.RUN; if (standCounterLeft < standCounterDelta) {
	 * standCounterLeft++; super.standDir = LastDir.LEFT; return
	 * animationRet.WAIT; } else { standCounterLeft = 0; return
	 * animationRet.RUN; } }
	 * 
	 * if (input.isKeyDown(Input.KEY_S)) { // Steht der Player bereits in der //
	 * richtigen Richtung? if (super.lastDir == LastDir.DOWN) return
	 * animationRet.RUN; if (standCounterDown < standCounterDelta) {
	 * standCounterDown++; super.standDir = LastDir.DOWN; return
	 * animationRet.WAIT; } else { standCounterDown = 0; return
	 * animationRet.RUN; } }
	 * 
	 * if (input.isKeyDown(Input.KEY_D)) { // Steht der Player bereits in der //
	 * richtigen Richtung? if (super.lastDir == LastDir.RIGHT) return
	 * animationRet.RUN; if (standCounterRight < standCounterDelta) {
	 * standCounterRight++; super.standDir = LastDir.RIGHT; return
	 * animationRet.WAIT; } else { standCounterRight = 0; return
	 * animationRet.RUN; } }
	 * 
	 * if (input.isKeyDown(Input.KEY_W)) { // Steht der Player bereits in der //
	 * richtigen Richtung? if (super.lastDir == LastDir.UP) return
	 * animationRet.RUN; if (standCounterUp < standCounterDelta) {
	 * standCounterUp++; super.standDir = LastDir.UP; return animationRet.WAIT;
	 * } else { standCounterUp = 0; return animationRet.RUN; } } // Überprüfen,
	 * ob zuvor eine Bewegungstaste gedrückt wurde, oder // Spieler // einfach
	 * nur Pausiert if (standCounterUp > 0 || standCounterDown > 0 ||
	 * standCounterLeft > 0 || standCounterRight > 0) { standCounterUp = 0;
	 * standCounterDown = 0; standCounterLeft = 0; standCounterRight = 0; return
	 * animationRet.TURN; } return animationRet.WAIT; }
	 */

	/**
	 * Methode für Überprüfung ob eine Turn Animation, eine Laufanimation oder
	 * ein einfaches Standbild gerendert werden muss
	 * 
	 * @param input
	 *            (int) Key ID der Tastatureingabe
	 * @return void
	 */
	private animationRet getAnimation(int input) {
		// Überprüfen welche Tastatureingabe wurde der Methode übermittelt
		// Links
		if (input == Input.KEY_A) {
			// Steht der Player bereits in der richtigen Richtung?
			if (super.currentView == Input.KEY_A)
				// Player führt Bewegungsanimation aus
				return animationRet.RUN;
			if (standCounterLeft < standCounterDelta) {
				// Tastenloops noch kleiner als CounterDelta, warten und
				// turnDirection setzen
				standCounterLeft++;
				turnDirection = input;
				return animationRet.WAIT;
			} else {
				// Eingabeloops mehr als Delta, Counter zurücksetzen und
				// Bewegungsanimation starten
				standCounterLeft = 0;
				return animationRet.RUN;
			}
		}
		// Unten
		if (input == Input.KEY_S) {
			// Steht der Player bereits in der richtigen Richtung?
			if (super.currentView == Input.KEY_S){
				// Player führt Bewegungsanimation aus
				return animationRet.RUN;
			}
			if (standCounterDown < standCounterDelta) {
				// Tastenloops noch kleiner als CounterDelta, warten und
				// turnDirection setzen
				standCounterDown++;
				turnDirection = input;
				return animationRet.WAIT;
			} else {
				// Eingabeloops mehr als Delta, Counter zurücksetzen und
				// Bewegungsanimation starten
				standCounterDown = 0;
				return animationRet.RUN;
			}
		}
		// Rechts
		if (input == Input.KEY_D) {
			// Steht der Player bereits in der richtigen Richtung?
			if (super.currentView == Input.KEY_D)
				// Player führt Bewegungsanimation aus
				return animationRet.RUN;
			if (standCounterRight < standCounterDelta) {
				// Tastenloops noch kleiner als CounterDelta, warten und
				// turnDirection setzen
				standCounterRight++;
				turnDirection = input;
				return animationRet.WAIT;
			} else {
				// Eingabeloops mehr als Delta, Counter zurücksetzen und
				// Bewegungsanimation starten
				standCounterRight = 0;
				return animationRet.RUN;
			}
		}
		// Hoch
		if (input == Input.KEY_W) {
			// Steht der Player bereits in der richtigen Richtung?
			if (super.currentView == Input.KEY_W)
				// Player führt Bewegungsanimation aus
				return animationRet.RUN;
			if (standCounterUp < standCounterDelta) {
				// Tastenloops noch kleiner als CounterDelta, warten und
				// turnDirection setzen
				standCounterUp++;
				turnDirection = input;
				return animationRet.WAIT;
			} else {
				// Eingabeloops mehr als Delta, Counter zurücksetzen und
				// Bewegungsanimation starten
				standCounterUp = 0;
				return animationRet.RUN;
			}
		}
		// Keine Tastatureingaben (input == 0)
		// Überprüfen, ob zuvor eine Bewegungstaste gedrückt wurde, oder Spieler
		// einfach nur Pausiert
		if (standCounterUp > 0 || standCounterDown > 0 || standCounterLeft > 0
				|| standCounterRight > 0) {
			standCounterUp = 0;
			standCounterDown = 0;
			standCounterLeft = 0;
			standCounterRight = 0;
			// Wert der letzen Key eingabe (vorheriger Loop) dem keyInput
			// mitteilen
			keyInput = turnDirection;
			return animationRet.TURN;
		}
		// Player pausiert
		return animationRet.WAIT;
	}

	/**
	 * Auslesen des Key Listener, welche Taste gedrückt wurde
	 * 
	 * @param input
	 *            (Input) Container für Maus und Tastatureingaben
	 * @return void
	 */
	private int getKeyValue(Input input) {
		if (input.isKeyDown(Input.KEY_A))
			return Input.KEY_A;
		else if (input.isKeyDown(Input.KEY_W))
			return Input.KEY_W;
		else if (input.isKeyDown(Input.KEY_D))
			return Input.KEY_D;
		else if (input.isKeyDown(Input.KEY_S))
			return Input.KEY_S;
		else if (input.isKeyDown(Input.KEY_ENTER))
			return Input.KEY_ENTER;
		else
			return 0;

	}

	/**
	 * Render Methode für oberes Tile (Kopf) Ruft Entity Rendermethode auf
	 */
	public void renderPlayerHead() {
		super.renderEntityHead();
	}

	/**
	 * Render Methode für unteres Tile (Körper) Ruft Entity Rendermethode auf
	 */
	public void renderPlayerBody() {
		super.renderEntityBody();
	}

	/*
	 * public void updatePlayer(Input input, boolean test) { keyInput =
	 * getKeyValue(input);
	 * 
	 * if (super.isStanding) { move = getAnimation(keyInput); if (move ==
	 * animationRet.STAND) { super.updateEntity(keyInput, true, 0); } else if
	 * (move == animationRet.RUN) { super.updateEntity(keyInput, 0); } } //
	 * Bewegung wird im Moment ausgeführt, Eingabe zu Entity durchschleifen else
	 * { super.updateEntity(keyInput, 1); } // super.updateEntity(input); }
	 */

	/**
	 * Update Methode für Objekt Player In dieser wird überprüft, welche
	 * Animation gerendert werden muss. Das Ergebnis wird an die Entity Klasse
	 * weitergegeben.
	 * 
	 * @param input
	 *            (Input) Container für Maus und Tastatureingaben
	 * @return void
	 */
	public void updatePlayer(Input input) {
		// Wert aus input auslesen
		keyInput = getKeyValue(input);

		// Überprüfen, ob Animation ausgeführt werden muss
		// System.out.println("isRunning: " + super.isRunning);
		// System.out.println("isStanding: " + super.isStanding);
		// System.out.println("currentView: " + super.currentView);
		// System.out.println("moveAgainstSolid: " + super.moveAgainstSolid);
		// System.out.println("---------------------------------");
		if (!super.isRunning) {
			//Überprüfen ob Aktionstaste gedrückt wurde
			if(keyInput == Input.KEY_ENTER)
				event.sendActionRequest(getTileX(), getTileY(), currentView);
			// Überprüfen, welche Animation ausgeführt werden muss
			move = getAnimation(keyInput);

			// Verschiedene Render Methoden der Entity Klasse aufrufen
			if (move == animationRet.RUN){
				playSolidSound = true;
				super.renderMoveAnimation(keyInput);
			}
			else if (move == animationRet.TURN)
				super.renderTurnAnimation(keyInput);
			else
				// Standbildanimation
				super.renderStand();
		}
		// Vorherige Animation wird noch ausgeführt; Tick aufrufen
		else {
			if (super.moveAgainstSolid && playSolidSound){
				Sound.audioSolid.playAsSoundEffect(1, 1, false);
				playSolidSound = false;
			}
			super.renderTick(keyInput);
		}
	}
}

/* Alter Quellcode *//*
					 * if (super.isStanding) { move = getAnimation(keyInput); if
					 * (move == animationRet.TURN) {
					 * super.updateEntity(keyInput, true, 0); } else if (move ==
					 * animationRet.RUN) { super.updateEntity(keyInput, 1); } }
					 * // Bewegung wird im Moment ausgeführt, Eingabe zu Entity
					 * durchschleifen else { super.updateEntity(keyInput, 1); }
					 * // super.updateEntity(input); } }
					 */