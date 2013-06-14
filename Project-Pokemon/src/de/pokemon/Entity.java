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

	/** Enum Feld für die vier Bewegungsrichtungen */
	protected enum Direction {
		DOWN, LEFT, UP, RIGHT, NULL
	};

	protected Direction lastDir = Direction.DOWN;
	protected Direction standDir; // Variable für Standanimation letzte
									// bewegungsrichtung

	private Image image;

	protected boolean isRunning = false;
	protected boolean isStanding = true; // protected, da Player und PlayState
											// darauf zugreifen müssen
	private boolean standAnimation = false; // Boolean ob Standanimation
											// ausgeführt werden soll

	private Image charImages[][] = new Image[8][4];
	private Animation aniMoveLeftHead, aniMoveUpHead, aniMoveRightHead,
			aniMoveDownHead, aniMoveLeftBody, aniMoveUpBody, aniMoveRightBody,
			aniMoveDownBody;
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

	private int standAniDelta = 200; // Dauer der Standanimation

	// Temp Variablen für Render Methode
	private Animation aniTemp;

	// Neue Temp Variable für Render/Update Methode
	/**
	 * Animation Variable, welche Animation im aktuellem Loop Gerendert werden
	 * soll. Tile Head
	 */
	private Animation currentAnimationHead;
	/**
	 * Animation Variable, welche Animation im aktuellem Loop Gerendert werden
	 * soll. Tile Body
	 */
	private Animation currentAnimationBody;
	/**
	 * int Wert für aktuelle Blickrichtung. Hierbei werden die Key ID's
	 * verwendet, welche normalerweise von einem Input Objekt übergeben werden.
	 * Default Wert ist Blickrichtung nach unten. protected, da Erbende Klassen
	 * darauf zugreifen müssen
	 */
	protected int currentView = Input.KEY_S;

	/**
	 * Variable zum Speichern, ob Entität im Aktuellem Loop gegen einen Soliden
	 * Block läuft. Protected, damit Player Klasse diesen Wert auslesen kann, um
	 * Solid Sound abspielen zu können
	 */
	protected boolean moveAgainstSolid = false;

	/**
	 * Variable zum Abspeichern, welcher Frame beim letzten Durchlauf, bei
	 * Bewegung gegen einen Soliden block abgespielt wurde. Wird in renderTick()
	 * verwendet um moveAgainstSolid animation abzubrechen. Initialisierungswert
	 * ist erster Frame
	 */
	private int lastSolidFrame = 0;

	private boolean blockedDelta = true;

	Entity(int posX, int posY, int width, int height, String imagePath) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;

		// Bilddatei mit Animationsabläufen
		try {
			image = new Image(imagePath);
		} catch (SlickException e) {
			System.err.println("Error while loading character images");
			e.printStackTrace();
		}

		// Aktueller Standort in Tiles (y+1, da Position von Körper der Entität
		// abhängt
		tileY = posY / Core.tileSize + 1;
		tileX = posX / Core.tileSize;

		// Aktuelle Position blocken
		Map.setBlocked(tileX, tileY, true);

		// Animationsbilder laden (Köpfe) (Laufanimationen)
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

		// Animationsbilder laden (Körper) (Laufanimationen)
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
		// (Körper)
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

		// Animatinsbilder Laden (Standanimationen) (Körper)
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

		// aniStandLeftHead.setLooping(false);
		// aniStandRightHead.setLooping(false);
		// aniStandUpHead.setLooping(false);
		// aniStandDownHead.setLooping(false);
		// aniStandLeftBody.setLooping(false);
		// aniStandRightBody.setLooping(false);
		// aniStandUpBody.setLooping(false);
		// aniStandLeftBody.setLooping(false);
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
		calcTilePosition(posX, true);
		Map.setBlocked(tileX, tileY, true);
	}

	public void setPosY(int posY) {
		isRunning = false;
		this.posY = posY;
		Map.setBlocked(tileX, tileY, false); // alte Position freigeben
		calcTilePosition(posY, false);
		Map.setBlocked(tileX, tileY, true);
	}

	public void setPosition(int posX, int posY) {
		isRunning = false;
		calcTilePosition(posX, true);
		calcTilePosition(posY, false);
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

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void calcTilePosition(int posPixel, boolean direction) {
		// direction == true (X) direction == false (Y)
		if (direction)
			tileX = posPixel / Core.tileSize;
		else
			this.tileY = (posPixel / Core.tileSize + 1);

	}

	private void calcTileX(int posX) {
		tileX = posX / Core.tileSize;
	}

	private void calcTileY(int posY) {
		tileY = posY / Core.tileSize + 1;
	}

	/**
	 * Einfache Update Methode für posX (Pixelgenauer) Standort Hierbei wird
	 * moveSpeed dem posX Wert ab- bzw. hinzugefügt
	 * 
	 * @param tileX
	 *            (int) Wert normalerweise -1 (Bewegung nach links), 1 (Bewegung
	 *            nach rechts), außer größere Sprünge sind beabsichtigt
	 * @return void
	 */
	public void moveX(int tileX) {
		posX += tileX * moveSpeed;

		/* Alter Quellcode *//*
							 * // Überprüft, ob Player bereits in Bewegung ist.
							 * Wenn ja wird Bewegung // bis zum nächsten Tile
							 * vortgesetzt if (isRunning) { if (posX %
							 * Core.tileSize == 0) { isRunning = false;
							 * Map.setBlocked(this.tileX, tileY, false);
							 * this.tileX = (tileX < 0) ? this.tileX - 1 :
							 * this.tileX + 1; } else { posX = (tileX < 0) ?
							 * posX - moveSpeed : posX + moveSpeed; } } //
							 * Player noch nicht in Bewegung, soll aber Schritte
							 * machen else if (tileX != 0) { // Überprüfen ob
							 * nächster Tile blocked ist if
							 * (!Map.isBlocked(this.tileX + tileX, tileY)) {
							 * Map.setBlocked(this.tileX + tileX, tileY, true);
							 * lastDir = (tileX < 0) ? Direction.LEFT :
							 * Direction.RIGHT; isRunning = true; isStanding =
							 * false; posX = (tileX < 0) ? posX - moveSpeed :
							 * posX + moveSpeed; } // Entity will gegen einen
							 * Soliden Block laufen else { lastDir = (tileX < 0)
							 * ? Direction.LEFT : Direction.RIGHT; isStanding =
							 * false;
							 * 
							 * // blockedDelta = aniMoveLeftHead.getFrame() == 1
							 * // || aniMoveRightHead.getFrame() == 1; // if
							 * (blockedDelta && (aniMoveLeftHead.getFrame() == 0
							 * || aniMoveRightHead .getFrame() == 0)) {
							 * Sound.audioSolid.playAsSoundEffect(1.0f, 1.0f,
							 * false); blockedDelta = false; if
							 * (aniMoveLeftHead.getFrame() == 1 ||
							 * aniMoveRightHead.getFrame() == 1) { blockedDelta
							 * = true; } // if(aniMoveLeftHead.getFrame() == 2
							 * && blockedDelta == // true || //
							 * aniMoveRightHead.getFrame() == 2 && blockedDelta
							 * == // true){ } } } // Standanimation else {
							 * aniMoveLeftHead.restart();
							 * aniMoveRightHead.restart(); isStanding = true; }
							 */
	}

	/**
	 * Einfache Update Methode für posY (Pixelgenauer) Standort Hierbei wird
	 * moveSpeed dem posY Wert ab- bzw. hinzugefügt
	 * 
	 * @param tileY
	 *            (int) Wert normalerweise -1 (Bewegung nach oben), 1 (Bewegung
	 *            nach unten), außer größere Sprünge sind beabsichtigt
	 * @return void
	 */
	public void moveY(int tileY) {
		posY += tileY * moveSpeed;

		/* Alter Quellcode *//*
							 * // Überprüft, ob Player bereits in Bewegung ist.
							 * Wenn ja wird Bewegung // bis zum nächsten Tile
							 * vortgesetzt if (isRunning) { if (posY %
							 * Core.tileSize == 0) { isRunning = false;
							 * Map.setBlocked(tileX, this.tileY, false);
							 * this.tileY = (tileY < 0) ? this.tileY - 1 :
							 * this.tileY + 1; } else { posY = (tileY < 0) ?
							 * posY - moveSpeed : posY + moveSpeed; } } //
							 * Entity noch nicht in Bewegung, soll aber Schritte
							 * machen else if (tileY != 0) { // Überprüfen ob
							 * nächster Tile blocked ist if
							 * (!Map.isBlocked(tileX, this.tileY + tileY)) {
							 * Map.setBlocked(tileX, tileY + this.tileY, true);
							 * lastDir = (tileY < 0) ? Direction.UP :
							 * Direction.DOWN; isRunning = true; isStanding =
							 * false; posY = (tileY < 0) ? posY - moveSpeed :
							 * posY + moveSpeed; } // Entity will gegen einen
							 * Soliden Block laufen else { lastDir = (tileY < 0)
							 * ? Direction.UP : Direction.DOWN; isStanding =
							 * false;
							 * 
							 * if (aniMoveDownHead.getFrame() == 1 ||
							 * aniMoveUpHead.getFrame() == 1) { blockedDelta =
							 * true; } if (aniMoveDownHead.getFrame() == 2 &&
							 * blockedDelta == true || aniMoveUpHead.getFrame()
							 * == 2 && blockedDelta == true) {
							 * Sound.audioSolid.playAsSoundEffect(1.0f, 1.0f,
							 * false); blockedDelta = false; }
							 * 
							 * } } // Standanimation else {
							 * aniMoveLeftHead.restart();
							 * aniMoveRightHead.restart(); isStanding = true; }
							 */
	}

	private boolean isBlocked(int tileX, int tileY) {
		return false;
	}

	public boolean collideWith(Entity me, Entity him) { // Fraglich ob benoetigt
		// TODO
		return false;
	}

	/** Wo kann ich die Methode nutzen -.- */
	// TODO
	private int getDirection(Input input) {
		if (input.isKeyPressed(Input.KEY_A))
			return 30;
		if (input.isKeyPressed(Input.KEY_W))
			return 17;
		if (input.isKeyPressed(Input.KEY_D))
			return 32;
		if (input.isKeyPressed(Input.KEY_S))
			return 31;
		return 0;
	}

	protected void renderEntityHead() {
		// Sollte currentAnimationHead == null sein wird Standbild von aktueller
		// Blickrichtung gerendert
		if (currentAnimationHead == null)
			renderStand();
		renderEntity(posX, posY, true);
		/*
		 * if (standAnimation) { if (standDir == Direction.UP &&
		 * aniTurnUpHead.isStopped() || standDir == Direction.DOWN &&
		 * aniTurnDownHead.isStopped() || standDir == Direction.LEFT &&
		 * aniTurnLeftHead.isStopped() || standDir == Direction.RIGHT &&
		 * aniTurnRightHead.isStopped()) { standAnimation = false;
		 * aniTurnUpHead.restart(); aniTurnDownHead.restart();
		 * aniTurnRightHead.restart(); aniTurnLeftHead.restart(); } }
		 */
	}

	/*
	 * 
	 * if (!isStanding) { // Bewegungsanimation if (lastDir == LastDir.LEFT) {
	 * aniMoveLeftHead.draw(posX, posY); } else if (lastDir == LastDir.UP) {
	 * aniMoveUpHead.draw(posX, posY);
	 * 
	 * } else if (lastDir == LastDir.RIGHT) { aniMoveRightHead.draw(posX, posY);
	 * 
	 * } else if (lastDir == LastDir.DOWN) { aniMoveDownHead.draw(posX, posY);
	 * 
	 * } } else if (standAnimation) { if (standDir == LastDir.LEFT)
	 * aniTurnLeftHead.draw(posX, posY); else if (standDir == LastDir.UP)
	 * aniTurnUpHead.draw(posX, posY); else if (standDir == LastDir.RIGHT)
	 * aniTurnRightHead.draw(posX, posY); else if (standDir == LastDir.DOWN)
	 * aniTurnDownHead.draw(posX, posY);
	 * 
	 * if (standDir == LastDir.UP && aniTurnUpHead.isStopped() || standDir ==
	 * LastDir.DOWN && aniTurnDownHead.isStopped() || standDir == LastDir.LEFT
	 * && aniTurnLeftHead.isStopped() || standDir == LastDir.RIGHT &&
	 * aniTurnRightHead.isStopped()) { standAnimation = false;
	 * aniTurnUpHead.restart(); aniTurnDownHead.restart();
	 * aniTurnRightHead.restart(); aniTurnLeftHead.restart(); } } else { //
	 * Stehbild if (lastDir.equals(LastDir.LEFT)) { image.getSubImage(0, 2 *
	 * height, width, height) .draw(posX, posY); } else if
	 * (lastDir.equals(LastDir.UP)) { image.getSubImage(0, 0, width,
	 * height).draw(posX, posY); } else if (lastDir.equals(LastDir.RIGHT)) {
	 * image.getSubImage(1 * width, 0, width, height).draw(posX, posY); } else
	 * if (lastDir.equals(LastDir.DOWN)) { image.getSubImage(2 * width, 1 *
	 * height, width, height).draw( posX, posY); } } }
	 */

	private Animation getAnimation(Boolean headBody) {
		if (!headBody) {
			if (!isStanding) {
				// Bewegungsanimation
				if (lastDir == Direction.LEFT)
					return aniMoveLeftBody;
				else if (lastDir == Direction.UP)
					return aniMoveUpBody;
				else if (lastDir == Direction.RIGHT)
					return aniMoveRightBody;
				else if (lastDir == Direction.DOWN)
					return aniMoveDownBody;
			}
			// Drehbewegung
			else if (standAnimation) {
				if (standDir == Direction.LEFT)
					return aniTurnLeftBody;
				else if (standDir == Direction.UP)
					return aniTurnUpBody;
				else if (standDir == Direction.RIGHT)
					return aniTurnRightBody;
				else if (standDir == Direction.DOWN)
					return aniTurnDownBody;
			} else {
				// Stehbild
				if (lastDir.equals(Direction.LEFT)) {
					return aniStandLeftBody;
				} else if (lastDir.equals(Direction.UP)) {
					return aniStandUpBody;
				} else if (lastDir.equals(Direction.RIGHT)) {
					return aniStandRightBody;
				} else if (lastDir.equals(Direction.DOWN)) {
					return aniStandDownBody;
				}
			}
		} else {
			if (!isStanding) {
				// Bewegungsanimation
				if (lastDir == Direction.LEFT)
					return aniMoveLeftHead;
				else if (lastDir == Direction.UP)
					return aniMoveUpHead;
				else if (lastDir == Direction.RIGHT)
					return aniMoveRightHead;
				else if (lastDir == Direction.DOWN)
					return aniMoveDownHead;
			}
			// Stehbild
			else if (standAnimation) {
				if (standDir == Direction.LEFT)
					return aniTurnLeftHead;
				else if (standDir == Direction.UP)
					return aniTurnUpHead;
				else if (standDir == Direction.RIGHT)
					return aniTurnRightHead;
				else if (standDir == Direction.DOWN)
					return aniTurnDownHead;
			} else {
				// Stehbild
				if (lastDir.equals(Direction.LEFT)) {
					return aniStandLeftHead;
				} else if (lastDir.equals(Direction.DOWN)) {
					return aniStandDownHead;
				} else if (lastDir.equals(Direction.UP)) {
					return aniStandUpHead;
				} else if (lastDir.equals(Direction.RIGHT)) {
					return aniStandRightHead;
				}
			}
		}
		return null;
	}

	/**
	 * Laden des Standbildes der Entität. Wird verwendet, wenn die Entität auf
	 * der Stelle stehen bleibt, ohne eine Aktion auszuführen. Parameter
	 * 'isRunning' wird dabei auf 'false' belassen, sodass weitere
	 * Bewegungsbefehle angenommen werden können. Sollte 'isRunning' beim Start
	 * der Methode auf 'true' stehen, wird diese abgebrochen und eine
	 * Fehlermeldung ausgegeben.
	 * 
	 * @return void
	 */
	protected void renderStand() {
		// Nochmalige Kontrolle ob Entität im Moment keine Bewegung ausführt
		if (!isRunning) {
			// Aktuelle Blickrichtung der Variable currentView übergeben
			// Aktuelle Animation der Temporären Variable currenAnimation
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

			// Keine weiteren Berechnungen wie Kollisionsberechnung nötig, da
			// Entität auf der Stelle stehen bleibt
		}
		// Sollte isRunning true sein wird eine Warnung ausgegeben
		else {
			System.err
					.println("isRunning == true, during method renderStand() in Class Entity");
		}
	}

	/**
	 * Laden der Bewegungsanimation der Entität. Wird verwendet wenn die Entität
	 * die aktuelle Position ändert. Parameter 'isRunning' wird dabei auf 'true'
	 * gesetzt, sodass keine weiteren Bewegungsbefehleangenommen werden können,
	 * bis die Entität die Zielposition erreicht hat. Sollte 'isRunning' beim
	 * Start der Methode auf 'true' stehen, wird diese abgebrochen und eine
	 * Fehlermeldung ausgegeben.
	 * 
	 * @param input
	 *            (int) Key ID der Tastatureingabe, bzw. Bewegungsanweisung bei
	 *            NPCs.
	 * @return void
	 */
	protected void renderMoveAnimation(int input) {
		// Merke: sollte Entität versuchen gegen Solid block zu laufen wird
		// isRunning trotzdem auf True gesetzt und die Bewegungsanimation ein
		// mal durchgespielt. Zusätzlich mus der Solid sound abgespielt werden.

		// Nochmalige Kontrolle ob Entität im moment keine Bewegung ausführt
		if (!isRunning) {
			// Aktuelle Blickrichtung der Variable currentView übergeben
			currentView = input;

			// Variable isRunning auf true setzen, sodass keine weiteren
			// Bewegungsabläufe angenommen werden
			isRunning = true;

			// Variable isStanding auf false setzen, sodass Methode renderTick
			// erkennt, dass im moment eine Bewegung ausgeführt wird
			isStanding = false;

			// Überprüfen ob nächster Block Solid ist. Wenn nicht wird dieser
			// Reserviert. und Entität wird einige Pixel bewegt Wenn Solid, wird
			// die Varible moveAgainstSolid auf true gesetzt Zusätzlich werden
			// die Animation in currentAnimation geladen
			if (input == Input.KEY_A) {
				currentAnimationHead = aniMoveLeftHead;
				currentAnimationBody = aniMoveLeftBody;
				if (!Map.isBlocked(tileX - 1, tileY)) {
					Map.setBlocked(tileX - 1, tileY, true);
					moveX(-1);
				} else
					moveAgainstSolid = true;
			} else if (input == Input.KEY_W) {
				currentAnimationHead = aniMoveUpHead;
				currentAnimationBody = aniMoveUpBody;
				if (!Map.isBlocked(tileX, tileY - 1)) {
					Map.setBlocked(tileX, tileX - 1, true);
					moveY(-1);
				} else
					moveAgainstSolid = true;
			} else if (input == Input.KEY_D) {
				currentAnimationHead = aniMoveRightHead;
				currentAnimationBody = aniMoveRightBody;
				if (!Map.isBlocked(tileX + 1, tileY)) {
					Map.setBlocked(tileX + 1, tileY, true);
					moveX(1);
				} else
					moveAgainstSolid = true;

			} else if (input == Input.KEY_S) {
				currentAnimationHead = aniMoveDownHead;
				currentAnimationBody = aniMoveDownBody;
				if (!Map.isBlocked(tileX, tileY + 1)) {
					Map.setBlocked(tileX, tileY + 1, true);
					moveY(1);
				} else
					moveAgainstSolid = true;
			}
		}
		// Sollte isRunning true sein wird eine Warnung ausgegeben
		else
			System.err
					.println("isRunning == true, during method renderMoveAnimation() in Class Entity");
	}

	/**
	 * Laden der Drehanimation der Entität. Wird verwendet, wenn die Entität die
	 * Blickrichtung ändert. Parameter 'isRunning' wird dabei auf 'true'
	 * gesetzt, sodass keine weiteren Bewegungsbefehle angenommen werden können,
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
		// Nochmalige Kontrolle ob Entität im moment keine Bewegung ausführt
		if (!isRunning) {
			// Aktuelle Blickrichtung der Variable currentView übergeben
			currentView = input;

			// Variable isRunning auf true setzen, sodass keine weiteren
			// Bewegungsabläufe angenommen werden
			isRunning = true;

			// Aktuelle Animation der Temporären Variable currenAnimation
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

			// Keine Kollisionsberechnung nötig, da Entität auf Position stehen
			// bleibt
		}
		// Sollte isRunning true sein wird eine Warnung ausgegeben
		else
			System.err
					.println("isRunning == true, during method renderTurnAnimation in Class Entity");
	}

	/**
	 * Tick Methode für Render Vorgänge. Soll ausgeführt werden, wenn isRunning
	 * auf true steht. Diese überprüft ob aktueller Bewegungsvorgang
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
		if (isRunning) { // TODO
			// Überprüfe, welcher Bewegungsvorgang aktuell abgearbeitet wird
			// move Bewegung wird ausgeführt
			if (!isStanding) {
				// Überprüfe, ob Entität im moment gegen einen Soliden Block
				// läuft
				if (moveAgainstSolid) {
					// Überprüfe, ob aktuelle animation abgeschlossen ist. Ist
					// dies der Fall, ist Bewegung abgeschlossen und
					// Werte/Animationen können zurückgesetzt werden
					if (currentAnimationBody.getFrame() == 0 && lastSolidFrame == currentAnimationBody.getFrameCount()-1) { // TODO
						isRunning = false;
						isStanding = true;
						moveAgainstSolid = false;
						lastSolidFrame = 0;

						// Animationen neustarten, sodass bei nächsten abspielen
						// kein Probleme auftreten können
						currentAnimationBody.restart();
						currentAnimationHead.restart();

						// Temporäre Reference Variable wieder freigeben
						currentAnimationBody = null;
						currentAnimationHead = null;
					}
					//Animation wird noch ausgeführt
					else{
						lastSolidFrame = currentAnimationBody.getFrame();
					}
				}
				// Entität bewegt sich auf neue Position
				else {
					// Überprüfen ob Entität angekommen ist
					// Bewegung auf X-Achse
					if (currentView == Input.KEY_A
							|| currentView == Input.KEY_D) {
						// Ist aktuelle X Position moduli Tile größe 0, wurde
						// Ziel erreicht
						if (posX % Core.tileSize == 0) {
							// Ursprungs Tile wieder freigeben
							Map.setBlocked(tileX, tileY, false);

							// Neue Tileposition berechnen
							calcTileX(posX);

							// isRunning false setzen, sodass im nächsten
							// Schritt keine Fehlermeldung ausgegeben wird
							isRunning = false;
							isStanding = true;

							// Überprüfe ob Bewegung weitergeführt werden soll
							// (Wenn input = 0 => keine Key eingabe, siehe
							// Player.getKeyValue()
							if (input != 0) {
								// Rufe renderMoveAnimation() methode auf, um
								// Bewegung vortzusetzen
								renderMoveAnimation(input);
							}
							// Keine Key eingabe.
							// Animationen neustarten, sodass bei nächsten
							// abspielen kein Probleme auftreten können
							else {
								currentAnimationBody.restart();
								currentAnimationHead.restart();

								// Temporäre Reference Variable wieder freigeben
								currentAnimationBody = null;
								currentAnimationHead = null;
							}
						}
						// Entität noch in Bewegung
						else {
							if (currentView == Input.KEY_A)
								moveX(-1);
							else if (currentView == Input.KEY_D)
								moveX(1);
						}
					}
					// Bewegung auf Y-Achse
					else {
						// Ist aktuelle Y Position moduli Tilegröße 0, wurde
						// Ziel erreicht
						if (posY % Core.tileSize == 0) {
							// Ursprungs Tile wieder freigeben
							Map.setBlocked(tileX, tileY, false);

							// Neue Tileposition berechnen
							calcTileY(posY);

							// isRunning false setzen, sodass im nächsten
							// Schritt keine Fehlermeldung ausgegeben wird
							isRunning = false;
							isStanding = true;

							// Überprüfe ob Bewegung weitergeführt werden soll
							// (Wenn input == 0 => keine Key eingabe, siehe
							// Player.getKeyValue()
							if (input != 0) {
								// Rufe renderMoveAnimation() methode auf, um
								// Bewegung vortzusetzen
								renderMoveAnimation(input);
							}
							// Keine Key eingabe. Werte können zurückgesetzt
							// werden
							// Animationen neustarten, sodass bei nächsten
							// abspielen
							// kein Probleme auftreten können
							currentAnimationBody.restart();
							currentAnimationHead.restart();

							// Temporäre Reference Variable wieder freigeben
							currentAnimationBody = null;
							currentAnimationHead = null;
						}
						// Entität noch in Bewegung
						else {
							if (currentView == Input.KEY_W)
								moveY(-1);
							else if (currentView == Input.KEY_S)
								moveY(1);
						}
					}
				}
			}

			// Turn Bewegung wird ausgeführt
			else {
				// Überprüfen ob aktuelle Animation abgeschlossen ist
				if (currentAnimationBody.isStopped()) {
					isRunning = false;

					// Animationen neustarten, sodass bei nächsten abspielen
					// kein Probleme auftreten können
					currentAnimationBody.restart();
					currentAnimationHead.restart();

					currentAnimationBody = null;
					currentAnimationHead = null;
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
			if (currentAnimationBody != null)
				currentAnimationBody.draw(posX, posY);
		} else {
			if (currentAnimationHead != null)
				currentAnimationHead.draw(posX, posY);
		}
	}

	protected void renderEntityBody() {
		// Sollte currentAnimationBody == null sein wird Standbild von aktueller
		// Blickrichtung gerendert
		if (currentAnimationBody == null)
			renderStand();
		renderEntity(posX, posY + height / 2, false);
		/* Alter Quellcode */
		/*
		 * if (standAnimation) { if (standDir == Direction.UP &&
		 * aniTurnUpBody.isStopped() || standDir == Direction.DOWN &&
		 * aniTurnDownBody.isStopped() || standDir == Direction.LEFT &&
		 * aniTurnLeftBody.isStopped() || standDir == Direction.RIGHT &&
		 * aniTurnRightBody.isStopped()) { standAnimation = false;
		 * aniTurnUpBody.restart(); aniTurnDownBody.restart();
		 * aniTurnRightBody.restart(); aniTurnLeftBody.restart(); } }
		 */
	}

	/*
	 * if (!isStanding) { // Bewegungsanimation if (lastDir == LastDir.LEFT) {
	 * aniMoveLeftBody.draw(posX, posY+height/2); } else if (lastDir ==
	 * LastDir.UP) { aniMoveUpBody.draw(posX, posY+height/2);
	 * 
	 * } else if (lastDir == LastDir.RIGHT) { aniMoveRightBody.draw(posX,
	 * posY+height/2);
	 * 
	 * } else if (lastDir == LastDir.DOWN) { aniMoveDownBody.draw(posX,
	 * posY+height/2);
	 * 
	 * } } else if (standAnimation) { if (standDir == LastDir.LEFT)
	 * aniTurnLeftBody.draw(posX, posY+height/2); else if (standDir ==
	 * LastDir.UP) aniTurnUpBody.draw(posX, posY+height/2); else if (standDir ==
	 * LastDir.RIGHT) aniTurnRightBody.draw(posX, posY+height/2); else if
	 * (standDir == LastDir.DOWN) aniTurnDownBody.draw(posX, posY+height/2);
	 * 
	 * if (standDir == LastDir.UP && aniTurnUpHead.isStopped() || standDir ==
	 * LastDir.DOWN && aniTurnDownBody.isStopped() || standDir == LastDir.LEFT
	 * && aniTurnLeftBody.isStopped() || standDir == LastDir.RIGHT &&
	 * aniTurnRightBody.isStopped()) { standAnimation = false;
	 * aniTurnUpBody.restart(); aniTurnDownBody.restart();
	 * aniTurnRightBody.restart(); aniTurnLeftBody.restart(); } } else { //
	 * Stehbild if (lastDir.equals(LastDir.LEFT)) { image.getSubImage(0, 2 *
	 * height, width, height) .draw(posX, posY); } else if
	 * (lastDir.equals(LastDir.UP)) { image.getSubImage(0, 0, width,
	 * height).draw(posX, posY); } else if (lastDir.equals(LastDir.RIGHT)) {
	 * image.getSubImage(1 * width, 0, width, height).draw(posX, posY); } else
	 * if (lastDir.equals(LastDir.DOWN)) { image.getSubImage(2 * width, 1 *
	 * height, width, height).draw( posX, posY); } } }
	 */
	/*
	 * public void updateEntity(Input input, boolean standAnimation, int steps)
	 * { this.standAnimation = standAnimation; if (standDir == LastDir.LEFT)
	 * lastDir = LastDir.LEFT; else if (standDir == LastDir.RIGHT) lastDir =
	 * LastDir.RIGHT; else if (standDir == LastDir.UP) lastDir = LastDir.UP;
	 * else if (standDir == LastDir.DOWN) lastDir = LastDir.DOWN;
	 * 
	 * this.updateEntity(input, steps); }
	 */

	public void updateEntity(int input, boolean standAnimation, int steps) {
		this.standAnimation = standAnimation;
		this.updateEntity(input, steps);
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
		// Keine Key Listener eingabe, Animationen werden zurückgesetzt;
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
	/**
	 * Update Methode des Objektes Entity
	 * 
	 * @param input
	 *            (Input) Key Listener Variable
	 * 
	 * @param steps
	 *            (int) Wie viele Tiles soll die Entity laufen
	 * 
	 * @return void
	 */
	/*
	 * public void updateEntity(Input input, int steps) { // Ist Entity bereits
	 * in bewegung if (isRunning) { if (lastDir == LastDir.LEFT) moveX(-steps);
	 * else if (lastDir == LastDir.RIGHT) moveX(steps); else if (lastDir ==
	 * LastDir.DOWN) moveY(steps); else if (lastDir == LastDir.UP)
	 * moveY(-steps); } // Noch nicht in Bewegung jedoch Key Listener eingabe
	 * else if (input.isKeyDown(Input.KEY_A)) moveX(-steps); else if
	 * (input.isKeyDown(Input.KEY_D)) moveX(steps); else if
	 * (input.isKeyDown(Input.KEY_S)) moveY(steps); else if
	 * (input.isKeyDown(Input.KEY_W)) moveY(-steps); // Keine Key Listener
	 * eingabe, Animationen werden zurückgesetzt; // Bewegungszustand auf stehen
	 * gesetzt else { isRunning = false; isStanding = true;
	 * aniMoveLeftHead.restart(); aniMoveUpHead.restart();
	 * aniMoveRightHead.restart(); aniMoveDownHead.restart();
	 * 
	 * aniMoveLeftBody.restart(); aniMoveUpBody.restart();
	 * aniMoveRightBody.restart(); aniMoveDownBody.restart();
	 * 
	 * } }
	 */
	/*
	 * //Überprüft, ob Player bereits in Bewegung ist. Wenn ja wird bewegung bis
	 * zum nächsten Tile vortgesetzt if(isRunning){ if(lastDir == LastDir.DOWN){
	 * if(posY % Core.tileSize == 0){ isRunning = false; tileY++; } else { posY
	 * += moveSpeed; } } else if(lastDir == LastDir.UP){ if(posY % Core.tileSize
	 * == 0){ isRunning = false; tileY--; } else { posY -= moveSpeed; }
	 * 
	 * } else if (lastDir == LastDir.LEFT){ if(posX % Core.tileSize == 0){
	 * isRunning = false; tileX--; } else { posX -= moveSpeed; } } else
	 * if(lastDir == LastDir.RIGHT){ if(posX % Core.tileSize == 0){ isRunning =
	 * false; tileX++; } else { posX += moveSpeed; } } } else
	 * if(input.isKeyDown(Input.KEY_A)){ lastDir = LastDir.LEFT; isRunning =
	 * true; isStanding = false; posX -= moveSpeed; }
	 * 
	 * else if(input.isKeyDown(Input.KEY_W)){ lastDir = LastDir.UP; isRunning =
	 * true; isStanding = false; posY -= moveSpeed; }
	 * 
	 * else if(input.isKeyDown(Input.KEY_D)){ lastDir = LastDir.RIGHT; isRunning
	 * = true; isStanding = false; posX += moveSpeed; }
	 * 
	 * else if(input.isKeyDown(Input.KEY_S)){ lastDir = LastDir.DOWN; isRunning
	 * = true; isStanding = false; posY += moveSpeed; } else {
	 * aniMoveLeftHead.restart(); aniMoveUpHead.restart();
	 * aniMoveRightHead.restart(); aniMoveDownHead.restart(); isStanding = true;
	 * }
	 */
}