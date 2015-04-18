package com.github.dreamsnatcher;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.utils.Disposable;
import com.github.dreamsnatcher.entities.GameObject;
import com.github.dreamsnatcher.utils.Assets;
import com.github.dreamsnatcher.utils.Constants;
import com.github.dreamsnatcher.utils.HighscoreHelper;

import java.util.concurrent.TimeUnit;
import java.util.logging.LoggingMXBean;

public class WorldRenderer implements Disposable {
    public OrthographicCamera camera;
    private OrthographicCamera cameraGUI;
    private SpriteBatch batch;
    private SpriteBatch nightmareBatch;
    private WorldController worldController;
    private Box2DDebugRenderer debugRenderer;
    private BitmapFont font;
    private TextureRegion background0;
    private TextureRegion background1;
    private TextureRegion background2;
    private TextureRegion background3;
    private TextureRegion energybar;
    private TextureRegion energypixel;
    private TextureRegion beerpixel;
    private TextureRegion schaumkrone;

    private int[] rotation;
    private TextureRegion finishPicture;

    public int beercounter = 0;
    public float timer = 0;
    public final float MAXTIMER = 0.02f;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    private void init() {
        debugRenderer = new Box2DDebugRenderer();
        batch = new SpriteBatch();
        nightmareBatch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();

        rotation = new int[100];
        for (int i = 0; i < rotation.length; i++) {
            rotation[i] = (int) (Math.random() * 16) + 1;
        }

        background0 = Assets.stars0;
        background1 = Assets.stars1;
        background2 = Assets.stars2;
        background3 = Assets.stars3;
        energybar = Assets.energyBar;
        energypixel = Assets.energyPixel;
        beerpixel = Assets.bierpixel;
        schaumkrone = Assets.schaumkrone;

        //GUI camera
        cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
        cameraGUI.position.set(0, 0, 0);
        cameraGUI.setToOrtho(true); //flip y-axis
        cameraGUI.update();

        font = new BitmapFont(true); //default 15pt Arial
        finishPicture = Assets.finishWookie;
        finishPicture.flip(false, true);
    }

    public void renderGUI(SpriteBatch batch) {
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
        String mmss = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(worldController.timeElapsed) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(worldController.timeElapsed) % TimeUnit.MINUTES.toSeconds(1));
        String lastHighscore = HighscoreHelper.readHighscore(worldController.getMap());

