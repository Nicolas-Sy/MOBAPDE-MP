package com.example.sparechange;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PerTransactionActivity extends AppCompatActivity {

    TextView textViewName, textViewType, textViewCateg, textViewAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_transaction);

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewType = (TextView) findViewById(R.id.textViewType);
        textViewCateg = (TextView) findViewById(R.id.textViewCateg);
        textViewAmount = (TextView) findViewById(R.id.textViewAmount);

        Intent intent = getIntent();

        textViewName.setText(intent.getStringExtra(MainActivity.TRANS_NAME));
        textViewType.setText(intent.getStringExtra(MainActivity.TRANS_TYPE));
        textViewCateg.setText(intent.getStringExtra(MainActivity.TRANS_CATEG));
        textViewAmount.setText(intent.getStringExtra(MainActivity.TRANS_AMOUNT));

    }

    //edit()

    //delete()

    //back()
}
