package main.java.item;

import javafx.scene.image.Image;
import main.java.Environment;

/**
 * Cette classe représente une potion de soin.
 */
public class HealPotion extends Item {

	public static Image img = new Image("items/heal_potion.png");
	
    /**
     * Constructeur de la classe HealPotion.
     *
     * @param y La coordonnée Y de la potion de soin.
     */
	public HealPotion(double x, double y) {
		super (x, y,0,0, 1, 1);
	}

    /**
     * Met à jour l'état de la potion de soin à chaque tick.
     *
     * @param e L'environnement dans lequel se trouve la potion de soin.
     */
	public void tick (Environment e) {
		super.tick(e);
		if (!status) {
		//	e.getPlayer().setHasHealPotion(true);
		//	e.getPlayer().setHasItem(new HealPotion(0,0), true);
			e.getPlayer().addToInventory(new HealPotion(0, 0),1);
		}
	}

    /**
     * Renvoie l'image correspondante à la potion de soin.
     *
     * @return L'image de la potion de soin.
     */
	@Override
	public Image getImage() {
		return img;
	}
}
