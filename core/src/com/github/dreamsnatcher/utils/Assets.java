package com.github.dreamsnatcher.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {
    //DO THIS WITH ASSETMANAGER LATER TODO
    public static TextureRegion spaceShip0;
    public static TextureRegion spaceShipCruzer0;
    public static TextureRegion spaceShipEmpty;
    public static TextureRegion planet;
    public static TextureRegion planetBonus;
    public static TextureRegion planetBonusLow;
    public static TextureRegion stars0;
    public static TextureRegion stars1;
    public static TextureRegion stars2;
    public static TextureRegion stars3;
    public static TextureRegion asteroid0;
    public static TextureRegion asteroid1;
    public static TextureRegion spaceBar;
    public static Animation spaceBarFinish;
    public static TextureRegion asteroid2;
    public static TextureRegion asteroid3;

    public static TextureRegion energyBar;
    public static TextureRegion energyPixel;
    public static TextureRegion penaltyPixel;
    public static TextureRegion spaceShipHarvest;
    public static TextureRegion spaceShipCruzerHarvest;
    public static Animation planetDead;
    public static TextureRegion planetLow;
    public static TextureRegion indicator;
    public static TextureRegion back;
    public static Animation planetBonusDead;

    public static TextureRegion bierpixel;
    public static TextureRegion schaumkrone;
    public static TextureRegion finishWookie;

    public static TextureRegion nightmare;

    //Animations
    public static Animation shipAnimationSpeed1;
    public static Animation shipAnimationSpeed2;
    public static Animation shipAnimationSpeed3;

    public static Animation wookieAnimation;


    public static Animation shipCruzerAnimationSpeed1;
    public static Animation shipCruzerAnimationSpeed2;
    public static Animation shipCruzerAnimationSpeed3;
    public static TextureRegion spaceShipCruzerEmpty;


    public static TextureRegion spaceShipFast0;
    public static TextureRegion spaceShipFastHarvest;
    public static TextureRegion spaceShipFastEmpty;
    public static Animation shipFastAnimationSpeed1;
    public static Animation shipFastAnimationSpeed2;
    public static Animation shipFastAnimationSpeed3;
    //static Array<Animation> allAnimations = new Array<Animation>();


    public static void init() {

        planetBonusDead =  loadAnimation("animations/dead_planet_3_", 2, 0.3f);
        planetDead =  loadAnimation("animations/dead_planet_1_", 2, 0.3f);
        spaceBarFinish = loadAnimation("animations/spacebar_landed_",3,0.3f);

        shipAnimationSpeed1 = loadAnimation("animations/spaceship_speed_0_", 3, 0.3f);
        shipAnimationSpeed2 = loadAnimation("animations/spaceship_speed_1_", 3, 0.3f);
        shipAnimationSpeed3 = loadAnimation("animations/spaceship_speed_2_", 3, 0.3f);

        wookieAnimation = loadAnimation("animations/wookie_",3 ,0.3f);

        shipCruzerAnimationSpeed1 = loadAnimation("animations/spaceship_cruzer_speed_0_", 3, 0.3f);
        shipCruzerAnimationSpeed2 = loadAnimation("animations/spaceship_cruzer_speed_1_", 3, 0.3f);
        shipCruzerAnimationSpeed3 = loadAnimation("animations/spaceship_cruzer_speed_2_", 3, 0.3f);

        shipFastAnimationSpeed1 = loadAnimation("animations/spaceship_speeder_speed_0_", 3, 0.3f);
        shipFastAnimationSpeed2 = loadAnimation("animations/spaceship_speeder_speed_1_", 3, 0.3f);
        shipFastAnimationSpeed3 = loadAnimation("animations/spaceship_speeder_speed_2_", 3, 0.3f);

        spaceShip0 = new TextureRegion(new Texture("spaceship_speed0.png"));
        spaceShipCruzer0 = new TextureRegion(new Texture("spaceship_cruzer_speed0.png"));
        spaceShip0.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        spaceShipCruzer0.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        spaceShipFast0= new TextureRegion(new Texture("spaceship_speeder_speed0.png"));
        spaceShipFast0.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        spaceShipFastHarvest = new TextureRegion(new Texture("spaceship_speeder_extract_energy.png"));
        spaceShipFastHarvest.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        spaceShipFastEmpty = new TextureRegion(new Texture("spaceship_speeder_empty.png"));
        spaceShipFastEmpty.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        spaceShipEmpty = new TextureRegion(new Texture("spaceship_empty.png"));
        spaceShipEmpty.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        spaceShipCruzerEmpty = new TextureRegion(new Texture("spaceship_cruzer_empty.png"));
        spaceShipCruzerEmpty.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        spaceShipHarvest = new TextureRegion(new Texture("spaceship_extract_energy.png"));
        spaceShipHarvest.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        spaceShipCruzerHarvest = new TextureRegion(new Texture("spaceship_cruzer_extract_energy.png"));
        spaceShipCruzerHarvest.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        planet = new TextureRegion(new Texture("planet_high_pop.png"));
        planet.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        planetLow = new TextureRegion(new Texture("planet_small_pop.png"));
        planetLow.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        planetBonus = new TextureRegion(new Texture("planet_3_high_pop.png"));
        planetBonus.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        planetBonusLow = new TextureRegion(new Texture("planet_3_small_pop.png"));
        planetBonusLow.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

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
        asteroid3 = new TextureRegion(new Texture("asteriod_small04.png"));
        energyBar = new TextureRegion(new Texture("energybar_container.png"));
        energyPixel = new TextureRegion(new Texture("pixel.png"));
        penaltyPixel = new TextureRegion(new Texture("penaltypixel.png"));
        indicator = new TextureRegion(new Texture("indicator.png"));
        back = new TextureRegion(new Texture("button_back.png"));
        back.flip(false, true);
        indicator.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        back.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        spaceBar = new TextureRegion(new Texture("spacebar.png"));
        spaceBar.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        bierpixel = new TextureRegion(new Texture("bierpixel.png"));
        bierpixel.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        schaumkrone = new TextureRegion(new Texture("schaumkrone.png"));
        schaumkrone.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


//        finishWookie = new TextureRegion(new Texture("wookie.png"));
//        finishWookie.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        nightmare = new TextureRegion(new Texture("nightmare.png"));
        nightmare.flip(false, true);
        nightmare.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

    }

    private static Animation loadAnimation(String path, int frames, float frameDuration) {
        TextureRegion[] regions = new TextureRegion[frames];

        for (int i = 0; i < frames; i++) {
            Texture tex = new Texture(Gdx.files.internal(path + i + ".png"));
            regions[i] = new TextureRegion(tex);
        }
        return new Animation(frameDuration, regions);
    }

    @Override
    public void dispose() {
        spaceShip0.getTexture().dispose();
        planet.getTexture().dispose();
        stars0.getTexture().dispose();
        stars1.getTexture().dispose();
        stars2.getTexture().dispose();
        stars3.getTexture().dispose();
        //allAnimations.dispose();
    }
}
