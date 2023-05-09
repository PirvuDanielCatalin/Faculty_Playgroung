package com.example.trickyapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trickyapp.R;
import com.example.trickyapp.models.User;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE_USER = 1;

    private ArrayList<User> items;

    private Context context;

    public CustomAdapter(Context context, ArrayList<User> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_USER;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {

            case TYPE_USER:
                View userView = inflater.inflate(R.layout.rv_item, parent, false);

                RecyclerView.ViewHolder viewHolderUser = new CustomAdapter.ViewHolderUser(userView);
                return viewHolderUser;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final User item = items.get(position);

        switch (getItemViewType(position)) {
            case TYPE_USER:
                TextView textViewUser = ((ViewHolderUser) viewHolder).fullnameTextView;

                String str = item.first_name + " " + item.last_name + " is awesome!";
                textViewUser.setText(str);

                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolderUser extends RecyclerView.ViewHolder {
        public TextView fullnameTextView;

        public ViewHolderUser(View itemView) {
            super(itemView);

            fullnameTextView = itemView.findViewById(R.id.rv_item_tv);
        }
    }
}

