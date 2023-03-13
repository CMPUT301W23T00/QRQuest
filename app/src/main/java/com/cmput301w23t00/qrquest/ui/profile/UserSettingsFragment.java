package com.cmput301w23t00.qrquest.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.cmput301w23t00.qrquest.ui.editaccount.EditAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * This class models the fragment used to display the user settings page
 */
public class UserSettingsFragment extends Fragment{

    public SwitchCompat pushNotifications;
    public SwitchCompat geoLocation;
    private UserSettings userSettings = new UserSettings();

    public Button editProfileButton;

    public UserSettingsFragment() {
        super(R.layout.fragment_settings);
    }

    /**
     * onCreateView inflates the view, showing a button that leads to an edit profile page as well
     * as settings for enabling/disabling push notifications and geo-location recording
     * @param inflater the LayoutInflater object that can be used to inflate any views in the fragment
     * @param container the parent view that the fragment's UI should be attached to
     * @param savedInstanceState the previously saved instance state
     * @return the view for the fragment's UI
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        setHasOptionsMenu(true);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Settings");

        pushNotifications = (SwitchCompat) root.findViewById(R.id.push_notification_switch);
        geoLocation = (SwitchCompat) root.findViewById(R.id.geo_location_switch_settings);
        editProfileButton = (Button) root.findViewById(R.id.editProfileButton);


        pushNotifications.setChecked(userSettings.getPushNotifications());
        geoLocation.setChecked(userSettings.getGeoLocation());

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToEditProfile = new Intent(getActivity(),EditAccount.class);
                startActivity(intentToEditProfile);
            }
        });

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
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

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

    /**
     * onPause is called when the view is temporarily left by the user
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * onResume is called when the view that was temporarily left is returned to
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * onDestroyView is called when the view is destroyed.
     * It cleans up any references to the binding to prevent memory leaks.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
