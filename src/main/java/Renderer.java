
package main.java;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import main.java.entity.Entity;
import main.java.entity.LivingEntity;
import main.java.entity.NPC1;
import main.java.entity.NPC2;

import java.util.Objects;



/**
 * La classe Renderer est responsable du rendu graphique dans une application JavaFX.
 */
public class Renderer{
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final Image[] block_images;
    public static final int scale = 64;

    /**
     * Initialise une nouvelle instance de la classe Renderer.
     *
     * @param canvas le canevas sur lequel effectuer le rendu
     */
    public Renderer(Canvas canvas){
        this.canvas = canvas;
        gc = this.canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false);

        block_images = new Image[Environment.BLOCK_PROPERTIES.size()];

        for (int i=0;i<Environment.BLOCK_PROPERTIES.size();i++){
            String path = Environment.BLOCK_PROPERTIES.get((short)i).imagePath();
            if(!Objects.equals(path, "")){
                block_images[i] = new Image("blocks/"+Environment.BLOCK_PROPERTIES.get((short)i).imagePath());
            }
        }
    }

    private void drawImage(Image image, Camera camera, double x, double y, double w, double h){
        gc.drawImage(image, canvas.getWidth() / 2 + (x - camera.getX())* scale, canvas.getHeight() / 2 - (h + y - camera.getY())* scale, w*scale, h*scale);
    }

    //le rectangle de la conversation
    private void drawRect(Camera camera,double x, double y, double w, double h, Color color){
        gc.setFill(color);
        gc.fillRect(canvas.getWidth() / 2 + (x - camera.getX()) * scale, canvas.getHeight() / 2 - (y - camera.getY()) * scale,w * scale, h * scale);
    }

    private void drawText(Camera camera, String text, double x, double y,Color color){
        gc.setFill(color);
        gc.fillText(text, canvas.getWidth() / 2 + (x - camera.getX()) * scale, canvas.getHeight() / 2 - (y - camera.getY()) * scale);
    }

    /**
     * Effectue le rendu graphique de l'environnement et des entités sur le canevas.
     *
     * @param camera      la caméra utilisée pour le rendu
     * @param environment l'environnement contenant les éléments a rendre
     */
    public void render(Camera camera,Environment environment){

        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        int start_x = (int) Math.floor(camera.getX() - canvas.getWidth() / (scale *2));
        int end_x = (int) Math.ceil(camera.getX() + canvas.getWidth() / (scale *2));
        int start_y = (int) Math.floor(camera.getY() - canvas.getHeight() / (scale *2));
        int end_y = (int) Math.ceil(camera.getY() + canvas.getHeight() / (scale *2));

        for (int x = start_x; x < end_x; x++) {
            for (int y = start_y; y < end_y; y++) {
                short block = environment.getBlock(x, y);
                if (block_images[block]!=null) { 
                    drawImage(block_images[block],camera,x,y,1,1);
                }
            }
        }
        for (int i=0;i<environment.getEntityCount();i++) {
            Entity entity = environment.getEntity(i);
            if (entity.getImage()!=null){
                drawImage(entity.getImage(),camera,entity.getX() + entity.getImageOffsetX(),entity.getY()  + entity.getImageOffsetY(),entity.getImageSizeX(),entity.getImageSizeY());
                gc.setGlobalAlpha(1);
                if(entity instanceof LivingEntity livingEntity){
                    if (entity instanceof NPC1 npc){
                         drawRect(camera,livingEntity.getX()-1,livingEntity.getY()+2.5,4,1.5,Color.WHITE);
                        if (environment.getGameProgression() == Environment.Progress.WELCOME) {
                            drawText(camera,"Tu dois avoir la clé pour gagner! \nReste ici j'ai quelque chose pour toi !",livingEntity.getX() - 0.8 ,livingEntity.getY()+2,Color.BLACK);
                            if (npc.getCooldown() <= 0) environment.setGameProgression(Environment.Progress.BOW);
                        }
                        if (environment.getGameProgression() == Environment.Progress.BOW) {
                            drawText(camera,"Va chercher la clé et reviens vers moi. \nSi tu veux l'arc, appuye sur 'o'. \nEt si tu veux la potion, appuye 'p'",livingEntity.getX() - 0.8 ,livingEntity.getY()+2.2,Color.BLACK);
                        }
                        if (environment.getGameProgression() == Environment.Progress.KEY) {
                            drawText(camera,"Bravo, tu as trouvé la clé. \nVa chercher maintenant la porte pour \nPasser au deuxième monde",livingEntity.getX() - 0.8 ,livingEntity.getY()+2,Color.BLACK);
                        }
                        if (environment.getGameProgression() == Environment.Progress.GG) {
                            drawText(camera,"Bravo, tu es maintenant dans le deuxième \nnmonde. Va chercher maintenant l'item GG\n pour gagner",livingEntity.getX() - 0.8 ,livingEntity.getY()+2,Color.BLACK);
                        }

                    }
                    else if (entity instanceof NPC2 npc) {
                        
                            drawText(camera,"Salut Camarade !",livingEntity.getX() - 0.8 ,livingEntity.getY()+2,Color.BLACK);
                        }
                    

                    drawRect(camera,livingEntity.getX() + entity.getImageOffsetX() - .15,livingEntity.getY()  + livingEntity.getImageOffsetY() - 0.05,livingEntity.getImageSizeX()+.3,.2,Color.LIGHTGRAY);
                    Color color;
                    double lifeRatio = livingEntity.getLife()/ livingEntity.getMaxLife();
                    if (lifeRatio<=0.3) color = Color.RED;
                    else if (lifeRatio<=0.6) color = Color.ORANGE;
                    else color = Color.GREEN;
                    drawRect(camera,livingEntity.getX() + entity.getImageOffsetX() - .1,livingEntity.getY()  + livingEntity.getImageOffsetY() - 0.1,(livingEntity.getImageSizeX()+.2)* lifeRatio,.1,color);
                }
                
                
                
            }
            
        }
        
    }
    
    /**
     * Affiche l'inventaire du joueur sur le canevas.
     *
     * @param inventory L'inventaire du joueur.
     */
   

}

