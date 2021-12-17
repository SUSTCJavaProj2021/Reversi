package com.demo.reversi.res.lang;

public enum LiteralConstants {
    // Button Text
    HomeButtonText("Home"),
    PlayButtonText("Play"),
    StatisticsButtonText("Statistics"),
    SettingsButtonText("Settings"),
    SaveAndLoadButtonText("Save / Load"),
    AboutButtonText("About"),
    ExitButtonText("Exit"),

    //todo: PlayPage Text
    WelcomeText("Welcome to Reversi!"),
    PlayLocalText("Play Local Game"),
    LoadGameText("Load Game from the Database"),
    BackButtonText("Back"),

    //todo: Title Text
    HomePageTitle("Home"),
    PlayPageTitle("Play"),
    StatisticsPageTitle("Statistics"),
    SettingsPageTitle("Settings"),
    SaveAndLoadPageTitle("Save And Load"),
    AboutPageTitle("About");

    private String literal;

    private LiteralConstants(String text) {
        this.literal = text;
    }

    @Override
    public String toString(){
        return this.literal;
    }
}
