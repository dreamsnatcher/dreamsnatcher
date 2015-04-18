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
//        private static final float MUSIC_VOLUME = 0.7f;


        public static final float PLAYEMPTYTIME = 5;

        public static float timer = 0;
        public static boolean playempty = false;

        public static Sound move_fast;
        public static Sound move_regular;
        public static Sound move_slow;
        public static Music mainloop;
        public static Music fearloop;
        public static Music havana;
        public static Sound ahit;
        public static Sound landing;
        public static Sound starting;
        public static Music empty;

        public static Array<Sound> allSounds = new Array<Sound>();

        public static float soundvolume = 0.2f;
        public static float musicvolume = 1f;


        public static void init(){
            empty = Gdx.audio.newMusic(Gdx.files.internal("sounds/empty_planet_mixdown.mp3"));
		    mainloop = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainloop.wav"));
            fearloop = Gdx.audio.newMusic(Gdx.files.internal("sounds/fearloop.wav"));
            havana = Gdx.audio.newMusic(Gdx.files.internal("sounds/havana.mp3"));
            mainloop.setLooping(true);
            fearloop.setLooping(true);
            mainloop.play();

            move_fast = Gdx.audio.newSound(Gdx.files.internal("sounds/ufo-move-oh-fast.wav"));
            move_regular = Gdx.audio.newSound(Gdx.files.internal("sounds/ufo-move-oh-regular.wav"));
            move_slow = Gdx.audio.newSound(Gdx.files.internal("sounds/ufo-move-oh-slow.wav"));
            ahit = Gdx.audio.newSound(Gdx.files.internal("sounds/ufo-hit-asteroid_01.mp3"));
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

            if(playempty){
                timer+=deltaTime;
                if(timer>=PLAYEMPTYTIME){
                    playempty = false;
                    timer = 0;
                    empty.stop();
                    mainloop.play();
                }
            }

        }


        public static void suckDryMusic(){
            timer = 0;
            playempty = true;
            mainloop.stop();
            empty.play();
        }

        public static void havanaMusic(){
            mainloop.stop();
            empty.stop();
            havana.setLooping(true);
            havana.play();
        }


    public static void moveSlow(){
            move_regular.stop();
            move_fast.stop();
            move_slow.setPitch(move_slow.loop(soundvolume),0.4f);
        }
        public static void moveRegular(){
            move_slow.stop();
            move_fast.stop();
            move_regular.setPitch(move_regular.loop(soundvolume),0.4f);
        }
        public static void moveFast(){
            move_regular.stop();
            move_slow.stop();
            move_fast.setPitch(move_fast.loop(soundvolume),0.4f);
        }
        public static void stop(){
            move_regular.stop();
            move_fast.stop();
            move_slow.stop();
        }


        public static void dispose() {
            mainloop.dispose();
            fearloop.dispose();
            empty.dispose();
            havana.dispose();
            for(Sound sound: allSounds) {
                sound.dispose();
            }
        }


}
