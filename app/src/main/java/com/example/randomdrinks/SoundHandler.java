package com.example.randomdrinks;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.HashMap;

public class SoundHandler {

    //Getting sound ID's
    public static final int buttonClick = R.raw.button_push;
    public static final int pourEffect = R.raw.pour;
    public static MediaPlayer intro;
    public static MediaPlayer backgroundMusic;


    //sound control variables
    public static boolean musicMuted = false;
    public static boolean soundMuted = false;
    public static float volume = 1f;

    //sound classes
    private static SoundPool soundPool;
    private static HashMap soundPoolMap;

    public static void loadSounds(Context context){
        intro = MediaPlayer.create(context, R.raw.intro);
        backgroundMusic = MediaPlayer.create(context, R.raw.dance);

        AudioAttributes attributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        soundPool = new SoundPool.Builder().setAudioAttributes(attributes).build();
        soundPoolMap = new HashMap(2);
        soundPoolMap.put(buttonClick, soundPool.load(context, R.raw.button_push, 1));
        soundPoolMap.put(pourEffect, soundPool.load(context, R.raw.pour,2));
    }

    public static void playSound(int soundID){
        if (!soundMuted){
            soundPool.play((int)soundPoolMap.get(soundID), volume, volume, 1,0,1f);
        }
    }

    public static void playIntro(){
        intro.start();
    }

    public static void playBackgroundMusic(){
        if (!musicMuted){
            backgroundMusic.start();
        }
    }

    public static void pauseBackgroundMusic(){
        backgroundMusic.pause();
    }
}
