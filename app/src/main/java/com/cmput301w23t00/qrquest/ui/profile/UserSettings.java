package com.cmput301w23t00.qrquest.ui.profile;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.cmput301w23t00.qrquest.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    private static String userId = UserProfile.getUserId();
    private static Boolean created = false;

    public UserSettings() {
        this.db.collection("users").whereEqualTo("identifierId", userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    geoLocation = task.getResult().getDocuments().get(0).getBoolean("recordGeoLocationByDefault");
                }
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
                DocumentReference documentReference = documentSnapshot.getReference();
                documentReference.update("recordGeoLocationByDefault", geoLocation);
            }
        });
    }

    public static void setCreated(Boolean created) {
        UserSettings.created = created;
    }

    public static void setUserId(String userId) {
        UserSettings.userId = userId;
    }
}
