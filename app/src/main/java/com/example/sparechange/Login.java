package com.example.sparechange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button register;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        register = (Button) findViewById(R.id.buttonRegister);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();

    }

    public void Login(View v){

        String stringEmail = email.getText().toString().trim();
        String stringPassword = password.getText().toString().trim();

        if(TextUtils.isEmpty(stringEmail)){
            email.setError("Email is required!");
            return;
        }

        if(TextUtils.isEmpty(stringPassword)){
            email.setError("Password is required!");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        fAuth.signInWithEmailAndPassword(stringEmail, stringPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                else{
                    Toast.makeText(Login.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    public void GoToRegister(View view){
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
        finish();
    }
}
