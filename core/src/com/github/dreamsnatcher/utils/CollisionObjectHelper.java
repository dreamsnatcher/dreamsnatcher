package com.github.dreamsnatcher.utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.github.dreamsnatcher.entities.*;

/**
 * Created by lschmoli on 4/18/15.
 */
public class CollisionObjectHelper {

    public static SpaceShip getSpaceship(Contact contact) {

        Object objectA = contact.getFixtureA().getBody().getUserData();
        Object objectB = contact.getFixtureB().getBody().getUserData();
        if (objectA instanceof SpaceShip) {
            return (SpaceShip) objectA;
        } else if (objectB instanceof SpaceShip) {
            return (SpaceShip) objectB;
        }
        return null;
    }

    public static GameObject getCollisionPartner(Contact contact) {

        Object objectA = contact.getFixtureA().getBody().getUserData();
        Object objectB = contact.getFixtureB().getBody().getUserData();
        if (objectA instanceof Planet || objectA instanceof Asteroid || objectA instanceof Spacebar) {
            return (GameObject) objectA;
        } else if (objectB instanceof SpaceShip) {
            return (SpaceShip) objectB;
        }
        return null;
    }

}
