package main.java;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.java.Environment.Progress;
import main.java.entity.projectile.Arrow;
import main.java.item.HealPotion;
import main.java.item.Key;
import main.java.item.Item1;
import main.java.item.Item2;
import main.java.item.GG;






/**
 * La classe TakeTheChest représente l'application principale du jeu.
 * Elle étend la classe Application de JavaFX et est responsable de la configuration de l'environnement du jeu,
 * de la gestion des ticks du jeu, du rendu du jeu et de la gestion des états de fin de partie (game over et victoire).
 */
public class TakeTheChest extends Application {
    
    private Canvas canvas;
    private boolean dead;
    private Camera camera;
    private Renderer renderer;
    private Environment environment1;
    private Environment environment2;
    private Input input;
    private Stage stage;
    private Inventory inventory;
    private Timeline time;
    private Text vie;
    private Text score;
    private Timer chrono;
    private Text chrono_display;
    private static final int TIME_TO_FINISH_THE_GAME = 300;

    private boolean useFirstEnvironment = true; // Variable pour basculer entre les environnements


    /**
     * Le point d'entrée principal de l'application.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initialise le jeu et configure la fenetre du jeu.
     *
     * @param stage Le stage principal de l'application sur lequel la scene peut etre définie.
     */
    @Override
    public void start(Stage stage) {
        
        if (dead) {
            dead = false;
            time.stop();
        }

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1000, 750);
        
        canvas = new Canvas(scene.getWidth(), scene.getHeight() - 100);
        canvas.setFocusTraversable(true);
        environment1 = new Environment();
        environment2 = new Environment();
        renderer = new Renderer(canvas);
        camera = new Camera(100, 200);
        root.setCenter(canvas);
        
        environment1.generateMonsters();
        environment1.generateItems();
        environment2.generateMonsters();
        environment2.generateItems2();
                
        chrono = new Timer(TIME_TO_FINISH_THE_GAME);
        chrono_display = new Text(chrono.toString());
        chrono_display.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        
        score = new Text();
        score.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        
        vie = new Text(String.valueOf((int) environment1.getPlayer().getLife()));
        vie.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        
        HBox top_hbox = new HBox(score, new Text("||"), chrono_display, new Text("||"), vie);
        top_hbox.setMaxWidth(scene.getWidth());
        top_hbox.setPrefHeight(25);
        top_hbox.setAlignment(Pos.CENTER);
        top_hbox.setSpacing(50);
        root.setTop(top_hbox);

        inventory = new Inventory();
        inventory.getHbox().setPrefWidth(scene.getWidth());
        inventory.getHbox().setAlignment(Pos.CENTER);
        inventory.getHbox().setSpacing(75);
        inventory.getHbox().setMinHeight(75);
        HBox bottom = new HBox(inventory.getHbox());
        root.setBottom(bottom);
        
        input = new Input(scene);

        stage.getIcons().add(new Image("blocks/chest.png"));
        stage.setTitle("Take the Chest");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        this.stage = stage;

        int frameRate = 60;
        final Duration d = Duration.millis((double) 1000 / frameRate);
        final KeyFrame oneFrame = new KeyFrame(d, this::tick);

