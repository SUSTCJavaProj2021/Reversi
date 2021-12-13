package view;

import component.pagecomponents.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class SelectorPage {
    public VBox root;

    public HomeButton homeButton;
    public PlayButton playButton;
    public StatisticsButton statisticsButton;
    public SaveAndLoadButton saveAndLoadButton;
    public SettingsButton settingsButton;
    public ExitButton exitButton;

    public AdaptiveStyleButton currentSelectedButton;

    public enum Selector{
        Home, Play, Statistics, SaveAndLoad, Settings, Exit;
    }

    public SelectorPage() {

        root = new VBox();
        GridPane.setVgrow(root, Priority.ALWAYS);
        GridPane.setHgrow(root, Priority.ALWAYS);

        homeButton = new HomeButton();
        playButton = new PlayButton();
        statisticsButton = new StatisticsButton();
        saveAndLoadButton = new SaveAndLoadButton();
        settingsButton = new SettingsButton();
        exitButton = new ExitButton();

        Region fillRegion = new Region();
        VBox.setVgrow(fillRegion, Priority.ALWAYS);
        HBox buttonGap[] = new HBox[4];
        for(int i = 0; i < 4; i ++ ){
            buttonGap[i] = new HBox();
            buttonGap[i].setMinHeight(5);
            buttonGap[i].setMaxHeight(5);
            buttonGap[i].setMinWidth(HBox.USE_COMPUTED_SIZE);
        }

        root.getChildren().add(homeButton);
        root.getChildren().add(buttonGap[0]);
        root.getChildren().add(playButton);
        root.getChildren().add(buttonGap[1]);
        root.getChildren().add(statisticsButton);
        root.getChildren().add(buttonGap[2]);
        root.getChildren().add(saveAndLoadButton);
        root.getChildren().add(buttonGap[3]);
        root.getChildren().add(settingsButton);
        root.getChildren().add(fillRegion);
        root.getChildren().add(exitButton);

        root.setBackground(new Background(new BackgroundFill(Color.web("1D1F2C"), null, null)));

        currentSelectedButton = homeButton;
        setCurrentSelection(Selector.Home);
    }

    public void setCurrentSelection(Selector s){
        currentSelectedButton.setBackground(AdaptiveStyleButton.defaultBackground);
        currentSelectedButton.isSelected = false;

        switch(s){
            case Home:
                homeButton.setBackground(AdaptiveStyleButton.onMousePressedBackground);
                homeButton.isSelected = true;
                currentSelectedButton = homeButton;
                break;

            case Play:
                playButton.setBackground(AdaptiveStyleButton.onMousePressedBackground);
                playButton.isSelected = true;
                currentSelectedButton = playButton;
                break;

            case Statistics:
                statisticsButton.setBackground(AdaptiveStyleButton.onMousePressedBackground);
                statisticsButton.isSelected = true;
                currentSelectedButton = statisticsButton;
                break;

            case Settings:
                settingsButton.setBackground(AdaptiveStyleButton.onMousePressedBackground);
                settingsButton.isSelected = true;
                currentSelectedButton = settingsButton;
                break;

            case SaveAndLoad:
                saveAndLoadButton.setBackground(AdaptiveStyleButton.onMousePressedBackground);
                saveAndLoadButton.isSelected = true;
                currentSelectedButton = saveAndLoadButton;
                break;

            case Exit:
                exitButton.setBackground(AdaptiveStyleButton.onMousePressedBackground);
                exitButton.isSelected = true;
                currentSelectedButton = exitButton;
                break;
        }
    }
}
