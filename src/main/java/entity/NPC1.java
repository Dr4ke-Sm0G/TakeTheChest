package main.java.entity;

import main.java.Environment;

public class NPC1 extends NonPlayableCharacter {
    private boolean visited;

	public NPC1(double x, double y) {
		super(x, y);
        this.visited = false;

	}

	/**
     * Met √† jour l'√©tat du PNJ √† chaque it√©ration du jeu.
     *
     * @param e L'environnement du jeu.
     */
	public void tick (Environment e) {
		super.tick(e);
		for (int i = 0;i<e.getEntityCount();i++){
			if(e.getEntity(i) instanceof Player){
				Player player = (Player)e.getEntity(i);
				if(x - 5<=player.getX() && player.getX()<= x + 1 && player.getY() >= y && player.getY()<= y + 1){
                    this.visited = true;
					if (e.getGameProgression() == Environment.Progress.START) {
						e.setGameProgression(Environment.Progress.WELCOME);
						cooldown = 100;
					}
					cooldown--;
				}
			}
		}
	}

    /**
     * VÈrifie si le NPC a ÈtÈ visitÈ par le joueur.
     *
     * @return true si le NPC a ÈtÈ visitÈ, sinon false.
     */
    public boolean isVisited() {
        return visited;
    }
}
