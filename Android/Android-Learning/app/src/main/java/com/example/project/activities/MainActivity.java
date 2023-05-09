package com.example.project.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.fragments.MainActivityFragment;
import com.example.project.fragments.PayInternetFragment;
import com.example.project.fragments.RecyclerViewFragment;
import com.example.project.helpers.Constants;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    Context context;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni == null) {
            setContentView(R.layout.activity_main);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.main_activity_fragment_container, new PayInternetFragment());
            ft.commit();

        } else {
            setContentView(R.layout.activity_main);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.main_activity_fragment_container, new MainActivityFragment());
            ft.commit();
        }

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        String old_date = sharedPreferences.getString("Date", Calendar.getInstance().getTime().toString());
        Toast.makeText(context, "Nu ați mai intrat în aplicație din data de: " + old_date, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        String date = Calendar.getInstance().getTime().toString();
        editor.putString("Date", date);
        editor.apply();
    }
}









