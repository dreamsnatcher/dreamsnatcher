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

    public void setUpPlanets(World world){
        objects.add(new Planet());
        objects.add(new Planet());
        objects.add(new Planet());
        objects.add(new Planet());
        for (GameObject object : objects) {
            object.init(world);
        }
    }

    public void setUpSpaceship(World world){
        spaceShip = new SpaceShip();
        spaceShip.init(world);
    }
}
