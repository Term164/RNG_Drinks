package com.example.randomdrinks;

import android.icu.util.MeasureUnit;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class PopActivity extends GameActivity {

    AnimationHandler animationHandler;

    /**
     * Creates a new popup activity with custom
     * Strings as input.
     *
     * @author Jani Bangiev
     * @since 06/03/2021
     * message1 the title of the popup, takes a {@code String} value
     * message2 the smaller text under the title, takes a {@code String} value
     * message3 Optional text that can be displayed, takes a {@code String} value
     * buttonText The text that is displayed on the button, takes a {@code String} value
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        animationHandler = new AnimationHandler(this);

        int layout = getIntent().getIntExtra("LAYOUT", R.layout.activity_pop);
        int message1 = getIntent().getIntExtra("TITLE_TEXT", R.string.empty_string);
        int message2 = getIntent().getIntExtra("SUB_TEXT", R.string.empty_string);
        int message3 = getIntent().getIntExtra("OPTIONAL_TEXT", R.string.empty_string);
        int buttonText = getIntent().getIntExtra("BUTTON_TEXT", R.string.exit_button);
        double widthPercentage = getIntent().getDoubleExtra("WIDTH", 0.8);
        double heightPercentage = getIntent().getDoubleExtra("HEIGHT", 0.3);
        int msg2FontSize = getIntent().getIntExtra("MSG2FONTSIZE", 0);
        int msg3FontSize = getIntent().getIntExtra("MSG3FONTSIZE", 0);
        int numberOfSips = getIntent().getIntExtra("SIPS", 0);

        setContentView(layout);
        final Button button;

        button = findViewById(R.id.close_btn);

        // Getting the screen size
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        if (layout == R.layout.activity_how_to_play){

            // Setting the popup size
            getWindow().setLayout((int)(width*0.9), (int) (height*0.9));
        }
        else {

            // Setting the popup size
            getWindow().setLayout((int)(width*widthPercentage), (int) (height*heightPercentage));

            TextView tittleMessage, subMessage, optionalMessage;

            // Hooks
            tittleMessage = findViewById(R.id.Title_Message);
            subMessage = findViewById(R.id.Sub_message);
            optionalMessage = findViewById(R.id.Optional_message);


            // Setting the text font size
            if (msg2FontSize != 0) subMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, msg2FontSize);
            if (msg3FontSize != 0) optionalMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, msg3FontSize);

            if (numberOfSips != 0){
                subMessage.setText(String.format(getString(message2), numberOfSips));
            } else {
                subMessage.setText(message2);
            }

            // Setting the text
            tittleMessage.setText(message1);
            optionalMessage.setText(message3);

        }


        // Setting the popup position
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        button.setText(buttonText);

        // Setting the close button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundHandler.playSound(R.raw.button_push);
                animationHandler.animate(button, R.anim.scale);
                finish();
            }
        });
    }
}