package com.example.project.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.helpers.Constants;
import com.example.project.models.Book;

import androidx.appcompat.app.AppCompatActivity;

public class NewBookForm extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_book_form);

        final Context context = this;

        Button button = findViewById(R.id.add_book_to_rv_btn);
        final EditText title_input = findViewById(R.id.title_input);
        final EditText author_input = findViewById(R.id.author_input);
        final EditText year_input = findViewById(R.id.year_input);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = title_input.getText().toString();
                String author = author_input.getText().toString();
                String yearString = year_input.getText().toString();

                if (!title.isEmpty() && !author.isEmpty() && !yearString.isEmpty()) {
                    int year = Integer.parseInt(yearString);
                    Book book = new Book(title, author, year);

                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("book", book);
                    startActivity(intent);
                } else {
                    Toast.makeText(context, Constants.ALL_REQUIRED, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
