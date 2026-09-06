package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Sound implements AutoCloseable {

	Clip clip;
	URL soundURL[] = new URL[7];
	private final Clip[] clips = new Clip[7];
	private final boolean[] attempted = new boolean[7];
	private final boolean muted = Boolean.getBoolean("spaceinvaders.mute");
	FloatControl fc;
	int volumeScale = 3;
	float volume;
	
	public Sound() {
		// BACKGROUND
		soundURL[0] = getClass().getResource("/sound/backgroundMusic.wav");
		
		// BULLET
		soundURL[1] = getClass().getResource("/sound/normalbulletsound.wav");
		
		// MONSTER HIT
		soundURL[2] = getClass().getResource("/sound/enemyShoot.wav");
	
		// GOT DAMAGED
		soundURL[3] = getClass().getResource("/sound/burning.wav");
		
		// GOT 10 KILLS
		soundURL[4] = getClass().getResource("/sound/coin.wav");
		
		// SELECT
		soundURL[5] = getClass().getResource("/sound/select.wav");
	
		// GAMEOVER
		soundURL[6] = getClass().getResource("/sound/gameover.wav");
	}
	
	public void preload(int... indexes) {
		for(int i : indexes) {
			loadClip(i);
		}
	}

	private Clip loadClip(int i) {
		if(muted) {
			return null;
		}
		if(!attempted[i]) {
			attempted[i] = true;
			Clip loaded = null;
			try {
				if(soundURL[i] == null) {
					throw new IllegalArgumentException("Missing sound resource " + i);
				}
				try(AudioInputStream stream = AudioSystem.getAudioInputStream(soundURL[i])) {
					loaded = AudioSystem.getClip();
					loaded.open(stream);
				}
				clips[i] = loaded;
			} catch(IOException | UnsupportedAudioFileException | LineUnavailableException | IllegalArgumentException e) {
				if(loaded != null) {
					loaded.close();
				}
				System.err.println("Sound unavailable (" + i + "): " + e.getMessage());
			}
		}
		return clips[i];
	}

	public void setFile(int i) {
		clip = loadClip(i);
		fc = clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)
				? (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN) : null;
		checkVolume();
	}

	public void play() {
		if(clip != null) {
			// Reuse one clip per sound; repeated effects restart without leaking lines.
			clip.stop();
			clip.setFramePosition(0);
			clip.start();
		}
	}

	public void loop() {
		if(clip != null) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	public void stop() {
		if(clip != null) {
			clip.stop();
		}
	}

	@Override
	public void close() {
		for(int i = 0; i < clips.length; i++) {
			if(clips[i] != null) {
				clips[i].close();
				clips[i] = null;
			}
			attempted[i] = false;
		}
		clip = null;
		fc = null;
	}

	public void checkVolume() {
		switch(volumeScale) {
		case 0:
			volume = -80f;
			break;
		case 1:
			volume = -20f;
			break;
		case 2:
			volume = -12f;
			break;
		case 3:
			volume = -5f;
			break;
		case 4:
			volume = 1f;
			break;
		case 5:
			volume = 6f;
			break;
		}
		if(fc != null) {
			fc.setValue(Math.max(fc.getMinimum(), Math.min(fc.getMaximum(), volume)));
		}
	}
}
