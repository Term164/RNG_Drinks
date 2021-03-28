package com.example.randomdrinks;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
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

        // Hooks
        numberOFPlayers = findViewById(R.id.number_of_players);
        startingNumber = findViewById(R.id.starting_number);

        // Animations
        animationHandler = new AnimationHandler(this);

    }


    // Starts a new instance of the game
    public void beginGame(final View view){
        SoundHandler.playSound(R.raw.button_push);
        animationHandler.buttonPressAnimation(view);


        String num_Of_Players = numberOFPlayers.getText().toString();
        String starting_number = startingNumber.getText().toString();


        try {
            if (!num_Of_Players.equals("") && !starting_number.equals("")){
                final int players = Integer.parseInt(num_Of_Players);
                final int number = Integer.parseInt(starting_number);
                if (Integer.parseInt(num_Of_Players) >= 2 && Integer.parseInt(num_Of_Players) <= 50){
                    if (Integer.parseInt(starting_number) >= 2 && Integer.parseInt(starting_number) <= 1000000){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                view.setAnimation(null);
                                newActivity = true;
                                Intent intent = new Intent(SetupMenu.this, PlayerSelect.class);
                                intent.putExtra("NUMBER_OF_PLAYERS", players);
                                intent.putExtra("STARTING_NUMBER", number);
                                startActivity(intent);
                            }
                        },200);

                    }else {

                        createPopup(R.string.error_title, R.string.error_type, R.string.number_error, R.string.error_button, 0.8 ,0.4,0,25);
                    }
                }else {
                    createPopup(R.string.error_title, R.string.error_type, R.string.player_error, R.string.error_button, 0.8, 0.4,0, 25);
                }
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