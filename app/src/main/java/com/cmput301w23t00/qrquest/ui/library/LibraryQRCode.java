package com.cmput301w23t00.qrquest.ui.library;

import android.widget.LinearLayout;

import java.util.Date;

public class LibraryQRCode {
    private String data;
    private long score;
    private Date date;

    public LibraryQRCode(String data, long score, Date date) {
        this.data = data;
        this.score = score;
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
