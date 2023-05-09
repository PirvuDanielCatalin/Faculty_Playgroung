package com.example.project.databases;

import com.example.project.daos.BookDao;
import com.example.project.models.Book;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Book.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BookDao bookDao();
}

