package com.example.sparechange;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.TextView;

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

public class BudgetBreakdownActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    PieChart pieChart;
    private TextView dateText;
    DatabaseReference databaseTransactions, databaseUser;
    List<Transaction> transactions;
    List<Float> chartAmount = new ArrayList<>();
    List<String> chartCategory = new ArrayList<>();
    float total, total2;
    String[] months = {"Jan", "Feb", "Mar"};
    int [] earnings = {500, 800, 2000};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_breakdown);

        pieChart = (PieChart) findViewById(R.id.pieChart);
        dateText = (TextView) findViewById(R.id.textViewDate);

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
                        Log.d("chartAmounts: ", String.valueOf(chartAmount.get(i)));
                        Log.d("chartCategories: ", chartCategory.get(i));
                    }

                    List<PieEntry> chartValues = new ArrayList<>();
                    for(int j = 0; j < transactions.size(); j++){
                        if(transactions.get(j).getTransaction_type().equals("Expense"))
                            chartValues.add(new PieEntry(chartAmount.get(j)*-1, chartCategory.get(j)));
                        else
                            chartValues.add(new PieEntry(chartAmount.get(j), chartCategory.get(j)));
                    }

                    PieDataSet dataSet = new PieDataSet(chartValues, "Transactions");
                    dataSet.setSliceSpace(3f);
                    dataSet.setSelectionShift(5f);
                    dataSet.setColors(ColorTemplate.PASTEL_COLORS);

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


                    Legend l = pieChart.getLegend(); // get legend of pie
                    l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER); // set vertical alignment for legend
                    l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT); // set horizontal alignment for legend
                    l.setOrientation(Legend.LegendOrientation.VERTICAL); // set orientation for legend
                    l.setDrawInside(false); // set if legend should be drawn inside or not
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
}
