package de.pokemon;

public class Event {
	/**Gesprächsarray. Dialoge der Npc's werden in dieser Gespeichert*/
	private String[] text;
	
	private String[] modifyStage;
	
	Event(String[] text, String[] modifyStage){
		this.text = text;
		this.modifyStage = modifyStage;
	}
	
	Event(String[] text){ 
		this.text = text;
		modifyStage = new String[text.length];
	}
	
	public String[] getText(){
		return text;
	}
	
	public void modStage(String name, int stage){
		if(stage <= text.length){
			
		}
	}
	
	public String[] getModifyStage(){
		return modifyStage;
	}
}