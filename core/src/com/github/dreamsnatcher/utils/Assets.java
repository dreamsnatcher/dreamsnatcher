package com.github.dreamsnatcher.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {

    public static Texture spaceShip;
    public static Texture planet;

    public static void init(){
        spaceShip = new Texture("spaceship.png");
        planet = new Texture("");
    }

    @Override
    public void dispose() {
        spaceShip.dispose();;
    }
}
