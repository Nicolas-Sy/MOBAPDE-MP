package com.example.sparechange;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class addTransaction extends AppCompatActivity {

    EditText name, amount, category;
    RadioGroup type_radio;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        name = findViewById(R.id.tName);
        amount = findViewById(R.id.tAmount);
        category = findViewById(R.id.tCategory);

        type_radio = findViewById(R.id.type_radio);


    }

    public void 
}
