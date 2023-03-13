package com.cmput301w23t00.qrquest.ui.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.databinding.FragmentLibraryBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class LibraryFragment extends Fragment {
    private long highestScore; // Highest QR code score for QR code summary statistics fragment
    private long lowestScore; // Lowest QR code score for QR code summary statistics fragment
    private long sumOfScores; // Sum of QR code scores for QR code summary statistics fragment
    private long totalScanned; // Total number of QR codes scanned for QR code summary statistics fragment
    private FragmentLibraryBinding binding; // View binding for the library fragment
    private ArrayAdapter<LibraryQRCode> QRAdapter; // Adapter for QR code list
    private ArrayList<LibraryQRCode> dataList; // List of QR codes to be displayed
    FirebaseFirestore db; // Firebase Firestore database instance
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLibraryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Connect to firebase instance and get collection references for database querying
        db = FirebaseFirestore.getInstance();
        final CollectionReference usersQRCodesCollectionReference = db.collection("usersQRCodes");
        final CollectionReference qrcodesCollectionReference = db.collection("qrcodes");

        // Initialize summary statistics values
        highestScore = 0;
        sumOfScores = 0;
        totalScanned = 0;
        lowestScore = -1;

        // Set adapter for QR code listview to update based on firebase data
        ListView QRList = binding.libraryQrCodesList;
        dataList = new ArrayList<>();
        QRAdapter = new LibraryQRCodeAdapter(getActivity(), dataList);
        QRList.setAdapter(QRAdapter);

        // TODO: REPLACE HARDCODED VALUE WITH VALUE FROM DEREK'S PREFERENCES WHEN MERGED
        //userID = FirebaseInstallations.getInstance().getId().toString();
        String userID = "com.google.android.gms.tasks.zzw@b2bf36a";
        // Find all QR codes scanned by current user with unique identifier ID
        usersQRCodesCollectionReference.whereEqualTo("identifierID", userID)
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
                                // Find the scores of all QR Codes the user has scanned
                                qrcodesCollectionReference.whereEqualTo("qrCodeData", qrCodeData)
                                        .get().addOnCompleteListener(new OnCompleteListener<>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) { // if found iterate through all QR codes
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        // Get score of QR code
                                                        long score = (long) document.getData().get("score");
                                                        // Check if current score is highest score
                                                        highestScore = Math.max(score, highestScore);
                                                        // Check if current score is lowest score
                                                        if (lowestScore == -1) {
                                                            lowestScore = score;
                                                        } else {
                                                            lowestScore = Math.min(lowestScore, score);
                                                        }
                                                        // Increment sum of scores with current score
                                                        sumOfScores += score;
                                                        // Increment total QR codes scanned
                                                        totalScanned += 1;
                                                        // Add found QR code to dataList to display
                                                        dataList.add(new LibraryQRCode(qrCodeData, score, dateScanned));
                                                    }
                                                    // Update view to include newly added QR codes
                                                    QRAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
        // Set on click listener on QR Stats button to navigate to QR code summary statistics fragment
        Button viewQrStats = binding.viewPersonalQrStatsButton;
        viewQrStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a bundle and store data that will be passed to the QR code summary statistics fragment
                Bundle bundle = new Bundle();
                bundle.putLong("highestScore", highestScore);
                bundle.putLong("lowestScore", lowestScore);
                bundle.putLong("sumOfScores", sumOfScores);
                bundle.putLong("totalScanned", totalScanned);
                // Use the Navigation component to navigate to the QR code summary statistics fragment,
                // and pass the bundle as an argument to the destination fragment
                Navigation.findNavController(view).navigate(R.id.action_navigation_qrcode_library_to_qrCodeSummaryStatisticsFragment2, bundle);
            }
        });

        QRList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                LibraryQRCode qrCode = dataList.get(index);

                // Create a bundle to store data that will be passed to the QR code information fragment
                Bundle bundle = new Bundle();
                // Add the selected QR code object and the user ID to the bundle
                bundle.putParcelable("selectedQRCode", qrCode);
                bundle.putString("userID", userID);

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