package com.github.dreamsnatcher.entities;

import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by badlogic on 17/04/15.
 */
public class GameWorld {
    public List<GameObject> objects = new ArrayList<GameObject>();
    public SpaceShip spaceShip;
    public Spacebar spacebar;

    /**
     * Call this after you loaded a map via GameWorldSerializer.
     */
    public void init(World b2World) {
        spaceShip.init(b2World);
        for (GameObject object : objects) {
            if (object instanceof Spacebar) {
                spacebar = (Spacebar) object;
            }
            object.init(b2World);
        }
    }
}
