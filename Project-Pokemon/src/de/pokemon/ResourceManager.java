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
	int [][] test = new int[][]{{1},{2}};
	//Npc Datenbank
	private static HashMap<String, NpcStruct> npcDatabase;
	
	public static void init(){
		npcDatabase = new HashMap<String, NpcStruct>();
		
		npcDatabase.put("mom", new NpcStruct("mom", "res/npc1.png", new int[][]{{1},{2}}));		
	}
	
	public static String getNpcImagePath(String name){
		return npcDatabase.get(name).getImagePath();
	}
	
	public static int[][] getNpcRoute(String name){
		return npcDatabase.get(name).getNpcRoute();
	}

}
