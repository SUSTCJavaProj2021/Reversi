package view.contentpages;

import component.TitleLabel;
import controller.SimpleGameSystem;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import res.literal.LiteralConstants;
import view.Theme;
import view.Updatable;

public class StatisticsPage implements Updatable {
    public final GridPane root;

    public final SimpleGameSystem gameSystem;
    public final Theme theme;

    public StatisticsPage(SimpleGameSystem gameSystem, Theme theme){
        this.gameSystem = gameSystem;
        this.theme = theme;
        root = new GridPane();
        root.add(new TitleLabel(LiteralConstants.StatisticsPageTitle.toString(), theme), 0, 0);

        root.add(new Label("Well played!"),0, 1);
    }

    @Override
    public void update() {

    }
}
