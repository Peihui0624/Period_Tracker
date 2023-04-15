package my.edu.utar.periodtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void scrollToView(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.putExtra("scrollTo", R.id.scroll_to_here);
        startActivity(intent);
    }

}