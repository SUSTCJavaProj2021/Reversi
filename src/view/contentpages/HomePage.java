package view.contentpages;

import component.TitleLabel;
import controller.GameSystem;
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
import res.literal.LiteralConstants;
import view.Theme;

public class HomePage {
    public final GridPane root;

    public final GridPane displayPane;

    public HBox hBox1;
    public HBox hBox2;
    public HBox hBox3;
    public HBox hBox4;

    public PieChart winRateChart;
    public Data winCntData;
    public Data lossCntData;

    public Label welcomeText;

    public Theme theme;

    public HomePage(GameSystem gameSystem, Theme theme) {
        this.theme = theme;

        welcomeText = new Label(LiteralConstants.WelcomeText.toString());

        hBox1 = new HBox();
        hBox2 = new HBox();
        hBox3 = new HBox();
        hBox4 = new HBox();

        welcomeText.setFont(new Font("Cambria", 24));
        welcomeText.setTextFill(Color.WHITE);
        welcomeText.setWrapText(true);

        winCntData = new Data("Win", 0.02);
        lossCntData = new Data("Loss", 0.98);

        ObservableList<PieChart.Data> WRChartData = FXCollections.observableArrayList(winCntData, lossCntData);

        winRateChart = new PieChart(WRChartData);
        winRateChart.setTitle("Poor win rate");
        winRateChart.setLegendSide(Side.LEFT);
        winRateChart.getStylesheets().add("/res/chart.css");

        /*
         Why is that the color must be changed using stylesheet? wtf?
         winCntData.getNode().setStyle("-fx-pie-color: GREEN");
         lossCntData.getNode().setStyle("-fx-pie-color: RED");
        */

        root = new GridPane();
        root.addRow(0, new TitleLabel("Home", theme));

        displayPane = new GridPane();


        ColumnConstraints UIColConstraints[] = new ColumnConstraints[2];
        RowConstraints UIRowConstraints[] = new RowConstraints[2];
        for (int i = 0; i < 2; i++) {
            UIColConstraints[i] = new ColumnConstraints();
            UIColConstraints[i].setPercentWidth(50);
            UIRowConstraints[i] = new RowConstraints();
            UIRowConstraints[i].setPercentHeight(50);
            displayPane.getColumnConstraints().add(UIColConstraints[i]);
            displayPane.getRowConstraints().add(UIRowConstraints[i]);
        }

        GridPane statPane = new GridPane();
        statPane.add(winRateChart, 0, 0);
        statPane.add(hBox4, 0, 1);

        GridPane.setHalignment(hBox4, HPos.CENTER);

        displayPane.add(hBox1, 0, 0);
        displayPane.add(hBox3, 1, 0);
        displayPane.add(statPane, 0, 1);
        displayPane.add(hBox2, 1, 1);

//        Image img = new Image(imageSrc);
//        BackgroundImage bkig = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, false, false, false, true));
//        Background bg = new Background(bkig);
//        rootPane.setBackground(bg);

        GridPane.setHalignment(hBox1, HPos.CENTER);
        GridPane.setHalignment(hBox2, HPos.CENTER);
        GridPane.setHalignment(hBox3, HPos.CENTER);
        GridPane.setHalignment(statPane, HPos.CENTER);

        Label welcomeText = new Label(LiteralConstants.WelcomeText.toString());
        welcomeText.setFont(new Font("Constantia", 30));
        welcomeText.setAlignment(Pos.CENTER_LEFT);
        welcomeText.setTextFill(Color.WHITE);
        welcomeText.setWrapText(true);

        Label welcomeText2 = new Label("But who can play this game for more than 15 minutes? Unless you are debugging.\n QAQ");
        welcomeText2.setFont(new Font("Constantia", 15));
        welcomeText2.setAlignment(Pos.CENTER_LEFT);
        welcomeText2.setTextFill(Color.WHITE);
        welcomeText2.setWrapText(true);

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

}
