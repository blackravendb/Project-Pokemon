package de.pokemon;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Entity Klasse. Wird auf Npc und Player vererbt. Bietet grunds�tzliche
 * Methoden f�r Laufanimationen und Renderm�glichkeiten
 * 
 * @author Dennis
 */
public class Entity {
	/** X-Position (Pixelgenau) */
	private int posX;
	/** Y-Position (Pixelgenau) */
	private int posY;
	/** Breite der Entit�t */
	private int width;
	/** H�he der Entit�t */
	private int height;
	/** X-Position (tilegenau) */
	private int tileX;
	/** Y-Position (tilegenau) */
	private int tileY;
	/** Bewegungsgeschwindigkeit (Pixel pro Durchlauf) */
	private int moveSpeed = 2;

	/** Referenz auf den EventManager um dessen Methoden aufzurufen */
	protected EventManager event;

	/** Enum Feld f�r die vier Bewegungsrichtungen */
	private enum Direction {
		DOWN, LEFT, UP, RIGHT, NULL
	};

	/** Speicher f�r Tastatureingabe, um korrekte Bewegung zu rendern */
	protected Direction lastDir = Direction.DOWN;
	/** Speicher f�r letzte Bewegungsrichtung, um korrektes Standbild zu rendern */
	protected Direction standDir; // Variable f�r Standanimation letzte
									// bewegungsrichtung
	/**
	 * Komplette Bewegungsbilder. Werden im Kontruktor auf einzelne
	 * Animationsvariablen aufgeteilt
	 */
	private Image image;

	/** Boolean-Wert, ob gerade eine Bewegungsanimation gerendert wird */
	protected boolean isRunning = false;
	/**
	 * Boolean-Wert, ob Entit�t gerade an Position stehen bleibt (Protected,
	 * sodass Player und PlayState darauf zugreifen k�nnen)
	 */
	protected boolean isStanding = true;

	/**
	 * Image-Array f�r Laufanimationen, wobei erste Dimension f�r Animation und
	 * zweite Dimension f�r die Bilder steht. Die ersten 4 Zeilen sind f�r
	 * Kopfanimationen die letzten 4 f�r K�rperanimationen
	 */
	private Image charImages[][] = new Image[8][4];

	/** Animationsvariablen f�r Bewegungsanimationen */
	private Animation aniMoveLeftHead, aniMoveUpHead, aniMoveRightHead,
			aniMoveDownHead, aniMoveLeftBody, aniMoveUpBody, aniMoveRightBody,
			aniMoveDownBody;
	/**
	 * Wert, wie viele ms ein Animationsbild angezeigt werden soll, bei
	 * Bewegungsanimation
	 */
	private int aniDelta = 150;
	private Animation aniTurnLeftHead = new Animation();
	private Animation aniTurnUpHead = new Animation();
	private Animation aniTurnRightHead = new Animation();
	private Animation aniTurnDownHead = new Animation();
	private Animation aniTurnLeftBody = new Animation();
	private Animation aniTurnUpBody = new Animation();
	private Animation aniTurnRightBody = new Animation();
	private Animation aniTurnDownBody = new Animation();

	private Animation aniStandLeftHead = new Animation();
	private Animation aniStandRightHead = new Animation();
	private Animation aniStandUpHead = new Animation();
	private Animation aniStandDownHead = new Animation();
	private Animation aniStandLeftBody = new Animation();
	private Animation aniStandRightBody = new Animation();
	private Animation aniStandUpBody = new Animation();
	private Animation aniStandDownBody = new Animation();

	/**
	 * Wert, wie viele ms ein Animationsbild angezeigt werden soll, bei
	 * Standanimation
	 */
	private int standAniDelta = 200; // Dauer der Standanimation

