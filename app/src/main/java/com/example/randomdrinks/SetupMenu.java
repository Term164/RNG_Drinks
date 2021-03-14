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
    AnimationHandler animationHandler;

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
        animationHandler = new AnimationHandler(this);

    }


    //Starts a new instance of the game
    public void beginGame(View view){
        SoundHandler.playSound(R.raw.button_push);
        animationHandler.buttonPressAnimation(view);

        String num_Of_Players = numberOFPlayers.getText().toString();
        String starting_number = startingNumber.getText().toString();
        int heightOffset = (int) -(screenHeight*0.05);
        try {
            if (Integer.parseInt(num_Of_Players) >= 2 && Integer.parseInt(num_Of_Players) <= 50){
                if (Integer.parseInt(starting_number) >= 2 && Integer.parseInt(starting_number) <= 1000000){
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("NUMBER_OF_PLAYERS", num_Of_Players);
                    intent.putExtra("STARTING_NUMBER", starting_number);
                    startActivity(intent);
                }else {

                    createPopup(R.string.error_title, R.string.error_type, R.string.number_error, R.string.error_button, 0.8 ,0.4, heightOffset,heightOffset,heightOffset,0,0,165);
                }
            }else {
                createPopup(R.string.error_title, R.string.error_type, R.string.player_error, R.string.error_button, 0.8, 0.4, heightOffset,heightOffset,heightOffset,0,0,110);
            }
        }catch (Exception e){
            createPopup(R.string.error_title, R.string.error_type,R.string.error_button);
        }

    }

    @Override
    public void back(View view) {
        animationHandler.buttonPressAnimation(view);
        super.back(view);
    }
}