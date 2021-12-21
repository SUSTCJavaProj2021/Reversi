package com.demo.reversi.view.contentpages;

import com.demo.reversi.component.TitleLabel;
import com.demo.reversi.controller.SimpleGameSystem;
import com.demo.reversi.res.lang.LiteralConstants;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HomePage implements Updatable {
    public final GridPane root;

    public final GridPane displayPane;


    public PieChart winRateChart;
    public Data winCntData;
    public Data lossCntData;

    public Label welcomeText;

    public Theme theme;

    public HomePage(SimpleGameSystem gameSystem, Theme theme) {
        this.theme = theme;


        root = new GridPane();
        root.addRow(0, new TitleLabel(LiteralConstants.HomePageTitle.toString(), theme));

        welcomeText = new Label("Welcome to Reversi!");


        welcomeText.setFont(new Font("Cambria", 24));
        welcomeText.setTextFill(Color.WHITE);
        welcomeText.setWrapText(true);

        winCntData = new Data("Win", 0.02);
        lossCntData = new Data("Loss", 0.98);

        ObservableList<PieChart.Data> WRChartData = FXCollections.observableArrayList(winCntData, lossCntData);

        winRateChart = new PieChart(WRChartData);
        winRateChart.setTitle("Poor win rate");
        winRateChart.setLegendSide(Side.LEFT);
        try {
            winRateChart.getStylesheets().add(theme.getClass().getResource("piechart.css").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        /*
         Why is that the color must be changed using stylesheet? wtf?
         winCntData.getNode().setStyle("-fx-pie-color: GREEN");
         lossCntData.getNode().setStyle("-fx-pie-color: RED");
        */

        displayPane = new GridPane();

        GridPane statPane = new GridPane();
        statPane.add(winRateChart, 0, 0);
        displayPane.add(statPane, 0, 1);

//        Image img = new Image(imageSrc);
//        BackgroundImage bkig = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, false, false, false, true));
//        Background bg = new Background(bkig);
//        rootPane.setBackground(bg);

        GridPane.setHalignment(statPane, HPos.CENTER);

        Label welcomeText = new Label(LiteralConstants.WelcomeText.toString());
        welcomeText.setFont(new Font("Constantia", 30));
        welcomeText.setAlignment(Pos.CENTER_LEFT);
        welcomeText.setTextFill(Color.WHITE);
        welcomeText.setWrapText(true);

        Label welcomeText2 = new Label();
        welcomeText2.setFont(new Font("Constantia", 17));
        welcomeText2.setAlignment(Pos.CENTER_LEFT);
        welcomeText2.setTextFill(Color.WHITE);
        welcomeText2.setWrapText(true);

        //Initialize the Clock
        {
            Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                welcomeText2.setText("It's " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:MM:ss , LLLL dd")) + " now.\n" +
                        "Get to rest if you have played for too long.");
            }),
                    new KeyFrame(Duration.seconds(1))
            );
            clock.setCycleCount(Animation.INDEFINITE);
            clock.play();
        }


        root.addRow(1, welcomeText);
        root.addRow(2,welcomeText2);
        root.addRow(3, displayPane);

        {
            RowConstraints rootRowConstraints[] = new RowConstraints[4];
            for (int i = 0; i < 4; i++) {
                rootRowConstraints[i] = new RowConstraints();
                root.getRowConstraints().add(rootRowConstraints[i]);
            }

            rootRowConstraints[1].setPercentHeight(20);
            rootRowConstraints[2].setPercentHeight(10);
            rootRowConstraints[3].setPercentHeight(50);
        }

    }

    @Override
    public void update() {

    }
}
