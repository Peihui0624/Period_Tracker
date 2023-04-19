package my.edu.utar.periodtracker;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewDateTime;
    private EditText editTextTitle, editTextNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);


        textViewDateTime = findViewById(R.id.textViewDateTime);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextNote = findViewById(R.id.editTextNote);

        // Set click listeners for the ImageViews
        ImageView backButton = findViewById(R.id.imageViewBack);
        ImageView deleteButton = findViewById(R.id.imageViewDelete);
        ImageView doneButton = findViewById(R.id.imageViewDone);

        // Set the click listener for all ImageViews
        backButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        doneButton.setOnClickListener(this);

        // Get the current date and time
        String currentDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // Set the current date and time to the TextView
        textViewDateTime.setText("Date and Time: " + currentDateTime);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewBack:
                // Handle "Go back to previous page" action
                Toast.makeText(NoteActivity.this, "Back Button Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NoteActivity.this, CalendarActivity.class);
                startActivity(intent);
                break;
            case R.id.imageViewDelete:
                // Handle "Delete" action
                Toast.makeText(NoteActivity.this, "Delete Button Clicked", Toast.LENGTH_SHORT).show();
                deleteNote();
                break;
            case R.id.imageViewDone:
                // Handle "Done" action
                Toast.makeText(NoteActivity.this, "Done Button Clicked", Toast.LENGTH_SHORT).show();
                saveNote();
                break;
        }
    }

    private void deleteNote() {
    }

    private void saveNote() {
        String textTitle = editTextTitle.getText().toString();
        String textNote = editTextNote.getText().toString();
        if(textTitle==null || textTitle.isEmpty()){
            editTextTitle.setError("Title is required");
            return;
        }
        NoteActivity2 note = new NoteActivity2();
        note.setTitle(textTitle);
        note.setNote(textNote);
        note.setTimestamp(Timestamp.now());

        saveNoteTo(note);
    }

    void saveNoteTo(NoteActivity2 note){

    }

}

