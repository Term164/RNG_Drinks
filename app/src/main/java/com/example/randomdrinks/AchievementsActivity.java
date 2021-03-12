package com.example.randomdrinks;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class AchievementsActivity extends GameActivity {

    AnimationHandler animationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        layout = R.layout.activity_achievements;
        setContentView(layout);
        animationHandler = new AnimationHandler(this);
    }

    @Override
    public void back(View view) {
        animationHandler.buttonPressAnimation(view);
        super.back(view);
    }
}