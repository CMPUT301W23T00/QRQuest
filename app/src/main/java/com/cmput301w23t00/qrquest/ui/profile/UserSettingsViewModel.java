package com.cmput301w23t00.qrquest.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
public class UserSettingsViewModel extends ViewModel {

    private static MutableLiveData<String> mText = null;

    public UserSettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is settings fragment");
    }

    public static LiveData<String> getText() {
        return mText;
    }
}
