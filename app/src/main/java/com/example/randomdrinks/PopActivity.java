package com.example.randomdrinks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class PopActivity extends GameActivity {

    //End messages
    public String[] endMessages = {"Tough luck!", "Better luck next time!", "Bottoms up!", "Out of luck!", "Unfortunate!", "Hard luck!", "Drinking time!", "Yikes!", "Ouch!", "Bad break!", "Cheers!", "Down the hatch!", "Drink up!"};
    public TextView end_message, sips_text;
    int number_of_sips;

    Button close_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        //Hooks
        end_message = findViewById(R.id.endMessage);
        sips_text = findViewById(R.id.sips_text);

        //Getting data from the previous activity
        number_of_sips = getIntent().getIntExtra("NUMBER_OF_SIPS", 0);

        //Moving the window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int) (height*.3));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        //setting the text
        end_message.setText(endMessages[(int)(Math.random()*endMessages.length)]);
        sips_text.setText("You have "+ number_of_sips +" sips");

        //Setting the close button
        close_btn = (Button) findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}