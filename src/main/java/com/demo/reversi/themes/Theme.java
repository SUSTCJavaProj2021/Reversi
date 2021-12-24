package com.demo.reversi.themes;

import com.demo.reversi.logger.Log0j;
import com.demo.reversi.view.MainView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Stack;

/**
 * I happened to realize that this class could actually be completely static.
 * When I've got enough time, it is likely going to be refactored entirely.
 */
public class Theme {

    //Fields below are all default settings.
    //They are all base on experience.
    /**
     * Main-stage-related parameters
     */
    public static final double DEFAULT_CORNER_RADII = 10;

    public static final int DEFAULT_MAIN_WINDOW_PREF_WIDTH = 1280;
    public static final int DEFAULT_MAIN_WINDOW_PREF_HEIGHT = 800;
    public static final int DEFAULT_MAIN_WINDOW_MIN_WIDTH = 600;
    public static final int DEFAULT_MAIN_WINDOW_MIN_HEIGHT = 450;


    /**
     * In-game bgm sources
     */
    public static Path defaultMainViewBGMSource;
    public static Path defaultPlayPageBGMSource;
    public static Path defaultGamePageBGMSource;
    public static Path defaultGameFinishBGMSource;
    public static Path defaultTutorialBGMSource;    //You shouldn't be changing this!
    public static Path defaultChessDownSoundSource;
    public static Path defaultChessUpSoundSource;
    public static Path defaultGridSoundSource;

    static {
        try {
            defaultMainViewBGMSource = Paths.get(Theme.class.getResource("MainViewBGM.mp3").toURI());
            Log0j.writeInfo("Main View BGM loaded on path: " + defaultMainViewBGMSource);
        } catch (NullPointerException | URISyntaxException e) {
            e.printStackTrace();
            Log0j.writeError("Main View BGM loading failed. Check your path.");
        }

        try {
            defaultPlayPageBGMSource = Paths.get(Theme.class.getResource("PlayPageBGM.mp3").toURI());
            Log0j.writeInfo("Play Page BGM loaded on path: " + defaultGamePageBGMSource);
        } catch (NullPointerException | URISyntaxException e) {
            e.printStackTrace();
            Log0j.writeError("Play Page BGM loading failed. Check your path.");
        }

        try {
            defaultGamePageBGMSource = Paths.get(Theme.class.getResource("GamePageBGM.mp3").toURI());
            Log0j.writeInfo("Game Page BGM loaded on path: " + defaultGamePageBGMSource);
        } catch (NullPointerException | URISyntaxException e) {
            e.printStackTrace();
            Log0j.writeError("Game Page BGM loading failed. Check your path.");
        }

        try {
            defaultGameFinishBGMSource = Paths.get(Theme.class.getResource("GameFinishBGM.mp3").toURI());
            Log0j.writeInfo("Game Finish BGM loaded on path: " + defaultGameFinishBGMSource);
        } catch (NullPointerException | URISyntaxException e) {
            e.printStackTrace();
            Log0j.writeError("Game Finish BGM loading failed. Check your path.");
        }

        try {
            defaultTutorialBGMSource = Paths.get(Theme.class.getResource("TutorialBGM.mp3").toURI());
            Log0j.writeInfo("Tutorial BGM loaded on path: " + defaultTutorialBGMSource);
        } catch (NullPointerException | URISyntaxException e) {
            e.printStackTrace();
            Log0j.writeError("Tutorial BGM loading failed. Check your path.");
        }

        try {
            defaultChessDownSoundSource = Paths.get(Paths.get(Theme.class.getResource("soundeffects/").toURI()).toString(), "Chess_Down.mp3");
            Log0j.writeInfo("Chess down sound loaded on path : " + defaultChessDownSoundSource);
        } catch (NullPointerException | URISyntaxException e) {
            e.printStackTrace();
            Log0j.writeError("Chess down sound loading failed. Check your path.");
        }

        try {
            defaultChessUpSoundSource = Paths.get(Paths.get(Theme.class.getResource("soundeffects/").toURI()).toString(), "Chess_Up.mp3");
            Log0j.writeInfo("Chess up sound loaded on path : " + defaultChessUpSoundSource);
        } catch (NullPointerException | URISyntaxException e) {
            e.printStackTrace();
            Log0j.writeError("Chess up sound loading failed. Check your path.");
        }

        try {
            defaultGridSoundSource = Paths.get(Paths.get(Theme.class.getResource("soundeffects/").toURI()).toString(), "Grid.mp3");
            Log0j.writeInfo("Grid sound loaded on path : " + defaultGridSoundSource);
        } catch (NullPointerException | URISyntaxException e) {
            e.printStackTrace();
            Log0j.writeError("Grid sound loading failed. Check your path.");
        }
    }

