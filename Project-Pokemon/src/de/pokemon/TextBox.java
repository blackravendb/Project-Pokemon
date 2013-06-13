package de.pokemon;


import java.util.Arrays;
import java.util.List;


import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class TextBox {
	public int rectx;
	public int recty;
	public int rectwidth;
	public int rectheight;
	
	public int length;  // Länge des übergebenen Strings
	public float maxLength; // maximallänge eines Teilstrings
	public int currentLength; // aktuelle Länge des Arrays string
	
	public int changeStrings;
	
	GameContainer gc;
	
	StringTokenizer tokenizer;
	
	String[] string; // geteilte Strings
	String text; //übergebener String
	
	public int stringx;
	public int[] stringy; //y-Koordinaten der Strings
	
	Color colorBackground;
	Color colorFont;
	
	Font font;
	
	private Rectangle background;
	private Polygon weiter;
	private final float[] cursorPoints = new float[]{6,0,0,6,6,12};
	
	boolean showDreieck;
	public long counter;
	
	public TextBox(String text, Color colorBackground, Color colorFont, /*Font font,*/ GameContainer gc){
		this.gc = gc;
		
		this.text = text; // übergebener Text
		
		this.colorBackground = colorBackground;
		this.colorFont = colorFont;
		//this.font = font;
		
		rectwidth = gc.getWidth()/2;
		rectheight = gc.getHeight()/6; // noch auf 3 Zeilen anpassen!
		rectx = (gc.getWidth() - rectwidth)/2; 
		recty = (gc.getHeight() - rectheight) - 50;
		
		background = new Rectangle(rectx, recty, rectwidth, rectheight);
		//length = font.getWidth(text);
		length = text.length() * 8; // WARUM?!?!?!?!? *6 && ändern, wenn Font eingeblendet wird!
		maxLength = rectwidth - 80;
		currentLength = 3;
		
		changeStrings = 0;
		
		string = new String[(int) (Math.ceil(length/maxLength))]; // rundet automatisch immer auf
		System.out.println("länge string: " + length);
		System.out.println("maximale Länge: " + maxLength);
		System.out.println("string array länge: " + string.length);
		//Länge des Strings falsch!
		
		stringx =  rectx + 10; // x bleibt immer gleich
		stringy = new int[3];
		stringy[0] = recty + 10;
		for(int i=1; i <= 2; i++){
			stringy[i] = (stringy[i-1] + 20);
		}
		
		weiter = new Polygon(cursorPoints);
		showDreieck = true;
		counter = 0;
		
		//Code aus Update
		
		StringBuffer buffer;
		StringTokenizer tokenizer = new StringTokenizer(text);
		
			for(int i = 0; i < string.length; i++){ 
				buffer = new StringBuffer();
				while(((buffer.toString().length()) * 9)  < maxLength && tokenizer.hasMoreTokens()){
					buffer.append((tokenizer.nextToken() + " ")); //speichert jeden Token in den buffer
					}
				string[i] = buffer.toString();
				System.out.println("maxLength: " + maxLength);
				System.out.println("buffer länge: " + buffer.toString().length()); //buffer Länge stimmt nicht WARUM?!?! jetzt *9
				System.out.println("string.length: " + string.length);
			}
			
		//Code aus Update Ende
			
	} // wird erzeugt!
	
	public void update(Input input, int delta){
		
		
				/*
				if(buffer.toString().length() <= (maxLength + (maxLength*i)) && buffer.toString().length() >= (maxLength*i) && tokenizer.hasMoreTokens()){ 
					
					string[i] = buffer.toString();
					System.out.println("string[i]: " + string[i].length());
				}*/
				/*
				System.out.println("buffer.toString().length() " + buffer.toString().length());
				if(buffer.toString().length() <= (maxLength + (maxLength*i)) && buffer.toString().length() >= (maxLength*i)){ //fügt den buffer dem jeweiligen Stringarray hinzu, wenn die Länge stimmt
					string[i] = buffer.toString();
					System.out.println("string[i]: " + string[i].length());
				} 
		System.out.println("string.length: " + string.length);
		System.out.println("string[2] länge: " + string[2].length());
		System.out.println("stringx: " + stringx);
		System.out.println("stringy[2]: " + stringy[2]);*/
		
		 // counter regelt, dass "weiter" nur jede Sekunde angezeigt wird
			if (counter <= 1000){
			counter += delta;
			}else{
				counter = 0;
			}
			if(counter < 500){
				showDreieck = true;
			}else{
				showDreieck = false;
			}
		
		weiter.setLocation((stringx + (string[2].length()*9 - 3)), stringy[2] + 5); //setzt das Dreieck hinter den letzten String; String[2].length stimmt nicht, deswegen *9
		
		if(input.isKeyPressed(Input.KEY_ENTER)){ //beim drücken von Enter werden die Strings nach oben verschoben
				changeStrings = 1;
			}
			input.clearKeyPressedRecord();
		}

	
	
	
	public void render(Graphics g){
		g.setColor(colorBackground);
		g.fill(background);
		g.setColor(colorFont);
		g.drawRect(rectx, recty, rectwidth, rectheight);
		
		/*if(changeStrings == true){
		for(int i = x + 1; i <= x + 2; i++){ //lässt Strings im Array nach oben rutschen (koordinaten ändern)
					g.drawString(str[], x, y);
				}
		}*/
		
		if(string.length >= (changeStrings*3)){
		for(int x = 0; x <= 2 + changeStrings; x++){ //gibt die Strings aus
				g.drawString(string[x+changeStrings], stringx, stringy[x]); //out of BounceException?!?
				//System.out.println("string[x]: " + string[x]);
			}
		}
		
		/*
			else if((changeStrings*3) < string.length && changeStrings != 0){
				g.clear();
				for(int i = x + 1; i <= x + 2; i++){  //lässt Strings im Array nach oben rutschen (koordinaten ändern)
					g.drawString(string[x+1], stringx, stringy[x+1]);
				}
			}	*/
		
		if(showDreieck == true){
			g.fill(weiter);
		}
	}
}


