package com.demo.reversi.themes;

import com.demo.reversi.logger.Log0j;
import com.demo.reversi.view.MainView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Theme {

    //Fields below are all default settings.
    //They are all base on experience.
    public static final double DEFAULT_CORNER_RADII = 10;

    public static final int DEFAULT_MAIN_WINDOW_PREF_WIDTH = 1280;
    public static final int DEFAULT_MAIN_WINDOW_PREF_HEIGHT = 800;
    public static final int DEFAULT_MAIN_WINDOW_MIN_WIDTH = 600;
    public static final int DEFAULT_MAIN_WINDOW_MIN_HEIGHT = 450;

    public static Path defaultMainViewBGMSource;
    public static Path defaultGamePageBGMSource;
    public static Path defaultGameFinishBGMSource;
    public static Path defaultChessSoundSource;

    static {
        try {
            defaultMainViewBGMSource = Paths.get(Theme.class.getResource("MainViewBGM.mp3").toURI());
            Log0j.writeLog("Main View BGM loaded on path: " + defaultMainViewBGMSource);
        } catch (Exception e) {
            Log0j.writeLog("Main View BGM loading failed. Check your path.");
            e.printStackTrace();
        }
        try {
            defaultGamePageBGMSource = Paths.get(Theme.class.getResource("GamePageBGM.mp3").toURI());
            Log0j.writeLog("Game Page BGM loaded on path: " + defaultGamePageBGMSource);
        } catch (Exception e) {
            Log0j.writeLog("Game Page BGM loading failed. Check your path.");
            e.printStackTrace();
        }
        try {
            defaultGameFinishBGMSource = Paths.get(Theme.class.getResource("GameFinishBGM.mp3").toURI());
            Log0j.writeLog("Game Finish BGM loaded on path: " + defaultGameFinishBGMSource);
        } catch (Exception e) {
            Log0j.writeLog("Game Finish BGM loading failed. Check your path.");
            e.printStackTrace();
        }
        try {
            defaultChessSoundSource = Paths.get(Paths.get(Theme.class.getResource("soundeffects/").toURI()).toString(), "Chess.mp3");
            Log0j.writeLog("Chess sound loaded on path : " + defaultChessSoundSource);
        } catch (Exception e) {
            Log0j.writeLog("Chess sound loading failed. Check your path.");
            e.printStackTrace();
        }
    }

    public static final double defaultBGMVolume = 0.4;
    public static final double defaultEffectVolume = 0.4;


    public static Path defaultBackgroundSource;

    static {
        try {
            defaultBackgroundSource = Paths.get(Theme.class.getResource("background.jpg").toURI());
            Log0j.writeLog("Correctly loaded default background source from " + defaultBackgroundSource.toUri().toString());
        } catch (URISyntaxException e) {
            Log0j.writeLog("Default background loading failed. Check your path.");
            e.printStackTrace();
        }
    }

    public static final Background defaultBackPaneBKGND = new Background(
            new BackgroundImage(new Image(defaultBackgroundSource.toUri().toString()),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT));

    public static final Background defaultFrontPaneBKGND = new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.15),
            new CornerRadii(MainView.VIEWCOVER_CORNER_RADII), null));

    public static Path defaultChessSource;

    static {
        try {
            defaultChessSource = Paths.get(Theme.class.getResource("Chess.png").toURI());
        } catch (URISyntaxException | NullPointerException e) {
            Log0j.writeLog("Failed to load default chess component. Chess will be initialized using circles.");
            e.printStackTrace();
        }
    }

    public static final Paint defaultDarkModeColor = Color.rgb(32, 32, 32);
    public static final Paint defaultLightModeColor = Color.rgb(240, 240, 240);
    public static final Color defaultThemeColor = Color.rgb(29, 31, 44);
    public static final Paint defaultThemePaint = Color.rgb(29, 31, 44);
    public static final Font defaultTitleFontFamily = new Font("Garamond", 25);
    public static final Paint defaultTitleFontPaint = Color.WHITE;
    public static final Font defaultMenuFontFamily = new Font("Segoe UI", 14);
    public static final Paint defaultMenuFontPaint = Color.WHITE;
    public static final Font defaultTextFontFamily = new Font("Segoe UI", 16);
    public static final Paint defaultTextFontPaint = Color.WHITE;

    public static final Paint defaultPlayerChessPaint1 = Color.WHITE;
    public static final Paint defaultPlayerChessPaint2 = Color.BLACK;
    public static final Paint defaultChessBoardPaint1 = Color.rgb(29, 31, 44);
    public static final Paint defaultChessBoardPaint2 = Color.rgb(55, 58, 84);
    public static final Paint defaultChessBoardGridPaint = Color.rgb(255, 255, 255, 0.50);
    public static final Background defaultChessBoardBackground = new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(DEFAULT_CORNER_RADII), null));

    /**
     * Below are properties
     */

    //Stage related
    public final SimpleDoubleProperty mainWindowPrefWidth;
    public final SimpleDoubleProperty mainWindowPrefHeight;
