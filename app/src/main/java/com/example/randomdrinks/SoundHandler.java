package com.example.randomdrinks;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.HashMap;

public class SoundHandler {

    //Getting sound ID's
    private static final int buttonClick = R.raw.button_push;
    private static final int pourEffect = R.raw.pour;
    private static MediaPlayer intro;
    private static MediaPlayer backgroundMusic;


    //sound control variables
    private static boolean musicMuted = false;
    private static boolean soundMuted = false;
    private static float volume = 1f;

    //sound classes
    private static SoundPool soundPool;
    private static HashMap<Integer, Integer> soundPoolMap;

    public static void loadSounds(Context context){
        intro = MediaPlayer.create(context, R.raw.intro);
        backgroundMusic = MediaPlayer.create(context, R.raw.dance);

        AudioAttributes attributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        soundPool = new SoundPool.Builder().setAudioAttributes(attributes).build();
        soundPoolMap = new HashMap<>(2);
        soundPoolMap.put(buttonClick, soundPool.load(context, R.raw.button_push, 1));
        soundPoolMap.put(pourEffect, soundPool.load(context, R.raw.pour,2));
    }

    public static void playSound(int soundID){
        if (!soundMuted){
            try {
                soundPool.play(soundPoolMap.get(soundID), volume, volume, 1,0,1f);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void playIntro(){
        intro.start();
    }

    public static void playBackgroundMusic(){
        if (!musicMuted){
            try {
                backgroundMusic.start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void pauseBackgroundMusic(){
        try {
            backgroundMusic.pause();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // =========================== GETTERS AND SETTERS ==============================

    public static boolean isMusicMuted() {
        return musicMuted;
    }

    public static void setMusicMuted(boolean musicMuted) {
        SoundHandler.musicMuted = musicMuted;
    }

    public static boolean isSoundMuted() {
        return soundMuted;
    }

    public static void setSoundMuted(boolean soundMuted) {
        SoundHandler.soundMuted = soundMuted;
    }
}