	/**
	 * Animation Variable, welche Animation im aktuellem Loop gerendert werden
	 * soll. Tile Head
	 */
	protected Animation currentAnimationHead;
	/**
	 * Animation Variable, welche Animation im aktuellem Loop Gerendert werden
	 * soll. Tile Body
	 */
	protected Animation currentAnimationBody;
	/**
	 * int Wert f�r aktuelle Blickrichtung. Hierbei werden die Key ID's
	 * verwendet, welche normalerweise von einem Input Objekt �bergeben werden.
	 * Default Wert ist Blickrichtung nach unten. protected, da Erbende Klassen
	 * darauf zugreifen m�ssen
	 */
	protected int currentView = Input.KEY_S;

	/**
	 * Variable zum Speichern, ob Entit�t im Aktuellem Loop gegen einen Soliden
	 * Block l�uft. Protected, damit Player Klasse diesen Wert auslesen kann, um
	 * Solid Sound abspielen zu k�nnen
	 */
	protected boolean moveAgainstSolid = false;

	/**
	 * Variable zum Abspeichern, welcher Frame beim letzten Durchlauf, bei
	 * Bewegung gegen einen Soliden Block abgespielt wurde. Wird in renderTick()
	 * verwendet um moveAgainstSolid animation abzubrechen. Initialisierungswert
	 * ist erster Frame
	 */
	private int lastSolidFrame = 0;

	/**
	 * Entit�t Konstruktor
	 * 
	 * @param posX
	 *            (int) Pixelgenaue X-Position
	 * @param posY
	 *            (int) Pixelgenaue Y-Position
	 * @param width
	 *            (int) Breite der Entit�t
	 * @param height
	 *            (int) H�he der Entit�t
	 * @param imagePath
	 *            (String) Pfad zur Biddatei de Entit�t
	 */
	Entity(int posX, int posY, int width, int height, String imagePath,
			EventManager event) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.event = event;

		// Bilddatei mit Animationsabl�ufen
		try {
			image = new Image(imagePath);
		} catch (SlickException e) {
			System.err.println("Error while loading character image");
			e.printStackTrace();
		}

		// Aktueller Standort in Tiles (y+1, da Position von K�rper der Entit�t
		// abh�ngt
		tileY = posY / Core.tileSize + 1;
		tileX = posX / Core.tileSize;

		// Aktuelle Position blocken
		Map.setBlocked(tileX, tileY, true);

		// Animationsbilder laden (K�pfe) (Laufanimationen)
		// Down
		charImages[0][0] = image.getSubImage(2 * width, 2 * height, width,
				height / 2);
		charImages[0][1] = image.getSubImage(2 * width, 1 * height, width,
				height / 2);
		charImages[0][2] = image.getSubImage(2 * width, 3 * height, width,
				height / 2);
		charImages[0][3] = charImages[0][1];

		// LEFT
		charImages[1][0] = image.getSubImage(0 * width, 1 * height, width,
				height / 2);
		charImages[1][1] = image.getSubImage(0 * width, 2 * height, width,
				height / 2);
		charImages[1][2] = image.getSubImage(0 * width, 3 * height, width,
				height / 2);
		charImages[1][3] = charImages[1][1];

		// UP
		charImages[2][0] = image.getSubImage(2 * width, 0 * height, width,
				height / 2);
		charImages[2][1] = image.getSubImage(0 * width, 0 * height, width,
				height / 2);
		charImages[2][2] = image.getSubImage(1 * width, 3 * height, width,
				height / 2);
		charImages[2][3] = charImages[2][1];

		// RIGHT
		charImages[3][0] = image.getSubImage(1 * width, 1 * height, width,
				height / 2);
		charImages[3][1] = image.getSubImage(1 * width, 0 * height, width,
				height / 2);
		charImages[3][2] = image.getSubImage(1 * width, 2 * height, width,
				height / 2);
		charImages[3][3] = charImages[3][1];

		// Animationsbilder laden (K�rper) (Laufanimationen)
		// Down
		charImages[4][0] = image.getSubImage(2 * width,
				2 * height + height / 2, width, height / 2);
		charImages[4][1] = image.getSubImage(2 * width,
				1 * height + height / 2, width, height / 2);
		charImages[4][2] = image.getSubImage(2 * width,
				3 * height + height / 2, width, height / 2);
		charImages[4][3] = charImages[4][1];

