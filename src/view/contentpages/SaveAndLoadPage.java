package view.contentpages;

import component.TitleLabel;
import controller.GameSystem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.prompts.LoadSystemPrompt;

public class SaveAndLoadPage {
    public final GridPane root;
    public final GameSystem gameSystem;

    public SaveAndLoadPage(GameSystem gameSystem) {
        this.gameSystem = gameSystem;
        root = new GridPane();
        root.add(new TitleLabel("Save And Load"), 0, 0);


        Button btn = new Button("Load");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage loadPrompt = new Stage();
                loadPrompt.setHeight(500);
                loadPrompt.setWidth(800);
                loadPrompt.setScene(new Scene(new LoadSystemPrompt(gameSystem).root));
                loadPrompt.show();
            }
        });

        root.add(btn, 0, 1);
    }
}
