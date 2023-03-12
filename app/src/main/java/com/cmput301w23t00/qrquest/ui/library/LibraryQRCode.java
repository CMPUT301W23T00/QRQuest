package com.cmput301w23t00.qrquest.ui.library;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class LibraryQRCode implements Parcelable {
    private String data;
    private int score;
    private Date date;

    public LibraryQRCode(String data, int score, Date date) {
        this.data = data;
        this.score = score;
        this.date = date;
    }

    protected LibraryQRCode(Parcel in) {
        data = in.readString();
        score = in.readInt();
        date = new Date(in.readLong());
    }

    public static final Creator<LibraryQRCode> CREATOR = new Creator<LibraryQRCode>() {
        @Override
        public LibraryQRCode createFromParcel(Parcel in) {
            return new LibraryQRCode(in);
        }

        @Override
        public LibraryQRCode[] newArray(int size) {
            return new LibraryQRCode[size];
        }
    };

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(data);
    }
}
