package com.example.project.daos;

import com.example.project.models.Book;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface BookDao {

    @Query("SELECT * FROM book")
    List<Book> getAll();

    @Query("SELECT * FROM book WHERE id IN (:booksId)")
    List<Book> getAllByIds(Integer[] booksId);

    @Query("SELECT * FROM book WHERE id = :bookId")
    Book getById(Integer bookId);

    @Query("SELECT * FROM book WHERE title LIKE :title")
    Book getByTitle(String title);

    @Insert
    void insert(Book book);

    @Insert
    void insertAll(Book... books);

    @Delete
    void delete(Book book);

    @Delete
    void deleteAll(Book... books);
}
