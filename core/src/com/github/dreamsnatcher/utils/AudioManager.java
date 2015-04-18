package com.github.dreamsnatcher.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Veit on 18.04.2015.
 */
public class AudioManager {
        public static boolean mute = false;
        private static final float MUSIC_VOLUME = 0.7f;

        public static Sound move_fast;
        public static Sound move_regular;
        public static Sound move_slow;
        public static Music mainloop;
        public static Music fearloop;
        public static Sound ahit;
        public static Sound landing;
        public static Sound starting;

        public static Array<Sound> allSounds = new Array<Sound>();




        public static void init(){
		    mainloop = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainloop.wav"));
            fearloop = Gdx.audio.newMusic(Gdx.files.internal("sounds/fearloop.wav"));
            mainloop.setLooping(true);
            fearloop.setLooping(true);
            mainloop.play();

            move_fast = Gdx.audio.newSound(Gdx.files.internal("sounds/ufo-move-oh-fast.wav"));
            move_regular = Gdx.audio.newSound(Gdx.files.internal("sounds/ufo-move-oh-regular.wav"));
            move_slow = Gdx.audio.newSound(Gdx.files.internal("sounds/ufo-move-oh-slow.wav"));
            ahit = Gdx.audio.newSound(Gdx.files.internal("sounds/landen.wav")); //TODO DUMMY SOUND - ALTER HAT NICHT FUNKTIONIERT
            landing = Gdx.audio.newSound(Gdx.files.internal("sounds/landen.wav"));
            starting = Gdx.audio.newSound(Gdx.files.internal("sounds/abheben.wav"));



            allSounds.add(move_fast);
            allSounds.add(move_regular);
            allSounds.add(move_slow);
            allSounds.add(ahit);
            allSounds.add(landing);
            allSounds.add(starting);
        }

        public static void update(float deltaTime) {

        }

        public static void moveSlow(){
            move_regular.pause();
            move_fast.pause();
            move_slow.loop();
            move_slow.play();
        }
        public static void moveRegular(){
            move_slow.pause();
            move_fast.pause();
            move_regular.loop();
            move_regular.play();
        }
        public static void moveFast(){
            move_regular.pause();
            move_slow.pause();
            move_fast.loop();
            move_fast.play();
        }
        public static void stop(){
            move_regular.pause();
            move_fast.pause();
            move_slow.pause();
        }


        public static void dispose() {
            mainloop.dispose();
            fearloop.dispose();
            for(Sound sound: allSounds) {
                sound.dispose();
            }
        }


}
