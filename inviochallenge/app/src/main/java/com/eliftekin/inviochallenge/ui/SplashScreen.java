package com.eliftekin.inviochallenge.ui;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.eliftekin.inviochallenge.R;
import com.eliftekin.inviochallenge.models.PageInfo;
import com.eliftekin.inviochallenge.ui.viewmodels.SplashViewModel;
import com.eliftekin.inviochallenge.utils.Singleton;

public class SplashScreen extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Handler handler;
    SplashViewModel viewModel;

    Singleton singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        setWindowSettings();

        singleton = Singleton.getInstance();

        viewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        viewModel.fetchData(1);

        viewModel.getPageInfoLiveData().observe(this, new Observer<PageInfo>() {
            @Override
            public void onChanged(PageInfo pageInfo) {
                startNewActivity(pageInfo);
            }
        });

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(SplashScreen.this, "Hata!", Toast.LENGTH_SHORT).show();
                Log.e(TAG, s);
            }
        });

    }

    private void startNewActivity(PageInfo pageInfo) {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                singleton.setPageInfo(pageInfo);

                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    private void setWindowSettings() {
        getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION   |
                SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}