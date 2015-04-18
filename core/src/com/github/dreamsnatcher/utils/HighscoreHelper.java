package com.github.dreamsnatcher.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Created by lschmoli on 18.04.2015.
 */
public class HighscoreHelper {

    public static String readHighscore(String level) {
        String score = "No highscore yet";
        try {
            FileHandle fileHandle = Gdx.files.local(level + ".hsc");
            score = fileHandle.readString();
        } catch (GdxRuntimeException ex) {
            ex.printStackTrace();
        }
        return score;
    }

    public static void writeHighscore(long millis, String level) {
        FileHandle fileHandle = Gdx.files.local(level + ".hsc");
        fileHandle.writeString(String.valueOf(millis), false);
    }
}
