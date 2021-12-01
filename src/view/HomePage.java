package view;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Duration;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;
import res.language.LiteralConstants;

public class HomePage {

    public Scene scene;

    public GridPane UIPane;
    public GridPane rootPane;
    public HBox hBox;

    public Button playButton;
    public Button settingsButton;
    public Button slButton;
    public Button statisticsButton;

    public PieChart winRateChart;
    public Data winCntData;
    public Data lossCntData;

    public Label welcomeText;

    public HomePage(int resWidth, int resHeight, String imageSrc) {

        welcomeText = new Label(LiteralConstants.WelcomeText.getText());

        playButton = new Button(LiteralConstants.PlayButtonText.getText());
        settingsButton = new Button(LiteralConstants.SettingsButtonText.getText());
        slButton = new Button(LiteralConstants.slButtonText.getText());
        statisticsButton = new Button(LiteralConstants.StatisticsButtonText.getText());

        welcomeText.setFont(new Font("Cambria", 24));
        welcomeText.setTextFill(Paint.valueOf("BLACK"));

        winCntData = new Data("Win", 0.02);
        lossCntData = new Data("Loss", 0.98);

        ObservableList<PieChart.Data> WRChartData = FXCollections.observableArrayList(winCntData, lossCntData);

        winRateChart = new PieChart(WRChartData);
        winRateChart.setTitle("Poor win rate");
        winRateChart.setLegendSide(Side.LEFT);

        /*
         Why is that the color must be changed using stylesheet? wtf?
         winCntData.getNode().setStyle("-fx-pie-color: GREEN");
         lossCntData.getNode().setStyle("-fx-pie-color: RED");
        */

        rootPane = new GridPane();
        UIPane = new GridPane();
        scene = new Scene(rootPane, resWidth, resHeight);

        // JMetro
        // JMetro jMetro = new JMetro(Style.DARK);
        // jMetro.setScene(scene);
        // rootPane.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        // End

        hBox = new HBox();

        hBox.getChildren().add(welcomeText);
        hBox.setAlignment(Pos.CENTER);

        ColumnConstraints UIColConstraints[] = new ColumnConstraints[2];
        RowConstraints UIRowConstraints[] = new RowConstraints[2];
        for (int i = 0; i < 2; i++) {
            UIColConstraints[i] = new ColumnConstraints();
            UIColConstraints[i].setPercentWidth(50);
            UIRowConstraints[i] = new RowConstraints();
            UIRowConstraints[i].setPercentHeight(50);
            UIPane.getColumnConstraints().add(UIColConstraints[i]);
            UIPane.getRowConstraints().add(UIRowConstraints[i]);
        }

        GridPane statPane = new GridPane();
        statPane.add(winRateChart, 0, 0);
        statPane.add(statisticsButton, 0, 1);

        GridPane.setHalignment(statisticsButton, HPos.CENTER);

        UIPane.add(playButton, 0, 0);
        UIPane.add(slButton, 1, 0);
        UIPane.add(statPane, 0, 1);
        UIPane.add(settingsButton, 1, 1);

        Image img = new Image(imageSrc);
        BackgroundImage bkig = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, false, false, false, true));
        Background bg = new Background(bkig);
        //rootPane.setBackground(bg);

        GridPane.setHalignment(playButton, HPos.CENTER);
        GridPane.setHalignment(settingsButton, HPos.CENTER);
        GridPane.setHalignment(slButton, HPos.CENTER);
        GridPane.setHalignment(statPane, HPos.CENTER);

        rootPane.addColumn(0, hBox);
        rootPane.addColumn(1, UIPane);

        ColumnConstraints rootColConstraints[] = new ColumnConstraints[2];
        for (int i = 0; i < 2; i++) {
            rootColConstraints[i] = new ColumnConstraints();
            rootPane.getColumnConstraints().add(rootColConstraints[i]);
        }
        rootColConstraints[0].setPercentWidth(25);
        rootColConstraints[1].setPercentWidth(75);
    }

}
