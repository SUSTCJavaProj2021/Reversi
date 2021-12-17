package com.demo.reversi.view.prompts;

import com.demo.reversi.controller.SimpleGameSystem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

public class LoadSystemPrompt {
    public GridPane root;
    public ScrollPane display;

    public SimpleGameSystem gameSystem;

    public LoadSystemPrompt(SimpleGameSystem gameSystem){
        this.gameSystem = gameSystem;
        root = new GridPane();
    }
}
