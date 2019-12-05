package com.example.sparechange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sparechange.Model.Transaction;
import com.example.sparechange.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button addTransacBtn;
    DatabaseReference databaseTransactions, databaseUser;
    List<Transaction> transactions;
    ListView listViewTransactions;
    TextView totalAmount;
    float total = 0, total2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseTransactions = FirebaseDatabase.getInstance().getReference("transactions");
        databaseUser = FirebaseDatabase.getInstance().getReference("users");
        addTransacBtn = findViewById(R.id.addTransacBtn);
        listViewTransactions = findViewById(R.id.listViewPosts);
        totalAmount = findViewById(R.id.totalAmount);

        transactions = new ArrayList<>();

        listViewTransactions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Transaction transaction = transactions.get(i);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseTransactions.addValueEventListener(new ValueEventListener() {
            int z = 0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                transactions.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Transaction per_transaction = postSnapshot.getValue(Transaction.class);
                    total += per_transaction.getAmount();
                    totalAmount.setText(total + "");
                    transactions.add(per_transaction);
                }
                TransactionList transactionAdapter = new TransactionList(MainActivity.this ,transactions);
                listViewTransactions.setAdapter(transactionAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
/*
        databaseTransactions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting user
                    User per_user = postSnapshot.getValue(User.class);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
*/

    }


    public void AddTransaction(View v){
        Intent intent = new Intent(this, addTransaction.class);
        startActivity(intent);
    }

    public float updateMoney(float money){
        total += money;
        return total;
    }

}