        time = new Timeline(frameRate, oneFrame);
        time.setCycleCount(Animation.INDEFINITE);
        time.playFromStart();
    }
    
    /**
     * Retourne le temps du jeu
     */
    public Timer getChrono() {
        return chrono;
    }

    /**
     * Effectue une itération du jeu lors d'un tick d'horloge.
     *
     * @param actionEvent L'événement d'action déclenché lors du tick d'horloge.
     */
    private void tick(ActionEvent actionEvent) {
        
        chrono.tick();
        chrono_display.setText(chrono.toString());

        Environment currentEnvironment = useFirstEnvironment ? environment1 : environment2;

        score.setText(String.valueOf((int) currentEnvironment.getPlayer().getScore()));
        vie.setText(String.valueOf((int) currentEnvironment.getPlayer().getLife()));
        
        currentEnvironment.getPlayer().handleInput(input, currentEnvironment);
        currentEnvironment.tickEntities();
        
        camera.setTarget_x(currentEnvironment.getPlayer().getX() + currentEnvironment.getPlayer().getWidth() / 2);
        camera.setTarget_y(currentEnvironment.getPlayer().getY());
        camera.tick();
        
        renderer.render(camera, currentEnvironment);

        // Vérifie si le joueur a la clé pour passer à l'environnement suivant
        
        if (useFirstEnvironment &&currentEnvironment.getPlayer().hasItem(Key.class) &&  currentEnvironment.getPlayer().getdoorTouched() ) {
            useFirstEnvironment = false;
            environment2.setPlayer(environment1.getPlayer());
            environment2.setGameProgression(Progress.GG);
            environment2.getPlayer().removeFromInventory(Key.class,1);
            environment2.getPlayer().setX(9);
            environment2.getPlayer().setY(51);
            environment2.getPlayer().setdoorTouched(false);
            
            // Mettre à jour les textes pour refléter l'état du nouveau joueur
            score.setText(String.valueOf((int) environment2.getPlayer().getScore()));
            vie.setText(String.valueOf((int) environment2.getPlayer().getLife()));
            
        }
       
        
        if (currentEnvironment.getPlayer().hasItem(Arrow.class)) {
            inventory.getSlot1().setImage(new Image("inventory/inventory_bow.png"));
        } else {
            inventory.getSlot1().setImage(new Image("inventory/inventory.png"));
        }
        
        if (currentEnvironment.getPlayer().hasItem(Key.class)) {
            inventory.getSlot2().setImage(new Image("inventory/inventory_key.png"));
        } else {
            inventory.getSlot2().setImage(new Image("inventory/inventory.png"));
        }
        
  /*      if (currentEnvironment.getPlayer().hasItem(HealPotion.class)) {
            inventory.getSlot3().setImage(new Image("items/heal_potion.png"));
        } else {
            inventory.getSlot3().setImage(new Image("inventory/inventory.png"));
        }*/
        if (currentEnvironment.getPlayer().hasItem(GG.class)) {
            inventory.getSlot3().setImage(new Image("items/GG.png"));
        } else {
            inventory.getSlot3().setImage(new Image("inventory/inventory.png"));
        }
        
     /*   switch (currentEnvironment.getPlayer().getShieldValue()) {
            case (3):
                inventory.getSlot4().setImage(new Image("inventory/inventory_shield_x3.png"));
                break;
            case (2):
                inventory.getSlot4().setImage(new Image("inventory/inventory_shield_x2.png"));
                break;
            case (1):
                inventory.getSlot4().setImage(new Image("inventory/inventory_shield_x1.png"));
                break;
            case (0):
                inventory.getSlot4().setImage(new Image("inventory/inventory_shield_broken.png"));
                break;
        }*/
        if (currentEnvironment.getPlayer().hasItem(Item1.class)) {
            inventory.getSlot4().setImage(new Image("items/item_one.png"));
        } else {
            inventory.getSlot4().setImage(new Image("inventory/inventory.png"));
        }
        if (currentEnvironment.getPlayer().hasItem(Item2.class)) {
            inventory.getSlot5().setImage(new Image("items/item_two.png"));
        } else {
            inventory.getSlot5().setImage(new Image("inventory/inventory.png"));
        }
        if ((currentEnvironment.getPlayer().getLife() <= 0.1 && !dead) || currentEnvironment.getPlayer().getY() < 0 || chrono.isFinished()) { // Conditions de défaites
            time.stop();
            gameOver();
        }


        if (currentEnvironment.getGameProgression() == Environment.Progress.WIN && !dead) {
            victory();
        }
        
    }
    
    /**
     * Affiche l'écran de fin de partie en cas de défaite.
     */
    private void gameOver() {
        
        dead = true;
        
        BorderPane deadPane = new BorderPane();
        ImageView imgView = new ImageView(new Image("game_over.png", 500, 500, false, false));
        
        Text score_display = new Text("Score : " + score.getText());
        score_display.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        
        Text vie_display = new Text("Vie : " + vie.getText());
        vie_display.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        
        Text chrono_display = new Text("Temps joué : " + String.valueOf((int) (TIME_TO_FINISH_THE_GAME - (chrono.getRemainingTicks() / 60))) + " secondes.");
        chrono_display.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        
        VBox img_and_perf = new VBox(imgView, score_display, chrono_display, vie_display);
        img_and_perf.setPrefWidth(750);
        img_and_perf.setSpacing(40);
        img_and_perf.setAlignment(Pos.CENTER);
        
        deadPane.setCenter(img_and_perf);
        
        Button quit = new Button("Quitter");
        quit.setStyle("-fx-font: 22 arial; -fx-base: #ce5e7e; -fx-background-radius:50; -fx-background-insets: 0;");
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                System.exit(0);
            }
        });
        
        Button relaunch = new Button("Rejouer");
        relaunch.setStyle("-fx-font: 22 arial; -fx-base: #5eceae; -fx-background-radius:50; -fx-background-insets: 0;");
        relaunch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                Environment.BLOCK_PROPERTIES.set((short) 14, new BlockProperties.BlockProperty("magic_wall.png", true, false));
                start(stage);
            }
        });
        
        HBox bottom = new HBox(quit, relaunch);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPrefSize(750, 100);
        bottom.setSpacing(20);
        
        deadPane.setBottom(bottom);
        deadPane.setPrefSize(1000, 750);
        Scene death = new Scene(deadPane, 1000, 750);
        deadPane.setPrefSize(death.getWidth(), death.getHeight());
        
        stage.setScene(death);
        stage.setTitle("Game Over");
        stage.show();
    }
    
    /**
     * Affiche l'écran de fin de partie en cas de victoire.
     */
    private void victory() {
        
        environment1.getPlayer().addScore(environment1.getPlayer().getLife() * 50 + (chrono.getRemainingTicks() / 60));
        score.setText(String.valueOf((int) environment1.getPlayer().getScore()));
        dead = true;
        
        BorderPane winPane = new BorderPane();
        ImageView imgView = new ImageView(new Image("win.jpg", 500, 500, false, false));
        
        Text score_display = new Text("Score : " + score.getText());
        score_display.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        
        Text vie_display = new Text("Vie : " + vie.getText());
        vie_display.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        
        Text chrono_display = new Text("Temps joué : " + String.valueOf((int) (TIME_TO_FINISH_THE_GAME - (chrono.getRemainingTicks() / 60))) + " secondes.");
        chrono_display.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        
        VBox img_and_perf = new VBox(imgView, score_display, chrono_display, vie_display);
        img_and_perf.setPrefWidth(750);
        img_and_perf.setSpacing(40);
        img_and_perf.setAlignment(Pos.CENTER);
        
        winPane.setCenter(img_and_perf);
        
        Button quit = new Button("Quitter");
        quit.setStyle("-fx-font: 22 arial; -fx-base: #ce5e7e; -fx-background-radius:50; -fx-background-insets: 0;");
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                System.exit(0);
            }
        });
        
        Button relaunch = new Button("Rejouer");
        relaunch.setStyle("-fx-font: 22 arial; -fx-base: #5eceae; -fx-background-radius:50; -fx-background-insets: 0;");
        relaunch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                Environment.BLOCK_PROPERTIES.set((short) 14, new BlockProperties.BlockProperty("magic_wall.png", true, false));
                start(stage);
            }
        });
        
        HBox bottom = new HBox(quit, relaunch);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPrefSize(750, 100);
        bottom.setSpacing(20);
        winPane.setBottom(bottom);
        
        Scene win = new Scene(winPane, 1000, 750);
        winPane.setPrefSize(win.getWidth(), win.getHeight());
        
        stage.setScene(win);
        stage.setTitle("You Win");
        stage.show();
    }

}
