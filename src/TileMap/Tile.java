package TileMap;

import java.awt.image.BufferedImage;

public class Tile {

	private BufferedImage image;
	private int type;

	// Types tile
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;

	// Constructeur
	public Tile(BufferedImage image, int type) {
		this.image = image;
		this.type = type;
	}

	// Getter sur la tile
	public BufferedImage getImage() {
		return image;
	}

	// Getter sur le type de tile
	public int getType() {
		return type;
	};

}
