package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.pictures;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class PictureData {

    private String mUsername;
    private String mDate;
    private Drawable mProfile;
    private Bitmap mPicture;

    PictureData(String User, String Text, Drawable Profile, Bitmap Picture) {
        this.mUsername = User;
        this.mDate = Text;
        this.mProfile = Profile;
        this.mPicture = Picture;
    }

    String getUser() {
        return mUsername;
    }

    String getDate() {
        return mDate;
    }

    Drawable getProfile() {
        return mProfile;
    }

    Bitmap getPicture() {
        return mPicture;
    }
}
