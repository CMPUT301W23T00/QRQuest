package com.cmput301w23t00.qrquest.ui.profile;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.cmput301w23t00.qrquest.MainActivity;
import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textProfile;
        setHasOptionsMenu(true);

        profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.profile_settings_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings_button) {
            FragmentManager fragmentManager = getParentFragmentManager();
            UserSettingsFragment settingsScreen = new UserSettingsFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main, settingsScreen, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("settings") // name can be null
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /*
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getParentFragmentManager();
        if (fragmentManager.getFragments().size() > 1) {
            fragmentManager.popBackStack();
            //reverts action bar to profile fragment setup
            ((MainActivity)getActivity()).getSupportActionBar().setTitle("Profile");
            ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }
    */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}