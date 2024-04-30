package com.eliftekin.inviochallenge.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.eliftekin.inviochallenge.R;
import com.eliftekin.inviochallenge.ui.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        HomeFragment homeFragment = new HomeFragment();
        transaction.addToBackStack(null).replace(R.id.main_screen, homeFragment).commit();

    }
}