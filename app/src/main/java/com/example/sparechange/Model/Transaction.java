package com.example.sparechange.Model;

import java.util.Date;

public class Transaction {
    private String id;
    public String transaction_name;
    private String transaction_type;
    private String transaction_category;
    private float amount;
    private Date transaction_date;
    private String userID;

    public Transaction()
    {

    }

    public Transaction(String id, String transaction_name, String transaction_type, String transaction_category, float amount, Date transaction_date, String userID){
        this.id = id;
        this.transaction_name = transaction_name;
        this.transaction_type = transaction_type;
        this.transaction_category = transaction_category;
        this.amount = amount;
        this.transaction_date = transaction_date;
        this.userID = userID;
    }

    public String getUserID(){
        return userID;
    }

    public void setUserID (String userID){
        this.userID = userID;
    }

    public String getTransaction_name(){
        return transaction_name;
    }

    public String getTransaction_category(){
        return transaction_category;
    }

    public String getId(){
        return id;
    }

    public String getTransaction_type(){
        return transaction_type;
    }

    public float getAmount(){
        return amount;
    }

    public Date getTransaction_date() {
        return transaction_date;
    }
}
