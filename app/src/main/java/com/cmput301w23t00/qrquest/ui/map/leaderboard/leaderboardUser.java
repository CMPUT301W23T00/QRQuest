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
    private String userName;
    private String userId;
    private String userAboutMe;
    private String userEmail;
    private String userPhoneNumber;
    private String userAvatarId;

    /**
     * Constructs a User based on a parcel input
     * @param in parcel containing data, score, and date scanned
     */
    protected leaderboardUser(Parcel in) {
        this.score = in.readLong();
        this.userName = in.readString();
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

    public leaderboardUser(String userId, String userName, String userAboutMe, String userEmail, String userPhoneNumber, String userAvatarId, long totalScore, int position) {
        this.score = totalScore;
        this.position = position;
        this.userAboutMe = userAboutMe;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userAvatarId = userAvatarId;
        this.userId = userId;
    }

    public String getUserAvatarId() {
        return userAvatarId;
    }

    public void setUserAvatarId(String userAvatarId) {
        this.userAvatarId = userAvatarId;
    }

    public String getUserAboutMe() {
        return userAboutMe;
    }

    public void setUserAboutMe(String userAboutMe) {
        this.userAboutMe = userAboutMe;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String user) {
        this.userName = user;
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
