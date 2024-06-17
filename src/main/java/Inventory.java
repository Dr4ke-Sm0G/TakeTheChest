package main.java;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


public class Inventory {
	private HBox hbox;
	private ImageView slot1 = new ImageView(new Image("inventory/inventory.png"));
	private ImageView slot2 = new ImageView(new Image("inventory/inventory.png"));
	private ImageView slot3 = new ImageView(new Image("inventory/inventory.png"));
	private ImageView slot4 = new ImageView(new Image("inventory/inventory.png"));
	private ImageView slot5 = new ImageView(new Image("inventory/inventory.png"));
    
	/** 
	 * Initialise une nouvelle instance de la classe Inventory.
	 */
    public Inventory() {
        hbox = new HBox();
        slot1.setFitHeight(50);
        slot1.setFitWidth(50);
        slot2.setFitHeight(50);
        slot2.setFitWidth(50);
        slot3.setFitHeight(50);
        slot3.setFitWidth(50);
        slot4.setFitHeight(50);
        slot4.setFitWidth(50);
        slot5.setFitHeight(50);
        slot5.setFitWidth(50);
        hbox = new HBox(slot1, slot2, slot3,slot4, slot5);
    }

    /**
     * Retourne la boite horizontale (HBox) de l'inventaire.
     *
     * @return la boite horizontale (HBox) de l'inventaire
     */
    public HBox getHbox() {
        return hbox;
    }

    /**
     * Definit la boite horizontale (HBox) de l'inventaire.
     *
     * @param hbox la boite horizontale (HBox) de l'inventaire a definir
     */
    public void setHbox(HBox hbox) {
        this.hbox = hbox;
    }

    /**
     * Retourne l'ImageView du slot 1 de l'inventaire.
     *
     * @return l'ImageView du slot 1 de l'inventaire
     */
    public ImageView getSlot1() {
        return slot1;
    }

    /**
     * Definit l'ImageView du slot 1 de l'inventaire.
     *
     * @param slot1 l'ImageView du slot 1 de l'inventaire a definir
     */
    public void setSlot1(ImageView slot1) {
        this.slot1 = slot1;
    }

    /**
     * Retourne l'ImageView du slot 2 de l'inventaire.
     *
     * @return l'ImageView du slot 2 de l'inventaire
     */
    public ImageView getSlot2() {
        return slot2;
    }

    /**
     * Definit l'ImageView du slot 2 de l'inventaire.
     *
     * @param slot2 l'ImageView du slot 2 de l'inventaire a definir
     */
    public void setSlot2(ImageView slot2) {
        this.slot2 = slot2;
    }

    /**
     * Retourne l'ImageView du slot 3 de l'inventaire.
     *
     * @return l'ImageView du slot 3 de l'inventaire
     */
    public ImageView getSlot3() {
        return slot3;
    }

    /**
     * Definit l'ImageView du slot 3 de l'inventaire.
     *
     * @param slot3 l'ImageView du slot 3 de l'inventaire a definir
     */
    public void setSlot3(ImageView slot3) {
        this.slot3 = slot3;
    }
    
    /**
     * Retourne l'ImageView du slot 4 de l'inventaire.
     *
     * @return l'ImageView du slot 4 de l'inventaire
     */
    public ImageView getSlot4() {
        return slot4;
    }

    /**
     * Definit l'ImageView du slot 4 de l'inventaire.
     *
     * @param slot4 l'ImageView du slot 4 de l'inventaire a definir
     */
    public void setSlot4(ImageView slot4) {
        this.slot4 = slot4;
    }
    /**
     * Retourne l'ImageView du slot 5 de l'inventaire.
     *
     * @return l'ImageView du slot 5 de l'inventaire
     */
    public ImageView getSlot5() {
        return slot5;
    }

    /**
     * Definit l'ImageView du slot 5 de l'inventaire.
     *
     * @param slot4 l'ImageView du slot 5 de l'inventaire a definir
     */
    public void setSlot5(ImageView slot5) {
        this.slot5 = slot5;
    }
    
}
