package com.example.randomdrinks;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PlayerSelect extends GameActivity {

    int numberOfPlayers, startingNumber;
    ListView playerList;
    private List<String> players;
    AnimationHandler animationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);

        // Connect a new animation handler
        animationHandler = new AnimationHandler(this);

        // Getting info from the previous activity
        numberOfPlayers = getIntent().getIntExtra("NUMBER_OF_PLAYERS", 0);
        startingNumber = getIntent().getIntExtra("STARTING_NUMBER", 0);

        playerList = findViewById(R.id.player_list);
        playerList.setItemsCanFocus(true);


        players = new ArrayList<String>();
        for (int i = 0; i < numberOfPlayers; i++){
            players.add(getString(R.string.player_list_element) + " " + (i+1));
        }

        ListViewAdapter adapter = new ListViewAdapter(this, players);
        playerList.setAdapter(adapter);
    }

    public void beginGame(final View view){
        animationHandler.buttonPressAnimation(view);
        SoundHandler.playSound(R.raw.button_push);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setAnimation(null);
                newActivity = true;
                Intent intent = new Intent(PlayerSelect.this, MainActivity.class);
                intent.putExtra("NUMBER_OF_PLAYERS", numberOfPlayers);
                intent.putExtra("STARTING_NUMBER", startingNumber);
                intent.putExtra("PLAYERS", players.toArray(new String[0]));
                startActivity(intent);
            }
        },200);
    }

    @Override
    public void back(View view) {
        animationHandler.buttonPressAnimation(view);
        super.back(view);
    }
}