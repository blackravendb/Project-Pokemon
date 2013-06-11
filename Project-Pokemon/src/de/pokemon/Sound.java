package de.pokemon;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sound {
	public static Audio audioSolid;
	public static Audio audioIntro;
	
	private static Audio currentAudio;
	
	private boolean isPlaying = false;
	
	
	public static void init(){
		try {
			audioSolid = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/solid.wav"));
			audioIntro = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/104_opening_selection.wav"));
		} catch (IOException e) {
			System.err.println("fail, while loading sound files");
			e.printStackTrace();
		}
		
	}
	
	public static void exit(){
		audioSolid.release();
		audioIntro.release();
		
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
