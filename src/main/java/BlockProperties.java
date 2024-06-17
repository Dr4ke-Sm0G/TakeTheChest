package main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;



/**
 * La classe BlockProperties represente les proprietes des blocs du jeu.
 * Elle charge les proprietes des blocs à  partir d'un fichier CSV et fournit des methodes pour y acceder.
 */
public class BlockProperties {
    /**
     * Le record BlockProperty represente les proprietes d'un bloc.
     * Il contient le chemin d'acces Ã  l'image du bloc, une valeur indiquant si le bloc est solide et un indicateur de grimpabilite.
     */
    public record BlockProperty(String imagePath, boolean solid, boolean climbable) {}
    private final ArrayList<BlockProperty> properties;

    /**
     * Constructeur de la classe BlockProperties.
     * Charge les proprietes des blocs a  partir d'un fichier CSV.
     */
    public BlockProperties() {
        properties = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("resources/blocks.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                properties.add(new BlockProperty(values[0], Objects.equals(values[1], "1"), Objects.equals(values[2], "1")));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retourne les proprietes du bloc a  l'indice specifie.
     *
     * @param i L'indice du bloc.
     * @return Les proprietes du bloc.
     */
    public BlockProperty get(short i) {
        return properties.get(i);
    }
    public void set(short i, BlockProperty property){
        properties.set(i,property);
    }
    /**
     * Retourne le nombre de proprietes de blocs disponibles.
     *
     * @return Le nombre de proprietes de blocs.
     */
    public int size(){
        return properties.size();
    }
}
