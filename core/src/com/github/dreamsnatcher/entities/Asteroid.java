package com.github.dreamsnatcher.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.github.dreamsnatcher.utils.Assets;

public class Asteroid extends GameObject {

    // loaded when init is called by GameWorldSerializer
    // not saved to json
    private transient TextureRegion texture0;
    private transient com.badlogic.gdx.physics.box2d.World b2World;
    private transient Body b2Body;

    public void init(com.badlogic.gdx.physics.box2d.World world) {
        double rand = Math.random();
        texture0 = Assets.asteroid0;
        if(rand < 0.33f){
            texture0 = Assets.asteroid1;
        }else if(rand > 0.66f){
            texture0 = Assets.asteroid2;
        }
        b2World = world;
        dimension.set(0.2f, 0.2f);
        origin.x = dimension.x / 2;
        origin.y = dimension.y / 2;
        initPhysics();
    }

    private void initPhysics() {
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
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0;

        b2Body.createFixture(fixtureDef);
        b2Body.setLinearDamping(0.5f);
        b2Body.setAngularDamping(1f);
        b2Body.setBullet(true);
        b2Body.setSleepingAllowed(false);

        circleShape.dispose(); //clean up!!
        b2Body.setUserData(this);
    }


    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture0,
                position.x - dimension.x / 2, position.y - dimension.y / 2,
                origin.x, origin.y,
                dimension.x, dimension.y,
                scale.x, scale.y,
                rotation);
    }


    public void update(float deltaTime) {
        position = b2Body.getPosition();
        rotation = b2Body.getAngle() * MathUtils.radiansToDegrees;
    }


    public Body getBody() {
        return b2Body;
    }
}
