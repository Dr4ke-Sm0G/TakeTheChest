package main.java.entity;

import java.util.Map;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import main.java.Environment;
import main.java.Environment.Progress;
import main.java.Input;
import main.java.entity.projectile.Arrow;
import main.java.entity.projectile.ArrowUpgraded;
import main.java.item.GG;
import main.java.item.HealPotion;
import main.java.item.Key;
import main.java.item.Item1;
import main.java.item.Item2;
import main.java.item.Item4;
import main.java.item.Item;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Player extends LivingEntity {

    private Image image = new Image("living_entities/player_right.png");
    private static Image image_left = new Image("living_entities/player_left.png");
    private static Image image_right = new Image("living_entities/player_right.png");
    protected boolean climbing;
    protected boolean allowToShoot;
    private boolean monsterIsDead;
    private double score;
    private int nbreMonsterDead;
    private boolean doorTouched = false;
    private boolean inventoryKeyPressed = false;
    private boolean errorDialogShown = false;
    private boolean messageDialogShown = false;  // Ajoutez cette ligne
    Environment environment;
    LivingEntity owner;
    Arrow arrow = new Arrow(0,0,0,owner);
    
    HealPotion healpotion = new HealPotion(0, 0);
    Key key = new Key(0, 0);

    public Player(double x, double y) {
        super(x, y, 30, 1);
        this.setLast_shot(30);
        this.setScore(0);
        this.setMonsterIsDead(false);
        this.setNumberDeaths(0);
    }

    public void setNumberDeaths(int n) {
        this.nbreMonsterDead = n;
    }

    public int getNumberDeaths() {
        return this.nbreMonsterDead;
    }

    @Override
    public void tick(Environment environment) {
        super.tick(environment);
        climbing = false;

        for (short block : touched) {
            climbing = climbing || Environment.BLOCK_PROPERTIES.get(block).climbable();
            if (block == 15) {
                if (environment.getPlayer().hasItem(Key.class))
                    environment.getPlayer().setdoorTouched(true);
            }

            if (block == 16) {
                environment.getPlayer().setLife(0);
            }
        }
        if (climbing) {
            vx = 0;
            vy = 0;
        }
    }
    
    void displayInventory(LivingEntity entity,Environment environment) {
        Platform.runLater(() -> {
            Stage inventoryStage = new Stage();
            inventoryStage.initModality(Modality.APPLICATION_MODAL);
            inventoryStage.setTitle("Inventory");

            BorderPane inventoryPane = new BorderPane();
            
            Text headerText = new Text("Inventory");
            headerText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            VBox headerBox = new VBox(headerText);
            headerBox.setAlignment(Pos.CENTER);
            headerBox.setSpacing(10);
            inventoryPane.setTop(headerBox);

            VBox itemsBox = new VBox();
            itemsBox.setSpacing(10);
            itemsBox.setAlignment(Pos.CENTER_LEFT);
            
            for (Map.Entry<Item, Integer> entry : entity.getInventory().entrySet()) {
                Item item = entry.getKey();
                int quantity = entry.getValue();
                
                HBox itemBox = new HBox();
                itemBox.setSpacing(10);
                itemBox.setAlignment(Pos.CENTER_LEFT);
                
                Text itemText = new Text("- " + item.getClass().getSimpleName() + " x" + quantity);
                itemText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
                itemBox.getChildren().add(itemText);
                
                if (item instanceof HealPotion ) {
                    Button useButton = new Button("Use");
                    useButton.setStyle("-fx-font: 22 arial; -fx-base: #5eceae; -fx-background-radius:50; -fx-background-insets: 0;");
                    useButton.setOnAction(event -> {
                        environment.getPlayer().heal(5);
                        entity.removeFromInventory(HealPotion.class, 1);
                        inventoryStage.close();
                    });
                    itemBox.getChildren().add(useButton);
                }
                if (item instanceof Item1 ) {
                    Button useButton = new Button("Use");
                    useButton.setStyle("-fx-font: 22 arial; -fx-base: #5eceae; -fx-background-radius:50; -fx-background-insets: 0;");
                    useButton.setOnAction(event -> {
                    	showNPCSelectionDialog(this,environment.getNPC(),environment.getNPC2());
                        inventoryStage.close();
                    });
                    itemBox.getChildren().add(useButton);
                }
                if (item instanceof Item2 ) {
                    Button useButton = new Button("Use");
                    useButton.setStyle("-fx-font: 22 arial; -fx-base: #5eceae; -fx-background-radius:50; -fx-background-insets: 0;");
                    useButton.setOnAction(event -> {
                        showStealItemDialog((Player) entity, environment.getNPC(), environment.getNPC2());
                        inventoryStage.close();
                    });
                    itemBox.getChildren().add(useButton);
                }
                if (item instanceof GG) {
                    Button useButton = new Button("Use");
                    useButton.setStyle("-fx-font: 22 arial; -fx-base: #5eceae; -fx-background-radius:50; -fx-background-insets: 0;");
                    useButton.setOnAction(event -> {
                        environment.setGameProgression(Progress.WIN);
                        inventoryStage.close();
                    });
                    itemBox.getChildren().add(useButton);
                }
                if (item instanceof Item4 ) {
                    Button useButton = new Button("Use");
                    useButton.setStyle("-fx-font: 22 arial; -fx-base: #5eceae; -fx-background-radius:50; -fx-background-insets: 0;");
                    useButton.setOnAction(event -> {
                    	LivingEntity shooter = arrow.shotFrom(); // Récupère l'entité qui a tiré la flèche
                        if (onHit(this) && shooter instanceof Monster) {
                            Monster monster = (Monster) shooter; // Cast l'entité en Monster
                            monster.destroy(); // Appelle la méthode destroy sur le monstre
                        }
                        inventoryStage.close();
                    });
                    itemBox.getChildren().add(useButton);
                }
                
                itemsBox.getChildren().add(itemBox);
            }

            inventoryPane.setCenter(itemsBox);

            Button closeButton = new Button("Close");
            closeButton.setStyle("-fx-font: 22 arial; -fx-base: #ce5e7e; -fx-background-radius:50; -fx-background-insets: 0;");
            closeButton.setOnAction(event -> inventoryStage.close());

            HBox bottomBox = new HBox(closeButton);
            bottomBox.setAlignment(Pos.CENTER);
            bottomBox.setPrefSize(750, 100);
            bottomBox.setSpacing(20);
            inventoryPane.setBottom(bottomBox);

            Scene inventoryScene = new Scene(inventoryPane, 600, 400);
            inventoryStage.setScene(inventoryScene);
            inventoryStage.show();
        });
    }

    public void handleInput(Input input, Environment environment) {
        if (input.keyPressed(KeyCode.I)) {
            if (!inventoryKeyPressed) {
                displayInventory(this,environment);
                inventoryKeyPressed = true;
            }
        } else {
            inventoryKeyPressed = false;
        }

        if (input.keyPressed(KeyCode.O) && environment.getGameProgression() == Environment.Progress.BOW && environment.getNPC().hasItem(Arrow.class)) {
            this.addToInventory(new Arrow(0, 0, 0, this), 1);
            environment.getNPC().removeFromInventory(Arrow.class, 1);
        }

        if (input.keyPressed(KeyCode.P) && environment.getGameProgression() == Environment.Progress.BOW && environment.getNPC().hasItem(HealPotion.class)) {
            this.addToInventory(healpotion, 1);
            environment.getNPC().removeFromInventory(HealPotion.class, 1);
        }
   

        if (input.keyPressed(KeyCode.RIGHT) || input.keyPressed(KeyCode.D)) {
            vx += 0.07;
            this.image = Player.image_right;
        }
        if (input.keyPressed(KeyCode.LEFT) || input.keyPressed(KeyCode.Q)) {
            vx -= 0.07;
            this.image = Player.image_left;
        }
        if (input.keyPressed(KeyCode.UP) || input.keyPressed(KeyCode.Z)) {
            if (on_ground) {
                vy = 0.35;
            } else if (climbing) {
                vy = 0.2;
            }
        }
        if ((input.keyPressed(KeyCode.DOWN) || input.keyPressed(KeyCode.S)) && climbing) {
            vy = -0.2;
        }

        if (hasArrow) {
            if (input.keyPressed(KeyCode.A) && getLast_shot() <= 0) {
                environment.addEntity(new Arrow(x + w / 2, y + 2 * h / 3, -0.5, this));
                setLast_shot(30);
            }
            if (input.keyPressed(KeyCode.E) && getLast_shot() <= 0) {
                environment.addEntity(new Arrow(x + w / 2, y + 2 * h / 3, 0.5, this));
                setLast_shot(30);
            }
            if (input.keyPressed(KeyCode.SPACE) && getLast_shot() <= 0) {
                if (this.image == Player.image_left) {
                    environment.addEntity(new ArrowUpgraded(x + w / 2, y + 2 * h / 3, -0.5, this));
                    setLast_shot(30);
                } else {
                    environment.addEntity(new ArrowUpgraded(x + w / 2, y + 2 * h / 3, 0.5, this));
                    setLast_shot(30);
                }
            }

            if (input.isMouseLeftButtonPressed() && getLast_shot() <= 0) {
                double shootDirection = this.image == Player.image_left ? -0.5 : 0.5;
                environment.addEntity(new Arrow(x + w / 2, y + 2 * h / 3, shootDirection, this));
                setLast_shot(30);
            }
        }
    }

    private void showNPCSelectionDialog(Player player, NPC1 npc, NPC2 npc2) {
    Platform.runLater(() -> {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Choose NPC");

        BorderPane dialogPane = new BorderPane();
        
        Text headerText = new Text("");
        headerText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        
        Text contentText = new Text("Enter NPC number to apply the item \n (1 for NPC1, 2 for NPC2):");
        contentText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        VBox textBox = new VBox(headerText, contentText);
        textBox.setSpacing(10);
        textBox.setAlignment(Pos.CENTER);
        dialogPane.setTop(textBox);

        // Input field and buttons
        javafx.scene.control.TextField inputField = new javafx.scene.control.TextField();
        inputField.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        
        Button applyButton = new Button("Apply");
        applyButton.setStyle("-fx-font: 22 arial; -fx-base: #5eceae; -fx-background-radius:50; -fx-background-insets: 0;");
        applyButton.setOnAction(event -> {
            String choice = inputField.getText();
            switch (choice) {
                case "1":
                	if(npc.isVisited()) {
                        displayInventory(npc,environment);
                        this.removeFromInventory(Item1.class, 1);

                	}
                	else showError("Not visited yet");
                	
                    break;
                case "2":
                	if(npc2.isVisited()) {
                        displayInventory(npc2,environment);
                        this.removeFromInventory(Item1.class, 1);

                        
                	}
                	else 
                        showError("Not visited yet");
                		break;
                default:
                    showError("Invalid choice.");

                    break;
            }
            dialogStage.close();
        });

        HBox inputBox = new HBox(inputField, applyButton);
        inputBox.setSpacing(10);
        inputBox.setAlignment(Pos.CENTER);
        dialogPane.setCenter(inputBox);

        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-font: 22 arial; -fx-base: #ce5e7e; -fx-background-radius:50; -fx-background-insets: 0;");
        closeButton.setOnAction(event -> dialogStage.close());

        HBox bottomBox = new HBox(closeButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPrefSize(750, 100);
        bottomBox.setSpacing(20);
        dialogPane.setBottom(bottomBox);

        Scene dialogScene = new Scene(dialogPane, 600, 300);
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    });
	}

private void showStealItemDialog(Player player, NPC1 npc1, NPC2 npc2) {
	
    Platform.runLater(() -> {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Choose NPC to Steal From");

        BorderPane dialogPane = new BorderPane();
        
        Text headerText = new Text("Steal an Item");
        headerText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        
        Text contentText = new Text("Enter NPC number to steal from (1 for NPC1, 2 for NPC2):");
        contentText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        VBox textBox = new VBox(headerText, contentText);
        textBox.setSpacing(10);
        textBox.setAlignment(Pos.CENTER);
        dialogPane.setTop(textBox);

        // Input field and buttons
        javafx.scene.control.TextField inputField = new javafx.scene.control.TextField();
        inputField.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        
        Button applyButton = new Button("Apply");
        applyButton.setStyle("-fx-font: 22 arial; -fx-base: #5eceae; -fx-background-radius:50; -fx-background-insets: 0;");
        applyButton.setOnAction(event -> {
            String choice = inputField.getText();
            switch (choice) {
                case "1":
                    if (npc1.isVisited()) {
                        showNPCItemsDialog(player, npc1);
                    } else {
                        showError("NPC1 not visited yet.");
                    }
                    break;
                case "2":
                    if (npc2.isVisited()) {
                        showNPCItemsDialog(player, npc2);
                    } else {
                        showError("NPC2 not visited yet.");
                    }
                    break;
                default:
                    showError("Invalid choice.");
                    break;
            }
            dialogStage.close();
        });

        HBox inputBox = new HBox(inputField, applyButton);
        inputBox.setSpacing(10);
        inputBox.setAlignment(Pos.CENTER);
        dialogPane.setCenter(inputBox);

        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-font: 22 arial; -fx-base: #ce5e7e; -fx-background-radius:50; -fx-background-insets: 0;");
        closeButton.setOnAction(event -> dialogStage.close());

        HBox bottomBox = new HBox(closeButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPrefSize(750, 100);
        bottomBox.setSpacing(20);
        dialogPane.setBottom(bottomBox);

        Scene dialogScene = new Scene(dialogPane, 600, 300);
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    });
}

private void showNPCItemsDialog(Player player, NonPlayableCharacter npc) {
    Platform.runLater(() -> {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Choose Item to Steal");

        BorderPane dialogPane = new BorderPane();
        
        Text headerText = new Text("Steal an Item");
        headerText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        
        VBox itemsBox = new VBox();
        itemsBox.setSpacing(10);
        itemsBox.setAlignment(Pos.CENTER_LEFT);
        
        for (Map.Entry<Item, Integer> entry : npc.getInventory().entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            
            HBox itemBox = new HBox();
            itemBox.setSpacing(10);
            itemBox.setAlignment(Pos.CENTER_LEFT);
            
            Text itemText = new Text("- " + item.getClass().getSimpleName() + " x" + quantity);
            itemText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            itemBox.getChildren().add(itemText);
            
            Button stealButton = new Button("Steal");
            stealButton.setStyle("-fx-font: 22 arial; -fx-base: #5eceae; -fx-background-radius:50; -fx-background-insets: 0;");
            stealButton.setOnAction(event -> {
                player.addToInventory(item, 1);
                if (quantity > 1) {
                    npc.getInventory().put(item, quantity - 1);
                } else {
                    npc.getInventory().remove(item);
                }
                player.removeFromInventory(Item2.class, 1);
                dialogStage.close();
            });
            itemBox.getChildren().add(stealButton);
            
            itemsBox.getChildren().add(itemBox);
        }

        if (itemsBox.getChildren().isEmpty()) {
            showError("NPC has no items to steal.");
            player.removeFromInventory(Item2.class, 1);
            dialogStage.close();
            return;
        }

        dialogPane.setCenter(itemsBox);

        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-font: 22 arial; -fx-base: #ce5e7e; -fx-background-radius:50; -fx-background-insets: 0;");
        closeButton.setOnAction(event -> dialogStage.close());

        HBox bottomBox = new HBox(closeButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPrefSize(750, 100);
        bottomBox.setSpacing(20);
        dialogPane.setBottom(bottomBox);

        Scene dialogScene = new Scene(dialogPane, 600, 400);
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    });
}

private void showError(String message) {
	    if (errorDialogShown) {
	        return;
	    }
	    errorDialogShown = true;
	
	    Platform.runLater(() -> {
	        Stage errorStage = new Stage();
	        errorStage.initModality(Modality.APPLICATION_MODAL);
	        errorStage.setTitle("Error");
	
	        BorderPane errorPane = new BorderPane();
	
	        Text errorText = new Text(message);
	        errorText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
	
	        VBox errorBox = new VBox(errorText);
	        errorBox.setAlignment(Pos.CENTER);
	        errorBox.setPrefSize(400, 100);
	        errorPane.setCenter(errorBox);
	
	
	        Scene errorScene = new Scene(errorPane, 400, 200);
	        errorStage.setScene(errorScene);
	        errorStage.show();
	    });
	}

public void showMessage(String message) {
	if (messageDialogShown) {
        return;
    }
    messageDialogShown = true;  // Ajoutez cette ligne
    Platform.runLater(() -> {
        Stage messageStage = new Stage();
        messageStage.initModality(Modality.APPLICATION_MODAL);
        messageStage.setTitle("Message");

        BorderPane messagePane = new BorderPane();
        
        Text messageText = new Text(message);
        messageText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

        VBox textBox = new VBox(messageText);
        textBox.setSpacing(10);
        textBox.setAlignment(Pos.CENTER);
        messagePane.setCenter(textBox);

        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-font: 22 arial; -fx-base: #ce5e7e; -fx-background-radius:50; -fx-background-insets: 0;");
        closeButton.setOnAction(event -> messageStage.close());

        HBox bottomBox = new HBox(closeButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPrefSize(750, 100);
        bottomBox.setSpacing(20);
        messagePane.setBottom(bottomBox);

        Scene messageScene = new Scene(messagePane, 400, 200);
        messageStage.setScene(messageScene);
        messageStage.show();
    });
}
@Override
public void addToInventory(Item item, int quantity) {
    if (inventory.size() < 5) {
        super.addToInventory(item, quantity);
    } else {
        showMessage("Inventory is full. Cannot add more items.");
    }
}

    @Override
    public Image getImage() {
        return image;
    }

    public boolean getdoorTouched() {
        return doorTouched;
    }

    public void setdoorTouched(boolean doorTouched) {
        this.doorTouched = doorTouched;
    }

    public boolean isAllowToShoot() {
        return allowToShoot;
    }

    public void setAllowToShoot(boolean allowToShoot) {
        this.allowToShoot = allowToShoot;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void addScore(double d) {
        this.score += d;
    }

    public void damageShield() {
        if (this.getShieldValue() > 0)
            this.setShieldValue(this.getShieldValue() - 1);
    }

    public boolean monsterIsDead() {
        return monsterIsDead;
    }

    public void setMonsterIsDead(boolean monsterIsDead) {
        this.monsterIsDead = monsterIsDead;
    }
    protected boolean onHit(LivingEntity livingEntity) {
        return true;
    }
}
