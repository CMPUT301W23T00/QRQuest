package com.cmput301w23t00.qrquest.ui.map.leaderboard;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

/**
 * This class defines a QR code
 */
public class leaderboardQRCode implements Parcelable {
    private String data;
    private long score;
    private Date date;
    private long position;

    /**
     * Constructs a QR code based on input
     * @param data data of QR code
     * @param score score of QR code
     * @param date date QR code was scanned
     * @param position position of user based on qr code
     */
    public leaderboardQRCode(String data, long score, Date date, long position) {
        this.data = data;
        this.score = score;
        this.date = date;
        this.position = position;
    }

    /**
     * Constructs a QR code based on a parcel input
     * @param in parcel containing data, score, and date scanned
     */
    protected leaderboardQRCode(Parcel in) {
        this.data = in.readString();
        this.score = in.readLong();
        date = new Date(in.readLong());
        this.position = in.readLong();
    }

    public static final Creator<leaderboardQRCode> CREATOR = new Creator<leaderboardQRCode>() {
        @Override
        public leaderboardQRCode createFromParcel(Parcel in) {
            return new leaderboardQRCode(in);
        }

        @Override
        public leaderboardQRCode[] newArray(int size) {
            return new leaderboardQRCode[size];
        }
    };

    /**
     * Gets QR code data
     * @return data of QR code
     */
    public String getData() {
        return data;
    }

    /**
     * Sets data of QR code
     * @param data new QR code data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Gets score of QR code
     * @return score of QR code
     */
    public long getScore() {
        return score;
    }

    /**
     * Sets score of QR code
     * @param score
     */
    public void setScore(long score) {
        this.score = score;
    }

    /**
     * Gets date QR code was scanned
     * @return date QR code was scanned
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date QR code was scanned
     * @param date new date when QR code was scanned
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Describes contents of QR code
     * @return 0
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Writes to a parcel
     * @param parcel parcel containing information about QR code
     * @param i flags
     */
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(data);
        parcel.writeInt((int) score);
        parcel.writeLong(date.getTime());
    }
}
