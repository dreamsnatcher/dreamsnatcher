package com.github.dreamsnatcher.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.github.dreamsnatcher.entities.GameObject;
import com.github.dreamsnatcher.entities.GameWorld;
import com.github.dreamsnatcher.entities.GameWorldSerializer;
import com.github.dreamsnatcher.entities.SpaceShip;

import javax.swing.*;
import java.io.File;

/**
 * Created by badlogic on 18/04/15.
 */
public class EditorController extends InputAdapter {
    public static enum EditorAction {
        None,
        Place,
        Pan,
        Drag
    }

    OrthographicCamera camera;
    GameWorld world;
    World b2World;
    EditorAction action;
    GameObject selected;
    Vector3 lastCamInput = new Vector3(-1, -1, -1);

    public EditorController() {
        camera = new OrthographicCamera(5, 5);
        newWorld();
    }

    public void saveWorld() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFileChooser fileChooser = new JFileChooser(new File("."));
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    GameWorldSerializer.serialize(world, file);
                }
            }
        });
    }

    public void loadWorld() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFileChooser fileChooser = new JFileChooser(new File("."));
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    final File file = fileChooser.getSelectedFile();
                    // this stuff needs to run on the libGDX UI thread
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            world = GameWorldSerializer.deserialize(new FileHandle(file.getAbsolutePath()));
                            newB2World();
                            world.spaceShip.init(b2World);
                            for (GameObject object : world.objects) {
                                object.init(b2World);
                            }
                            resetState();
                        }
                    });
                }
            }
        });
    }

    private void resetState() {
        selected = null;
        action = EditorAction.None;
        camera.position.set(0, 0, 0);
    }

    public void newWorld() {
        world = new GameWorld();
        newB2World();
        world.spaceShip = new SpaceShip();
        world.spaceShip.init(b2World);
        resetState();
    }

    private void newB2World() {
        if(b2World != null) {
            b2World.dispose();
        }
        b2World = new World(new Vector2(0, -9), true);
    }

    public void place(Class<?> clazz) {
        try {
            GameObject obj = (GameObject)clazz.newInstance();
            obj.init(b2World);
            action = EditorAction.Place;
            selected = obj;
            Vector3 pos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            selected.position.set(pos.x, pos.y);
        } catch (Throwable t) {
            throw new GdxRuntimeException("Couldn't create instance of " + clazz.getSimpleName(), t);
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 pos = camera.unproject(new Vector3(screenX, screenY, 0));
        Vector2 pos2 = new Vector2(pos.x, pos.y);

        if(action == EditorAction.Place) {
            selected.position.set(pos.x, pos.y);
        } else if(action == EditorAction.None) {
            selected = null;
            if(hitTest(world.spaceShip, pos2)) {
                selected = world.spaceShip;
            } else {
                for(GameObject obj: world.objects) {
                    if(hitTest(obj, pos2)) {
                        selected = obj;
                        break;
                    }
                }
            }
            if(selected != null) {
                action = EditorAction.Drag;
            } else {
                action = EditorAction.Pan;
            }
        }

        return true;
    }

    public boolean hitTest(GameObject obj, Vector2 pos) {
        Rectangle rect = new Rectangle(obj.position.x - obj.dimension.x / 2, obj.position.y - obj.dimension.y / 2, obj.dimension.x, obj.dimension.y);
        return rect.contains(pos);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector3 pos = camera.unproject(new Vector3(screenX, screenY, 0));

        if(action == EditorAction.Place) {
            selected.position.set(pos.x, pos.y);
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 pos = camera.unproject(new Vector3(screenX, screenY, 0));

        if(action == EditorAction.Drag) {
            selected.position.set(pos.x, pos.y);
        } else if (action == EditorAction.Pan) {
            Vector3 curr = new Vector3();
            Vector3 delta = new Vector3();
            camera.unproject(curr.set(screenX, screenY, 0));
            if (!(lastCamInput.x == -1 && lastCamInput.y == -1 && lastCamInput.z == -1)) {
                camera.unproject(delta.set(lastCamInput.x, lastCamInput.y, 0));
                delta.sub(curr);
                camera.position.add(delta.x, delta.y, 0);
            }
            lastCamInput.set(screenX, screenY, 0);
            return false;
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 pos = camera.unproject(new Vector3(screenX, screenY, 0));

        if(action == EditorAction.Place) {
            selected.position.set(pos.x, pos.y);
            world.objects.add(selected);
        }

        if(action == EditorAction.Drag) {
            selected.position.set(pos.x, pos.y);
        }

        if(action == EditorAction.Pan) {
            lastCamInput.set(-1, -1, -1);
        }

        action = EditorAction.None;

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.DEL && selected != null) {
            world.objects.remove(selected);
            selected = null;
            action = EditorAction.None;
        }
        return true;
    }
}
