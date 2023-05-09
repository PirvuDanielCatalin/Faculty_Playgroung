package com.example.project.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.activities.MainActivity;
import com.example.project.activities.ShowBookForm;
import com.example.project.interfaces.OnBookRepositoryActionListener;
import com.example.project.models.Book;
import com.example.project.repositories.BookRepository;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_BOOK = 1;

    private ArrayList<Book> items;

    private Context context;

    public CustomAdapter(Context context, ArrayList<Book> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_BOOK;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {

            case TYPE_BOOK:
                View bookView = inflater.inflate(R.layout.books_rv_item, parent, false);

                RecyclerView.ViewHolder viewHolderBook = new CustomAdapter.ViewHolderBook(bookView);
                return viewHolderBook;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final Book item = items.get(position);
        boolean checkDB = new BookRepository(context).getById(item.getId(), new OnBookRepositoryActionListener() {
            @Override
            public void actionSuccess() {

            }

            @Override
            public void actionFailed() {

            }
        }) != null;

        switch (getItemViewType(position)) {
            case TYPE_BOOK:
                TextView textViewBook = ((ViewHolderBook) viewHolder).titleTextView;
                textViewBook.setText(item.getTitle());
                textViewBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (context.getClass().equals(MainActivity.class)) {
                            Intent intent = new Intent(context, ShowBookForm.class);
                            intent.putExtra("book", item);
                            context.startActivity(intent);
                            ((MainActivity) context).finish();
                        }
                    }
                });

                Button favoriteButton = ((ViewHolderBook) viewHolder).favoriteButton;
                favoriteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (context.getClass().equals(MainActivity.class)) {
                            Intent intent = new Intent(context, ShowBookForm.class);
                            intent.putExtra("book", item);
                            context.startActivity(intent);
                            ((MainActivity) context).finish();
                        }
                    }
                });

                if (checkDB)
                    favoriteButton.setBackgroundResource(R.drawable.star_f);
                else
                    favoriteButton.setBackgroundResource(R.drawable.star_uf);

                break;
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolderBook extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView titleTextView;
        public Button favoriteButton;
        public LinearLayout linearLayout;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolderBook(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            titleTextView = itemView.findViewById(R.id.rv_item_tv);
            favoriteButton = itemView.findViewById(R.id.rv_item_star);
            linearLayout = itemView.findViewById(R.id.rv_item_ll);
        }
    }
}
