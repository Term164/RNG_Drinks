package com.example.randomdrinks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    private final int ANIMATION_DELAY = 200;

    //variables
    TextView title,developer;
    Button playButton, settingsButton, leaderboardButton, exitButton;
    Animation topAnimation, leftAnimation1, leftAnimation2, leftAnimation3, leftAnimation4, bottomAnimation, scale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_menu);
        SoundHandler.playBackgroundMusic();

        //Animations
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        leftAnimation1 = AnimationUtils.loadAnimation(this, R.anim.left_animation);
        leftAnimation2 = AnimationUtils.loadAnimation(this, R.anim.left_animation);
        leftAnimation3 = AnimationUtils.loadAnimation(this, R.anim.left_animation);
        leftAnimation4 = AnimationUtils.loadAnimation(this, R.anim.left_animation);

        scale = AnimationUtils.loadAnimation(this, R.anim.scale);

        leftAnimation1.setDuration(1500);
        leftAnimation2.setDuration(2000);
        leftAnimation3.setDuration(2500);
        leftAnimation4.setDuration(3000);

        //Hooks
        title = findViewById(R.id.Title);
        developer = findViewById(R.id.developer);
        playButton = findViewById(R.id.play_button);
        settingsButton = findViewById(R.id.settings_button);
        leaderboardButton = findViewById(R.id.leaderboard_button);
        exitButton = findViewById(R.id.exit_button);

        title.setAnimation(topAnimation);
        playButton.setAnimation(leftAnimation1);
        settingsButton.setAnimation(leftAnimation2);
        leaderboardButton.setAnimation(leftAnimation3);
        exitButton.setAnimation(leftAnimation4);
        developer.setAnimation(bottomAnimation);
    }

    //Start a new instance of the game with the saved settings
    public void startGame(final View view){
        SoundHandler.playSound(R.raw.button_push);
        view.startAnimation(scale);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setAnimation(null);
                Intent intent = new Intent(MainMenu.this, SetupMenu.class);
                startActivity(intent);
            }
        },ANIMATION_DELAY);
    }

    //Open the settings window
    public void openSettings(final View view){
        SoundHandler.playSound(R.raw.button_push);
        view.startAnimation(scale);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setAnimation(null);
                Intent intent = new Intent(MainMenu.this, SettingsActivity.class);
                startActivity(intent);
            }
        },ANIMATION_DELAY);
    }

    public void openLeaderboard(final View view){
        SoundHandler.playSound(R.raw.button_push);
        view.startAnimation(scale);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setAnimation(null);
                Intent intent = new Intent(MainMenu.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        },ANIMATION_DELAY);
    }

    //Close the application
    public void exit(final View view){
        SoundHandler.playSound(R.raw.button_push);
        view.startAnimation(scale);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setAnimation(null);
                finish();
                System.exit(0);
            }
        },ANIMATION_DELAY);
    }


}