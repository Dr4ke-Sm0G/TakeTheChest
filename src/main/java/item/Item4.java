package main.java.item;

import javafx.scene.image.Image;
import main.java.Environment;

/**
 * Cette classe represente l'item "GG" permettant de finir le jeu par une réussite en le ramassant.
 */
public class Item4 extends Item {

	public static Image img = new Image("items/item_three.png");

    /**
     * Constructeur de la classe GG.
     *
     * @param x La coordonnee X de l'item "Item4".
     * @param y La coordonnee Y de l'item "Item4".
     */
	public Item4(double x, double y) {
		super (x, y,0,0, 1, 1);
	}

    /**
     * Met a jour l'etat de l'item "Item4" a chaque tick.
     *
     * @param e L'environnement dans lequel se trouve l'item "Item4".
     */
	@Override
	public void tick (Environment e) {
		super.tick(e);
		if (!status) {
				e.getPlayer().addToInventory(new Item4(0, 0),1);
		}
	}

    /**
     * Renvoie l'image correspondante a l'item "4".
     *
     * @return L'image de l'item "4".
     */
	@Override
	public Image getImage() {
		return img;
	}
}
