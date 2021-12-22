package com.demo.reversi.view.contentpages;

import com.demo.reversi.component.MetroButton;
import com.demo.reversi.component.TitleLabel;
import com.demo.reversi.component.panes.SmoothishScrollPane;
import com.demo.reversi.controller.PlayerLayer;
import com.demo.reversi.controller.local.SimpleGameSystem;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.res.lang.LiteralConstants;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import javafx.scene.layout.GridPane;

import java.util.List;

public class StatisticsPage implements Updatable {
    public final GridPane root;
    public final SmoothishScrollPane scrollPane;
    public final GridPane contentWrapper;
    public final GridPane configWrapper;

    public final MetroButton refreshButton;

    public final SimpleGameSystem gameSystem;
    public final Theme theme;

    public StatisticsPage(SimpleGameSystem gameSystem, Theme theme){
        this.gameSystem = gameSystem;
        this.theme = theme;
        root = new GridPane();
        contentWrapper = new GridPane();
        scrollPane = new SmoothishScrollPane(contentWrapper);
        configWrapper = new GridPane();

        //finally
        root.add(new TitleLabel(LiteralConstants.StatisticsPageTitle.toString(), theme), 0, 0);
        root.add(new TitleLabel(LiteralConstants.SettingsPageTitle.toString(), theme), 0, 0);
        root.add(scrollPane, 0, 1);
        root.add(configWrapper, 0, 2);


        //todo: Change test
        refreshButton = new MetroButton("Refresh", theme);
        configWrapper.add(refreshButton,0,0);
        initRefreshButton();
    }

    private void initRefreshButton() {
        refreshButton.setOnAction(event -> {
            Log0j.writeInfo("Refreshing statistics.");
            refreshStats();
        });
    }

    private void refreshStats(){
        contentWrapper.getChildren().clear();
        List<PlayerLayer> playerLayerList = gameSystem.queryPlayerInfoAllSorted();
    }

    @Override
    public void update() {
        refreshStats();
    }


}
