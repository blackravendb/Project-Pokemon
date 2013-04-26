package de.pokemon;

import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JFrame;

public class Core extends Applet implements Runnable {

	private static final long serialVersionUID = 1L;

	private static JFrame frame;
	
	public static final int res = 1;
	public static double oY = 0, oX = 0;
	public static int dir = 0;
	
	public static boolean moving = false;
	public static boolean run = false;
	
	private Image screen;
	
	public static Dimension screenSize = new Dimension (600, 500);
	public static Dimension pixel = new Dimension (screenSize.width, screenSize.height);
	public static Dimension size;
	
	public static String name = "Project Pokemon";
	
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
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		core.start();

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
