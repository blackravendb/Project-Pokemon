package de.pokemon;

public class EventStruct {
	private int tileX;
	private int tileY;
	private String name;
	private Entity entityReference;
	
	//Dialog Level
	private int stage = 0;
	
	//Variable zum laden der Events
	private String[] dialog;
	
	private String[] modifyStage;
	
	
	EventStruct(Entity entityReference, String name, int tileX, int tileY){
		this.entityReference = entityReference;
		this.name = name;
		this.tileX = tileX;
		this.tileY = tileY;
		dialog = ResourceManager.getDialog(name);
		modifyStage = ResourceManager.getModifyStage(name);
	}
	
	public int getTileX(){
		return tileX;
	}
	
	public int getTileY(){
		return tileY;
	}
	
	public Entity getReference(){
		return entityReference;
	}
	
	public int getStage(){
		return stage;
	}
	
	public String getName(){
		return name;
	}
	
	public String getModifyStage(){
		if(modifyStage[stage] != null){
			String temp = modifyStage[stage];
			modifyStage[stage] = null;
			return temp;
		}
		return null;	
	}
	
	public void setTileX(int tileX){
		this.tileX = tileX;
	}
	
	public void setTileY(int tileY){
		this.tileY = tileY;
	}
	
	public void increaseStage(){
		stage++;
	}
	
	
	public String getCurrentDialog(){
		return dialog[stage];
	}
	
	public void setReference (Entity reference){
		this.entityReference = reference;
	}
	
	public boolean isOnPosition(int tileX, int tileY){
		if(this.tileX == tileX && this.tileY == tileY)
			return true;
		
		return false;
	}

}