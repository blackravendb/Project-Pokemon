package de.pokemon;

import java.io.IOException;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sound {
	public static Audio audioSolid;
	public static Audio audioIntro;
	public static Audio audioGlurak;
	public static Audio audioTextBox;
	public static Music audioMenu;
	public static Music audioInGame;
	private static Audio currentAudio;
	public static Music audioCredits;
	
	private boolean isPlaying = false;
	
	
	public static void init(){
		try {
			//WAV
		/*	audioSolid = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/solid.wav"));
			audioIntro = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/104_opening_selection.wav"));
			audioGlurak = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/Glurak.wav"));
			audioTextBox = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/TextBox.wav"));
			//audioMenu = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/Intro/Men�Musik.wav"));*/
			//OGG
			audioSolid = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/sounds/solid.ogg"));
			audioIntro = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/sounds/104_opening_selection.ogg"));
			audioGlurak = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/sounds/Glurak.ogg"));
			audioTextBox = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/sounds/TextBox.ogg"));
		//	audioMenu = new Music("res/Intro/Men�Musik.wav");
			audioMenu = new Music("res/Intro/Men�Musik.ogg");
			audioInGame = new Music("res/sounds/Home-theme.ogg");
			audioCredits = new Music("res/CreditsState/Musik.ogg");
		} catch (IOException | SlickException e) {
			System.err.println("fail, while loading sound files");
			e.printStackTrace();
		}
		
	}
	
	public static void exit(){
		audioSolid.release();
		audioIntro.release();
		audioGlurak.release();
		audioTextBox.release();
		audioMenu.release();
		audioCredits.release();
		
		currentAudio.release();
	}
	
	public static void playAudioIntro(){
		currentAudio = audioIntro;
		currentAudio.playAsMusic(1.0f, 1.0f, true);
	}
	
	public static void changeSound(Audio audio){
		if(currentAudio == audio)
			return;
	}
	
	public static void update(){
		
	}
}
