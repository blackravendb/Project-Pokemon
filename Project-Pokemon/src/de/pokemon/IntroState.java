package de.pokemon;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class IntroState extends BasicGameState{
	/** ID of the IntroState */
	public static int ID;
	
	/** NameMenu for the player */
	private NameMenu menu_1;
	/** NameMenu for the grandson*/
	private NameMenu menu_2;
	
	/** TextBox of the Intro*/
	private TextBox textBox;
	
	/** image of the professor*/
	private Image prof;
	/** image of Glurak*/
	private Image pokemon_1;
	/** image for the animation of Glurak*/
	private Image pokemon_2;
	/** image of the player*/
	private Image trainer;
	/** image of the grandson*/
	private Image grandson;
	/** image of the trainer how he looks in the game*/
	private Image trainerGame;
	
	/** x-coordinate of the professor*/
	private int profX;
	/** y-coordinate of the professor*/
	private int profY;
	/** rules the transparency of the professor*/
	private float deltaProfTransparency = 0;
	/** true if the professor appears*/
	private boolean aWildDeublerAppears = true;
	
	/** x-coordinate of pokemon_1 and pokemon_2*/
	private int pokemonX;
	/** y-coordinate of pokemon_1 and pokemon_2*/
	private int pokemonY;
	/** x-coordinate where pokemon_1 and pokemon_2 stops sliding*/
	private int pokemonXEnd;
	
	/** x-coordinate of the trainer*/
	private int trainerX;
	/** y-coordinate of the trainer*/
	private int trainerY;
	/** x-coordinate where trainer stops sliding*/
	private int trainerXEnd;
	/** width of the trainer*/
	private int trainerWidth;
	/** height of the trainer*/
	private int trainerHeight;
	/** y-coordinate where trainer stops sliding*/
	private int trainerYEnd;
	
	/** x-coordinate of grandson*/
	private int grandsonX;
	/** y-coordinate of grandson*/
	private int grandsonY;
	/** x-coordinate where grandson stops sliding*/
	private int grandsonXEnd;
	
	/** rules which objects are rendering or updating*/
	private int state;
	/** rules which text should be shown in the TextBox*/
	private int setText;
	
	/** counter which rules how long trainer has to need for sliding*/
	private long counter_1;
	/** counter which rules how long trainer has to need for contraction*/
	private long counter_2;
	
	/** true if something is sliding*/
	private boolean sliding;
	
	/** true if music should start*/
	private boolean musicStart;
	/** true if glurak has to bawl*/
	private boolean glurakSound;
	
	/**Sets the ID of this state
	 * 
	 * @param id
	 */
	public IntroState(int id){
		ID = id;
	}
	
	
	/**Initialises the IntroState
	 * 
	 * @param gc the container holding the game
	 * @param game the game
	 * 
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		
		menu_1 = new NameMenu(gc, game, true, "FRANZ", "TONI", "SEPP");
		menu_2 = new NameMenu (gc, game, false, "Sirgo", "Svenni", "Olaf");
		
		musicStart = true;
		glurakSound = true;
		
		textBox = new TextBox("Servus! Herzli Wuikomma in da W‰id vo de Pokemon! I hoass DEUBLER! Man nennt mi den Pokemon - PROFESSOR!", Color.black, Color.white, gc);
		
		state = 1;
		setText = 1;
		
		prof = new Image("res/Intro/lind.png");
		pokemon_1 = new Image("res/Intro/Glurak2.png");
		pokemon_2 = new Image("res/Intro/Glurak3.png");
		grandson = new Image("res/Intro/Enkel.png");
		trainer = new Image("res/Intro/trainer1.png");
		trainerGame = new Image("res/Intro/Player.png");
		
		profX = (gc.getWidth() - prof.getWidth())/2;
		profY = gc.getHeight()/4;
		deltaProfTransparency = 0;
		aWildDeublerAppears = true;
		
		pokemonX = gc.getWidth() + 30;
		pokemonY = gc.getHeight()/6;
		pokemonXEnd = (gc.getWidth() - pokemon_1.getWidth())/2;
		
		grandsonX = gc.getWidth() + 20;
		grandsonY = gc.getHeight()/5;
		grandsonXEnd = (gc.getWidth() - grandson.getWidth())/2;
		
		trainerX = gc.getWidth() + 20;
		trainerY = gc.getHeight()/5;
		trainerXEnd = (gc.getWidth() - trainer.getWidth())/2;
		trainerWidth = trainer.getWidth();
		trainerHeight = trainer.getHeight();
		trainerYEnd = trainerY + 80;
		
		sliding = true; 
		
		counter_1 = 0;
		counter_2 = 0;
	}

	
	/** Renders the IntroState
	 * @param gc the container holding the game
	 * @param game the game
	 * @param g the current graphics context
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		
		if(state == 1){//renders state 1
		g.drawImage(prof, profX, profY);
		if(aWildDeublerAppears == false){ 
			textBox.render(g);
			}
		}
		
		else if(state == 2){ //renders state 2
			g.drawImage(pokemon_1, pokemonX, pokemonY);
			textBox.render(g);
		}
		
		else if(state == 3){ //renders state 3
			g.drawImage(pokemon_2, pokemonX, pokemonY);
		}
		else if(state == 4){ //renders state 4
			g.drawImage(trainer, trainerX, trainerY);
			textBox.render(g);
		}
		else if(state == 5){ //renders state 5
			g.drawImage(trainer, trainerX, trainerY);
			textBox.render(g);
		}
		else if(state == 6){//renders state 6
			g.drawImage(grandson, grandsonX, grandsonY);
			textBox.render(g);
			}
		else if(state == 7){//renders state 7
			g.drawImage(grandson, grandsonX, grandsonY);
			textBox.render(g);
		}
		else if(state == 8){//renders state 8
			g.drawImage(trainer, trainerX, trainerY);
			textBox.render(g);
		}
		else if(state == 9){//renders state 9
			trainer.draw(trainerX, trainerY, trainerWidth, trainerHeight);		
		}
		else if(state == 10){//renders state 10
			g.drawImage(trainerGame, trainerX, trainerY); 
		}
		if(menu_1.showMenu && state == 4){
			menu_1.render(g);
		}
		if(menu_2.showMenu && state == 6){
			menu_2.render(g);
		}
	}
	
	
	/** Updates the IntroState, e.g. TextBox, processes Input, animations
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
		
		if(musicStart){
			Sound.audioIntro.playAsMusic(1.0f, 1.0f, true);
			musicStart = false;
		}
		
		if (state == 1){ //animation of the professor
			if(deltaProfTransparency < 1 && aWildDeublerAppears == true){
				prof.setAlpha(deltaProfTransparency);
				deltaProfTransparency += 0.004f;
			}
			else{
				aWildDeublerAppears = false;
			}
			if(aWildDeublerAppears == false)
				textBox.update(input, delta); 
			
			if(aWildDeublerAppears == false && textBox.textBox == 1){ //changes the content of the TextBox
				state = 2;
				}
		}
		
		if (state == 2){ //animation of Glurak
			if(setText == 1){
				textBox.setText("De W‰id werd vo komische Wesn bewohnt, zu dene ma Pokemon sogt! Fia manche Leid han Pokemon Haustiere, andre drogn a K‰mpfe mit eana aus. I seiba hob mei Hobby zum Beruf gmacht und studier Pokemon.");
				setText = 2;
			}
			sliding = true;
			if(pokemonX > pokemonXEnd && sliding == true){
				pokemonX -= 8;
			}
			else{
				sliding = false;
			}
			//animation(pokemonx, pokemonXEnd);
			if(sliding == false)
			textBox.update(input, delta);
			
			if(textBox.textBox == 2){
				state = 3;
			}
		}
		
		if(state == 3){ //changes the content of the TextBox
			if(glurakSound == true){
				Sound.audioGlurak.playAsSoundEffect(1.0f, 3.0f, false);
				glurakSound = false;
			}
			if(Sound.audioGlurak.isPlaying() == false){ 
				state = 4;
				}
		}
		
		if (state == 4){ //animation of the trainer
			sliding = true;
			if(trainerX > trainerXEnd && sliding == true){
				trainerX -= 8;
			}
			else{
				sliding = false;
			}
			if(!menu_1.showMenu){ 
				if(setText == 2){
				textBox.setText("Wia hoaﬂtn du glei wieda?");
				setText = 3;
				}
				if(sliding == false)
				textBox.update(input, delta);
			}
			else if(menu_1.showMenu && sliding == false){
				textBox.update = false;
				textBox.showTriangle = false;
				menu_1.update(input, delta);
				if(menu_1.showTextField == true){
					menu_1.update = false;
				}
			}
			if(menu_1.name != null){
				menu_1.showMenu = false;
				state = 5;
			}
			//animation(trainerx, trainerXEnd);
			if(textBox.textBox == 3 && sliding == false){
				menu_1.showMenu = true; //opens the NameMenu
				}
		}
		
		if(state == 5) {
			textBox.update(input, delta);
			
			if(setText == 3){
				textBox.setText("Stimmt ja, du warst da " + menu_1.name + "!");
				setText = 4;
			}
			
			if(textBox.textBox == 4){ // changes the content of the TextBox
				state = 6;
			}
		}
		
		if (state== 6){ //animation of the grandson
			sliding = true;
			if(grandsonX > grandsonXEnd && sliding == true){
				grandsonX -= 8;
			}
			else{
				sliding = false;
			}
			
			if(!menu_2.showMenu){ 
			if(setText == 4){
				textBox.setText("Des is mei Enkel. Scho imma woits ihr besser sei wia da andere! Wie hoaﬂtn der jetz scho wieda?");
				setText = 5;
			}
			if(sliding == false)
				textBox.update(input, delta);
			}
			else if(menu_2.showMenu && sliding == false){
				textBox.update = false;
				textBox.showTriangle = false;
				menu_2.update(input, delta);
				if(menu_2.showTextField == true){
					menu_2.update = false;
				}
			}
			if(menu_2.name != null){
				menu_2.showMenu = false;
				state = 7;
			}
			
			if(textBox.textBox == 5 && sliding == false){
				menu_2.showMenu = true; //opens the NameMenu
				}
		}
		
		if(state == 7){
			if(setText == 5){
				textBox.setText(menu_2.name + "! Stimmt! Grod hob i's no gwusst!");
				setText = 6;
			}
			
			textBox.update(input, delta);
			
			if(textBox.textBox == 6){// changes the TextBox
				state = 8;
			}
		}
		
		if(state == 8){
			if(setText == 6){
				textBox.setText(menu_1.name + "! A unglaubliche Reise in de W‰id da Pokemon erwart Di! A W‰id voia Wunda, Obnteia und Geheimnisse! Des is a Draum!");
				setText = 7;
			}
			
			if(textBox.textBox == 6) 
			textBox.update(input, delta);
			
			sliding = true;
			
			if(textBox.textBox == 7){
			if(trainerY < trainerYEnd && sliding == true){
				trainerY += 1;
			}else{
				sliding = false;
			}
			if(sliding == false){
				state = 9;
			}
			}
		}
		
		if (state == 9){ //animation of the trainer and counter starts 

			if(counter_2 <= 1000 && sliding == false){
				counter_2 += delta;
			}
			if(trainerHeight > 20){
				trainerWidth -= trainerWidth/40;
				trainerHeight -= trainerHeight/40;
				trainerX = (gc.getWidth() - trainerWidth)/2;
				trainerY = (gc.getHeight() - trainerHeight)/2;
			}
			if (counter_2 >= 1000){
				state = 10;
				}
		}
		
		if(state == 10){
			if (counter_1 <= 1000){
			counter_1 += delta;
			}
			if (counter_1 >= 1000){
				state= 11;
				}
		}
		
		if(state == 11){
			state = 0;
			game.enterState(1);
			Sound.audioIntro.stop();
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
	
	/*
	public void animation(int koordinateX, int ende){
		if(koordinateX > ende && sliding == true){
			koordinateX -= 16;
		}
		else{
			sliding = false;
		}
	}
	*/

}



