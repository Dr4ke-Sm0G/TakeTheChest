package main.java.entity;

import java.util.Map;
import java.util.HashMap;

import main.java.Environment;
import main.java.entity.projectile.Arrow;
import main.java.item.HealPotion;
import main.java.item.Item;
import main.java.item.Item2;
import main.java.item.Key;

/**
 * Classe représentant une entité vivante.
 * Une entité vivante est une entité physique avec une quantité de vie.
 */
public class LivingEntity extends PhysicalEntity {

    double life;
    double maxLife;
 //   protected List<Item> inventory;
    protected Map<Item, Integer> inventory;
    
    /**
     * Indique si le joueur possède la clé.
     */
    protected boolean hasKey;
    
    /**
     * Indique si l'entité physique possède la potion santé.
     */
    protected boolean hasHealPotion;
    
    /**
     * Indique si l'entité physique possède Arrow.
     */
    protected boolean hasArrow;  
    
    /**
     * La valeur du bouclier du joueur. C'est-à-dire, le nombre de flèches qu'il peut encaisser sans prendre de dégâts.
     */
    private int shieldValue;

    /**
     * Constructeur de la classe LivingEntity.
     *
     * @param x     La coordonnée en abscisse de l'entité vivante.
     * @param y     La coordonnée en ordonnée de l'entité vivante.
     * @param life  La quantité de vie de l'entité vivante.
     */
    public LivingEntity(double x, double y, double life, double size_multiplicator) {
        super(x, y, 0, 0, (double) size_multiplicator * 20 / 24, (double) size_multiplicator * 20 / 24);
        this.setShieldValue(-1);
       // this.inventory = new ArrayList<>();
        this.inventory = new HashMap<>();
        if (this instanceof Player) {
            this.addToInventory(new Arrow(0, 0, 0, this),1);


        } else if (this instanceof NPC1) {
            this.addToInventory(new Arrow(0, 0, 0, this),1);
            this.addToInventory(new HealPotion(0, 0),1);
            this.addToInventory(new HealPotion(0, 0),1);

        } 
        else if (this instanceof NPC2) {
            
        } 
        else if (this instanceof PNG1) {
            this.addToInventory(new Item2(0, 0),5);

        } 
        else {
            this.addToInventory(new Arrow(0, 0, 0, this),1);
            
        }
        
        this.life = life;
        maxLife = life;
    }
    
    /**
     * Met à jour l'état de l'entité vivante à chaque itération du jeu.
     *
     * @param environment L'environnement du jeu.
     */
    @Override
    public void tick(Environment environment) {
        if (life < 0.001) {
            if ((this instanceof Monster)) {
                environment.getPlayer().addScore(100);
                environment.getPlayer().setNumberDeaths(environment.getPlayer().getNumberDeaths() + 1);
            }
            if ((this instanceof MonsterUpgraded)) { 
                environment.getPlayer().setMonsterIsDead(true);
                environment.getPlayer().addScore(1000);
            }
            destroy(environment);
        }
        
        vy -= 0.02;
        vx *= 0.7;
        super.tick(environment);
    }
    
    /**
     * Ajout d'un item dans l'inventaire
     */

    public void addToInventory(Item item, int quantity) {
        inventory.put(item, inventory.getOrDefault(item, 0) + quantity);
    }
    /**
     * Suppression d'un item dans l'inventaire
     */

    public void removeFromInventory(Class<? extends Item> itemType, int quantity) {
        for (Map.Entry<Item, Integer> entry : inventory.entrySet()) {
            Item item = entry.getKey();
            if (itemType.isInstance(item)) {
                int currentQuantity = entry.getValue();
                int newQuantity = currentQuantity - quantity;
                if (newQuantity > 0) {
                    inventory.put(item, newQuantity);
                } else {
                    inventory.remove(item);
                }
                break;  // Sortir de la boucle après avoir trouvé et mis à jour l'item
            }
        }
    }

    /**
     * Renvoie la quantité maximale de vie de l'entité vivante.
     *
     * @return La quantité maximale de vie.
     */
    public double getMaxLife() {
        return maxLife;
    }

    /**
     * Définit la quantité maximale de vie de l'entité vivante.
     *
     * @param maxLife La quantité maximale de vie à définir.
     */
    public void setMaxLife(double maxLife) {
        this.maxLife = maxLife;
    }

    /**
     * Renvoie la quantité de vie actuelle de l'entité vivante.
     *
     * @return La quantité de vie actuelle.
     */
    public double getLife() {
        return life;
    }

    /**
     * Définit la quantité de vie de l'entité vivante.
     *
     * @param life La quantité de vie à définir.
     */
    public void setLife(double life) {
        this.life = life;
    }

    /**
     * Inflige des dégâts à l'entité vivante en réduisant sa quantité de vie.
     *
     * @param d Les dégâts à infliger.
     */
    public void damage(double d) {
        this.life -= d;
    }

    /**
     * Soigne l'entité vivante en augmentant sa quantité de vie.
     *
     * @param d La quantité de vie à ajouter.
     */
    public void heal(double d) {
        setLife(Math.min(life + d, maxLife));
    }
    
    /**
     * Renvoie la valeur du bouclier du joueur.
     */
    public int getShieldValue() {
        return shieldValue;
    }

    /**
     * Définit la valeur du bouclier du joueur.
     *
     * @param shieldValue La valeur du bouclier à définir.
     */
    public void setShieldValue(int shieldValue) {
        this.shieldValue = shieldValue;
    }
    
    public int nbItems() {
        return this.inventory.size();
    }

    /**
     * Vérifie si le joueur possède un item spécifique.
     *
     * @param item L'item à vérifier.
     * @return `true` si le joueur possède l'item, sinon `false`.
     */

    public <T extends Item> boolean hasItem(Class<T> itemType) {
        return inventory.keySet().stream().anyMatch(item -> itemType.isAssignableFrom(item.getClass()));
    }

    public <T extends Item> int getItemCount(Class<T> itemType) {
        return inventory.entrySet().stream()
                .filter(entry -> itemType.isAssignableFrom(entry.getKey().getClass()))
                .mapToInt(Map.Entry::getValue)
                .sum();
    }
    public Map<Item, Integer> getInventory() {
        return inventory;
    }
    /**
     * Définit le drapeau de possession d'un item spécifique.
     *
     * @param item L'item à vérifier.
     * @param hasItem `true` si l'item est présent, sinon `false`.
     */

    public void setHasItem(Class<? extends Item> itemType, boolean hasItem) {
        if (itemType.isAssignableFrom(HealPotion.class)) {
            this.hasHealPotion = hasItem;
        } else if (itemType.isAssignableFrom(Arrow.class)) {
            this.hasArrow = hasItem;
        } else if (itemType.isAssignableFrom(Key.class)) {
            this.hasKey = hasItem;
        }
    }

}
