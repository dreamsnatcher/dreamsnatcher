package com.github.dreamsnatcher.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Veit on 18.04.2015.
 */
public class AudioManager {
        public boolean mute = false;
        private static final float MUSIC_VOLUME = 0.7f;

        public Sound move_fast;
        public Sound move_regular;
        public Sound move_slow;
        public Music mainloop;
        public Music fearloop;

        Array<Sound> allSounds = new Array<Sound>();




        public AudioManager() {
		    mainloop = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainloop.wav"));
            fearloop = Gdx.audio.newMusic(Gdx.files.internal("sounds/fearloop.wav"));
            mainloop.setLooping(true);
            fearloop.setLooping(true);

            move_fast = Gdx.audio.newSound(Gdx.files.internal("sounds/ufo-move-oh-fast.wav"));
            move_regular = Gdx.audio.newSound(Gdx.files.internal("sounds/ufo-move-oh-regular.wav"));
            move_slow = Gdx.audio.newSound(Gdx.files.internal("sounds/ufo-move-oh-slow.wav"));

            allSounds.add(move_fast);
            allSounds.add(move_regular);
            allSounds.add(move_slow);

        }

        public void update(float deltaTime) {

        }

        public void dispose() {
            mainloop.dispose();
            fearloop.dispose();
            for(Sound sound: allSounds) {
                sound.dispose();
            }
        }


}
