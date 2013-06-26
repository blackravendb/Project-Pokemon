package de.pokemon;

import java.util.ArrayList;

import org.newdawn.slick.Input;
import org.newdawn.slick.tiled.GroupObject;


public class EventManager {
	
	PlayState playState;
	Player player;
	String dialog;
	String npcName;
	
	ArrayList<EventStruct> events;

	EventManager(PlayState playState){
		this.playState = playState;
		events = new ArrayList<EventStruct>();
	}
	
	public void setPlayerReference(Player player){
		this.player = player;
	}
	
	public void setPlayerPosition(int tileX, int tileY){
		
	}
	
	public void sendActionRequest(int tileX, int tileY, int currentView){
		if(currentView == Input.KEY_A)
			tileX--;
		else if(currentView == Input.KEY_W)
			tileY--;
		else if(currentView == Input.KEY_D)
			tileX++;
		else if(currentView == Input.KEY_S)
				tileY++;
		
		for(EventStruct entries : events){
			if( entries.isOnPosition(tileX, tileY)){
				String modifyEntryName = entries.getModifyStage();
				dialog = entries.getCurrentDialog();
				
				if(modifyEntryName != null){
					
					for(EventStruct modifyEntry: events){
						if(modifyEntry.getName().equals(modifyEntryName)){
							modifyEntry.increaseStage();
							//Verlassen der for-Schleife (modify NPC gefunden)
							break;
						}
					}
				}
				if(currentView == Input.KEY_A){
					System.out.println("blub");
					entries.getReference().setLastDir(Input.KEY_D);
				}
				else if(currentView == Input.KEY_W){
					System.out.println("blub2");
					entries.getReference().setLastDir(Input.KEY_S);
				}
				else if(currentView == Input.KEY_D){
					System.out.println("blub3");
					entries.getReference().setLastDir(Input.KEY_A);
				}
				else if(currentView == Input.KEY_S){
					System.out.println("blub4");
					entries.getReference().setLastDir(Input.KEY_W);
				}
				playState.setDialogString(dialog);
//				if(dialog.endsWith("[setStage]")){
//				//	npcName = dialog.split("|")[0]
//					npcName= dialog.split(Pattern.quote("|"))[1];
//					dialog = dialog.split(Pattern.quote("|"))[0];
//					if(npcName != null)
//						System.out.println("NPC Name: " + npcName);
//				//	dialog=dialog.substring(npcName.length()+1, dialog.length()-npcName.length()-9);
//					for(EventStruct npc : events){
//						if(npc.getName().equals(npcName)){
//							npc.increaseStage();
//							break;
//						}						
//					}
//				}
			//	System.out.println(entries.getCurrentDialog());
				//Verlassen der for Schleife (NPC gefunden)
				break;
			}
		}
	}
	
	public void setNpcPosition(Npc npc, int tileX, int tileY){
		
	}
	
	public void setNpcPosX(Npc npc, int tileX){
		for(EventStruct npcObject : events){
			if(npc == npcObject.getReference()){
				npcObject.setTileX(tileX);
				break;
			}
		}
	}
	
	public void setNpcPosY (Npc npc, int tileY){
		for(EventStruct npcObject : events){
			if(npc == npcObject.getReference()){
				npcObject.setTileY(tileY);
				break;
			}
		}
	}
	
	public void initNpc (Npc npc, String name, int tileX, int tileY){
		 boolean isContained = false;
		for(EventStruct npcObject : events){
			if(npcObject.getName().equals(name)){
				npcObject.setReference(npc);
				npcObject.setTileX(tileX);
				npcObject.setTileY(tileY);
				isContained = true;
				break;
			}
		}
		if(!isContained)
			events.add(new EventStruct(npc, name, tileX, tileY));
	}
	
	public void initStaticEvents (ArrayList <GroupObject> staticEvents){
		if (!staticEvents.isEmpty()){
			//blub
		}
	}
}
