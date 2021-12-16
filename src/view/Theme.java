package view;

import controller.logger.Log0j;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

public class Theme {
    //Stage related
    public SimpleDoubleProperty mainWindowPrefWidth;
    public SimpleDoubleProperty mainWindowPrefHeight;
//    public int mainWindowMinWidth;
//    public int mainWindowMinHeight;

    //Overall Theme related
    public ObjectProperty<Background> backPaneBackgroundPR;
    public ObjectProperty<Background> frontPaneBackgroundPR;
    public BooleanProperty isBackgroundPureColor;

    public ObjectProperty<Color> themeColorPR;
    public ObjectProperty<Paint> themePaintPR;
    public ObjectProperty<Font> titleFontFamilyPR;
    public ObjectProperty<Paint> titleFontPaintPR;
    public ObjectProperty<Font> menuFontFamilyPR;
    public ObjectProperty<Paint> menuFontPaintPR;
    public ObjectProperty<Font> textFontFamilyPR;
    public ObjectProperty<Paint> textFontPaintPR;
    //Audio related
    public DoubleProperty volumePR;

    //Chessboard Color
    public ObjectProperty<Paint> playerChessPaintPR1;
    public ObjectProperty<Paint> playerChessPaintPR2;
    public ObjectProperty<Paint> chessGridPaintPR1;
    public ObjectProperty<Paint> chessGridPaintPR2;
    public ObjectProperty<Background> chessBoardBackgroundPR;

    //Fields below are all default settings.
    //They are all base on experience.

    public static final int DEFAULT_MAIN_WINDOW_PREF_WIDTH = 1280;
    public static final int DEFAULT_MAIN_WINDOW_PREF_HEIGHT = 800;
    public static final int DEFAULT_MAIN_WINDOW_MIN_WIDTH = 600;
    public static final int DEFAULT_MAIN_WINDOW_MIN_HEIGHT = 450;

    public static final Background defaultBackPaneBKGND = new Background(new BackgroundImage(new Image("/res/background.jpg"),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT));

