package com.github.dreamsnatcher.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.github.dreamsnatcher.desktop.utils.CameraHelper;

public class WorldController extends InputAdapter {
    public CameraHelper cameraHelper;
    public long timeElapsed;
    int coinCount = 0;
    private World b2World;
    private boolean debug = false;

    public WorldController() {
        init();
    }

    public void init() {
        Gdx.input.setInputProcessor(this);
        cameraHelper = new CameraHelper();
        b2World = new World(new Vector2(0, -9.81f), true);

    }

    public void update(float deltaTime) {
        timeElapsed += deltaTime * 1000;
        cameraHelper.update(deltaTime);
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
