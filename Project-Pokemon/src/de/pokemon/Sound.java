package de.pokemon;

import java.io.IOException;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 * Allgemeine Datensammlung für Sound Dateien. Bietet static Methoden wodurch
 * sounds abgespielt und gewechselt werden können
 * 
 * @author Dennis
 */
public class Sound {
	// Sound Container
	/**
	 * Solid Sound. Wird von Player wiedergegeben, sollte er gegen einen Soliden
	 * Block laufen
	 */
	public static Audio audioSolid;
	/** Intro Musik */
	public static Audio audioIntro;
	/** Intro Glurak gebrüll */
	public static Audio audioGlurak;
	/** Klick Geräusch, wenn im Menü der Cursor versetzt wird */
	public static Audio audioTextBox;
	/** Boarische Menü Musik */
	public static Music audioMenu;
	/** In-Game Musik */
	public static Music audioInGame;
	/** Creditmusik */
	public static Music audioCredits;
	/** Haus Musik */
	public static Audio audioHouse;
	/** Musik außerhalb vom Zuhause */
	public static Audio audioHome;
	/** Musik in der Stadt */
	public static Audio audioTown;
	/** Musik in der FH */
	public static Audio audioUniversity;
	/** Musik im Studentenwohnheim */
	public static Audio audioTownHouse1;
	/** Referenzvariable, welche Musik im moment abgespielt wird */
	private static Audio currentAudio;

	/**
	 * Init Methode der Klasse Sound. Wird einmalig beim starten des Spiels
	 * aufgerufen
	 * 
	 * @return void
	 */
	public static void init() {
		try {
			audioSolid = AudioLoader.getAudio("OGG",
					ResourceLoader.getResourceAsStream("res/sounds/solid.ogg"));
			audioIntro = AudioLoader
					.getAudio(
							"OGG",
							ResourceLoader
									.getResourceAsStream("res/sounds/104_opening_selection.ogg"));
			audioGlurak = AudioLoader
					.getAudio("OGG", ResourceLoader
							.getResourceAsStream("res/sounds/Glurak.ogg"));
			audioTextBox = AudioLoader.getAudio("OGG", ResourceLoader
					.getResourceAsStream("res/sounds/TextBox.ogg"));
			audioMenu = new Music("res/Intro/MenüMusik.ogg");
			audioInGame = new Music("res/sounds/Home-theme.ogg");
			audioCredits = new Music("res/CreditsState/Musik.ogg");
			audioHouse = AudioLoader.getAudio("OGG",
					ResourceLoader.getResourceAsStream("res/sounds/house.ogg"));
			audioHome = AudioLoader.getAudio("OGG",
					ResourceLoader.getResourceAsStream("res/sounds/home.ogg"));
			audioTown = AudioLoader.getAudio("OGG",
					ResourceLoader.getResourceAsStream("res/sounds/town.ogg"));
			audioUniversity = AudioLoader.getAudio("OGG", ResourceLoader
					.getResourceAsStream("res/sounds/university.ogg"));
			audioTownHouse1 = AudioLoader.getAudio("OGG", ResourceLoader
					.getResourceAsStream("res/sounds/townHouse1.ogg"));
		} catch (IOException | SlickException e) {
			System.err.println("error, while loading sound files");
			e.printStackTrace();
		}

	}

	/**
	 * Exit Methode der Sound Klasse. In dieser werden die Streams geschlossen
	 * 
	 * @return void
	 */
	public static void exit() {
		audioSolid.release();
		audioIntro.release();
		audioGlurak.release();
		audioTextBox.release();
		audioMenu.release();
		audioCredits.release();
		audioInGame.release();

		currentAudio = null;
	}

	/**
	 * Hintergrundmusik starten oder ändern
	 * 
	 * @param audio
	 *            (Audio) Sounddatei welche als nächstes gespielt werden soll
	 * @return void
	 */
	public static void changeSound(Audio audio) {
		if (currentAudio == null) {
			currentAudio = audio;
			currentAudio.playAsMusic(1.0f, 1.0f, true);
		} else if (currentAudio == audio)
			currentAudio.playAsMusic(1.0f, 1.0f, true);
		else {
			currentAudio.stop();
			currentAudio = audio;
			currentAudio.playAsMusic(1.0f, 1.0f, true);
		}

	}

	public static void changeMapSound(String ref) {
		System.out.println(ref);
		if (ref.equals("House"))
			changeSound(audioHouse);
		else if (ref.equals("Home"))
			changeSound(audioHome);
		else if (ref.equals("Town"))
			changeSound(audioTown);
		else if (ref.equals("University"))
			changeSound(audioUniversity);
		else if (ref.equals("townHouse1"))
			changeSound(audioTownHouse1);
	}
}
