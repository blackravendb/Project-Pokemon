package de.pokemon;

public class Event {
	
	PlayState playState;
	Player player;
	
	
	
	Event(PlayState playState){
		this.playState = playState;
	}
	
	public void setPlayerReference(Player player){
		this.player = player;
	}
	
	public void setPlayerPosition(int tileX, int tileY){
		
	}
	
	public void setNpcPosition(int tileX, int tileY){
		
	}
	
	//wird benötigt?
	public void initPlayState(){
		
	}

}
