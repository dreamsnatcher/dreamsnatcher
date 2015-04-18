package com.github.dreamsnatcher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.github.dreamsnatcher.entities.*;
import com.github.dreamsnatcher.screens.ScreenManager;
import com.github.dreamsnatcher.utils.AudioManager;
import com.github.dreamsnatcher.utils.CameraHelper;
import com.github.dreamsnatcher.utils.CollisionObjectHelper;
import com.github.dreamsnatcher.utils.HighscoreHelper;


public class WorldController extends InputAdapter implements ContactListener {
    public static final int MAX_ACCELERATION = 30;
    private static final float MAX_V = 3;
    public static final float DRAIN_ENERGY_STEP = 0.1f;

    public CameraHelper cameraHelper;
    private boolean zoomIn;
    private boolean finish;
    public boolean finalAnimationFinished;
    public boolean switchToMainMenu;

    public void setWorldRenderer(WorldRenderer worldRenderer) {
        this.worldRenderer = worldRenderer;
    }

    public WorldRenderer worldRenderer;
    public long timeElapsed;
    private World b2World;
    public GameWorld gameWorld;
    private boolean debug = false;
    private Vector2 curTouchPos;
    private String map = "map1.map";
    private long highscore;

    public WorldController(String level) {
        this.map = level;
        String highscore = HighscoreHelper.readHighscore(getMap());
        this.highscore = highscore.contains("no") ? 0 : Long.parseLong(highscore);
        init();
    }

    public void init() {
        ScreenManager.multiplexer.addProcessor(this);
        cameraHelper = new CameraHelper();
        b2World = new World(new Vector2(0, 0f), true);
        b2World.setContactListener(this);
        // You can open this file and edit it via
        // Editor in the desktop project. You can of course
        // also create new files.
        gameWorld = GameWorldSerializer.deserialize(Gdx.files.internal(map));
        gameWorld.init(b2World);
        cameraHelper.setTarget(gameWorld.spaceShip.getBody());
    }

    public String getMap(){
        return map.substring(0, map.lastIndexOf("."));
    }

    public void update(float deltaTime) {
        if (!finish) {
            timeElapsed += deltaTime * 1000;
        }
        cameraHelper.update(deltaTime);
        gameWorld.spaceShip.update(deltaTime);
        for (GameObject object : gameWorld.objects) {
            object.update(deltaTime);
        }
        b2World.step(1 / 60f, 3, 8); //timeStep, velocityIteration, positionIteration
        if (Gdx.input.isTouched() && gameWorld.spaceShip.getEnergy() > 0 && !finish) {
            accelerate(curTouchPos.x, curTouchPos.y);
        }
        if (zoomIn) {
            cameraHelper.setZoom(cameraHelper.getZoom() - 0.002f);
        }
        if(gameWorld.spaceShip.getEnergy() <= 0f && gameWorld.spaceShip.getBody().getLinearVelocity().len() <= 0.001f ){
            finalAnimationFinished = true;
            AudioManager.stopAll();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.PLUS:
                cameraHelper.addZoom(-0.2f);
                break;
            case Input.Keys.MINUS:
                cameraHelper.addZoom(0.2f);
                break;
            case Input.Keys.D:
                debug = !debug;
                break;
            case Input.Keys.R:
                reset();
                break;
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        curTouchPos = new Vector2(screenX, screenY);
        if (this.finalAnimationFinished) {
            this.switchToMainMenu = true;
            ScreenManager.multiplexer.removeProcessor(this);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        curTouchPos = new Vector2(screenX, screenY);
        return true;
    }

    public boolean isDebug() {
        return debug;
    }

    public World getB2World() {
        return b2World;
    }

    private void accelerate(float screenX, float screenY) {
        if (gameWorld.spaceShip.harvest || gameWorld.spaceShip.transist) {
            gameWorld.spaceShip.endHarvest();
        }
        Vector3 touch = worldRenderer.camera.unproject(new Vector3(screenX, screenY, 0));
        SpaceShip spaceShip = gameWorld.spaceShip;
        Vector2 shipPos = spaceShip.getBody().getPosition();
        Vector2 thrustDir = new Vector2(shipPos.x - touch.x, shipPos.y - touch.y);
        Vector2 thrustNormed = new Vector2(thrustDir.x / (thrustDir.len() * MAX_ACCELERATION), thrustDir.y / (thrustDir.len() * MAX_ACCELERATION));
        if (spaceShip.getBody().getLinearVelocity().len() < MAX_V) {
            spaceShip.getBody().applyForceToCenter(thrustNormed, true);
            spaceShip.getBody().setTransform(shipPos.x, shipPos.y, (thrustNormed.angle() - 90) * MathUtils.degreesToRadians);
            spaceShip.drainEnergy();
        }
    }

    private void reset() {
        SpaceShip spaceShip = gameWorld.spaceShip;
        spaceShip.getBody().setTransform(0, 0, 0);
        spaceShip.getBody().setLinearVelocity(0, 0);
        spaceShip.setEnergy(50f);
    }

    @Override
    public void beginContact(Contact contact) {
        SpaceShip spaceShip = CollisionObjectHelper.getSpaceship(contact);
        Planet planet = CollisionObjectHelper.getPlanet(contact);
        Asteroid asteroid = CollisionObjectHelper.getAsteroid(contact);
        Spacebar spacebar = CollisionObjectHelper.getSpaceBar(contact);

        if (planet != null && spaceShip != null && planet.getEnergy() > 1f && planet.cooldown <= 0) {
            spaceShip.getBody().setLinearVelocity(0, 0);
            spaceShip.beginHarvest(planet);
            AudioManager.landing.play();
        }
        if (asteroid != null && spaceShip != null) {
            AudioManager.ahit.play();
            spaceShip.setEnergy(spaceShip.getEnergy() - 20f);
            asteroid.hit();
        }

        if (spaceShip != null && spacebar != null) {
            cameraHelper.setTarget(spacebar.getBody());
            finish = true;
            spaceShip.hasLanded();
            spacebar.hasBeenLandedOn();
            AudioManager.havanaMusic();
            zoomIn = true;
            if (timeElapsed <= highscore){
                HighscoreHelper.writeHighscore(timeElapsed, getMap());
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isFinish() {
        return finish;
    }
}
