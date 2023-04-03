package com.cmput301w23t00.qrquest.ui.loadingscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cmput301w23t00.qrquest.MainActivity;
import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.createaccount.CreateAccount;
import com.cmput301w23t00.qrquest.ui.profile.UserProfile;
import com.cmput301w23t00.qrquest.ui.profile.UserSettings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;
import java.util.UUID;

/**
 * LoadingScreen the activity the is displayed for the first 5 seconds of runtime
 */
public class LoadingScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        final boolean[] accountExists = new boolean[1];
        final String[] fid = new String[1];
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final String TAG = "LoadingScreen";
        final String USER_PROFILE_INFORMATION = "userProfile";
        final String SETTINGS_PREFS_NAME = "userPreferences";

        SharedPreferences profile = getSharedPreferences(USER_PROFILE_INFORMATION, MODE_PRIVATE);
        if (!profile.getBoolean("existingAccount", false)) {
            fid[0] = UUID.randomUUID().toString();
            UserProfile.setUserId(fid[0]);
        }
        else if (profile.getString("userId", "").equals("")) {
            fid[0] = UUID.randomUUID().toString();
            UserProfile.setUserId(fid[0]);
        }
        else {
            fid[0] = profile.getString("userId", "");
            UserProfile.setCreated(true);
            UserProfile.setUserId(profile.getString("userId", ""));
            UserSettings.setCreated(true);
        }

        // Instead of getSupportActionBar().hide(); pre-req is that Support Action Bar must be set.
        Objects.requireNonNull(getSupportActionBar()).hide();

        // check https://firebase.google.com/docs/projects/manage-installations#java_5
        // FID is the Firebase Installation ID: should act as identifierId.
        // TODO: fix code below.
        // Recommended way to get fid[0] as on line 33:
        /*FirebaseInstallations.getInstance().getId()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            // gets fid.
                            fid[0] = task.getResult().toString();
                            Log.d("Installations", "Installation ID: " + fid[0]);
                        } else {
                            Log.e("Installations", "Unable to get Installation ID");
                        }
                    }
                });*/

        // queries for identifierId in db that matches fid.
        db.collection("users")
                .whereEqualTo("identifierId", fid[0])
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            Log.d(TAG, "Task successful: " + task.isSuccessful());
                            // sets query results into querySnapshot.
                            QuerySnapshot querySnapshot = task.getResult();

                            // if query results in no result, account does not exist.
                            accountExists[0] = querySnapshot.size() != 0;
                            Log.d(TAG, "AccountExists: " + accountExists[0]);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        // Method to invoke loading time; second variable is time in milliseconds.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // if fid is not present, goes to CreateAccount.
                // CHANGE TO !
                if (!accountExists[0]){
                    Intent intentNoUID = new Intent(LoadingScreen.this, CreateAccount.class);
                    // sends fid to CreateAccount
                    intentNoUID.putExtra("fid", fid[0]);
                    UserProfile.setUserId(fid[0]);
                    startActivity(intentNoUID);
                }
                // if fid is present, goes to MainActivity.
                else {
                    Intent intentWithUID = new Intent(LoadingScreen.this, MainActivity.class);
                    startActivity(intentWithUID);
                }
                // finish() ends this activity (user won't be able to go back to loading screen).
                finish();
            }
        }, 4000);
    }
}
