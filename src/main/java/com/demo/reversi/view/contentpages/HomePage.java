package com.demo.reversi.view.contentpages;

import com.demo.reversi.MainApp;
import com.demo.reversi.component.TextLabel;
import com.demo.reversi.component.TitleLabel;
import com.demo.reversi.component.gamemodel.ChessBoard;
import com.demo.reversi.component.panes.SmoothishScrollPane;
import com.demo.reversi.controller.basic.GameSystem;
import com.demo.reversi.controller.interfaces.GameSystemLayer;
import com.demo.reversi.controller.local.SimpleGameController;
import com.demo.reversi.controller.local.SimpleGameSystem;
import com.demo.reversi.controller.local.SimplePlayer;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.res.lang.LiteralConstants;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HomePage implements Updatable {
    public final GridPane root;

    public final GridPane welcomePane;
    public final SmoothishScrollPane displayContainer;
    public final GridPane displayPane;

    public final TitleLabel welcomeLabel;
    public final TextLabel clockLabel;

    public Theme theme;

    public HomePage(GameSystemLayer gameSystem, Theme theme) {
        this.theme = theme;


        root = new GridPane();
        root.addRow(0, new TitleLabel(LiteralConstants.HomePageTitle.toString(), theme));

        Separator s = new Separator();
        s.setOpacity(0);
        root.addRow(1, s);

        welcomePane = new GridPane();
        root.addRow(2, welcomePane);

        welcomeLabel = new TitleLabel(LiteralConstants.WelcomeText.toString(), theme);
        welcomeLabel.setWrapText(true);

        clockLabel = new TextLabel(theme);
        clockLabel.setWrapText(true);

        displayPane = new GridPane();
        displayContainer = new SmoothishScrollPane(displayPane);
        root.addRow(3, displayContainer);
        GridPane.setHgrow(displayContainer, Priority.ALWAYS);
        GridPane.setVgrow(displayContainer, Priority.ALWAYS);

        //Initialize the Clock
        initClock();

        initLayout();
    }

    private void initLayout() {
        welcomePane.add(welcomeLabel, 0, 1);
        welcomePane.add(clockLabel, 0, 2);

        displayPane.add(new TitleLabel("Introduction to Reversi!", theme), 0, 0, GridPane.REMAINING, 1);
        TextFlow textFlow = new TextFlow();
        Text text = new Text();
        try {
            text.setText(Files.readString(Paths.get(MainApp.class.getResource("TutorialText.txt").toURI())));
            Log0j.writeInfo("Succeeded on reading tutorial text.");
        } catch (URISyntaxException | IOException e) {
            Log0j.writeError("Failed to read tutorial text. It will not be initialized.");
            e.printStackTrace();
        }
        text.fontProperty().bind(theme.textFontFamilyPR());
        text.fillProperty().bind(theme.titleFontPaintPR());
        textFlow.getChildren().add(text);
        displayPane.add(textFlow, 0, 1);
        displayPane.add(new ChessBoard(
                new SimpleGameController(new SimplePlayer("TEST PLAYER 1"), new SimplePlayer("TEST PLAYER 2"), true), theme), 1, 1);
    }

    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            clockLabel.setText("Current Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss , LLLL dd")) + "\n" +
                    "Get to rest if you have played for too long. (Or, are you still debugging late at night?)");
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    @Override
    public void update() {

    }
}
