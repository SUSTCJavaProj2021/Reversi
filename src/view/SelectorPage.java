package view;

import component.selector.*;
import controller.Log0j;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class SelectorPage {
    public VBox root;

    public HomeButton homeButton;
    public PlayButton playButton;
    public StatisticsButton statisticsButton;
    public SaveAndLoadButton saveAndLoadButton;
    public SettingsButton settingsButton;
    public AboutButton aboutButton;
    public ExitButton exitButton;

    public AdaptiveStyleSelectorButton currentSelectedButton;

    public enum Selector{
        Home, Play, Statistics, SaveAndLoad, Settings, About, Exit;
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
        aboutButton = new AboutButton();
        exitButton = new ExitButton();


        Region fillRegion = new Region();
        VBox.setVgrow(fillRegion, Priority.ALWAYS);
        HBox buttonGap[] = new HBox[5];
        for(int i = 0; i < 5; i ++ ){
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
        root.getChildren().add(buttonGap[4]);
        root.getChildren().add(aboutButton);
        root.getChildren().add(fillRegion);
        root.getChildren().add(exitButton);

        root.setBackground(new Background(new BackgroundFill(Color.web("1D1F2C"), null, null)));

        currentSelectedButton = homeButton;
        setCurrentSelection(Selector.Home);
    }

    public void setCurrentSelection(Selector s){
        currentSelectedButton.setDeselected();

        switch(s){
            case Home:
                currentSelectedButton = homeButton;
                break;

            case Play:
                currentSelectedButton = playButton;
                break;

            case Statistics:
                currentSelectedButton = statisticsButton;
                break;

            case Settings:
                currentSelectedButton = settingsButton;
                break;

            case SaveAndLoad:
                currentSelectedButton = saveAndLoadButton;
                break;

            case About:
                currentSelectedButton = aboutButton;
                break;

            case Exit:
                currentSelectedButton = exitButton;
                break;
        }
        currentSelectedButton.setSelected();

        Log0j.writeLog(getClass().getSimpleName(), "Switched selection to " + currentSelectedButton.getClass());
    }
}
