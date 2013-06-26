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
	
	private int k;
	
	Image title;
	private int titleX;
	private int titleY;
	
	private int state;
	
	/** true if the professor appears*/
	private boolean sliding;
	
	/** counter which rules how long trainer has to need for sliding*/
	private long counter;
	
	private Animation animation;
	
	private String area[] = new String[15];
	private String name[] = new String[5];
	private int textX;
	private int textY;
	private int[] nameY = new int[4];
	
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
		
		title = new Image("res/CreditsState/Obspann.png");
		titleX = (gc.getWidth() - title.getWidth()) / 2;
		titleY = 0;
		
		state = 1;
		
		sliding = true;
		Image [] trainer = {new Image("res/CreditsState/Trainer Animation/IMG00000.bmp"), new Image("res/CreditsState/Trainer Animation/IMG00001.bmp"), new Image("res/CreditsState/Trainer Animation/IMG00002.bmp"), new Image("res/CreditsState/Trainer Animation/IMG00003.bmp"), new Image("res/CreditsState/Trainer Animation/IMG00004.bmp"), new Image("res/CreditsState/Trainer Animation/IMG00005.bmp"), new Image("res/CreditsState/Trainer Animation/IMG00006.bmp"), new Image("res/CreditsState/Trainer Animation/IMG00007.bmp")};
		
		animation = new Animation(trainer, 100, true);
		animation.setAutoUpdate(true);
		
		area[0] = "Blabla"; //Director
		area[1] = "Intro";
		area[2] = "Mapdesigner";
		area[3] = "Graphic Artist";
		area[4] = "Debugfunctions";
		area[5] = "Mapprogramming";
		area[6] = "Mapmovement";
		area[7] = "Actionfields";
		area[8] = "Startmenu";
		area[9] = "Talkings";
		area[10] = "Movement";
		area[11] = "Credits";
		area[12] = "NPCs";
		area[13] = "Deserteur";
		
		
		name[0] = "Dennis Brandmüller";
		name[1] = "Oliver Alraun";
		name[2] = "Lukas Schmitt";
		name[3] = "Thomas Wenzl";
		
		textX = 660;
		textY = gc.getHeight()/2;
		
		nameY[0] = textY + 20;
		for(int x = 1; x <= 3; x++){
			nameY[x] = nameY[x-1] + 20; 
		}
		
		counter = 0;
		
		isPlayingMusic = false;
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		
		g.drawImage(title, titleX, titleY);
		g.drawAnimation(animation, gc.getWidth()/2, gc.getHeight()/2 - 80);
		
		g.setColor(Color.yellow);
		if(state == 1){
			sliding = true;
			g.drawString(area[0], textX, textY);
			g.drawString(name[0], textX, nameY[0]);
		}
		
		else if(state == 2){
			sliding = true;
			g.drawString(area[1], textX, textY);
			g.drawString(name[1], textX, nameY[0]);
		}
		
		else if(state == 3){
			sliding = true;
			g.drawString(area[2], textX, textY);
			g.drawString(name[0], textX, nameY[0]);
			g.drawString(name[2], textX, nameY[1]);
		}
		else if(state == 4){
			sliding = true;
			g.drawString(area[3], textX, textY);
			g.drawString(name[1], textX, nameY[0]);
			g.drawString(name[2], textX, nameY[1]);
		}
		else if(state == 5){
			sliding = true;
			g.drawString(area[4], textX, textY);
			g.drawString(name[1], textX, nameY[0]);
			g.drawString(name[2], textX, nameY[1]);
		}
		else if(state == 6){
			sliding = true;
			g.drawString(area[5], textX, textY);
			g.drawString(name[0], textX, nameY[0]);
			g.drawString(name[2], textX, nameY[1]);
		}
		else if(state == 7){
			sliding = true;
			g.drawString(area[6], textX, textY);
			g.drawString(name[2], textX, nameY[0]);
		}
		else if(state == 8){
			sliding = true;
			g.drawString(area[7], textX, textY);
			g.drawString(name[0], textX, nameY[0]);
			g.drawString(name[2], textX, nameY[1]);
		}
		else if(state == 9){
			sliding = true;
			g.drawString(area[8], textX, textY);
			g.drawString(name[1], textX, nameY[0]);
		}
		else if(state == 10){
			sliding = true;
			g.drawString(area[9], textX, textY);
			g.drawString(name[0], textX, nameY[0]);
		}
		else if(state == 11){
			sliding = true;
			g.drawString(area[10], textX, textY);
			g.drawString(name[0], textX, nameY[0]);
		}
		else if(state == 12){
			sliding = true;
			g.drawString(area[11], textX, textY);
			g.drawString(name[1], textX, nameY[0]);
		}
		else if(state == 13){
			sliding = true;
			g.drawString(area[12], textX, textY);
			g.drawString(name[0], textX, nameY[0]);
		}
		else if(state == 14){
			sliding = true;
			g.drawString(area[13], textX, textY);
			g.drawString(name[3], textX, nameY[0]);
		}
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		Input input = gc.getInput();
		
		if(sliding == true && textX > (gc.getWidth() - gc.getGraphics().getFont().getWidth(area[k])) / 2){ //animation of the text
			textX -= 8;
		}
		else{
			sliding = false;
			if(counter <= 3000){
				counter += delta;
			}else{
				if(textX > -200){
					textX -= 8;
				}else{
				counter = 0;
				textX = 660;
				state += state;
				k += k;
				}
			}
		}
		
		if(input.isKeyPressed(input.KEY_ESCAPE)){
			isPlayingMusic = false;
			Sound.audioCredits.stop();
			game.enterState(Core.menu);
			
		}
		if (!isPlayingMusic) {
			Sound.audioCredits.loop();
			isPlayingMusic = true;
		}
		}
		
	
	@Override
	public int getID() {
		return ID;
	}

		
}
