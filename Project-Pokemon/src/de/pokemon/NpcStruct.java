package de.pokemon;

public class NpcStruct {
	private String name;
	private String imagePath;
	private int[][] route;
	/**
	 * @param route
	 *            (int[][]) Laufroute des Npc's: erstes Array Art der Bewegung
	 *            (0= Warten; 1= Links drehen; 2= Hoch drehen; 3= Rechts drehen;
	 *            4= Runter drehen; 5= Links laufen; 6= Hoch laufen; 7= Rechts
	 *            Laufen; 8= Runter Laufen; zweite Array Wartezeit in ms bei 0
	 *            bzw. Schrittweite (z.B Array 2:0 bedeutung Hoch drehen; Array
	 *            6:3 bedeutung 3 Schritte nach oben); NULL beudeutet Npc bleibt
	 *            auf der Stelle stehen
	 * @param imagePath
	 *            (String) Pfad zum Animationsbild
	 */
	NpcStruct(String name, String imagePath, int[][] route) {
		this.name = name;
		this.imagePath = imagePath;
		this.route = route;
	}

	public String getName() {
		return name;
	}

	public String getImagePath() {
		return imagePath;
	}

	public int[][] getNpcRoute() {
		return route;
	}
}
