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

    public static Planet getPlanet(Contact contact) {

        Object objectA = contact.getFixtureA().getBody().getUserData();
        Object objectB = contact.getFixtureB().getBody().getUserData();
        if (objectA instanceof Planet ) {
            return (Planet) objectA;
        } else if (objectB instanceof Planet){
            return (Planet) objectB;
        }
        return null;
    }

    public static Asteroid getAsteroid(Contact contact) {

        Object objectA = contact.getFixtureA().getBody().getUserData();
        Object objectB = contact.getFixtureB().getBody().getUserData();
        if (objectA instanceof Asteroid ) {
            return (Asteroid) objectA;
        } else if (objectB instanceof Asteroid){
            return (Asteroid) objectB;
        }
        return null;
    }

    public static Spacebar getSpaceBar(Contact contact) {

        Object objectA = contact.getFixtureA().getBody().getUserData();
        Object objectB = contact.getFixtureB().getBody().getUserData();
        if (objectA instanceof Spacebar ) {
            return (Spacebar) objectA;
        } else if (objectB instanceof Spacebar){
            return (Spacebar) objectB;
        }
        return null;
    }

}
