package com.github.dreamsnatcher.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.github.dreamsnatcher.WorldController;
import com.github.dreamsnatcher.utils.Assets;

/**
 * Created by badlogic on 17/04/15.
 */
public class PlanetRotating extends Planet {

    public PlanetRotating() {
        super();
        doRotation = true;

    }
}
