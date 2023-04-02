package com.cmput301w23t00.qrquest.ui.profile;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * UserProfile class is used to temporarily store user information for the
 * duration of the app to reduce server accesses and act as an
 * intermediary between the server and the user interface when information is updated
 */
public class UserProfile {

    private static String aboutMe;
    private static String phoneNumber;
    private static String email;
    private static String name;
    private static String userId;
    private static Boolean firstInstantiation = true;
    private static Boolean created = false;

    public UserProfile(Boolean test) {
        userId = null;
        if (!test) {
            aboutMe = null;
            phoneNumber = null;
            email = null;
            name = null;
            firstInstantiation = true;
            created = false;
        }
    }
    /**
     * UserProfile constructor, on firstInstantiation of the class the user information is
     * updated to match that of the server and then sets firstInstantiation to false
     * in order to avoid unnecessarily contacting the server
     */
    public UserProfile() {
        //only occurs when app is initially opened
        if (firstInstantiation && created) {
            FirebaseFirestore.getInstance().collection("users").whereEqualTo("identifierId", userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        aboutMe = task.getResult().getDocuments().get(0).getString("aboutMe");
                        phoneNumber = task.getResult().getDocuments().get(0).getString("phoneNumber");
                        email = task.getResult().getDocuments().get(0).getString("email");
                        name = task.getResult().getDocuments().get(0).getString("name");
                        firstInstantiation = false;
                        created = true;
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
    }

    /**
     * aboutMe getter
     * @return returns aboutMe string
     */
    public static String getAboutMe() {
        return aboutMe;
    }

    /**
     * phoneNumber getter
     * @return returns phoneNumber string
     */
    public static String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * email getter
     * @return returns email string
     */
    public static String getEmail() {
        return email;
    }

    /**
     * name getter
     * @return returns name string
     */
    public static String getName() {
        return name;
    }

    /**
     * userId getter
     * @return  returns userId string
     */
    public static String getUserId() {
        return userId;
    }

    /**
     * Used to check if an account exists for settings to be contained in
     * @return created value, whether or not an account exists
     */
    public static Boolean getCreated() {
        return created;
    }

    /**
     * aboutMe setter that additionally updates aboutMe string on the server
     * value, is stored and updated later if connection cannot be established
     * @param aboutMe changes class aboutMe value to parameter value
     */
    public static void setAboutMe(String aboutMe) {
        UserProfile.aboutMe = aboutMe;

        if (userId != null) {
            FirebaseFirestore.getInstance().collection("users").whereEqualTo("identifierId", userId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                    DocumentReference documentReference = documentSnapshot.getReference();
                    documentReference.update("aboutMe", aboutMe);
                }
            });
        }
    }

    /**
     * phoneNumber setter that additionally updates phoneNumber string on the server
     * value, is stored and updated later if connection cannot be established
     * @param phoneNumber changes class phoneNumber value to parameter value
     */
    public static void setPhoneNumber(String phoneNumber) {
        UserProfile.phoneNumber = phoneNumber;

        if (userId != null) {
            FirebaseFirestore.getInstance().collection("users").whereEqualTo("identifierId", userId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                    DocumentReference documentReference = documentSnapshot.getReference();
                    documentReference.update("phoneNumber", phoneNumber);
                }
            });
        }
    }

    /**
     * email setter that additionally updates email string on the server
     * value, is stored and updated later if connection cannot be established
     * @param email changes class email value to parameter value
     */
    public static void setEmail(String email) {
        UserProfile.email = email;

        if (userId != null) {
            FirebaseFirestore.getInstance().collection("users").whereEqualTo("identifierId", userId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                    DocumentReference documentReference = documentSnapshot.getReference();
                    documentReference.update("email", email);
                }
            });
        }
    }

    /**
     * name setter that additionally updates name string on the server
     * value, is stored and updated later if connection cannot be established
     * @param name changes class name value to parameter value
     */
    public static void setName(String name) {
        UserProfile.name = name;

        if (userId != null) {
            FirebaseFirestore.getInstance().collection("users").whereEqualTo("identifierId", userId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                    DocumentReference documentReference = documentSnapshot.getReference();
                    documentReference.update("name", name);
                }
            });
        }
    }

    /**
     * userId setter
     * @param userId sets class userId value to parameter value
     */
    public static void setUserId(String userId) {
        UserProfile.userId = userId;
    }

    /**
     * Lets the UserProfile class know that an account exists to check user profile
     * information from
     * @param created changes class created value to parameter value
     */
    public static void setCreated(Boolean created) {
        UserProfile.created = created;
    }
}
