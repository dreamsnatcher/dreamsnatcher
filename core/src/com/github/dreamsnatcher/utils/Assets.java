package com.github.dreamsnatcher.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {

    public static TextureRegion spaceShip;
    public static Texture planet;
    public static TextureRegion stars0;
    public static TextureRegion stars1;
    public static TextureRegion stars2;
    public static TextureRegion stars3;

    public static void init(){
        spaceShip = new TextureRegion(new Texture("spaceship.png"));
        stars0 = new TextureRegion(new Texture("space_1_v2.png"));
        stars1 = new TextureRegion(new Texture("space_2_v2.png"));
        stars2 = new TextureRegion(new Texture("space_3_v2.png"));
        stars3 = new TextureRegion(new Texture("space_4_v2.png"));
    }

    @Override
    public void dispose() {
        spaceShip.getTexture().dispose();
    }
}
