package com.example.sparechange;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sparechange.Model.Reward;
import com.example.sparechange.Model.RewardAdapter;

import java.util.ArrayList;
import java.util.List;

public class RewardsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RewardAdapter adapter;

    List<Reward> rewardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        rewardList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        rewardList.add(
            new Reward(
                1,
                "Siomai Chao Fan P50 off",
                "Chowking",
                3000,
                R.drawable.siomaichaofan)
        );

        rewardList.add(
                new Reward(
                        2,
                        "Free Halo Halo",
                        "Chowking",
                        5000,
                        R.drawable.halohalo)
        );

        rewardList.add(
                new Reward(
                        3,
                        "Cheesy Classic P30 off",
                        "Jollibee",
                        2000,
                        R.drawable.cheesyclassic)
        );

        rewardList.add(
                new Reward(
                        1,
                        "Free Yum Burger",
                        "Jollibee",
                        5000,
                        R.drawable.yum)
        );

        adapter = new RewardAdapter(this, rewardList);
        recyclerView.setAdapter(adapter);

    }
}
