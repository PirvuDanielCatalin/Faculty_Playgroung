package com.example.trickyapp.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.trickyapp.models.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE first_name LIKE :first_name AND last_name LIKE :last_name")
    User getByFirstAndLastName(String first_name, String last_name);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
}
