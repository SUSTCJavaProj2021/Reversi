package controller;

import controller.GameStatus;
import controller.PlayerConstants;
import controller.CLIGameSystem;
import controller.PlayerConstants;
import controller.DebugConsole;
import controller.output.OutputCategory;
import controller.output.OutputChannel;
import javafx.beans.property.StringProperty;

public class GameController {

    public CLIGameSystem sys;
    private boolean isGameModifiable;

    public GameController(boolean isGameModifiable) {
        this.isGameModifiable = isGameModifiable;
        sys = new CLIGameSystem();
    }

    public int getRowSize() {
        return sys.rowSize;
    }

    public int getColSize() {
        return sys.colSize;
    }

    public boolean getModifiability() {
        return this.isGameModifiable;
    }


    public void onGridClick(int rowIndex, int colIndex) {
        DebugConsole.writeToConsole(OutputChannel.STDOUT, OutputCategory.CLI_INFO,
                String.format("%s Clicked Grid (%d, %d)", sys.curPlayer.toString(), rowIndex, colIndex));
        sys.makeMove(rowIndex, colIndex);
    }

    public BlockStatus getBlockInfo(int rowIndex, int colIndex) {
        return sys.getBlockStatus(rowIndex, colIndex);
    }
}