    /**
     * In-game bgm volumes
     */
    public static final double defaultBGMVolume = 0.4;
    public static final double defaultEffectVolume = 1.0;


    /**
     * Main-View background
     */
    public static Path defaultBackgroundSource;

    static {
        try {
            defaultBackgroundSource = Paths.get(Theme.class.getResource("Background.jpg").toURI());
            Log0j.writeInfo("Correctly loaded default background source from " + defaultBackgroundSource.toUri().toString());
        } catch (NullPointerException | URISyntaxException e) {
            e.printStackTrace();
            Log0j.writeError("Default background loading failed. Check your path.");
        }
    }

    public static final Background defaultBackPaneBKGND = new Background(
            new BackgroundImage(new Image(defaultBackgroundSource.toUri().toString()),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT));
    /**
     * Main-View FrontPane background
     */
    public static final Background defaultFrontPaneBKGND = new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.15),
            new CornerRadii(MainView.VIEWCOVER_CORNER_RADII), null));

    /**
     * Custom chess (currently not used)
     */
    public static Path defaultChessSource;

    static {
        try {
            defaultChessSource = Paths.get(Theme.class.getResource("Chess.png").toURI());
        } catch (NullPointerException | URISyntaxException e) {
            e.printStackTrace();
            Log0j.writeError("Failed to load default chess component. Chess will be initialized using circles.");
        }
    }


    /**
     * Mode colors
     */
    public static final Color defaultDarkModeColor = Color.rgb(32, 32, 32);
    public static final Color defaultLightModeColor = Color.rgb(240, 240, 240);

    /**
     * Theme colors & paints
     * Theme Paint is by default bound to Theme Color
     */
    public static final Color defaultThemeColor = Color.rgb(29, 31, 44);
    public static final Paint defaultThemePaint = Color.rgb(29, 31, 44);

    /**
     * Fonts: Title, Menu, Text
     */
    public static final Font defaultTitleFontFamily = new Font("Garamond", 25);
    public static final Paint defaultTitleFontPaint = Color.WHITE;
    public static final Font defaultInfoTitleFontFamily = new Font("Garamond", 24);
    public static final Paint defaultInfoTitleFontPaint = Color.WHITE;
    public static final Font defaultMenuFontFamily = new Font("Segoe UI", 14);
    public static final Paint defaultMenuFontPaint = Color.WHITE;
    public static final Font defaultTextFontFamily = new Font("Segoe UI Light", 16);
    public static final Paint defaultTextFontPaint = Color.WHITE;

    /**
     * In-game settings: Chess Paint, ChessBoard Grid Paint, etc.
     */
    public static final Color defaultPlayerChessColor1 = Color.BLACK;
    public static final Color defaultPlayerChessColor2 = Color.WHITE;
    public static final Paint defaultChessBoardPaint1 = Color.rgb(29, 31, 44);
    public static final Paint defaultChessBoardPaint2 = Color.rgb(55, 58, 84);
    public static final Paint defaultChessBoardGridPaint = Color.rgb(255, 255, 255, 0.50);
    public static final Background defaultChessBoardBackground = new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(DEFAULT_CORNER_RADII), null));

    /**
     * Players
     */
    public static Image defaultPlayerIcon;

    static {
        try {
            defaultPlayerIcon = new Image(Theme.class.getResource("PlayerIcon.png").toURI().toString());
        } catch (NullPointerException | URISyntaxException e) {
            e.printStackTrace();
            Log0j.writeError("Cannot load default player icon.");
        }
    }

    /**
     * App's Icon
     */
    public static Image appIcon;

    static {
        try {
            appIcon = new Image(Theme.class.getResource("icons/App.png").toURI().toString());
        } catch (NullPointerException | URISyntaxException e) {
            e.printStackTrace();
        }
    }


    /**
     * Below are properties that will be used for real-time previews
     */
    //Stage related
    public final SimpleDoubleProperty mainWindowPrefWidth;
    public final SimpleDoubleProperty mainWindowPrefHeight;
