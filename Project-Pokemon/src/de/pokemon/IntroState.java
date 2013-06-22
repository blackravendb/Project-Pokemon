package de.pokemon;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class IntroState extends BasicGameState{

	NameMenu menu;
	NameMenu menu2;
	
	TextBox textBox1;
	
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
	
	public int text;
	public int setText;
	
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
	
	public int rectx;
	public int recty;
	public int rectwidth;
	public int rectheight;
	
	public int[] stringx = new int[10];
	public int[] stringy = new int[10];
	
	public int mittex;
	
	public long counter;
	public long counter1;
	
	Input input;
	
	public boolean sliding;
	
	public boolean musicStart;
	public boolean glurakSound;
	
	public int i;
	public IntroState(int id){
		ID = id;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		
		menu = new NameMenu(gc, game, "FRANZ", "TONI", "SEPP");
		menu2 = new NameMenu (gc, game, "Sirgo", "Svenni", "Olaf");
		
		musicStart = true;
		glurakSound = true;
		
		textBox1 = new TextBox("Servus! Herzli Wuikomma in da W�id vo de Pokemon! I hoass DEUBLER! Man nennt mi den Pokemon - PROFESSOR!", Color.black, Color.white, gc);
		
		text = 1;
		setText = 1;
		
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
		enkely = gc.getHeight()/5;
		enkelXEnd = (gc.getWidth() - enkel.getWidth())/2;
		
		trainerx = gc.getWidth() + 20;
		trainery = gc.getHeight()/5;
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
		
		mittex = (gc.getWidth() - gc.getGraphics().getFont().getWidth("[Enter dr�cken]"))/2;
	
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
		if(aWildDeublerAppearse == false){ //Ansicht 1
			textBox1.render(g);
			/*if(text == 1 && aWildDeublerAppearse == false && textBox1.textBox == 1){
				text = 2;
				}*/
			}
		}
		
		else if(text == 2){ //Ansicht 2
			g.drawImage(pokemon, pokemonx, pokemony);
			textBox1.render(g);
			
			//textBox2.render(g);
			/*if(textBox1.textBox == 2){
				text = 3;
			}*/
		}
		
		else if(text == 3){ //Ansicht 5
			g.drawImage(pokemon2, pokemonx, pokemony);
		}
		else if(text == 4){ //Ansicht 6
			g.drawImage(trainer, trainerx, trainery);
			textBox1.render(g);
			/*if(textBox3.textBox == 1 && sliding == false){
				menu.showMenu = true; //NameMenu aufrufen
				}*/
		}
		else if(text == 5){ //Ansicht 7
			g.drawImage(trainer, trainerx, trainery);
			textBox1.render(g);
			/*if(textBox4.textBox == 1){
				text = 6;
			}*/
		}
		else if(text == 6){
			g.drawImage(enkel, enkelx, enkely);
			textBox1.render(g);
			/*if(textBox5.textBox == 1 && sliding == false){
				menu2.showMenu = true; //NameMenu aufrufen
				}*/
			}
		else if(text == 7){
			g.drawImage(enkel, enkelx, enkely);
			textBox1.render(g);
			/*if(textBox6.textBox == 1){
				text = 8;
			}*/
		}
		else if(text == 8){
			g.drawImage(trainer, trainerx, trainery);
			textBox1.render(g);
			/*if(textBox7.textBox == 1){
				text = 9;
			}*/
		}
		else if(text == 9){
			trainer.draw(trainerx, trainery, trainerWidth, trainerHeight);		
			/*if (counter1 >= 1000){
			text = 10;
			}*/
		}
		else if(text == 10){
			g.drawImage(trainerSpiel, trainerx, trainery); 
			/*if (counter >= 1000){
			text= 11;
			}*/
		}
		/*else if(text == 11){
			text = 0;
			game.enterState(1);
		}*/
		if(menu.showMenu && text == 4){
			menu.render(g);
		}
		if(menu2.showMenu && text == 6){
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
		
		if (text == 1){ //Deubler Animation
			if(deltaDeublerTransparence < 1 && aWildDeublerAppearse == true){
				deubler.setAlpha(deltaDeublerTransparence);
				deltaDeublerTransparence += 0.004f;
			}
			else{
				aWildDeublerAppearse = false;
				//System.out.println("blub");
			}
			if(aWildDeublerAppearse == false)
				textBox1.update(input, delta); 
			
			if(aWildDeublerAppearse == false && textBox1.textBox == 1){ //�ndert die Textbox
				text = 2;
				}
		}
		
		if (text == 2){ //Glurak Animation
			if(setText == 1){
				textBox1.setText("De W�id werd vo komische Wesn bewohnt, zu dene ma Pokemon sogt! Fia manche Leid han Pokemon Haustiere, andre drogn a K�mpfe mit eana aus. I seiba hob mei Hobby zum Beruf gmacht und studier Pokemon.");
				setText = 2;
			}
			sliding = true;
			if(pokemonx > pokemonXEnd && sliding == true){
				pokemonx -= 8;
			}
			else{
				sliding = false;
			}
			//animation(pokemonx, pokemonXEnd);
			//textBox2.update(input, delta);
			if(sliding == false)
			textBox1.update(input, delta);
			
			if(textBox1.textBox == 2){
				text = 3;
			}
		}
		
		if(text == 3){ //�ndert die Textbox
			if(glurakSound == true){
				Sound.audioGlurak.playAsSoundEffect(1.0f, 3.0f, false);
				glurakSound = false;
			}
			if(Sound.audioGlurak.isPlaying() == false){ //input.isKeyPressed(Input.KEY_ENTER) && 
				text = 4;
				}
		}
		
		if (text == 4){ //Trainer Animation
			sliding = true;
			if(trainerx > trainerXEnd && sliding == true){
				trainerx -= 8;
			}
			else{
				sliding = false;
			}
			if(!menu.showMenu){ // Wenn das Men� nicht angezeigt wird, soll die TextBox upgedatet werden
				if(setText == 2){
				textBox1.setText("Wia hoa�tn du glei wieda?");
				setText = 3;
				}
				if(sliding == false)
				textBox1.update(input, delta);
			}
			else if(menu.showMenu && sliding == false){
				textBox1.update = false;
				textBox1.showDreieck = false;
				menu.update(input, delta);
				if(menu.showTextField == true){
					menu.update = false;
				}
			}
			if(menu.name != null){
				menu.showMenu = false;
				text = 5;
			}
			//animation(trainerx, trainerXEnd);
			if(textBox1.textBox == 3 && sliding == false){
				menu.showMenu = true; //NameMenu aufrufen
				}
		}
		
		if(text == 5) {
			textBox1.update(input, delta);
			
			if(setText == 3){
				textBox1.setText("Stimmt ja, du warst da " + menu.name + "!");
				setText = 4;
			}
			//textBox4 = new TextBox("Stimmt ja, du warst da " + menu.name + "!", Color.black, Color.white, gc); // update Fehler, weils nicht oben erzeugt wird
			
			if(textBox1.textBox == 4){ // �ndert die TextBox
				text = 6;
			}
		}
		
		if (text== 6){ //Enkel Animation
			sliding = true;
			if(enkelx > enkelXEnd && sliding == true){
				enkelx -= 8;
			}
			else{
				sliding = false;
			}
			
			if(!menu2.showMenu){ // Wenn das Men� nicht angezeigt wird, soll die TextBox upgedatet werden
			if(setText == 4){
				textBox1.setText("Des is mei Enkel. Scho imma woits ihr besser sei wia da andere! Wie hoa�tn der jetz scho wieda?");
				setText = 5;
			}
			//kopiert!!!
			if(sliding == false)
				textBox1.update(input, delta);
			}
			else if(menu2.showMenu && sliding == false){
				textBox1.update = false;
				textBox1.showDreieck = false;
				menu2.update(input, delta);
				if(menu2.showTextField == true){
					menu2.update = false;
				}
			}
			//kopiert
			
			if(menu2.name != null){
				menu2.showMenu = false;
				text = 7;
			}
			
			if(textBox1.textBox == 5 && sliding == false){
				menu2.showMenu = true; //NameMenu aufrufen
				}
		}
		
		if(text == 7){
			if(setText == 5){
				textBox1.setText(menu2.name + "! Stimmt! Grod hob i's no gwusst!");
				setText = 6;
			}
			//textBox6 = new TextBox(menu2.name + "! Stimmt! Grod hob i's no gwusst!", Color.black, Color.white, gc);
			
			textBox1.update(input, delta);
			
			if(textBox1.textBox == 6){// �ndert die TextBox
				text = 8;
			}
		}
		
		if(text == 8){
			if(setText == 6){
				textBox1.setText(menu.name + "! A unglaubliche Reise in de W�id da Pokemon erwart Di! A W�id voia Wunda, Obnteia und Geheimnisse! Des is a Draum!");
				setText = 7;
			}
			
			if(textBox1.textBox == 6) 
			textBox1.update(input, delta);
			
			sliding = true;
			
			if(textBox1.textBox == 7){
			if(trainery < trainerYEnd && sliding == true){
				trainery += 1;
			}else{
				sliding = false;
			}
			if(sliding == false){
				text = 9;
			}
			}
		}
		
		if (text == 9){ //trainer Animation �bergang + counter start

			if(counter1 <= 1000 && sliding == false){
				counter1 += delta;
			}
			if(trainerHeight > 20){
				trainerWidth -= trainerWidth/40;
				trainerHeight -= trainerHeight/40;
				trainerx = (gc.getWidth() - trainerWidth)/2;
				trainery = (gc.getHeight() - trainerHeight)/2;
			}
			if (counter1 >= 1000){
				text = 10;
				}
		}
		
		if(text == 10){
			if (counter <= 1000){
			counter += delta;
			}
			if (counter >= 1000){
				text= 11;
				}
		}
		
		if(text == 11){
			text = 0;
			game.enterState(1);
			Sound.audioIntro.stop();
		}
			
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
