package com.example.randomdrinks;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //End messages
    public String[] endMessages = {"Tough luck!", "Better luck next time!", "Bottoms up!", "Out of luck!", "Unfortunate!", "Hard luck!", "Drinking time!", "Yikes!", "Ouch!", "Bad break!", "Cheers!", "Down the hatch!", "Drink up!"};

    //Variables
    public View overlay;
    public TextView new_random_number_text,number_of_sips_text, end_message, lose_text;
    private int number_of_sips = 0, prev_random, current_random, starting_number;
    float current_percentage = 0f;

    Animation scale;

    Random random = new Random();
    private ValueAnimator animator = new ValueAnimator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Hooks
        new_random_number_text = findViewById(R.id.new_random_text);
        number_of_sips_text = findViewById(R.id.sips_text);
        end_message = findViewById(R.id.lose_message);
        lose_text = findViewById(R.id.you_lose_text);
        overlay = findViewById(R.id.overlay);
        overlay.setVisibility(View.INVISIBLE);

        //Getting data from previous activity
        prev_random = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("STARTING_NUMBER")));
        current_random = starting_number = prev_random;

        //Animations
        scale = AnimationUtils.loadAnimation(this, R.anim.scale);
    }

    //When the button is clicked this function is called
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextRandom(final View view){
        view.startAnimation(scale);
        // If someone lost (current_random == 1) then on the next click reset the game
        // Otherwise execute the normal code
        if (current_random == 1){
            end_message.setVisibility(View.INVISIBLE);
            lose_text.setVisibility(View.INVISIBLE);
            Button test = (Button)view;
            test.setText("Test your luck!");
            current_random = prev_random = starting_number;
            number_of_sips = 0;
            current_percentage = 0f;
            //Fill the glass on loss

        }
        else {
            SoundHandler.playSound(R.raw.pour);
            valid_click(view);
        }
    }


    public void valid_click(final View view){
        number_of_sips++;
        prev_random = current_random;
        current_random = random.nextInt(current_random)+1;

        int delay = 1500;
        /*if (prev_random < 10){
            delay = prev_random * 100;
        }
         */

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
            //Show all losing messages
            end_message.setText(endMessages[(int)(Math.random()*endMessages.length + 1)]); //Set a random end message
            end_message.setVisibility(View.VISIBLE);
            lose_text.setVisibility(View.VISIBLE);
            Button test = (Button)view;
            test.setText("ANOTHER ROUND!");

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