package com.example.trickyapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.trickyapp.R;
import com.example.trickyapp.fragments.ControlPanel;
import com.example.trickyapp.fragments.RvList;

public class LetsDoSomething extends AppCompatActivity {

    public RvList rvList;
    public ControlPanel controlPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lets_do_something);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        controlPanel = new ControlPanel();
        ft.add(R.id.control_panel_container, controlPanel);

        rvList = new RvList();
        ft.add(R.id.rv_list_container, rvList);

        ft.commit();
    }
}
