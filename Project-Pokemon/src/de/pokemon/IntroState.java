package de.pokemon;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class IntroState extends BasicGameState {

	NameMenu menu;
	
	public static int ID;
	
	Image deubler;
	Image pokemon;
	Image pokemon2;
	Image trainer;
	
	public int deublerx;
	public int deublery;
	
	public int text;
	
	public int rectx;
	public int recty;
	public int rectwidth;
	public int rectheight;
	
	public int[] stringx = new int[10];
	public int[] stringy = new int[10];
	
	public int pokemonx;
	public int pokemony;
	
	public int trainerx;
	public int trainery;
	
	public int mittex;
	
	public int rectnamex;
	public int rectnamey;
	public int rectnamewidth;
	public int rectnameheight;
	
	Input input;
	
	public IntroState(int id){
		ID = id;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		menu = new NameMenu(gc, game);
		
		deubler = new Image("res/lind.png");
		
		deublerx = (gc.getWidth() - deubler.getWidth())/2;
		deublery = gc.getHeight()/4;
		
		text = 1;
		
		rectwidth = gc.getWidth()/2;
		rectheight = gc.getHeight()/5;
		rectx = (gc.getWidth() - rectwidth)/2;
		recty = deublery + deubler.getHeight() + 15;
		
		stringy[0] = recty + 10;
		stringx[0] = rectx + 10;
		for(int i=1; i<stringy.length; i++){
			stringy[i] = stringy[i-1] + 20;
		}
		for(int i=1; i<stringx.length; i++){
			stringx[i] = stringx[0];
		}
		
		pokemon = new Image("res/Glurak2.png");
		
		pokemonx = (gc.getWidth() - pokemon.getWidth())/2;
		pokemony = gc.getHeight()/6;
		
		pokemon2 = new Image("res/Glurak3.png");
		
		trainer = new Image("res/trainer1.png");
		trainerx = (gc.getWidth() - pokemon.getWidth())/2;
		trainery = gc.getHeight()/6;
		
		mittex = (gc.getWidth() - gc.getGraphics().getFont().getWidth("[Enter drücken]"))/2;
		
		rectnamex = (gc.getWidth() - rectnamewidth)/9;
		rectnamey = (gc.getWidth() - rectnameheight)/9;
		rectnamewidth = 150;
		rectnameheight = gc.getHeight()/3;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		Input input = gc.getInput();
		
		if (text == 1){ //Ansicht 1
			g.drawImage(deubler, deublerx, deublery);
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
			//TODO Bilder animieren
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
		}
		if(menu.showMenu){
			menu.render(g);
		}
		
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		input = gc.getInput();
		
		if(menu.showMenu){
			menu.update(input);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

}
