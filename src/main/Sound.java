package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

	Clip clip;
	URL soundURL[] = new URL[30];
	FloatControl fc;
	int volumeScale = 3;
	float volume;
	
	public Sound() {
		// BACKGROUND
		soundURL[0] = getClass().getResource("/sound/backgroundMusic.wav");
		
		// BULLET
		soundURL[1] = getClass().getResource("/sound/normalBulletSound.wav");
		
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
	
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
			// FLOAT CONTROL ACCEPT -80F TO 6
			fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		
			checkVolume();
		}catch(Exception e) {}
	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
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
		fc.setValue(volume);
	}
}