        if (!lastHighscore.contains("no")){
            long highscore = Long.parseLong(lastHighscore);
            lastHighscore = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(highscore) % TimeUnit.HOURS.toMinutes(1),
                    TimeUnit.MILLISECONDS.toSeconds(highscore) % TimeUnit.MINUTES.toSeconds(1));
        }
        font.draw(batch, mmss, 10, 10);
        font.draw(batch, "Highscore: " + lastHighscore, 50, 10);
        batch.draw(Assets.indicator, 10, 20, Assets.indicator.getRegionWidth() / 2, Assets.indicator.getRegionHeight() / 2,
                Assets.indicator.getRegionWidth(), Assets.indicator.getRegionHeight(), 0.5f, 0.5f, getCurrentIndicatorAngle());

        batch.draw(energybar, 760, 100, 40, 400);

        if (!worldController.isFinish()) {
            for (int i = 1; i <= this.worldController.gameWorld.spaceShip.getEnergy(); i++) {
                batch.draw(new TextureRegion(energypixel), 760, 500 - i * 4, 40, 4);
            }
        }
        if (worldController.isFinish()) {
            timer += Gdx.graphics.getDeltaTime();
            if (timer > MAXTIMER) {
                timer = 0;
                if (beercounter <= 400) {
                    beercounter++;
                }
            }
            for (int i = 1; i <= beercounter; i++) {
                batch.draw(new TextureRegion(beerpixel), 760, 500 - i, 40, 4);
            }
            batch.draw(new TextureRegion(schaumkrone), 755, 500 - beercounter - 3, 50, 20);
        }
        if (beercounter >= 400) {
            batch.draw(new TextureRegion(finishPicture), 100, 300, finishPicture.getRegionWidth(), finishPicture.getRegionHeight());
            worldController.finalAnimationFinished = true;
        }

        batch.end();
    }

    private float getCurrentIndicatorAngle() {
        Vector2 shipPos = worldController.gameWorld.spaceShip.getBody().getPosition();
        Vector2 barPos = worldController.gameWorld.spacebar.getBody().getPosition();
        Vector2 shipBarDistance = new Vector2(shipPos.x - barPos.x, shipPos.y - barPos.y);
        return 180 - shipBarDistance.angle();
    }

    public void render() {
        worldController.cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        int k = 0;
        for (int i = -20; i < 20; i++) {
            for (int j = -20; j < 20; j++) {
                TextureRegion textureRegion;
                switch (rotation[k % rotation.length]) {
                    case 1:
                        textureRegion = new TextureRegion(background0);
                        break;
                    case 2:
                        textureRegion = new TextureRegion(background0);
                        textureRegion.flip(true, false);
                        break;
                    case 3:
                        textureRegion = new TextureRegion(background0);
                        textureRegion.flip(true, true);
                        break;
                    case 4:
                        textureRegion = new TextureRegion(background0);
                        textureRegion.flip(false, true);
                        break;
                    case 5:
                        textureRegion = new TextureRegion(background1);
                        break;
                    case 6:
                        textureRegion = new TextureRegion(background1);
                        textureRegion.flip(true, false);
                        break;
                    case 7:
                        textureRegion = new TextureRegion(background1);
                        textureRegion.flip(true, true);
                        break;
                    case 8:
                        textureRegion = new TextureRegion(background1);
                        textureRegion.flip(false, true);
                        break;
                    case 9:
                        textureRegion = new TextureRegion(background2);
                        break;
                    case 10:
                        textureRegion = new TextureRegion(background2);
                        textureRegion.flip(true, false);
                        break;
                    case 11:
                        textureRegion = new TextureRegion(background2);
                        textureRegion.flip(true, true);
                        break;
                    case 12:
                        textureRegion = new TextureRegion(background2);
                        textureRegion.flip(false, true);
                        break;
                    case 13:
                        textureRegion = new TextureRegion(background3);
                        break;
                    case 14:
                        textureRegion = new TextureRegion(background3);
                        textureRegion.flip(true, false);
                        break;
                    case 15:
                        textureRegion = new TextureRegion(background3);
                        textureRegion.flip(true, true);
                        break;
                    case 16:
                        textureRegion = new TextureRegion(background3);
                        textureRegion.flip(false, true);
                        break;
                    default:
                        textureRegion = background0;
                }
                k++;
                batch.draw(textureRegion, i, j, 1, 1);
            }
        }
        for (GameObject object : worldController.gameWorld.objects) {
            object.render(batch);
        }
        worldController.gameWorld.spaceShip.render(batch);
        batch.end();
        renderGUI(batch);
        if (worldController.isDebug()) {
            debugRenderer.render(worldController.getB2World(), camera.combined);
        }
        showNightmare(worldController.gameWorld.spaceShip.getEnergy());
    }

    public void resize(int width, int height) {
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width; //calculate aspect ratio
        camera.update();
    }

    public void showNightmare(float energy) {
        float alpha = 0.0f;
        if (!worldController.isFinish() && energy < 20) {
            alpha = (20 - energy)/20f;
        } else if (worldController.isFinish()){
            alpha = 0.0f;
        }
        nightmareBatch.setProjectionMatrix(cameraGUI.combined);
        nightmareBatch.begin();
        Color c = nightmareBatch.getColor();
        nightmareBatch.setColor(c.r, c.g, c.b, alpha);
        nightmareBatch.draw(Assets.nightmare, 0, 0, cameraGUI.viewportWidth, cameraGUI.viewportHeight);
        nightmareBatch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
