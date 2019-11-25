package com.example.sparechange.Model;

import java.util.Date;

public class Transaction {
    private String transaction_name;
    private String transaction_type;
    private float amount;
    private Date transaction_date;

    public Transaction(){

    }

    public Transaction(String transaction_name, String transaction_type, float amount, Date transaction_date){
        transaction_name = this.transaction_name;
        transaction_type = this.transaction_type;
        amount = this.amount;
        transaction_date = this.transaction_date;

    }
}
