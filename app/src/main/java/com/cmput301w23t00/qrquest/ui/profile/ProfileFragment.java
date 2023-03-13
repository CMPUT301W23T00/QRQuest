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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.cmput301w23t00.qrquest.ui.addqrcode.QRCodeProcessor;
import com.cmput301w23t00.qrquest.ui.library.LibraryQRCode;
import com.cmput301w23t00.qrquest.ui.library.LibraryQRCodeAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class models the fragment used to display the user profile
 */
public class ProfileFragment extends Fragment {

    private TextView name;
    private TextView aboutMe;
    private TextView phoneNumber;
    private TextView email;
    private TextView totalPointsText;
    private TextView highestScoreText;
    private TextView lowestScoreText;
    private ListView QRlist;
    private ArrayAdapter<LibraryQRCode> QRAdapter;
    private ArrayList<LibraryQRCode> dataList;

    private ArrayList<LinearLayout> qr_codes;

    /**
     * onCreateView inflates the view, showing a user's collection of QR codes with a button to see
     * summary statistics
     * @param inflater the LayoutInflater object that can be used to inflate any views in the fragment
     * @param container the parent view that the fragment's UI should be attached to
     * @param savedInstanceState the previously saved instance state
     * @return the view for the fragment's UI
     */
    @SuppressLint("DefaultLocale")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        name = (TextView) root.findViewById(R.id.profile_username);
        aboutMe = (TextView) root.findViewById(R.id.profile_bio);
        phoneNumber = (TextView) root.findViewById(R.id.profile_phone_number);
        email = (TextView) root.findViewById(R.id.profile_email);
        totalPointsText = (TextView) root.findViewById(R.id.profile_total_points_count);
        highestScoreText = (TextView) root.findViewById(R.id.profile_highest_score_count);
        lowestScoreText = (TextView) root.findViewById(R.id.profile_lowest_score_count);
        QRlist = (ListView) root.findViewById(R.id.recent_list);

        UserProfile userProfile = new UserProfile();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference usersQRCodesCollectionReference = db.collection("usersQRCodes");

        final long[] highestScore = {0};
        final long[] sumOfScores = {0};
        final long[] totalScanned = {0};
        final long[] lowestScore = {-1};


        dataList = new ArrayList<>();
        String userID = /*"com.google.android.gms.tasks.zzw@9bae679"*/ UserProfile.getUserId();
        QRAdapter = new LibraryQRCodeAdapter(getActivity(), dataList);
        QRlist.setAdapter(QRAdapter);

        usersQRCodesCollectionReference.whereEqualTo("identifierId", userID)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String qrCodeData = (String) document.getData().get("qrCodeData");
                                com.google.firebase.Timestamp timestamp = (com.google.firebase.Timestamp) document.getData().get("dateScanned");
                                Date dateScanned = timestamp.toDate();
                                long score = new QRCodeProcessor(qrCodeData).getScore();
                                highestScore[0] = Math.max(score, highestScore[0]);
                                if (lowestScore[0] == -1) {
                                    lowestScore[0] = score;
                                } else {
                                    lowestScore[0] = Math.min(lowestScore[0], score);
                                }
                                sumOfScores[0] += score;
                                totalScanned[0] += 1;
                                if (totalScanned[0] <= 5) {
                                    dataList.add(new LibraryQRCode(qrCodeData, score, dateScanned));
                                }
                                QRAdapter.notifyDataSetChanged();
                            }
                            highestScoreText.setText(String.format("%d", (int) highestScore[0]));
                            totalPointsText.setText(String.format("%d", (int) sumOfScores[0]));
                            if (lowestScore[0] == -1) lowestScoreText.setText(String.format("%d", 0));
                            else lowestScoreText.setText(String.format("%d", (int) lowestScore[0]));
                            QRlist.setMinimumHeight(100*dataList.size());
                        }
                    }
                });

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

    /**
     * onDestroyView is called when the view is destroyed.
     * It cleans up any references to the binding to prevent memory leaks.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}