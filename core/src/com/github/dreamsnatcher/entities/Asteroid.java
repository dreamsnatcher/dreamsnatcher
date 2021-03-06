package com.github.dreamsnatcher.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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
    private float angleNew = 0f;
    private static transient float RADIUS = .00002f ;
    private static transient float RTT = 40f ;
    private transient Vector2 center;
    public transient boolean doRotation = false;
    private transient boolean hit;


    public void init(com.badlogic.gdx.physics.box2d.World world) {
        angleNew = (float) Math.random() * 360;
        double rand = Math.random();
        texture0 = Assets.asteroid0;
        if(rand < 0.25f){
            texture0 = Assets.asteroid1;
        }else if(rand >= 0.25f && rand < 0.5f){
            texture0 = Assets.asteroid2;
        }else  if(rand>= 0.5f){
            texture0 = Assets.asteroid3;
        }
        b2World = world;
        dimension.set(0.2f, 0.2f);
        origin.x = dimension.x / 2;
        origin.y = dimension.y / 2;
        center = new Vector2(position.x,position.y-RADIUS);
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
        fixtureDef.density = 0.1f;
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
        if(doRotation && !hit) {

            angleNew += (float) (deltaTime * (2 * Math.PI / RTT));
            float cos = MathUtils.cos(angleNew);
            float sin = MathUtils.sin(angleNew);
            Vector2 vector2 = new Vector2(center.x + cos, center.y + sin);
            vector2.setLength(center.len() + RADIUS);

            b2Body.setTransform(vector2, b2Body.getAngle());
        }
    }

    public void hit(){
        this.hit = true;
    }

    public Body getBody() {
        return b2Body;
    }
}
