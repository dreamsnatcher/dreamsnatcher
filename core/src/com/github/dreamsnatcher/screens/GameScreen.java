package com.github.dreamsnatcher.screens;

import com.badlogic.gdx.Gdx;
import com.github.dreamsnatcher.WorldController;
import com.github.dreamsnatcher.WorldRenderer;
import com.github.dreamsnatcher.utils.Assets;
import com.github.dreamsnatcher.utils.AudioManager;


public class GameScreen extends Screen {
    WorldRenderer worldRenderer;
    WorldController worldController;

    public GameScreen(ScreenManager manager, String level) {
        super(manager);
        Assets.init();
        worldController = new WorldController(level);
        worldRenderer = new WorldRenderer(worldController);
        worldController.setWorldRenderer(worldRenderer);
    }
    @Override
    public void render() {
        worldController.update(Gdx.graphics.getDeltaTime());
        AudioManager.update(Gdx.graphics.getDeltaTime());
        worldRenderer.render();
        if(worldController.switchToMainMenu){
            AudioManager.stopAll();
            AudioManager.init();
            manager.setScreen(new MainMenuScreen(manager));
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        worldRenderer.resize(width, height);
    }

    @Override
    public void dispose() {
        worldRenderer.dispose();
    }
}