		// LEFT
		charImages[5][0] = image.getSubImage(0 * width,
				1 * height + height / 2, width, height / 2);
		charImages[5][1] = image.getSubImage(0 * width,
				2 * height + height / 2, width, height / 2);
		charImages[5][2] = image.getSubImage(0 * width,
				3 * height + height / 2, width, height / 2);
		charImages[5][3] = charImages[5][1];

		// UP
		charImages[6][0] = image.getSubImage(2 * width,
				0 * height + height / 2, width, height / 2);
		charImages[6][1] = image.getSubImage(0 * width,
				0 * height + height / 2, width, height / 2);
		charImages[6][2] = image.getSubImage(1 * width,
				3 * height + height / 2, width, height / 2);
		charImages[6][3] = charImages[6][1];

		// RIGHT
		charImages[7][0] = image.getSubImage(1 * width,
				1 * height + height / 2, width, height / 2);
		charImages[7][1] = image.getSubImage(1 * width,
				0 * height + height / 2, width, height / 2);
		charImages[7][2] = image.getSubImage(1 * width,
				2 * height + height / 2, width, height / 2);
		charImages[7][3] = charImages[7][1];

		// Arrays den Animationen zuordnen mit Abspielgeschwindigkeit aniDelta
		// (Kopf)
		aniMoveLeftHead = new Animation(charImages[1], aniDelta);
		aniMoveRightHead = new Animation(charImages[3], aniDelta);
		aniMoveUpHead = new Animation(charImages[2], aniDelta);
		aniMoveDownHead = new Animation(charImages[0], aniDelta);

		// Arrays den Animationen zuordnen mit Abspielgeschwindigkeit aniDelta
		// (K�rper)
		aniMoveLeftBody = new Animation(charImages[5], aniDelta);
		aniMoveRightBody = new Animation(charImages[7], aniDelta);
		aniMoveUpBody = new Animation(charImages[6], aniDelta);
		aniMoveDownBody = new Animation(charImages[4], aniDelta);

		// Animatinsbilder Laden (Standanimationen) (Kopf)
		// LEFT
		aniTurnLeftHead.addFrame(charImages[1][0], standAniDelta);
		aniTurnLeftHead.addFrame(charImages[1][1], standAniDelta);
		aniTurnLeftHead.setLooping(false);

		// RIGHT
		aniTurnRightHead.addFrame(charImages[3][0], standAniDelta);
		aniTurnRightHead.addFrame(charImages[3][1], standAniDelta);
		aniTurnRightHead.setLooping(false);

		// UP
		aniTurnUpHead.addFrame(charImages[2][0], standAniDelta);
		aniTurnUpHead.addFrame(charImages[2][1], standAniDelta);
		aniTurnUpHead.setLooping(false);

		// DOWN
		aniTurnDownHead.addFrame(charImages[0][0], standAniDelta);
		aniTurnDownHead.addFrame(charImages[0][1], standAniDelta);
		aniTurnDownHead.setLooping(false);

		// Animatinsbilder Laden (Standanimationen) (K�rper)
		// LEFT
		aniTurnLeftBody.addFrame(charImages[5][0], standAniDelta);
		aniTurnLeftBody.addFrame(charImages[5][1], standAniDelta);
		aniTurnLeftBody.setLooping(false);

		// RIGHT
		aniTurnRightBody.addFrame(charImages[7][0], standAniDelta);
		aniTurnRightBody.addFrame(charImages[7][1], standAniDelta);
		aniTurnRightBody.setLooping(false);

		// UP
		aniTurnUpBody.addFrame(charImages[6][0], standAniDelta);
		aniTurnUpBody.addFrame(charImages[6][1], standAniDelta);
		aniTurnUpBody.setLooping(false);

		// DOWN
		aniTurnDownBody.addFrame(charImages[4][0], standAniDelta);
		aniTurnDownBody.addFrame(charImages[4][1], standAniDelta);
		aniTurnDownBody.setLooping(false);

