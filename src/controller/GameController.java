package controller;

public class GameController {

    private CLIGameController sys;
    private boolean isGameModifiable;

    public GameController(boolean isGameModifiable) {
        this.isGameModifiable = isGameModifiable;
        setGameSystem(new CLIGameController());
    }

    public int getRowSize() {
        return getGameSystem().rowSize;
    }

    public int getColSize() {
        return getGameSystem().colSize;
    }

    public boolean getModifiability() {
        return this.isGameModifiable;
    }


    public void onGridClick(int rowIndex, int colIndex) {
        Log0j.writeLog(getClass().toString(),
                String.format("%s Clicked Grid (%d, %d)", getGameSystem().curPlayer.toString(), rowIndex, colIndex));
        getGameSystem().makeMove(rowIndex, colIndex);
    }

    public BlockStatus getBlockInfo(int rowIndex, int colIndex) {
        return getGameSystem().getBlockStatus(rowIndex, colIndex);
    }

    public CLIGameController getGameSystem() {
        return sys;
    }

    public void setGameSystem(CLIGameController sys) {
        this.sys = sys;
    }

}
