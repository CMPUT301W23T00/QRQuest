package com.cmput301w23t00.qrquest.ui.map.leaderboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.databinding.FragmentLeaderboardBinding;
import com.cmput301w23t00.qrquest.ui.addqrcode.QRCodeProcessor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class leaderboardFragment extends Fragment {

    private FragmentLeaderboardBinding binding; // View binding for the library fragment
    private ArrayAdapter<leaderboardUser> UserAdapter; // Adapter for User list
    private ArrayList<leaderboardUser> dataList; // List of Users to be displayed
    private ArrayList<String> documentIDList; // List of documents
    FirebaseFirestore db; // Firebase Firestore database instance

    /**
     * This method creates the fragment view and initializes firebase collections and adapters.
     *
     * @param inflater LayoutInflater object used to inflate the layout.
     * @param container ViewGroup object that contains the fragment.
     * @param savedInstanceState Bundle object containing saved state information.
     * @return The View object that is created.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the fragment layout and get the root View object.
        binding = FragmentLeaderboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Connect to Firebase instance and get collection references for database querying.
        db = FirebaseFirestore.getInstance();
        final CollectionReference usersQRCodesCollectionReference = db.collection("usersQRCodes");
        final CollectionReference usersCollectionReference = db.collection("users");

        // Set adapter for User listview to update based on firebase data.
        ListView QRList = binding.leaderboardUsersList;
        dataList = new ArrayList<>();
        UserAdapter = new leaderboardUserAdapter(getActivity(), dataList);
        QRList.setAdapter(UserAdapter);
        documentIDList = new ArrayList<>();

        // Loop through all users fill the listviews with the correct values
        usersCollectionReference.get().addOnCompleteListener(new OnCompleteListener<>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) { // If found iterate through all user QR codes
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        String userId = (String) document.getData().get("identifierId");
                        String userName = (String) document.getData().get("name");
                        String userAboutMe = (String) document.getData().get("aboutMe");
                        String userEmail = (String) document.getData().get("email");
                        String userPhoneNumber = (String) document.getData().get("phoneNumber");

                        // Find all QR codes scanned by the current user
                        usersQRCodesCollectionReference.whereEqualTo("identifierId", userId)
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            long totalScore = 0;

                                            // Iterate through each QR code and get the total score
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                String tempQRCodeData = (String) document.getData().get("qrCodeData");
                                                long tempScore = new QRCodeProcessor(tempQRCodeData).getScore();
                                                totalScore += tempScore;
                                            }

                                            try {
                                                // Add the QR code with the highest score to the leaderboard list
                                                dataList.add(new leaderboardUser(userId, userName, totalScore, 1));

                                                // Sort the leaderboard list based on score in descending order
                                                Collections.sort(dataList, new Comparator<leaderboardUser>() {
                                                    @Override
                                                    public int compare(leaderboardUser o1, leaderboardUser o2) {
                                                        int scoreComparison = Long.compare(o2.getScore(), o1.getScore());
                                                        return scoreComparison;
                                                    }
                                                });

                                                // Assign a position to each user on the leaderboard
                                                int currentRank = 1;
                                                for (int i = 0; i < dataList.size(); i++) {
                                                    leaderboardUser currentQRCode = dataList.get(i);
                                                    if (i > 0) {
                                                        leaderboardUser previousQRCode = dataList.get(i - 1);
                                                        if (currentQRCode.getScore() != previousQRCode.getScore()) {
                                                            currentRank = currentRank + 1;
                                                        }
                                                    }
                                                    currentQRCode.setPosition(currentRank);
                                                }

                                            } catch (NullPointerException e) {
                                                // Do nothing if there are no QR codes for the user
                                            }

                                            // Notify the adapter that the data has changed
                                            UserAdapter.notifyDataSetChanged();
                                        }
                                    }
                                });
                    }
                }
            }
        });

        QRList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                leaderboardUser user = dataList.get(index);

                // Create a bundle to store data that will be passed to the other user profile fragment
                Bundle bundle = new Bundle();


                // we are going to change this when profile is implemented
                //Navigation.findNavController(view).navigate(R.id.leaderboard_to_action_qrcodeinfopage, bundle);
            }
        });

        return root;
    }

    /**
     * onOptionsItemSelected is called when a menu item is selected.
     *
     * @param item The menu item that was selected
     * @return True if the menu item was handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // Back arrow
        if (item.getItemId() == android.R.id.home) {
            // Navigate back to the previous fragment
            NavHostFragment.findNavController(leaderboardFragment.this).navigate(R.id.leaderboard_to_action_mapFragment);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is called when the fragment is created. It sets up the fragment's menu by calling setHasOptionsMenu(true).
     * @param savedInstanceState the saved instance state bundle
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Creates this fragment's menu.
        setHasOptionsMenu(true);
    }

    /**
     * This method is called when the view hierarchy associated with this fragment is being destroyed. It sets the binding variable to null to prevent memory leaks.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