		aniStandLeftHead.addFrame(charImages[1][1], standAniDelta);
		aniStandRightHead.addFrame(charImages[3][1], standAniDelta);
		aniStandUpHead.addFrame(charImages[2][1], standAniDelta);
		aniStandDownHead.addFrame(charImages[0][1], standAniDelta);
		aniStandLeftBody.addFrame(charImages[5][1], standAniDelta);
		aniStandRightBody.addFrame(charImages[7][1], standAniDelta);
		aniStandUpBody.addFrame(charImages[6][1], standAniDelta);
		aniStandDownBody.addFrame(charImages[4][1], standAniDelta);

		// Startanimatin renderStand() aufrufen, sodass Entit�t auf Bildschirm
		// gerendert wird
		renderStand();

	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosX(int posX) {
		isRunning = false;
		this.posX = posX;
		Map.setBlocked(tileX, tileY, false); // alte Position freigeben
		calcTileX(posX);
		Map.setBlocked(tileX, tileY, true);
	}

	public void setPosY(int posY) {
		isRunning = false;
		this.posY = posY;
		Map.setBlocked(tileX, tileY, false); // alte Position freigeben
		calcTileY(posY);
		Map.setBlocked(tileX, tileY, true);
	}

	public void setPosition(int posX, int posY) {
		isRunning = false;
		isStanding = true;
		Map.setBlocked(tileX, tileY, false); // alte Position freigeben
		calcTileX(posX);
		calcTileY(posY);
		this.posX = posX;
		this.posY = posY;
		Map.setBlocked(tileX, tileY, true);
	}

