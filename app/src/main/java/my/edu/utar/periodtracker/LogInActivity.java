package my.edu.utar.periodtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        int scrollToId = getIntent().getIntExtra("scrollTo", -1);
        if (scrollToId != -1) {
            View view = findViewById(scrollToId);
            view.post(new Runnable() {
                @Override
                public void run() {
                    view.getParent().requestChildFocus(view, view);
                }
            });
        }
    }
}