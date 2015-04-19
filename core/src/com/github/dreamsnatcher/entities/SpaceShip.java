package com.github.dreamsnatcher.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.github.dreamsnatcher.WorldController;
import com.github.dreamsnatcher.utils.Assets;
import com.github.dreamsnatcher.utils.AudioManager;

public class SpaceShip extends GameObject {

    // loaded when init is called by GameWorldSerializer
    // not saved to json
    protected transient TextureRegion texture;
    protected transient TextureRegion textureStop;
    protected transient TextureRegion textureHarvest;
    protected transient TextureRegion textureEmpty;
    protected transient Animation animationSpeed1;
    protected transient Animation animationSpeed2;
    protected transient Animation animationSpeed3;
    protected transient com.badlogic.gdx.physics.box2d.World b2World;
    protected transient Body b2Body;
    protected volatile transient float energy;
    protected transient float angle;
    public transient boolean transist;
    protected transient float transistTime;
    public transient boolean harvest = false;
    protected transient Planet currentPlanet;
    protected transient boolean harvestStarted = false;
    protected transient Joint joint;
    protected transient boolean destroyJoint;
    protected transient boolean landed;
    public boolean mute = false;
    protected float counter = 0;

    private float penaltyTime = 0f;

    public void init(com.badlogic.gdx.physics.box2d.World world) {
        if (this instanceof SpaceShipFast) {
            texture = Assets.spaceShipFast0;
            textureStop = Assets.spaceShipFast0;
            textureHarvest = Assets.spaceShipFastHarvest;
            textureEmpty = Assets.spaceShipFastEmpty;
            animationSpeed1 = Assets.shipFastAnimationSpeed1;
            animationSpeed2 = Assets.shipFastAnimationSpeed2;
            animationSpeed3 = Assets.shipFastAnimationSpeed3;
        } else if(this instanceof SpaceShipBig) {
            texture = Assets.spaceShipCruzer0;
            textureStop = Assets.spaceShipCruzer0;
            textureHarvest = Assets.spaceShipCruzerHarvest;
            textureEmpty = Assets.spaceShipCruzerEmpty;
            animationSpeed1 = Assets.shipCruzerAnimationSpeed1;
            animationSpeed2 = Assets.shipCruzerAnimationSpeed2;
            animationSpeed3 = Assets.shipCruzerAnimationSpeed3;
        } else{
            texture = Assets.spaceShip0;
            textureStop = Assets.spaceShip0;
            textureHarvest = Assets.spaceShipHarvest;
            textureEmpty = Assets.spaceShipEmpty;
            animationSpeed1 = Assets.shipAnimationSpeed1;
            animationSpeed2 = Assets.shipAnimationSpeed2;
            animationSpeed3 = Assets.shipAnimationSpeed3;
        }

        b2World = world;
        dimension.set(0.4f, 0.5f);
        energy = 50f;
        origin.x = dimension.x / 2;
        origin.y = dimension.y / 2;
        initPhysics();
    }

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
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 0.5f;
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
        counter += Gdx.graphics.getDeltaTime();
        if (counter >= 95415613840f) {
            counter = 0;
        }
        texture = textureStop;
        if (b2Body.getLinearVelocity().len() > 0.2f) {
            texture = animationSpeed1.getKeyFrame(counter, true);
        }

        if (b2Body.getLinearVelocity().len() > 0.5f) {
            texture = animationSpeed2.getKeyFrame(counter, true);

        }

        if (b2Body.getLinearVelocity().len() > 0.7f) {
            texture = animationSpeed3.getKeyFrame(counter, true);
        }
        if (harvest) {
            texture = textureHarvest;
        }
        if (landed) {
            texture = textureEmpty;
        }
        batch.draw(texture, position.x - dimension.x / 2, position.y - dimension.y / 2,
                origin.x, origin.y,
                dimension.x, dimension.y,
                scale.x, scale.y,
                rotation);
    }


    public void update(float deltaTime) {
        if (b2Body.getLinearVelocity().len() > 0.2f) {
            if(!mute)
                AudioManager.moveSlow();
        }

        if (b2Body.getLinearVelocity().len() > 0.5f) {
            if(!mute)
            AudioManager.moveRegular();
        }

        if (b2Body.getLinearVelocity().len() > 0.7f) {
            if(!mute)
            AudioManager.moveFast();
        }
        if (b2Body.getLinearVelocity().len() <= 0.1f) {
            AudioManager.stop();
        }
        if (harvest) {
            AudioManager.stop();
            //AudioManager.harvest();

        }
        if (transist) {
            transistTime -= deltaTime;
            if (transistTime <= 0) {
                transist = false;
                harvest = true;
            }
            b2Body.setTransform(b2Body.getPosition().x, b2Body.getPosition().y, (float) (angle - Math.PI / 2f));
        } else {
            transistTime = 1f;
        }

        if (destroyJoint) {
            destroyJoint = false;
            if (joint != null) {
                b2World.destroyJoint(joint);
                joint = null;
            }
        }

        if (harvestStarted && currentPlanet != null) {
            WeldJointDef jointDef = new WeldJointDef();
            jointDef.bodyA = b2Body;
            jointDef.bodyB = currentPlanet.getBody();
            Vector2 v = new Vector2(b2Body.getWorldCenter().x - currentPlanet.getBody().getWorldCenter().x, b2Body.getWorldCenter().y - currentPlanet.getBody().getWorldCenter().y);
            jointDef.localAnchorB.set(v);
            joint = b2World.createJoint(jointDef);
            harvestStarted = false;
        }
        if (harvest) {
            b2Body.setTransform(b2Body.getPosition().x, b2Body.getPosition().y, (float) (angle - Math.PI / 2f));
            if (currentPlanet != null) {
                if (currentPlanet.drainEnergy() > 0 && energy <= 99f) {
                    gainEnergy();
                } else {
                    if (currentPlanet.getEnergy() == 0) {
                        energy = 4 * energy / 5;
                        penaltyTime = penaltyTime > 0 ? penaltyTime + 2f : 2f;
                        if(!mute)
                        AudioManager.suckDryMusic();
                    }
                    endHarvest();
                }
            }
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
        energy -= WorldController.DRAIN_ENERGY_STEP *1.3f;
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

    public void beginHarvest(Planet planet) {
        harvestStarted = true;
        Vector2 shipPos = b2Body.getWorldCenter();
        Vector2 thrustDir = new Vector2(shipPos.x - planet.getBody().getWorldCenter().x, shipPos.y - planet.getBody().getWorldCenter().y);

        angle = (float) ((thrustDir.angleRad() % (2 * Math.PI)));
        transist = true;
        currentPlanet = planet;

    }

    public void endHarvest() {
        if (transist) {
            return;
        }
        destroyJoint = true;
        harvest = false;
        if (currentPlanet != null) {
            currentPlanet.setCooldown(2f);
            currentPlanet = null;
        }
        AudioManager.landing.stop();
        if(!mute)
            AudioManager.starting.play();
    }

    public void hasLanded() {
        landed = true;
        mute = true;
    }

    public float getPenaltyTime() {
        return penaltyTime;
    }

    public void setPenaltyTime(float penaltyTime) {
        this.penaltyTime = penaltyTime;
    }

    public void lowerPenaltyTime(float penaltyTime) {
        this.penaltyTime = this.penaltyTime - penaltyTime < 0 ? 0f : this.penaltyTime - penaltyTime;
    }
}
