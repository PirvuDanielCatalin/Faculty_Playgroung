package com.example.trickyapp.databases;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.trickyapp.daos.UserDao;
import com.example.trickyapp.models.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}

