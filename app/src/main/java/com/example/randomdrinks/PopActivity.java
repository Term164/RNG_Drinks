package com.example.randomdrinks;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class PopActivity extends GameActivity {


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

        int message1 = getIntent().getIntExtra("TITLE_TEXT", R.string.empty_string);
        int message2 = getIntent().getIntExtra("SUB_TEXT", R.string.empty_string);
        int message3 = getIntent().getIntExtra("OPTIONAL_TEXT", R.string.empty_string);
        int buttonText = getIntent().getIntExtra("BUTTON_TEXT", R.string.empty_string);
        double widthPercentage = getIntent().getDoubleExtra("WIDTH", 0.8);
        double heightPercentage = getIntent().getDoubleExtra("HEIGHT", 0.3);
        int message1Offset = getIntent().getIntExtra("MSG1OFFSET", 0);
        int message2Offset = getIntent().getIntExtra("MSG2OFFSET", 0);
        int message3Offset = getIntent().getIntExtra("MSG3OFFSET", 0);
        int buttonOffset = getIntent().getIntExtra("BTNOFFSET", 0);
        int msg2Height = getIntent().getIntExtra("MSG1HEIGHT", 0);
        int msg3Height = getIntent().getIntExtra("MSG2HEIGHT", 0);
        int numberOfSips = getIntent().getIntExtra("SIPS", 0);

        TextView tittleMessage, subMessage, optionalMessage;
        Button button;

        //Hooks
        tittleMessage = findViewById(R.id.Title_Message);
        subMessage = findViewById(R.id.Sub_message);
        optionalMessage = findViewById(R.id.Optional_message);
        button = findViewById(R.id.close_btn);

        tittleMessage.setTranslationY(message1Offset);
        subMessage.setTranslationY(message2Offset);
        optionalMessage.setTranslationY(message3Offset);
        button.setTranslationY(buttonOffset);

        subMessage.setHeight(subMessage.getHeight() + msg2Height);
        optionalMessage.setHeight(optionalMessage.getHeight() + msg3Height);

        //Getting the screen size
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        //Setting the popup size
        getWindow().setLayout((int)(width*widthPercentage), (int) (height*heightPercentage));

        //Setting the popup position
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        if (numberOfSips != 0){
            System.out.println("test");
            System.out.println(getString(message2));
            subMessage.setText(String.format(getString(message2), numberOfSips));
        } else {
            System.out.println("test2");
            subMessage.setText(message2);
        }

        //setting the text
        tittleMessage.setText(message1);
        optionalMessage.setText(message3);
        button.setText(buttonText);

        //Setting the close button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}