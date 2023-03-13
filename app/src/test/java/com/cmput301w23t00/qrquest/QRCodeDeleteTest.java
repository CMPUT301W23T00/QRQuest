package com.cmput301w23t00.qrquest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import androidx.annotation.NonNull;

import com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.QRCodeInformationFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Config.OLDEST_SDK, Config.NEWEST_SDK})
public class QRCodeDeleteTest {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersQRCodesCollectionReference;

    @Before
    public void setup() {
        usersQRCodesCollectionReference = db.collection("usersQRCodes");

        String qrCodeData = "JuliaTest";
        String identifierID = "com.google.android.gms.tasks.zzw@b2bf36a";
        Date dateScanned = Calendar.getInstance().getTime();

        for (int i = 0; i < 5; i++) {
            Map<String, Object> qrCode = new HashMap<>();
            qrCode.put("qrCodeData", qrCodeData);
            qrCode.put("identifierID", identifierID);
            qrCode.put("dateScanned", dateScanned);

            // Add the QR code to the collection
            db.collection("usersQRCodes").add(qrCode).addOnSuccessListener(documentReference -> {
                assertNotNull(documentReference.getId());
            });
        }

        testDeleteQRCodeDuplicates();
    }

    @Test
    public void testDeleteQRCodeDuplicates() {
        // expected result: only delete one qr code

        usersQRCodesCollectionReference = db.collection("usersQRCodes");

        QRCodeInformationFragment qrCodeInformationFragment = new QRCodeInformationFragment();

        String qrCodeData = "JuliaTest";
        String identifierID = "com.google.android.gms.tasks.zzw@b2bf36a";
        Date dateScanned = Calendar.getInstance().getTime();

        qrCodeInformationFragment.deleteQRCode(identifierID, dateScanned, qrCodeData);

        final int[] qrCodeCounter = {0};

        usersQRCodesCollectionReference.whereEqualTo("identifierID", identifierID)
                .whereEqualTo("dateScanned", dateScanned)
                .whereEqualTo("qrCodeData", qrCodeData)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                qrCodeCounter[0] += 1;
                            }
                        }
                    }
                });

        assertEquals(qrCodeCounter[0], 4);
    }
}
