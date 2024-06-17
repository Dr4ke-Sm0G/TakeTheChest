
package main.java.entity.projectile;

import javafx.scene.image.Image;
import main.java.Environment;
import main.java.entity.LivingEntity;
import main.java.entity.Player;
import main.java.entity.NonPlayableCharacter;

/**
 * Cette classe représente une flèche.
 */
public class Arrow extends Projectile {

	private static final Image imageLeft = new Image("arrows/arrow_left.png");
	private static final Image imageRight = new Image("arrows/arrow_right.png");


	/**
	 * Constructeur de la classe Arrow.
	 *
	 * @param x      La coordonnée X de départ de la flèche.
	 * @param y      La coordonnée Y de départ de la flèche.
	 * @param vx     La vitesse horizontale de la flèche.
	 * @param owner  L'entité parente de la flèche.
	 */
	public Arrow(double x, double y, double vx, LivingEntity owner) {
		super(x-0.5, y,vx,0,1,0.2,owner);
	}

	/**
	 * Renvoie l'entité qui a tiré la flèche.
	 *
	 * @return L'entité propriétaire de la flèche.
	 */
	public LivingEntity shotFrom() {
		return owner;
	}


	/**
	 * Met à jour l'état de la flèche à chaque tick.
	 *
	 * @param environment L'environnement dans lequel évolue la flèche.
	 */
	public void tick(Environment environment) {
		super.tick(environment);
	}

	/**
	 * Méthode invoquée lorsqu'une entité est touchée par la flèche.
	 *
	 * @param livingEntity L'entité touchée par la flèche.
	 * @return Vrai si l'entité a été touchée et endommagée, faux sinon.
	 */
	@Override
	protected boolean onHit(LivingEntity livingEntity) {
		if(!(livingEntity instanceof NonPlayableCharacter)){
			if(livingEntity instanceof Player && ((Player)livingEntity).getShieldValue()>0) ((Player)livingEntity).damageShield();
			else livingEntity.damage(1);
			destroy();
			return true;
		}
		return false;
	}

	/**
	 * Renvoie l'image correspondante à la flèche en fonction de sa direction.
	 *
	 * @return L'image de la flèche.
	 */
	public Image getImage() {
	    return vx > 0 ? imageRight : imageLeft;
	}
}