package com.github.dreamsnatcher.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class TestScreen extends Screen {
    OrthographicCamera camera;
    ShapeRenderer renderer;

    public TestScreen(ScreenManager manager) {
        super(manager);
        this.camera = new OrthographicCamera(8, 6);
        this.camera.position.set(4, 3, 0);
        this.renderer = new ShapeRenderer();
    }

    @Override
    public void render() {
        camera.update();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);
        renderer.rect(1, 1, 1, 1);
        renderer.end();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
