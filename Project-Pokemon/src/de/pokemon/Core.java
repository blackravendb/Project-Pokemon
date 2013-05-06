package de.pokemon;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

public class Core extends Applet implements Runnable {

	private static final long serialVersionUID = 1L;

	private static JFrame frame; //Hauptfenster des Spiels
	
	public static final int res = 1; //resolution: multiplikator für Bildpunkt * res = Pixelgröße auf Bildschirm
	public static double oY = 0, oX = 0; //offset y und offset X
	public static int dir = 0; //Richtungsvariable (0=hoch, 1=rechts, 2=runter, 3=links)
	
	public static boolean moving = false; //Variabel ob Bewegung möglich ist (z.B. wärend Dialogen)
	public static boolean run = false; //Ob der Charakter im moment Rennt (fraglich ob diese funktion überhaupt implementiert wird xD)
	
	private Image screen;
	public static Player player;
	
	public Level level;
	
	public static Dimension screenSize = new Dimension (800, 600); //Auflösung des Hauptfensters (muss noch besprochen werden)
	public static Dimension pixel = new Dimension (screenSize.width, screenSize.height);
	public static Dimension size;
	
	public static String name = "Project Pokemon"; //Name des Hauptfensters
	
	public Core () {
		setPreferredSize(screenSize);
		addKeyListener(new InputManager());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Core core = new Core();
		
		frame = new JFrame();
		frame.add(core);
		frame.pack();
		
		size = new Dimension(frame.getWidth(), frame.getHeight());
		
		frame.setTitle(name);
		frame.setResizable(true); //Verhindern, dass der Benutzer die Größe des Bildschirms ändern kann
		frame.setLocationRelativeTo(null); //Zentrieren des Fensters in der Mitte des Bildschirms
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Wenn Fenster geschlossen wird, wird dadurch auch das Programm geschlossen
		frame.setVisible(true);
		core.start();
	}
	
	public void start() {
		requestFocus(); //Hauptfenster wird beim starten fokusiert
		
		//define classes
		level = new Level(1);
		player = new Player("Player");
		new Tile();
		
		run = true;
	
		new Thread(this).start();
	}
	
	public void stop() {
		run = false;
	}
	
	public void tick(){
		frame.pack();
		player.tick();
		level.tick();
	}
	
	public void render() {
		Graphics g = screen.getGraphics();
		g.setColor(Color.black);
		g.drawRect(0, 0, screenSize.width, screenSize.height);
		
		//absolute Renderreihenfolge im Spielfenster
		level.render(g, (int) oX, (int)oY, (pixel.width / Tile.size) + 2, (pixel.height / Tile.size) + 2);
		player.render(g);
		
		g = this.getGraphics();
		g.drawImage(screen, 0, 0, screenSize.width, screenSize.height, 0 , 0, pixel.width, pixel.height, null);
		g.dispose();
		
	}

	@Override
	public void run() {
		screen = createVolatileImage(pixel.width, pixel.height);
		
		while(run) {
			tick();
			render();
			
			try {
				Thread.sleep(5);
			} catch (Exception e){
				System.err.println("Error while trying to sleep Thread");
			}
		}
	}

}
