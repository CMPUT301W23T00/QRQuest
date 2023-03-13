package com.cmput301w23t00.qrquest.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.cmput301w23t00.qrquest.MainActivity;
import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.databinding.FragmentProfileBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserSettingsFragment extends Fragment{

    public SwitchCompat pushNotifications;
    public SwitchCompat geoLocation;
    private UserSettings userSettings = new UserSettings();

    public UserSettingsFragment() {
        super(R.layout.fragment_settings);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        setHasOptionsMenu(true);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Settings");

        pushNotifications = (SwitchCompat) root.findViewById(R.id.push_notification_switch);
        geoLocation = (SwitchCompat) root.findViewById(R.id.geo_location_switch_settings);

        pushNotifications.setChecked(userSettings.getPushNotifications());
        geoLocation.setChecked(userSettings.getGeoLocation());

        pushNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                userSettings.setPushNotifications(b);
            }
        });

        geoLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                userSettings.setGeoLocation(b);
            }
        });

        //allows for exit by system back button
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                restoreActionBar();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //adds back button to action bar in settings fragment
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            restoreActionBar();
        }
        return super.onOptionsItemSelected(item);
    }

    private void restoreActionBar() {
        NavHostFragment.findNavController(this).navigate(R.id.action_navigation_settings_to_navigation_profile);
        //reverts action bar to profile fragment setup
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Profile");
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