//    public int mainWindowMinWidth;
//    public int mainWindowMinHeight;

    public static Image appIcon;

    static {
        try {
            appIcon = new Image(Theme.class.getResource("icons/App.png").toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Audio related
    public MediaPlayer bgmPlayer;
    public final ObjectProperty<Path> mainViewBGMSourcePR;
    public final ObjectProperty<Path> gamePageBGMSourcePR;
    public final ObjectProperty<Path> gameFinishBGMSourcePR;
    private int gameCnt;
    public final ObjectProperty<Path> chessSoundSourcePR;
    public final DoubleProperty bgmVolumePR;
    public final DoubleProperty effectVolumePR;

    //Audio File Related
    public final BooleanProperty bgmSourceMoved;
    public final BooleanProperty chessSoundSourceMoved;


    //Theme related
    public final ObjectProperty<Background> backPaneBackgroundPR;
    public final ObjectProperty<Background> frontPaneBackgroundPR;
//    public final BooleanProperty isBackgroundPureColor;

    /**
     * If <code>modeSwitchPR</code> is set to true, then dark mode is applied.
     */
    public final BooleanProperty modeSwitchPR;
    public final ObjectProperty<Paint> modePaintPR;
    //This property is for getting the reversed color of the mode.
    public final ObjectProperty<Paint> modeRevPaintPR;

    public final ObjectProperty<Color> themeColorPR;
    public final ObjectProperty<Paint> themePaintPR;

    public final ObjectProperty<Font> titleFontFamilyPR;
    public final ObjectProperty<Paint> titleFontPaintPR;

    public final ObjectProperty<Font> menuFontFamilyPR;
    public final ObjectProperty<Paint> menuFontPaintPR;

    public final ObjectProperty<Font> textFontFamilyPR;
    public final ObjectProperty<Paint> textFontPaintPR;

    //Chessboard Color
    public final ObjectProperty<Paint> playerChessPaintPR1;
    public final ObjectProperty<Paint> playerChessPaintPR2;
    public final ObjectProperty<Paint> chessBoardPaintPR1;
    public final ObjectProperty<Paint> chessBoardPaintPR2;
    public final ObjectProperty<Paint> chessBoardGridPaintPR;
    public final ObjectProperty<Background> chessBoardBackgroundPR;


    public final Stage primaryStage;

    //todo: finish chessboard paint default
    //Default Theme
    public Theme(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mainWindowPrefWidth = new SimpleDoubleProperty();
        mainWindowPrefHeight = new SimpleDoubleProperty();

        bgmVolumePR = new SimpleDoubleProperty();
        mainViewBGMSourcePR = new SimpleObjectProperty<>();
        gamePageBGMSourcePR = new SimpleObjectProperty<>();
        gameFinishBGMSourcePR = new SimpleObjectProperty<>();
        gameCnt = 0;
        chessSoundSourcePR = new SimpleObjectProperty<>();
        effectVolumePR = new SimpleDoubleProperty();

        bgmSourceMoved = new SimpleBooleanProperty();
        chessSoundSourceMoved = new SimpleBooleanProperty();

        backPaneBackgroundPR = new SimpleObjectProperty<>();
        frontPaneBackgroundPR = new SimpleObjectProperty<>();

        modeSwitchPR = new SimpleBooleanProperty();
        modePaintPR = new SimpleObjectProperty<>();
        modeRevPaintPR = new SimpleObjectProperty<>();

        themeColorPR = new SimpleObjectProperty<>();
        themePaintPR = new SimpleObjectProperty<>();
        titleFontFamilyPR = new SimpleObjectProperty<>();
        titleFontPaintPR = new SimpleObjectProperty<>();
        menuFontFamilyPR = new SimpleObjectProperty<>();
        menuFontPaintPR = new SimpleObjectProperty<>();
        textFontFamilyPR = new SimpleObjectProperty<>();
        textFontPaintPR = new SimpleObjectProperty<>();

        playerChessPaintPR1 = new SimpleObjectProperty<>();
        playerChessPaintPR2 = new SimpleObjectProperty<>();
        chessBoardPaintPR1 = new SimpleObjectProperty<>();
        chessBoardPaintPR2 = new SimpleObjectProperty<>();
        chessBoardGridPaintPR = new SimpleObjectProperty<>();
        chessBoardBackgroundPR = new SimpleObjectProperty<>();

        applyDefaultTheme();
        initRelations();
        Log0j.writeLog("Theme initialized.");

        //Audio Applied.

        loadTheme();

        try {
            Media media;
            //todo: modify this
            if (!bgmSourceMoved.getValue()) {
                media = new Media(mainViewBGMSourcePR.getValue().toUri().toString());
            } else {
                Log0j.writeLog("Default BGM Source initialized on path: " + defaultMainViewBGMSource.toUri().toString());
                media = new Media(defaultMainViewBGMSource.toUri().toString());
            }
            bgmPlayer = new MediaPlayer(media);
            bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            bgmPlayer.volumeProperty().bind(bgmVolumePR);
            Log0j.writeLog("BGM Player Initialized.");

        } catch (Exception e) {
            e.printStackTrace();
            Log0j.writeLog("Failed to initialize BGM.");
        }
    }

    public void applyDefaultTheme() {
        primaryStage.setWidth(DEFAULT_MAIN_WINDOW_PREF_WIDTH);
        primaryStage.setHeight(DEFAULT_MAIN_WINDOW_PREF_HEIGHT);

        bgmVolumePR.setValue(defaultBGMVolume);
        effectVolumePR.setValue(defaultEffectVolume);

        mainViewBGMSourcePR.setValue(defaultMainViewBGMSource);
        gamePageBGMSourcePR.setValue(defaultGamePageBGMSource);
        gameFinishBGMSourcePR.setValue(defaultGameFinishBGMSource);
        chessSoundSourcePR.setValue(defaultChessSoundSource);

        bgmSourceMoved.setValue(false);
        chessSoundSourceMoved.setValue(false);

        backPaneBackgroundPR.setValue(defaultBackPaneBKGND);
        frontPaneBackgroundPR.setValue(defaultFrontPaneBKGND);

        modeSwitchPR.setValue(true);
        modePaintPR.setValue(defaultDarkModeColor);
        modeRevPaintPR.setValue(defaultLightModeColor);

        themeColorPR.setValue(defaultThemeColor);
        themePaintPR.setValue(defaultThemePaint);

        titleFontFamilyPR.setValue(defaultTitleFontFamily);
        titleFontPaintPR.setValue(defaultTitleFontPaint);
        menuFontFamilyPR.setValue(defaultMenuFontFamily);
        menuFontPaintPR.setValue(defaultMenuFontPaint);
        textFontFamilyPR.setValue(defaultTextFontFamily);
        textFontPaintPR.setValue(defaultTextFontPaint);

        playerChessPaintPR1.setValue(defaultPlayerChessPaint1);
        playerChessPaintPR2.setValue(defaultPlayerChessPaint2);
        chessBoardPaintPR1.setValue(defaultChessBoardPaint1);
        chessBoardPaintPR2.setValue(defaultChessBoardPaint2);
        chessBoardGridPaintPR.setValue(defaultChessBoardGridPaint);
        chessBoardBackgroundPR.setValue(defaultChessBoardBackground);

        Log0j.writeLog("Default Theme Applied.");

    }

    public void initRelations() {

        //Bind mode switch to dynamically change color.
        modePaintPR.bind(Bindings.createObjectBinding(() -> {
            if (modeSwitchPR.getValue()) {
                return defaultDarkModeColor;
            } else {
                return defaultLightModeColor;
            }
        }, modeSwitchPR));

        modeRevPaintPR.bind(Bindings.createObjectBinding(() -> {
            if (modeSwitchPR.getValue()) {
                return defaultLightModeColor;
            } else {
                return defaultDarkModeColor;
            }
        }, modeSwitchPR));

        titleFontPaintPR.bind(modeRevPaintPR);
        menuFontPaintPR.bind(modeRevPaintPR);
        textFontPaintPR.bind(modeRevPaintPR);

        //Bind theme paint to theme color
        themePaintPR.bind(Bindings.createObjectBinding(themeColorPR::getValue, themeColorPR));


        Log0j.writeLog("Relation initialized.");
    }

    public static Image getAppIcon() {
        return appIcon;
    }

    public void bindToStage(Stage stage) {
        bindToStageWidth(stage.widthProperty());
        bindToStageHeight(stage.heightProperty());
    }

    public void bindToStageWidth(ReadOnlyDoubleProperty widthProperty) {
        mainWindowPrefWidth.bind(widthProperty);
    }

    public void bindToStageHeight(ReadOnlyDoubleProperty heightProperty) {
        mainWindowPrefHeight.bind(heightProperty);
    }

    public MediaPlayer getBGMPlayer() {
        return bgmPlayer;
    }

    public ObjectProperty<Path> getMainViewBgmSource() {
        return mainViewBGMSourcePR;
    }

    public ObjectProperty<Path> getGamePageBGMSource() {
        return gamePageBGMSourcePR;
    }

    public void registerGame() {
        if (gameCnt == 0) {
            Platform.runLater(() -> {
                setBGMPlayerContent(gamePageBGMSourcePR.getValue());
            });
        }
        gameCnt++;
    }

    public void unregisterGame() {
        gameCnt--;
        if (gameCnt == 0) {
            Platform.runLater(() -> {
                setBGMPlayerContent(mainViewBGMSourcePR.getValue());
            });
        }
    }

    public ObjectProperty<Path> gameFinishBGMSourcePR(){
        return gameFinishBGMSourcePR;
    }

    public void setBGMPlayerContent(Path path) {
        //Unbind the previous bgm player volume first.
        Platform.runLater(bgmPlayer.volumeProperty()::unbind);

        Timeline fadeOutTimeline = new Timeline(
                new KeyFrame(Duration.millis(500),
                        new KeyValue(bgmPlayer.volumeProperty(), 0)));

        fadeOutTimeline.setOnFinished(ActionEvent1 -> {
            bgmPlayer.stop();
            bgmPlayer = new MediaPlayer(new Media(path.toUri().toString()));
            bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            bgmPlayer.setVolume(0);
            Timeline fadeInTimeline = new Timeline(
                    new KeyFrame(Duration.millis(500),
                            new KeyValue(bgmPlayer.volumeProperty(), bgmVolumePR.getValue())));
            fadeInTimeline.setOnFinished(ActionEvent2 -> {
                bgmPlayer.volumeProperty().bind(bgmVolumePR);
            });
            Platform.runLater(() -> {
                fadeInTimeline.play();
                bgmPlayer.play();
            });
        });
        Platform.runLater(fadeOutTimeline::play);
    }


    public ObjectProperty<Path> getChessSoundSource() {
        return chessSoundSourcePR;
    }

    public double getEffectVolume() {
        return effectVolumePR.getValue();
    }

    public void bindBGMVolumeTo(DoubleProperty volumePR) {
        this.bgmVolumePR.bind(volumePR);
    }

    public void unbindBGMVolume() {
        bgmVolumePR.unbind();
    }

    public void bindEffectVolumeTo(DoubleProperty volumePR) {
        this.effectVolumePR.bind(volumePR);
    }

    public void unbindEffectVolume() {
        effectVolumePR.unbind();
    }


    public void bindToBackPane(ObjectProperty<Background> background) {
        background.bind(Bindings.createObjectBinding(backPaneBackgroundPR::getValue, backPaneBackgroundPR));
    }

    public void bindBackPanePRTo(ObjectProperty<Background> background) {
        backPaneBackgroundPR.bind(Bindings.createObjectBinding(background::getValue, background));
    }

    public void unbindBackPane() {
        backPaneBackgroundPR.unbind();
    }

    public void bindToFrontPane(ObjectProperty<Background> background) {
        background.bind(Bindings.createObjectBinding(frontPaneBackgroundPR::getValue, frontPaneBackgroundPR));
    }

    public void bindFrontPanePRTo(ObjectProperty<Background> background) {
        frontPaneBackgroundPR.bind(Bindings.createObjectBinding(background::getValue, background));
    }

    public void unbindFrontPane() {
        frontPaneBackgroundPR.unbind();
    }

    public BooleanProperty modeSwitchPR() {
        return modeSwitchPR;
    }

    public ObjectProperty<Paint> modePaintPR() {
        return modePaintPR;
    }

    public ObjectProperty<Paint> modeRevPaintPR() {
        return modePaintPR();
    }

    public void bindToModePaint(ObjectProperty<Paint> paint) {
        paint.bind(Bindings.createObjectBinding(modePaintPR::getValue, modePaintPR));
    }

    public void bindToModePaintBackground(ObjectProperty<Background> background) {
        background.bind(Bindings.createObjectBinding(() -> {
            return new Background(new BackgroundFill(modePaintPR.getValue(), null, null));
        }, modePaintPR));
    }

    public void bindToModeRevPaint(ObjectProperty<Paint> paint) {
        paint.bind(Bindings.createObjectBinding(modeRevPaintPR::getValue, modePaintPR));
    }

    public void bindToModeRevPaintBackground(ObjectProperty<Background> background) {
        background.bind(Bindings.createObjectBinding(() -> {
            return new Background(new BackgroundFill(modeRevPaintPR.getValue(), null, null));
        }, modeRevPaintPR));
    }

    public ObjectProperty<Color> themeColorPR() {
        return themeColorPR;
    }

    public void bindToThemePaint(ObjectProperty<Paint> paint) {
        paint.bind(Bindings.createObjectBinding(themePaintPR::getValue, themePaintPR));
    }

    public void bindToPaintBackground(ObjectProperty<Background> background) {
        background.bind(Bindings.createObjectBinding(() -> {
            return new Background(new BackgroundFill(themePaintPR.getValue(), null, null));
        }, themePaintPR));
    }

    public void bindToTitleFontFamily(ObjectProperty<Font> fontFamily) {
        fontFamily.bind(Bindings.createObjectBinding(titleFontFamilyPR::getValue, titleFontFamilyPR));
    }

    public void bindToTitleFontPaint(ObjectProperty<Paint> fontPaint) {
        fontPaint.bind(Bindings.createObjectBinding(titleFontPaintPR::getValue, titleFontPaintPR));
    }

    public void bindToMenuFontFamily(ObjectProperty<Font> fontFamily) {
        fontFamily.bind(Bindings.createObjectBinding(menuFontFamilyPR::getValue, menuFontFamilyPR));
    }

    public void bindToMenuFontPaint(ObjectProperty<Paint> fontPaint) {
        fontPaint.bind(Bindings.createObjectBinding(menuFontPaintPR::getValue, menuFontPaintPR));
    }

    public void bindToTextFontFamily(ObjectProperty<Font> fontFamily) {
        fontFamily.bind(Bindings.createObjectBinding(textFontFamilyPR::getValue, textFontFamilyPR));
    }

    public void bindToTextFontPaint(ObjectProperty<Paint> fontPaint) {
        fontPaint.bind(Bindings.createObjectBinding(textFontPaintPR::getValue, textFontPaintPR));
    }

    public void bindToChessBoardPaint1(ObjectProperty<Background> background) {
        background.bind(Bindings.createObjectBinding(() -> {
            return new Background(new BackgroundFill(chessBoardPaintPR1.getValue(), null, null));
        }, chessBoardPaintPR1));
    }

    public void bindToChessBoardPaint2(ObjectProperty<Background> background) {
        background.bind(Bindings.createObjectBinding(() -> {
            return new Background(new BackgroundFill(chessBoardPaintPR2.getValue(), null, null));
        }, chessBoardPaintPR2));
    }

    public void bindToBorderPaint(ObjectProperty<Border> borderProperty) {
        borderProperty.bind(Bindings.createObjectBinding(() -> {
            return new Border(new BorderStroke(chessBoardGridPaintPR.getValue(), BorderStrokeStyle.SOLID, null, BorderWidths.DEFAULT));
        }, chessBoardGridPaintPR));
    }


    public void loadTheme() {
        try {
            loadTheme(Paths.get(Theme.class.getResource("theme.json").toURI()).toUri().toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log0j.writeLog("Error occurred because cannot found theme.json. No theme is changed.");
        }
    }

    public void loadTheme(String srcPath) {
        try {
            //Try to read the configuration file
            Log0j.writeLog("Loading theme file from the following path: " + srcPath);
            JSONObject jsonObject = new JSONObject(new File(srcPath));
            Log0j.writeLog("Theme loaded.");
        } catch (Exception e) {
            e.printStackTrace();
            Log0j.writeLog("Error occurred during converting source file to file stream. No theme is changed.");
        }
    }


    public void saveTheme() {

        Log0j.writeLog("Theme saved.");
    }

}
