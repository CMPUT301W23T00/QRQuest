package com.cmput301w23t00.qrquest.ui.map.leaderboard;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * This class defines a User for leaderboard
 */
public class leaderboardUser implements Parcelable {
    private long score;
    private long position;
    private String user;
    private String userId;

    /**
     * Constructs a User based on a parcel input
     * @param in parcel containing data, score, and date scanned
     */
    protected leaderboardUser(Parcel in) {
        this.score = in.readLong();
        this.user = in.readString();
        this.userId = in.readString();
    }

    public static final Creator<leaderboardUser> CREATOR = new Creator<leaderboardUser>() {
        @Override
        public leaderboardUser createFromParcel(Parcel in) {
            return new leaderboardUser(in);
        }

        @Override
        public leaderboardUser[] newArray(int size) {
            return new leaderboardUser[size];
        }
    };

    public leaderboardUser(String userId, String userName, long score, int position) {
        this.score = score;
        this.position = position;
        this.user = userName;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Gets score of User
     * @return score of User
     */
    public long getScore() {
        return score;
    }

    /**
     * Sets score of User
     * @param score
     */
    public void setScore(long score) {
        this.score = score;
    }


    /**
     * Describes contents of User
     * @return 0
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Writes to a parcel
     * @param parcel parcel containing information about User
     * @param i flags
     */
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt((int) score);
    }

    /**
     * Compares Users based on their score
     * @param anotherUser User to compare to
     * @return -1, 0, or 1 depending on the score comparison
     */
    public int compareTo(leaderboardUser anotherUser) {
        if (this.score < anotherUser.score) {
            return 1;
        } else if (this.score > anotherUser.score) {
            return -1;
        } else {
            return 0;
        }
    }
}
