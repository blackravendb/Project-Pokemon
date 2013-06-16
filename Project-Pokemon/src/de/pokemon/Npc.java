package de.pokemon;

public class Npc extends Entity {
	private int[][] route;
	private String name;
//	private boolean tick = true;

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
		
	}
	
	public void renderNpcHead() {
		super.renderEntityHead();
	}
	
	public void renderNpcBody() {
		super.renderEntityBody();
	}

}
