package com.github.dreamsnatcher.screens;

import com.badlogic.gdx.Gdx;
import com.github.dreamsnatcher.WorldController;
import com.github.dreamsnatcher.WorldRenderer;


public class GameScreen extends Screen {
    WorldRenderer worldRenderer;
    WorldController worldController;

    public GameScreen(ScreenManager manager) {
        super(manager);
        worldController = new WorldController();
        worldRenderer = new WorldRenderer(worldController);
    }

    @Override
    public void render() {
        worldController.update(Gdx.graphics.getDeltaTime());
        worldRenderer.render();
    }





    @Override
    public void dispose() {
        worldRenderer.dispose();
    }
}
