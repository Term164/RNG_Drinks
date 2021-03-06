package com.example.randomdrinks;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends GameActivity {

    private TextView musicText, soundText;
    private Button musicButton, soundButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.settings_activity);

        //Hooks
        musicText = findViewById(R.id.music_text);
        soundText = findViewById(R.id.sound_text);
        musicButton = findViewById(R.id.music_button);
        soundButton = findViewById(R.id.sound_button);

        soundCheck();
        musicCheck();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void musicClicked(View view){
        SoundHandler.musicMuted = !SoundHandler.musicMuted;
        musicCheck();
    }

    public void soundClicked(View view){
        SoundHandler.soundMuted = !SoundHandler.soundMuted;
        soundCheck();
    }

    // Helper functions
    public void musicCheck(){
        if(SoundHandler.musicMuted){
            SoundHandler.pauseBackgroundMusic();
            musicButton.setBackgroundResource(R.drawable.music_off);
            musicText.setText("MUSIC OFF");
        }else {
            SoundHandler.playBackgroundMusic();
            musicButton.setBackgroundResource(R.drawable.music_on);
            musicText.setText("MUSIC ON");
        }
    }

    public void soundCheck(){
        if(SoundHandler.soundMuted){
            soundButton.setBackgroundResource(R.drawable.volume_off);
            soundText.setText("SOUND OFF");
        }else {
            soundButton.setBackgroundResource(R.drawable.volume_on);
            soundText.setText("SOUND ON");
        }
    }
}