package com.example.randomdrinks;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class AnimationHandler {

    private final Context context;

    public AnimationHandler(Context context){
        this.context = context;
    }

    public void animate(View view, int animationNumber, int duration){
        Animation animation = AnimationUtils.loadAnimation(context, animationNumber);
        animation.setDuration(duration);
        view.setAnimation(animation);
    }

    public void animate(View view, int animationNumber){
        Animation animation = AnimationUtils.loadAnimation(context, animationNumber);
        view.setAnimation(animation);
    }

    public void buttonPressAnimation(View view){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale);
        view.startAnimation(animation);
    }
}
