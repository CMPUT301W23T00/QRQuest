package com.cmput301w23t00.qrquest.ui.externaluserpage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.addqrcode.QRCodeProcessor;
import com.cmput301w23t00.qrquest.ui.library.LibraryQRCode;
import com.cmput301w23t00.qrquest.ui.library.LibraryQRCodeAdapter;
import com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.QRCodeInformationFragment;
import com.cmput301w23t00.qrquest.ui.profile.UserProfile;
import com.cmput301w23t00.qrquest.ui.updateavatar.AvatarUtility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ExternalUserProfileFragment extends Fragment {
    private TextView name;
    private TextView aboutMe;
    private TextView phoneNumber;
    private TextView email;
    private TextView totalPointsText;
    private TextView highestScoreText;
    private TextView lowestScoreText;
    private ListView QRlist;
    private ShapeableImageView profileImage;
    private ArrayAdapter<LibraryQRCode> QRAdapter;
    private ArrayList<LibraryQRCode> dataList;
    private Boolean isSearch;
    private Boolean isLeaderboard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        name = (TextView) root.findViewById(R.id.profile_username);
        aboutMe = (TextView) root.findViewById(R.id.profile_bio);
        phoneNumber = (TextView) root.findViewById(R.id.profile_phone_number);
        email = (TextView) root.findViewById(R.id.profile_email);
        totalPointsText = (TextView) root.findViewById(R.id.profile_total_points_count);
        highestScoreText = (TextView) root.findViewById(R.id.profile_highest_score_count);
        lowestScoreText = (TextView) root.findViewById(R.id.profile_lowest_score_count);
        QRlist = (ListView) root.findViewById(R.id.recent_list);
        profileImage = (ShapeableImageView) root.findViewById(R.id.imageView);

        Bundle bundle = getArguments();
        ExternalUserProfile userProfile = bundle.getParcelable("selectedUser");
        isSearch = bundle.getBoolean("isSearch");
        isLeaderboard = bundle.getBoolean("isLeaderboard");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference usersQRCodesCollectionReference = db.collection("usersQRCodes");

        final long[] highestScore = {0};
        final long[] sumOfScores = {0};
        final long[] totalScanned = {0};
        final long[] lowestScore = {-1};

        dataList = new ArrayList<>();
        String userID = userProfile.getUserId();
        QRAdapter = new LibraryQRCodeAdapter(getActivity(), dataList);
        QRlist.setAdapter(QRAdapter);

        usersQRCodesCollectionReference.whereEqualTo("identifierId", userID)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("DefaultLocale")
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
                                dataList.add(new LibraryQRCode(qrCodeData, score, dateScanned));
                            }
                            highestScoreText.setText(String.format("%d", (int) highestScore[0]));
                            totalPointsText.setText(String.format("%d", (int) sumOfScores[0]));
                            if (lowestScore[0] == -1) lowestScoreText.setText(String.format("%d", 0));
                            else lowestScoreText.setText(String.format("%d", (int) lowestScore[0]));
                            dataList.sort(Comparator.comparing(LibraryQRCode::getDate));
                            Collections.reverse(dataList);
                            QRlist.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, dataList.size()*150 + 200));
                            QRAdapter.notifyDataSetChanged();
                        }
                    }
                });

        name.setText(userProfile.getName());
        aboutMe.setText(userProfile.getAboutMe());
        phoneNumber.setText(String.format("Phone: %s", userProfile.getPhoneNumber()));
        email.setText(String.format("Email: %s", userProfile.getEmail()));

        try {
            profileImage.setImageResource(AvatarUtility.getAvatarImageResource(Integer.parseInt(userProfile.getAvatarId())));
        } catch (Exception e) {
            profileImage.setImageResource(AvatarUtility.getAvatarImageResource(0));
        }

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                restoreActionBar();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

        return root;
    }
    /**
     * onCreate is called to do initial creation of the fragment.
     * @param savedInstanceState the previously saved instance state
     */
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Creates this fragment's menu.
        setHasOptionsMenu(true);
    }

    /**
     * onOptionsItemSelected is called when a menu item is selected.
     *
     * @param item The menu item that was selected
     * @return True if the menu item was handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Back arrow
        if (id == android.R.id.home) {
            // Navigate back to the previous fragment
            if (isLeaderboard) {
                NavHostFragment.findNavController(ExternalUserProfileFragment.this).navigate(R.id.action_navigation_externaluserprofilefragment_to_leaderboard);
            } else if (isSearch) {
                NavHostFragment.findNavController(ExternalUserProfileFragment.this).navigate(R.id.action_externaluser_profile_to_navigation_search);
            } else {
                NavHostFragment.findNavController(this).navigate(R.id.action_navigation_externaluserprofile_to_externalusersfragment);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void restoreActionBar() {
        NavHostFragment.findNavController(this).navigate(R.id.action_navigation_externaluserprofile_to_externalusersfragment);
    }
}