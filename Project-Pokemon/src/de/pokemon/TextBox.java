package de.pokemon;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import java.util.StringTokenizer;

public class TextBox {
	/** set to true if TextBox should update*/
	boolean update;
	
	/** x-coordinate of the frame*/
	private int rectX;
	/** y-coordinate of the frame */
	private int rectY;
	/** width of the frame*/
	private int rectWidth;
	/** height of the frame*/
	private int rectHeight;
	
	/** counter which ruels, the changes of the TextBox*/
	public int textBox;
	/** remembers the last value of changeStrings if changeStrings > string.length - 3*/
	private int mem;
	
	/** length of the string*/
	private int length;  // Länge des übergebenen Strings
	/** maximum length from one part of the string*/
	private float maxLength; // maximallänge eines Teilstrings
	
	/** counter which counts the changes of the divided string*/
	private int changeStrings;
	/** true if the value of textBox should increase around one*/
	private boolean textBoxInc;
	
	/** divided strings */
	private String[] string; // geteilte Strings
	/** committed string */
	private String text; //übergebener String
	
	/** x-coordinate of the string in the TextBox */
	private int stringx;
	/** different y-coordinates of the divided string in the TextBox */
	private int[] stringY; //y-Koordinaten der Strings
	
	/** background color of the textBox */
	private Color colorBackground;
	/** color of the Font*/
	private Color colorFont;
	
	/** frame of the TextBox*/
	private Rectangle background;
	/** triangle in the TextBox*/
	private Polygon next;
	/** the points for the triangle in x,y order */
	private final float[] cursorPoints = new float[]{6,0,0,6,6,12};
	
	/** true if triangle should be shown*/
	public boolean showTriangle;
	/** rules if the triangle is shown or not */
	private long counter;
	
	public TextBox(String text, Color colorBackground, Color colorFont, GameContainer gc){
		
		this.text = text; // übergebener Text
		
		this.colorBackground = colorBackground;
		this.colorFont = colorFont;
		
		update = true;
		
		rectWidth = gc.getWidth()/2;
		rectHeight = gc.getHeight()/6; // noch auf 3 Zeilen anpassen!
		rectX = (gc.getWidth() - rectWidth)/2; 
		rectY = (gc.getHeight() - rectHeight) - 50;
		
		background = new Rectangle(rectX, rectY, rectWidth, rectHeight);
		length = text.length() * 8; // *6 && ändern, wenn Font eingeblendet wird!
		maxLength = rectWidth - 80;
	
		changeStrings = 0;
		textBoxInc = true;
		
		stringx =  rectX + 10; // x bleibt immer gleich
		stringY = new int[3];
		stringY[0] = rectY + 10;
		for(int i=1; i <= 2; i++){
			stringY[i] = (stringY[i-1] + 20);
		}
		
		next = new Polygon(cursorPoints);
		showTriangle = true;
		counter = 0;
		
		textBox = 0;
		
		splitText(text);
		
	} // wird erzeugt!
	
	private void splitText (String text){ // Teilt den String in die verschiedenen Teile auf
		string = new String[(int) (Math.ceil(length/maxLength))]; // rundet automatisch immer auf
		System.out.println("länge string: " + length);
		System.out.println("maximale Länge: " + maxLength);
		System.out.println("string array länge: " + string.length);
		
		StringBuffer buffer;
		StringTokenizer tokenizer = new StringTokenizer(text);
		
			for(int i = 0; i < string.length; i++){ 
				buffer = new StringBuffer();
				while(((buffer.toString().length()) * 9)  < maxLength && tokenizer.hasMoreTokens()){
					buffer.append((tokenizer.nextToken() + " ")); //speichert jeden Token in den buffer
					}
				string[i] = buffer.toString();
				System.out.println("string[i]: " + string[i]);
				System.out.println("maxLength: " + maxLength);
				System.out.println("buffer länge: " + buffer.toString().length()); //buffer Länge stimmt nicht; jetzt *9
				System.out.println("string.length: " + string.length);
			}
	}
	
	public void setText (String p){
		
		
		text = p;
		update = true;
		
		length = p.length() * 8; // *6 && ändern, wenn Font eingeblendet wird!
		
		changeStrings = 0;
		
		string = new String[(int) (Math.ceil(length/maxLength))];
		
		showTriangle = true;
		counter = 0;
		
		textBoxInc = true;
		
		splitText(p);
		
	}
	
	public void update(Input input, int delta){

		if(update == true){
			
		if(string.length == 1 && changeStrings == 1 && textBoxInc == true){ //ändert die TextBox bei Länge 1
			textBox += 1;
			textBoxInc = false;
			}
		
		else if(string.length == 2 && changeStrings == 1){ //ändert die TextBox bei Länge 2
			if(textBoxInc == true){
				textBox += 1;
				textBoxInc = false;
			}
		}
		
		for(int k = 0; k <= 2; k++){ // ändert die TextBox bei Länge > 2
			if(string.length >= 3 && changeStrings > string.length - 3 && textBoxInc == true){
				textBox += 1;
				System.out.println("textBox: " + textBox);
				textBoxInc = false;
				}
			}
		
		 // counter regelt, dass "weiter" nur jede Sekunde angezeigt wird
			if (counter <= 1000){
			counter += delta;
			}else{
				counter = 0;
			}
			if(counter < 500){
				showTriangle = true;
			}else{
				showTriangle = false;
			}
		
		next.setLocation(rectX + rectWidth - 10, stringY[2] + 5); //setzt das Dreieck nach rechts unten
		
		if(input.isKeyPressed(Input.KEY_ENTER)){ //beim drücken von Enter werden die Strings nach oben verschoben
				Sound.audioTextBox.playAsSoundEffect(1.0f, 3.0f, false);
				changeStrings++;
				System.out.println("changeStrings: " + changeStrings);
			}
			input.clearKeyPressedRecord();
		}
	}
	
	public void render(Graphics g){
		g.setColor(colorBackground);
		g.fill(background);
		g.setColor(colorFont);
		g.drawRect(rectX, rectY, rectWidth, rectHeight);
		
		if(string.length == 1){
			g.drawString(string[0], stringx, stringY[0]);
		}
		
		else if(string.length == 2){
			for(int q = 0; q <= 1; q++){
			g.drawString(string[q], stringx, stringY[q]);
			}
		}
		
		else if(string.length > 2){
			
		for(int k = 0; k <= 2; k++){ // ändert die Position der Strings
			if(string.length == 3){
				g.drawString(string[k], stringx, stringY[k]);
			}else if(changeStrings <= string.length - 3){
				g.drawString(string[k + changeStrings], stringx, stringY[k]); 
			}else{
				mem = changeStrings - 1;
			}
			if(changeStrings > string.length - 3){
				
				g.drawString(string[k + mem], stringx, stringY[k]);
				}
				}
			}
		if(showTriangle == true){
			g.fill(next);
			}
		}
	}



