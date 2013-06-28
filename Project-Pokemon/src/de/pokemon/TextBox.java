package de.pokemon;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import java.util.StringTokenizer;

public class TextBox {
	/** set to true if TextBox should update*/
	boolean update;

	boolean lastWord;
	
	Font font;

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
	/** true => End of the Textbox */
	boolean end;
	/** remembers the last value of changeStrings if changeStrings > string.length - 3*/
	private int mem;

	/** length of the string*/
	private int length;  
	/** maximum length from one part of the string*/
	private float maxLength;

	/** counter which counts the changes of the divided string*/
	private int changeStrings;
	/** true if the value of textBox should increase around one*/
	private boolean textBoxInc;

	/** divided strings */
	private String[] textArray; 
	/** committed string */
	private String[] textArray2;
	private String text; 

	/** x-coordinate of the string in the TextBox */
	private int stringx;
	/** different y-coordinates of the divided string in the TextBox */
	private int[] stringY; 

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

	/**Initialises the TextBox
	 * 
	 * @param text committed String
	 * @param colorBackground the color of the background
	 * @param colorFont the color of the font
	 * @param gc the container holding the game
	 */
	public TextBox(String text, Color colorBackground, Color colorFont, GameContainer gc){

		this.text = text; 

		this.colorBackground = colorBackground;
		this.colorFont = colorFont;

		update = true;
		
		rectWidth = gc.getWidth()/2;
		rectHeight = gc.getHeight()/6; 
		rectX = (gc.getWidth() - rectWidth)/2; 
		rectY = (gc.getHeight() - rectHeight) - 50;

		background = new Rectangle(rectX, rectY, rectWidth, rectHeight);
		length = (gc.getDefaultFont().getWidth(text)); 
		System.out.println("length: " + length);
		maxLength = rectWidth - 15;
		System.out.println("maxLength: " + maxLength);
		
		font = gc.getDefaultFont();

		changeStrings = 0;
		textBoxInc = true;

		stringx =  rectX + 10; 
		stringY = new int[3];
		stringY[0] = rectY + 10;
		for(int i=1; i <= 2; i++){
			stringY[i] = (stringY[i-1] + 20);
		}

		next = new Polygon(cursorPoints);
		showTriangle = true;
		counter = 0;

		textBox = 0;
		end = false;

		splitText(text);

	} 

	/**splits the committed string and buffers it in an array of strings
	 *
	 * @param text committed string
	 * 
	 */
	private void splitText (String text){
		textArray2 = new String[30]; //the length of the array depends on the length of the commited string
		System.out.println("LängetextArray: "+ textArray2.length);
		int counterArray = 0;
		
		//System.out.println("Feldanzahl: " + ((int) (Math.ceil(length/maxLength))));
		StringBuffer buffer;
		StringBuffer buffer2;
		StringTokenizer tokenizer_1 = new StringTokenizer(text);
		StringTokenizer tokenizer_2 = new StringTokenizer(text);
		String rem2 = "";
		//System.out.println(font.getWidth(buffer.append(tokenizer_1.nextToken() + " ")));
		
		
		for(int i = 0; i < textArray2.length; i++){ //loops through the stringarray
			counterArray = counterArray + 1;
			buffer = new StringBuffer();
			//buffer2 = new StringBuffer();
			String rem = "";
			
			if(rem2 != ""){
			buffer.append(rem2 + " ");
			}
			
			/*
			 * De Wäid werd vo komische Wesn bewohnt, zu dene ma Pokemon sogt! Fia manche Leid
			 */
			
			while(tokenizer_1.hasMoreTokens() && font.getWidth(buffer.toString()) < maxLength){
			rem = buffer.toString();
			System.out.println("rem: " + rem);
			rem2 = tokenizer_1.nextToken();
			buffer.append(rem2 + " ");
			}
			
			//System.out.println("lastWord: " + lastWord);
			
			if(tokenizer_1.hasMoreTokens() == false && font.getWidth(buffer.toString()) < maxLength){
					rem = buffer.toString();
					}
			/*
			while(tokenizer_1.hasMoreTokens() && font.getWidth(buffer.append(tokenizer_1.nextToken() + " ").toString()) < maxLength){ 
				buffer2.append(tokenizer_2.nextToken() + " "); //saves every token in a buffer
			}*/
			textArray2[i] = rem; // saves the part of the string in a stringarray
			if(tokenizer_1.hasMoreTokens() == false){
				System.out.println("BREAK!");
				if(font.getWidth(buffer.toString()) >= maxLength){
					textArray2[i+1] = rem2;
					counterArray += 1; 
				}
				break;
			}
	}
		
		
		if(counterArray < 3){ //TODO
			textArray = new String[counterArray];
		}else{
		textArray = new String[counterArray + 1];
		}
		System.out.println("counter: " + counterArray);
		
		for(int i = 0; i < counterArray; i++){ 
			textArray[i] = textArray2[i];
			System.out.println("textArray: " + textArray[i]);
		}
		
		System.out.println("ENDE_SPLIT");
	}

