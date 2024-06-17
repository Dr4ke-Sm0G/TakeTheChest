package main.java.entity;

import java.util.Random;

import javafx.scene.image.Image;
import main.java.Environment;
import main.java.entity.projectile.Arrow;

public class MonsterUpgraded extends LivingEntity {
	
	private boolean dodge;
	private int dodge_cooldown;
	
	/**
	 * L'image du monstre amélioré.
	 */
	private static final Image image = new Image("living_entities/monster_upgraded.png");

    /**
     * Initialise un nouvel objet MonsterUpgraded avec les coordonnées spécifiées.
     *
     * @param x La position horizontale du monstre.
     * @param y La position verticale du monstre.
	 */
	public MonsterUpgraded(double x, double y) {
		super(x, y, 2, 2);
		dodge = false;
		dodge_cooldown = 0;
	}

    /**
     * Met à jour l'état du monstre à chaque cycle de jeu.
     *
     * @param environment L'environnement de jeu.
     */
    @Override
    public void tick(Environment environment){
        super.tick(environment);

        double dodge = new Random().nextInt(2);
        
     // Vérifiez si l'inventaire contient une flèche

        if (hasItem(Arrow.class)) {
        
	        if(getLast_shot()<=0){
	        	setLast_shot(60);
	            environment.addEntity(new Arrow(x-w/2,y+1.2*h/3,-0.5,this));
	            environment.addEntity(new Arrow(x-w/2,y+h/3,-0.5,this));
	        }
        }
        if (dodge == 0 && dodge_cooldown == 0) {
        	this.dodge_cooldown = 30;
        	this.dodge = true;
        }
        else if (dodge == 1 && dodge_cooldown == 0){
        	this.dodge_cooldown = 30;
        	this.dodge = false;
        }else {
        	this.dodge_cooldown--;
        }
    }

    /**
     * Retourne l'image du monstre amélioré.
     *
     * @return L'image du monstre amélioré.
     */
    @Override
    public Image getImage(){
        return image;
    }

	public boolean isDodge() {
		return dodge;
	}

	public void setDodge(boolean dodge) {
		this.dodge = dodge;
	}
}
