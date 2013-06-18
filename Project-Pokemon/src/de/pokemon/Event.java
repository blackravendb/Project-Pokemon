package de.pokemon;

import java.util.ArrayList;

import org.newdawn.slick.Input;

public class Event {
	
	PlayState playState;
	Player player;
	
	ArrayList<EventStruct> events;
	
	
	
	
	Event(PlayState playState){
		this.playState = playState;
		events = new ArrayList<EventStruct>();
	}
	
	public void setPlayerReference(Player player){
		this.player = player;
	}
	
	public void setPlayerPosition(int tileX, int tileY){
		
	}
	
	public void sendActionQuestion(int tileX, int tileY, int currentView){
		for(EventStruct entries : events){
			if(currentView == Input.KEY_A)
				tileX--;
			else if(currentView == Input.KEY_W)
				tileY--;
			else if(currentView == Input.KEY_D)
				tileX++;
			else if(currentView == Input.KEY_S)
					tileY++;
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
	
	
	//wird ben�tigt?
	public void initPlayState(){
		
	}

}
