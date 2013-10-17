package Entity;

import java.awt.image.BufferedImage;

public class Animation {

	// Tableau de frame
	private BufferedImage[] frames;
	private int currentFrame;

	// Compteur temps
	private long startTime;
	private long delay;

	private boolean playedOnce;

	// Constructeur
	public Animation() {
		playedOnce = false;
	}

	// Setter sur les frames pour animer
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}

	// Setter delay animation
	public void setDelay(long d) {
		delay = d;
	}

	// Setter sur une frame en particulier
	public void setFrame(int i) {
		currentFrame = i;
	}

	// Mise à jour Animation
	public void update() {

		if (delay == -1)
			return;

		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if (elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}
		if (currentFrame == frames.length) {
			currentFrame = 0;
			playedOnce = true;
		}

	}

	// Getter sur l'index de la frame en cours
	public int getFrame() {
		return currentFrame;
	}

	// Getter sur la frame en cours
	public BufferedImage getImage() {
		return frames[currentFrame];
	}

	// Verification animation deja effectué
	public boolean hasPlayedOnce() {
		return playedOnce;
	}

}