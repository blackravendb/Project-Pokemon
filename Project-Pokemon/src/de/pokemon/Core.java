package de.pokemon;

import java.applet.Applet;
import java.awt.Dimension;
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
	
	public static Dimension screenSize = new Dimension (600, 500); //Auflösung des Hauptfensters (muss noch besprochen werden)
	public static Dimension pixel = new Dimension (screenSize.width, screenSize.height);
	public static Dimension size;
	
	public static String name = "Project Pokemon"; //Name des Hauptfensters
	
	public Core () {
		setPreferredSize(screenSize);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Core core = new Core();
		
		frame = new JFrame();
		frame.add(core);
		frame.pack();
		
		size = new Dimension(frame.getWidth(), frame.getHeight());
		
		frame.setTitle(name);
		frame.setResizable(false); //Verhindern, dass der Benutzer die Größe des Bildschirms ändern kann
		frame.setLocationRelativeTo(null); //Zentrieren des Fensters in der Mitte des Bildschirms
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Wenn Fenster geschlossen wird, wird dadurch auch das Programm geschlossen
		frame.setVisible(true);
		core.start();
	}
	
	public void start() {
		requestFocus(); //Hauptfenster wird beim starten fokusiert
		
		
		
		run = true;
	
		new Thread(this).start();
	}
	
	public void stop() {
		run = false;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(5);
		} catch (Exception e){
			System.err.println("Error while trying to sleep Thread");
		}
		
	}

}
