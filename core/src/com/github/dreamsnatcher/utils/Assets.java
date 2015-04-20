package com.github.dreamsnatcher.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {
    //DO THIS WITH ASSETMANAGER LATER TODO
    public static TextureRegion spaceShip0;
    public static TextureRegion spaceShipCruzer0;
    public static TextureRegion spaceShipEmpty;
    public static Animation planet;
    public static Animation planetBonus;
    public static Animation planetBonusLow;
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
    public static Animation planetLow;
    public static TextureRegion indicator;
    public static TextureRegion back;
    public static Animation planetBonusDead;

    public static TextureRegion bierpixel;
    public static TextureRegion schaumkrone;

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

    static Array<Animation> allAnimations = new Array<Animation>();
    static Array<TextureRegion> allTextureRegions = new Array<TextureRegion>();


    public static void init() {


        /**
         * Begin Loading Animations
         */
        planetBonusDead =  loadAnimation("animations/dead_planet_3_", 2, 0.3f);
        allAnimations.add(planetBonusDead);
        planetDead =  loadAnimation("animations/dead_planet_1_", 2, 0.3f);
        allAnimations.add(planetDead);
        spaceBarFinish = loadAnimation("animations/spacebar_landed_",3,0.3f);
        allAnimations.add(spaceBarFinish);
        shipAnimationSpeed1 = loadAnimation("animations/spaceship_speed_0_", 3, 0.3f);
        allAnimations.add(shipAnimationSpeed1);
        shipAnimationSpeed2 = loadAnimation("animations/spaceship_speed_1_", 3, 0.3f);
        allAnimations.add(shipAnimationSpeed2);
        shipAnimationSpeed3 = loadAnimation("animations/spaceship_speed_2_", 3, 0.3f);
        allAnimations.add(shipAnimationSpeed3);
        wookieAnimation = loadAnimation("animations/wookie_",3 ,0.3f);
        allAnimations.add(wookieAnimation);
        shipCruzerAnimationSpeed1 = loadAnimation("animations/spaceship_cruzer_speed_0_", 3, 0.3f);
        allAnimations.add(shipCruzerAnimationSpeed1);
        shipCruzerAnimationSpeed2 = loadAnimation("animations/spaceship_cruzer_speed_1_", 3, 0.3f);
        allAnimations.add(shipCruzerAnimationSpeed2);
        shipCruzerAnimationSpeed3 = loadAnimation("animations/spaceship_cruzer_speed_2_", 3, 0.3f);
        allAnimations.add(shipCruzerAnimationSpeed3);
        shipFastAnimationSpeed1 = loadAnimation("animations/spaceship_speeder_speed_0_", 3, 0.3f);
        allAnimations.add(shipFastAnimationSpeed1);
        shipFastAnimationSpeed2 = loadAnimation("animations/spaceship_speeder_speed_1_", 3, 0.3f);
        allAnimations.add(shipFastAnimationSpeed2);
        shipFastAnimationSpeed3 = loadAnimation("animations/spaceship_speeder_speed_2_", 3, 0.3f);
        allAnimations.add(shipFastAnimationSpeed3);
        planet = loadAnimation("animations/planet_high_pop_", 2, 0.3f);
        allAnimations.add(planet);
        planetLow = loadAnimation("animations/planet_small_pop_", 2, 0.3f);
        allAnimations.add(planetLow);
        planetBonus = loadAnimation("animations/planet_3_high_pop_", 2, 0.3f);
        allAnimations.add(planetBonus);
        planetBonusLow = loadAnimation("animations/planet_3_small_pop_", 2, 0.3f);
        allAnimations.add(planetBonusLow);
        /**
         * End Loading Animations
         */


        /**
         * Begin Loading TextureRegions
         */
        spaceShip0 = new TextureRegion(new Texture("spaceship_speed0.png"));
        allTextureRegions.add(spaceShip0);
        spaceShipCruzer0 = new TextureRegion(new Texture("spaceship_cruzer_speed0.png"));
        allTextureRegions.add(spaceShipCruzer0);
        spaceShipFast0= new TextureRegion(new Texture("spaceship_speeder_speed0.png"));
        allTextureRegions.add(spaceShipFast0);
        spaceShipFastHarvest = new TextureRegion(new Texture("spaceship_speeder_extract_energy.png"));
        allTextureRegions.add(spaceShipFastHarvest);
        spaceShipFastEmpty = new TextureRegion(new Texture("spaceship_speeder_empty.png"));
        allTextureRegions.add(spaceShipFastEmpty);
        spaceShipEmpty = new TextureRegion(new Texture("spaceship_empty.png"));
        allTextureRegions.add(spaceShipEmpty);
        spaceShipCruzerEmpty = new TextureRegion(new Texture("spaceship_cruzer_empty.png"));
        allTextureRegions.add(spaceShipCruzerEmpty);
        spaceShipHarvest = new TextureRegion(new Texture("spaceship_extract_energy.png"));
        allTextureRegions.add(spaceShipHarvest);
        spaceShipCruzerHarvest = new TextureRegion(new Texture("spaceship_cruzer_extract_energy.png"));
        allTextureRegions.add(spaceShipCruzerHarvest);
        stars0 = new TextureRegion(new Texture("space_1_v2.png"));
        allTextureRegions.add(stars0);
        stars1 = new TextureRegion(new Texture("space_2_v2.png"));
        allTextureRegions.add(stars1);
        stars2 = new TextureRegion(new Texture("space_3_v2.png"));
        allTextureRegions.add(stars2);
        stars3 = new TextureRegion(new Texture("space_4_v2.png"));
        allTextureRegions.add(stars3);
        asteroid0 = new TextureRegion(new Texture("asteriod_small01.png"));
        allTextureRegions.add(asteroid0);
        asteroid1 = new TextureRegion(new Texture("asteriod_small02.png"));
        allTextureRegions.add(asteroid1);
        asteroid2 = new TextureRegion(new Texture("asteriod_big01.png"));
        allTextureRegions.add(asteroid2);
        asteroid3 = new TextureRegion(new Texture("asteriod_small04.png"));
        allTextureRegions.add(asteroid3);
        energyBar = new TextureRegion(new Texture("energybar_container.png"));
        allTextureRegions.add(energyBar);
        energyPixel = new TextureRegion(new Texture("pixel.png"));
        allTextureRegions.add(energyPixel);
        penaltyPixel = new TextureRegion(new Texture("penaltypixel.png"));
        allTextureRegions.add(penaltyPixel);
        indicator = new TextureRegion(new Texture("indicator.png"));
        allTextureRegions.add(indicator);
        spaceBar = new TextureRegion(new Texture("spacebar.png"));
        allTextureRegions.add(spaceBar);
        bierpixel = new TextureRegion(new Texture("bierpixel.png"));
        allTextureRegions.add(bierpixel);
        schaumkrone = new TextureRegion(new Texture("schaumkrone.png"));
        allTextureRegions.add(schaumkrone);
        nightmare = new TextureRegion(new Texture("nightmare.png"));
        allTextureRegions.add(nightmare);
        nightmare.flip(false, true);
        for(TextureRegion tex: allTextureRegions){
            tex.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }

        /**
         * End Loading TextureRegions
         */

    }

    private static Animation loadAnimation(String path, int frames, float frameDuration) {
        TextureRegion[] regions = new TextureRegion[frames];

        for (int i = 0; i < frames; i++) {
            Texture tex = new Texture(Gdx.files.internal(path + i + ".png"));
            tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            regions[i] = new TextureRegion(tex);
        }
        return new Animation(frameDuration, regions);
    }

    @Override
    public void dispose() {
        for(TextureRegion tex: allTextureRegions){
            tex.getTexture().dispose();
        }
        for(Animation anim: allAnimations){
            for(int i = 0; i< anim.getKeyFrames().length; i++){
                anim.getKeyFrames()[i].getTexture().dispose();
            }
        }
    }
}
