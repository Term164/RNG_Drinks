package com.example.randomdrinks;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends GameActivity {

    private final int ANIMATION_DELAY = 200;

    //variables
    TextView title,developer;
    Button playButton, howToPlayButton, settingsButton, leaderboardButton, exitButton;
    AnimationHandler animationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        layout = R.layout.activity_main_menu;
        setContentView(layout);
        SoundHandler.playBackgroundMusic();

        //Animation handler
        animationHandler = new AnimationHandler(this);

        //Hooks
        title = findViewById(R.id.Title);
        developer = findViewById(R.id.developer);
        playButton = findViewById(R.id.play_button);
        howToPlayButton = findViewById(R.id.how_to_play_button);
        settingsButton = findViewById(R.id.settings_button);
        leaderboardButton = findViewById(R.id.achievements_button);
        exitButton = findViewById(R.id.exit_button);

        animationHandler.animate(title, R.anim.top_animation);
        animationHandler.animate(developer, R.anim.bottom_animation);
        animationHandler.animate(playButton, R.anim.left_animation, 1500);
        animationHandler.animate(howToPlayButton, R.anim.left_animation, 2000);
        animationHandler.animate(settingsButton, R.anim.left_animation, 2500);
        animationHandler.animate(leaderboardButton, R.anim.left_animation, 3000);
        animationHandler.animate(exitButton, R.anim.left_animation, 3500);
    }

    //Start a new instance of the game with the saved settings
    public void startGame(final View view){
        SoundHandler.playSound(R.raw.button_push);
        animationHandler.buttonPressAnimation(view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setAnimation(null);
                Intent intent = new Intent(MainMenu.this, SetupMenu.class);
                startActivity(intent);
            }
        },ANIMATION_DELAY);
    }

    public void howToPlay(final View view){
        SoundHandler.playSound(R.raw.button_push);
        animationHandler.buttonPressAnimation(view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setAnimation(null);
                howToPlayWindow();
            }
        },ANIMATION_DELAY);
    }

    //Open the settings window
    public void openSettings(final View view){
        SoundHandler.playSound(R.raw.button_push);
        animationHandler.buttonPressAnimation(view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setAnimation(null);
                Intent intent = new Intent(MainMenu.this, SettingsActivity.class);
                startActivity(intent);
            }
        },ANIMATION_DELAY);
    }

    // TODO: Rename function to openAchievements
    public void openLeaderboard(final View view){
        SoundHandler.playSound(R.raw.button_push);
        animationHandler.buttonPressAnimation(view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setAnimation(null);
                Intent intent = new Intent(MainMenu.this, AchievementsActivity.class);
                startActivity(intent);
            }
        },ANIMATION_DELAY);
    }

    //Close the application
    public void exit(final View view){
        SoundHandler.playSound(R.raw.button_push);
        animationHandler.buttonPressAnimation(view);
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