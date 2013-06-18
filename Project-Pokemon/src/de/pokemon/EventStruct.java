package de.pokemon;

public class EventStruct {
	private int tileX;
	private int tileY;
	private String name;
	private Entity reference;
	
	//Eventlevel
	private int stage = 0;
	
	//Verschiedene Sprachsätze
	private String[] text;
	
	
	EventStruct(Entity reference, String name, int tileX, int tileY){
		this.reference = reference;
		this.name = name;
		this.tileX = tileX;
		this.tileY = tileY;
		
	}
	
	public int getTileX(){
		return tileX;
	}
	
	public int getTileY(){
		return tileY;
	}
	
	public Entity getReference(){
		return reference;
	}
	
	public String getText(int stage){
		return text[stage];
	}
	
	public int getStage(){
		return stage;
	}
	
	public String getName(){
		return name;
	}
	
	public void setTileX(int tileX){
		this.tileX = tileX;
	}
	
	public void setTileY(int tileY){
		this.tileY = tileY;
	}
	
	public void setStage(int stage){
		this.stage = stage;
	}
	
	public void setReference (Entity reference){
		this.reference = reference;
	}
	
	public boolean isOnPosition(int tileX, int tileY){
		if(this.tileX == tileX && this.tileY == tileY)
			return true;
		
		return false;
	}

}