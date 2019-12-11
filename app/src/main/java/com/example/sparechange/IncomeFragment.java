package com.example.sparechange;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class IncomeFragment  extends Fragment {
    GridView gridView;

    String[] categoryNames = {"Salary", "Sale", "Interest", "Award"};
    int[] categoryImage = {R.drawable.salary, R.drawable.sale, R.drawable.interest, R.drawable.award};
    public IncomeFragment(){

    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View category_grid = inflater.inflate(R.layout.category_grid, container, false);
        gridView = category_grid.findViewById(R.id.grid_view);
        CategoryAdapter adapter = new CategoryAdapter(getContext(),categoryNames,categoryImage);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "You Clicked" + categoryNames[+position], Toast.LENGTH_SHORT).show();
            }
        });

        return category_grid;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.item1:
                Toast.makeText(getActivity(),"Item 1 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(getActivity(),"Item 2Selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void ChosenCategory(View v){

    }
}