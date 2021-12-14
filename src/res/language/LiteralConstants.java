package res.language;

public enum LiteralConstants {
    // Button Text
    HomeButtonText("Home"),
    PlayButtonText("Play"),
    StatisticsButtonText("Statistics"),
    SettingsButtonText("Settings"),
    SaveAndLoadButtonText("Save / Load"),
    AboutButtonText("About"),
    ExitButtonText("Exit"),

    // TODO: PlayPage Text
    WelcomeText("We recommend you that you surrender immediately after 15 minutes."),
    PlayLocalText("Play Local Game"),
    LoadGameText("Load Game from the Database"),
    BackButtonText("Back");

    private String literal;

    private LiteralConstants(String text) {
        this.literal = text;
    }

    @Override
    public String toString(){
        return this.literal;
    }
}
