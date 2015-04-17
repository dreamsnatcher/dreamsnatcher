package com.github.dreamsnatcher.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.github.dreamsnatcher.entities.GameObject;
import com.github.dreamsnatcher.entities.GameWorld;
import com.github.dreamsnatcher.entities.Planet;
import com.github.dreamsnatcher.entities.SpaceShip;
import com.github.dreamsnatcher.utils.Assets;

/**
 * Created by badlogic on 17/04/15.
 */
public class EditorScreen extends Screen {
    Skin skin;
    Stage stage;
    SpriteBatch batch;
    OrthographicCamera camera;

    GameWorld world;
    GameObject selected;

    public enum Action {
        Place,
        Drag,
        Pan
    }

    public EditorScreen(ScreenManager manager) {
        super(manager);
        batch = new SpriteBatch();
        camera = new OrthographicCamera(5, 5);

        Assets.init();
        initUI();
        newWorld();
    }

    private void initUI() {
        stage = new Stage();
        ScreenManager.multiplexer.addProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        root.top().pad(5).defaults().space(5);

        TextButton button = new TextButton("New", skin);
        root.add(button);
        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        button = new TextButton("Save", skin);
        root.add(button);
        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        button = new TextButton("Load", skin);
        root.add(button);
        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        button = new TextButton("Spaceship", skin);
        root.add(button);
        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        button = new TextButton("Planet", skin);
        root.add(button);
        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
    }

    private void newWorld() {
        world = new GameWorld();
        world.spaceShip = new SpaceShip();
    }

    private void saveWorld() {

    }

    private void loadWorld() {

    }

    @Override
    public void render () {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(Assets.planet, world.spaceShip.position.x, world.spaceShip.position.y);
        for(GameObject object: world.objects) {
            if(object instanceof Planet) {
                batch.draw(Assets.planet, object.position.x, object.position.y);
            }
        }
        batch.end();
    }

    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {

    }
}
