package com.demo.reversi.view.contentpages;

import com.demo.reversi.MainApp;
import com.demo.reversi.component.MetroButton;
import com.demo.reversi.component.TitleLabel;
import com.demo.reversi.component.panes.SmoothishScrollPane;
import com.demo.reversi.component.switches.TitledToggleSwitch;
import com.demo.reversi.controller.local.SimpleGameSystem;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.res.lang.LiteralConstants;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Page that contains overall settings
 * <p>
 * Layout::
 * root
 * TitleLabel
 * scrollPane
 * contentWrapper
 * containers
 * SettingItems
 * configWrapper
 * discardButton
 * loadButton
 * saveButton
 * resetButton
 */
public class SettingsPage implements Updatable {
    public static final double ITEM_GAP = 20;
    public static final double PREF_CONTAINER_HEIGHT = 100;
    public final GridPane root;
    public final SmoothishScrollPane scrollPane;
    public final GridPane contentWrapper;
    public final GridPane configWrapper;

    public final MetroButton discardButton;
    public final MetroButton loadButton;
    public final MetroButton saveButton;
    public final MetroButton resetButton;

    public final SimpleGameSystem gameSystem;
    public final Theme theme;


    public SettingsPage(SimpleGameSystem gameSystem, Theme theme) {
        this.gameSystem = gameSystem;
        this.theme = theme;
        root = new GridPane();
        contentWrapper = new GridPane();
        scrollPane = new SmoothishScrollPane(contentWrapper);
        configWrapper = new GridPane();

        {
            {
                RowConstraints[] constraints = new RowConstraints[3];
                for (int i = 0; i < 3; i++) {
                    constraints[i] = new RowConstraints();
                    root.getRowConstraints().add(constraints[i]);
                }
                constraints[1].setVgrow(Priority.ALWAYS);
            }
            {
                ColumnConstraints[] constraints = new ColumnConstraints[3];
                for (int i = 0; i < 3; i++) {
                    constraints[i] = new ColumnConstraints();
                    constraints[i].setHgrow(Priority.ALWAYS);
                    root.getColumnConstraints().add(constraints[i]);
                }
            }
        }


        //Finally
        root.add(new TitleLabel(LiteralConstants.SettingsPageTitle.toString(), theme), 0, 0);
        root.add(scrollPane, 0, 1);
        root.add(configWrapper, 0, 2);

        //Test ColorPicker
        discardButton = new MetroButton("Discard Changes", theme);
        loadButton = new MetroButton("Load Theme", theme);
        saveButton = new MetroButton("Save Theme", theme);
        resetButton = new MetroButton("Reset Theme", theme);

        initContent();
        initConfig();

    }


    private void initConfig() {
        configWrapper.add(discardButton, 0, 0);
        configWrapper.add(loadButton, 1, 0);
        configWrapper.add(saveButton, 2, 0);
        configWrapper.add(resetButton, 3, 0);
        discardButton.setOnAction(ActionEvent -> {
            theme.applyDefaultTheme();
            refreshContent();
        });
        loadButton.setOnAction(ActionEvent ->
        {   //todo: add file picker
            theme.loadThemeFromFileExplorer();
            refreshContent();
        });
        saveButton.setOnAction(ActionEvent -> {
            theme.saveTheme();
        });
        resetButton.setOnAction(ActionEvent -> {
            refreshContent();
            theme.applyDefaultTheme();
        });
    }

    public void refreshContent() {
        contentWrapper.getChildren().clear();
        initContent();
    }

