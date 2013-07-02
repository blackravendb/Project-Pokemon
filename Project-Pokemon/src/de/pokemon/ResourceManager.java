/**
 * 
 */
package de.pokemon;

import java.util.HashMap;
import de.pokemon.MoveSequence.moveSequence;

/**
 * @author Dennis
 * 
 */
public class ResourceManager {
	// Bildpfad
	private final static String imagePath = "res/character/";
	// Npc Datenbank
	private static HashMap<String, NpcStruct> npcDatabase;

	public static void init() {
		npcDatabase = new HashMap<String, NpcStruct>();

		npcDatabase
		.put("mom",
				new NpcStruct(
						"mom",
						imagePath + "mom.png",
						new int[][] {
								{ moveSequence.moveLeft.ordinal(), 4 },
								{ moveSequence.wait.ordinal(), 1000 },
								{ moveSequence.turnDown.ordinal(), 0 },
								{ moveSequence.wait.ordinal(), 500 },
								{ moveSequence.moveRight.ordinal(), 4 },
								{ moveSequence.wait.ordinal(), 1000 },
								{ moveSequence.turnDown.ordinal(), 0 },
								{ moveSequence.wait.ordinal(), 500 } },
								new Event(
										new String[] {
												"Morgen, hast du gut geschlafen?",
										"Der Pokemon Professor hat gerade angerufen. Vor ner halben Stunde hattest du deine Projektvorstellung. Du solltest dich beeilen!" })));
		npcDatabase
		.put("dad",
				new NpcStruct(
						"dad",
						imagePath + "dad.png",
						new int[][] {
								{ moveSequence.turnUp.ordinal(), 0 },
								{ moveSequence.wait.ordinal(),
									2147483647 } },
									new Event(
											new String[] { "Servus, du, dei Muada is heid ganz außer sich. Sie hat irgendwas vo Projekt und FH gsagt. Ich glaub du soidst moi mit ihra redn." },
											new String[] { "mom" })));

		npcDatabase
		.put("supervisor",
				new NpcStruct(
						"supervisor",
						imagePath + "supervisor.png",
						new int[][] {
								{ moveSequence.turnRight.ordinal(), 0 },
								{ moveSequence.wait.ordinal(), 3000 },
								{ moveSequence.turnUp.ordinal(), 0 },
								{ moveSequence.wait.ordinal(), 3000 } },
								new Event(
										new String[] { "Du kommst hier nicht rein. Durch das Hochwasser letzten Monats ist der komplette Berg abgerutscht und hat den Höhleneingang verschüttet." })));

		npcDatabase
		.put("captain",
				new NpcStruct(
						"captain",
						imagePath + "captain.png",
						new int[][] {
								{ moveSequence.turnRight.ordinal(), 0 },
								{ moveSequence.wait.ordinal(),
									2147483647 } },
									new Event(
											new String[] { "Arrr, das Meer ist so ruhig heute. Ist denn schon wieder Weihnachten?" },
											new String[] { "skateboader" })));

		npcDatabase
		.put("deubler1",
				new NpcStruct(
						"deubler1",
						imagePath + "deubler.png",
						new int[][] {
								{ moveSequence.turnLeft.ordinal(), 0 },
								{ moveSequence.wait.ordinal(), 1000 },
								{ moveSequence.turnDown.ordinal(), 0 },
								{ moveSequence.wait.ordinal(), 1000 },
								{ moveSequence.turnRight.ordinal(), 0 },
								{ moveSequence.wait.ordinal(), 1000 },
								{ moveSequence.turnDown.ordinal(), 0 },
								{ moveSequence.wait.ordinal(), 1000 } },
								new Event(
										new String[] { "Mei, hundad moi hob ichs eich gsogt: \"Sads gfälligst pünktlich\". Naja, nachsts Jahr verschlafts vielleicht ned." }
										)));

		npcDatabase
		.put("holzerarmee",
				new NpcStruct("holzerarmee", imagePath
						+ "holzerarmee.png", new int[][] { {
							moveSequence.wait.ordinal(), 2147483647 } },
							new Event(new String[] { "HODOR" })));

		npcDatabase
		.put("skateboader",
				new NpcStruct(
						"skateboader",
						imagePath + "skateboader.png",
						new int[][] {
								{ moveSequence.moveUp.ordinal(), 3 },
								{ moveSequence.moveLeft.ordinal(), 4 },
								{ moveSequence.moveDown.ordinal(), 3 },
								{ moveSequence.moveRight.ordinal(), 4 } },
								new Event(
										new String[] {
												"Siehst du den alten Kapitän rechts? Der redet nur komisches Zeug",
										"Hab ichs dir nicht gesagt?" })));

		npcDatabase
		.put("student1",
				new NpcStruct(
						"student1",
						imagePath + "student1.png",
						new int[][] {
								{ moveSequence.turnLeft.ordinal(), 3 },
								{ moveSequence.wait.ordinal(),
									2147483647 } },
									new Event(
											new String[] { "Wie war eure Projektvorstellung?", "Zefix, für was braucht ma denn getter und setter, ich mach einfach ois public." }
											)));

		npcDatabase
		.put("student2",
				new NpcStruct(
						"student2",
						imagePath + "student2.png",
						new int[][] {
								{ moveSequence.turnRight.ordinal(), 3 },
								{ moveSequence.wait.ordinal(),
									2147483647 } },
									new Event(
											new String[] { "Woah, nächste Woche sind ja schon Prüfungen. Wie soll man denn bei dem Wetter lernen?" },
											new String[] { "student1" })));

	}

	public static String getNpcImagePath(String name) {
		return npcDatabase.get(name).getImagePath();
	}

	public static int[][] getNpcRoute(String name) {
		return npcDatabase.get(name).getNpcRoute();
	}

	public static String[] getDialog(String name) {
		return npcDatabase.get(name).getDialog();
	}

	public static String[] getModifyStage(String name) {
		return npcDatabase.get(name).getModifyStage();
	}

}
