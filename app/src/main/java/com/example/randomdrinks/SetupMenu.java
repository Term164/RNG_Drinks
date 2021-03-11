package com.example.randomdrinks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

public class SetupMenu extends GameActivity {

    EditText numberOFPlayers, startingNumber;
    Animation scale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        layout = R.layout.activity_setup_menu;
        setContentView(layout);

        //hooks
        numberOFPlayers = findViewById(R.id.number_of_players);
        startingNumber = findViewById(R.id.starting_number);

        //Animations
        scale = AnimationUtils.loadAnimation(this,R.anim.scale);

    }


    //Starts a new instance of the game
    public void beginGame(View view){
        SoundHandler.playSound(R.raw.button_push);
        view.startAnimation(scale);
        String num_Of_Players = numberOFPlayers.getText().toString();
        String starting_number = startingNumber.getText().toString();
        if (Integer.parseInt(num_Of_Players) >= 2 && Integer.parseInt(starting_number) >= 2){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("NUMBER_OF_PLAYERS", num_Of_Players);
            intent.putExtra("STARTING_NUMBER", starting_number);
            startActivity(intent);
        } else{
            createPopup("Ooops!", "Wrong numbers entered", "Please choose numbers above 2", "Close");
        }
    }
}