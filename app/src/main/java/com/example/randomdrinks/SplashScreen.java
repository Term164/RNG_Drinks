package com.example.randomdrinks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends GameActivity {

    private static int SPLASH_SCREEN = 5000;

    //Variables
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo, developer;
    View overlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        SoundHandler.loadSounds(this);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);


        //Hooks
        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.logo);
        developer = findViewById(R.id.developer);
        overlay = findViewById(R.id.overlay);
        overlay.setVisibility(View.INVISIBLE);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        developer.setAnimation(bottomAnim);



        SoundHandler.playIntro();

        //Run the filling liquid animation at the correct time
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scaleView(overlay, 0f, 1f, 3000); //Background
            }
        }, 2000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainMenu.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);

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