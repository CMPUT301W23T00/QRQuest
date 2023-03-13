package com.cmput301w23t00.qrquest.ui.library;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
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
import com.cmput301w23t00.qrquest.ui.addqrcode.QRCodeProcessor;
import com.cmput301w23t00.qrquest.ui.profile.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.installations.FirebaseInstallations;

import java.util.ArrayList;
import java.util.Date;

public class LibraryFragment extends Fragment {
    private long highestScore;
    private long lowestScore;
    private long sumOfScores;
    private long totalScanned;
    private FragmentLibraryBinding binding;
    private ArrayAdapter<LibraryQRCode> QRAdapter;
    private ArrayList<LibraryQRCode> dataList;
    FirebaseFirestore db;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLibraryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // QR Code List
        db = FirebaseFirestore.getInstance();
        final CollectionReference usersQRCodesCollectionReference = db.collection("usersQRCodes");
        final CollectionReference qrcodesCollectionReference = db.collection("qrcodes");

        highestScore = 0;
        sumOfScores = 0;
        totalScanned = 0;
        lowestScore = -1;

        ListView QRList = binding.libraryQrCodesList;
        dataList = new ArrayList<>();
        String userID = /*"com.google.android.gms.tasks.zzw@9bae679"*/ UserProfile.getUserId();
        QRAdapter = new LibraryQRCodeAdapter(getActivity(), dataList);
        QRList.setAdapter(QRAdapter);
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
                                highestScore = Math.max(score, highestScore);
                                if (lowestScore == -1) {
                                    lowestScore = score;
                                } else {
                                    lowestScore = Math.min(lowestScore, score);
                                }
                                sumOfScores += score;
                                totalScanned += 1;
                                dataList.add(new LibraryQRCode(qrCodeData, score, dateScanned));
                                QRAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
        // QR Stats button
        Button viewQrStats = binding.viewPersonalQrStatsButton;
        viewQrStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putLong("highestScore", highestScore);
                bundle.putLong("lowestScore", lowestScore);
                bundle.putLong("sumOfScores", sumOfScores);
                bundle.putLong("totalScanned", totalScanned);
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