package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailSignUp, usernameSignUp, passwordSignUp, repasswordSignUp;
    private Button signUpButton;
    private DataBaseHelper myDB;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle("Music Player");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        emailSignUp = findViewById(R.id.signupemail);
        usernameSignUp = findViewById(R.id.signupusername);
        passwordSignUp = findViewById(R.id.siguppassword);
        repasswordSignUp = findViewById(R.id.repassword);
        signUpButton = findViewById(R.id.signupbutton);

        myDB = new DataBaseHelper(this);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate(usernameSignUp.getText().toString(), passwordSignUp.getText().toString(), repasswordSignUp.getText().toString())) {
                    insertUser();
                    String to=emailSignUp.getText().toString();
                    String subject="Sign Up Details";
                    String message=usernameSignUp.getText().toString()+" "+passwordSignUp.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                    intent.putExtra(Intent.EXTRA_TEXT, message);
                    intent.setType("message/rfc822");
                    startActivity(new Intent(SignUpActivity.this , LoginActivity.class));
                    startActivity(Intent.createChooser(intent, "Send Email"));
                }
            }
        });
    }

    private void insertUser() {
        boolean var = myDB.registerUser(usernameSignUp.getText().toString(), emailSignUp.getText().toString(), passwordSignUp.getText().toString());
        if (var) {
            Toast.makeText(SignUpActivity.this, "User Registered Successfully !!", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(SignUpActivity.this, "Registration Error !!", Toast.LENGTH_SHORT).show();
    }

    public boolean validate(String usernameSignUp, String passwordSignUp, String repasswordSignUp) {
        if (!passwordSignUp.equals(repasswordSignUp)) {
            Toast.makeText(SignUpActivity.this, "Passswords do not match!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!isEmailValid(emailSignUp.getText().toString())) {
            Toast.makeText(SignUpActivity.this, "Email not valid!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}

