package my.edu.utar.periodtracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        boolean isFirstTime = sharedPreferences.getBoolean("isFirstTime", true);
//
//        if (isFirstTime) {
//            // Display your page
//            setContentView(R.layout.activity_main);
//
//            // Set the value of isFirstTime to false
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putBoolean("isFirstTime", false);
//            editor.apply();
//        } else {
//            // Display your main activity
//            Intent intent = new Intent(this, CalendarActivity.class);
//            startActivity(intent);
//            finish();
//        }

        Button btn = findViewById(R.id.RegisterButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    public void clickToView(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

}