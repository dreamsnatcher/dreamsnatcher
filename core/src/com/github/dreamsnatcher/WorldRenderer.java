package com.github.dreamsnatcher;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.github.dreamsnatcher.entities.GameObject;
import com.github.dreamsnatcher.entities.SpaceShip;
import com.github.dreamsnatcher.utils.Assets;
import com.github.dreamsnatcher.utils.AudioManager;
import com.github.dreamsnatcher.utils.Constants;

import java.util.concurrent.TimeUnit;

public class WorldRenderer implements Disposable {
    public OrthographicCamera camera;
    public OrthographicCamera cameraGUI;
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
    private TextureRegion penaltypixel;
    public RayHandler rayHandler;

    private TextureRegion schaumkrone;

    private TextureRegion[][] bgs;
    private int[] rotation;

    private TextureRegion finishPicture;
    public int beercounter = 0;
    public float beerTimer = 0;

    public float globalTimer = 0;


    public final float MAXTIMER = 0.02f;
    int minX = -10;
    int minY = -10;
    int maxX = 10;
    int maxY = 10;
    private ConeLight light;


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
        penaltypixel = Assets.penaltyPixel;
        beerpixel = Assets.bierpixel;
        schaumkrone = Assets.schaumkrone;

        //GUI camera
        cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
        cameraGUI.position.set(0, 0, 0);
        cameraGUI.setToOrtho(true); //flip y-axis
        cameraGUI.update();

        font = new BitmapFont(Gdx.files.internal("fonts/superfont.fnt"),
                Gdx.files.internal("fonts/superfont.png"), true);

        font = new BitmapFont(Gdx.files.internal("fonts/candlestick.fnt"),
                Gdx.files.internal("fonts/candlestick.png"), true);
        font.setColor(Color.GREEN);

        rayHandler = new RayHandler(worldController.getB2World());
        rayHandler.setAmbientLight(new Color(0.1f,0.1f,0.1f,1f));
        light = new ConeLight(rayHandler, 128, new Color(0.1f,0.1f,0.1f,1), 100, camera.position.x, camera.position.y,0,50f);

