package com.example.sparechange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sparechange.Model.Transaction;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class addTransactionActivity extends AppCompatActivity {

    EditText tName, tAmount;
    RadioGroup type_radio;
    RadioButton typeBtn;
    List<Transaction> transactions;
    Button save;
    Date currentTime = Calendar.getInstance().getTime();
    DatabaseReference databaseTransactions, databaseCategories;
    TextView tCategory;
    private static final String TRANSACTION_NAME = "TRANSACTION_NAME", TRANSACTION_ID = "TRANSACTION_ID", TRANSACTION_AMOUNT = "TRANSACTION_AMOUNT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        databaseTransactions = FirebaseDatabase.getInstance().getReference("transactions");
        tName = findViewById(R.id.tName);
        tAmount = findViewById(R.id.tAmount);
        tCategory = findViewById(R.id.tCategory);

        type_radio = findViewById(R.id.type_radio);

        transactions = new ArrayList<>();



    }
    public void onClick(View v){
        Intent i = new Intent(getApplicationContext(),CategorySlider.class);
        startActivity(i);
    }
    public void checkButton(View v) {
        int radioId = type_radio.getCheckedRadioButtonId();
        typeBtn = findViewById(radioId);

        Toast.makeText(this, "Selected Radio Button: " + typeBtn, Toast.LENGTH_SHORT).show();
    }

    public void createTransaction(View v) {

        String name = tName.getText().toString();
        String category = tCategory.getText().toString();
        float amount = Float.parseFloat(tAmount.getText().toString()) * -1;
        float amount2 = Float.parseFloat(tAmount.getText().toString());
        String type = typeBtn.getText().toString();

        if (!(TextUtils.isEmpty(name) && TextUtils.isEmpty(type))) {
            String id = databaseTransactions.push().getKey();

            if (type.equals("Expenses")) {
                Transaction transaction = new Transaction(id, name, type, category, amount, currentTime);
                databaseTransactions.child(id).setValue(transaction);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra(TRANSACTION_NAME, name);
                    intent.putExtra(TRANSACTION_ID, id);
                    intent.putExtra(TRANSACTION_AMOUNT, amount);
                    startActivity(intent);
            } else if (type.equals("Income")) {
                Transaction transaction = new Transaction(id, name, type, category, amount2, currentTime);
                databaseTransactions.child(id).setValue(transaction);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra(TRANSACTION_NAME, name);
                    intent.putExtra(TRANSACTION_ID, id);
                    intent.putExtra(TRANSACTION_AMOUNT, amount2);
                    startActivity(intent);
            }

            tName.setText("");
            tAmount.setText("");
            tCategory.setText("");

            Toast.makeText(this, "Transaction added", Toast.LENGTH_LONG).show();


        } else {
            Toast.makeText(this, "Please enter a name and content", Toast.LENGTH_LONG).show();
        }

    }

    public void Back(View v){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    }
