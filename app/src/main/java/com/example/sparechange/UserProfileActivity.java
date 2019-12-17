package com.example.sparechange;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sparechange.Model.Transaction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    DatabaseReference databaseTransactions;
    List<Transaction> transactions;
    float totalIncome, totalExpense, total;
    TextView totalSavings, txtViewSpareCoins;
    Button buttonRewards;
    int spareCoins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        totalSavings = (TextView) findViewById(R.id.totalSavings);
        txtViewSpareCoins = (TextView) findViewById(R.id.txtViewSpareCoins);
        buttonRewards = (Button) findViewById(R.id.buttonRewards);

        databaseTransactions = FirebaseDatabase.getInstance().getReference("transactions");
        transactions = new ArrayList<>();

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

                    totalSavings.setText(Float.toString(total));
                    txtViewSpareCoins.setText(Integer.toString(spareCoins));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//        databaseTransactions.addValueEventListener(new ValueEventListener() {
//            int z = 0;
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                transactions.clear();
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Transaction per_transaction = postSnapshot.getValue(Transaction.class);
//
//                    if (per_transaction.getUserID().equals(userID)) {
//                        transactions.add(per_transaction);
//                        if (per_transaction.getAmount() > 0) {
//                            totalIncome += per_transaction.getAmount();
//                        } else if (per_transaction.getAmount() < 0) {
//                            totalExpense += per_transaction.getAmount();
//                        }
//                    }
//
//
//                    total = totalIncome + totalExpense;
//                    spareCoins = (100*transactions.size());
//
//                    totalSavings.setText(Float.toString(total));
//                    txtViewSpareCoins.setText(Integer.toString(spareCoins));
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//
//
//        });
//    }

    public void goToRewards(View v){
        Intent intent = new Intent(this, RewardsActivity.class);
        startActivity(intent);
    }
}
