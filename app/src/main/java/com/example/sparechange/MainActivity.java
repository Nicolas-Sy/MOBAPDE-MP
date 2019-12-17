package com.example.sparechange;

import android.content.Intent;
import android.graphics.Color;
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
import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DatePickerListener {
    String TAG = "MainActivity";
    public static final String TRANS_ID = "TRANS_ID";
    public static final String TRANS_NAME = "TRANS_NAME";
    public static final String TRANS_TYPE = "TRANS_TYPE";
    public static final String TRANS_CATEG = "TRANS_CATEG";
    public static final String TRANS_AMOUNT = "TRANS_AMOUNT";
    public static final String TRANS_DATE = "TRANS_DATE";


    private DrawerLayout drawer;
    DatabaseReference databaseTransactions, databaseUser;
    List<Transaction> transactions;
    ListView listViewTransactions, listViewTransactionsMonth, listViewTransactionsYear;
    TextView textViewIncome, textViewExpenses, totalAmount;
    Button addTransacBtn;
    float total, totalIncome, totalExpense;
    TextView yearMonth;
    int choosenYear = 2017;

    DateTime picked_date = new DateTime();
    final Format formatter = new SimpleDateFormat("yyyy-MM-dd");

    int spareCoins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseTransactions = FirebaseDatabase.getInstance().getReference("transactions");
        databaseUser = FirebaseDatabase.getInstance().getReference("users");

        addTransacBtn = findViewById(R.id.addTransacBtn);
        listViewTransactions = findViewById(R.id.listViewPosts);
//        listViewTransactionsMonth = findViewById(R.id.listViewPosts2);
//        listViewTransactionsYear =findViewById(R.id.listViewPosts3);
        totalAmount = findViewById(R.id.textViewBalance);
        textViewIncome = findViewById(R.id.textViewIncome);
        textViewExpenses = findViewById(R.id.textViewExpenses);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar,
                                        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        HorizontalPicker picker = findViewById(R.id.date_picker);
           picker.setListener(this)
                .setDays(900)
                .setOffset(15)
                .setDateSelectedColor(Color.DKGRAY)
                .setDateSelectedTextColor(Color.WHITE)
                .setMonthAndYearTextColor(Color.DKGRAY)
                .setTodayButtonTextColor(Color.DKGRAY)
                .setTodayDateTextColor(Color.DKGRAY)
                .setTodayDateBackgroundColor(Color.GRAY)
                .setUnselectedDayTextColor(Color.DKGRAY)
                .setDayOfWeekTextColor(Color.DKGRAY)
                .setUnselectedDayTextColor(Color.BLACK)
                .showTodayButton(true)
                .init();

        // or on the View directly after init was completed
        picker.setBackgroundColor(Color.LTGRAY);
        picker.setDate(picked_date);

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
            case R.id.nav_rewards:
                Intent rewards_intent = new Intent(getApplicationContext(), RewardsActivity.class);
                startActivity(rewards_intent);
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Intent logout_intent = new Intent(getApplicationContext(), Login.class);
                startActivity(logout_intent);
                finish();
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        total = 0;
        totalIncome = 0;
        totalExpense = 0;
        total = 0;
        databaseTransactions.addValueEventListener(new ValueEventListener() {
            int z = 0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                transactions.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Transaction per_transaction = postSnapshot.getValue(Transaction.class);
                    String timestamp_string = formatter.format(per_transaction.getTransaction_date());
                    String timestamp_string2 = formatter.format(picked_date.toDate());

                    if(per_transaction.getUserID().equals(userID) && (timestamp_string.equals(timestamp_string2))){
                        transactions.add(per_transaction);
                        if(per_transaction.getAmount() > 0){
                            totalIncome += per_transaction.getAmount();
                        }
                        else if(per_transaction.getAmount() < 0){
                            totalExpense += per_transaction.getAmount();
                        }
                    }

                    total = totalIncome + totalExpense;
                    textViewIncome.setText(Float.toString(totalIncome));
                    textViewExpenses.setText(Float.toString(totalExpense));
                    totalAmount.setText(Float.toString(total));

                }

               TransactionList transactionAdapter = new TransactionList(MainActivity.this ,transactions);
               listViewTransactions.setAdapter(transactionAdapter);
//               listViewTransactionsMonth.setAdapter(transactionAdapter);
//               listViewTransactionsYear.setAdapter(transactionAdapter);
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

    @Override
    public void onDateSelected(DateTime dateSelected) {
        //Toast.makeText(this, dateSelected.toString(), Toast.LENGTH_SHORT).show();

        picked_date = dateSelected;
        onStart();
    }

//    private void setNormalPicker() {
//        setContentView(R.layout.activity_calendar_test);
//        final Calendar today = Calendar.getInstance();
//
//        findViewById(R.id.month_picker).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(MainActivity.this, new MonthPickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(int selectedMonth, int selectedYear) {
//                        Log.d(TAG, "selectedMonth : " + selectedMonth + " selectedYear : " + selectedYear);
//                        Toast.makeText(MainActivity.this, "Date set with month" + selectedMonth + " year " + selectedYear, Toast.LENGTH_SHORT).show();
//                    }
//                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));
//
//                builder.setActivatedMonth(Calendar.JULY)
//                        .setMinYear(1990)
//                        .setActivatedYear(2017)
//                        .setMaxYear(2030)
//                        .setMinMonth(Calendar.FEBRUARY)
//                        .setTitle("Select trading month")
//                        .setMonthRange(Calendar.FEBRUARY, Calendar.NOVEMBER)
//                        // .setMaxMonth(Calendar.OCTOBER)
//                        // .setYearRange(1890, 1890)
//                        // .setMonthAndYearRange(Calendar.FEBRUARY, Calendar.OCTOBER, 1890, 1890)
//                        .showMonthOnly()
//                        // .showYearOnly()
//                        .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
//                            @Override
//                            public void onMonthChanged(int selectedMonth) {
//                                Log.d(TAG, "Selected month : " + selectedMonth);
//                                // Toast.makeText(MainActivity.this, " Selected month : " + selectedMonth, Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
//                            @Override
//                            public void onYearChanged(int selectedYear) {
//                                Log.d(TAG, "Selected year : " + selectedYear);
//                                // Toast.makeText(MainActivity.this, " Selected year : " + selectedYear, Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .build()
//                        .show();
//
//            }
//        });
//
//        findViewById(R.id.date_picker).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar cal = Calendar.getInstance();
//                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, null, 2017,
//                        cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
//                dialog.show();
//            }
//        });
//
//        final TextView year = (TextView) findViewById(R.id.year);
//        findViewById(R.id.choose_year).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(MainActivity.this, new MonthPickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(int selectedMonth, int selectedYear) {
//                        year.setText(Integer.toString(selectedYear));
//                        choosenYear = selectedYear;
//                    }
//                }, choosenYear, 0);
//
//                builder.showYearOnly()
//                        .setYearRange(1990, 2030)
//                        .build()
//                        .show();
//            }
//        });
//
//    }
}
