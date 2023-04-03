package com.cmput301w23t00.qrquest.ui.map;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.Date;

/**
 *
 */
public class QRCodeSuggestions implements SearchSuggestion {

    private String name;
    private String qrCodeData;
    private Date date;
    private String documentId;
    private String identifierId;

    public String getName() {
        return name;
    }

    public String getQrCodeData() {
        return qrCodeData;
    }

    public Date getDate() {
        return date;
    }

    public String getIdentifierId() {
        return identifierId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public QRCodeSuggestions(String name, String qrCodeData, String identifierId, String documentId, Date date) {
        this.name = name;
        this.qrCodeData = qrCodeData;
        this.identifierId = identifierId;
        this.documentId = documentId;
        this.date = date;
    }

    public QRCodeSuggestions(Parcel source) {
        this.name = source.readString();
        this.qrCodeData = source.readString();
        this.identifierId = source.readString();
        this.documentId = source.readString();
        this.date = (java.util.Date) source.readSerializable();
    }

    @Override
    public String getBody() {
        return name;
    }

    public static final Creator<QRCodeSuggestions> CREATOR = new Creator<QRCodeSuggestions>() {
        @Override
        public QRCodeSuggestions createFromParcel(Parcel in) {
            return new QRCodeSuggestions(in);
        }

        @Override
        public QRCodeSuggestions[] newArray(int size) {
            return new QRCodeSuggestions[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(qrCodeData);
        parcel.writeString(identifierId);
        parcel.writeString(documentId);
        parcel.writeSerializable(date);
    }
}
