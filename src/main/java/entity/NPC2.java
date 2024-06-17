package main.java.entity;

import main.java.Environment;

public class NPC2 extends NonPlayableCharacter {

    private boolean visited;

    public NPC2(double x, double y) {
        super(x, y);
        this.visited = false;
    }

    /**
     * Met à jour l'état du PNJ à chaque itération du jeu.
     *
     * @param e L'environnement du jeu.
     */
    @Override
    public void tick(Environment e) {
        super.tick(e);
        for (int i = 0; i < e.getEntityCount(); i++) {
            if (e.getEntity(i) instanceof Player) {
                Player player = (Player) e.getEntity(i);
                if (x - 5 <= player.getX() && player.getX() <= x + 1 && player.getY() >= y && player.getY() <= y + 1) {
                    this.visited = true;
                }
            }
        }
    }

    /**
     * Vérifie si le NPC a été visité par le joueur.
     *
     * @return true si le NPC a été visité, sinon false.
     */
    public boolean isVisited() {
        return visited;
    }
}
