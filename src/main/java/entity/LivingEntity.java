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
 * Classe repr�sentant une entit� vivante.
 * Une entit� vivante est une entit� physique avec une quantit� de vie.
 */
public class LivingEntity extends PhysicalEntity {

    double life;
    double maxLife;
 //   protected List<Item> inventory;
    protected Map<Item, Integer> inventory;
    
    /**
     * Indique si le joueur poss�de la cl�.
     */
    protected boolean hasKey;
    
    /**
     * Indique si l'entit� physique poss�de la potion sant�.
     */
    protected boolean hasHealPotion;
    
    /**
     * Indique si l'entit� physique poss�de Arrow.
     */
    protected boolean hasArrow;  
    
    /**
     * La valeur du bouclier du joueur. C'est-�-dire, le nombre de fl�ches qu'il peut encaisser sans prendre de d�g�ts.
     */
    private int shieldValue;

    /**
     * Constructeur de la classe LivingEntity.
     *
     * @param x     La coordonn�e en abscisse de l'entit� vivante.
     * @param y     La coordonn�e en ordonn�e de l'entit� vivante.
     * @param life  La quantit� de vie de l'entit� vivante.
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
     * Met � jour l'�tat de l'entit� vivante � chaque it�ration du jeu.
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
                break;  // Sortir de la boucle apr�s avoir trouv� et mis � jour l'item
            }
        }
    }

    /**
     * Renvoie la quantit� maximale de vie de l'entit� vivante.
     *
     * @return La quantit� maximale de vie.
     */
    public double getMaxLife() {
        return maxLife;
    }

    /**
     * D�finit la quantit� maximale de vie de l'entit� vivante.
     *
     * @param maxLife La quantit� maximale de vie � d�finir.
     */
    public void setMaxLife(double maxLife) {
        this.maxLife = maxLife;
    }

    /**
     * Renvoie la quantit� de vie actuelle de l'entit� vivante.
     *
     * @return La quantit� de vie actuelle.
     */
    public double getLife() {
        return life;
    }

    /**
     * D�finit la quantit� de vie de l'entit� vivante.
     *
     * @param life La quantit� de vie � d�finir.
     */
    public void setLife(double life) {
        this.life = life;
    }

    /**
     * Inflige des d�g�ts � l'entit� vivante en r�duisant sa quantit� de vie.
     *
     * @param d Les d�g�ts � infliger.
     */
    public void damage(double d) {
        this.life -= d;
    }

    /**
     * Soigne l'entit� vivante en augmentant sa quantit� de vie.
     *
     * @param d La quantit� de vie � ajouter.
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
     * D�finit la valeur du bouclier du joueur.
     *
     * @param shieldValue La valeur du bouclier � d�finir.
     */
    public void setShieldValue(int shieldValue) {
        this.shieldValue = shieldValue;
    }
    
    public int nbItems() {
        return this.inventory.size();
    }

    /**
     * V�rifie si le joueur poss�de un item sp�cifique.
     *
     * @param item L'item � v�rifier.
     * @return `true` si le joueur poss�de l'item, sinon `false`.
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
     * D�finit le drapeau de possession d'un item sp�cifique.
     *
     * @param item L'item � v�rifier.
     * @param hasItem `true` si l'item est pr�sent, sinon `false`.
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
