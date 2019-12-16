package com.example.sparechange;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sparechange.Model.dialogDelete;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PerTransactionActivity extends AppCompatActivity {

    String databaseID;
    TextView textViewName, textViewType, textViewCateg, textViewAmount;
    Button buttonEdit, buttonDelete;
    DatabaseReference databaseTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_transaction);
        databaseTransactions = FirebaseDatabase.getInstance().getReference("transactions");

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewType = (TextView) findViewById(R.id.textViewType);
        textViewCateg = (TextView) findViewById(R.id.textViewCateg);
        textViewAmount = (TextView) findViewById(R.id.textViewAmount);
        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        Intent intent = getIntent();

        databaseID = intent.getStringExtra(MainActivity.TRANS_ID);
        textViewName.setText(intent.getStringExtra(MainActivity.TRANS_NAME));
        textViewType.setText(intent.getStringExtra(MainActivity.TRANS_TYPE));
        textViewCateg.setText(intent.getStringExtra(MainActivity.TRANS_CATEG));
        textViewAmount.setText(intent.getStringExtra(MainActivity.TRANS_AMOUNT));

        /* CONFIRMATION BEFORE DELETING */
//        buttonDelete.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                openDialog();
//            }
//        });

    }

    public void openDialog(){
        dialogDelete dialog = new dialogDelete();
        dialog.show(getSupportFragmentManager(), " dialog");
    }

    public void edit(View v){
        Intent intent = new Intent(this, editTransactionActivity.class);
        intent.putExtra("databaseID", databaseID);
        startActivity(intent);
    }

    public void delete(View v){
        databaseTransactions.child(databaseID).removeValue();
        Intent intent = new Intent (this, MainActivity.class);
        Toast.makeText(this,"Transaction Deleted", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
    //back()
}
