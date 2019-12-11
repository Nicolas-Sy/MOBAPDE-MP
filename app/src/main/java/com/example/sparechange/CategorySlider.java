package com.example.sparechange;

import android.os.Bundle;


import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class CategorySlider extends AppCompatActivity {

    TabLayout myTabs;
    ViewPager myPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_slider);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myTabs = findViewById(R.id.myTabs);
        myPage = findViewById(R.id.myPage);

        myTabs.setupWithViewPager(myPage);

        SetUpViewPager(myPage);

    }
    public void SetUpViewPager (ViewPager viewpage){
        myViewPageAdapter Adapter = new myViewPageAdapter(getSupportFragmentManager());

        Adapter.AddFragmentPage(new ExpenseFragment(), "Expense");
        Adapter.AddFragmentPage(new IncomeFragment(), "Income");
        viewpage.setAdapter(Adapter);
    }


    public class myViewPageAdapter extends FragmentPagerAdapter{
        private List<Fragment> myFragment = new ArrayList<>();
        private List<String> myPageTitle = new ArrayList<>();

        public myViewPageAdapter(FragmentManager manager){
            super(manager);
        }

        public void AddFragmentPage(Fragment Frag, String Title){
            myFragment.add(Frag);
            myPageTitle.add(Title);

        }
        @Override
        public Fragment getItem(int position) {
            return myFragment.get(position);
        }

        public CharSequence getPageTitle(int position){
            return myPageTitle.get(position);
        }
        @Override
        public int getCount() {
            return 2;
        }
    }

}
