package com.example.sparechange;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sparechange.Model.Reward;
import com.example.sparechange.Model.RewardAdapter;
import com.example.sparechange.Model.Transaction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RewardsActivity extends AppCompatActivity implements RewardAdapter.onRewardListener{

    DatabaseReference databaseTransactions;
    float totalIncome, totalExpense, total;
    List<Transaction> transactions;
    RecyclerView recyclerView;
    RewardAdapter adapter;
    TextView txtViewSpareCoins;
    int spareCoins;
    List<Reward> rewardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        txtViewSpareCoins = (TextView) findViewById(R.id.txtViewSpareCoins);

        databaseTransactions = FirebaseDatabase.getInstance().getReference("transactions");
        transactions = new ArrayList<>();

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

        adapter = new RewardAdapter(this, rewardList, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseTransactions.addValueEventListener(new ValueEventListener() {
            int z = 0;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                transactions.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Transaction per_transaction = postSnapshot.getValue(Transaction.class);

                    if (per_transaction.getUserID().equals(userID)) {
                        transactions.add(per_transaction);
                        if (per_transaction.getAmount() > 0) {
                            totalIncome += per_transaction.getAmount();
                        } else if (per_transaction.getAmount() < 0) {
                            totalExpense += per_transaction.getAmount();
                        }
                    }


                    total = totalIncome + totalExpense;
                    spareCoins = (100*transactions.size());
                    spareCoins += 10000;

                    txtViewSpareCoins.setText(Integer.toString(spareCoins));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }


    public void buyReward(){

    }

    @Override
    public void onRewardClick(int position) {
        int bawas = rewardList.get(position).getPrice();

        if(spareCoins>=bawas){
            spareCoins -= bawas;
            txtViewSpareCoins.setText(Integer.toString(spareCoins));
            Toast.makeText(this, "Reward Purchased", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Not Enough Spare Coins", Toast.LENGTH_LONG).show();
        }

    }
}
