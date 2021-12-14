package controller;

public class GameSystem {
    public GameSystem(){

    }

    public void queryPlayerInfo(Player player){

    }

    public void addPlayer(){

    }

    public void delPlayer(){

    }

    public void getPlayerInfo(){

    }

    public GameController startNewGame(){
        return new GameController(true);
    }

    public GameController loadReadOnlyGame(int index){
        return new GameController(false);
    }

    public void updateRanking(){

    }

}
