package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

;

public class Explosion {

	// Coordonn�es
	private int x;
	private int y;
	private int xmap;
	private int ymap;

	// Dimension
	private int width;
	private int height;

	private Animation animation;
	private BufferedImage[] sprites;

	private boolean remove;

	// Constructeur
	public Explosion(int x, int y) {

		this.x = x;
		this.y = y;

		width = 30;
		height = 30;

		// Chargement Sprites
		try {

			BufferedImage spritesheet = ImageIO.read(getClass()
					.getResourceAsStream("/Sprites/Enemies/explosion.gif"));

			sprites = new BufferedImage[6];
			for (int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width,
						height);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(70);

	}

	// Mise � jour animation
	public void update() {
		animation.update();
		if (animation.hasPlayedOnce()) {
			remove = true;
		}
	}

	// Animation deja effectu�
	public boolean shouldRemove() {
		return remove;
	}

	// Setter position
	public void setMapPosition(int x, int y) {
		xmap = x;
		ymap = y;
	}

	// Affichage
	public void draw(Graphics2D g) {
		g.drawImage(animation.getImage(), x + xmap - width / 2, y + ymap
				- height / 2, null);
	}
}
