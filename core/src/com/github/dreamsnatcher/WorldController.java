package com.github.dreamsnatcher;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.github.dreamsnatcher.entities.SpaceShip;
import com.github.dreamsnatcher.screens.ScreenManager;
import com.github.dreamsnatcher.utils.CameraHelper;


public class WorldController extends InputAdapter {
    public CameraHelper cameraHelper;

    public void setWorldRenderer(WorldRenderer worldRenderer) {
        this.worldRenderer = worldRenderer;
    }

    public WorldRenderer worldRenderer;
    public long timeElapsed;
    private World b2World;
    private boolean debug = false;
    public SpaceShip spaceShip;

    public WorldController() {
        init();
    }

    public void init() {
        ScreenManager.multiplexer.addProcessor(this);
        cameraHelper = new CameraHelper();
        b2World = new World(new Vector2(0, 0f), true);
        spaceShip = new SpaceShip();
        spaceShip.init(b2World);
    }

    public void update(float deltaTime) {
        timeElapsed += deltaTime * 1000;
        cameraHelper.update(deltaTime);
        spaceShip.update(deltaTime);
        b2World.step(1 / 60f, 3, 8); //timeStep, velocityIteration, positionIteration
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
                break;
        }
        return true;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //TODO calculate vector between spaceship and touch point
        Vector3 touch = worldRenderer.camera.project(new Vector3(screenX,screenX,0));
        Vector2 shipPos = spaceShip.getBody().getPosition();
        Vector2 thrustDir = new Vector2(touch.x - shipPos.x, touch.y - shipPos.y);
        spaceShip.getBody().applyForceToCenter(thrustDir,true);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    public boolean isDebug() {
        return debug;
    }

    public World getB2World() {
        return b2World;
    }

}
