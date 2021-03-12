package com.example.randomdrinks;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class SettingsActivity extends GameActivity {

    private TextView musicText, soundText;
    private Button musicButton, soundButton;
    AnimationHandler animationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        layout = R.layout.settings_activity;
        setContentView(layout);

        animationHandler = new AnimationHandler(this);

        // Hooks
        hook();

        // Check the current state of sound/music and display the correct text/image.
        soundCheck();
        musicCheck();
    }

    //Change the language of the whole app
    public void changeLanguage(final View view){
        SoundHandler.playSound(R.raw.button_push);
        animationHandler.buttonPressAnimation(view);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String lang = ((Button) view).getText().toString();
                setLocale(lang);
                refreshLayout(layout);
                // We have to reestablish the hooks after we draw the new layout
                hook();
                soundCheck();
                musicCheck();
            }
        },200);
    }

    //Mutes or un mutes music depending on the current state
    public void musicClicked(View view){
        SoundHandler.playSound(R.raw.button_push);
        animationHandler.buttonPressAnimation(view);
        SoundHandler.setMusicMuted(!SoundHandler.isMusicMuted());
        musicCheck();
    }

    //Mutes or un mutes sounds depending on the current state
    public void soundClicked(View view){
        SoundHandler.playSound(R.raw.button_push);
        animationHandler.buttonPressAnimation(view);
        SoundHandler.setSoundMuted(!SoundHandler.isSoundMuted());
        soundCheck();
    }

    //Hooks on view elements
    private void hook(){
        musicText = findViewById(R.id.music_text);
        soundText = findViewById(R.id.sound_text);
        musicButton = findViewById(R.id.music_button);
        soundButton = findViewById(R.id.sound_button);
    }

    // Helper functions
    // Changes the button picture and text depending on the current state
    // Mutes or un mutes sound/music depending on the current state
    private void musicCheck(){
        if(SoundHandler.isMusicMuted()){
            SoundHandler.pauseBackgroundMusic();
            musicButton.setBackgroundResource(R.drawable.music_off);
            musicText.setText(R.string.music_off);
        }else {
            SoundHandler.playBackgroundMusic();
            musicButton.setBackgroundResource(R.drawable.music_on);
            musicText.setText(R.string.music_on);
        }
    }

    private void soundCheck(){
        if(SoundHandler.isSoundMuted()){
            soundButton.setBackgroundResource(R.drawable.volume_off);
            soundText.setText(R.string.sound_off);
        }else {
            soundButton.setBackgroundResource(R.drawable.volume_on);
            soundText.setText(R.string.sound_on);
        }
    }

    @Override
    public void back(View view) {
        animationHandler.buttonPressAnimation(view);
        super.back(view);
    }
}