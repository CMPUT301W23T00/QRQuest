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

/**
 * used to store and give information for a given external user
 */
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
        avatarId = "0";
    }

    public ExternalUserProfile(String name, String aboutMe, String userId, String phoneNumber, String email, String avatarId) {
        this.name = name;
        this.aboutMe = aboutMe;
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.avatarId = avatarId;
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
        avatarId = in.readString();
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

    /**
     * name getter
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * aboutMe getter
     * @return aboutMe
     */
    public String getAboutMe() {
        return aboutMe;
    }

    /**
     * phoneNumber getter
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * email getter
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * userId getter
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * name setter
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * aboutMe setter
     * @param aboutMe
     */
    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    /**
     * phoneNumber setter
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * email setter
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * avatarId getter
     * @return avatarId
     */
    public String getAvatarId() {
        return avatarId;
    }

    /**
     * avatarId setter
     * @param avatarId
     */
    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    /**
     * parcelable
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * parcelable implementation
     * @param parcel
     * @param i
     */
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(aboutMe);
        parcel.writeString(userId);
        parcel.writeString(phoneNumber);
        parcel.writeString(email);
        parcel.writeString(avatarId);
    }
}
