package com.cmput301w23t00.qrquest;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.cmput301w23t00.qrquest.ui.profile.UserProfile;
import com.cmput301w23t00.qrquest.ui.profile.UserSettings;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cmput301w23t00.qrquest.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.installations.FirebaseInstallations;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NavController navController;
    public static final String SETTINGS_PREFS_NAME = "userPreferences";
    public static final String USER_PROFILE_INFORMATION = "userProfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_map, R.id.navigation_qrcode_library, R.id.navigation_add_qrcode,
                R.id.navigation_search, R.id.navigation_profile)
                .build();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_main);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        SharedPreferences settings = getSharedPreferences(SETTINGS_PREFS_NAME, MODE_PRIVATE);
        if (UserSettings.getCreated()) {
            UserSettings userSettings = new UserSettings();
            userSettings.setPushNotifications(settings.getBoolean("PushNotifications", false));
            userSettings.setGeoLocation(settings.getBoolean("GeoLocation", false));
        }

        UserProfile userProfile = new UserProfile();
        SharedPreferences profile = getSharedPreferences(USER_PROFILE_INFORMATION, MODE_PRIVATE);
        if (UserProfile.getCreated()) {
            userProfile.setAboutMe(profile.getString("aboutMe", ""));
            userProfile.setPhoneNumber(profile.getString("phoneNumber", ""));
            userProfile.setEmail(profile.getString("email", ""));
            userProfile.setName(profile.getString("name", ""));
            String Id = FirebaseInstallations.getInstance().getId().toString();
            UserProfile.setUserId(profile.getString("userId", ""));
        }
        UserProfile.setCreated(true);
        UserSettings.setCreated(true);
    }

    @SuppressLint("ApplySharedPref")
    @Override
    protected void onPause() {
        super.onPause();
        UserSettings userSettings = new UserSettings();
        SharedPreferences settings = getSharedPreferences(SETTINGS_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editorSettings = settings.edit();
        if (!UserSettings.getCreated()) { UserSettings.setCreated(true); }
        editorSettings.putBoolean("PushNotifications", userSettings.getPushNotifications());
        editorSettings.putBoolean("GeoLocation", userSettings.getGeoLocation());
        editorSettings.putBoolean("existingAccount", UserSettings.getCreated());
        editorSettings.commit();

        UserProfile userProfile = new UserProfile();
        SharedPreferences profile = getSharedPreferences(USER_PROFILE_INFORMATION, MODE_PRIVATE);
        SharedPreferences.Editor editorProfile = profile.edit();
        if (!UserProfile.getCreated()) { UserProfile.setCreated(true); }
        editorProfile.putString("aboutMe", /*"I <3 big rock"*/UserProfile.getAboutMe());
        editorProfile.putString("phoneNumber", /*"780-333-2344"*/UserProfile.getPhoneNumber());
        editorProfile.putString("email", /*"grug@gmail.com" */UserProfile.getEmail());
        editorProfile.putString("name", /*"Grug"*/UserProfile.getName());
        editorProfile.putBoolean("existingAccount", UserProfile.getCreated());
        editorProfile.putString("userId", "com.google.android.gms.tasks.zzw@9bae679" /*UserProfile.getUserId()*/);
        editorProfile.commit();
    }
}