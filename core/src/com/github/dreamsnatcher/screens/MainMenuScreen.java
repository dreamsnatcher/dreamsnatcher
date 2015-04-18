package com.github.dreamsnatcher.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MainMenuScreen extends com.github.dreamsnatcher.screens.Screen {

    private Stage stage;
    private Table table;
    private Skin skin;

    public MainMenuScreen(ScreenManager manager){
        super(manager);
        initUI();
    }


    private void initUI(){
        skin = new Skin(Gdx.files.internal("editor/uiskin.json"));
        stage = new Stage();
        table = new Table();

        Label title = new Label("Dreamsnatcher",skin);
        table.add(title).padBottom(40).row();

        TextButton button  = new TextButton("Play", skin);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Same way we moved here from the Splash Screen
                //We set it to new Splash because we got no other screens
                //otherwise you put the screen there where you want to go
                manager.setScreen(new GameScreen(manager));
            }
        });
        table.add(button).size(150,60).padBottom(20).row();

        button = new TextButton("Editor", skin);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.setScreen(new EditorScreen(manager));
            }
        });
        table.add(button).size(150, 60).padBottom(20).row();

        button = new TextButton("Exit", skin);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                // or System.exit(0);
            }
        });
        table.add(button).size(150,60).padBottom(20).row();


        table.setFillParent(true);
        stage.addActor(table);

        Texture texture = new Texture("alienstart.png");
        TextureRegion region = new TextureRegion(texture, 0, 0, 500, 500);
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