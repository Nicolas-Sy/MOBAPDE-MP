package com.example.sparechange;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sparechange.Model.Transaction;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;


    public class TransactionList extends ArrayAdapter<Transaction> {
        private Activity context;
        List<Transaction> transactions;
        final Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        public TransactionList(Activity context, List<Transaction> transactions) {
            super(context, R.layout.layout_transaction_list, transactions);
            this.context = context;
            this.transactions = transactions;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_transaction_list, null, true);

            TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewTitle);
            TextView textViewCategory = (TextView) listViewItem.findViewById(R.id.textViewCategory);
            TextView textViewDate = (TextView) listViewItem.findViewById(R.id.textViewDate);

            Transaction transaction = transactions.get(position);

            textViewName.setText(transaction.getTransaction_name());
            textViewCategory.setText(transaction.getTransaction_category());
            return listViewItem;
        }

    }
