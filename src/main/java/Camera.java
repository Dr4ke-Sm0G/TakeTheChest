package main.java;

/**
 * La classe Camera represente une camera utilisee pour suivre un objet dans le jeu.
 * Elle permet de deplacer la vue du jeu en fonction de la position de l'objet cible.
 */
public class Camera {
	
    private double x, y;
    private double target_x, target_y;



    /**
     * Constructeur de la classe Camera.
     *
     * @param x La position horizontale initiale de la camera.
     * @param y La position verticale initiale de la camera.
     */
    public Camera(double x, double y) {
        this.x = x;
        this.y = y;
        this.target_x = x;
        this.target_y = y;
    }

    /**
     * Retourne la position horizontale de la camera.
     *
     * @return La position horizontale de la camera.
     */
    public double getX() {
        return x;
    }

    /**
     * Modifie la position horizontale de la camera.
     *
     * @param x La nouvelle position horizontale de la camera.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Retourne la position verticale de la camera.
     *
     * @return La position verticale de la camera.
     */
    public double getY() {
        return y;
    }

    /**
     * Modifie la position verticale de la camera.
     *
     * @param y La nouvelle position verticale de la camera.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Retourne la position horizontale cible de la camera.
     *
     * @return La position horizontale cible de la camera.
     */
    public double getTarget_x() {
        return target_x;
    }

    /**
     * Modifie la position horizontale cible de la camera.
     *
     * @param target_x La nouvelle position horizontale cible de la camera.
     */
    public void setTarget_x(double target_x) {
        this.target_x = target_x;
    }

    /**
     * Retourne la position verticale cible de la camera.
     *
     * @return La position verticale cible de la camera.
     */
    public double getTarget_y() {
        return target_y;
    }

    /**
     * Modifie la position verticale cible de la camera.
     *
     * @param target_y La nouvelle position verticale cible de la camera.
     */
    public void setTarget_y(double target_y) {
        this.target_y = target_y;
    }

    /**
     * Met à jour la position de la camera en effectuant une interpolation entre la position actuelle et la position cible.
     */
    public void tick() {
        x = 0.9 * x + 0.1 * target_x;
        y = 0.9 * y + 0.1 * target_y;
    }
}
