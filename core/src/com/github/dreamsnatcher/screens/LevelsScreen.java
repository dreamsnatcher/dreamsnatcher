package com.github.dreamsnatcher.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LevelsScreen extends Screen {

    private Stage stage;
    private Table table;
    private Skin skin;

    public LevelsScreen(ScreenManager manager) {
        super(manager);
        initUI();
    }


    private void initUI() {
        skin = new Skin(Gdx.files.internal("editor/uiskin.json"));
        stage = new Stage();
        table = new Table();
        table.row();

        FileHandle dirHandle = Gdx.files.internal("levels");
        int i = 0;
        for (FileHandle entry : dirHandle.list()) {
            if (entry.extension().equals("map")) {
                if (i % 5 >= 4) {
                    table.row();
                }
                i++;
                final TextButton finalButton = new TextButton(entry.nameWithoutExtension(), skin);
                finalButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        manager.setScreen(new GameScreen(manager, "levels/" + finalButton.getText().toString() + ".map"));
                    }
                });

                table.add(finalButton).size(150, 60).pad(10);
            }
        }


        table.setFillParent(true);
        stage.addActor(table);
        Texture texture = new Texture("mainscreen.png");
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