package de.pokemon;

/**
 * Event Struktur zum zusammenfassen von Event Attributen
 * 
 * @author Dennis
 */
public class EventStruct {
	/** X Position des Events (tilegenau) */
	private int tileX;
	/** Y Position des Events (tilegenau) */
	private int tileY;
	/** Name des Events (bei Npc's) */
	private String name;
	/** Referenz des Eventinhabers */
	private Entity entityReference;

	/** Dialog Level */
	private int stage = 0;

	/** Variable zum laden der Dialoge */
	private String[] dialog;

	/** Variable zum laden der Event Mdifizierer */
	private String[] modifyStage;

	/**
	 * Event Struktur zum zusammenfassen von Event Attributen
	 * 
	 * @param entityReference
	 *            (Entity) Referenz zum Eventinhaber
	 * @param name
	 *            (String) Name des Eventinhabers (Wird benötigt, wenn Entity
	 *            neue Referenz hat)
	 * @param tileX
	 *            (int) start X-Position der Entität (tilegenau)
	 * @param tileY
	 *            (int) start Y-Position der Entität (tilegenau)
	 */
	EventStruct(Entity entityReference, String name, int tileX, int tileY) {
		this.entityReference = entityReference;
		this.name = name;
		this.tileX = tileX;
		this.tileY = tileY;
		//
		dialog = ResourceManager.getDialog(name);
		modifyStage = ResourceManager.getModifyStage(name);
	}

	public int getTileX() {
		return tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public Entity getReference() {
		return entityReference;
	}

	public int getStage() {
		return stage;
	}

	public String getName() {
		return name;
	}

	public String getModifyStage() {
		if (modifyStage[stage] != null) {
			String temp = modifyStage[stage];
			modifyStage[stage] = null;
			return temp;
		}
		return null;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}

	public void increaseStage() {
		stage++;
	}

	public String getCurrentDialog() {
		return dialog[stage];
	}

	public void setReference(Entity reference) {
		this.entityReference = reference;
	}

	/**
	 * Überpüft, ob Event auf Übermitteltet Position steht
	 * 
	 * @param tileX
	 *            X-Position (tilegenau)
	 * @param tileY
	 *            Y-Position (tilegenau)
	 * @return boolean true=Event befindet sich auf übermittelten Koordinaten
	 *         false=Event befindet sich nicht auf übermittelten Koordinten
	 */
	public boolean isOnPosition(int tileX, int tileY) {
		if (this.tileX == tileX && this.tileY == tileY)
			return true;

		return false;
	}

}