    public static final Background defaultFrontPaneBKGND = new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.15),
            new CornerRadii(MainView.VIEWCOVER_CORNER_RADII), null));

    public static final Color defaultThemeColor = Color.rgb(29, 31, 44);
    public static final Paint defaultThemePaint = Color.rgb(29, 31, 44);
    public static final Font defaultTitleFontFamily = new Font("Constantia", 25);
    public static final Paint defaultTitleFontPaint = Color.WHITE;
    public static final Font defaultMenuFontFamily = new Font("Segoe UI", 16);
    public static final Paint defaultMenuFontPaint = Color.WHITE;
    public static final Font defaultTextFontFamily = new Font("Segoe UI", 16);
    public static final Paint defaultTextFontPaint = Color.WHITE;

    //todo: finish chessboard paint default
    //Default Theme
    public Theme() {
        mainWindowPrefWidth = new SimpleDoubleProperty(DEFAULT_MAIN_WINDOW_PREF_WIDTH);
        mainWindowPrefHeight = new SimpleDoubleProperty(DEFAULT_MAIN_WINDOW_PREF_HEIGHT);

        backPaneBackgroundPR = new SimpleObjectProperty<>(defaultBackPaneBKGND);
        frontPaneBackgroundPR = new SimpleObjectProperty<>(defaultFrontPaneBKGND);

        themeColorPR = new SimpleObjectProperty<>(defaultThemeColor);
        themePaintPR = new SimpleObjectProperty<>(defaultThemePaint);
        titleFontFamilyPR = new SimpleObjectProperty<>(defaultTitleFontFamily);
        titleFontPaintPR = new SimpleObjectProperty<>(defaultTitleFontPaint);
        menuFontFamilyPR = new SimpleObjectProperty<>(defaultMenuFontFamily);
        menuFontPaintPR = new SimpleObjectProperty<>(defaultMenuFontPaint);
        textFontFamilyPR = new SimpleObjectProperty<>(defaultTextFontFamily);
        textFontPaintPR = new SimpleObjectProperty<>(defaultTextFontPaint);

        Log0j.writeLog("Theme initialized.");
        initRelations();
        loadTheme();
    }

    public void applyDefaultTheme() {
        mainWindowPrefWidth.setValue(DEFAULT_MAIN_WINDOW_PREF_WIDTH);
        mainWindowPrefHeight.setValue(DEFAULT_MAIN_WINDOW_PREF_HEIGHT);

        backPaneBackgroundPR.setValue(defaultBackPaneBKGND);
        frontPaneBackgroundPR.setValue(defaultFrontPaneBKGND);

        themeColorPR.setValue(defaultThemeColor);
//        themePaintPR.setValue(defaultThemePaint);

        titleFontFamilyPR.setValue(defaultTitleFontFamily);
        titleFontPaintPR.setValue(defaultTitleFontPaint);
        menuFontFamilyPR.setValue(defaultMenuFontFamily);
        menuFontPaintPR.setValue(defaultMenuFontPaint);
        textFontFamilyPR.setValue(defaultTextFontFamily);
        textFontPaintPR.setValue(defaultTextFontPaint);
        Log0j.writeLog("Default Theme Applied.");
    }

    public void initRelations() {
        themePaintPR.bind(Bindings.createObjectBinding(() -> {
            return themeColorPR.getValue();
        }, themeColorPR));
        Log0j.writeLog("Relation initialized.");
    }

    public void bindToStage(Stage stage){
        bindToStageWidth(stage.widthProperty());
        bindToStageHeight(stage.heightProperty());
    }

    public void bindToStageWidth(ReadOnlyDoubleProperty widthProperty){
        mainWindowPrefWidth.bind(widthProperty);
    }

    public void bindToStageHeight(ReadOnlyDoubleProperty heightProperty){
        mainWindowPrefHeight.bind(heightProperty);
    }

    public void bindToBackPane(ObjectProperty<Background> background) {
        background.bind(Bindings.createObjectBinding(() -> {
            Background newBackground = backPaneBackgroundPR.getValue();
            return newBackground;
        }, backPaneBackgroundPR));
    }

    public void bindBackPanePRTo(ObjectProperty<Background> background) {
        backPaneBackgroundPR.bind(Bindings.createObjectBinding(() -> {
            Background newBackground = background.getValue();
            return newBackground;
        }, background));
    }

    public void unbindBackPane() {
        backPaneBackgroundPR.unbind();
    }

    public void bindToFrontPane(ObjectProperty<Background> background) {
        background.bind(Bindings.createObjectBinding(() -> {
            Background newBackground = frontPaneBackgroundPR.getValue();
            return newBackground;
        }, frontPaneBackgroundPR));
    }

    public void bindFrontPanePRTo(ObjectProperty<Background> background) {
        frontPaneBackgroundPR.bind(Bindings.createObjectBinding(() -> {
            Background newBackground = background.getValue();
            return newBackground;
        }, background));
    }

    public void unbindFrontPane() {
        frontPaneBackgroundPR.unbind();
    }


    public void bindToThemePaint(ObjectProperty<Paint> paint) {
        paint.bind(Bindings.createObjectBinding(() -> {
            Paint newPaint = themePaintPR.getValue();
            return newPaint;
        }, themePaintPR));
    }

    public void bindToPaintBackground(ObjectProperty<Background> background) {
        background.bind(Bindings.createObjectBinding(() -> {
            Background newBackground = new Background(new BackgroundFill(themePaintPR.getValue(), null, null));
            return newBackground;
        }, themePaintPR));
    }

    public void bindToTitleFontFamily(ObjectProperty<Font> fontFamily) {
        fontFamily.bind(Bindings.createObjectBinding(() -> {
            return titleFontFamilyPR.getValue();
        }, titleFontFamilyPR));
    }

    public void bindToTitleFontPaint(ObjectProperty<Paint> fontPaint) {
        fontPaint.bind(Bindings.createObjectBinding(() -> {
            return titleFontPaintPR.getValue();
        }, titleFontPaintPR));
    }

    public void bindToTextFontFamily(ObjectProperty<Font> fontFamily) {
        fontFamily.bind(Bindings.createObjectBinding(() -> {
            return textFontFamilyPR.getValue();
        }, textFontFamilyPR));
    }

    public void bindToTextFontPaint(ObjectProperty<Paint> fontPaint) {
        fontPaint.bind(Bindings.createObjectBinding(() -> {
            return textFontPaintPR.getValue();
        }, textFontPaintPR));
    }


    public void loadTheme() {
        loadTheme("/res/theme.json");
    }

    public void loadTheme(String srcPath) {
        try {
            //Try to read the configuration file
            FileReader themeFileSrc = new FileReader(srcPath);
            JSONObject jsonObject = new JSONObject(themeFileSrc);

            themeFileSrc.close();
            Log0j.writeLog("Theme loaded.");
        } catch (Exception e) {
            e.printStackTrace();
            Log0j.writeLog("Error Occurred. Resetting all data to default value.");
            applyDefaultTheme();
        }
    }

    public void saveTheme() {

        Log0j.writeLog("Theme saved.");
    }

}
