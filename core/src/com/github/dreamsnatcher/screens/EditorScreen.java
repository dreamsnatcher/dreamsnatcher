package com.github.dreamsnatcher.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.github.dreamsnatcher.entities.*;
import com.github.dreamsnatcher.utils.Assets;

/**
 * Created by badlogic on 17/04/15.
 */
public class EditorScreen extends Screen {
    Skin skin;
    Stage stage;
    SpriteBatch batch;

    EditorController controller;
    ShapeRenderer renderer;


    public EditorScreen(ScreenManager manager) {
        super(manager);
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();

        Assets.init();
        initUI();
        controller = new EditorController();

        ScreenManager.multiplexer.addProcessor(stage);
        ScreenManager.multiplexer.addProcessor(controller);
    }

    private void initUI() {
        stage = new Stage();

        skin = new Skin(Gdx.files.internal("editor/uiskin.json"));

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        root.top().pad(5).defaults().space(5);

        TextButton button = new TextButton("New", skin);
        root.add(button);
        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                controller.newWorld();
            }
        });

        button = new TextButton("Save", skin);
        root.add(button);
        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                controller.saveWorld();
            }
        });

        button = new TextButton("Load", skin);
        root.add(button);
        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                controller.loadWorld();
            }
        });

        button = new TextButton("Return to Main Menu", skin);
        root.add(button);
        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.multiplexer.removeProcessor(stage);
                ScreenManager.multiplexer.removeProcessor(controller);
                manager.setScreen(new MainMenuScreen(manager));
            }
        });
        root.row();
        button = new TextButton("Planet", skin);
        root.add(button);
        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                controller.place(Planet.class);
            }
        });

        button = new TextButton("Planet (rotating)", skin);
        root.add(button);
        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                controller.place(PlanetRotating.class);
            }
        });

        button = new TextButton("Spacebar", skin);
        root.add(button);
        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                controller.place(Spacebar.class);
            }
        });

        button = new TextButton("Asteroid", skin);
        root.add(button);
        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                controller.place(Asteroid.class);
            }
        });

        button = new TextButton("Asteroid (moving)", skin);
        root.add(button);
        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                controller.place(AsteroidRotating.class);
            }
        });
        button = new TextButton("Spaceship", skin);
        root.add(button);
        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                controller.camera.position.set(controller.world.spaceShip.position.x, controller.world.spaceShip.position.y, 0);
            }
        });

    }

    @Override
    public void render () {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        controller.camera.update();
        batch.setProjectionMatrix(controller.camera.combined);
        batch.begin();
        controller.world.spaceShip.render(batch);
        for(GameObject object: controller.world.objects) {
            object.render(batch);
        }

        if(controller.action == EditorController.EditorAction.Place) {
            controller.selected.render(batch);
        }
        batch.end();

        // DEBUG rendering for controller hit testing
        if(controller.selected != null) {
            renderer.setProjectionMatrix(controller.camera.combined);
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(Color.GREEN);
            render(controller.selected);
            renderer.end();
        }
    }

    public void render(GameObject obj) {
        Rectangle rect = new Rectangle(obj.position.x - obj.dimension.x / 2, obj.position.y - obj.dimension.y / 2, obj.dimension.x, obj.dimension.y);
        renderer.rect(rect.x, rect.y, rect.width, rect.height);
    }

    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {

    }
}
