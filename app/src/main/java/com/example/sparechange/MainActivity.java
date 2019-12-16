package com.example.sparechange;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.sparechange.Model.Transaction;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final String TRANS_ID = "TRANS_ID";
    public static final String TRANS_NAME = "TRANS_NAME";
    public static final String TRANS_TYPE = "TRANS_TYPE";
    public static final String TRANS_CATEG = "TRANS_CATEG";
    public static final String TRANS_AMOUNT = "TRANS_AMOUNT";
    public static final String TRANS_DATE = "TRANS_DATE";

    private DrawerLayout drawer;
    DatabaseReference databaseTransactions, databaseUser;
    List<Transaction> transactions;
    ListView listViewTransactions;
    TextView totalAmount;
    Button addTransacBtn;
    float total, total2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseTransactions = FirebaseDatabase.getInstance().getReference("transactions");
        databaseUser = FirebaseDatabase.getInstance().getReference("users");

        totalAmount = findViewById(R.id.totalAmount);
        listViewTransactions = (ListView) findViewById(R.id.listViewPosts);
        addTransacBtn = findViewById(R.id.addTransacBtn);

        addTransacBtn = findViewById(R.id.addTransacBtn);
        listViewTransactions = findViewById(R.id.listViewPosts);
        totalAmount = findViewById(R.id.textViewBalance);

        //For Date - final Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar,
                                        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        transactions = new ArrayList<>();

        listViewTransactions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Transaction transaction = transactions.get(i);
               //For Date - String timestamp_string = formatter.format(transaction.getTransaction_date());

                Intent intent = new Intent(getApplicationContext(), PerTransactionActivity.class);
                intent.putExtra(TRANS_ID, transaction.getId());
                intent.putExtra(TRANS_NAME, transaction.getTransaction_name());
                intent.putExtra(TRANS_TYPE, transaction.getTransaction_type());
                intent.putExtra(TRANS_CATEG, transaction.getTransaction_category());
                intent.putExtra(TRANS_AMOUNT, transaction.getAmount());
                startActivity(intent);

            }
        });


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.nav_profile:
                Intent profile_intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                startActivity(profile_intent);
                break;

            case R.id.nav_budget:
                Intent budget_intent = new Intent(getApplicationContext(), BudgetBreakdownActivity.class);
                startActivity(budget_intent);
                break;

        }
        return true;
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.item1:
                Toast.makeText(this,"Item 1 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this,"Item 2Selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void AddTransaction(View v){
        Intent intent = new Intent(this, addTransactionActivity.class);
        startActivity(intent);
    }

    public void OpenBudgetBreakdownActivity(View v){
        Intent intent = new Intent(this, BudgetBreakdownActivity.class);
        startActivity(intent);
    }

    public float updateMoney(float money){
        total += money;
        return total;
    }

}
