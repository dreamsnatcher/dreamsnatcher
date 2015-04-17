package com.github.dreamsnatcher.desktop;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.github.dreamsnatcher.desktop.utils.Assets;
import com.github.dreamsnatcher.desktop.utils.Constants;

import java.util.concurrent.TimeUnit;

public class WorldRenderer implements Disposable {
    private OrthographicCamera camera;
    private OrthographicCamera cameraGUI;
    private SpriteBatch batch;
    private WorldController worldController;
    private Box2DDebugRenderer debugRenderer;
    private BitmapFont font;
    private TextureRegion coinTexture;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    private void init() {
        debugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();

        //GUI camera
        cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
        cameraGUI.position.set(0, 0, 0);
        cameraGUI.setToOrtho(true); //flip y-axis
        cameraGUI.update();

        font = new BitmapFont(true); //default 15pt Arial
        coinTexture = Assets.getInstance(new AssetManager()).findRegion("coinGold");
    }

    public void renderGUI(SpriteBatch batch) {
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
        String mmss = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(worldController.timeElapsed) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(worldController.timeElapsed) % TimeUnit.MINUTES.toSeconds(1));
        font.draw(batch, mmss, 10, 10);
        batch.draw(coinTexture, 10, 40, 20, 20);
        font.draw(batch, Integer.toString(worldController.coinCount), 35, 40);
        batch.end();
    }

    public void render() {
        worldController.cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.end();
        renderGUI(batch);
        if (worldController.isDebug()) {
            debugRenderer.render(worldController.getB2World(), camera.combined);
        }
    }

    public void resize(int width, int height) {
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width; //calculate aspect ratio
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
