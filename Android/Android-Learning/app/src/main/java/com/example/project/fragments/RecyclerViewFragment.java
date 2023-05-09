package com.example.project.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.project.R;
import com.example.project.adapters.CustomAdapter;
import com.example.project.interfaces.OnBookRepositoryActionListener;
import com.example.project.json_parsers.BookFromJson;
import com.example.project.models.Book;
import com.example.project.repositories.BookRepository;
import com.example.project.singletons.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewFragment extends Fragment {
    private RecyclerView rvList;
    private CustomAdapter customAdapter;
    private ArrayList<Book> array;
    private BookRepository bookRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_activity_rv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvList = view.findViewById(R.id.recycler_view);
        bookRepository = new BookRepository(getContext());
        getToDoItems();
    }

    private void getToDoItems() {
        String url = "https://raw.githubusercontent.com/benoitvallon/100-best-books/master/books.json";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<BookFromJson> booksFromJson = new ArrayList<>();

                int removedBookId = 0;
                Book addedBook = null;

                Intent intent = getActivity().getIntent();
                if (intent != null) {
                    removedBookId = intent.getIntExtra("id", 0);
                    addedBook = (Book) intent.getSerializableExtra("book");
                }

                for (int index = 0; index < response.length(); index++) {
                    if (index + 1 != removedBookId)
                        try {
                            BookFromJson item = new BookFromJson().fromJSON(response.getJSONObject(index));
                            item.setId(index + 1);
                            booksFromJson.add(item);
                        } catch (JSONException ex) {
                            Toast.makeText(getContext(), "Json parsing error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                }

                if (addedBook != null) {
                    BookFromJson item = new BookFromJson().fromBook(addedBook);
                    item.setId(response.length());
                    booksFromJson.add(item);
                }

                array = new ArrayList<>();
                for (BookFromJson bookFromJson : booksFromJson) {
                    Book book = new Book(bookFromJson.getTitle(), bookFromJson.getAuthor(), bookFromJson.getYear());
                    book.setId(bookFromJson.getId());

                    boolean checkDB = bookRepository.getById(book.getId(), new OnBookRepositoryActionListener() {
                        @Override
                        public void actionSuccess() {

                        }

                        @Override
                        public void actionFailed() {

                        }
                    }) != null;

                    if (checkDB)
                        array.add(0, book);
                    else
                        array.add(book);
                }

                customAdapter = new CustomAdapter(getContext(), array);
                GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
                rvList.setLayoutManager(mLayoutManager);
                rvList.setAdapter(customAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Volley error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        MySingleton.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);
    }
}