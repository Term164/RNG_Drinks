package com.example.randomdrinks;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.Random;

public class MainActivity extends GameActivity {

    // Ad object
    private InterstitialAd mInterstitialAd;

    // Variables
    public View overlay;
    public TextView new_random_number_text, number_of_sips_text, current_player;
    private String[] players;
    private int number_of_sips = 0, prev_random, current_random, starting_number, number_of_players, playerTurn = 0, gameNumber = 0;
    float current_percentage = 0f;

    AnimationHandler animationHandler;

    Random random = new Random();
    private final ValueAnimator animator = new ValueAnimator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        layout = R.layout.activity_main;
        setContentView(layout);

        // Hooks
        new_random_number_text = findViewById(R.id.new_random_text);
        number_of_sips_text = findViewById(R.id.Sub_message);
        current_player = findViewById(R.id.current_player);
        overlay = findViewById(R.id.overlay);
        overlay.setVisibility(View.INVISIBLE);

        // Getting data from previous activity
        prev_random = getIntent().getIntExtra("STARTING_NUMBER", 0);
        number_of_players = getIntent().getIntExtra("NUMBER_OF_PLAYERS", 0);
        players = getIntent().getStringArrayExtra("PLAYERS");

        // Setting up the main display
        current_player.setText(current_player.getText() + " \n" + players[0]);
        if (prev_random >= 100000) new_random_number_text.setTextSize(100);

        current_random = starting_number = prev_random;

        // Animations
        animationHandler = new AnimationHandler(this);

        // Ad setup
        loadAd();
    }

    //When the button is clicked this function is called
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextRandom(final View view){
        animationHandler.buttonPressAnimation(view);
        SoundHandler.playSound(R.raw.pour);
        valid_click(view);
    }


    private void valid_click(final View view){
        prev_random = current_random;
        current_random = random.nextInt(current_random)+1;
        // custom randomization
        // Currently if
        if (number_of_sips == number_of_players && current_random == 1){
            if (Math.random() >= 0.1){
                do {
                    current_random = random.nextInt(prev_random)+1;
                }while (current_random == 1);
            }
        }

        number_of_sips++;

        int delay = 1500;
        if(prev_random == current_random) delay = 300;
        else if(prev_random - current_random < 20) delay = 1000;

        // Animating the numbers and background
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
        // If you end the game fill the screen
        if(current_random == 1) scaleView(overlay, current_percentage, 1f, 1000); //Background
        else scaleView(overlay, current_percentage, 1f - (float) current_random/starting_number, delay); //Background
        current_percentage = 1f - (float) current_random/starting_number;

        number_of_sips_text.setText(getString(R.string.num_of_sips) + " " + number_of_sips); // Setting the new text

        // Check if a player has lost
        if (current_random == 1){
            gameNumber++;
            // Show the lose text after the animations have finished
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new_random_number_text.setText("" + starting_number);
                    number_of_sips_text.setText(getString(R.string.num_of_sips) + " " +0);

                    createPopup(R.string.popup_title, R.string.popup_sips, R.string.messages, R.string.lose_button_text, number_of_sips, players[playerTurn%number_of_players]);

                    // Reset the values
                    current_random = prev_random = starting_number;
                    number_of_sips = 0;
                    current_percentage = 0f;
                    scaleView(overlay,0,0,1);

                }
            }, delay);

            // Enable the button after 5 seconds
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mInterstitialAd != null && gameNumber%3 == 0){
                        setCallback();
                        SoundHandler.pauseBackgroundMusic();
                        mInterstitialAd.show(MainActivity.this);
                    } else Log.d("not Ready", "The interstitial ad wasn't ready yet.");
                    view.setEnabled(true);
                }
            },5000);
        }
        else {
            playerTurn++;
            // Enable the button after 1 second
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setEnabled(true);
                }
            },delay);
        }

        current_player.setText(getString(R.string.current_player) + " \n" + players[playerTurn%number_of_players]);
    }

    // Background animation (Filling the glass)
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

    // =========================== BUTTONS =================================

    @Override
    public void back(View view) {
        animationHandler.buttonPressAnimation(view);
        super.back(view);
    }

    public void show_rules(View view){
        SoundHandler.playSound(R.raw.button_push);
        animationHandler.buttonPressAnimation(view);
        howToPlayWindow();
    }

    // ============================= AD LOGIC ===============================
    private void loadAd(){
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback(){
            @Override
            public void onAdLoaded(@NonNull InterstitialAd InterstitialAd) {
                mInterstitialAd = InterstitialAd;
                Log.i("loadAd", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.i("loadFailed", loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });
    }

    private void setCallback(){
        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                Log.d("failedToLow", "The ad failed to show");
            }

            @Override
            public void onAdShowedFullScreenContent() {
                mInterstitialAd = null;
                Log.d("shown", "The add was shown");
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                Log.d("dissmised", "The ad was dismissed");
                SoundHandler.playBackgroundMusic();
                loadAd();
            }
        });
    }


}