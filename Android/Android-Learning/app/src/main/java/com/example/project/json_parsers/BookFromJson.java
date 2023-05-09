package com.example.project.json_parsers;

import com.example.project.models.Book;

import org.json.JSONException;
import org.json.JSONObject;

public class BookFromJson {
    private int id;
    private String title;
    private String author;
    private int year;

    public BookFromJson fromJSON(JSONObject jsonObject) throws JSONException {
        BookFromJson bookFromJson = new BookFromJson();
        bookFromJson.title = jsonObject.getString("title");
        bookFromJson.author = jsonObject.getString("author");
        bookFromJson.year = jsonObject.getInt("year");
        return bookFromJson;
    }

    public BookFromJson fromBook(Book book) {
        BookFromJson bookFromJson = new BookFromJson();
        bookFromJson.title = book.title;
        bookFromJson.author = book.author;
        bookFromJson.year = book.year;
        return bookFromJson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }
}
