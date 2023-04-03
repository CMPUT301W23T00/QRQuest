package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.pictures;


import android.net.Uri;

import java.util.Date;

public class PictureData {

    private String mUsername;
    private Date mDate;
    private int mProfile;
    private Uri mPicture;

    PictureData(String User, Date Text, int Profile, Uri Picture) {
        this.mUsername = User;
        this.mDate = Text;
        this.mProfile = Profile;
        this.mPicture = Picture;
    }

    String getUser() {
        return mUsername;
    }

    Date getDate() {
        return mDate;
    }

    int getProfile() {
        return mProfile;
    }

    Uri getPicture() {
        return mPicture;
    }
}
