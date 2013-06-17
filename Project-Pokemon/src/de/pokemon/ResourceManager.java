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
	// Npc Datenbank
	private static HashMap<String, NpcStruct> npcDatabase;

	public static void init() {
		npcDatabase = new HashMap<String, NpcStruct>();

		npcDatabase.put("mom", new NpcStruct("mom", "res/npc1.png",
				new int[][] { { moveSequence.moveLeft.ordinal(), 4 },
						{ moveSequence.wait.ordinal(), 1000 },
						{ moveSequence.turnDown.ordinal(), 0 },
						{ moveSequence.wait.ordinal(), 500 },
						{ moveSequence.moveRight.ordinal(), 4 },
						{ moveSequence.wait.ordinal(), 1000 },
						{ moveSequence.turnDown.ordinal(), 0 },
						{ moveSequence.wait.ordinal(), 500 } }));
		npcDatabase.put("supervisor",
				new NpcStruct("supervisor", "res/supervisor.png", new int[][] {
						{ moveSequence.turnRight.ordinal(), 0 },
						{ moveSequence.wait.ordinal(), 3000 },
						{ moveSequence.turnUp.ordinal(), 0 },
						{ moveSequence.wait.ordinal(), 3000 } }));
		npcDatabase.put("biker", new NpcStruct("biker", "res/biker.png",
				new int[][] { { moveSequence.moveUp.ordinal(), 3 },
						{ moveSequence.moveLeft.ordinal(), 4 },
						{ moveSequence.moveDown.ordinal(), 3 },
						{ moveSequence.moveRight.ordinal(), 4 } }));
		npcDatabase.put("captain", new NpcStruct("captain", "res/captain.png",
				new int[][] { { 3, 0 },
						{ moveSequence.wait.ordinal(), 2147483647 } }));
		npcDatabase.put("deubler1",
				new NpcStruct("deubler1", "res/deubler.png", new int[][] {
						{ moveSequence.turnLeft.ordinal(), 0 },
						{ moveSequence.wait.ordinal(), 1000 },
						{ moveSequence.turnDown.ordinal(), 0 },
						{ moveSequence.wait.ordinal(), 1000 },
						{ moveSequence.turnRight.ordinal(), 0 },
						{ moveSequence.wait.ordinal(), 1000 },
						{ moveSequence.turnDown.ordinal(), 0 },
						{ moveSequence.wait.ordinal(), 1000 } }));
		npcDatabase
				.put("holzerarmee", new NpcStruct("holzerarmee",
						"res/holzerarmee.png", new int[][] { {
								moveSequence.wait.ordinal(), 2147483647 } }));

	}

	public static String getNpcImagePath(String name) {
		return npcDatabase.get(name).getImagePath();
	}

	public static int[][] getNpcRoute(String name) {
		return npcDatabase.get(name).getNpcRoute();
	}

}
