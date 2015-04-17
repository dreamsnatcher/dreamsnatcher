package com.github.dreamsnatcher.entities;

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
            writer.write(output);
            writer.close();
        } catch (IOException e) {
            throw new GdxRuntimeException("Couldn't write file '" + file + "'", e);
        }
    }

    public static GameWorld deserialize(File file) {
        Json json = new Json();

        try {
            FileReader reader = new FileReader(file);
            GameWorld world = json.fromJson(GameWorld.class, reader);
            reader.close();
            return world;
        } catch (Throwable e) {
            throw new GdxRuntimeException("Couldn't read file '" + file + "'", e);
        }
    }

    public static void main(String[] argv) {
        GameWorld world = new GameWorld();
        world.spaceShip = new SpaceShip();
        world.objects.add(new Planet());

        serialize(world, new File("test.map"));
        deserialize(new File("test.map"));
    }
}
