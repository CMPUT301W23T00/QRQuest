package com.cmput301w23t00.qrquest.ui.loadingscreen;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.cmput301w23t00.qrquest.R;

public class LoadingScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
        getSupportActionBar().hide();


        finish();
    }
}
