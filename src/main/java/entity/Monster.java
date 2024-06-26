package main.java.entity;

import javafx.scene.image.Image;
import main.java.Environment;
import main.java.entity.projectile.Arrow;

import java.util.Random;

/**
 * Classe repr�sentant un monstre.
 * Un monstre est une entit� vivante qui peut attaquer en lan�ant des fl�ches.
 */
public class Monster extends LivingEntity {

	private Image image;
	

	/**
	 * Constructeur de la classe Monster.
	 *
	 * @param x La coordonn�e en abscisse du monstre.
	 * @param y La coordonn�e en ordonn�e du monstre.
	 */
	public Monster(double x, double y,int life, String pathImage) {
		super(x, y, life, 1);
		this.image = new Image(pathImage);
	}
    /**
     * Met � jour l'�tat du monstre � chaque it�ration du jeu.
     *
     * @param environment L'environnement du jeu.
     */
    @Override
    public void tick(Environment environment) {
        super.tick(environment);
        
        // V�rifiez si l'inventaire contient une fl�che

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
   public Monster getMonster() {
	   return this;
	   
   }
   
    /**
     * Renvoie l'image associ�e au monstre.
     *
     * @return L'image du monstre.
     */
    @Override
    public Image getImage() {
        return image;
    }
    /**
     * Méthode invoquée lorsqu'une entité est touchée par le projectile.
     *
     * @param livingEntity L'entité touchée par le projectile.
     * @return Vrai si l'entité a été touchée par le projectile, faux sinon.
     */
    protected boolean onHit(LivingEntity livingEntity) {
        return true;
    }
    
}
