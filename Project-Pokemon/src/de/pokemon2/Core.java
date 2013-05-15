package de.pokemon2;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Core extends Applet implements Runnable {

	private static final long serialVersionUID = 1L;

	public final int TARGET_FPS = 60;
	public final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	public static long lastFpsTime = 0;
	public static int fps = 0;
	public static int renderFps = 0;
	
	private static JFrame frame; //Hauptfenster des Spiels
	
	public static final int res = 1; //resolution: multiplikator für Bildpunkt * res = Pixelgröße auf Bildschirm
	public static double oY = 0, oX = 0; //offset y und offset X
	public static int dir = 0; //Richtungsvariable (0=hoch, 1=rechts, 2=runter, 3=links)
	
	public static boolean moving = false; //Variabel ob Bewegung möglich ist (z.B. wärend Dialogen)
	public static boolean run = false; //Ob der Charakter im moment Rennt (fraglich ob diese funktion überhaupt implementiert wird xD)
	
	private Image screen;
	
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Entity> removeList = new ArrayList<Entity>();
	
	public static EntityPlayer player;
	
	public static boolean bW, bS, bA, bD; //Definierte Tasten (ButtonW,...)
	public static boolean inGame = true; // Game state variable (ingame)
	
	public Level level;
	
	public static Core core;
	
	public static Dimension screenSize = new Dimension (400, 400); //Auflösung des Hauptfensters (muss noch besprochen werden)
	public static Dimension pixel = new Dimension (screenSize.width, screenSize.height);
	public static Dimension size;
	
	public static String name = "Project Pokemon"; //Name des Hauptfensters
	
	public Core () {
		setPreferredSize(screenSize);
		addKeyListener(new InputManager());
		
		initEntities();
	}
	
	public void start() {
		requestFocus(); //Hauptfenster wird beim starten fokusiert
		
		//define classes
		level = new Level(1);
		new Tile();
		
		run = true;
	
		new Thread(this).start();
	}
	
	public void stop() {
		run = false;
	}
	
	public void tick(double delta){
		
			frame.pack();
			
			if(inGame) {
				
				for(int i = 0; i < entities.size(); i++) {
					Entity entity = (Entity) entities.get(i);
					entity.move(delta);
				}
				
				for(int i = 0; i < entities.size(); i++) {
					for(int j = i + 1; j < entities.size(); j++){
						Entity me = (Entity)entities.get(i);
						Entity him = (Entity)entities.get(j);
						if(me.collidesWith(him)){
							me.collidedWith(him);
							him.collidedWith(me);
						}
					}
				}
				
				entities.remove(removeList);
				removeList.clear();
				
			}
	}
	
	public void render() {
		Graphics g = screen.getGraphics();
		g.setColor(Color.black);
		g.drawRect(0, 0, screenSize.width, screenSize.height);
		
		//absolute Renderreihenfolge im Spielfenster
		level.render(g, (int) oX, (int)oY, (pixel.width / Tile.size) + 2, (pixel.height / Tile.size) + 2);
		
		for(int i = 0; i < entities.size(); i++) {
			Entity entity = (Entity) entities.get(i);
			entity.render(g);
		}
		
		g.setColor(Color.red);
		g.drawString("oX:" + (int)oX + " oY:" + (int)oY, 405, 20);
		g.drawString("FPS: " + renderFps, 405, 35);
		
		g = this.getGraphics();
		g.drawImage(screen, 0, 0, screenSize.width, screenSize.height, 0 , 0, pixel.width, pixel.height, null);
		g.dispose();
		
	}
	
	public void removeEntity(Entity entity){
		removeList.add(entity);
	}
	
	public void initEntities(){
		player = new EntityPlayer(core, screenSize.width / 2 - Tile.size / 2 + oX, screenSize.height / 2 - Tile.size / 2 + oY, Tile.size, Tile.size*2);
		entities.add(player);
	}

	public void run() {
		screen = createVolatileImage(pixel.width, pixel.height);
		
		long lastLoopTime = System.nanoTime();
		
		while(run) {
			
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			
			double delta = updateLength / (double)OPTIMAL_TIME;
			lastFpsTime += updateLength;
			fps++;
			
			long sleepTime = 0;
			
			if(lastFpsTime >= 1000000000) { // 1.000.000.000 ns = 1 Sekunde
				renderFps = fps;
				fps = 0;
				lastFpsTime = 0;
			}
			
			tick(delta);
			
			render();
			
			try {
				sleepTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
				Thread.sleep(sleepTime);
			} catch (Exception e){
				if(sleepTime >= 0)
					System.err.println("Error while trying to sleep Thread");
			}
		}
	}

public static void main(String[] args) {
		
		core = new Core();
		
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
	
}