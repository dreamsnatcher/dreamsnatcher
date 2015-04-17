package com.github.dreamsnatcher.desktop.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {

    private static Assets instance;
    private AssetManager assetManager;
    private TextureAtlas atlas;

    private Assets(AssetManager assetManager) {
        init(assetManager);
    }

    public static Assets getInstance(AssetManager assetManager) {
        if (instance == null) {
            instance = new Assets(assetManager);
        }
        return instance;
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.load("images/assets.atlas", TextureAtlas.class);
        assetManager.finishLoading();
        atlas = assetManager.get("images/assets.atlas");
        for (Texture t : atlas.getTextures()) {
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear); //linear filter for smoothing pixels
        }
    }

    public TextureAtlas.AtlasRegion findRegion(String name) {
        return atlas.findRegion(name);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        instance = null;
    }
}
