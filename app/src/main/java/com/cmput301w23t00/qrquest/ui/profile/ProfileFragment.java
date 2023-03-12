package com.cmput301w23t00.qrquest.ui.profile;

import androidx.navigation.fragment.NavHostFragment;

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
import androidx.lifecycle.ViewModelProvider;

import com.cmput301w23t00.qrquest.MainActivity;
import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.databinding.FragmentProfileBinding;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {

    private TextView name;
    private TextView aboutMe;
    private TextView phoneNumber;
    private TextView email;
    private TextView totalPoints;
    private TextView highestScore;
    private TextView lowestScore;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        name = (TextView) root.findViewById(R.id.profile_username);
        aboutMe = (TextView) root.findViewById(R.id.profile_bio);
        phoneNumber = (TextView) root.findViewById(R.id.profile_phone_number);
        email = (TextView) root.findViewById(R.id.profile_email);
        totalPoints = (TextView) root.findViewById(R.id.profile_total_points_count);
        highestScore = (TextView) root.findViewById(R.id.profile_highest_score_count);
        lowestScore = (TextView) root.findViewById(R.id.profile_lowest_score_count);

        UserProfile userProfile = new UserProfile();

        name.setText(UserProfile.getName());
        aboutMe.setText(UserProfile.getAboutMe());
        phoneNumber.setText(String.format("Phone: %s", UserProfile.getPhoneNumber()));
        email.setText(String.format("Email: %s", UserProfile.getEmail()));

        setHasOptionsMenu(true);

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
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_profile_to_navigation_settings);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}