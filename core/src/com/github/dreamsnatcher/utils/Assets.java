package com.github.dreamsnatcher.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {

    public static TextureRegion spaceShip;
    public static TextureRegion planet;

    public static void init(){

        spaceShip = new TextureRegion(new Texture("spaceship.png"));
        planet = new TextureRegion(new Texture("planet.png"));
    }

    @Override
    public void dispose() {
        spaceShip.getTexture().dispose();
        planet.getTexture().dispose();
    }
}
