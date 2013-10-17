package Entity;

import TileMap.TileMap;

public class Enemy extends MapObject {

	// Caractéristiques enemmi
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;

	protected boolean flinching;
	protected long flinchTimer;

	// Constructeur
	public Enemy(TileMap tm) {
		super(tm);
	}

	// Verification mort
	public boolean isDead() {
		return dead;
	}

	// Getter dommage
	public int getDamage() {
		return damage;
	}

	// Degat sur ennemi
	public void hit(int damage) {
		if (dead || flinching)
			return;
		health -= damage;
		if (health < 0)
			health = 0;
		if (health == 0)
			dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}

	public void update() {

	}

}
