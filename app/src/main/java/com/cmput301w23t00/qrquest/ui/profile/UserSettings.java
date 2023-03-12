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
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firestore.v1.WriteResult;

public class UserSettings {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static Boolean geoLocation = true;
    private static Boolean pushNotifications = true;
    private static String userId = FirebaseInstallations.getInstance().getId().toString();
    private static Boolean created = false;

    public UserSettings() {
        this.db.collection("users").whereEqualTo("identifierId", userId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.d(TAG, "onSuccess: Document obtained");
                geoLocation = queryDocumentSnapshots.getDocuments().get(0).getBoolean("recordGeoLocationByDefault");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: Document not obtained", e);
            }
        });
    }

    public Boolean getPushNotifications() {
        return pushNotifications;
    }

    public Boolean getGeoLocation() {
        return geoLocation;
    }

    public static Boolean getCreated() {
        return created;
    }

    public void setPushNotifications(Boolean pushNotifications) {
        this.pushNotifications = pushNotifications;
    }

    public void setGeoLocation(Boolean geoLocation) {
        this.geoLocation = geoLocation;
        // Update an existing document
        db.collection("users").whereEqualTo("identifierId", userId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                DocumentReference documentReference = documentSnapshot.getDocumentReference("recordGeoLocationByDefault");
                documentReference.update("recordGeoLocationByDefault", geoLocation);
            }
        });
    }

    public static void setCreated(Boolean created) {
        UserSettings.created = created;
    }
}
