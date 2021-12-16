package view.contentpages;

import component.TitleLabel;
import controller.SimpleGameSystem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import res.literal.LiteralConstants;
import view.Theme;
import view.Updatable;
import view.prompts.LoadSystemPrompt;

public class SaveAndLoadPage implements Updatable {
    public final GridPane root;
    public final SimpleGameSystem gameSystem;
    public final Theme theme;

    public SaveAndLoadPage(SimpleGameSystem gameSystem, Theme theme) {
        this.gameSystem = gameSystem;
        this.theme = theme;
        root = new GridPane();
        root.add(new TitleLabel(LiteralConstants.SaveAndLoadPageTitle.toString(), theme), 0, 0);


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

    @Override
    public void update() {

    }
}
