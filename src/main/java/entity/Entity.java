package main.java.entity;

import javafx.scene.image.Image;
import main.java.Environment;

/**
 * Classe abstraite qui définit une entité.
 * 
 *
 * 
 * Une entité représente tout ce qui peut être rendu et qui n'est pas sur la carte.
 * 
 */
public abstract class Entity {
	
	/** Coordonnée en abscisse */
    protected double x;
    /** Coordonnée en ordonnée */
    protected double y;
    /** Coordonnée de la vitesse en abscisse */
    protected double vx;
    /** Coordonnée de la vitesse en ordonnée */
    protected double vy;
    /** Variable booléenne indiquant si l'entité doit être rendue, initialisée à false */
    private boolean destroyed = false;

    /**
     * Constructeur de la classe.
     *
     * @param x  La coordonnée en abscisse
     * @param y  La coordonnée en ordonnée
     * @param vx La coordonnée de la vitesse en abscisse
     * @param vy La coordonnée de la vitesse en ordonnée
     */
    public Entity(double x, double y, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    /**
     * Retourne la coordonnée en abscisse de l'entité.
     *
     * @return La coordonnée en abscisse
     */
    public double getX() {
        return x;
    }

    /**
     * Définit la coordonnée en abscisse de l'entité.
     *
     * @param x La coordonnée en abscisse à définir
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Retourne la coordonnée en ordonnée de l'entité.
     *
     * @return La coordonnée en ordonnée
     */
    public double getY() {
        return y;
    }

    /**
     * Définit la coordonnée en ordonnée de l'entité.
     *
     * @param y La coordonnée en ordonnée à définir
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Retourne la coordonnée de la vitesse en abscisse de l'entité.
     *
     * @return La coordonnée de la vitesse en abscisse
     */
    public double getVx() {
        return vx;
    }

    /**
     * Définit la coordonnée de la vitesse en abscisse de l'entité.
     *
     * @param vx La coordonnée de la vitesse en abscisse à définir
     */
    public void setVx(double vx) {
        this.vx = vx;
    }

    /**
     * Retourne la coordonnée de la vitesse en ordonnée de l'entité.
     *
     * @return La coordonnée de la vitesse en ordonnée
     */
    public double getVy() {
        return vy;
    }

    /**
     * Définit la coordonnée de la vitesse en ordonnée de l'entité.
     *
     * @param vy La coordonnée de la vitesse en ordonnée à définir
     */
    public void setVy(double vy) {
        this.vy = vy;
    }
    
    /**
     * Met à jour l'entité à chaque tick du jeu.
     * Vérifie si l'entité est en dehors des limites de l'environnement et la détruit si c'est le cas.
     *
     * @param environment L'environnement du jeu
     */
    public void tick(Environment environment){
        if (x < -100 || x > environment.getWidth() + 100 || y < 0 || y > environment.getHeight() + 100){
            this.destroy(environment);
        }
    }

    /**
     * Vérifie si l'entité est détruite.
     *
     * @return true si l'entité est détruite, sinon false
     */
    public boolean destroyed() {
        return destroyed;
    }

    /**
     * Détruit l'entité.
     */
    public void destroy(Environment environment) {
        this.destroyed = true;
    }
    
    /**
     * Détruit l'entité.
     */
    public void destroy() {
        this.destroyed = true;
    }

    /**
     * Retourne l'image de l'entité.
     *
     * @return L'image de l'entité
     */
    public Image getImage(){
        return null;
    }

    /**
     * Retourne le décalage de l'image en abscisse.
     *
     * @return Le décalage de l'image en abscisse
     */
    public double getImageOffsetX(){
        return 0;
    }

    /**
     * Retourne le décalage de l'image en ordonnée.
     *
     * @return Le décalage de l'image en ordonnée
     */
    public double getImageOffsetY(){
        return 0;
    }

    /**
     * Retourne la taille de l'image en abscisse.
     *
     * @return La taille de l'image en abscisse
     */
    public double getImageSizeX(){
        return 1;
    }

    /**
     * Retourne la taille de l'image en ordonnée.
     *
     * @return La taille de l'image en ordonnée
     */
    public double getImageSizeY(){
        return 1;
    }
}
