package main.java;

import main.java.entity.Entity;
import main.java.item.GG;
import main.java.item.HealPotion;
import main.java.item.Item1;
import main.java.item.Item2;
import main.java.item.Item4;
import main.java.item.Key;
import main.java.item.Shield;
import main.java.entity.Monster;
import main.java.entity.MonsterUpgraded;
import main.java.entity.NPC1;
import main.java.entity.NPC2;
import main.java.entity.PNG1;

import main.java.entity.Player;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * La classe Environment represente l'environnement du jeu, contenant la carte, les entites et la progression du jeu.
 */
public class Environment {
	
	public enum Progress{
        START,
        WELCOME,
        BOW,
        KEY,
        WIN,
        GG,
        VISITED,
        END
    }
	
    public static BlockProperties BLOCK_PROPERTIES = new BlockProperties();
    private final short[][] blocks;
    private final int map_width, map_height;
    private ArrayList<Entity> entities;
    private ArrayList<Entity> addedEntities;
    private NPC1 NPC;
    private NPC2 NPC2;

    private Player player;
    private Progress gameProgression;
    private static final int PLAYER_STARTING_X = 9; // Valeur idéale : 9
    private static final int PLAYER_STARTING_Y = 51; // Valeur idéale : 51
    
    /**
     * Constructeur de la classe Environment.
     * Charge la carte a partir du fichier XML et initialise les parametres du jeu.
     */
    public Environment(){
    	gameProgression = Progress.START;
    	
        entities = new ArrayList<>();
    	if(!this.checkPlayerExists()) {
    		player = new Player(PLAYER_STARTING_X, PLAYER_STARTING_Y);
    		entities.add(player);
    	}
    	
        NPC = new NPC1(15, 51);
        NPC2 = new NPC2(25, 51);



        entities.add(NPC);
        entities.add(NPC2);
  //      entities.add(new PNG1(7, 51));

    	

     

        addedEntities = new ArrayList<>();
        
        Document doc = null;
        try {
            File file = new File("resources/maps/map.tmx");
            if (!file.exists()) {
                throw new IOException("File not found: " + file.getAbsolutePath());
            }
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        } catch (ParserConfigurationException e) {
            System.err.println("ParserConfigurationException: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to configure the parser", e);
        } catch (SAXException e) {
            System.err.println("SAXException: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to parse the XML file", e);
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to read the file", e);
        }

        // Ensure doc is initialized before using it
        if (doc == null) {
            throw new RuntimeException("Document initialization failed.");
        }
        
        Node info = doc.getElementsByTagName("map").item(0);
        map_width = Short.parseShort(info.getAttributes().getNamedItem("width").getTextContent());
        map_height = Short.parseShort(info.getAttributes().getNamedItem("height").getTextContent());
        blocks = new short[map_height][map_width];
        String data = doc.getElementsByTagName("data").item(0).getTextContent().trim();
        
        int y = map_height;
        for (String row : data.split("\n")){
            y--;
            int x = 0;
            for (String block : row.split(",")){
                blocks[y][x] = Short.parseShort(block);
                x++;
            }
        }
    }
    
    /**
     * Recupere le type de bloc a la position specifiee.
     *
     * @param x La position horizontale du bloc.
     * @param y La position verticale du bloc.
     * @return Le type de bloc a la position specifiee.
     */
    public short getBlock(int x, int y) {
    	
        if (x < 0 || y < 0 || x >= map_width || y >= map_height){
            return 0;
        }
        return blocks[y][x];
    }
    
    /**
     * Recupere le type de bloc a la position specifiee en virgule flottante.
     *
     * @param x La position horizontale du bloc.
     * @param y La position verticale du bloc.
     * @return Le type de bloc a la position specifiee.
     */
    public short getBlock(double x, double y) {
        return getBlock((int) x, (int) y);
    }
    
    /**
     * Modifie le type de bloc a la position specifiee.
     *
     * @param x   La position horizontale du bloc.
     * @param y   La position verticale du bloc.
     * @param val Le nouveau type de bloc.
     */
    public void setBlock(int x, int y, short val) {
        this.blocks[y][x] = val;
    }

    /**
     * Retourne la largeur de la carte.
     *
     * @return La largeur de la carte.
     */
    public int getWidth() {
        return map_width;
    }

    /**
     * Retourne la hauteur de la carte.
     *
     * @return La hauteur de la carte.
     */
    public int getHeight() {
        return map_height;
    }
    
    /**
     * Ajoute une entite a l'environnement.
     *
     * @param entity L'entite a ajouter.
     */
    public void addEntity(Entity entity){
        addedEntities.add(entity);
    }
    