	/**changes the text which is shown in the TextBox
	 *
	 * @param p string which should be shown in the TextBox
	 * 
	 */
	public void setText (String p, GameContainer gc){ //28.06 gc

		System.out.println("ANFANG");
		//text = p;
		update = true;

		length = (gc.getDefaultFont().getWidth(p));                                                 //p.length() * 8; 
		System.out.println("length2: " + length);
		
		changeStrings = 0;

		textArray = new String[(int) (Math.ceil(length/maxLength))];

		showTriangle = true;
		counter = 0;

		textBoxInc = true;

		splitText(p);

		end = false;
		System.out.println("ENDE");

	}

	/**Updates the TextBox, e.g. cursor position, processes Input
	 *
	 * @param input input of the IntroState
	 * @param delta delta of the IntroState
	 */

	public void update(Input input, int delta){
		
		System.out.println("UPDATE!");
		System.out.println("textArray.length: " + textArray.length);
		System.out.println("textBoxInc: " + textBoxInc);
		System.out.println("update: " + update);
		
		if(update == true){

			
			
			if(textArray.length == 1 && changeStrings == 1 && textBoxInc == true){ //changes TextBox if the length of the stringarray == 1
				textBox += 1;
				end = true;
				textBoxInc = false;
			}

			else if(textArray.length == 2 && changeStrings == 1){//changes TextBox if the length of the stringarray == 2
				if(textBoxInc == true){
					textBox += 1;
					end = true;
					textBoxInc = false;
				}
			}

			for(int k = 0; k <= 2; k++){ // //changes TextBox if the length of the stringarray > 2
				if(textArray.length >= 3 && changeStrings > textArray.length - 4 && textBoxInc == true){
					
					textBox += 1;
					end = true;
					System.out.println("textBox: " + textBox);
					textBoxInc = false;
				}
			}

			// counter rules that next is shown every second
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

			next.setLocation(rectX + rectWidth - 10, stringY[2] + 5); //sets the location of the triangle

			if(input.isKeyPressed(Input.KEY_ENTER)){ //if you press Enter the strings are scrolling up
				Sound.audioTextBox.playAsSoundEffect(1.0f, 3.0f, false);
				changeStrings++;
				System.out.println("changeStrings: " + changeStrings);
			}
			input.clearKeyPressedRecord();
		}
	}

	/** Renders the Textbox
	 * 
	 * @param g the current graphis context
	 */
	public void render(Graphics g){
		g.setColor(colorBackground);
		g.fill(background);
		g.setColor(colorFont);
		g.drawRect(rectX, rectY, rectWidth, rectHeight);

		if(textArray.length == 1){
			g.drawString(textArray[0], stringx, stringY[0]);
		}

		else if(textArray.length == 2){
			for(int q = 0; q <= 1; q++){
				
				System.out.println("textArray bei 2: " + textArray[q]);
				g.drawString(textArray[q], stringx, stringY[q]);
			}
		}

		else if(textArray.length > 2){

			for(int k = 0; k <= 2; k++){ //changes the position of the strings
				if(textArray.length == 3){
					g.drawString(textArray[k], stringx, stringY[k]);
				}else if(changeStrings <= textArray.length - 4){ // war - 3
					g.drawString(textArray[k + changeStrings], stringx, stringY[k]); 
				}else{
					mem = changeStrings - 1;
				}
				if(changeStrings > textArray.length - 4){// war - 3
					g.drawString(textArray[k + mem], stringx, stringY[k]);
				}
			}
		}
		if(showTriangle == true){
			g.fill(next);
		}
	}

	public boolean end(){
		if(end == true){
			return true;
		}else{
			return false;
		}
	}
}


