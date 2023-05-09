package com.example.project.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.helpers.Constants;
import com.example.project.interfaces.OnBookRepositoryActionListener;
import com.example.project.models.Book;
import com.example.project.repositories.BookRepository;

import androidx.appcompat.app.AppCompatActivity;

public class ShowBookForm extends AppCompatActivity {

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_book);

        final Book book = (Book) getIntent().getSerializableExtra("book");

        final BookRepository bookRepository = new BookRepository(this);

        final Book checkDB = bookRepository.getById(book.getId(), new OnBookRepositoryActionListener() {
            @Override
            public void actionSuccess() {

            }

            @Override
            public void actionFailed() {

            }
        });

        TextView title_tv = findViewById(R.id.title_tv);
        title_tv.setText(book.title);

        TextView author_tv = findViewById(R.id.author_tv);
        author_tv.setText(book.author);

        TextView year_tv = findViewById(R.id.year_tv);
        year_tv.setText(book.year + "");

        Button remove_btn = findViewById(R.id.remove_btn);
        remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkDB != null) {
                    bookRepository.delete(checkDB, new OnBookRepositoryActionListener() {
                        @Override
                        public void actionSuccess() {

                        }

                        @Override
                        public void actionFailed() {

                        }
                    });
                }

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("id", book.id);
                context.startActivity(intent);
            }
        });

        final Button star_btn = findViewById(R.id.star_btn);

        if (checkDB != null)
            star_btn.setBackgroundResource(R.drawable.star_f);
        else
            star_btn.setBackgroundResource(R.drawable.star_uf);

        star_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book temp = bookRepository.getById(book.getId(), new OnBookRepositoryActionListener() {
                    @Override
                    public void actionSuccess() {

                    }

                    @Override
                    public void actionFailed() {

                    }
                });

                if (temp != null) {
                    bookRepository.delete(temp, new OnBookRepositoryActionListener() {
                        @Override
                        public void actionSuccess() {
                            Toast.makeText(context, Constants.REMOVED_BOOK_MSG, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void actionFailed() {

                        }
                    });
                    star_btn.setBackgroundResource(R.drawable.star_uf);
                } else {
                    bookRepository.insert(book, new OnBookRepositoryActionListener() {
                        @Override
                        public void actionSuccess() {
                            Toast.makeText(context, Constants.ADDED_BOOK_MSG, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void actionFailed() {

                        }
                    });
                    star_btn.setBackgroundResource(R.drawable.star_f);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
