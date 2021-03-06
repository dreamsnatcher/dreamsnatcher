package com.github.dreamsnatcher.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.github.dreamsnatcher.WorldController;
import com.github.dreamsnatcher.utils.Assets;

/**
 * Created by badlogic on 17/04/15.
 */
public class Planet extends GameObject {

    public float MAX_ENERGY = 50f;
    // loaded when init is called by GameWorldSerializer
    // not saved to json
    private transient Animation texture;
    private transient Animation textureLow;
    private transient Animation textureDead;
    private transient com.badlogic.gdx.physics.box2d.World b2World;
    private transient Body b2Body;
    private transient float energy;
    private static transient float RADIUS = 2f ;
    private static transient float RTT = 20f ;
    private transient Vector2 center;



    public transient  float cooldown = 0f;
    private transient float angleNew = 0f;
    public transient boolean doRotation = false;
    float MAX_ENERGY_BONUS = 75f;
    private float elapsed = 0;

    private void initPhysics() {
        //create body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
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
        energy = MAX_ENERGY;
        angleNew = (float) Math.random() * 360;
        if(MAX_ENERGY == 50f){
            texture = Assets.planet;
            textureLow = Assets.planetLow;
            textureDead = Assets.planetDead;
        }else if(MAX_ENERGY == MAX_ENERGY_BONUS){
            texture = Assets.planetBonus;
            textureLow = Assets.planetBonusLow;
            textureDead = Assets.planetBonusDead;
        }
        center = new Vector2(position.x,position.y-RADIUS);
        b2World = world;
        dimension.set(1f, 1f);
        origin.x = dimension.x / 2;
        origin.y = dimension.y / 2;
        scale= new Vector2(1.3f, 1.3f);
        initPhysics();
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion textureRegion = texture.getKeyFrame(elapsed, true);
        if(energy< MAX_ENERGY/2f){
            textureRegion = textureLow.getKeyFrame(elapsed, true);
        }
        if(energy <= 1f){
            textureRegion = textureDead.getKeyFrame(elapsed,true);
        }

        batch.draw(textureRegion,
                position.x - dimension.x / 2, position.y - dimension.y / 2,
                origin.x, origin.y,
                dimension.x, dimension.y,
                scale.x, scale.y,
                rotation);
    }

    public float drainEnergy() {
        energy -= WorldController.DRAIN_ENERGY_STEP;
        if (energy < 0) {
            energy = 0;
        }
        return energy;
    }

    public float gainEnergy() {
        energy += WorldController.DRAIN_ENERGY_STEP /1000f;
        if (energy >= MAX_ENERGY) {
            energy = MAX_ENERGY;
        }
        return energy;
    }


    public void update(float deltaTime) {
        elapsed += deltaTime;
        if(cooldown>=0){
            cooldown -= deltaTime;
        }
        position = b2Body.getPosition();
        rotation = b2Body.getAngle() * MathUtils.radiansToDegrees;
        if(doRotation) {
            angleNew += (float) (deltaTime * (2 * Math.PI / RTT));
            float cos = MathUtils.cos(angleNew);
            float sin = MathUtils.sin(angleNew);
            Vector2 vector2 = new Vector2(center.x + cos, center.y + sin);

            b2Body.setTransform(vector2, b2Body.getAngle());
        }
        if(energy>=0){
            gainEnergy();
        }
    }


    public Body getBody() {
        return b2Body;
    }

    public float getEnergy() {
        return energy;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }
}
