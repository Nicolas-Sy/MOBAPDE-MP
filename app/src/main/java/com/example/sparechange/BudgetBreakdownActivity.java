package com.example.sparechange;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;

public class BudgetBreakdownActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    PieChart pieChart;
    private TextView dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_breakdown);

        pieChart = (PieChart) findViewById(R.id.pieChart);
        dateText = (TextView) findViewById(R.id.textViewDate);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);


        ArrayList<PieEntry> chartValues = new ArrayList<>();

        chartValues.add(new PieEntry (34f, "Philippines"));
        chartValues.add(new PieEntry (23f, "USA"));
        chartValues.add(new PieEntry (14f, "UK"));
        chartValues.add(new PieEntry (35, "India"));
        chartValues.add(new PieEntry (40, "Russia"));
        chartValues.add(new PieEntry (23, "Japan"));

        PieDataSet dataSet = new PieDataSet(chartValues, "Countries");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData data = new PieData ((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);
    }

    public void Date(View v){
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
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = "Month/Day/Year: " + (month+1) + "/" + dayOfMonth + "/" + year;
        dateText.setText(date);
    }

    public void Back(View v){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
