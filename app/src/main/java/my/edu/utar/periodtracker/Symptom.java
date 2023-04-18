package my.edu.utar.periodtracker;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Symptom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom);

        ImageView backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        LinearLayout crampLayout = findViewById(R.id.cramp_layout);

        // Create an array of cramp images
        int[] crampImages = {R.drawable.stomach, R.drawable.migraine, R.drawable.cramp};

        // Keep track of the selected cramp index
        final int[] selectedCrampIndex = {-1};

        // Loop through the cramp images and create an ImageButton for each one
        for (int i = 0; i < crampImages.length; i++) {
            ImageButton crampButton = new ImageButton(this);
            crampButton.setImageResource(crampImages[i]);
            crampButton.setBackgroundColor(Color.TRANSPARENT);
            crampButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            crampButton.setPadding(20, 20, 20, 20);

            // Set the layout params to evenly distribute the width of each ImageButton
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            crampButton.setLayoutParams(params);

            // Set an OnClickListener to handle the click event
            final int crampIndex = i;
            crampButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // If the previously selected cramp is different, remove its border
                    if (selectedCrampIndex[0] != -1 && selectedCrampIndex[0] != crampIndex) {
                        ImageButton previouslySelectedCrampButton = (ImageButton) crampLayout.getChildAt(selectedCrampIndex[0]);
                        previouslySelectedCrampButton.setBackgroundResource(0);
                    }

                    // Toggle the border for the clicked ImageButton
                    if (selectedCrampIndex[0] == crampIndex) {
                        // If the clicked cramp is already selected, deselect it
                        v.setBackgroundResource(0);
                        selectedCrampIndex[0] = -1;
                    } else {
                        // If the clicked cramp is not selected, select it
                        v.setBackgroundResource(R.drawable.border);
                        selectedCrampIndex[0] = crampIndex;
                    }
                }
            });

            // Add the ImageButton to the LinearLayout
            crampLayout.addView(crampButton);
        }

    }
}