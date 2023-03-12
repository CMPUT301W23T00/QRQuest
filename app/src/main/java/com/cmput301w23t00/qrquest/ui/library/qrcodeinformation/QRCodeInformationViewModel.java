package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * The QRCodeInformationViewModel class is a ViewModel that holds data for the QR code information fragment.
 * It extends the ViewModel class from the Android Jetpack library.
 */
public class QRCodeInformationViewModel extends ViewModel {

    private MutableLiveData<String> mName;
    private MutableLiveData<String> mDescription;

    public QRCodeInformationViewModel() {
        mName = new MutableLiveData<>();
        mDescription = new MutableLiveData<>();
    }

    public LiveData<String> getName() {
        return mName;
    }

    public MutableLiveData<String> getDescription() {
        return mDescription;
    }

    public void setQRCodeInfo(String name, String description) {
        mName.setValue(name);
        mDescription.setValue(description);
    }
}

