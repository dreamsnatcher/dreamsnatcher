package com.github.dreamsnatcher.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MainMenuScreen extends com.github.dreamsnatcher.screens.Screen {

    private Stage stage;
    private Table table;
    private Skin skin;

    public MainMenuScreen(ScreenManager manager) {
        super(manager);
        initUI();
    }


    private void initUI() {
        skin = new Skin(Gdx.files.internal("editor/uiskin.json"));
        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        table.row();


        TextButton button = new TextButton("Editor", skin);
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    manager.setScreen(new EditorScreen(manager));
                }
            });

            table.add(button).size(150, 60).pad(10);
        }

        button = new TextButton("Play", skin);
        button.setColor(Color.GREEN);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Same way we moved here from the Splash Screen
                //We set it to new Splash because we got no other screens
                //otherwise you put the screen there where you want to go
                manager.setScreen(new LevelsScreen(manager));
            }
        });
        table.add(button).size(150, 60).pad(10);

        button = new TextButton("Credits", skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.setScreen(new CreditScreen(manager));
            }
        });
        table.add(button).size(150, 60).pad(10);

        table.row();
        table.add(new Actor()).size(150, 60).pad(10);
        button = new TextButton("Exit", skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                // or System.exit(0);
            }
        });
        table.add(button).size(100, 50).pad(10);
        table.setFillParent(true);
        stage.addActor(table);

        Texture texture = new Texture("mainscreen.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
        table.setBackground(new TextureRegionDrawable(region));

        ScreenManager.multiplexer.addProcessor(stage);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

}