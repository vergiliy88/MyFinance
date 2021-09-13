package com.example.myfinance;

import android.app.Application;

import androidx.room.Room;

import com.example.myfinance.data.DataBase;

public class App extends Application {

    public static App instance;

    private DataBase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, DataBase.class, "my_finance")
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public DataBase getDatabase() {
        return database;
    }
}