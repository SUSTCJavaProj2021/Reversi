package controller;

import controller.output.OutputCategory;
import controller.output.OutputChannel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log0j {
    public static void writeLog(String message) {
        String tmp = Thread.currentThread().getStackTrace()[2].getClassName();
        String className = tmp.substring(tmp.lastIndexOf((int)'.') + 1);
        System.out.printf("[%s] [%s]: %s\n", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SS")), className, message);
    }
}
