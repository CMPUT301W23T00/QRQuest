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
    private ArrayList<leaderboardQRCode> topDataList; // List of QR codes to be displayed
    private ArrayList<leaderboardQRCode> bottomDataList; // List of QR codes to be displayed
    private ArrayList<String> documentIDList; // List of documents
    FirebaseFirestore db; // Firebase Firestore database instance


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLeaderboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Connect to firebase instance and get collection references for database querying
        db = FirebaseFirestore.getInstance();
        final CollectionReference usersQRCodesCollectionReference = db.collection("usersQRCodes");

        final CollectionReference usersCollectionReference = db.collection("users");

        // Set adapter for QR code listview to update based on firebase data

        // this is only the top half for now, need to change it later
        ListView QRListTopHalf = binding.leaderboardQrCodesListTopHalf;
        ListView QRListBottomHalf = binding.leaderboardQrCodesListBottomHalf;

        dataList = new ArrayList<>();
        topDataList = new ArrayList<>();
        bottomDataList = new ArrayList<>();

        documentIDList = new ArrayList<>();
        //QRAdapter = new leaderboardQRCodeAdapter(getActivity(), dataList);

        leaderboardQRCodeAdapter topQRAdapter = new leaderboardQRCodeAdapter(getActivity(), topDataList);
        leaderboardQRCodeAdapter bottomQRAdapter = new leaderboardQRCodeAdapter(getActivity(), bottomDataList);

        QRListTopHalf.setAdapter(topQRAdapter);
        QRListBottomHalf.setAdapter(bottomQRAdapter);

        documentIDList = new ArrayList<>();
        String CurrentUserID = UserProfile.getUserId();

        // loop through all users and get their highest scoring QR code
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

                                                // Sort dataList based on score in descending order
                                                Collections.sort(dataList, new Comparator<leaderboardQRCode>() {
                                                    @Override
                                                    public int compare(leaderboardQRCode o1, leaderboardQRCode o2) {
                                                        return Long.compare(o2.getScore(), o1.getScore());
                                                    }
                                                });

                                                // Then sort by userName
                                                Collections.sort(dataList, new Comparator<leaderboardQRCode>() {
                                                    @Override
                                                    public int compare(leaderboardQRCode qr1, leaderboardQRCode qr2) {
                                                        return qr1.getUser().compareTo(qr2.getUser());
                                                    }
                                                });

                                                for (int i = 0; i < dataList.size(); i++) {
                                                    leaderboardQRCode qrCode = dataList.get(i);
                                                    qrCode.setPosition(i + 1);
                                                }

                                                System.out.println("hi:");
                                                System.out.println(dataList);

                                                int dataListSize = dataList.size();

                                                topDataList = new ArrayList<>(dataList.subList(0, 3));
                                                bottomDataList = new ArrayList<>(dataList.subList(3, dataListSize));
                                                QRListTopHalf.setAdapter(topQRAdapter);
                                                QRListBottomHalf.setAdapter(bottomQRAdapter);

                                            } catch (NullPointerException e) {
                                            }


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
