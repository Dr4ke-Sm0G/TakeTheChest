package main.java.item;

import javafx.scene.image.Image;
import main.java.Environment;

/**
 * Cette classe représente une clé.
 */
public class Key extends Item {

	public static Image img = new Image("items/key.png");

    /**
     * Constructeur de la classe Key.
     *
     * @param x La coordonnée X de la clé.
     * @param y La coordonnée Y de la clé.
     */
	public Key(double x, double y) {
		super (x, y,0,0, 1, 1);
	}

    /**
     * Met à jour l'état de la clé à chaque tick.
     *
     * @param e L'environnement dans lequel se trouve la clé.
     */
	@Override
	public void tick (Environment e) {
		super.tick(e);
		if (!status) {
			e.getPlayer().addToInventory(new Key(0, 0),1);
			e.setGameProgression(Environment.Progress.KEY);

		}
	}

    /**
     * Renvoie l'image correspondante à la clé.
     *
     * @return L'image de la clé.
     */
	@Override
	public Image getImage() {
		return img;
	}
}
