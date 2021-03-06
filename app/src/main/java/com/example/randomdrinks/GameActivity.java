package com.example.randomdrinks;

import android.content.Intent;

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

    //Creates a popup Activity with all the messages
    protected void createPopup(String message1, String message2, String message3, String buttonText){
        Intent i = new Intent(getApplicationContext(), PopActivity.class);
        i.putExtra("TITLE_TEXT", message1);
        i.putExtra("SUB_TEXT", message2);
        i.putExtra("OPTIONAL_TEXT", message3);
        i.putExtra("BUTTON_TEXT", buttonText);
        startActivity(i);
    }

    //Creates a popup Activity without the optional message
    protected void createPopup(String message1, String message2, String buttonText){
        Intent i = new Intent(getApplicationContext(), PopActivity.class);
        i.putExtra("TITLE_TEXT", message1);
        i.putExtra("SUB_TEXT", message2);
        i.putExtra("OPTIONAL_TEXT", "");
        i.putExtra("BUTTON_TEXT", buttonText);
        startActivity(i);
    }
}


