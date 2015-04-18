package com.github.dreamsnatcher.entities;

/**
 * Created by lknoch on 18.04.15.
 */
public class PlanetBonus extends Planet{

    public PlanetBonus() {
        super();
        MAX_ENERGY = MAX_ENERGY_BONUS;
    }
}
