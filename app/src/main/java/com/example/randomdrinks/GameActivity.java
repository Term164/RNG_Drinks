package com.example.randomdrinks;

import androidx.appcompat.app.AppCompatActivity;

//A special activity modified for game functions
public class GameActivity extends AppCompatActivity {

    // Stoping and starting the background music on pause and resume
    @Override
    protected void onPause() {
        super.onPause();
        SoundHandler.pauseBackgroundMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SoundHandler.playBackgroundMusic();
    }
}
