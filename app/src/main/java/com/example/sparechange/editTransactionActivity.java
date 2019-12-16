package com.example.sparechange;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sparechange.Model.Transaction;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class editTransactionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText tName, tAmount;
    List<Transaction> transactions;
    Button save;
    Date transacDate;
    DatabaseReference databaseTransactions, databaseCategories;
    TextView tCategory, dateText;
    String type, databaseID;
    private static final String TRANSACTION_NAME = "TRANSACTION_NAME", TRANSACTION_ID = "TRANSACTION_ID", TRANSACTION_AMOUNT = "TRANSACTION_AMOUNT";
    final Format formatter = new SimpleDateFormat("MM/DD/YYYY");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);
        databaseTransactions = FirebaseDatabase.getInstance().getReference("transactions");
        tName = findViewById(R.id.tName);
        tAmount = findViewById(R.id.tAmount);
        tCategory = findViewById(R.id.tCategory);
        dateText = findViewById(R.id.textViewDate);

        transactions = new ArrayList<>();

        Intent intent = getIntent();
        databaseID = intent.getStringExtra("databaseID");
    }

    public void onClick(View v){
        Intent i = new Intent(getApplicationContext(),CategorySlider.class);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 ){
            if(resultCode == 1)
            {
                tCategory.setText(data.getStringExtra("CATEGORY"));
                type = data.getStringExtra("TYPE");
            }

        }
    }

    public void updateTransaction(View v) {
        String name = tName.getText().toString();
        String category = tCategory.getText().toString();
        float amount = Float.parseFloat(tAmount.getText().toString()) * -1;
        float amount2 = Float.parseFloat(tAmount.getText().toString());

        if (!(TextUtils.isEmpty(name) && TextUtils.isEmpty(type))) {

            if (type.equals("Expense")) {
                Transaction transaction = new Transaction(databaseID, name, type, category, amount, transacDate);
                databaseTransactions.child(databaseID).setValue(transaction);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(TRANSACTION_NAME, name);
                intent.putExtra(TRANSACTION_ID, databaseID);
                intent.putExtra(TRANSACTION_AMOUNT, amount);
                startActivity(intent);
            } else if (type.equals("Income")) {
                Transaction transaction = new Transaction(databaseID, name, type, category, amount2, transacDate);
                databaseTransactions.child(databaseID).setValue(transaction);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(TRANSACTION_NAME, name);
                intent.putExtra(TRANSACTION_ID, databaseID);
                intent.putExtra(TRANSACTION_AMOUNT, amount2);
                startActivity(intent);
            }

            tName.setText("");
            tAmount.setText("");
            tCategory.setText("");

            Toast.makeText(this, "Transaction updated", Toast.LENGTH_LONG).show();


        } else {
            Toast.makeText(this, "Please enter a name and content", Toast.LENGTH_LONG).show();
        }

    }

    public void Date(View v){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int year = Calendar.getInstance().get(Calendar.YEAR);

        transacDate = getDateFromDatePicker(datePickerDialog.getDatePicker());
        String formatDate = formatter.format(transacDate);

        datePickerDialog.show();
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);


        return calendar.getTime();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = "Month/Day/Year: " + (month+1) + "/" + dayOfMonth + "/" + year;
        dateText.setText(date);
    }

    public void Back(View v){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void budgetBreakdown(View v){
        Intent intent = new Intent(this,BudgetBreakdownActivity.class);
        startActivity(intent);
        finish();
    }
}
