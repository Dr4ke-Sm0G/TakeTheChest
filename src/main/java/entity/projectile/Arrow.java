
package main.java.entity.projectile;

import javafx.scene.image.Image;
import main.java.Environment;
import main.java.entity.LivingEntity;
import main.java.entity.Player;
import main.java.entity.MonsterUpgraded;
import main.java.entity.NonPlayableCharacter;

/**
 * Cette classe repr�sente une fl�che.
 */
public class Arrow extends Projectile {

	private static final Image imageLeft = new Image("file:src/main/resources/arrows/arrow_left.png");
	private static final Image imageRight = new Image("file:src/main/resources/arrows/arrow_right.png");


    /**
     * Constructeur de la classe Arrow.
     *
     * @param x      La coordonn�e X de d�part de la fl�che.
     * @param y      La coordonn�e Y de d�part de la fl�che.
     * @param vx     La vitesse horizontale de la fl�che.
     * @param owner  L'entit� parente de la fl�che.
     */
	public Arrow(double x, double y, double vx, LivingEntity owner) {
		super(x-0.5, y,vx,0,1,0.2,owner);
	}

    /**
     * Renvoie l'entit� qui a tir� la fl�che.
     *
     * @return L'entit� propri�taire de la fl�che.
     */
	public LivingEntity shotFrom() {
		return owner;
	}


    /**
     * Met � jour l'�tat de la fl�che � chaque tick.
     *
     * @param environment L'environnement dans lequel �volue la fl�che.
     */
	public void tick(Environment environment) {
		super.tick(environment);
	}

    /**
     * M�thode invoqu�e lorsqu'une entit� est touch�e par la fl�che.
     *
     * @param livingEntity L'entit� touch�e par la fl�che.
     * @return Vrai si l'entit� a �t� touch�e et endommag�e, faux sinon.
     */
    @Override
    protected boolean onHit(LivingEntity livingEntity) {
		if(!(livingEntity instanceof NonPlayableCharacter) && !(livingEntity instanceof MonsterUpgraded)){
			if(livingEntity instanceof Player && ((Player)livingEntity).getShieldValue()>0) ((Player)livingEntity).damageShield();
			else livingEntity.damage(1);
			destroy();
			return true;
		}
		return false;
	}

    /**
     * Renvoie l'image correspondante � la fl�che en fonction de sa direction.
     *
     * @return L'image de la fl�che.
     */
    public Image getImage() {
        return vx > 0 ? imageRight : imageLeft;
    }
}
