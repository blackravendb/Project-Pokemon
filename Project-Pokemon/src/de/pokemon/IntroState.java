package de.pokemon;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class IntroState extends BasicGameState {

	NameMenu menu;
	NameMenu menu2;
	
	public static int ID;
	
	Image deubler;
	Image pokemon;
	Image pokemon2;
	Image trainer;
	Image enkel;
	Image trainerSpiel;
	
	public int deublerx;
	public int deublery;
	public float deltaDeublerTransparence = 0;
	private boolean aWildDeublerAppearse = true;
	
	public int pokemonx;
	public int pokemony;
	public int pokemonXEnd;
	
	public int trainerx;
	public int trainery;
	public int trainerXEnd;
	public int trainerWidth;
	public int trainerHeight;
	public int trainerYEnd;
	
	public int enkelx;
	public int enkely;
	public int enkelXEnd;
	
	public int text;
	
	public int rectx;
	public int recty;
	public int rectwidth;
	public int rectheight;
	
	public int[] stringx = new int[10];
	public int[] stringy = new int[10];
	
	public int mittex;
	
	public int rectnamex;
	public int rectnamey;
	public int rectnamewidth;
	public int rectnameheight;
	
	public long counter;
	public long counter1;
	
	Input input;
	
	public boolean sliding;
	public boolean musicStart = true;
	
	public int i;
	public IntroState(int id){
		ID = id;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		
		menu = new NameMenu(gc, game, "ROT", "ASH", "JACK");
		menu2 = new NameMenu (gc, game, "BLAU", "GARY", "JOHN");
		
		musicStart = true;
		
		text = 1;
		
		deubler = new Image("res/Intro/lind.png");
		pokemon = new Image("res/Intro/Glurak2.png");
		pokemon2 = new Image("res/Intro/Glurak3.png");
		enkel = new Image("res/Intro/Enkel.png");
		trainer = new Image("res/Intro/trainer1.png");
		trainerSpiel = new Image("res/Intro/Player.png");
		
		deublerx = (gc.getWidth() - deubler.getWidth())/2;
		deublery = gc.getHeight()/4;
		deltaDeublerTransparence = 0;
		aWildDeublerAppearse = true;
		
		pokemonx = gc.getWidth() + 30;
		pokemony = gc.getHeight()/6;
		pokemonXEnd = (gc.getWidth() - pokemon.getWidth())/2;
		
		enkelx = gc.getWidth() + 20;
		enkely = gc.getHeight()/7;
		enkelXEnd = (gc.getWidth() - enkel.getWidth())/2;
		
		trainerx = gc.getWidth() + 20;
		trainery = gc.getHeight()/6;
		trainerXEnd = (gc.getWidth() - trainer.getWidth())/2;
		trainerWidth = trainer.getWidth();
		trainerHeight = trainer.getHeight();
		trainerYEnd = trainery + 80;
		
		rectwidth = gc.getWidth()/2;
		rectheight = gc.getHeight()/5;
		rectx = (gc.getWidth() - rectwidth)/2;
		recty = deublery + deubler.getHeight() + 50;
		
		stringy[0] = recty + 10;
		stringx[0] = rectx + 10;
		for(int i=1; i<stringy.length; i++){
			stringy[i] = stringy[i-1] + 20;
		}
		for(int i=1; i<stringx.length; i++){
			stringx[i] = stringx[0];
		}
		
		mittex = (gc.getWidth() - gc.getGraphics().getFont().getWidth("[Enter drücken]"))/2;
		
		rectnamex = (gc.getWidth() - rectnamewidth)/9;
		rectnamey = (gc.getWidth() - rectnameheight)/9;
		rectnamewidth = 150;
		rectnameheight = gc.getHeight()/3;
		sliding = true; 
		
		counter = 0;
		counter1 = 0;
		
		i = 1;
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		Input input = gc.getInput();
		
		if(text == 1){ 
			g.drawImage(deubler, deublerx, deublery);
		}
		
		if(text == 1 && aWildDeublerAppearse == false){ //Ansicht 1
			g.drawRect(rectx, recty, rectwidth, rectheight);
			g.drawString("Servus! Herzli Wuikomma in da Wäid" , stringx[0], stringy[0]); //String 1
			g.drawString("vo de Pokemon!", stringx[1], stringy[1]); //String 2
			g.drawString("I hoass DEUBLER! Man nennt mi den", stringx[2], stringy[2]); //String 3
			g.drawString("Pokemon-PROFESSOR! [Enter drücken]", stringx[3], stringy[3]); //String 4
			if(input.isKeyPressed(Input.KEY_ENTER)){
				text = 2;
			}
			}
		else if(text == 2){ //Ansicht 2
			g.drawImage(pokemon, pokemonx, pokemony);
			g.drawRect(rectx, recty, rectwidth, rectheight);
			g.drawString("De Wäid werd vo komische Wesn", stringx[0], stringy[0]); //String 1
			g.drawString("bewohnt, zu dene ma Pokemon sogt!", stringx[1], stringy[1]); //String2
			g.drawString("Fia manche Leid han Pokemon", stringx[2], stringy[2]); //String 3
			g.drawString("Haustiere, andre [Enter drücken]", stringx[3], stringy[3]); //String 4
			if(input.isKeyPressed(Input.KEY_ENTER)){
				text = 3;
			}
		}
	
		else if(text == 3){ //Ansicht 4
			g.drawImage(pokemon, pokemonx, pokemony);
			g.drawRect(rectx, recty, rectwidth, rectheight);
			g.drawString("drogn a Kämpfe, mit eana aus.", stringx[0], stringy[0]); //String1
			g.drawString("I seiba hob mei Hobby zum Beruf", stringx[1], stringy[1]); //String2
			g.drawString("gmacht und studier Pokemon.", stringx[2], stringy[2]); //String3
			g.drawString("[Enter drücken]", stringx[3], stringy[3]); //String 4
			if(input.isKeyPressed(Input.KEY_ENTER)){
				text = 4;
			}
		}
		else if(text == 4){ //Ansicht 5
			
			g.drawImage(pokemon2, pokemonx, pokemony);
			//TODO Sound abspielen!
			g.drawString("[Enter drücken]", mittex, stringy[1]);
			if(input.isKeyPressed(Input.KEY_ENTER)){
				text = 5;
				}
		}
		else if(text == 5){ //Ansicht 6
			g.drawImage(trainer, trainerx, trainery);
			g.drawRect(rectx, recty, rectwidth, rectheight);
			g.drawString("Wia hoaßtn du glei wieda?", stringx[0], stringy[0]);
			g.drawString("[Enter drücken]", stringx[1], stringy[1]);
			if(input.isKeyPressed(Input.KEY_ENTER)){ //NameMenu aufrufen
				menu.showMenu = true;
			}
			if(menu.name != null && text == 5){
				menu.showMenu = false;
				text = 6;
			}
		}
		else if(text == 6){ //Ansicht 7
			g.drawImage(trainer, trainerx, trainery);
			g.drawRect(rectx, recty, rectwidth, rectheight);
			g.drawString("Stimmt ja, du warst da " + menu.name + "!", stringx[0], stringy[0]);
			g.drawString("[Enter drücken]", stringx[1], stringy[1]);
			if(input.isKeyPressed(Input.KEY_ENTER)){ 
				text = 7;
			}
		}
		else if(text == 7){
			g.drawImage(enkel, enkelx, enkely);
			g.drawRect(rectx, recty, rectwidth, rectheight);
			g.drawString("Des is mei Enkel. Scho imma woits", stringx[0], stringy[0]);
			g.drawString("ihr besser sei wia da andere!", stringx[1], stringy[1]);
			g.drawString("Wie hoaßtn der jetz scho wieda?", stringx[2], stringy[2]);
			g.drawString("[Enter drücken]", stringx[3], stringy[3]);
			if(input.isKeyPressed(Input.KEY_ENTER)){ 
				menu2.showMenu = true;
			}
			if(menu2.name != null && text == 7){
				menu2.showMenu = false;
				text = 8;
			}
		}
		else if(text == 8){
			g.drawImage(enkel, enkelx, enkely);
			g.drawRect(rectx, recty, rectwidth, rectheight);
			g.drawString(menu2.name + "! Stimmt! Grod hob i's no", stringx[0], stringy[0]); 
			g.drawString("gwusst! [Enter drücken]", stringx[1], stringy[1]);
			if(input.isKeyPressed(Input.KEY_ENTER)){ 
				text = 9;
			}
		}
		else if(text == 9){
			g.drawImage(trainer, trainerx, trainery);
			g.drawRect(rectx, recty, rectwidth, rectheight);
			g.drawString(menu.name + "! A unglaubliche Reise in de", stringx[0], stringy[0]);
			g.drawString("Wäid da Pokemon erwartet Di!", stringx[1], stringy[1]);
			g.drawString("A Wäid voia Wunda, Obnteia", stringx[2], stringy[2]);
			g.drawString("und Geheimnisse! Des is a Draum!", stringx[3], stringy[3]);
			if(input.isKeyPressed(Input.KEY_ENTER)){ 
				text = 10;
			}
		}
		else if(text == 10){
			trainer.draw(trainerx, trainery, trainerWidth, trainerHeight);
		}
		else if(text == 11){
			trainer.draw(trainerx, trainery, trainerWidth, trainerHeight);		
			if (counter1 >= 1000){
			text = 12;
			}
		}
		else if(text == 12){
			g.drawImage(trainerSpiel, trainerx, trainery); 
			if (counter >= 1000){
			text = 13;
			}
		}
		else if(text == 13){
			text = 0;
			game.enterState(1);
		}
		if(menu.showMenu && text == 5){
			menu.render(g);
		}
		if(menu2.showMenu && text == 7){
			menu2.render(g);
		}
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		input = gc.getInput();
		
		if(musicStart){
			Sound.audioIntro.playAsMusic(1.0f, 1.0f, true);
			musicStart = false;
		}
		
		if(menu.showMenu && text == 5){
			menu.update(input);
		}
		
		if(text == 12){
			if (counter <= 1000){
			counter += delta;
			}
		}
		
		if(text == 10){
			sliding = true;
			if(trainery < trainerYEnd && sliding == true){
				trainery += 1;
			}else{
				sliding = false;
			}
			if(sliding == false){
				text = 11;
			}
		}
		if (text == 11){ //trainer Animation Übergang + counter start

			if(counter1 <= 1000 && sliding == false){
				counter1 += delta;
			}
			if(trainerHeight > 20){//i<70
				trainerWidth -= trainerWidth/40;
				trainerHeight -= trainerHeight/40;
				trainerx = (gc.getWidth() - trainerWidth)/2;
				trainery = (gc.getHeight() - trainerHeight)/2;
				//i++;
			}
		}	
		
		if (text == 1){ //Deubler Animation
			if(deltaDeublerTransparence < 1 && aWildDeublerAppearse == true){
				deubler.setAlpha(deltaDeublerTransparence);
				deltaDeublerTransparence += 0.004f;
			}
			else{
				aWildDeublerAppearse = false;
				System.out.println("blub");
			}
		}
		if (text == 2){ //Glurak Animation
			sliding = true;
			if(pokemonx > pokemonXEnd && sliding == true){
				pokemonx -= 8;
			}
			else{
				sliding = false;
			}
			//animation(pokemonx, pokemonXEnd);
		}
		if (text == 5){ //Trainer Animation
			sliding = true;
			if(trainerx > trainerXEnd && sliding == true){
				trainerx -= 8;
			}
			else{
				sliding = false;
			}
			//animation(trainerx, trainerXEnd);
		}
		if (text == 7){ //Enkel Animation
			sliding = true;
			if(enkelx > enkelXEnd && sliding == true){
				enkelx -= 8;
			}
			else{
				sliding = false;
			}
			//animation(enkelx, enkelXEnd);
		}
		
		if(menu2.showMenu && text == 7){
			menu2.update(input);
		}
		
		if(text==13)
			Sound.audioIntro.stop();
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
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
