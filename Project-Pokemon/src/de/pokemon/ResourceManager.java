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
	
		npcDatabase.put("mom", new NpcStruct("mom", "res/npc1.png", new int[][]{{5,4},{0,1000},{4,0},{0,500},{7,4},{0,1000},{4,0},{0,500}}));
		npcDatabase.put("supervisor", new NpcStruct("supervisor", "res/supervisor.png", new int[][]{{3,0},{0,3000},{2,0},{0,3000}}));
		npcDatabase.put("biker", new NpcStruct("biker", "res/biker.png", new int[][]{{6,3},{5,4},{8,3},{7,4}}));
		npcDatabase.put("captain", new NpcStruct("captain", "res/captain.png", new int[][]{{3,0},{0,2147483647}}));
		npcDatabase.put("deubler1", new NpcStruct("deubler1", "res/deubler.png", new int[][]{{1,0},{0,1000},{4,0},{0,1000},{3,0},{0,1000},{4,0},{0,1000}}));
		npcDatabase.put("holzerarmee", new NpcStruct("holzerarmee", "res/holzerarmee.png", new int[][]{{0,2147483647}}));
		
	}
	
	public static String getNpcImagePath(String name){
		return npcDatabase.get(name).getImagePath();
	}
	
	public static int[][] getNpcRoute(String name){
		return npcDatabase.get(name).getNpcRoute();
	}

}
