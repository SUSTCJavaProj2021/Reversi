package res.language;

public enum LiteralConstants {
    // HomePage Text
    PlayButtonText("Play"),
    StatisticsButtonText("Statistics"),
    SettingsButtonText("Settings"),
    slButtonText("Save / Load"),
    WelcomeText("Wow! Well played!"),

    // TODO: PlayPage Text
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
