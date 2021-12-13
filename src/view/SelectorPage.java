package view;

import component.*;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class SelectorPage {
    public VBox root;

    public PlayButton playButton;
    public StatisticsButton statisticsButton;
    public SaveAndLoadButton saveAndLoadButton;
    public SettingsButton settingsButton;
    public ExitButton exitButton;

    public SelectorPage() {
        root = new VBox();
        GridPane.setVgrow(root, Priority.ALWAYS);
        GridPane.setHgrow(root, Priority.ALWAYS);

        playButton = new PlayButton();
        statisticsButton = new StatisticsButton();
        saveAndLoadButton = new SaveAndLoadButton();
        settingsButton = new SettingsButton();
        exitButton = new ExitButton();

        Region fillRegion = new Region();
        VBox.setVgrow(fillRegion, Priority.ALWAYS);

        System.out.println(root.getWidth());

        root.setOnZoom(new EventHandler<ZoomEvent>() {
            @Override
            public void handle(ZoomEvent event) {
                playButton.setPrefSize(root.getWidth(), 50);
            }
        });

//        VBox.setVgrow(playButton, Priority.ALWAYS);
//        VBox.setVgrow(statisticsButton, Priority.ALWAYS);
//        VBox.setVgrow(saveAndLoadButton, Priority.ALWAYS);
//        VBox.setVgrow(settingsButton, Priority.ALWAYS);
//        VBox.setVgrow(exitButton, Priority.ALWAYS);


        root.getChildren().addAll(playButton, statisticsButton, saveAndLoadButton, settingsButton, fillRegion, exitButton);
        root.setBackground(new Background(new BackgroundFill(Color.DEEPSKYBLUE, null, null)));
    }
}
