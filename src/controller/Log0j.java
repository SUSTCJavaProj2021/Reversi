package controller;

import controller.output.OutputCategory;
import controller.output.OutputChannel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log0j {
    public static void writeLog(String className, String message) {
        System.out.printf("[%s] [%s]: %s\n", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS")), className, message);
    }
}
