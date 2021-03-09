package com.example.randomdrinks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//A special activity modified for game functions
public class GameActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        hideSystemUI();
    }

    // This snippet hides the system bars.
    protected void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    // This snippet shows the system bars. It does this by removing all the flags
// except for the ones that make the content appear under the system bars.
    protected void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

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
        hideSystemUI();
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


