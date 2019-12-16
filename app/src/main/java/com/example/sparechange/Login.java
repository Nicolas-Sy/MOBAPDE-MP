package com.example.sparechange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void Login(View v){

    }

    public void GoToRegister(View view){
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
        finish();
    }
}
