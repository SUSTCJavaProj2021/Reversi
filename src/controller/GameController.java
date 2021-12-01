package controller;

import controller.output.OutputCategory;
import controller.output.OutputChannel;

public class GameController {

    private CLIGameSystem sys;
    private boolean isGameModifiable;

    public GameController(boolean isGameModifiable) {
        this.isGameModifiable = isGameModifiable;
        setGameSystem(new CLIGameSystem());
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
        DebugConsole.writeToConsole(OutputChannel.STDOUT, OutputCategory.CLI_INFO,
                String.format("%s Clicked Grid (%d, %d)", getGameSystem().curPlayer.toString(), rowIndex, colIndex));
        getGameSystem().makeMove(rowIndex, colIndex);
    }

    public BlockStatus getBlockInfo(int rowIndex, int colIndex) {
        return getGameSystem().getBlockStatus(rowIndex, colIndex);
    }

    public CLIGameSystem getGameSystem() {
        return sys;
    }

    public void setGameSystem(CLIGameSystem sys) {
        this.sys = sys;
    }

}
