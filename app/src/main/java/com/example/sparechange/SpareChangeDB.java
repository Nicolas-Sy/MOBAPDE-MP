package com.example.sparechange;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class SpareChangeDB extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
