package com.github.dreamsnatcher.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.World;
import com.github.dreamsnatcher.utils.Assets;

/**
 * Created by badlogic on 17/04/15.
 */
public class Spacebar extends GameObject {

    // loaded when init is called by GameWorldSerializer
    // not saved to json
    private transient TextureRegion texture;
    private transient com.badlogic.gdx.physics.box2d.World b2World;
    private transient Body b2Body;
    private boolean hasBeenLandedOn = false;
    private float elapse =0;

    private void initPhysics() {
        //create body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
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
        b2Body.setLinearDamping(1f);
        circleShape.dispose(); //clean up!!
        b2Body.setUserData(this);
    }

    @Override
    public void init(World world) {
        texture = Assets.spaceBar;
        b2World = world;
        dimension.set(1f, 1f);
        origin.x = dimension.x / 2;
        origin.y = dimension.y / 2;
        initPhysics();
    }

    @Override
    public void render(SpriteBatch batch) {
        if(hasBeenLandedOn){
            texture = Assets.spaceBarFinish.getKeyFrame(elapse,true);
        }
        batch.draw(texture, position.x - dimension.x / 2, position.y - dimension.y / 2,
                origin.x, origin.y,
                dimension.x, dimension.y,
                scale.x, scale.y,
                rotation);
    }

    public void update(float deltaTime) {
        elapse+=deltaTime;
        position = b2Body.getPosition();
        rotation = b2Body.getAngle() * MathUtils.radiansToDegrees;
    }

    public Body getBody() {
        return b2Body;
    }

    public void hasBeenLandedOn(){
        hasBeenLandedOn = true;
    }
}

