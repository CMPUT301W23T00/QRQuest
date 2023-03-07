package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


/**
 * The class  QR code information view model extends view model
 */
public class QRCodeInformationViewModel extends ViewModel {

    private final MutableLiveData<String> mText;


    /**
     *
     *  QR code information view model
     *
     */
    public QRCodeInformationViewModel() {

        mText = new MutableLiveData<>();
        mText.setValue("This is QR Code Information Description");
    }


    /**
     *
     * Gets the text
     *
     * @return the text
     */
    public LiveData<String> getText() {

        return mText;
    }
}
