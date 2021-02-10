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

public class SetupMenu extends AppCompatActivity {

    EditText numberOFPlayers, startingNumber;
    Animation scale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setup_menu);

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
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("NUMBER_OF_PLAYERS", numberOFPlayers.getText().toString());
        intent.putExtra("STARTING_NUMBER", startingNumber.getText().toString());
        startActivity(intent);
    }
}