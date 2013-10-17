package GameState;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Audio.AudioPlayer;
import Entity.Enemy;
import Entity.Explosion;
import Entity.HUD;
import Entity.Player;
import Entity.Enemies.Slugger;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class Level1State extends GameState {

	private TileMap tileMap;
	private Background bg;

	private Player player;

	// Tableau conteneur ennemi
	private ArrayList<Enemy> enemies;
	// Tableau conteneur explosion
	private ArrayList<Explosion> explosions;

	private HUD hud;

	// Music
	private AudioPlayer bgMusic;

	// Constructeur
	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	// Initialisation niveau
	public void init() {

		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/level1-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);

		bg = new Background("/Backgrounds/grassbg1.gif", 0.1);

		player = new Player(tileMap);
		player.setPosition(100, 100);

		populateEnemies();

		explosions = new ArrayList<Explosion>();

		hud = new HUD(player);

		bgMusic = new AudioPlayer("/Music/level1-1.mp3"); // TODO : corriger le son
		bgMusic.play();

	}
	
	// Chargement des ennemies dans le niveau
	private void populateEnemies() {

		enemies = new ArrayList<Enemy>();

		Slugger s; // Ennemi type Slugger
		Point[] points = new Point[] { new Point(200, 100),
				new Point(860, 200), new Point(1525, 200),
				new Point(1680, 200), new Point(1800, 200) };

		for (int i = 0; i < points.length; i++) {
			s = new Slugger(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
	}
	
	// Mise a jour des éléments dans le niveau
	public void update() {

		// Mise a jour Joueur
		player.update();
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(),
				GamePanel.HEIGHT / 2 - player.gety());

		// Mise en place background
		bg.setPosition(tileMap.getX(), tileMap.getY());

		// Verification attaque sur ennemie
		player.checkAttack(enemies);

		// Mise à jour ennemi
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if (e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(), e.gety()));
			}
		}

		// Mise à jour sur les explosions
		for (int i = 0; i < explosions.size(); i++) {
			Explosion ex = explosions.get(i);
			ex.update();
			if (ex.shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
	}
	
	// Affichage 
	public void draw(Graphics2D g) {

		// Background
		bg.draw(g);

		// Map
		tileMap.draw(g);

		// Player
		player.draw(g);

		// Ennemi
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}

		// Explosion
		for (int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition((int) tileMap.getX(),
					(int) tileMap.getY());
			explosions.get(i).draw(g);
		}

		// HUD
		hud.draw(g);

	}
	
	// Evenement Joueur
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_LEFT)
			player.setLeft(true);
		if (k == KeyEvent.VK_RIGHT)
			player.setRight(true);
		if (k == KeyEvent.VK_UP)
			player.setUp(true);
		if (k == KeyEvent.VK_DOWN)
			player.setDown(true);
		if (k == KeyEvent.VK_SPACE)
			player.setJumping(true);
		if (k == KeyEvent.VK_C)
			player.setGliding(true);
		if (k == KeyEvent.VK_Z)
			player.setScratching();
		if (k == KeyEvent.VK_CONTROL)
			player.setFiring();

	}

	public void keyReleased(int k) {
		if (k == KeyEvent.VK_LEFT)
			player.setLeft(false);
		if (k == KeyEvent.VK_RIGHT)
			player.setRight(false);
		if (k == KeyEvent.VK_UP)
			player.setUp(false);
		if (k == KeyEvent.VK_DOWN)
			player.setDown(false);
		if (k == KeyEvent.VK_SPACE)
			player.setJumping(false);
		if (k == KeyEvent.VK_C)
			player.setGliding(false);
	}
}
