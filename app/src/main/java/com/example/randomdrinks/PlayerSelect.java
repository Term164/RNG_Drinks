package com.example.randomdrinks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerSelect extends GameActivity {

    int numberOfPlayers, startingNumber;
    ListView playerList;
    private List<String> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);

        // Getting info from the previous activity
        numberOfPlayers = getIntent().getIntExtra("NUMBER_OF_PLAYERS", 0);
        startingNumber = getIntent().getIntExtra("STARTING_NUMBER", 0);

        playerList = findViewById(R.id.player_list);
        playerList.setItemsCanFocus(true);

        players = new ArrayList<String>();
        for (int i = 0; i < numberOfPlayers; i++){
            players.add("Player " + (i+1));
        }

        ListViewAdapter adapter = new ListViewAdapter(this, players);
        playerList.setAdapter(adapter);
    }

    public void beginGame(View view){
        for (String s : players){
            System.out.println(s);
        }
    }
}