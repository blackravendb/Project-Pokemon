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

		npcDatabase.put("mom", new NpcStruct("mom", imagePath + "mom.png",
				new int[][] { { moveSequence.moveLeft.ordinal(), 4 },
						{ moveSequence.wait.ordinal(), 1000 },
						{ moveSequence.turnDown.ordinal(), 0 },
						{ moveSequence.wait.ordinal(), 500 },
						{ moveSequence.moveRight.ordinal(), 4 },
						{ moveSequence.wait.ordinal(), 1000 },
						{ moveSequence.turnDown.ordinal(), 0 },
						{ moveSequence.wait.ordinal(), 500 } }));
		npcDatabase.put("supervisor",
				new NpcStruct("supervisor", imagePath + "supervisor.png",
						new int[][] { { moveSequence.turnRight.ordinal(), 0 },
								{ moveSequence.wait.ordinal(), 3000 },
								{ moveSequence.turnUp.ordinal(), 0 },
								{ moveSequence.wait.ordinal(), 3000 } }));
		npcDatabase.put("biker", new NpcStruct("biker",
				imagePath + "biker.png", null));
		npcDatabase.put("captain",
				new NpcStruct("captain", imagePath + "captain.png",
						new int[][] { { 3, 0 },
								{ moveSequence.wait.ordinal(), 2147483647 } }));
		npcDatabase.put("deubler1",
				new NpcStruct("deubler1", imagePath + "deubler.png",
						new int[][] { { moveSequence.turnLeft.ordinal(), 0 },
								{ moveSequence.wait.ordinal(), 1000 },
								{ moveSequence.turnDown.ordinal(), 0 },
								{ moveSequence.wait.ordinal(), 1000 },
								{ moveSequence.turnRight.ordinal(), 0 },
								{ moveSequence.wait.ordinal(), 1000 },
								{ moveSequence.turnDown.ordinal(), 0 },
								{ moveSequence.wait.ordinal(), 1000 } }));
		npcDatabase
				.put("holzerarmee",
						new NpcStruct("holzerarmee", imagePath
								+ "holzerarmee.png", new int[][] { {
								moveSequence.wait.ordinal(), 2147483647 } }));

		npcDatabase.put("skateboader",
				new NpcStruct("skateboader", imagePath + "skateboader.png",
						new int[][] { { moveSequence.moveUp.ordinal(), 3 },
								{ moveSequence.moveLeft.ordinal(), 4 },
								{ moveSequence.moveDown.ordinal(), 3 },
								{ moveSequence.moveRight.ordinal(), 4 } }));

		npcDatabase.put("student1",
				new NpcStruct("student1", imagePath + "student1.png",
						new int[][] { { moveSequence.turnRight.ordinal(), 3 },
								{ moveSequence.wait.ordinal(), 2147483647 } }));

		npcDatabase.put("student2",
				new NpcStruct("student2", imagePath + "student2.png",
						new int[][] { { moveSequence.turnRight.ordinal(), 3 },
								{ moveSequence.wait.ordinal(), 2147483647 } }));

	}

	public static String getNpcImagePath(String name) {
		return npcDatabase.get(name).getImagePath();
	}

	public static int[][] getNpcRoute(String name) {
		return npcDatabase.get(name).getNpcRoute();
	}

}
