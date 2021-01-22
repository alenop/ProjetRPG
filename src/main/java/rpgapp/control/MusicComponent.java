package rpgapp.control;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.audio.Music;

public class MusicComponent {
	
	public static String soundPlayed;
	public static String musicPlayed = "inside.mp3";
	public static Music musicPrevious;
	
	public static void setSoundPlayed(String sound) {
		soundPlayed = sound;
	}
	
	public static void setMusicPlayed(String music) {
		musicPlayed = music;
	}
	
	public static void setMusicPrevious(Music music) {
		musicPrevious = music;
	}
	
	public static void musicPlay(String music) {
		if(musicPrevious != null) {
			FXGL.getApp().getAudioPlayer().stopMusic(musicPrevious);
		}
		switch (music) {
			case "battle":
				setMusicPlayed("boss_Fight.mp3");
				break;
			case "mapMaison.json":
			case "mapPnj1.json":
			case "mapPnj2.json":
			case "mapPnj3.json":
				setMusicPlayed("inside.mp3");
				break;
			case "mapJardin.json":
				setMusicPlayed("outside.mp3");
				break;
			case "mapCave.json":
			case "cave":
				setMusicPlayed("cave.mp3");
				break;
			case "victory":
				setMusicPlayed("victory.mp3");
				break;
			case "gameover":
				setMusicPlayed("gameover.mp3");
				break;
		}
		setMusicPrevious(FXGL.getApp().getAssetLoader().loadMusic(musicPlayed));
		FXGL.getApp().getAudioPlayer().playMusic(musicPlayed);
	}
	
	public static void soundPlay(String sound) {
		switch(sound) {
			case "door":
				setSoundPlayed("door.wav");
				break;
			case "chest":
				setSoundPlayed("chest.wav");
				break;
			case "win":
				setSoundPlayed("win.mp3");
				break;
			case "attack":
				setSoundPlayed("attack.mp3");
				break;
			case "shield":
				setSoundPlayed("shield.mp3");
				break;
			case "run":
				setSoundPlayed("run.mp3");
				break;
			case "accept":
				setSoundPlayed("accepted.mp3");
				break;
			case "succes":
				setSoundPlayed("succes.mp3");
				break;
			case "open":
				setSoundPlayed("open.mp3");
				break;
			case "analyse":
				setSoundPlayed("analyse.mp3");
				break;
		}
		FXGL.getApp().getAudioPlayer().playSound(soundPlayed);
	}

}
