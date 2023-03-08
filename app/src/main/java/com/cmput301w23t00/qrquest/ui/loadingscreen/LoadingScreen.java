package com.cmput301w23t00.qrquest.ui.loadingscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.cmput301w23t00.qrquest.MainActivity;
import com.cmput301w23t00.qrquest.R;

public class LoadingScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
        getSupportActionBar().hide();

        String uid = null;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (uid.equals(null)){
                    Intent intentNoUID = new Intent(LoadingScreen.this, CreateAccount.class);
                    startActivity(intentNoUID);
                }
                else {
                    Intent intentWithUID = new Intent(LoadingScreen.this, MainActivity.class);
                    startActivity(intentWithUID);
                }
                finish();
            }
        }, 3000);
    }
}
