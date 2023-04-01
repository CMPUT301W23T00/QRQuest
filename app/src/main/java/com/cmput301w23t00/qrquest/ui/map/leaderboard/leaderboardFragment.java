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
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.databinding.FragmentLeaderboardBinding;
import com.cmput301w23t00.qrquest.ui.addqrcode.QRCodeProcessor;
import com.cmput301w23t00.qrquest.ui.profile.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class leaderboardFragment extends Fragment {

    private FragmentLeaderboardBinding binding; // View binding for the library fragment
    private ArrayAdapter<leaderboardQRCode> QRAdapter; // Adapter for QR code list
    private ArrayList<leaderboardQRCode> dataList; // List of QR codes to be displayed
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

        // Set adapter for QR code listview to update based on firebase data.
        ListView QRListTopHalf = binding.leaderboardQrCodesListTopHalf;
        ArrayList<leaderboardQRCode> tempDataList = new ArrayList<>();
        dataList = new ArrayList<>();
        leaderboardQRCodeAdapter QRAdapter = new leaderboardQRCodeAdapter(getActivity(), dataList);
        QRListTopHalf.setAdapter(QRAdapter);
        documentIDList = new ArrayList<>();

        // Get current user ID and name.
        String CurrentUserID = UserProfile.getUserId();
        String CurrentUserName = UserProfile.getName();

        // Loop through all users fill the listviews with the correct values
        usersCollectionReference.get().addOnCompleteListener(new OnCompleteListener<>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) { // If found iterate through all user QR codes
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        String userId = (String) document.getData().get("identifierId");
                        String userName = (String) document.getData().get("name");

                        // Find all QR codes scanned by current user with unique identifier ID
                        usersQRCodesCollectionReference.whereEqualTo("identifierId", userId)
                                .get().addOnCompleteListener(new OnCompleteListener<>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) { // If found iterate through all user QR codes

                                            QueryDocumentSnapshot final_document = null;
                                            String qrCodeData = null;
                                            Date dateScanned = null;
                                            long score = 0;

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                String tempQRCodeData = (String) document.getData().get("qrCodeData");
                                                long tempScore = new QRCodeProcessor(tempQRCodeData).getScore();

                                                if (tempScore > score) {
                                                    qrCodeData = tempQRCodeData;
                                                    score = tempScore;
                                                    com.google.firebase.Timestamp timestamp = (com.google.firebase.Timestamp) document.getData().get("dateScanned");
                                                    dateScanned = timestamp.toDate();
                                                    final_document = document;
                                                }
                                            }

                                            try {
                                                documentIDList.add(final_document.getId());
                                                dataList.add(new leaderboardQRCode(userId, userName, qrCodeData, score, dateScanned, 1));
                                                tempDataList.add(new leaderboardQRCode(userId, userName, qrCodeData, score, dateScanned, 1));

                                                // Sort dataList based on score in descending order
                                                Collections.sort(dataList, new Comparator<leaderboardQRCode>() {
                                                    @Override
                                                    public int compare(leaderboardQRCode o1, leaderboardQRCode o2) {
                                                        int scoreComparison = Long.compare(o2.getScore(), o1.getScore());
                                                        if (scoreComparison == 0) {
                                                            return o1.getUser().compareTo(o2.getUser());
                                                        }
                                                        return scoreComparison;
                                                    }
                                                });

                                                for (int i = 0; i < dataList.size(); i++) {
                                                    dataList.get(i).setPosition(i + 1);
                                                }

                                            } catch (NullPointerException e) {
                                            }

                                            QRAdapter.notifyDataSetChanged();
                                        }
                                    }
                                });
                    }
                }
            }
        });

        QRListTopHalf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                leaderboardQRCode qrCode = dataList.get(index);
                String docID = documentIDList.get(index);

                // Create a bundle to store data that will be passed to the QR code information fragment
                Bundle bundle = new Bundle();
                // Add the selected QR code object and the user ID to the bundle
                bundle.putParcelable("selectedQRCode", qrCode);
                bundle.putString("CurrentUserID", CurrentUserID);
                bundle.putString("documentID", docID);
                bundle.putBoolean("isMap", false);
                bundle.putBoolean("isLeaderboard", true);

                // Use the Navigation component to navigate to the QR code information fragment,
                // and pass the bundle as an argument to the destination fragment
                Navigation.findNavController(view).navigate(R.id.leaderboard_to_action_qrcodeinfopage, bundle);
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

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Creates this fragment's menu.
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
