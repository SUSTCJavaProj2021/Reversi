package controller;

import controller.output.OutputCategory;
import controller.output.OutputChannel;

public class DebugConsole {
    public static void writeToConsole(OutputChannel type, OutputCategory category, String message) {
        if (type == OutputChannel.STDOUT) {
            System.out.println("[" + category + "]: " + message);
        } else if (type == OutputChannel.STDERR) {
            System.out.println("[" + category + "]: " + message);
        }
    }
}