	public int getTileX() {
		return tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setLastDir(int input) {
		if (input == Input.KEY_A) {
			lastDir = Direction.LEFT;
		} else if (input == Input.KEY_S) {
			lastDir = Direction.RIGHT;
		} else if (input == Input.KEY_W) {
			lastDir = Direction.UP;
		} else if (input == Input.KEY_S) {
			lastDir = Direction.DOWN;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	/**
	 * X Tile Position aus Pixel Koordinaten berechnen
	 * 
	 * @param posX
	 *            (int) X Koordinate der Entit�t
	 * @return void
	 */
	private void calcTileX(int posX) {
		tileX = posX / Core.tileSize;
	}

	/**
	 * Y Tile Position aus Pixel Koordinaten berechnen
	 * 
	 * @param posY
	 *            (int) Y Koordinate der Entit�t
	 * @return void
	 */
	private void calcTileY(int posY) {
		tileY = posY / Core.tileSize + 1;
	}

	/**
	 * Einfache Update Methode f�r posX (Pixelgenauer) Standort Hierbei wird
	 * moveSpeed dem posX Wert ab- bzw. hinzugef�gt
	 * 
	 * @param tileX
	 *            (int) Wert normalerweise -1 (Bewegung nach links), 1 (Bewegung
	 *            nach rechts), au�er gr��ere Spr�nge sind beabsichtigt
	 * @return void
	 */
	public void moveX(int tileX) {
		posX += tileX * moveSpeed;
	}

	/**
	 * Einfache Update Methode f�r posY (Pixelgenauer) Standort Hierbei wird
	 * moveSpeed dem posY Wert ab- bzw. hinzugef�gt
	 * 
	 * @param tileY
	 *            (int) Wert normalerweise -1 (Bewegung nach oben), 1 (Bewegung
	 *            nach unten), au�er gr��ere Spr�nge sind beabsichtigt
	 * @return void
	 */
	public void moveY(int tileY) {
		posY += tileY * moveSpeed;
	}

	protected void renderEntityHead() {
		renderEntity(posX, posY, true);
	}

	/**
	 * Laden des Standbildes der Entit�t. Wird verwendet, wenn die Entit�t auf
	 * der Stelle stehen bleibt, ohne eine Aktion auszuf�hren. Parameter
	 * 'isRunning' wird dabei auf 'false' belassen, sodass weitere
	 * Bewegungsbefehle angenommen werden k�nnen. Sollte 'isRunning' beim Start
	 * der Methode auf 'true' stehen, wird diese abgebrochen und eine
	 * Fehlermeldung ausgegeben.
	 * 
	 * @return void
	 */
	protected void renderStand() {
		// Nochmalige Kontrolle ob Entit�t im Moment keine Bewegung ausf�hrt
		if (!isRunning) {
			// Aktuelle Blickrichtung der Variable currentView �bergeben
			// Aktuelle Animation der Tempor�ren Variable currenAnimation
			// zuweisen
			if (currentView == Input.KEY_A) {
				currentAnimationHead = aniStandLeftHead;
				currentAnimationBody = aniStandLeftBody;
			} else if (currentView == Input.KEY_W) {
				currentAnimationHead = aniStandUpHead;
				currentAnimationBody = aniStandUpBody;
			} else if (currentView == Input.KEY_D) {
				currentAnimationHead = aniStandRightHead;
				currentAnimationBody = aniStandRightBody;
			} else if (currentView == Input.KEY_S) {
				// implementiert werden
				currentAnimationHead = aniStandDownHead;
				currentAnimationBody = aniStandDownBody;
			}

			// Keine weiteren Berechnungen wie Kollisionsberechnung n�tig, da
			// Entit�t auf der Stelle stehen bleibt
		}
		// Sollte isRunning true sein wird eine Warnung ausgegeben
		else {
			System.err
					.println("isRunning == true, during method renderStand() in Class Entity");
		}
	}

	/**
	 * Laden der Bewegungsanimation der Entit�t. Wird verwendet wenn die Entit�t
	 * die aktuelle Position �ndert. Parameter 'isRunning' wird dabei auf 'true'
	 * gesetzt, sodass keine weiteren Bewegungsbefehleangenommen werden k�nnen,
	 * bis die Entit�t die Zielposition erreicht hat. Sollte 'isRunning' beim
	 * Start der Methode auf 'true' stehen, wird diese abgebrochen und eine
	 * Fehlermeldung ausgegeben.
	 * 
	 * @param input
	 *            (int) Key ID der Tastatureingabe, bzw. Bewegungsanweisung bei
	 *            NPCs.
	 * @return void
	 */
	protected void renderMoveAnimation(int input) {
		// Merke: sollte Entit�t versuchen gegen Solid block zu laufen wird
		// isRunning trotzdem auf True gesetzt und die Bewegungsanimation ein
		// mal durchgespielt. Zus�tzlich mus der Solid sound abgespielt werden.

		// Nochmalige Kontrolle ob Entit�t im moment keine Bewegung ausf�hrt
		if (!isRunning) {
			// Aktuelle Blickrichtung der Variable currentView �bergeben
			currentView = input;

			// Variable isRunning auf true setzen, sodass keine weiteren
			// Bewegungsabl�ufe angenommen werden
			isRunning = true;

			// Variable isStanding auf false setzen, sodass Methode renderTick
			// erkennt, dass im moment eine Bewegung ausgef�hrt wird
			isStanding = false;

			// �berpr�fen ob n�chster Block Solid ist. Wenn nicht wird dieser
			// Reserviert. und Entit�t wird einige Pixel bewegt Wenn Solid, wird
			// die Varible moveAgainstSolid auf true gesetzt Zus�tzlich werden
			// die Animation in currentAnimation geladen
			if (input == Input.KEY_A) {
				currentAnimationHead = aniMoveLeftHead;
				currentAnimationBody = aniMoveLeftBody;
				if (!Map.isBlocked(tileX - 1, tileY)) {
					Map.setBlocked(tileX - 1, tileY, true);
					moveX(-1);
				} else {
					moveAgainstSolid = true;
					// Nochmaliges zur�cksetzen der Animationen, da sonst
					// Soundfehler in einem Player Objekt auftauchen k�nnen,
					// wenn dieser direkt nach einer move Animation gegen einen
					// Soliden Block l�uft
					currentAnimationBody.restart();
					currentAnimationHead.restart();
				}

			} else if (input == Input.KEY_W) {
				currentAnimationHead = aniMoveUpHead;
				currentAnimationBody = aniMoveUpBody;
				if (!Map.isBlocked(tileX, tileY - 1)) {
					Map.setBlocked(tileX, tileY - 1, true);
					moveY(-1);
				} else {
					moveAgainstSolid = true;
					// Nochmaliges zur�cksetzen der Animationen, da sonst
					// Soundfehler in einem Player Objekt auftauchen k�nnen,
					// wenn dieser direkt nach einer move Animation gegen einen
					// Soliden Block l�uft
					currentAnimationBody.restart();
					currentAnimationHead.restart();
				}

			} else if (input == Input.KEY_D) {
				currentAnimationHead = aniMoveRightHead;
				currentAnimationBody = aniMoveRightBody;
				if (!Map.isBlocked(tileX + 1, tileY)) {
					Map.setBlocked(tileX + 1, tileY, true);
					moveX(1);
				} else {
					moveAgainstSolid = true;
					// Nochmaliges zur�cksetzen der Animationen, da sonst
					// Soundfehler in einem Player Objekt auftauchen k�nnen,
					// wenn dieser direkt nach einer move Animation gegen einen
					// Soliden Block l�uft
					currentAnimationBody.restart();
					currentAnimationHead.restart();
				}

			} else if (input == Input.KEY_S) {
				currentAnimationHead = aniMoveDownHead;
				currentAnimationBody = aniMoveDownBody;
				if (!Map.isBlocked(tileX, tileY + 1)) {
					Map.setBlocked(tileX, tileY + 1, true);
					moveY(1);
				} else {
					moveAgainstSolid = true;
					// Nochmaliges zur�cksetzen der Animationen, da sonst
					// Soundfehler in einem Player Objekt auftauchen k�nnen,
					// wenn dieser direkt nach einer move Animation gegen einen
					// Soliden Block l�uft
					currentAnimationBody.restart();
					currentAnimationHead.restart();
				}
			}
		}
		// Sollte isRunning true sein wird eine Warnung ausgegeben
		else
			System.err
					.println("isRunning == true, during method renderMoveAnimation() in Class Entity");
	}

	/**
	 * Laden der Drehanimation der Entit�t. Wird verwendet, wenn die Entit�t die
	 * Blickrichtung �ndert. Parameter 'isRunning' wird dabei auf 'true'
	 * gesetzt, sodass keine weiteren Bewegungsbefehle angenommen werden k�nnen,
	 * bis die Animation abgeschlosen ist. Sollte 'isRunning' beim Start der
	 * Methode auf 'true' stehen, wird diese abgebrochen und eine Fehlermeldung
	 * ausgegeben.
	 * 
	 * @param input
	 *            (int) Key ID der Tastatureingabe, bzw. Bewegungsanweisung bei
	 *            NPCs
	 * @return void
	 */
	protected void renderTurnAnimation(int input) {
		// Nochmalige Kontrolle ob Entit�t im moment keine Bewegung ausf�hrt
		if (!isRunning) {
			// Aktuelle Blickrichtung der Variable currentView �bergeben
			currentView = input;

			// Variable isRunning auf true setzen, sodass keine weiteren
			// Bewegungsabl�ufe angenommen werden
			isRunning = true;

			// Aktuelle Animation der Tempor�ren Variable currenAnimation
			// zuweisen
			if (input == Input.KEY_A) {
				currentAnimationHead = aniTurnLeftHead;
				currentAnimationBody = aniTurnLeftBody;
			} else if (input == Input.KEY_W) {
				currentAnimationHead = aniTurnUpHead;
				currentAnimationBody = aniTurnUpBody;
			} else if (input == Input.KEY_D) {
				currentAnimationHead = aniTurnRightHead;
				currentAnimationBody = aniTurnRightBody;
			} else if (input == Input.KEY_S) {
				currentAnimationHead = aniTurnDownHead;
				currentAnimationBody = aniTurnDownBody;
			}

			// Keine Kollisionsberechnung n�tig, da Entit�t auf Position stehen
			// bleibt
		}
		// Sollte isRunning true sein wird eine Warnung ausgegeben
		else
			System.err
					.println("isRunning == true, during method renderTurnAnimation in Class Entity");
	}

	/**
	 * Tick Methode f�r Render Vorg�nge. Soll ausgef�hrt werden, wenn isRunning
	 * auf true steht. Diese �berpr�ft ob aktueller Bewegungsvorgang
	 * abgeschlossen ist. Ist dies der Fall wird isRunning auf false gesetzt und
	 * input neu ausgewertet. Sonst wird Animation weiter abgespielt
	 * 
	 * @param input
	 *            (int) Key ID der Tastatureingabe, bzw. Bewegungsanweisung bei
	 *            NPCs. Wird in dieser Klasse nur verwendet, sollte
	 *            Bewegungsvorgang abgeschlossen sein.
	 * @return void
	 */
	protected void renderTick(int input) {
		if (isRunning) {
			// �berpr�fe, welcher Bewegungsvorgang aktuell abgearbeitet wird
			// move Bewegung wird ausgef�hrt
			if (!isStanding) {
				// �berpr�fe, ob Entit�t im moment gegen einen Soliden Block
				// l�uft
				if (moveAgainstSolid) {
					// �berpr�fe, ob aktuelle animation abgeschlossen ist. Ist
					// dies der Fall, ist Bewegung abgeschlossen und
					// Werte/Animationen k�nnen zur�ckgesetzt werden
					if (currentAnimationBody.getFrame() == 3
							&& lastSolidFrame == 2) {
						isRunning = false;
						isStanding = true;
						moveAgainstSolid = false;
						lastSolidFrame = 0;

						// Animationen neustarten, sodass bei n�chsten abspielen
						// kein Probleme auftreten k�nnen
						currentAnimationBody.restart();
						currentAnimationHead.restart();

						// Tempor�re Reference Variable auf Standanimation
						// zur�cksetzen
						renderStand();
					}
					// Animation wird noch ausgef�hrt
					else {
						lastSolidFrame = currentAnimationBody.getFrame();
					}
				}
				// Entit�t bewegt sich auf neue Position
				else {
					// �berpr�fen ob Entit�t angekommen ist
					// Bewegung auf X-Achse
					if (currentView == Input.KEY_A
							|| currentView == Input.KEY_D) {
						// Ist aktuelle X Position moduli Tile gr��e 0, wurde
						// Ziel erreicht
						if (posX % Core.tileSize == 0) {
							// Ursprungs Tile wieder freigeben
							Map.setBlocked(tileX, tileY, false);

							// Neue Tileposition berechnen
							calcTileX(posX);

							// isRunning false setzen, sodass im n�chsten
							// Schritt keine Fehlermeldung ausgegeben wird
							isRunning = false;
							isStanding = true;

							// �berpr�fe ob Bewegung weitergef�hrt werden soll
							// (Wenn input == 0 => keine Key Eingabe, siehe
							// Player.getKeyValue()
							if (input != 0) {
								// Rufe renderMoveAnimation() methode auf, um
								// Bewegung fortzusetzen
								renderMoveAnimation(input);
							}
							// Keine Key eingabe.
							// Animationen neustarten, sodass bei n�chsten
							// abspielen kein Probleme auftreten k�nnen
							else {
								currentAnimationBody.restart();
								currentAnimationHead.restart();

								// Tempor�re Reference Variable wieder auf
								// Standanimation zur�cksetzen
								renderStand();
							}
						}
						// Entit�t noch in Bewegung
						else {
							if (currentView == Input.KEY_A)
								moveX(-1);
							else if (currentView == Input.KEY_D)
								moveX(1);
						}
					}
					// Bewegung auf Y-Achse
					else if (currentView == Input.KEY_W
							|| currentView == Input.KEY_S) {
						// Ist aktuelle Y Position moduli Tilegr��e 0, wurde
						// Ziel erreicht
						if (posY % Core.tileSize == 0) {
							// Ursprungs Tile wieder freigeben
							Map.setBlocked(tileX, tileY, false);

							// Neue Tileposition berechnen
							calcTileY(posY);

							// isRunning false setzen, sodass im n�chsten
							// Schritt keine Fehlermeldung ausgegeben wird
							isRunning = false;
							isStanding = true;

							// �berpr�fe ob Bewegung weitergef�hrt werden soll
							// (Wenn input == 0 => keine Key Eingabe, siehe
							// Player.getKeyValue()
							if (input != 0) {
								// Rufe renderMoveAnimation() methode auf, um
								// Bewegung fortzusetzen
								renderMoveAnimation(input);
							}
							// Keine Key eingabe. Werte k�nnen zur�ckgesetzt
							// werden
							// Animationen neustarten, sodass bei n�chsten
							// abspielen
							// kein Probleme auftreten k�nnen
							else {
								currentAnimationBody.restart();
								currentAnimationHead.restart();

								// Tempor�re Reference Variable wieder auf
								// Standanimation zur�cksetzen
								renderStand();
							}
						}
						// Entit�t noch in Bewegung
						else {
							if (currentView == Input.KEY_W)
								moveY(-1);
							else if (currentView == Input.KEY_S)
								moveY(1);
						}
					}
				}
			}

			// Turn Bewegung wird ausgef�hrt
			else {
				// �berpr�fen ob aktuelle Animation abgeschlossen ist
				if (currentAnimationBody.isStopped()) {
					isRunning = false;

					// Animationen neustarten, sodass bei n�chsten abspielen
					// kein Probleme auftreten k�nnen
					currentAnimationBody.restart();
					currentAnimationHead.restart();

					// Tempor�re Reference Variable auf Standanimation
					// zur�cksetzen
					renderStand();
				}
			}
		}
		// Sollte isRunning false sein wird eine Warnung ausgegeben
		else
			System.err
					.println("isRunning == false, during method renderTick() in Class Entity");
	}

	private void renderEntity(int posX, int posY, boolean headBody) {
		// aniTemp = getAnimation(headBody);
		if (!headBody) {
			currentAnimationBody.draw(posX, posY);
		} else {
			currentAnimationHead.draw(posX, posY);
		}
	}

	protected void renderEntityBody() {
		// Sollte currentAnimationBody == null sein wird Standbild von aktueller
		// Blickrichtung gerendert
		// if (currentAnimationBody == null)
		// renderStand();
		renderEntity(posX, posY + height / 2, false);
	}

	public void updateEntity(int input, int steps) {
		// Ist Entity bereits in bewegung
		if (isRunning) {
			if (lastDir == Direction.LEFT)
				moveX(-steps);
			else if (lastDir == Direction.RIGHT)
				moveX(steps);
			else if (lastDir == Direction.DOWN)
				moveY(steps);
			else if (lastDir == Direction.UP)
				moveY(-steps);
		}
		// Noch nicht in Bewegung jedoch Key Listener eingabe
		else if (input == Input.KEY_A)
			moveX(-steps);
		else if (input == Input.KEY_D)
			moveX(steps);
		else if (input == Input.KEY_S)
			moveY(steps);
		else if (input == Input.KEY_W)
			moveY(-steps);
		// Keine Key Listener eingabe, Animationen werden zur�ckgesetzt;
		// Bewegungszustand auf stehen gesetzt
		else {
			isRunning = false;
			isStanding = true;
			aniMoveLeftHead.restart();
			aniMoveUpHead.restart();
			aniMoveRightHead.restart();
			aniMoveDownHead.restart();

			aniMoveLeftBody.restart();
			aniMoveUpBody.restart();
			aniMoveRightBody.restart();
			aniMoveDownBody.restart();
		}
	}
}