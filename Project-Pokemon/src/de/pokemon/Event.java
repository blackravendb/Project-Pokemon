package de.pokemon;

/** Klasse zum Filtern von Eventbefehlen und Stage modifizierern */
public class Event {
	/** Gesprächsarray. Dialoge der Npc's werden in dieser Gespeichert */
	private String[] text;
	/**
	 * Stage modifizierer. gibt an, ob bestimmte Events einflüsse auf andere
	 * Eventquellen haben
	 * 
	 * @author Dennis
	 */
	private String[] modifyStage;

	/**
	 * Event Konstruktor für Events mit Stage modifizierer
	 * 
	 * @param text
	 *            (String[]) Sprech. bzw. Ausgabetexte von Eventquellen
	 * @param mdifyStage
	 *            (String[]) Stage Modifizierer. Gibt an ob textausgabe andere
	 *            Eventquellen beeinflusst
	 */
	Event(String[] text, String[] modifyStage) {
		this.text = text;
		this.modifyStage = modifyStage;
	}

	/**
	 * Event Konstruktor für Events ohne Stage modifizierer
	 * 
	 * @param text
	 *            (String[]) Sprech. bzw. Ausgabetexte von Eventquellen
	 */
	Event(String[] text) {
		this.text = text;
		modifyStage = new String[text.length];
	}

	public String[] getText() {
		return text;
	}

	public String[] getModifyStage() {
		return modifyStage;
	}
}