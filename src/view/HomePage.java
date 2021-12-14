package view;

import controller.GameController;
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
import res.language.LiteralConstants;

public class HomePage {
    public GridPane root;

    public GridPane displayPane;
    public HBox hBox;

    public HBox hBox1;
    public HBox hBox2;
    public HBox hBox3;
    public HBox hBox4;

    public PieChart winRateChart;
    public Data winCntData;
    public Data lossCntData;

    public Label welcomeText;

    public HomePage(GameController controller) {

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

        /*
         Why is that the color must be changed using stylesheet? wtf?
         winCntData.getNode().setStyle("-fx-pie-color: GREEN");
         lossCntData.getNode().setStyle("-fx-pie-color: RED");
        */

        root = new GridPane();
        displayPane = new GridPane();

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

        root.addColumn(0, hBox);
        root.addColumn(1, displayPane);

        ColumnConstraints rootColConstraints[] = new ColumnConstraints[2];
        for (int i = 0; i < 2; i++) {
            rootColConstraints[i] = new ColumnConstraints();
            root.getColumnConstraints().add(rootColConstraints[i]);
        }

        rootColConstraints[0].setPercentWidth(25);
        rootColConstraints[1].setPercentWidth(75);

        root.setBackground(new Background(new BackgroundFill(Color.web("26272F"), null, null)));
    }

}
