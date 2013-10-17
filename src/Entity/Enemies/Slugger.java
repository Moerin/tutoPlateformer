package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
//import java.util.ArrayList;

import javax.imageio.ImageIO;

import TileMap.TileMap;

import Entity.Animation;
import Entity.Enemy;

public class Slugger extends Enemy {
	
	private BufferedImage[] sprites;
	//private final int[] numFrames = { 2, 8, 1, 2, 4, 2, 5 };
	
	// Constructeur
	public Slugger(TileMap tm) {
		
		super(tm);
		
		moveSpeed = 0.3;
		maxSpeed = 0.3;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		width = 30;
		height = 30;
		cwidth = 20;
		cheight = 20;
		
		health =  maxHealth = 2;
		damage = 1;
		
		// Chargement Sprites
		try {

			BufferedImage spriteSheet = ImageIO.read(getClass()
					.getResourceAsStream("/Sprites/Enemies/slugger.gif"));

			sprites = new BufferedImage[3];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spriteSheet.getSubimage(i * width, 0, width, height);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(300);
		
		right = true;
		facingRight = true;
	}
	
	// Getter sur la position suivante du Slugger
	private void getNextPosition() {
		
		// Mouvement
		if (left) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		} else if (right) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
		} 
		
		// Chute
		if(falling) {
			dy += fallSpeed;
		}
		
	}
	
	// Mise a jour animation
	public void update() {
		
		// Mise a jour position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// Verification scintillement
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 400) {
				flinching = false;
			}
		}
		
		// En cas de collision avec un mur, on flip lq direction et l'animation
		if(right && dx == 0) {
			right = false;
			left = true;
			facingRight = false;
		}
		else if(left && dx == 0) {
			right = true;
			left = false;
			facingRight = true;
		}
		
		animation.update();
	}
	
	// Affichage sur la map
	public void draw(Graphics2D g) {
		
//		if(notOnScreen()) return;
		
		setMapPosition();
		
		super.draw(g);
		
	}
}

