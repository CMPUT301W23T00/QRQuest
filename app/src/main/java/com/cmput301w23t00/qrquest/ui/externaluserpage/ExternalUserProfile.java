package com.cmput301w23t00.qrquest.ui.externaluserpage;

import static android.content.ContentValues.TAG;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Timer;

public class ExternalUserProfile implements Parcelable {
    private String name;
    private String aboutMe;
    private String userId;
    private String phoneNumber;
    private String email;
    private String avatarId;

    public ExternalUserProfile() {
        name = "Bob";
        aboutMe = "am Bob";
        phoneNumber = "123-000-1234";
        email = "bob@bob.bob";
        userId = "1010110";
    }
    public ExternalUserProfile(String name, String aboutMe, String userId, String phoneNumber, String email) {
        this.name = name;
        this.aboutMe = aboutMe;
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public ExternalUserProfile(String userId) {
        this.userId = userId;
    }

    protected ExternalUserProfile(Parcel in) {
        name = in.readString();
        aboutMe = in.readString();
        userId = in.readString();
        phoneNumber = in.readString();
        email = in.readString();
    }

    public static final Creator<ExternalUserProfile> CREATOR = new Creator<ExternalUserProfile>() {
        @Override
        public ExternalUserProfile createFromParcel(Parcel in) {
            return new ExternalUserProfile(in);
        }

        @Override
        public ExternalUserProfile[] newArray(int size) {
            return new ExternalUserProfile[size];
        }
    };

    public ExternalUserProfile(String userId, String userName, String userAboutMe, String userEmail, String userPhoneNumber, String userAvatarId) {
        this.userId = userId;
        name = userName;
        aboutMe = userAboutMe;
        phoneNumber = userPhoneNumber;
        email = userEmail;
        avatarId = userAvatarId;
    }

    public String getName() {
        return name;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarId() {
        return avatarId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(aboutMe);
        parcel.writeString(userId);
        parcel.writeString(phoneNumber);
        parcel.writeString(email);
    }
}
