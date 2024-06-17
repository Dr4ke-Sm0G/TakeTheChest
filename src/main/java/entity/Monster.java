package main.java.entity;

import javafx.scene.image.Image;
import main.java.Environment;
import main.java.entity.projectile.Arrow;
import java.util.Random;

/**
 * Classe représentant un monstre.
 * Un monstre est une entité vivante qui peut attaquer en lançant des flèches.
 */
public class Monster extends LivingEntity {

	private Image image;
	

	/**
	 * Constructeur de la classe Monster.
	 *
	 * @param x La coordonnée en abscisse du monstre.
	 * @param y La coordonnée en ordonnée du monstre.
	 */
	public Monster(double x, double y,int life, String pathImage) {
		super(x, y, life, 1);
		this.image = new Image(pathImage);
	}
    /**
     * Met à jour l'état du monstre à chaque itération du jeu.
     *
     * @param environment L'environnement du jeu.
     */
    @Override
    public void tick(Environment environment) {
        super.tick(environment);
        
        // Vérifiez si l'inventaire contient une flèche

        if (hasItem(Arrow.class)) {
	        if (getLast_shot() <= 0) {
	            setLast_shot(60);
	            double dir = new Random().nextInt(2);
	            if (dir == 0) {
	                environment.addEntity(new Arrow(x-w/2,y+2*h/3,-0.5,this));
	            }
	            if (dir == 1) {
	                environment.addEntity(new Arrow(x + 3 * w / 2, y + 2 * h / 3, 0.5, this));
	            }
	        }
        }
    }
    public double getLife() {
    	
    	return life;
    }
    public void setLife(int life) {
        this.life = life;
    }
    /**
     * Renvoie l'image associée au monstre.
     *
     * @return L'image du monstre.
     */
    @Override
    public Image getImage() {
        return image;
    }
}
