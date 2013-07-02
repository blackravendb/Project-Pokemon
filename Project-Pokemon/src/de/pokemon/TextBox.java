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

	/** the used font in the game */
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

		this.colorBackground = colorBackground;
		this.colorFont = colorFont;

		update = true;

		rectWidth = gc.getWidth()/2;
		rectHeight = gc.getHeight()/6; 
		rectX = (gc.getWidth() - rectWidth)/2; 
		rectY = (gc.getHeight() - rectHeight) - 50;

		background = new Rectangle(rectX, rectY, rectWidth, rectHeight);
		length = (gc.getDefaultFont().getWidth(text)); 
		maxLength = rectWidth - 15;

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
		textArray2 = new String[30]; 
		//counts how often the program loops through the array
		int counterArray = 0;

		StringBuffer buffer;
		StringTokenizer tokenizer = new StringTokenizer(text);
		//saves the next word to add the last word to the new line if "maxLength" has reached
		String remWord = "";
		
		//loops through the stringarray
		for(int i = 0; i < textArray2.length; i++){ 
			//counts the lines which are really needed
			counterArray = counterArray + 1;
			buffer = new StringBuffer();
			//saves the actual line 
			String remLine = "";
			
			//first word of the next line 
			if(remWord != ""){
				buffer.append(remWord + " ");
			}
			
			//appends the next token to the buffer, remembers the string before and the last word
			while(tokenizer.hasMoreTokens() && font.getWidth(buffer.toString()) < maxLength){
				remLine = buffer.toString();
				remWord = tokenizer.nextToken(); 
				buffer.append(remWord + " ");
			}
			
			//if the length from the last line is ok 
			if(tokenizer.hasMoreTokens() == false && font.getWidth(buffer.toString()) < maxLength){
				remLine = buffer.toString();
			}
			 // saves the final line in the stringarray
			textArray2[i] = remLine;
			//if the last word is reached 
			if(tokenizer.hasMoreTokens() == false){
				//1. if the last word doesn't fit in the current line a new line is created
				if(font.getWidth(buffer.toString()) >= maxLength){
					textArray2[i+1] = remWord;
					counterArray += 1; 
				}
				break;
			}
		}

		//the textArray is created 
		if(counterArray < 3){ 
			textArray = new String[counterArray];
		}else{
			textArray = new String[counterArray + 1];
		}
		//the textArray is filled
		for(int i = 0; i < counterArray; i++){ 
			textArray[i] = textArray2[i];
		}
	}

	/**changes the text which is shown in the TextBox
	 *
	 * @param p string which should be shown in the TextBox
	 * @param gc the container holding the game
	 * 
	 */
	public void setText (String p, GameContainer gc){ 

		update = true;

		length = (gc.getDefaultFont().getWidth(p));                                                

		changeStrings = 0;

		textArray = new String[(int) (Math.ceil(length/maxLength))];

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
			//changes TextBox if the length of the stringarray == 1
			if(textArray.length == 1 && changeStrings == 1 && textBoxInc == true){
				textBox += 1;
				end = true;
				textBoxInc = false;
			}
			//changes TextBox if the length of the stringarray == 2
			else if(textArray.length == 2 && changeStrings == 1){
				if(textBoxInc == true){
					textBox += 1;
					end = true;
					textBoxInc = false;
				}
			}
			//changes TextBox if the length of the stringarray >= 3
			for(int k = 0; k <= 2; k++){
				if(textArray.length >= 3 && changeStrings > textArray.length - 4 && textBoxInc == true){

					textBox += 1;
					end = true;
					textBoxInc = false;
				}
			}

			// counter rules that "next" is shown every second
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

			//sets the location of the triangle
			next.setLocation(rectX + rectWidth - 10, stringY[2] + 5); 

			//if you press Enter the strings are scrolling up
			if(input.isKeyPressed(Input.KEY_ENTER)){ 
				Sound.audioTextBox.playAsSoundEffect(1.0f, 3.0f, false);
				changeStrings++;
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

		//renders the TextBox if the TextBox has just one line 
		if(textArray.length == 1){
			g.drawString(textArray[0], stringx, stringY[0]);
		}

		//renders the TextBox if the TextBox has just two lines
		else if(textArray.length == 2){
			for(int q = 0; q <= 1; q++){
				g.drawString(textArray[q], stringx, stringY[q]);
			}
		}

		//renders the TextBox if the TextBox has more than two lines 
		else if(textArray.length > 2){

			//changes the position of the strings
			for(int k = 0; k <= 2; k++){ 
				if(textArray.length == 3){
					g.drawString(textArray[k], stringx, stringY[k]);
				}else if(changeStrings <= textArray.length - 4){ 
					g.drawString(textArray[k + changeStrings], stringx, stringY[k]); 
				}else{
					mem = changeStrings - 1;
				}
				if(changeStrings > textArray.length - 4){
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


