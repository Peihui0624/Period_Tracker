package my.edu.utar.periodtracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword;
    private Button buttonRegister;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (editTextName.getText().toString().trim().isEmpty()) {
            editTextName.setError("Name is required.");
            editTextName.requestFocus();
            return;
        }

        if (editTextEmail.getText().toString().trim().isEmpty()) {
            editTextEmail.setError("Email is required.");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getText().toString().trim()).matches()) {
            editTextEmail.setError("Please enter a valid email.");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(editTextPassword.getText().toString().trim())) {
            editTextPassword.setError("Password is required.");
            editTextPassword.requestFocus();
            return;
        }

        if (editTextPassword.getText().toString().trim().length() < 6) {
            editTextPassword.setError("Password should be at least 6 characters long.");
            editTextPassword.requestFocus();
            return;
        }

//        // Store user data in SharedPreferences
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("name", editTextName.getText().toString().trim());
//        editor.putString("email", editTextEmail.getText().toString().trim());
//        editor.putString("password", editTextPassword.getText().toString().trim());
//        editor.apply();

        String userId = myRef.push().getKey();
        User user = new User(name, email, password);
        myRef.child(userId).setValue(user);

        // If registration is successful, navigate to LoginActivity
        Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}