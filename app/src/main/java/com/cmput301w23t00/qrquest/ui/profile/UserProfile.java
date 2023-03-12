package com.cmput301w23t00.qrquest.ui.profile;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProfile {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static String aboutMe;
    private static String phoneNumber;
    private static String email;
    private static String name;
    private static String userId;
    private static Boolean firstInstantiation = true;
    private static Boolean created = false;

    public UserProfile() {
        //only occurs when app is initially opened
        if (firstInstantiation) {
            this.db.collection("users").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Log.d(TAG, "onSuccess: Accessed user information");
                    aboutMe = documentSnapshot.getString("aboutMe");
                    phoneNumber = documentSnapshot.getString("phoneNumber");
                    email = documentSnapshot.getString("email");
                    name = documentSnapshot.getString("name");
                    firstInstantiation = false;
                    created = true;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "onFailure: ", e);
                }
            });
        }
    }

    public static String getAboutMe() {
        return aboutMe;
    }

    public static String getPhoneNumber() {
        return phoneNumber;
    }

    public static String getEmail() {
        return email;
    }

    public static String getName() {
        return name;
    }

    public static String getUserId() {
        return userId;
    }

    public static Boolean getCreated() {
        return created;
    }

    public static Boolean getFirstInstantiation() {
        return firstInstantiation;
    }

    public void setAboutMe(String aboutMe) {
        UserProfile.aboutMe = aboutMe;

        DocumentReference docRef = this.db.collection("users").document(this.userId);
        docRef.update("aboutMe", aboutMe);
    }

    public void setPhoneNumber(String phoneNumber) {
        UserProfile.phoneNumber = phoneNumber;

        DocumentReference docRef = this.db.collection("users").document(this.userId);
        docRef.update("phoneNumber", phoneNumber);
    }

    public void setEmail(String email) {
        UserProfile.email = email;

        DocumentReference docRef = this.db.collection("users").document(this.userId);
        docRef.update("email", email);
    }

    public void setName(String name) {
        UserProfile.name = name;

        DocumentReference docRef = this.db.collection("users").document(this.userId);
        docRef.update("name", name);
    }

    public static void setUserId(String userId) {
        UserProfile.userId = userId;
    }

    public static void setCreated(Boolean created) {
        UserProfile.created = created;
    }
}
