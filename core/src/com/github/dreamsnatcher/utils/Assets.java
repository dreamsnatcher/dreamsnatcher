package com.github.dreamsnatcher.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {

    public static TextureRegion spaceShip0;
    public static TextureRegion spaceShip1;
    public static TextureRegion spaceShip2;
    public static TextureRegion spaceShip3;
    public static TextureRegion planet;
    public static TextureRegion stars0;
    public static TextureRegion stars1;
    public static TextureRegion stars2;
    public static TextureRegion stars3;
    public static TextureRegion asteroid0;
    public static TextureRegion asteroid1;
    public static TextureRegion spaceBar;
    public static TextureRegion asteroid2;

    public static TextureRegion energyBar;
    public static TextureRegion energyPixel;

    public static void init(){
        spaceShip0 = new TextureRegion(new Texture("spaceship_speed0.png"));
        spaceShip0.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        spaceShip1 = new TextureRegion(new Texture("spaceship_speed1.png"));
        spaceShip1.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        spaceShip2 = new TextureRegion(new Texture("spaceship_speed2.png"));
        spaceShip2.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        spaceShip3 = new TextureRegion(new Texture("spaceship_speed3.png"));
        spaceShip3.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        planet = new TextureRegion(new Texture("planet_high_pop.png"));
        planet.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        stars0 = new TextureRegion(new Texture("space_1_v2.png"));
        stars0.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        stars1 = new TextureRegion(new Texture("space_2_v2.png"));
        stars1.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        stars2 = new TextureRegion(new Texture("space_3_v2.png"));
        stars2.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        stars3 = new TextureRegion(new Texture("space_4_v2.png"));
        stars3.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        asteroid0 = new TextureRegion(new Texture("asteriod_small01.png"));
        asteroid1 = new TextureRegion(new Texture("asteriod_small02.png"));
        asteroid2 = new TextureRegion(new Texture("asteriod_big01.png"));
        energyBar = new TextureRegion(new Texture("energybar_container.png"));
        energyPixel = new TextureRegion(new Texture("pixel.png"));
        spaceBar = new TextureRegion(new Texture("spacebar.png"));
        spaceBar.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void dispose() {
        spaceShip0.getTexture().dispose();
        planet.getTexture().dispose();
        stars0.getTexture().dispose();
        stars1.getTexture().dispose();
        stars2.getTexture().dispose();
        stars3.getTexture().dispose();


    }
}
