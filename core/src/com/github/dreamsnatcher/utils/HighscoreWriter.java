package com.github.dreamsnatcher.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by lschmoli on 18.04.2015.
 */
public class HighscoreWriter {

    public static String readHighscore(String level) {
        FileHandle fileHandle = Gdx.files.internal("levels/" + level + ".hsc");
        return fileHandle.readString();
    }

    public void writeHighscore(long millis, String level) {
        FileHandle fileHandle = Gdx.files.internal("levels/" + level + ".hsc");
        fileHandle.writeString(String.valueOf(millis), false);
    }
}
