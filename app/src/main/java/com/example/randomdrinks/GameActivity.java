package com.example.randomdrinks;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

//A special activity modified for game functions
public class GameActivity extends AppCompatActivity {

    String currentLanguage;
    protected int layout;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Load the selected language
        loadLocale();
        // Make the app fullscreen
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

    // Stopping and starting the background music on pause and resume
    // Saving the current language for future reference
    @Override
    protected void onPause() {
        super.onPause();
        SoundHandler.pauseBackgroundMusic();
        // Getting the current language
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        currentLanguage = prefs.getString("My_Lang", "");
    }

    // Checking the selected language and the previously saved language for reference
    // If the language does not match reload the layout
    @Override
    protected void onResume() {
        super.onResume();
        SoundHandler.playBackgroundMusic();
        hideSystemUI();

        // Checking if current activity language matches the selected language
        // Getting the selected language
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        if (!prefs.getString("My_Lang", "").equals(currentLanguage)) {
            currentLanguage = prefs.getString("My_Lang", "");
            refreshLayout(layout);
        }

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

    // Creates a popup Activity without the optional message
    protected void createPopup(String message1, String message2, String buttonText){
        Intent i = new Intent(getApplicationContext(), PopActivity.class);
        i.putExtra("TITLE_TEXT", message1);
        i.putExtra("SUB_TEXT", message2);
        i.putExtra("OPTIONAL_TEXT", "");
        i.putExtra("BUTTON_TEXT", buttonText);
        startActivity(i);
    }

    // Multiple language support
    // Changes to provided language
    protected void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = this.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // Saving the new language preferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    // Loads the language from memory
    protected void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        currentLanguage = language;
        setLocale(language);
    }
    
    protected void refreshLayout(int layout){
        setContentView(layout);
    }

}


