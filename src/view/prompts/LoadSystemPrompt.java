package view.prompts;

import controller.GameSystem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class LoadSystemPrompt {
    public GridPane root;
    public ScrollPane display;

    public GameSystem gameSystem;

    public LoadSystemPrompt(GameSystem gameSystem){
        this.gameSystem = gameSystem;
        root = new GridPane();
    }
}