    /**
     * Genere des monstres dans l'environnement.
     * Cette methode est actuellement commentee, mais elle permettrait de generer des monstres a des positions specifiques.
     */
    public void generateMonsters(){
    	//Monster(double x, double y,int life, String pathImage) : life=2 -> il est mort après 2 tirs
    	addedEntities.add(new Monster(23,51,2,"living_entities/monster.png"));
    	addedEntities.add(new MonsterUpgraded(56,46));
    	addedEntities.add(new Monster(75,53,2,"living_entities/monster.png"));
    	addedEntities.add(new Monster(74,58,2,"living_entities/monster.png"));
    	addedEntities.add(new Monster(82,64,2,"living_entities/monster.png"));
    	addedEntities.add(new Monster(67,64,2,"living_entities/monster.png"));
    	addedEntities.add(new Monster(69,81,2,"living_entities/monster.png"));
    	addedEntities.add(new Monster(75,70,2,"living_entities/monster.png"));
    	addedEntities.add(new Monster(81,72,2,"living_entities/monster.png"));
    	addedEntities.add(new MonsterUpgraded(80,76));
    	addedEntities.add(new Monster(86,88,2,"living_entities/monster.png"));
    	addedEntities.add(new Monster(72,82,2,"living_entities/monster.png"));
    	addedEntities.add(new Monster(78,82,2,"living_entities/monster.png"));
    	addedEntities.add(new Monster(75,84,2,"living_entities/monster.png"));
    	addedEntities.add(new Monster(115,33,2,"living_entities/monster.png"));
    }
    
    /**
     * Genere des objets dans l'environnement1.
     * Cette methode ajoute des objets (cles, potions, etc.) a des positions specifiques.
     */
    public void generateItems() {
    	//addedEntities.add(new HealPotion(16, 51));
    	//addedEntities.add(new Item2(16, 51));

    	addedEntities.add(new Item4(12, 51));
    	
    	//addedEntities.add(new GG(10, 51));


    //	addedEntities.add(new Shield(13, 51));
    	addedEntities.add(new Key(53, 47));
      	addedEntities.add(new HealPotion(68, 83));
    	addedEntities.add(new HealPotion(64, 83));
    	addedEntities.add(new HealPotion(190, 53));
   // 	addedEntities.add(new GG(60,46));//138,22
    	addedEntities.add(new Shield(167, 51));
    }
    /**
     * Genere des objets dans l'environnement2.
     * Cette methode ajoute GG à une position spécifique.
     */
    public void generateItems2() {
    	addedEntities.add(new GG(60,46));//138,22
    }
    
    /**
     * Retourne le joueur actuel.
     *
     * @return Le joueur actuel.
     */
    public Player getPlayer() {
		return player;
	}

    /**
     * Definit le joueur actuel.
     *
     * @param player Le joueur a  definir.
     */
	public void setPlayer(Player player) {
		this.entities.removeIf(entity -> entity instanceof Player);
		this.player = player;
		entities.add(player);
	}
	
	public boolean checkPlayerExists() {
        if (player == null) {
            return false;
            
        } else {
            return true;
        }
    }
	
	/**
     * Retourne le NPC.
     *
     * @return Le NPC actuel.
     */
    public NPC1 getNPC() {
		return NPC;
	}

    /**
     * Definit le joueur actuel.
     *
     * @param player Le joueur a  definir.
     */
	public void setNPC(NPC1 npc1) {
		this.NPC = npc1;
		addEntity(npc1);
	}

	/**
     * Retourne le NPC.
     *
     * @return Le NPC actuel.
     */
    public NPC2 getNPC2() {
		return NPC2;
	}

    /**
     * Definit le joueur actuel.
     *
     * @param player Le joueur a  definir.
     */
	public void setNPC2(NPC2 npc2) {
		this.NPC2 = npc2;
		addEntity(npc2);
	}
	/**
     * Met a  jour les entites dans l'environnement.
     */
	public void tickEntities(){
    	
        entities.addAll(addedEntities);
        addedEntities.clear();
        
        for (Entity entity : entities) {
            entity.tick(this);
        }
        entities.removeIf(Entity::destroyed);
    }
    
    /**
     * Retourne le nombre total d'entites dans l'environnement.
     *
     * @return Le nombre total d'entites.
     */
    public int getEntityCount(){
        return entities.size();
    }
    
    /**
     * Recupere une entite a l'index specifie.
     *
     * @param i L'index de l'entite.
     * @return L'entite a  l'index specifie.
     */
    public Entity getEntity(int i){
        return entities.get(i);
    }

    /**
     * Retourne la progression du jeu.
     *
     * @return La progression du jeu.
     */
	public Progress getGameProgression() {
		return gameProgression;
	}

    /**
     * Definit la progression du jeu.
     *
     * @param gameProgression La progression du jeu a definir.
     */
	public void setGameProgression(Progress gameProgression) {
		this.gameProgression = gameProgression;
	}
  
}
