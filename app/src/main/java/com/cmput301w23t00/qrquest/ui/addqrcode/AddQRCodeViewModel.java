package com.cmput301w23t00.qrquest.ui.addqrcode;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddQRCodeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AddQRCodeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Add QRCode fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}