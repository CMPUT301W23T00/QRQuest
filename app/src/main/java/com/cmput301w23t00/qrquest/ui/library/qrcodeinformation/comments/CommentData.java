package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.comments;

import android.graphics.drawable.Drawable;
import android.media.Image;

/**
 * This class contains data that is shown on the comments feed for each comment.
 */
public class CommentData {

    private String mUser;
    private String mText;
    private Drawable mProfile;

    CommentData(String User, String Text, Drawable Profile) {
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
