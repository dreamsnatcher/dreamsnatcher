package com.github.dreamsnatcher.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.github.dreamsnatcher.WorldController;
import com.github.dreamsnatcher.utils.Assets;
import com.github.dreamsnatcher.utils.AudioManager;

public class SpaceShip extends GameObject {

    // loaded when init is called by GameWorldSerializer
    // not saved to json
    private transient TextureRegion texture0;
    private transient TextureRegion texture1;
    private transient TextureRegion texture2;
    private transient TextureRegion texture3;
    private transient com.badlogic.gdx.physics.box2d.World b2World;
    private transient Body b2Body;
    private volatile transient float energy;
    private transient float angle;
    private transient boolean transist;
    private transient float transistTime;
    private boolean harvest = false;
    private TextureRegion textureHarvest;

    public void init(com.badlogic.gdx.physics.box2d.World world) {
        texture0 = Assets.spaceShip0;
        texture1 = Assets.spaceShip1;
        texture2 = Assets.spaceShip2;
        texture3 = Assets.spaceShip3;
        textureHarvest = Assets.spaceShipHarvest;
        b2World = world;
        dimension.set(0.4f, 0.5f);
        energy = 50;
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
        b2Body.setLinearDamping(1f);
        b2Body.setAngularDamping(1f);
        b2Body.setBullet(true);

        circleShape.dispose(); //clean up!!
        b2Body.setUserData(this);
    }


    @Override
    public void render(SpriteBatch batch) {
        TextureRegion textureRegion = texture0;
        if(b2Body.getLinearVelocity().len()>0.2f){
            textureRegion = texture1;
            AudioManager.moveSlow();
        }

        if(b2Body.getLinearVelocity().len()>0.5f){
            textureRegion = texture2;
            AudioManager.moveRegular();
        }

        if(b2Body.getLinearVelocity().len()>0.7f){
            textureRegion = texture3;
            AudioManager.moveFast();
        }
        if(b2Body.getLinearVelocity().len()<=0.1f){
            AudioManager.stop();
        }
        if(harvest){
            textureRegion = textureHarvest;
            AudioManager.stop();
            //AudioManager.harvest();


        }



        batch.draw(textureRegion,
                position.x - dimension.x / 2, position.y - dimension.y / 2,
                origin.x, origin.y,
                dimension.x, dimension.y,
                scale.x, scale.y,
                rotation);
    }


    public void update(float deltaTime) {
        if(transist){
            transistTime -= deltaTime;
            if(transistTime<= 0){
                transist = false;
                harvest = true;
            }
            b2Body.setTransform(b2Body.getPosition().x, b2Body.getPosition().y, angle);
        }else{
            transistTime = 1f;
        }
        position = b2Body.getPosition();
        rotation = b2Body.getAngle() * MathUtils.radiansToDegrees;
    }


    public Body getBody() {
        return b2Body;
    }

    public float getEnergy() {
        return energy;
    }

    public float drainEnergy() {
        energy -= WorldController.DRAIN_ENERGY_STEP;
        if (energy < 0) {
            energy = 0;
        }
        return energy;
    }

    public float gainEnergy() {
        energy += WorldController.DRAIN_ENERGY_STEP;
        if (energy > 100) {
            energy = 100;
        }
        return energy;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public void beginHarvest(Planet planet){
        Vector2 shipPos = b2Body.getPosition();
        Vector2 thrustDir = new Vector2(shipPos.x - planet.getBody().getWorldCenter().x, shipPos.y - planet.getBody().getWorldCenter().y);

        angle = (float) (((b2Body.getAngle() % (2 * Math.PI) - Math.PI)));
        transist = true;

    }
}
