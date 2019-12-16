package com.example.sparechange.Model;

public class User {
    private String id;
    private String fullname;
    private String email;
    private String password;
    private float balance;

    public User(){

    }

    public User(String id, float balance){
        this.id = id;
        this.balance = balance;
    }

    public User(String id, String fullname, String email, String password){
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getFullname (String fullname){
        return fullname;
    }

    public void setFullname (String fullname){
        this.fullname = fullname;
    }

    public String getEmail (String email){
        return email;
    }

    public void setEmail (String email){
        this.email = email;
    }

    public String getPassword (String password){
        return password;
    }

    public void setPassword (String password){
        this.password = password;
    }




}
