package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


/**
 * The QRCodeInformationViewModel class is a ViewModel that holds data for the QR code information fragment.
 * It extends the ViewModel class from the Android Jetpack library.
 */
public class QRCodeInformationViewModel extends ViewModel {

    private final MutableLiveData<String> mText;


    /**
     *
     *  The constructor for the QRCodeInformationViewModel class.
     */
    public QRCodeInformationViewModel() {

        mText = new MutableLiveData<>();
        mText.setValue("This is QR Code Information Description");
    }


    /**
     *
     * This method returns a LiveData object that contains the text to be displayed in the QR code information fragment.
     * @return the text to be displayed
     */
    public LiveData<String> getText() {

        return mText;
    }
}
