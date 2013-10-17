package TileMap;

import java.awt.*;
import java.awt.image.*;

import java.io.*;
import javax.imageio.ImageIO;

import Main.GamePanel;

public class TileMap {

	// Position
	private double x;
	private double y;

	// Rebords
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;

	// Carac pour un scrolling sans saccade
	private double tween;

	// Carac map
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;

	// Tileset
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[][] tiles;

	// Affichage
	private int rowOffset;
	private int columnOffset;
	private int numRowsToDraw;
	private int numColsToDraw;

	// Constructeur
	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
		tween = 0.07;
	}

	// Chargement des tiles de la map
	public void loadTiles(String s) {

		try {

			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new Tile[2][numTilesAcross];

			BufferedImage subimage;
			for (int col = 0; col < numTilesAcross; col++) {
				subimage = tileset.getSubimage(col * tileSize, 0, tileSize,
						tileSize);
				tiles[0][col] = new Tile(subimage, Tile.NORMAL);
				subimage = tileset.getSubimage(col * tileSize, tileSize,
						tileSize, tileSize);
				tiles[1][col] = new Tile(subimage, Tile.BLOCKED);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Creation de la map
	public void loadMap(String s) {

		try {

			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;

			xmin = GamePanel.WIDTH - width;
			xmax = 0;
			ymin = GamePanel.HEIGHT - height;
			ymax = 0;

			xmin = GamePanel.WIDTH - width;
			xmax = 0;
			ymin = GamePanel.HEIGHT - height;
			ymax = 0;

			String delims = "\\s+";
			for (int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for (int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Getter taille de la tile
	public int getTileSize() {
		return tileSize;
	}

	// Getter coordonnées
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}

	// Getter Dimension
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

	// Getter sur le type de tiles (blocked ou decor)
	public int getType(int row, int col) {
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}

	// Setter position de la tile
	public void setPosition(double x, double y) {

		System.out.println(this.x);
		System.out.println((x - this.x) * tween);

		this.x += (x - this.x) * tween;
		this.y += (y - this.y) * tween;

		System.out.println(this.x + "\n==========");

		fixBounds();

		columnOffset = (int) -this.x / tileSize;
		rowOffset = (int) -this.y / tileSize;
	}

	// Setter sur tween
	public void setTween(double d) {
		this.tween = d;
	}

	private void fixBounds() {
		if (x < xmin)
			x = xmin;
		if (y < ymin)
			y = ymin;
		if (x > xmax)
			x = xmax;
		if (y > ymax)
			y = ymax;
	}

	// Affichage
	public void draw(Graphics2D g) {

		for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {

			if (row >= numRows)
				break;

			for (int col = columnOffset; col < columnOffset + numColsToDraw; col++) {
				if (col >= numCols)
					break;

				if (map[row][col] == 0)
					continue;

				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;

				g.drawImage(tiles[r][c].getImage(), (int) x + col * tileSize,
						(int) y + row * tileSize, null);
			}
		}

	}
}
