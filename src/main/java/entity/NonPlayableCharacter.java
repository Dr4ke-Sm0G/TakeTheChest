package main.java.entity;

import javafx.scene.image.Image;

/**
 * Classe représentant un personnage non jouable (PNJ).
 * Un PNJ est une entité vivante qui interagit avec le joueur et peut déclencher des événements dans le jeu.
 */
public abstract class NonPlayableCharacter extends LivingEntity {
	
	protected int cooldown;
	protected static final Image image = new Image("living_entities/pnj.png");
    
    

	/**
	 * Constructeur de la classe NonPlayableCharacter.
	 *
	 * @param x La coordonnée en abscisse du PNJ.
	 * @param y La coordonnée en ordonnée du PNJ.
	 */	
	public NonPlayableCharacter(double x, double y) {
		super(x,y, 100000, 1);
		cooldown = 0;
	}
	

    /**
     * Renvoie le temps de recharge du PNJ.
     *
     * @return Le temps de recharge du PNJ.
     */
    public int getCooldown() {
        return cooldown;
    }

    /**
     * Définit le temps de recharge du PNJ.
     *
     * @param cooldown Le temps de recharge du PNJ.
     */
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
    
    /**
     * Renvoie l'image associ�e � l'entit� vivante.
     *
     * @return L'image de l'entit� vivante.
     */
    @Override
    public Image getImage() {
        return image;
    }
    

}