//    public int mainWindowMinWidth;
//    public int mainWindowMinHeight;


    //Audio related
    public MediaPlayer bgmPlayer;
    public Stack<MediaPlayer> bgmStack;
    public final ObjectProperty<Path> mainViewBGMSourcePR;
    public final ObjectProperty<Path> playPageBGMSourcePR;
    public final ObjectProperty<Path> gamePageBGMSourcePR;
    public final ObjectProperty<Path> gameFinishBGMSourcePR;
    private int gameCnt;
    public final ObjectProperty<Path> chessDownSoundSourcePR;
    public final ObjectProperty<Path> chessUpSoundSourcePR;
    public final ObjectProperty<Path> gridSoundSourcePR;
    public final DoubleProperty bgmVolumePR;
    public final DoubleProperty effectVolumePR;

    //Audio File Related
    public final BooleanProperty bgmSourceMoved;


    //Theme related
    public final ObjectProperty<Background> backPaneBackgroundPR;
    public final ObjectProperty<Background> frontPaneBackgroundPR;
//    public final BooleanProperty isBackgroundPureColor;

    /**
     * If <code>modeSwitchPR</code> is set to true, then dark mode is applied.
     */
    public final BooleanProperty modeSwitchPR;
    public final ObjectProperty<Color> modeColorPR;
    //This property is for getting the reversed color of the mode.
    public final ObjectProperty<Color> modeRevColorPR;

    public final ObjectProperty<Color> themeColorPR;
    public final ObjectProperty<Paint> themePaintPR;

    public final ObjectProperty<Font> titleFontFamilyPR;
    public final ObjectProperty<Paint> titleFontPaintPR;

    public final ObjectProperty<Font> infoTitleFontFamilyPR;
    public final ObjectProperty<Paint> infoTitleFontPaintPR;

    public final ObjectProperty<Font> menuFontFamilyPR;
    public final ObjectProperty<Paint> menuFontPaintPR;

    public final ObjectProperty<Font> textFontFamilyPR;
    public final ObjectProperty<Paint> textFontPaintPR;

    //Chessboard Color
    public final ObjectProperty<Color> player1ChessColorPR;
    public final ObjectProperty<Color> player2ChessColorPR;
    public final ObjectProperty<Paint> chessBoardPaintPR1;
    public final ObjectProperty<Paint> chessBoardPaintPR2;
    public final ObjectProperty<Paint> chessBoardGridPaintPR;
    public final ObjectProperty<Background> chessBoardBackgroundPR;

    public final ObjectProperty<Image> playerIconPR;


    public final Stage primaryStage;

    //todo: finish chessboard paint default
    //Default Theme
    public Theme(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mainWindowPrefWidth = new SimpleDoubleProperty();
        mainWindowPrefHeight = new SimpleDoubleProperty();

        bgmStack = new Stack<>();
        bgmVolumePR = new SimpleDoubleProperty();
        mainViewBGMSourcePR = new SimpleObjectProperty<>();
        playPageBGMSourcePR = new SimpleObjectProperty<>();
        gamePageBGMSourcePR = new SimpleObjectProperty<>();
        gameFinishBGMSourcePR = new SimpleObjectProperty<>();
        gameCnt = 0;
        chessDownSoundSourcePR = new SimpleObjectProperty<>();
        chessUpSoundSourcePR = new SimpleObjectProperty<>();
        gridSoundSourcePR = new SimpleObjectProperty<>();
        effectVolumePR = new SimpleDoubleProperty();

        bgmSourceMoved = new SimpleBooleanProperty();

        backPaneBackgroundPR = new SimpleObjectProperty<>();
        frontPaneBackgroundPR = new SimpleObjectProperty<>();

        modeSwitchPR = new SimpleBooleanProperty();
        modeColorPR = new SimpleObjectProperty<>();
        modeRevColorPR = new SimpleObjectProperty<>();

        themeColorPR = new SimpleObjectProperty<>();
        themePaintPR = new SimpleObjectProperty<>();
        titleFontFamilyPR = new SimpleObjectProperty<>();
        titleFontPaintPR = new SimpleObjectProperty<>();
        infoTitleFontFamilyPR = new SimpleObjectProperty<>();
        infoTitleFontPaintPR = new SimpleObjectProperty<>();
        menuFontFamilyPR = new SimpleObjectProperty<>();
        menuFontPaintPR = new SimpleObjectProperty<>();
        textFontFamilyPR = new SimpleObjectProperty<>();
        textFontPaintPR = new SimpleObjectProperty<>();

        player1ChessColorPR = new SimpleObjectProperty<>();
        player2ChessColorPR = new SimpleObjectProperty<>();
        chessBoardPaintPR1 = new SimpleObjectProperty<>();
        chessBoardPaintPR2 = new SimpleObjectProperty<>();
        chessBoardGridPaintPR = new SimpleObjectProperty<>();
        chessBoardBackgroundPR = new SimpleObjectProperty<>();

        playerIconPR = new SimpleObjectProperty<>();

        applyDefaultTheme();
        initRelations();
        Log0j.writeInfo("Theme initialized.");

        //Audio Applied.

        loadTheme();

    }

    public void initMedia() {
        bgmStack.clear();
        if (bgmPlayer != null) {
            bgmPlayer.stop();
        }
        try {
            Media media;
            //todo: modify this
            if (!bgmSourceMoved.getValue()) {
                media = new Media(mainViewBGMSourcePR.getValue().toUri().toString());
            } else {
                Log0j.writeInfo("Default BGM Source initialized on path: " + defaultMainViewBGMSource.toUri().toString());
                media = new Media(defaultMainViewBGMSource.toUri().toString());
            }
            bgmPlayer = new MediaPlayer(media);
            bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            bgmPlayer.volumeProperty().bind(bgmVolumePR);
            bgmPlayer.play();
            Log0j.writeInfo("BGM Player Initialized.");

        } catch (NullPointerException e) {
            e.printStackTrace();
            Log0j.writeInfo("Failed to initialize BGM.");
        }
    }

    public void applyDefaultTheme() {
        removeRelations();
        primaryStage.setWidth(DEFAULT_MAIN_WINDOW_PREF_WIDTH);
        primaryStage.setHeight(DEFAULT_MAIN_WINDOW_PREF_HEIGHT);

        bgmVolumePR.setValue(defaultBGMVolume);
        effectVolumePR.setValue(defaultEffectVolume);

        mainViewBGMSourcePR.setValue(defaultMainViewBGMSource);
        playPageBGMSourcePR.setValue(defaultPlayPageBGMSource);
        gamePageBGMSourcePR.setValue(defaultGamePageBGMSource);
        gameFinishBGMSourcePR.setValue(defaultGameFinishBGMSource);
        chessDownSoundSourcePR.setValue(defaultChessDownSoundSource);
        chessUpSoundSourcePR.setValue(defaultChessUpSoundSource);
        gridSoundSourcePR.setValue(defaultGridSoundSource);

        bgmSourceMoved.setValue(false);

        backPaneBackgroundPR.setValue(defaultBackPaneBKGND);
        frontPaneBackgroundPR.setValue(defaultFrontPaneBKGND);

        modeSwitchPR.setValue(true);
        modeColorPR.setValue(defaultDarkModeColor);
        modeRevColorPR.setValue(defaultLightModeColor);

        themeColorPR.setValue(defaultThemeColor);
        themePaintPR.setValue(defaultThemePaint);

        titleFontFamilyPR.setValue(defaultTitleFontFamily);
        titleFontPaintPR.setValue(defaultTitleFontPaint);
        infoTitleFontFamilyPR.setValue(defaultInfoTitleFontFamily);
        infoTitleFontPaintPR.setValue(defaultInfoTitleFontPaint);
        menuFontFamilyPR.setValue(defaultMenuFontFamily);
        menuFontPaintPR.setValue(defaultMenuFontPaint);
        textFontFamilyPR.setValue(defaultTextFontFamily);
        textFontPaintPR.setValue(defaultTextFontPaint);

        player1ChessColorPR.setValue(defaultPlayerChessColor1);
        player2ChessColorPR.setValue(defaultPlayerChessColor2);
        chessBoardPaintPR1.setValue(defaultChessBoardPaint1);
        chessBoardPaintPR2.setValue(defaultChessBoardPaint2);
        chessBoardGridPaintPR.setValue(defaultChessBoardGridPaint);
        chessBoardBackgroundPR.setValue(defaultChessBoardBackground);

        playerIconPR.setValue(defaultPlayerIcon);

        initMedia();
        initRelations();
        Log0j.writeInfo("Default Theme Applied.");

    }

    public void removeRelations() {
        //Bind mode switch to dynamically change color.
        modeColorPR.unbind();

        modeRevColorPR.unbind();

        titleFontPaintPR.unbind();
        infoTitleFontPaintPR.unbind();
        menuFontPaintPR.unbind();
        textFontPaintPR.unbind();

        //Bind theme paint to theme color
        themePaintPR.unbind();

        Log0j.writeInfo("Relation removed.");
    }

    public void initRelations() {

        //Bind mode switch to dynamically change color.
        modeColorPR.bind(Bindings.createObjectBinding(() -> {
            if (modeSwitchPR.getValue()) {
                return defaultDarkModeColor;
            } else {
                return defaultLightModeColor;
            }
        }, modeSwitchPR));

        modeRevColorPR.bind(Bindings.createObjectBinding(() -> {
            if (modeSwitchPR.getValue()) {
                return defaultLightModeColor;
            } else {
                return defaultDarkModeColor;
            }
        }, modeSwitchPR));

        titleFontPaintPR.bind(modeRevColorPR);
        infoTitleFontPaintPR.bind(modeRevColorPR);
        menuFontPaintPR.bind(modeRevColorPR);
        textFontPaintPR.bind(modeRevColorPR);


        //Bind theme paint to theme color
        themePaintPR.bind(Bindings.createObjectBinding(themeColorPR::getValue, themeColorPR));


        Log0j.writeInfo("Relation initialized.");
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

    public int getGameCnt() {
        return gameCnt;
    }

    public void registerGameBGM() {
        if (gameCnt == 0) {
            Platform.runLater(() -> {
                bgmPlayerInterrupt(gamePageBGMSourcePR.getValue(), 400);
            });
        }
        gameCnt++;
    }

    public void unregisterGameBGM() {
        gameCnt--;
        if (gameCnt == 0) {
            Platform.runLater(() -> {
                bgmPlayerResumeFromInterrupt(100);
            });
        }
    }

    public void registerPlayPageBGM() {
        if (gameCnt == 0) {
            Platform.runLater(() -> {
                bgmPlayerInterrupt(playPageBGMSourcePR.getValue(), 100);
            });
        }
    }

    public void unregisterPlayPageBGM() {
        if (gameCnt == 0) {
            Platform.runLater(() -> {
                bgmPlayerResumeFromInterrupt(100);
            });
        }
    }

    public void bgmPlayerPause() {
        bgmPlayer.pause();
    }

    public void bgmPlayerResume() {
        fadeInBGM(bgmPlayer);
    }

    public void bgmPlayerInterrupt(Path BGMSource, long delayDurationMillis) {
        Log0j.writeInfo("Calling bgm player interrupt.");
        //First unbind all connections
        if (bgmPlayer != null) {
            bgmPlayer.volumeProperty().unbind();
            bgmPlayer.pause();
            bgmStack.push(bgmPlayer);
        }


        bgmPlayer = new MediaPlayer(new Media(BGMSource.toUri().toString()));
        bgmPlayer.setVolume(bgmVolumePR.getValue());
        bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        try {
            Thread.sleep(delayDurationMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fadeInBGM(bgmPlayer);
    }

    /**
     * You must first call bgmPlayerInterrupt().
     */
    public void bgmPlayerResumeFromInterrupt(long delayDurationMillis) {
        if (bgmStack.isEmpty()) {
            Log0j.writeInfo("Invalid bgm resume call.");
        } else {
            Log0j.writeInfo("Resuming bgm player to its previous state.");
            Platform.runLater(bgmPlayer.volumeProperty()::unbind);

            Timeline fadeOutTimeline = new Timeline(
                    new KeyFrame(Duration.millis(100),
                            new KeyValue(bgmPlayer.volumeProperty(), 0)));

            fadeOutTimeline.setOnFinished(ActionEvent1 -> {
                bgmPlayer.pause();
                try {
                    Thread.sleep(delayDurationMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log0j.writeInfo("BGM delaying failed. Normally this shouldn't happen.");
                }
                bgmPlayer = bgmStack.pop();
                bgmPlayer.setVolume(0);
                fadeInBGM(bgmPlayer);
            });

            Platform.runLater(fadeOutTimeline::play);
        }
    }

    public ObjectProperty<Path> gameFinishBGMSourcePR() {
        return gameFinishBGMSourcePR;
    }

    /**
     * Load the specific music to the player
     *
     * @param path music path
     */
    public void setBGMPlayerContent(Path path, long delayDurationMillis) {
        //Unbind the previous bgm player volume first.
        Platform.runLater(bgmPlayer.volumeProperty()::unbind);

        Timeline fadeOutTimeline = new Timeline(
                new KeyFrame(Duration.millis(100),
                        new KeyValue(bgmPlayer.volumeProperty(), 0)));

        fadeOutTimeline.setOnFinished(ActionEvent1 -> {
            bgmPlayer.stop();
            try {
                Thread.sleep(delayDurationMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log0j.writeInfo("BGM delaying failed. Normally this shouldn't happen.");
            }
            bgmPlayer = new MediaPlayer(new Media(path.toUri().toString()));
            bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            bgmPlayer.setVolume(0);
            fadeInBGM(bgmPlayer);
        });
        Platform.runLater(fadeOutTimeline::play);
    }

    public void fadeInBGM(MediaPlayer bgmPlayer) {
        Timeline fadeInTimeline = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new KeyValue(bgmPlayer.volumeProperty(), bgmVolumePR.getValue())));
        fadeInTimeline.setOnFinished(ActionEvent2 -> {
            bgmPlayer.volumeProperty().bind(bgmVolumePR);
        });
        Platform.runLater(() -> {
            fadeInTimeline.play();
            bgmPlayer.play();
        });
    }


    public ObjectProperty<Path> chessDownSoundSourcePR() {
        return chessDownSoundSourcePR;
    }

    public ObjectProperty<Path> chessUpSoundSourcePR() {
        return chessUpSoundSourcePR;
    }

    public ObjectProperty<Path> gridSoundSourcePR() {
        return gridSoundSourcePR;
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

    public ObjectProperty<Background> backPanePR() {
        return backPaneBackgroundPR;
    }

    public void bindBackPanePRTo(ObjectProperty<Background> background) {
        backPaneBackgroundPR.bind(background);
    }

    public void unbindBackPane() {
        backPaneBackgroundPR.unbind();
    }

    public ObjectProperty<Background> frontPanePR() {
        return frontPaneBackgroundPR;
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

    public ObjectProperty<Color> modePaintPR() {
        return modeColorPR;
    }

    public ObjectProperty<Color> modeRevPaintPR() {
        return modeRevColorPR;
    }

    public void bindToModePaintBackground(ObjectProperty<Background> background) {
        background.bind(Bindings.createObjectBinding(() -> {
            return new Background(new BackgroundFill(modeColorPR.getValue(), null, null));
        }, modeSwitchPR));
    }

    public void bindToModeRevPaintBackground(ObjectProperty<Background> background) {
        background.bind(Bindings.createObjectBinding(() -> {
            return new Background(new BackgroundFill(modeRevColorPR.getValue(), null, null));
        }, modeSwitchPR));
    }

    public ObjectProperty<Color> themeColorPR() {
        return themeColorPR;
    }

    public ObjectProperty<Paint> themePaintPR() {
        return themePaintPR;
    }

    public void bindToPaintBackground(ObjectProperty<Background> background) {
        background.bind(Bindings.createObjectBinding(() -> {
            return new Background(new BackgroundFill(themePaintPR.getValue(), null, null));
        }, themePaintPR));
    }

    public ObjectProperty<Font> titleFontFamilyPR() {
        return titleFontFamilyPR;
    }

    public ObjectProperty<Paint> titleFontPaintPR() {
        return titleFontPaintPR;
    }


    public ObjectProperty<Font> infoTitleFontFamilyPR() {
        return infoTitleFontFamilyPR;
    }

    public ObjectProperty<Paint> infoTitleFontPaintPR() {
        return infoTitleFontPaintPR;
    }

    public ObjectProperty<Font> menuFontFamilyPR() {
        return menuFontFamilyPR;
    }

    public ObjectProperty<Paint> menuFontPaintPR() {
        return menuFontPaintPR;
    }

    public ObjectProperty<Font> textFontFamilyPR() {
        return textFontFamilyPR;
    }


    public ObjectProperty<Paint> textFontPaintPR() {
        return textFontPaintPR;
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

    public ObjectProperty<Image> playerIconPR() {
        return playerIconPR;
    }

    public ObjectProperty<Color> player1ChessColorPR() {
        return player1ChessColorPR;
    }

    public ObjectProperty<Color> player2ChessColorPR() {
        return player2ChessColorPR;
    }


    public void loadTheme() {
        try {
            loadTheme(new File(Theme.class.getResource("theme.json").toURI()));
            Log0j.writeInfo("Theme.json loaded.");
        } catch (URISyntaxException | NullPointerException e) {
            e.printStackTrace();
            Log0j.writeError("Error occurred because cannot found theme.json. No theme is changed.");
        }
    }

    public void loadThemeFromFileExplorer() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load theme file from File Explorer");
//            fileChooser.setInitialDirectory(new File(Theme.class.getResource("theme.json").toURI().toString()).getParentFile());
            fileChooser.getExtensionFilters().addAll(
                    new ExtensionFilter("Theme Config", "*.json"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            loadTheme(selectedFile);
            Log0j.writeInfo("Trying to load them from external environment.");
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log0j.writeError("Error occurred because cannot found theme.json. No theme is changed.");
        }
    }

    private void loadTheme(File file) {
        try {
            //Try to read the configuration file
            Log0j.writeInfo("Loading theme file from the following path: " + file.getPath());
            //todo: add methods
            JSONObject jsonObject = new JSONObject(file);
            Log0j.writeInfo("Theme loaded.");
        } catch (NullPointerException | JSONException e) {
            e.printStackTrace();
            Log0j.writeError("Error occurred during converting source file to file stream. No theme is changed.");
        }
    }


    public void saveTheme() {

        Log0j.writeInfo("Theme saved.");
    }

}
