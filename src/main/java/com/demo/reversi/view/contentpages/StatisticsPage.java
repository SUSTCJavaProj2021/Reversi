package com.demo.reversi.view.contentpages;

import com.demo.reversi.component.MetroButton;
import com.demo.reversi.component.TitleLabel;
import com.demo.reversi.component.panes.SmoothishScrollPane;
import com.demo.reversi.component.statistics.PlayerItem;
import com.demo.reversi.controller.basic.GameSystem;
import com.demo.reversi.controller.interfaces.GameSystemLayer;
import com.demo.reversi.controller.interfaces.PlayerLayer;
import com.demo.reversi.controller.local.SimpleGameSystem;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.res.lang.LiteralConstants;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import java.util.List;

public class StatisticsPage implements Updatable {
    public final GridPane root;
    public final SmoothishScrollPane scrollPane;
    public final GridPane contentWrapper;
    public final GridPane configWrapper;

    public final MetroButton refreshButton;

    public final GameSystemLayer gameSystem;
    public final Theme theme;

    public StatisticsPage(GameSystemLayer gameSystem, Theme theme) {
        this.gameSystem = gameSystem;
        this.theme = theme;
        root = new GridPane();
        contentWrapper = new GridPane();
        scrollPane = new SmoothishScrollPane(contentWrapper);
        configWrapper = new GridPane();

        {
            {
                RowConstraints[] constraints = new RowConstraints[3];
                for (int i = 0; i < 3; i++) {
                    constraints[i] = new RowConstraints();
                    root.getRowConstraints().add(constraints[i]);
                }
                constraints[1].setVgrow(Priority.ALWAYS);
            }
            {
                ColumnConstraints[] constraints = new ColumnConstraints[3];
                for (int i = 0; i < 3; i++) {
                    constraints[i] = new ColumnConstraints();
                    constraints[i].setHgrow(Priority.ALWAYS);
                    root.getColumnConstraints().add(constraints[i]);
                }
            }
        }

        //finally
        root.add(new TitleLabel(LiteralConstants.StatisticsPageTitle.toString(), theme), 0, 0);
        root.add(scrollPane, 0, 1);
        root.add(configWrapper, 0, 2);



        refreshButton = new MetroButton("Refresh", theme);
        configWrapper.add(refreshButton, 0, 0);

        initRefreshButton();
        refreshStats();
    }

    private void initRefreshButton() {
        refreshButton.setOnAction(event -> {
            Log0j.writeInfo("Refreshing statistics.");
            refreshStats();
        });
        
    }

    private void refreshStats() {
        Log0j.writeInfo("Stats refreshing triggered.");
        contentWrapper.getChildren().clear();
        List<PlayerLayer> playerLayerList = gameSystem.queryPlayerInfoAllSorted();
        Log0j.writeInfo("Player List count: " + playerLayerList.size());

        //Initialize header
        contentWrapper.add(new PlayerItem(theme), 0, 0);

        //Add player items
        for (int i = 0; i < playerLayerList.size(); i++) {
            Log0j.writeInfo("Player Item added: " + playerLayerList.get(i).nameProperty() + ", given index: " + contentWrapper.getRowCount());
            contentWrapper.add(new PlayerItem(i + 1, playerLayerList.get(i), theme), 0, contentWrapper.getRowCount());
        }
    }

    @Override
    public void update() {
        refreshStats();
    }

    @Override
    public void performOnCloseAction() {

    }


}
