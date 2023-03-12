package com.cmput301w23t00.qrquest.ui.profile;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.cmput301w23t00.qrquest.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;
import com.google.firebase.firestore.model.Document;
import com.google.firestore.v1.WriteResult;

public class UserSettings {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static Boolean geoLocation = true;
    private static Boolean pushNotifications = true;
    private static String userId = "ivqEiEof56L80Z2gkhfI";

    public UserSettings() {
        this.db.collection("users").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d(TAG, "onSuccess: document obtained");
                geoLocation = documentSnapshot.getBoolean("recordGeoLocationByDefault");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: ", e);
            }
        });
    }

    public Boolean getPushNotifications() {
        return pushNotifications;
    }

    public Boolean getGeoLocation() {
        return geoLocation;
    }

    public void setPushNotifications(Boolean pushNotifications) {
        this.pushNotifications = pushNotifications;
    }

    public void setGeoLocation(Boolean geoLocation) {
        this.geoLocation = geoLocation;
        // Update an existing document
        DocumentReference docRef = db.collection("users").document(userId);
        docRef.update("recordGeoLocationByDefault", geoLocation);

    }
}
