package com.github.dreamsnatcher.entities;

/**
 * Created by lknoch on 18.04.15.
 */
public class PlanetBonusRotating extends Planet{

    public PlanetBonusRotating() {
        super();
        MAX_ENERGY = 200f;
        this.doRotation = true;
    }
}
