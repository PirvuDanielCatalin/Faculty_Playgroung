package com.example.project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.project.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PayInternetFragment extends Fragment {

    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pay_internet_bill, container, false);

        imageView = view.findViewById(R.id.pay_bill_img);

        int imageResource = getResources().getIdentifier("@drawable/no_internet", null, getActivity().getPackageName());
        imageView.setImageResource(imageResource);

        return view;
    }
}
