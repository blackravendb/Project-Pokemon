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
	private String[] string; 
	/** committed string */
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
		length = (gc.getDefaultFont().getWidth(text)); //text.length() * 8; 
		maxLength = rectWidth - 80;
	
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
		string = new String[(int) (Math.ceil(length/maxLength))]; //the length of the array depends on the length of the commited string

		StringBuffer buffer;
		StringTokenizer tokenizer = new StringTokenizer(text);

		for(int i = 0; i < string.length; i++){ //loops through the stringarray
			buffer = new StringBuffer();
			while((font.getWidth(buffer.toString())) < maxLength && tokenizer.hasMoreTokens()){ //*9 ändern!
				buffer.append((tokenizer.nextToken() + " ")); //saves every token in a buffer
			}
			string[i] = buffer.toString(); // saves the part of the string in a stringarray
		}
	}

	/**changes the text which is shown in the TextBox
	 *
	 * @param p string which should be shown in the TextBox
	 * 
	 */
	public void setText (String p){


		text = p;
		update = true;

		length = p.length() * 8; 

		changeStrings = 0;

		string = new String[(int) (Math.ceil(length/maxLength))];

		showTriangle = true;
		counter = 0;

		textBoxInc = true;

		splitText(p);
		
		end = false;

	}

	/**Updates the TextBox, e.g. cursor position, processes Input
	 *
	 * @param input input of the IntroState
	 * @param delta delta of the IntroState
	 */

	public void update(Input input, int delta){

		if(update == true){

			if(string.length == 1 && changeStrings == 1 && textBoxInc == true){ //changes TextBox if the length of the stringarray == 1
				textBox += 1;
				end = true;
				textBoxInc = false;
			}

			else if(string.length == 2 && changeStrings == 1){//changes TextBox if the length of the stringarray == 2
				if(textBoxInc == true){
					textBox += 1;
					end = true;
					textBoxInc = false;
				}
			}

			for(int k = 0; k <= 2; k++){ // //changes TextBox if the length of the stringarray > 2
				if(string.length >= 3 && changeStrings > string.length - 3 && textBoxInc == true){
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

		if(string.length == 1){
			g.drawString(string[0], stringx, stringY[0]);
		}

		else if(string.length == 2){
			for(int q = 0; q <= 1; q++){
				g.drawString(string[q], stringx, stringY[q]);
			}
		}

		else if(string.length > 2){

			for(int k = 0; k <= 2; k++){ //changes the position of the strings
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

	public boolean end(){
		if(end == true){
			return true;
		}else{
			return false;
		}
	}
}


