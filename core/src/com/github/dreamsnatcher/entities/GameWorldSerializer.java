package com.github.dreamsnatcher.entities;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;

import java.io.*;

/**
 * Takes a GameWorld and serializes it to a json file
 */
public class GameWorldSerializer {
    public static void serialize(GameWorld world, File file) {
        Json json = new Json();
        String output = json.toJson(world);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(json.prettyPrint(output));
            writer.close();
        } catch (IOException e) {
            throw new GdxRuntimeException("Couldn't write file '" + file + "'", e);
        }
    }

    public static GameWorld deserialize(FileHandle file) {
        Json json = new Json();
        return json.fromJson(GameWorld.class, file.readString());
    }
}
