package main.java.entity;

import main.java.Environment;
import main.java.item.Item;
import java.util.Map;
import main.java.entity.Player;

public class PNG1 extends NonPlayableCharacter {
    private boolean visited;

    public PNG1(double x, double y) {
        super(x, y);
        this.visited = false;
    }

    /**
     * Met à jour l'état du PNJ à chaque itération du jeu.
     *
     * @param e L'environnement du jeu.
     */
    public void tick(Environment e) {
        super.tick(e);
        boolean playerInRange = false;

        for (int i = 0; i < e.getEntityCount(); i++) {
            if (e.getEntity(i) instanceof Player) {
                Player player = (Player) e.getEntity(i);
                if (x - 5 <= player.getX() && player.getX() <= x + 1 && player.getY() >= y && player.getY() <= y + 1) {
                    playerInRange = true;
                    if (!visited) {
                        player.displayInventory(this, e);
                        visited = true;
                    }
                }
            }
        }

        if (!playerInRange) {
            visited = false;
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
