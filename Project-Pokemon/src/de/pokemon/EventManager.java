package de.pokemon;

import java.util.ArrayList;
import org.newdawn.slick.Input;

/**
 * Zuständig für alle Events. Überprüft bei drücken der Event taste ob Player
 * sich vor einem Event befindet. Außerdem sendet jeder Npc nach jedem Schritt
 * seine aktuelle Position an diese Klasse
 * 
 * @author Dennis
 */
public class EventManager {
	/**
	 * Referenz der playState. Wird benötigt wenn ein Event ausgelöst wird um
	 * Methoden aufzurufen
	 */
	private PlayState playState;
	/**
	 * Referenz des Player Objekt. Wird benötigt um Skriptgesteuerte bewegungen
	 * weiterzugeben. Aktuell noch nicht im Einsatz
	 */
	@SuppressWarnings("unused")
	private Player player;
	private String dialog;

	/** Array Liste für alle aktuell geladenen Events */
	private ArrayList<EventStruct> events;

	/**
	 * @param playState
	 *            (PlayState) Referenz auf playState Objekt um Events auslösen
	 *            zu können
	 */
	EventManager(PlayState playState) {
		this.playState = playState;
		events = new ArrayList<EventStruct>();
	}

	/**
	 * Player übermittelt beim initialisieren seine Referenz mittels dieser
	 * Methode
	 * 
	 * @param player
	 *            (Player) player referenz
	 * @return void
	 */
	public void setPlayerReference(Player player) {
		this.player = player;
	}

	/**
	 * Sollte Event taste gedrückt werden ruft Player diese Methode auf und
	 * übermittelt hierbei seine aktuelle Position und Blickrichtung
	 * 
	 * @param tileX
	 *            (int) X Position des Players (tilegenau)
	 * @param tileY
	 *            (int) Y Position des Players (tilegenau)
	 * @param currentView
	 *            (int) Blickrichtung des Players, wobei Werte von Input.KEY_?
	 *            abgeleitet sind
	 * @return void
	 */
	public void sendActionRequest(int tileX, int tileY, int currentView) {
		// ausrechnen auf welches Tile Player "schaut"
		if (currentView == Input.KEY_A)
			tileX--;
		else if (currentView == Input.KEY_W)
			tileY--;
		else if (currentView == Input.KEY_D)
			tileX++;
		else if (currentView == Input.KEY_S)
			tileY++;

		// Scannen aller Events, ob sich eines vor Player befindet
		for (EventStruct entries : events) {
			if (entries.isOnPosition(tileX, tileY)) {
				// Übermitteln des Modifizierungseigenschaft des Events
				String modifyEntryName = entries.getModifyStage();

				// Übermitteln des Dialogs des Events
				dialog = entries.getCurrentDialog();

				// Überpüfen, ob Modifizierer gesetzt ist
				if (modifyEntryName != null) {
					for (EventStruct modifyEntry : events) {
						if (modifyEntry.getName().equals(modifyEntryName)) {
							modifyEntry.increaseStage();
							// Verlassen der for-Schleife (modify NPC gefunden)
							break;
						}
					}
				}
				// Übermitteln des Dialog Strings an PlayState
				playState.setDialogString(dialog);
				break;
			}
		}
	}

	/**
	 * Methode, fals Npc sich in X Richtung bewegt. Sobald Bewegungsschritt
	 * abgeschlossen ist wird diese Methde aufgerufen
	 * 
	 * @param npc
	 *            (Npc) Referenz des Npc's um diese in ArrayList zu
	 *            aktualisieren
	 * @param tileX
	 *            (int) neue X Position (tilegenau)
	 * @return void
	 */
	public void setNpcPosX(Npc npc, int tileX) {
		for (EventStruct npcObject : events) {
			if (npc == npcObject.getReference()) {
				npcObject.setTileX(tileX);
				break;
			}
		}
	}

	/**
	 * Methode, fals Npc sich in Y Richtung bewegt. Sobald Bewegungsschritt
	 * abgeschlossen ist wird diese Methde aufgerufen
	 * 
	 * @param npc
	 *            (Npc) Referenz des Npc's um diese in ArrayList zu
	 *            aktualisieren
	 * @param tileY
	 *            (int) neue Y Position (tilegenau)
	 * @return void
	 */
	public void setNpcPosY(Npc npc, int tileY) {
		for (EventStruct npcObject : events) {
			if (npc == npcObject.getReference()) {
				npcObject.setTileY(tileY);
				break;
			}
		}
	}

	/**
	 * Init Methode für neue Npcs. Hierbei wird überprüft, ob sich Npc bereits
	 * in ArrayList befindet. Wenn nicht wird ein neuer Eintrag hinzugefügt.
	 * 
	 * @param npc
	 *            (Npc) Referenz des aufrufenden Npc Objekts
	 * @param name
	 *            (String) Name des Npc Objekts
	 * @param tileX
	 *            (int) X-Position des Npc Objekts (tilegenau)
	 * @param tileY
	 *            (int) Y-Position des Npc Objekts (tilegenau)
	 * @return void
	 */
	public void initNpc(Npc npc, String name, int tileX, int tileY) {
		boolean isContained = false;

		// Überprüft, ob sich ein Npc Objekt mit gleichen Namen bereits in der
		// ArrayList events befindet. Wenn ja wird nur die Referenz &
		// Positionsdaten aktualisiert
		for (EventStruct npcObject : events) {
			if (npcObject.getName().equals(name)) {
				npcObject.setReference(npc);
				npcObject.setTileX(tileX);
				npcObject.setTileY(tileY);
				isContained = true;
				break;
			}
		}
		// Wenn sich NPC noch nicht in events befindet, wird ein neuer Eintrag
		// erzeugt
		if (!isContained)
			events.add(new EventStruct(npc, name, tileX, tileY));
	}
}
