/**
 * 
 */
package de.pokemon;

import java.util.HashMap;

/**
 * @author Dennis
 *
 */
public class ResourceManager {
	//Npc Datenbank
	private static HashMap<String, NpcStruct> npcDatabase;
	
	public static void init(){
		npcDatabase = new HashMap<String, NpcStruct>();
		
	//	npcDatabase.put("mom", new NpcStruct("mom", "res/npc1.png", new int[][]{{5,0,4,0,7,0,4},{4,200,0,200,4,200,0}}));
		npcDatabase.put("mom", new NpcStruct("mom", "res/npc1.png", new int[][]{{5,4},{0,200},{4,0},{7,4},{0,200},{4,0}}));
		
	}
	
	public static String getNpcImagePath(String name){
		return npcDatabase.get(name).getImagePath();
	}
	
	public static int[][] getNpcRoute(String name){
		return npcDatabase.get(name).getNpcRoute();
	}

}
