package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * The QRCodeInformationViewModel class is a ViewModel that holds data for the QR code information fragment.
 * It extends the ViewModel class from the Android Jetpack library.
 */
public class QRCodeInformationViewModel extends ViewModel {

    // Two MutableLiveData instances to store QR Code name and description
    private MutableLiveData<String> mName;
    private MutableLiveData<String> mDescription;

    /**
     * Constructor for QRCodeInformationViewModel class
     * Initializes MutableLiveData instances for mName and mDescription.
     */
    public QRCodeInformationViewModel() {
        mName = new MutableLiveData<>();
        mDescription = new MutableLiveData<>();
    }

    /**
     * Getter method for mName LiveData.
     * @return LiveData object for mName.
     */
    public LiveData<String> getName() {
        return mName;
    }

    /**
     * Getter method for mDescription MutableLiveData.
     * @return MutableLiveData object for mDescription.
     */
    public MutableLiveData<String> getDescription() {
        return mDescription;
    }

    /**
     * Setter method for QR code information.
     * Sets the values of mName and mDescription with the provided parameters.
     * @param name Name of the QR code.
     * @param description Description of the QR code.
     */
    public void setQRCodeInfo(String name, String description) {
        mName.setValue(name);
        mDescription.setValue(description);
    }
}

