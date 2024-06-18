package main.java.entity;

import main.java.Environment;

import java.util.Arrays;

/**
 * Classe abstraite représentant une entité physique.
 * Une entité physique possède une taille, peut se déplacer et interagir avec l'environnement du jeu.
 */
public abstract class PhysicalEntity extends Entity {

    protected double w; // Largeur de l'entité
    protected double h; // Hauteur de l'entité
    protected boolean on_ground; // Indique si l'entité est au sol
    protected short[] touched; // Tableau des blocs touchés par l'entité
    private int last_shot; // Temps depuis le dernier tir effectué par l'entité

    /**
     * Constructeur de la classe PhysicalEntity.
     *
     * @param x  La coordonnée en abscisse de l'entité.
     * @param y  La coordonnée en ordonnée de l'entité.
     * @param vx La vitesse horizontale de l'entité.
     * @param vy La vitesse verticale de l'entité.
     * @param w  La largeur de l'entité.
     * @param h  La hauteur de l'entité.
     */
    public PhysicalEntity(double x, double y, double vx, double vy, double w, double h) {
        super(x, y, vx, vy);
        this.w = w;
        this.h = h;
        this.on_ground = false;
        this.touched = new short[(int) (Math.ceil(w + 1) * Math.ceil(h + 10))];
    }

    /**
     * Renvoie le temps écoulé depuis le dernier tir effectué par l'entité.
     *
     * @return Le temps écoulé depuis le dernier tir.
     */
    public int getLast_shot() {
        return last_shot;
    }

    /**
     * Définit le temps écoulé depuis le dernier tir effectué par l'entité.
     *
     * @param last_shot Le temps écoulé depuis le dernier tir.
     */
    public void setLast_shot(int last_shot) {
        this.last_shot = last_shot;
    }

    /**
     * Met à jour l'�tat de l'entit� � chaque it�ration du jeu.
     *
     * @param environment L'environnement du jeu.
     */
    public void tick(Environment environment) {

        setLast_shot(getLast_shot() - 1);
        on_ground = false;

        // Détection de collision avec les blocs horizontaux lors du déplacement horizontal
        if (vx < 0) {
            int scan_x = (int) Math.floor(x);
            int end_x = (int) Math.floor(x + vx);
            int start_y = (int) Math.floor(y);
            int end_y = (int) Math.ceil(y + h) - 1;
            boolean hit = false;
            while (scan_x>end_x && !hit){
            	scan_x-=1;
                for (int i = start_y;i<=end_y;i++){
                    hit = hit || Environment.BLOCK_PROPERTIES.get(environment.getBlock(scan_x,i)).solid();
                }
            }
            if (hit) {
                vx = 0;
                x = scan_x + 1;
            } else {
                x += vx;
            }
        } else {
            int scan_x = (int) Math.ceil(x + w) - 1;
            int end_x = (int) Math.floor(x + w + vx);
            int start_y = (int) Math.floor(y);
            int end_y = (int) Math.ceil(y + h) - 1;
            boolean hit = false;
            while (scan_x<end_x && !hit){
                scan_x+=1;
                for (int i = start_y;i<=end_y;i++){
                    hit = hit || Environment.BLOCK_PROPERTIES.get(environment.getBlock(scan_x,i)).solid();
            
                }
            }
            if (hit) {
                vx = 0;
                x = scan_x - w;
            } else {
                x += vx;
            }
        }

        // Détection de collision avec les blocs verticaux lors du déplacement vertical
        if (vy < 0) {
            int scan_y = (int) Math.floor(y);
            int end_y = (int) Math.floor(y + vy);
            int start_x = (int) Math.floor(x);
            int end_x = (int) Math.ceil(x + w) - 1;
            boolean hit = false;
            while (scan_y>end_y && !hit){
                scan_y-=1;
                for (int i = start_x;i<=end_x;i++){
                    hit = hit || Environment.BLOCK_PROPERTIES.get(environment.getBlock(i,scan_y)).solid();
           
                }
            }
            if (hit) {
                on_ground = true;
                vy = 0;
                y = scan_y + 1;
            } else {
                y += vy;
            }
        }
        else {
            int scan_y = (int)Math.ceil(y+h)-1;
            int end_y = (int)Math.floor(y+h+vy);
            int start_x = (int)Math.floor(x);
            int end_x = (int)Math.ceil(x+w)-1;
            boolean hit = false;
            while (scan_y<end_y && !hit){
                scan_y+=1;
                for (int i = start_x;i<=end_x;i++){
                    hit = hit || Environment.BLOCK_PROPERTIES.get(environment.getBlock(i,scan_y)).solid();
                }
            }
            if(hit){
                vy=0;
                y=scan_y-h;
            }
            else{
                y+=vy;
            }
        }

        // Mise à jour des blocs touchés par l'entité
        int bottom_x = (int) Math.floor(x);
        int top_x = (int) Math.ceil(x + w) - 1;
        int bottom_y = (int) Math.floor(y);
        int top_y = (int) Math.ceil(y + h) - 1;
        Arrays.fill(touched, (short) 0);
        int i = 0;
        for (int j = bottom_x; j <= top_x; j++) {
            for (int k = bottom_y; k <= top_y; k++) {
                touched[i] = environment.getBlock(j, k);
                i++;
            }
        }
    }

    /**
     * Vérifie si l'entité est au sol.
     *
     * @return Vrai si l'entité est au sol, sinon faux.
     */
    public boolean isOnGround() {
        return on_ground;
    }

    /**
     * Renvoie la largeur de l'entité.
     *
     * @return La largeur de l'entité.
     */
    public double getWidth() {
        return w;
    }

    /**
     * Renvoie la hauteur de l'entité.
     *
     * @return La hauteur de l'entité.
     */
    public double getHeight() {
        return h;
    }

    /**
     * Renvoie la taille de l'image associée à l'entité selon l'axe des abscisses.
     *
     * @return La taille de l'image selon l'axe des abscisses.
     */
    @Override
    public double getImageSizeX() {
        return w;
    }

    /**
     * Renvoie la taille de l'image associée à l'entité selon l'axe des ordonnées.
     *
     * @return La taille de l'image selon l'axe des ordonnées.
     */
    @Override
    public double getImageSizeY() {
        return h;
    }


}