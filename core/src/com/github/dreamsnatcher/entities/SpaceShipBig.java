package com.github.dreamsnatcher.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.github.dreamsnatcher.WorldController;

public class SpaceShipBig extends SpaceShip {


    public static final float CRUZER_DAMPENER = 0.5f;

    @Override
    protected void initPhysics() {
        //create body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x, position.y);

        //create body in world
        b2Body = b2World.createBody(bodyDef);

        //create shape
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(dimension.x / 2);

        //create fixture to attach shape to body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0;

        b2Body.createFixture(fixtureDef);
        b2Body.setLinearDamping(0.2f);
        b2Body.setAngularDamping(1f);
        b2Body.setBullet(true);
        b2Body.setSleepingAllowed(false);

        circleShape.dispose(); //clean up!!
        b2Body.setUserData(this);
    }



    @Override
    public float drainEnergy() {
        energy -= WorldController.DRAIN_ENERGY_STEP* CRUZER_DAMPENER;
        if (energy < 0) {
            energy = 0;
        }
        return energy;
    }

    @Override
    public float gainEnergy() {
        energy += WorldController.DRAIN_ENERGY_STEP * CRUZER_DAMPENER;
        if (energy > 100) {
            energy = 100;
        }
        return energy;
    }

}
