package com.github.dreamsnatcher.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.github.dreamsnatcher.utils.Assets;

/**
 * Created by lschmoli on 19.04.2015.
 */
public class CreditScreen extends Screen {

    private Stage stage;

    private static final String credits = "TEAM\n" +
            "Lukas Knoch [Programming]\nThomas Grafenauer [Artwork]\nLorenz Schmoliner [Programming]\nVeit Frick [Programming]\nChristian Zellot [Artwork|Programming]\n\n" +
            "IDEA and GAMEDESIGN\nall of them :)\n\nSPECIAL THANX TO\nMario Zechner [Technical Consultant]\nMathias Lux [Sound]\nChristina Lukasser [Logo-Design]";

    private static final String mario = "Mario Zechner";
    private static final String zelle = "Christian Zellot";
    private static final String thomas = "Thomas Grafenauer";
    private static final String lukas = "Lukas Knoch";
    private static final String lorenz = "Lorenz Schmoliner";
    private static final String lux = "Mathias Lux";
    private static final String veit = "Veit Frick";


    public CreditScreen(ScreenManager manager) {
        super(manager);
        initUI();
    }

    private void initUI() {
        this.stage = new Stage();
        ScreenManager.multiplexer.addProcessor(this.stage);
        final Skin skin = new Skin(Gdx.files.internal("editor/uiskin.json"));

        Table scrollTable = new Table();

        Texture texture = new Texture("name.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());

        Image image = new Image(region);
        scrollTable.add(image);
        scrollTable.row();

        Label text = new Label("powered by libGDX\n", skin);
        scrollTable.add(text);
        scrollTable.row();

        text = new Label(credits, skin);
        text.setAlignment(Align.center);
        text.setWrap(true);
        scrollTable.add(text);
        scrollTable.row();

        TextButton button = new TextButton("Back", skin);
        button.setColor(Color.GREEN);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.setScreen(new MainMenuScreen(manager));
            }
        });
        ScrollPane scrollPane = new ScrollPane(scrollTable);

        Table table = new Table();
        table.setFillParent(true);
        table.add(scrollPane).fill().expand().row();
        table.add(button).size(150, 60).pad(10);

        this.stage.addActor(table);
    }

    @Override
    public void render() {
        this.stage.act();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
