package com.demo.reversi.logger;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log0j {
    public static void writeInfo(String message) {

        //Trace back the caller class.
        String tmp = Thread.currentThread().getStackTrace()[2].getClassName();
        String className = tmp.substring(tmp.lastIndexOf((int)'.') + 1);

        System.out.printf("[%s] [%s/Info]: %s\n", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SS")), className, message);
    }

    public static void writeError(String message) {
        String tmp = Thread.currentThread().getStackTrace()[2].getClassName();
        String className = tmp.substring(tmp.lastIndexOf((int)'.') + 1);

        System.err.printf("[%s] [%s/Error]: %s\n", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SS")), className, message);
    }

    public static void writeCaution(String message) {
        String tmp = Thread.currentThread().getStackTrace()[2].getClassName();
        String className = tmp.substring(tmp.lastIndexOf((int)'.') + 1);

        System.err.printf("[%s] [%s/Caution]: %s\n", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SS")), className, message);
    }
}
