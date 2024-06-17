package main.java.item;

import javafx.scene.image.Image;
import main.java.Environment;

/**
 * Cette classe represente l'item "GG" permettant de finir le jeu par une réussite en le ramassant.
 */
public class GG extends Item {

	public static Image img = new Image("items/gg.png");

    /**
     * Constructeur de la classe GG.
     *
     * @param x La coordonnee X de l'item "GG".
     * @param y La coordonnee Y de l'item "GG".
     */
	public GG(double x, double y) {
		super (x, y,0,0, 1, 1);
	}

    /**
     * Met a jour l'etat de l'item "GG" a chaque tick.
     *
     * @param e L'environnement dans lequel se trouve l'item "GG".
     */
	@Override
	public void tick (Environment e) {
		super.tick(e);
		if (!status) {
				e.getPlayer().addScore(10000);
			//	e.setGameProgression(Environment.Progress.WIN);
				e.getPlayer().addToInventory(new GG(0, 0),1);

		}
	}

    /**
     * Renvoie l'image correspondante a l'item "GG".
     *
     * @return L'image de l'item "GG".
     */
	@Override
	public Image getImage() {
		return img;
	}
}
