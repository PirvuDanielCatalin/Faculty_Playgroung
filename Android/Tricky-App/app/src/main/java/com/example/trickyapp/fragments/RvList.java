package com.example.trickyapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trickyapp.R;
import com.example.trickyapp.adapters.CustomAdapter;
import com.example.trickyapp.interfaces.OnUserRepositoryActionListener;
import com.example.trickyapp.models.User;
import com.example.trickyapp.repositories.UserRepository;

import java.util.ArrayList;

public class RvList extends Fragment {

    RecyclerView rvList;
    CustomAdapter customAdapter;
    ArrayList<User> array;
    UserRepository userRepository;

    public RvList() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rv_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvList = view.findViewById(R.id.rv_list);
        userRepository = new UserRepository(getContext());
        getToDoItems();
    }

    private void getToDoItems() {

        array = new ArrayList<>(userRepository.getAll(new OnUserRepositoryActionListener() {
            @Override
            public void actionSuccess() {

            }

            @Override
            public void actionFailed() {

            }
        }));

        customAdapter = new CustomAdapter(getContext(), array);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        rvList.setLayoutManager(mLayoutManager);
        rvList.setAdapter(customAdapter);
    }
}
