package main.java.item;

import javafx.scene.image.Image;
import main.java.Environment;

/**
 * Cette classe represente l'item "GG" permettant de finir le jeu par une réussite en le ramassant.
 */
public class Item2 extends Item {

	public static Image img = new Image("items/item_two.png");

    /**
     * Constructeur de la classe GG.
     *
     * @param x La coordonnee X de l'item "Item2".
     * @param y La coordonnee Y de l'item "Item2".
     */
	public Item2(double x, double y) {
		super (x, y,0,0, 1, 1);
	}

    /**
     * Met a jour l'etat de l'item "Item2" a chaque tick.
     *
     * @param e L'environnement dans lequel se trouve l'item "Item2".
     */
	@Override
	public void tick (Environment e) {
		super.tick(e);
		if (!status) {
				e.getPlayer().addToInventory(new Item2(0, 0),1);
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
