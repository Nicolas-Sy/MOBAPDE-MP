package com.example.sparechange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button addTransacBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addTransacBtn = findViewById(R.id.addTransacBtn);
    }

    public void AddTransaction(View v){
        Intent intent = new Intent(this, addTransaction.class);
        startActivity(intent);
    }


}
