package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.pictures;

import android.graphics.drawable.Drawable;

public class PictureData {

    private String mUser;
    private String mText;
    private Drawable mProfile;

    PictureData(String User, String Text, Drawable Profile) {
        this.mUser = User;
        this.mText = Text;
        this.mProfile = Profile;
    }

    String getUser() {
        return mUser;
    }

    String getText() {
        return mText;
    }

    Drawable getProfile() {
        return mProfile;
    }
}
