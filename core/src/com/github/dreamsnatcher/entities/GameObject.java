package com.github.dreamsnatcher.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.github.dreamsnatcher.utils.Assets;

public abstract class GameObject {
    public Vector2 position;
    public Vector2 dimension;
    public Vector2 origin;
    public Vector2 scale;
    public float rotation;
    protected Assets assets;

    public GameObject() {
        position = new Vector2();
        dimension = new Vector2(1, 1);
        origin = new Vector2();
        scale = new Vector2(1, 1);
        rotation = 0;
    }

    public void update(float deltaTime) {
    }

    public abstract void render(SpriteBatch batch);
}
