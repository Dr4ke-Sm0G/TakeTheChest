package main.java;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;

/**
 * La classe Input gere les entrees clavier dans une application JavaFX.
 */
public class Input {

    private final boolean[] keyBoardState;

    private boolean mouseLeftButtonPressed = false;

    
    /**
     * Initialise une nouvelle instance de la classe Input.
     *
     * @param scene la scene JavaFX a surveiller pour les evenements clavier
     */
    public Input(Scene scene) {
        keyBoardState = new boolean[KeyCode.values().length];
        
        // Gestion des evenements de touche enfoncee
        scene.setOnKeyPressed((KeyEvent event) -> {
            keyBoardState[event.getCode().ordinal()] = true;
        });
        
        // Gestion des evenements de touche relâchee
        scene.setOnKeyReleased((KeyEvent event) -> {
            keyBoardState[event.getCode().ordinal()] = false;
        });
        
        scene.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                mouseLeftButtonPressed = true;
            }
        });

        scene.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                mouseLeftButtonPressed = false;
            }
        });

    }

    /**
     * Verifie si une touche est enfoncee.
     *
     * @param key la touche a verifier
     * @return true si la touche est enfoncee, false sinon
     */
    public boolean keyPressed(KeyCode key) {
        return keyBoardState[key.ordinal()];
    }
    
    public boolean isMouseLeftButtonPressed() {
        return mouseLeftButtonPressed;
    }

}
