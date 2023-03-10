package com.cmput301w23t00.qrquest.ui.loadingscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cmput301w23t00.qrquest.MainActivity;
import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.createaccount.CreateAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.installations.FirebaseInstallations;

import java.util.Objects;

public class LoadingScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        // Instead of getSupportActionBar().hide(); pre-req is that Support Action Bar must be set.
        Objects.requireNonNull(getSupportActionBar()).hide();

        // check https://firebase.google.com/docs/projects/manage-installations#java_5
        // FID is the Firebase Installation ID: should act as identifierId.
        FirebaseInstallations.getInstance().getId()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            String fid = task.getResult();
                            Log.d("Installations", "Installation ID: " + fid);
                        } else {
                            Log.e("Installations", "Unable to get Installation ID");
                        }
                    }
                });


        // Method to invoke loading time; second variable is time in milliseconds.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // if uid is not present, goes to CreateAccount.
                if (fid.equals(null)){
                    Intent intentNoUID = new Intent(LoadingScreen.this, CreateAccount.class);
                    startActivity(intentNoUID);
                }
                // if uid is present, goes to MainActivity.
                else {
                    Intent intentWithUID = new Intent(LoadingScreen.this, MainActivity.class);
                    startActivity(intentWithUID);
                }
                // finish() ends this activity (user won't be able to go back to loading screen).
                finish();
            }
        }, 5000);
    }
}
