package main.java.entity.projectile;

import main.java.Environment;
import main.java.entity.LivingEntity;
import main.java.entity.MonsterUpgraded;
import main.java.item.Item;

/**
 * Cette classe représente un projectile générique.
 */
public class Projectile extends Item {

    protected LivingEntity owner;

    /**
     * Constructeur de la classe Projectile.
     *
     * @param x      La coordonnée X de départ du projectile.
     * @param y      La coordonnée Y de départ du projectile.
     * @param vx     La vitesse horizontale du projectile.
     * @param vy     La vitesse verticale du projectile.
     * @param w      La largeur du projectile.
     * @param h      La hauteur du projectile.
     * @param owner  L'entité parente du projectile.
     */
    public Projectile(double x, double y, double vx, double vy, double w, double h, LivingEntity owner) {
        super(x, y, vx, vy, w, h);
        this.owner = owner;
    }

    /**
     * Met à jour l'état du projectile à chaque tick.
     *
     * @param environment L'environnement dans lequel évolue le projectile.
     */
    @Override
    public void tick(Environment environment) {
        super.tick(environment);
        for (int i = 0; i < environment.getEntityCount(); i++) {
            if (environment.getEntity(i) instanceof LivingEntity livingEntity) {            	
                if (!owner.getClass().equals(livingEntity.getClass())) {
                    if (x < livingEntity.getX() + livingEntity.getWidth() && x + w > livingEntity.getX() && y < livingEntity.getY() + livingEntity.getHeight() && y + h > livingEntity.getY()) {
                        if (livingEntity instanceof MonsterUpgraded) {
                        	if (!((MonsterUpgraded)livingEntity).isDodge()) {
                        		if (onHit(livingEntity)) {
                                    destroy();
                                    break;
                                }
                        	}
                        }else {
                        	if (onHit(livingEntity)) {
                                destroy();
                                break;
                            }
                        }
                    	
                    }
                }
            }
        }
        if (vx == 0) {
            destroy();
        }
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
