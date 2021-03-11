package com.example.randomdrinks;

import androidx.annotation.RequiresApi;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import java.util.Objects;
import java.util.Random;

public class MainActivity extends GameActivity {


    //Variables
    public View overlay;
    public TextView new_random_number_text,number_of_sips_text;
    private int number_of_sips = 0, prev_random, current_random, starting_number, number_of_players;
    float current_percentage = 0f;
    public String[] endMessages = {"Tough luck!", "Better luck next time!", "Bottoms up!", "Out of luck!", "Unfortunate!", "Hard luck!", "Drinking time!", "Yikes!", "Ouch!", "Bad break!", "Cheers!", "Down the hatch!", "Drink up!"};

    Animation scale;

    Random random = new Random();
    private ValueAnimator animator = new ValueAnimator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        layout = R.layout.activity_main;
        setContentView(layout);

        //Hooks
        new_random_number_text = findViewById(R.id.new_random_text);
        number_of_sips_text = findViewById(R.id.Sub_message);
        overlay = findViewById(R.id.overlay);
        overlay.setVisibility(View.INVISIBLE);

        //Getting data from previous activity
        prev_random = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("STARTING_NUMBER")));
        number_of_players = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("NUMBER_OF_PLAYERS")));
        current_random = starting_number = prev_random;

        //Animations
        scale = AnimationUtils.loadAnimation(this, R.anim.scale);
    }

    //When the button is clicked this function is called
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextRandom(final View view){
        view.startAnimation(scale);
        SoundHandler.playSound(R.raw.pour);
        valid_click(view);
    }


    private void valid_click(final View view){

        prev_random = current_random;
        current_random = random.nextInt(current_random)+1;

        //custom randomization
        //Currently if
        System.out.println(number_of_sips + " | " + number_of_players);
        if (number_of_sips == number_of_players && current_random == 1){
            System.out.println("second chance");
            if (Math.random() >= 0.1){
                do {
                    current_random = random.nextInt(prev_random)+1;
                }while (current_random == 1);
                System.out.println("nice");
            }
        }

        number_of_sips++;


        int delay = 1500;
        if(prev_random == current_random) delay = 300;
        else if(prev_random - current_random < 20) delay = 1000;


        //System.out.println(delay);

        //Animating the numbers and background
        animator.setObjectValues(prev_random, current_random);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                new_random_number_text.setText(String.valueOf(valueAnimator.getAnimatedValue()));
            }
        });

        view.setEnabled(false);
        animator.setDuration(delay);
        animator.start(); //Numbers
        overlay.setVisibility(View.VISIBLE);
        //If you end the game fill the screen
        if(current_random == 1) scaleView(overlay, current_percentage, 1f, 1000); //Background
        else scaleView(overlay, current_percentage, 1f - (float) current_random/starting_number, delay); //Background
        current_percentage = 1f - (float) current_random/starting_number;

        number_of_sips_text.setText("Number of sips: " + number_of_sips); // Setting the new text

        // Check if a player has lost
        if (current_random == 1){
            //Show the lose text after the animations have finished
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    createPopup("YOU LOSE!", "You have "+ number_of_sips + " sips", endMessages[(int)(Math.random()*endMessages.length)], "ANOTHER ROUND!");

                    //Reset the values
                    current_random = prev_random = starting_number;
                    new_random_number_text.setText("" + starting_number);
                    number_of_sips_text.setText("" + 0);
                    number_of_sips = 0;
                    current_percentage = 0f;
                }
            }, delay);

            //Enable the button after 5 seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setEnabled(true);
                }
            },5000);
        }
        else {
            //Enable the button after 1 second
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setEnabled(true);
                }
            },delay);
        }
    }

    //TODO: Make an animation handler class
    //Background animation (Filling the glass)
    public void scaleView(View v, float startScale, float endScale, int delay) {
        Animation anim = new ScaleAnimation(
                1f, 1f, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(delay);
        v.startAnimation(anim);
    }

}