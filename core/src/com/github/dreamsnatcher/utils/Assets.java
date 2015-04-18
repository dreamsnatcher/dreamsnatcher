package com.github.dreamsnatcher.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {

    public static TextureRegion spaceShip;
    public static TextureRegion planet;
    public static TextureRegion stars0;
    public static TextureRegion stars1;
    public static TextureRegion stars2;
    public static TextureRegion stars3;

    public static TextureRegion energyBar;
    public static TextureRegion energyPixel;

    public static void init(){
        spaceShip = new TextureRegion(new Texture("spaceship_speed0.png"));
        spaceShip.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        planet = new TextureRegion(new Texture("planet.png"));
        planet.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        stars0 = new TextureRegion(new Texture("space_1_v2.png"));
        stars0.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        stars1 = new TextureRegion(new Texture("space_2_v2.png"));
        stars1.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        stars2 = new TextureRegion(new Texture("space_3_v2.png"));
        stars2.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        stars3 = new TextureRegion(new Texture("space_4_v2.png"));
        stars3.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        energyBar = new TextureRegion(new Texture("energybar_container.png"));
        energyBar.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        energyPixel = new TextureRegion(new Texture("pixel.png"));
        energyPixel.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

    }

    @Override
    public void dispose() {
        spaceShip.getTexture().dispose();
        planet.getTexture().dispose();
        stars0.getTexture().dispose();
        stars1.getTexture().dispose();
        stars2.getTexture().dispose();
        stars3.getTexture().dispose();

        energyBar.getTexture().dispose();
        energyPixel.getTexture().dispose();


    }
}
