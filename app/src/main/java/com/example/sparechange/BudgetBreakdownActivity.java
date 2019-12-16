package com.example.sparechange;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sparechange.Model.Transaction;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BudgetBreakdownActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    PieChart pieChart;
    private TextView dateText;
    DatabaseReference databaseTransactions, databaseUser;
    List<Transaction> transactions;
    List<Float> chartAmount = new ArrayList<>();
    List<String> chartCategory = new ArrayList<>();
    List<String> newchartCategory = new ArrayList<>();
    List<String> newchartAmount = new ArrayList<>();
    float total, total2;
    String[] months = {"Jan", "Feb", "Mar"};
    int [] earnings = {500, 800, 2000};
    int switchMode = 1;
    String transactionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_breakdown);

        pieChart = (PieChart) findViewById(R.id.pieChart);
        dateText = (TextView) findViewById(R.id.textViewDate);
        Spinner spinnerTransactionType = findViewById(R.id.spinnerTransactionType);
        spinnerTransactionType.setOnItemSelectedListener(this);

        databaseTransactions = FirebaseDatabase.getInstance().getReference("transactions");
        databaseUser = FirebaseDatabase.getInstance().getReference("users");

        transactions = new ArrayList<>();

    }

    public void Date(View v) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseTransactions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                transactions.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Transaction per_transaction = postSnapshot.getValue(Transaction.class);
                    transactions.add(per_transaction);
                }
                Log.d("transaction size: ", String.valueOf(transactions.size()));

                for(int i = 0; i < transactions.size(); i++){
                    chartAmount.add(i, transactions.get(i).getAmount());
                    chartCategory.add(i, transactions.get(i).getTransaction_category());
                }

                createExpenseChart();
                }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void createIncomeChart(){
        for(int k = 0; k < chartCategory.size(); k++){
            for(int l = k +1; l < chartCategory.size(); l++){
                if(chartCategory.get(k).equals(chartCategory.get(l))) {
                    chartAmount.set(k, Float.valueOf(chartAmount.get(k)+ chartAmount.get(l)));
                    chartCategory.remove(l);
                    chartAmount.remove(l);
                }
            }
        }

        List<PieEntry> chartValues = new ArrayList<>();
        for(int j = 0; j < chartAmount.size(); j++){
            if(transactions.get(j).getTransaction_type().equals("Income"))
                chartValues.add(new PieEntry(chartAmount.get(j), chartCategory.get(j)));
        }

        PieDataSet dataSet = new PieDataSet(chartValues, "Transactions");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);


        pieChart.setData(data);
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
    }

    public void createExpenseChart(){
        for(int k = 0; k < chartCategory.size(); k++){
            for(int l = k +1; l < chartCategory.size(); l++){
                if(chartCategory.get(k).equals(chartCategory.get(l))) {
                    chartAmount.set(k, Float.valueOf(chartAmount.get(k)+ chartAmount.get(l)));
                    chartCategory.remove(l);
                    chartAmount.remove(l);
                }
            }
        }

        List<PieEntry> chartValues = new ArrayList<>();
        for(int j = 0; j < chartAmount.size(); j++){
            if(transactions.get(j).getTransaction_type().equals("Expense"))
                chartValues.add(new PieEntry(chartAmount.get(j)*-1, chartCategory.get(j)));
        }

        PieDataSet dataSet = new PieDataSet(chartValues, "Transactions");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);


        pieChart.setData(data);
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = "Month/Day/Year: " + (month + 1) + "/" + dayOfMonth + "/" + year;
        dateText.setText(date);
    }

    public void Back(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        transactionType = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), transactionType, Toast.LENGTH_SHORT).show();

        if(transactionType.equals("Expense")){
            createExpenseChart();
        }
        else if(transactionType.equals("Income")){
            createIncomeChart();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
