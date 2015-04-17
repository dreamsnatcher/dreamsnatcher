package com.github.dreamsnatcher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.github.dreamsnatcher.entities.SpaceShip;
import com.github.dreamsnatcher.screens.ScreenManager;
import com.github.dreamsnatcher.utils.CameraHelper;


public class WorldController extends InputAdapter {
    public static final int MAX_ACCELERATION = 10;
    public CameraHelper cameraHelper;

    public void setWorldRenderer(WorldRenderer worldRenderer) {
        this.worldRenderer = worldRenderer;
    }

    public WorldRenderer worldRenderer;
    public long timeElapsed;
    private World b2World;
    private boolean debug = false;
    public SpaceShip spaceShip;
    private Vector2 curTouchPos;
    private static float MAX_V = 1;

    public WorldController() {
        init();
    }

    public void init() {
        ScreenManager.multiplexer.addProcessor(this);
        cameraHelper = new CameraHelper();
        b2World = new World(new Vector2(0, 0f), true);
        spaceShip = new SpaceShip();
        spaceShip.init(b2World);
        cameraHelper.setTarget(spaceShip.getBody());
    }

    public void update(float deltaTime) {
        timeElapsed += deltaTime * 1000;
        cameraHelper.update(deltaTime);
        spaceShip.update(deltaTime);
        b2World.step(1 / 60f, 3, 8); //timeStep, velocityIteration, positionIteration
        if (Gdx.input.isTouched()) {
            accelerate(curTouchPos.x, curTouchPos.y);
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
                spaceShip.getBody().setTransform(0,0,0);
                spaceShip.getBody().setLinearVelocity(0,0);
                break;
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //TODO calculate vector between spaceship and touch point
        curTouchPos = new Vector2(screenX, screenY);
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
        Vector3 touch = worldRenderer.camera.unproject(new Vector3(screenX, screenY, 0));
        Vector2 shipPos = spaceShip.getBody().getPosition();
        Vector2 thrustDir = new Vector2(shipPos.x - touch.x, shipPos.y - touch.y);
        Vector2 thrustNormed = new Vector2(thrustDir.x / (thrustDir.len() * MAX_ACCELERATION), thrustDir.y / (thrustDir.len() * MAX_ACCELERATION));
        if (spaceShip.getBody().getLinearVelocity().len() < MAX_V) {
            spaceShip.getBody().applyForceToCenter(thrustNormed, true);
            spaceShip.getBody().setTransform(shipPos.x,shipPos.y,(thrustNormed.angle()-90) * MathUtils.degreesToRadians);
        }
    }

}
