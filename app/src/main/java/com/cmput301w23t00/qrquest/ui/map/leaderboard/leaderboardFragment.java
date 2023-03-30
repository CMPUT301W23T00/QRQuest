package com.cmput301w23t00.qrquest.ui.map.leaderboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.databinding.FragmentLeaderboardBinding;
import com.cmput301w23t00.qrquest.ui.addqrcode.QRCodeProcessor;
import com.cmput301w23t00.qrquest.ui.library.LibraryQRCode;
import com.cmput301w23t00.qrquest.ui.library.LibraryQRCodeAdapter;
import com.cmput301w23t00.qrquest.ui.profile.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class leaderboardFragment extends Fragment {

    private FragmentLeaderboardBinding binding; // View binding for the library fragment
    private ArrayAdapter<LibraryQRCode> QRAdapter; // Adapter for QR code list
    private ArrayList<LibraryQRCode> dataList; // List of QR codes to be displayed
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
        ListView QRListTemp = binding.leaderboardQrCodesListTopHalf;
        dataList = new ArrayList<>();
        documentIDList = new ArrayList<>();
        QRAdapter = new LibraryQRCodeAdapter(getActivity(), dataList);
        QRListTemp.setAdapter(QRAdapter);
        documentIDList = new ArrayList<>();
        String userID = UserProfile.getUserId();

        List<String> userIdList = new ArrayList<>();

        // loop through all users and get their highest scoring QR code
        usersCollectionReference.get().addOnCompleteListener(new OnCompleteListener<>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) { // If found iterate through all user QR codes
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String userId = (String) document.getData().get("identifierId");

                                if (userId != "") {
                                    userIdList.add(userId);
                                }
                            }
                        }
                    }
                });

        for (int i = 0; i < userIdList.size(); i++) {
            // Find all QR codes scanned by current user with unique identifier ID
            usersQRCodesCollectionReference.whereEqualTo("identifierId", userIdList.get(i))
                    .get().addOnCompleteListener(new OnCompleteListener<>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) { // If found iterate through all user QR codes
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // Get QR code data and date QR code scanned
                                    String qrCodeData = (String) document.getData().get("qrCodeData");
                                    com.google.firebase.Timestamp timestamp = (com.google.firebase.Timestamp) document.getData().get("dateScanned");
                                    // Convert Firebase Timestamp to Date
                                    Date dateScanned = timestamp.toDate();
                                    // Get score of QR code
                                    long score = new QRCodeProcessor(qrCodeData).getScore();
                                    // Add found QR code to dataList to display
                                    // Update view to include newly added QR codes

                                }
//                                documentIDList.add(document.getId());
//                                dataList.add(new LibraryQRCode(qrCodeData, score, dateScanned));
//                                QRAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }

        QRListTemp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                LibraryQRCode qrCode = dataList.get(index);
                String docID = documentIDList.get(index);

                // Create a bundle to store data that will be passed to the QR code information fragment
                Bundle bundle = new Bundle();
                // Add the selected QR code object and the user ID to the bundle
                bundle.putParcelable("selectedQRCode", qrCode);
                bundle.putString("userID", userID);
                bundle.putString("documentID", docID);
                bundle.putBoolean("isMap", false);

                // Use the Navigation component to navigate to the QR code information fragment,
                // and pass the bundle as an argument to the destination fragment
                Navigation.findNavController(view).navigate(R.id.action_libraryFragment_to_qrCodeInformationFragment, bundle);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
