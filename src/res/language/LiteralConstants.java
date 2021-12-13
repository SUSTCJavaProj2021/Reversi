package res.language;

public enum LiteralConstants {
    // Button Text
    PlayButtonText("Play"),
    StatisticsButtonText("Statistics"),
    SettingsButtonText("Settings"),
    SaveAndLoadButtonText("Save / Load"),
    ExitButtonText("Exit"),

    // TODO: PlayPage Text
    WelcomeText("Wow! Well played!"),
    PlayLocalText("Play Local Game"),
    BackButtonText("Back");

    private String literal;

    private LiteralConstants(String text) {
        this.literal = text;
    }

    public String getText() {
        return this.literal;
    }
}