        //finishPicture.flip(false, true);
    }

    public void calculateBackground() {
        for (GameObject o : worldController.gameWorld.objects) {
            if (o.position.x < minX) {
                minX = (int) o.position.x;
            }
            if (o.position.y < minY) {
                minY = (int) o.position.y;
            }
            if (o.position.y > maxY) {
                maxY = (int) o.position.y;
            }
            if (o.position.x > maxX) {
                maxX = (int) o.position.x;
            }
        }
        minX -= 10;
        minY -= 10;
        maxX += 10;
        maxY += 10;


        bgs = new TextureRegion[maxX - minX][maxY - minY];
        int k = 0;
        TextureRegion textureRegion;
        for (int i = 0; i < bgs.length; i++) {
            for (int j = 0; j < bgs[i].length; j++) {
                k++;
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
                bgs[i][j] = textureRegion;
            }
        }
    }

    public void renderGUI(SpriteBatch batch) {
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
        String mmss = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(worldController.timeElapsed) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(worldController.timeElapsed) % TimeUnit.MINUTES.toSeconds(1));
        String lastHighscore = "No highscore yet";
        long highScoreVal = worldController.getHighscore();
        if (highScoreVal > -1) {
            lastHighscore = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(highScoreVal) % TimeUnit.HOURS.toMinutes(1),
                    TimeUnit.MILLISECONDS.toSeconds(highScoreVal) % TimeUnit.MINUTES.toSeconds(1));
        }
        font.draw(batch, mmss, 10, 10);
        font.draw(batch, "Highscore: " + lastHighscore, 100, 10);
        batch.draw(Assets.indicator, 10, 20, Assets.indicator.getRegionWidth() / 2, Assets.indicator.getRegionHeight() / 2,
                Assets.indicator.getRegionWidth(), Assets.indicator.getRegionHeight(), 0.5f, 0.5f, getCurrentIndicatorAngle());

        batch.draw(energybar, cameraGUI.viewportWidth-40f, 100, 40, 400);

        if (!worldController.isFinish()) {
            for (int i = 1; i <= this.worldController.gameWorld.spaceShip.getEnergy(); i++) {
                if (this.worldController.gameWorld.spaceShip.getPenaltyTime() <= 0) {
                    batch.draw(new TextureRegion(energypixel), cameraGUI.viewportWidth-40f, 500 - i * 4, 40, 4);
                } else {
                    batch.draw(new TextureRegion(penaltypixel), cameraGUI.viewportWidth-40f, 500 - i * 4, 40, 4);
                }
            }
            if (this.worldController.gameWorld.spaceShip.getPenaltyTime() > 0) {
                this.worldController.gameWorld.spaceShip.lowerPenaltyTime(Gdx.graphics.getDeltaTime());
            }
        }
        if (worldController.isFinish()) {
            beerTimer += Gdx.graphics.getDeltaTime();
            if (beerTimer > MAXTIMER) {
                beerTimer = 0;
                if (beercounter <= 400) {
                    beercounter++;
                }
            }
            for (int i = 1; i <= beercounter; i++) {
                batch.draw(new TextureRegion(beerpixel), cameraGUI.viewportWidth-40f, 500 - i, 40, 4);
            }
            batch.draw(new TextureRegion(schaumkrone), cameraGUI.viewportWidth- 45f, 500 - beercounter - 3, 50, 20);
        }
        if (beercounter >= 400) {
            globalTimer+=Gdx.graphics.getDeltaTime();
            finishPicture = Assets.wookieAnimation.getKeyFrame(globalTimer, true);
            batch.draw(new TextureRegion(finishPicture), 100, 300, finishPicture.getRegionWidth(), finishPicture.getRegionHeight());
            worldController.finalAnimationFinished = true;
        }

        font.draw(batch, "BACK",  cameraGUI.viewportWidth-80f, 10);
        batch.end();
    }

    private float getCurrentIndicatorAngle() {
        Vector2 shipPos = worldController.gameWorld.spaceShip.getBody().getPosition();
        Vector2 barPos = worldController.gameWorld.spacebar.getBody().getPosition();
        Vector2 shipBarDistance = new Vector2(shipPos.x - barPos.x, shipPos.y - barPos.y);
        return 180 - shipBarDistance.angle();
    }

    public void render() {
        SpaceShip spaceShip = worldController.gameWorld.spaceShip;
        light.setPosition(spaceShip.getBody().getWorldCenter().x,spaceShip.getBody().getWorldCenter().y);
        light.setDirection((worldController.gameWorld.spaceShip.getBody().getAngle() * MathUtils.radiansToDegrees)+90);
        worldController.cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if(Gdx.app.getType() != Application.ApplicationType.Android) {
            renderBackground();
        }
        for (GameObject object : worldController.gameWorld.objects) {
            object.render(batch);
        }
        worldController.gameWorld.spaceShip.render(batch);
        batch.end();
        showNightmare(worldController.gameWorld.spaceShip.getEnergy());
        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.updateAndRender();
        renderGUI(batch);
        if (worldController.isDebug()) {
            debugRenderer.render(worldController.getB2World(), camera.combined);
        }
        if(worldController.gameWorld.spaceShip.getEnergy()<20){
            AudioManager.noEnergy();
        }
        else{
            AudioManager.someEnergy();
        }
    }

    private void renderBackground() {
        for (int i = 0; i < maxX-minX; i++) {
            for (int j = 0; j < maxY-minY; j++) {
                batch.draw(bgs[i][j], i+minX, j+minY, 1, 1);
            }
        }
    }

    public void resize(int width, int height) {
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / (float) height) * (float) width; //calculate aspect ratio
        camera.update();
        cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
        cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float) height) * (float) width; //calculate aspect ratio
        cameraGUI.position.set(cameraGUI.viewportWidth/2, cameraGUI.viewportHeight/2,0);
        cameraGUI.update();
    }

    public void showNightmare(float energy) {
        float alpha = 0.0f;
        if (energy < 0f) {
            energy = 0f;
        }
        if (!worldController.isFinish() && energy < 20) {
            alpha = (20 - energy) / 20f;
        } else if (worldController.isFinish()) {
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
        rayHandler.dispose();
    }
}
