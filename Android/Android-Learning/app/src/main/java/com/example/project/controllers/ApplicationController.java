package com.example.project.controllers;

import android.app.Application;

import com.example.project.databases.AppDatabase;
import com.example.project.helpers.Constants;

import androidx.room.Room;

public class ApplicationController extends Application {
    //  If your app runs in a single process, you should follow the singleton design pattern
    //  when instantiating an AppDatabase object.
    private static ApplicationController mInstance;

    //  Each RoomDatabase instance is fairly expensive,
    //  and you rarely need access to multiple instances within a single process.
    private static AppDatabase mAppDatabase;

    public static ApplicationController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        // Get a database instance to work with
        mAppDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, Constants.DB_NAME).build();
    }

    public static AppDatabase getAppDatabase() {
        return mAppDatabase;
    }
}
