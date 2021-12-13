package controller;

import controller.output.OutputCategory;
import controller.output.OutputChannel;

public class Log0j {
    public static void writeLog(OutputChannel type, OutputCategory category, String message) {
        if (type == OutputChannel.STDOUT) {
            System.out.println("[" + category + "]: " + message);
        } else if (type == OutputChannel.STDERR) {
            System.err.println("[" + category + "]: " + message);
        }
    }
}
