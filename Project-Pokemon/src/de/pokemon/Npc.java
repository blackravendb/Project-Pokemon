package de.pokemon;

import org.newdawn.slick.SlickException;

public class Npc extends Entity {
	private int[][] route;
	private boolean tick = true;

	/**
	 * Konstruktor Npc
	 * 
	 * @param posX
	 *            (int) Start X Position des Npc's gemessen Kopf oben links
	 * @param posY
	 *            (int) Start Y Position des Npc's gemessen Kopf oben links
	 * @param imagePath
	 *            (String) Pfad zum Animationsbild
	 * @param route
	 *            (int[][]) Laufroute des Npc's: erstes Array Art der Bewegung
	 *            (0= Warten; 1= Links drehen; 2= Hoch drehen; 3= Rechts drehen;
	 *            4= Runter drehen; 5= Links laufen; 6= Hoch laufen; 7= Rechts
	 *            Laufen; 8= Runter Laufen; zweite Array Wartezeit in ms bei 0
	 *            bzw. Schrittweite (z.B Array 2:0 bedeutung Hoch drehen; Array
	 *            6:3 bedeutung 3 Schritte nach oben); NULL beudeutet Npc bleibt
	 *            auf der Stelle stehen
	 * @param name (String) Name des Npc's und Referenz für übrige Werte
	 * @return void
	 */
	Npc(int posX, int posY, String name){
		super(posX, posY, Core.tileSize, Core.tileSize*2, "res/npc1.png");
	}

	/**
	 * Konstruktor Npc
	 * 
	 * @param posX
	 *            (int) Start X Position des Npc's gemessen Kopf oben links
	 * @param posY
	 *            (int) Start Y Position des Npc's gemessen Kopf oben links
	 * @param imagePath
	 *            (String) Pfad zum Animationsbild
	 * @param random
	 *            (boolean) Wert ob Bewegungsmuster zufällig (true)
	 * @return void
	 */
	Npc(int posX, int posY, String imagePath, boolean random){
		super(posX, posY, Core.tileSize, Core.tileSize*2, imagePath);

	}

	public void updateNpc() {
	/*	if (tick) {
			tick = false;
			super.moveX(1);
		} else {
			tick = true;
			super.moveX(-1);
		}*/
	}
	
	public void renderNpcHead() {
		super.renderEntityHead();
	}
	
	public void renderNpcBody() {
		super.renderEntityBody();
	}

}
