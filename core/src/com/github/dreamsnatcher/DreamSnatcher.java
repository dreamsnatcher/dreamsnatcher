package com.github.dreamsnatcher;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.github.dreamsnatcher.screens.Screen;
import com.github.dreamsnatcher.screens.ScreenManager;
import com.github.dreamsnatcher.screens.TestScreen;

public class DreamSnatcher implements ApplicationListener {
	ScreenManager manager;

	@Override
	public void create () {
		manager = new ScreenManager();
		// SET START SCREEN HERE!
		Screen screen = new TestScreen(manager);
		manager.setScreen(screen);
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		manager.render();
	}

	@Override
	public void pause () {
		manager.pause();
	}

	@Override
	public void resume () {
		manager.resume();
	}

	@Override
	public void dispose () {
		manager.dispose();
	}
}