    /**
     * Init all the settings
     */
    private void initContent() {

        /*
         * Following is the list of the included items
         *
         * Colors Settings::
         * Dark Mode
         * Theme Color Picker
         * Player 1 Chess Color
         * Player 2 Chess Color
         * ChessBoard Color 1
         * ChessBoard Color 2
         * ChessBoard Grid Color
         *
         *
         * Background Settings::
         * Use Pure Color Background?
         * Background Image Source
         * ChessBoard Background Image Source
         *
         *
         * In-Game Settings::
         * PlayerIcon Source
         *
         *
         * Font Settings::
         * Title Font
         * Info Title Font
         * Menu Font
         * Text Font
         *
         *
         * Volume Settings::
         * BGM Volume Picker
         * Effect Volume Picker
         *
         *
         * Audio Settings::
         * MainView BGM Source
         * PlayPage BGM Source
         * GamePage BGM Source
         * GameFinish BGM Source
         * Chess Down Sound Source
         * Chess Up Sound Source
         * Grid Sound Source
         */

        {
            //Init Mode Color Settings
            TitledToggleSwitch toggleSwitch = new TitledToggleSwitch(theme, "Dark Mode Enabled", "Light Mode Enabled");
            toggleSwitch.switchedOnProperty().setValue(theme.modeSwitchPR().getValue());
            toggleSwitch.switchedOnProperty().addListener(((observable, oldValue, newValue) -> {
                theme.setDarkMode(newValue);
            }));

            contentWrapper.add(createItemContainer("Theme Color", toggleSwitch), 0, contentWrapper.getRowCount());

        }


        {
            //Init Theme ColorPicker

            ColorPicker cp = new ColorPicker(theme.themeColorPR().getValue());
            cp.valueProperty().addListener(((observable, oldValue, newValue) -> {
                theme.themeColorPR().setValue(newValue);
            }));

            contentWrapper.add(createItemContainer("Theme Color", cp), 0, contentWrapper.getRowCount());
        }

        {
            //Init Player 1 Chess Color

            ColorPicker cp = new ColorPicker(theme.player1ChessColorPR().getValue());
            cp.valueProperty().addListener(((observable, oldValue, newValue) -> {
                theme.player1ChessColorPR().setValue(newValue);
            }));

            contentWrapper.add(createItemContainer("Player 1 Chess Color", cp), 0, contentWrapper.getRowCount());
        }

        {
            //Init Player 2 Chess Color

            ColorPicker cp = new ColorPicker(theme.player2ChessColorPR().getValue());
            cp.valueProperty().addListener(((observable, oldValue, newValue) -> {
                theme.player2ChessColorPR().setValue(newValue);
            }));

            contentWrapper.add(createItemContainer("Player 2 Chess Color", cp), 0, contentWrapper.getRowCount());
        }

        {
            //Init Chess Board Color 1

            ColorPicker cp = new ColorPicker(theme.chessBoardColor1PR().getValue());
            cp.valueProperty().addListener(((observable, oldValue, newValue) -> {
                theme.chessBoardColor1PR().setValue(newValue);
            }));

            contentWrapper.add(createItemContainer("ChessBoard Color 1", cp), 0, contentWrapper.getRowCount());
        }

        {
            //Init Chess Board Color 2

            ColorPicker cp = new ColorPicker(theme.chessBoardColor2PR().getValue());
            cp.valueProperty().addListener(((observable, oldValue, newValue) -> {
                theme.chessBoardColor2PR().setValue(newValue);
            }));

            contentWrapper.add(createItemContainer("ChessBoard Color 2", cp), 0, contentWrapper.getRowCount());
        }

        {
            //Init Chess Board Grid Color

            ColorPicker cp = new ColorPicker(theme.chessBoardGridColorPR().getValue());
            cp.valueProperty().addListener(((observable, oldValue, newValue) -> {
                theme.chessBoardGridColorPR().setValue(newValue);
            }));

            contentWrapper.add(createItemContainer("Chess Grid Color", cp), 0, contentWrapper.getRowCount());
        }

        {
            //Init Background Image Loader
            MetroButton loadButton = new MetroButton("Load from File", theme);
            loadButton.setOnAction(ActionEvent -> {
                FileChooser fileChooser = createImageFileChooser("Select Background Image");
                File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());

                if (selectedFile != null) {
                    theme.backPanePR().setValue(new Background(new BackgroundImage(new Image(selectedFile.getPath()),
                            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                } else {
                    Log0j.writeError("Image file is null. Cannot load background image.");
                }
            });

            //Init Background Pure Color Settings
            TitledToggleSwitch toggleSwitch = new TitledToggleSwitch(theme, "Theme Color Background", "Image Background");
            toggleSwitch.switchedOnProperty().setValue(theme.modeSwitchPR().getValue());
            toggleSwitch.switchedOnProperty().addListener(((observable, oldValue, newValue) -> {
                if (newValue) {
                    theme.setBackPanePureColor(theme.themeColorPR().getValue());
                    loadButton.setDisable(true);
                } else {
                    theme.backPanePR().setValue(Theme.defaultBackPaneBKGND);
                    loadButton.setDisable(false);
                }
            }));

            contentWrapper.add(createItemContainer("Is background pure color?", toggleSwitch), 0, contentWrapper.getRowCount());
            contentWrapper.add(createItemContainer("Background Image Source", loadButton), 0, contentWrapper.getRowCount());
        }

        {
            //Init ChessBoard Image Loader
            MetroButton loadButton = new MetroButton("Load from File", theme);
            loadButton.setOnAction(ActionEvent -> {
                FileChooser fileChooser = createImageFileChooser("Select ChessBoard Background Image");

                File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());

                if (selectedFile != null) {
                    theme.chessBoardBackgroundPR().setValue(new Background(new BackgroundImage(new Image(selectedFile.getPath()),
                            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                } else {
                    Log0j.writeError("Image file is null. Cannot load ChessBoard image.");
                }
            });
            contentWrapper.add(createItemContainer("ChessBoard Image Source", loadButton), 0, contentWrapper.getRowCount());
        }

        {
            //Init PlayerIcon Image Loader
            MetroButton loadButton = new MetroButton("Load from File", theme);
            loadButton.setOnAction(ActionEvent -> {
                FileChooser fileChooser = createImageFileChooser("Select PlayerIcon Image");

                File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());

                if (selectedFile != null) {
                    theme.playerIconPR().setValue(new Image(selectedFile.getPath()));
                } else {
                    Log0j.writeError("Image file is null. Cannot load PlayerIcon image.");
                }
            });
            contentWrapper.add(createItemContainer("Player Icon Image Source", loadButton), 0, contentWrapper.getRowCount());
        }

        {
            
        }
    }

    public HBox createItemContainer(String text, Node node) {
        HBox container = createItemBaseContainer();
        Label label = createItemLabel(text);
        container.getChildren().addAll(label, node);
        return container;
    }

    public HBox createItemBaseContainer() {
        HBox container = new HBox(ITEM_GAP);
        container.setPrefHeight(PREF_CONTAINER_HEIGHT);
        return container;
    }

    public Label createItemLabel(String text) {
        Label label = new Label(text);
        label.fontProperty().bind(theme.textFontFamilyPR());
        label.textFillProperty().bind(theme.textFontPaintPR());
        return label;
    }

    @Override
    public void update() {

    }

    private FileChooser createImageFileChooser(String title){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);

        try {
            fileChooser.setInitialDirectory(new File(MainApp.class.getResource("themes/").toURI().toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image File",
                "*.bmp", "*.jpg", "*.jpeg", "*.png", "*.gif"));

        return fileChooser;
    }
}
