package com.cmput301w23t00.qrquest.ui.library;

import android.widget.LinearLayout;

import java.util.Date;

public class LibraryQRCode {
    private String data;
    private Integer score;
    private Date date;

    public LibraryQRCode(String data, Integer score, Date date) {
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
