package de.pokemon;

import java.util.ArrayList;

import org.newdawn.slick.Input;
import org.newdawn.slick.tiled.GroupObject;

public class EventManager {
	
	PlayState playState;
	Player player;
	
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
				//Starte Event
				System.out. println("blub");
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
	
	
	//wird benötigt?
	public void initPlayState(){
		
	}

}
