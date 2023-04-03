package com.cmput301w23t00.qrquest.ui.externaluserpage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.addqrcode.QRCodeProcessor;
import com.cmput301w23t00.qrquest.ui.library.LibraryQRCode;
import com.cmput301w23t00.qrquest.ui.library.LibraryQRCodeAdapter;
import com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.ViewCycleStack;
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

/**
 * Fragment used to display an external users profile
 */
public class ExternalUserProfileFragment extends Fragment {
    private TextView name;
    private TextView aboutMe;
    private TextView phoneNumber;
    private TextView email;
    private TextView totalPointsText;
    private TextView highestScoreText;
    private TextView lowestScoreText;
    private ListView QRlist;
    private ImageView profileImage;
    private ArrayAdapter<LibraryQRCode> QRAdapter;
    private ArrayList<LibraryQRCode> dataList;
    private Bundle bundle;
    private Boolean isSearch;
    private Boolean isLeaderboard;
    private Boolean isExternalUsers;
    Boolean back = false;

    /**
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView recent;
        recent = (TextView) root.findViewById(R.id.profile_recent_box);
        name = (TextView) root.findViewById(R.id.profile_username);
        aboutMe = (TextView) root.findViewById(R.id.profile_bio);
        phoneNumber = (TextView) root.findViewById(R.id.profile_phone_number);
        email = (TextView) root.findViewById(R.id.profile_email);
        totalPointsText = (TextView) root.findViewById(R.id.profile_total_points_count);
        highestScoreText = (TextView) root.findViewById(R.id.profile_highest_score_count);
        lowestScoreText = (TextView) root.findViewById(R.id.profile_lowest_score_count);
        QRlist = (ListView) root.findViewById(R.id.recent_list);
        profileImage = (ImageView) root.findViewById(R.id.profile_icon);

        if (getArguments() == null) this.bundle = ViewCycleStack.pop();
        else this.bundle = getArguments();
        ExternalUserProfile userProfile = bundle.getParcelable("selectedUser");
        isSearch = bundle.getBoolean("isSearch");
        isLeaderboard = bundle.getBoolean("isLeaderboard");
        isExternalUsers = bundle.getBoolean("isExternalUsers");

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

        recent.setText("QR Codes");
        name.setText(userProfile.getName());
        aboutMe.setText(userProfile.getAboutMe());
        phoneNumber.setText(String.format("Phone: %s", userProfile.getPhoneNumber()));
        email.setText(String.format("Email: %s", userProfile.getEmail()));


        try {
            profileImage.setImageResource(AvatarUtility.getAvatarImageResource(Integer.parseInt(userProfile.getAvatarId())));
        } catch (Exception e) {
            profileImage.setImageResource(AvatarUtility.getAvatarImageResource(0));
        }

        setHasOptionsMenu(true);

        QRlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                LibraryQRCode qrCode = dataList.get(index);
                //String docID = documentIDList.get(index);

                // Create a bundle to store data that will be passed to the QR code information fragment
                Bundle bundle = new Bundle();
                // Add the selected QR code object and the user ID to the bundle
                bundle.putParcelable("selectedQRCode", qrCode);
                bundle.putString("userID", userID);
                //bundle.putString("documentID", docID);
                bundle.putBoolean("isMap", false);
                bundle.putBoolean("isLeaderboard", false);
                bundle.putBoolean("isExternalUserProfile", true);
                // Use the Navigation component to navigate to the QR code information fragment,
                // and pass the bundle as an argument to the destination fragment
                Navigation.findNavController(view).navigate(R.id.externaluser_profile_to_qrcodeinformation_fragment, bundle);
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                back = true;
                if (isLeaderboard) {
                    NavHostFragment.findNavController(ExternalUserProfileFragment.this).navigate(R.id.action_navigation_externaluserprofilefragment_to_leaderboard);
                } else if (isSearch) {
                    NavHostFragment.findNavController(ExternalUserProfileFragment.this).navigate(R.id.action_externaluser_profile_to_navigation_search);
                } else if (isExternalUsers) {
                    NavHostFragment.findNavController(ExternalUserProfileFragment.this).navigate(R.id.action_navigation_externaluserprofile_to_externalusersfragment);
                } else {
                    NavHostFragment.findNavController(ExternalUserProfileFragment.this).navigate(R.id.action_navigation_externaluserprofile_to_externalusersfragment);
                }
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
            back = true;
            // Navigate back to the previous fragment
            if (isLeaderboard) {
                NavHostFragment.findNavController(ExternalUserProfileFragment.this).navigate(R.id.action_navigation_externaluserprofilefragment_to_leaderboard);
            } else if (isSearch) {
                NavHostFragment.findNavController(ExternalUserProfileFragment.this).navigate(R.id.action_externaluser_profile_to_navigation_search);
            } else if (isExternalUsers) {
                NavHostFragment.findNavController(ExternalUserProfileFragment.this).navigate(R.id.action_navigation_externaluserprofile_to_externalusersfragment);
            } else {
                NavHostFragment.findNavController(ExternalUserProfileFragment.this).navigate(R.id.action_navigation_externaluserprofile_to_externalusersfragment);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * When fragment pauses add save state to stack
     */
    @Override
    public void onPause() {
        super.onPause();
        if (!back) ViewCycleStack.push(bundle);
    }
}