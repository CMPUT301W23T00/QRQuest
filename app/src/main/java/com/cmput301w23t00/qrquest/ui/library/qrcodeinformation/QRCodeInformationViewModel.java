package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QRCodeInformationViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public QRCodeInformationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is QR Code Information Description");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
