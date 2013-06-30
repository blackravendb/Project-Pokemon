package de.pokemon;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class CreditsState extends BasicGameState {

	/** ID of the state*/
	public static int ID; 

	/** counter which rules which area is shown */
	private int k;

	/** the image at the last part*/ 
	Image end;
	/** x-coordinate of the Image "end" */
	private int endX;
	/** y-coordinate of the Image "end" */
	private int endY;
	/** changes the visibility of the picture "end"*/
	private float deltaEndTransparency;
	/** true if "end" appears */
	private boolean endAppears;

	/** the image at the top of the Credits*/
	Image title;
	/** x-coordinate of the "title" */
	private int titleX;
	/** y-coordinate of the "title" */
	private int titleY;
	/** changes the visibility of the picture "title" */
	private float deltaTitleTransparency;
	/** true if "title" appears*/
	private boolean titleAppearse;

	/** rules which strings are shown at the moment */
	private int state;

	/** true if the professor appears*/
	private boolean sliding;

	/** counter which rules how long trainer has to need for sliding*/
	private long counter;

	/** the animation of the trainer above the strings */
	private Animation animation;

	/** the different areas at which we worked*/
	private String area[] = new String[14];
	/** the different people who worked on the project*/
	private String name[] = new String[5];
	/** x-coordinate of the strings*/
	private int textX;
	/** y-coordinate of the strings*/
	private int textY;
	/** the different y-coordinates of the names*/
	private int[] nameY = new int[4];

	/** true if music is playing */
	private boolean isPlayingMusic;

	/**Sets the ID of this state
	 * 
	 * @param id
	 */
	public CreditsState(int id){
		ID = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {

		end = new Image("res/CreditsState/Ende.png");
		endX = (gc.getWidth() - end.getWidth())/2;
		endY = (gc.getHeight() - end.getHeight())/2;

		deltaEndTransparency = 0;
		endAppears = false;

		title = new Image("res/CreditsState/Obspann.png");
		titleX = (gc.getWidth() - title.getWidth()) / 2;
		titleY = 0;

		titleAppearse = true;
		deltaTitleTransparency = 0;

		state = 1;

		sliding = true;
		Image [] trainer = {new Image("res/CreditsState/Trainer Animation/IMG00000.bmp"), 
				new Image("res/CreditsState/Trainer Animation/IMG00001.bmp"), 
				new Image("res/CreditsState/Trainer Animation/IMG00002.bmp"), 
				new Image("res/CreditsState/Trainer Animation/IMG00003.bmp"), 
				new Image("res/CreditsState/Trainer Animation/IMG00004.bmp"), 
				new Image("res/CreditsState/Trainer Animation/IMG00005.bmp"), 
				new Image("res/CreditsState/Trainer Animation/IMG00006.bmp"), 
				new Image("res/CreditsState/Trainer Animation/IMG00007.bmp")};

		animation = new Animation(trainer, 100, true);
		animation.setAutoUpdate(true);

		area[0] = "Intro";
		area[1] = "Mapdesigner";
		area[2] = "Debugfunctions";
		area[3] = "Mapprogramming";
		area[4] = "Mapmovement";
		area[5] = "Actionfields";
		area[6] = "Startmenu";
		area[7] = "Talkings";
		area[8] = "Movement";
		area[9] = "Credits";
		area[10] = "NPCs";
		area[11] = "Deserteur";
		area[12] = "Informatik 2";

		name[0] = "Dennis Brandmüller";
		name[1] = "Oliver Alraun";
		name[2] = "Lukas Schmitt";
		name[3] = "Thomas Wenzl";

		textX = 660;
		textY = gc.getHeight()/2;

		nameY[0] = textY + 30;
		for(int x = 1; x <= 3; x++){
			nameY[x] = nameY[x-1] + 20; 
		}

		counter = 0;

		isPlayingMusic = false;
	}

	/** Renders the CreditsState
	 * @param gc the container holding the game
	 * @param game the game
	 * @param g the current graphics context
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {

		if(state != 14){
			g.drawImage(title, titleX, titleY);
			if(titleAppearse == false){
				g.drawAnimation(animation, gc.getWidth()/2, gc.getHeight()/2 - 80);
			}
		}

		g.setColor(Color.yellow);
		if(state == 1){
			sliding = true;
			g.drawString(area[0], textX, textY); //Intro
			g.drawString(name[1], textX, nameY[0]); // Oliver Alraun
		}

		else if(state == 2){
			sliding = true;
			g.drawString(area[1], textX, textY); //Mapdesigner
			g.drawString(name[0], textX, nameY[0]);// Dennis Brandmüller
			g.drawString(name[2], textX, nameY[1]);//Lukas Schmitt
		}

		else if(state == 3){
			sliding = true;
			g.drawString(area[2], textX, textY);//Debugfunctions
			g.drawString(name[1], textX, nameY[0]);//Oliver Alraun
			g.drawString(name[2], textX, nameY[1]);//Lukas Schmitt
		}
		else if(state == 4){
			sliding = true;
			g.drawString(area[3], textX, textY);//Mapprogramming
			g.drawString(name[2], textX, nameY[0]);//Lukas Schmitt
		}
		else if(state == 5){
			sliding = true;
			g.drawString(area[4], textX, textY);//Mapmovement
			g.drawString(name[0], textX, nameY[0]);//Dennis Brandmüller
			g.drawString(name[2], textX, nameY[1]);//Lukas Schmitt
		}
		else if(state == 6){
			sliding = true;
			g.drawString(area[5], textX, textY);//Actionfields
			g.drawString(name[2], textX, nameY[0]);//Lukas Schmitt
		}
		else if(state == 7){
			sliding = true;
			g.drawString(area[6], textX, textY);//Startmenu
			g.drawString(name[1], textX, nameY[0]);//Oliver Alraun
		}
		else if(state == 8){
			sliding = true;
			g.drawString(area[7], textX, textY);//Talkings
			g.drawString(name[1], textX, nameY[0]);//Oliver Alraun
			g.drawString(name[0], textX, nameY[1]);//Dennis Brandmüller
		}
		else if(state == 9){
			sliding = true;
			g.drawString(area[8], textX, textY); //Movement
			g.drawString(name[0], textX, nameY[0]);//Dennis Brandmüller
		}
		else if(state == 10){
			sliding = true;
			g.drawString(area[9], textX, textY);//Credits
			g.drawString(name[1], textX, nameY[0]);//Oliver Alraun
		}
		else if(state == 11){
			sliding = true;
			g.drawString(area[10], textX, textY);//NPCs
			g.drawString(name[0], textX, nameY[0]);//Dennis Brandmüller
		}
		else if(state == 12){
			sliding = true;
			g.drawString(area[11], textX, textY);//Deserteur
			g.drawString(name[3], textX, nameY[0]);//Thomas Wenzel
		}
		else if(state == 13){
			sliding = true;
			g.drawString(area[12], textX, textY); //Informatik 2
			g.drawString(name[0], textX, nameY[0]); //Programmieren 2
			g.drawString(name[1], textX, nameY[1]); //bei Prof. Dr. Martin Deubler
			g.drawString(name[2], textX, nameY[2]); //Projekt 2013
		}
		else if(state == 14){
			sliding = false;
			g.drawImage(end, endX, endY);
		}
	}

	/** Updates the CreditsState, processes Input, animations
	 * 
	 * @param gc the container holding the game
	 * @param game the game
	 * @param delta the number of milliseconds between frames
	 * 
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		Input input = gc.getInput();
		
		//animation of the title
		if(deltaTitleTransparency < 1 && titleAppearse == true){
			title.setAlpha(deltaTitleTransparency);
			deltaTitleTransparency += 0.004f;
		}
		else{
			titleAppearse = false;
		}
		
		//animation of the text
		if(titleAppearse == false && endAppears == false){
			if(sliding == true && textX > (gc.getWidth() - gc.getGraphics().getFont().getWidth(area[k])) / 2){ 
				textX -= 8;
			}
			else{
				sliding = false;
				if(counter <= 3000){
					counter += delta;
				}else{
					if(textX > -300){
						textX -= 8;
					}else{
						counter = 0;
						textX = 660;
						if(state < 14){
							state = state + 1;
							k = k  + 1;
						}
					}
				}
			}
		}
		
		//press escape to return to the menu
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			isPlayingMusic = false;
			Sound.audioCredits.stop();
			game.enterState(Core.menu);

		}
		//turns on the music
		if (!isPlayingMusic) {
			Sound.audioCredits.loop();
			isPlayingMusic = true;
		}

		//changes the text at the last but not least state
		if(state == 13){

			name[0] = "Programmieren 2";
			name[1] = "bei Prof. Dr. Martin Deubler";
			name[2] = "Projekt 2013";

		}

		//animation of the picture "end"
		if(state == 14){
			endAppears = true;
			if(deltaEndTransparency < 1 && endAppears == true){
				end.setAlpha(deltaEndTransparency);
				deltaEndTransparency += 0.004f;
			}
			else{
				endAppears = false;
			}
			if(endAppears == false && sliding == false){
				Sound.audioCredits.stop();
				game.enterState(Core.menu);
			}
		}
	}

	/**Sets the ID of this state
	 * 
	 * @param id
	 */
	@Override
	public int getID() {
		return ID;
	}


}
