package com.example.randomdrinks;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class PopActivity extends GameActivity {
    private String message1, message2, message3, buttonText;

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

        String message1 = getIntent().getStringExtra("TITLE_TEXT");
        String message2 = getIntent().getStringExtra("SUB_TEXT");
        String message3 = getIntent().getStringExtra("OPTIONAL_TEXT");
        String buttonText = getIntent().getStringExtra("BUTTON_TEXT");

        TextView tittleMessage, subMessage, optionalMessage;
        Button button;

        //Hooks
        tittleMessage = findViewById(R.id.Title_Message);
        subMessage = findViewById(R.id.Sub_message);
        optionalMessage = findViewById(R.id.Optional_message);
        button = findViewById(R.id.close_btn);

        //Getting the screen size
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        //Setting the popup size
        getWindow().setLayout((int)(width*.8), (int) (height*.3));

        //Setting the popup position
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        //setting the text
        tittleMessage.setText(message1);
        subMessage.setText(message2);
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