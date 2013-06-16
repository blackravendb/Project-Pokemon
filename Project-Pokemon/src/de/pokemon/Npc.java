package de.pokemon;

import org.newdawn.slick.Input;

public class Npc extends Entity {
	private int[][] route;
	private String name;
	
	private int counterDelta;
	
	private int step = 1;

	/**
	 * Konstruktor Npc
	 * 
	 * @param posX
	 *            (int) Start X Position des Npc's gemessen Kopf oben links
	 * @param posY
	 *            (int) Start Y Position des Npc's gemessen Kopf oben links
	 * @param name (String) Name des Npc's und Referenz für übrige Werte
	 * @return void
	 */
	Npc(int posX, int posY, String name){
		super(posX, posY, Core.tileSize, Core.tileSize*2, ResourceManager.getNpcImagePath(name));
		route = ResourceManager.getNpcRoute(name);
		this.name = name;
	}
	
	public void updateNpc(int delta){
		
		if(step <= route.length){
			//Warten
			switch(route[step][0]){
			case 0:
				if(counterDelta <= route[step][0])
					step++;
				else
					counterDelta+= delta;
				break;
				//Runter drehen
			case 4:
				super.renderTurnAnimation(Input.KEY_S);
			}
			
		}
		else
			step = 1;
	}
	
	public void renderNpcHead() {
		super.renderEntityHead();
	}
	
	public void renderNpcBody() {
		super.renderEntityBody();
	}

}
