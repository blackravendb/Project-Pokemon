package de.pokemon;

import org.newdawn.slick.Input;

/**
 * Klasse zum erstellen von Npc's. Erbt von Entity. Wird durch Map Klasse mit
 * Npc Namen als Parameter aufgerufen. Daraufhin abfrage an den ResourceManager
 * nach Rendergrafik und Bewegungsmuster. Bewegungseingaben werden an die Entity
 * Klasse weitergegeben.
 * 
 * @author Dennis
 */
public class Npc extends Entity {
	/**
	 * Variable zum Speichern der Laufroute. Informtionen aus dem
	 * ResourceManager werden in dieser abgelegt.
	 */
	private int[][] route;

	/**
	 * Name des Npc's. Wird unter anderem als Indikator im ResourceManager
	 * verwendet
	 */
	private String name;

	/**
	 * Countervariable wie viel Befehle der Npc in aktueller Bewegungsanimation
	 * noch abarbeiten muss. Z.B. millisekunden im Wait-Befehl oder
	 * Schrittanzahl bei Laufbewegung.
	 */
	private int counter = 0;

	/**
	 * Countervariable in welcher Bewegungsanimation sich der Npc im Moment
	 * befindet
	 */
	private int step = 0;

	/**
	 * Konstruktor Npc
	 * 
	 * @param posX
	 *            (int) Start X Position des Npc's gemessen Kopf oben links
	 * @param posY
	 *            (int) Start Y Position des Npc's gemessen Kopf oben links
	 * @param name
	 *            (String) Name des Npc's und Referenz für übrige Werte
	 * @param event
	 *            (EventManager) Referenz auf EventManager. Wird benötigt um
	 *            aktuelle Position dem EventManager zu übergeben.
	 * @return void
	 */
	Npc(int posX, int posY, String name, EventManager event) {
		super(posX, posY, Core.tileSize, Core.tileSize * 2, ResourceManager
				.getNpcImagePath(name), event);
		// Laufroute des NPCs ermitteln
		route = ResourceManager.getNpcRoute(name);
		this.name = name;

		// Position und Reference an Event übermitteln
		event.initNpc(this, this.name, getTileX(), getTileY());
	}

	/**
	 * Bewegungsanimation ermitteln und starten
	 * 
	 * @param command
	 *            (int) Bewegungsbefehl welcher der Npc ausführen soll
	 *            (0=Warten, 1=Links drehen, 2=Hoch drehen, 3=Rechts drehen,
	 *            4=Runter drehen, 5=Links laufen, 6=Hoch laufen, 7=Rechts
	 *            laufen, 8=Runter laufen
	 * @param duration
	 *            (int) wie lange der command befehl ausgeführt werden soll
	 *            (z.B. 3 Schritte laufen, 2000ms warten)
	 * @param delta
	 *            (int) Zeit, seit dem letzten update. Wird für wait befehl
	 *            benötigt.
	 * @return boolean true=Kommando wurde ausgeführt false=Kommando wird im
	 *         moment noch ausgeführt
	 */
	private boolean moveNpc(int command, int duration, int delta) {
		switch (command) {
		// Warten
		case 0:
			if (counter >= duration) {
				counter = 0;
				return true;
			} else {
				counter += delta;
				return false;
			}
			// Links drehen
		case 1:
			renderTurnAnimation(Input.KEY_A);
			return true;

			// Hoch drehen
		case 2:
			renderTurnAnimation(Input.KEY_W);
			return true;

			// Rechts drehen
		case 3:
			renderTurnAnimation(Input.KEY_D);
			return true;

			// Runter drehen
		case 4:
			renderTurnAnimation(Input.KEY_S);
			return true;

			// links laufen
		case 5:
			if (counter < duration) {
				// Kollision abfragen
				if (Map.isBlocked(getTileX() - 1, getTileY())) {
					if (currentView != Input.KEY_A)
						renderTurnAnimation(Input.KEY_A);
					return false;
				}
				renderMoveAnimation(Input.KEY_A);
				event.setNpcPosX(this, getTileX() - 1);
				counter++;
				return false;
			} else {
				counter = 0;
				return true;
			}

			// Hoch laufen
		case 6:
			if (counter < duration) {
				if (Map.isBlocked(getTileX(), getTileY() - 1)) {
					if (currentView != Input.KEY_W)
						renderTurnAnimation(Input.KEY_W);
					return false;
				}
				renderMoveAnimation(Input.KEY_W);
				event.setNpcPosY(this, getTileY() - 1);
				counter++;
				return false;
			} else {
				counter = 0;
				return true;
			}

			// Rechts laufen
		case 7:
			if (counter < duration) {
				if (Map.isBlocked(getTileX() + 1, getTileY())) {
					if (currentView != Input.KEY_D)
						renderTurnAnimation(Input.KEY_D);
					return false;
				}
				renderMoveAnimation(Input.KEY_D);
				event.setNpcPosX(this, getTileX() + 1);
				counter++;
				return false;
			} else {
				counter = 0;
				return true;
			}

			// Runter Laufen
		case 8:
			if (counter < duration) {
				if (Map.isBlocked(getTileX(), getTileY() + 1)) {
					if (currentView != Input.KEY_S)
						renderTurnAnimation(Input.KEY_S);
					return false;
				}
				renderMoveAnimation(Input.KEY_S);
				event.setNpcPosY(this, getTileY() + 1);
				counter++;
				return false;
			} else {
				counter = 0;
				return true;
			}
		}
		return true;
	}

	/**
	 * Updatemethode der Klasse Npc. Über diese werden die Bewegungsanimationen
	 * gesteuert
	 * 
	 * @param delta
	 *            (int) Zeit, seit dem letzten update. Wird für wait befehl
	 *            benötigt.
	 * @return void
	 */
	public void updateNpc(int delta) {
		if (route != null) {
			if (step < route.length) {
				if (!super.isRunning) {
					if (moveNpc(route[step][0], route[step][1], delta))
						step++;
				} else {
					super.renderTick(0);
				}
			} else
				step = 0;
		}
	}

	/**
	 * Kopf rendern
	 * 
	 * @return void
	 */
	public void renderNpcHead() {
		renderEntityHead();
	}

	/**
	 * Körper rendern
	 * 
	 * @return void
	 */
	public void renderNpcBody() {
		renderEntityBody();
	}

}
