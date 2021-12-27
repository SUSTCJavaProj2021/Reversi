package com.demo.reversi.save;

import java.io.File;
import java.io.IOException;

public class SaveLoader {
    protected static final String SAVE_PATH = "src/main/java/com/demo/reversi/save/";

    public static File getResource(String pathName) {
        File file = new File(SAVE_PATH + pathName);

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    public static File getDirectory(String pathName) {
        File file = new File(SAVE_PATH + pathName);

        file.mkdirs();

        return file;
    }
}
