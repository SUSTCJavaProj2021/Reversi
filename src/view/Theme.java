package view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class Theme {

    //Overall Theme related
    public ObjectProperty<Background> backPaneBackgroundPR;
    public ObjectProperty<Background> frontPaneBackgroundPR;

    public ObjectProperty<Color> themeColorPR;
    public ObjectProperty<Paint> themePaintPR;
    public ObjectProperty<Paint> titleFontPaintPR;
    public ObjectProperty<Font> titleFontFamilyPR;
    public ObjectProperty<Paint> textFontPaintPR;
    public ObjectProperty<Font> textFontFamilyPR;
    //Audio related
    public DoubleProperty volumePR;

    //Chessboard Color
    public ObjectProperty<Paint> playerChessPaintPR1;
    public ObjectProperty<Paint> playerChessPaintPR2;
    public ObjectProperty<Paint> chessGridPaintPR1;
    public ObjectProperty<Paint> chessGridPaintPR2;
    public ObjectProperty<Background> chessBoardBackgroundPR;



    public static final Background defaultBackPaneBKGND = new Background(new BackgroundImage(new Image("/res/background.jpg"),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT));

    public static final Background defaultFrontPaneBKGND = new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.15),
            new CornerRadii(MainView.VIEWCOVER_CORNER_RADII), null));

    public static final Color defaultThemeColor = Color.rgb(29, 31, 44);
    public static final Paint defaultThemePaint = Color.rgb(29, 31, 44);
    public static final Paint defaultTitleFontPaint = Color.WHITE;
    public static final Font defaultTitleFontFamily = new Font("Constantia", 25);
    public static final Paint defaultTextFontPaint = Color.WHITE;
    public static final Font defaultTextFontFamily = new Font("Segoe UI", 16);

    //todo: finish chessboard paint default
    //Default Theme
    public Theme() {
        backPaneBackgroundPR = new SimpleObjectProperty<>(this, "backPaneBackground", defaultBackPaneBKGND);
        frontPaneBackgroundPR = new SimpleObjectProperty<>(this, "frontPaneBackground", defaultFrontPaneBKGND);
        themeColorPR = new SimpleObjectProperty<>(this, "themeColor", defaultThemeColor);
        themePaintPR = new SimpleObjectProperty<>(this, "themePaint", defaultThemePaint);
        titleFontFamilyPR = new SimpleObjectProperty<>(this, "titleFontFamily", defaultTitleFontFamily);
        titleFontPaintPR = new SimpleObjectProperty<>(this, "titleFontPaint", defaultTitleFontPaint);
        textFontFamilyPR = new SimpleObjectProperty<>(this, "textFontFamily", defaultTextFontFamily);
        textFontPaintPR = new SimpleObjectProperty<>(this, "textFontPaint", defaultTextFontPaint);
        initRelations();
    }

    public void initRelations() {
        themePaintPR.bind(Bindings.createObjectBinding(() -> {
            return themeColorPR.getValue();
        }, themeColorPR));
    }

    public void bindBackPane(ObjectProperty<Background> background) {
        background.bind(Bindings.createObjectBinding(() -> {
            Background newBackground = backPaneBackgroundPR.getValue();
            return newBackground;
        }, backPaneBackgroundPR));
    }

    public void bindBackPaneTo(ObjectProperty<Background> background) {
        backPaneBackgroundPR.bind(Bindings.createObjectBinding(() -> {
            Background newBackground = background.getValue();
            return newBackground;
        }, background));
    }

    public void unbindBackPane(){
        backPaneBackgroundPR.unbind();
    }

    public void bindFrontPane(ObjectProperty<Background> background) {
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

    public void unbindFrontPane(){
        frontPaneBackgroundPR.unbind();
    }


    public void bindPaint(ObjectProperty<Paint> paint) {
        paint.bind(Bindings.createObjectBinding(() -> {
            Paint newPaint = themePaintPR.getValue();
            return newPaint;
        }, themePaintPR));
    }

    public void bindPaintBackground(ObjectProperty<Background> background) {
        background.bind(Bindings.createObjectBinding(() -> {
            Background newBackground = new Background(new BackgroundFill(themePaintPR.getValue(), null, null));
            return newBackground;
        }, themePaintPR));
    }

    public void bindTitleFontPaint(ObjectProperty<Paint> fontPaint) {
        fontPaint.bind(Bindings.createObjectBinding(() -> {
            return titleFontPaintPR.getValue();
        }, titleFontPaintPR));
    }

    public void bindTitleFontFamily(ObjectProperty<Font> fontFamily) {
        fontFamily.bind(Bindings.createObjectBinding(() -> {
            return titleFontFamilyPR.getValue();
        }, titleFontFamilyPR));
    }

    public void bindTextFontPaint(ObjectProperty<Paint> fontPaint) {
        fontPaint.bind(Bindings.createObjectBinding(() -> {
            return textFontPaintPR.getValue();
        }, textFontPaintPR));
    }

    public void bindTextFontFamily(ObjectProperty<Font> fontFamily) {
        fontFamily.bind(Bindings.createObjectBinding(() -> {
            return textFontFamilyPR.getValue();
        }, textFontFamilyPR));
    }

    public void loadTheme(){

    }
}
