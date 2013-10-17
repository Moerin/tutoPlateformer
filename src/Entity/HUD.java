package Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class HUD {

	private Player player;

	private BufferedImage image;
	private Font font;

	// Constructeur
	public HUD(Player p) {
		player = p;
		try {
			image = ImageIO
					.read(getClass().getResourceAsStream("/HUD/hud.gif"));

			font = new Font("Arial", Font.PLAIN, 14);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Dessin du HUD avec carac
	public void draw(Graphics2D g) {

		g.drawImage(image, 0, 10, null); // Coordonnée du HUD
		g.setFont(font);
		g.setColor(Color.WHITE); // Couleur police
		g.drawString(player.getHealth() + "/" + player.getMaxHealth(), 30, 25); // Affichage
																				// vie
																				// du
																				// joueur
		g.drawString(player.getFire() / 100 + "/" + player.getMaxFire() / 100,
				30, 45); // Affichage nbre fireballs
	}

}