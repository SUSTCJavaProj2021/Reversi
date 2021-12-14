package view.prompts;

import controller.GameSystem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class LoadGamePrompt {
    public GridPane root;
    public ScrollPane display;

    public GameSystem gameSystem;

    public LoadGamePrompt(GameSystem gameSystem){
        this.gameSystem = gameSystem;
    }
}
