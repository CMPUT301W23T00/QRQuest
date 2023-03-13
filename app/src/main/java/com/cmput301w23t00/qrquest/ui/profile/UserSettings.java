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

/**
 * UserSettings class is used to temporarily store user information for the
 * duration of the app to reduce server accesses and act as an
 * intermediary between the server and the user interface when information is updated
 */
public class UserSettings {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static Boolean geoLocation = true;
    private static Boolean pushNotifications = true;
    private static String userId = UserProfile.getUserId();
    private static Boolean created = false;


    /**
     * UserSettings constructor, updates the user geo-location settings from the server
     * if the values are differeing when UserSettings is called
     */
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

    /**
     * pushNotifications getter
     * @return pushNotifications settings value
     */
    public Boolean getPushNotifications() {
        return pushNotifications;
    }

    /**
     * geoLocation getter
     * @return geoLocation settings value
     */
    public Boolean getGeoLocation() {
        return geoLocation;
    }

    /**
     * Used to check if an account exists for settings to be contained in
     * @return created value, whether or not an account exists
     */
    public static Boolean getCreated() {
        return created;
    }

    /**
     * pushNotifications setter
     * @param pushNotifications changes class pushNotifications value to parameter value
     */
    public void setPushNotifications(Boolean pushNotifications) {
        this.pushNotifications = pushNotifications;
    }

    /**
     * geoNotifications setter that additionally updates geoLocation setting on the server
     * value, is stored and updated later if connection cannot be established
     * @param geoLocation changes class geoLocation value to parameter value
     */
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

    /**
     * Lets the UserSettings class know that an account exists to check settings
     * preferences from
     * @param created changes class created value to parameter value
     */
    public static void setCreated(Boolean created) {
        UserSettings.created = created;
    }
}
