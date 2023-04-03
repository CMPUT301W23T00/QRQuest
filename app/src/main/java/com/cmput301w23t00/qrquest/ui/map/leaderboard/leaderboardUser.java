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

    /**
     * Creator implementation for the leaderboardUser class.
     * Provides methods to create a new leaderboardUser object from a Parcel and an array of leaderboardUser objects.
     */
    public static final Creator<leaderboardUser> CREATOR = new Creator<leaderboardUser>() {
        /**
         * Creates a new leaderboardUser object from a Parcel.
         *
         * @param in the Parcel containing the data to create a new leaderboardUser object
         * @return the new leaderboardUser object created from the Parcel
         */
        @Override
        public leaderboardUser createFromParcel(Parcel in) {
            return new leaderboardUser(in);
        }

        /**
         * Creates an array of leaderboardUser objects of the given size.
         *
         * @param size the size of the array to create
         * @return the new array of leaderboardUser objects
         */
        @Override
        public leaderboardUser[] newArray(int size) {
            return new leaderboardUser[size];
        }
    };

    /**
     * Constructs a new leaderboardUser object with the specified values.
     *
     * @param userId the user's unique ID
     * @param userName the user's name
     * @param userAboutMe a short description of the user
     * @param userEmail the user's email address
     * @param userPhoneNumber the user's phone number
     * @param userAvatarId the ID of the user's avatar image
     * @param totalScore the user's total score
     * @param position the user's position on the leaderboard
     */
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

    /**
     * Returns the ID of the user's avatar image.
     *
     * @return the ID of the user's avatar image
     */
    public String getUserAvatarId() {
        return userAvatarId;
    }

    /**
     * Sets the ID of the user's avatar image.
     *
     * @param userAvatarId the new ID of the user's avatar image
     */
    public void setUserAvatarId(String userAvatarId) {
        this.userAvatarId = userAvatarId;
    }

    /**
     * Returns a short description of the user.
     *
     * @return a short description of the user
     */
    public String getUserAboutMe() {
        return userAboutMe;
    }

    /**
     * Sets the short description of the user.
     *
     * @param userAboutMe the new short description of the user
     */
    public void setUserAboutMe(String userAboutMe) {
        this.userAboutMe = userAboutMe;
    }

    /**
     * Returns the user's email address.
     *
     * @return the user's email address
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Sets the user's email address.
     *
     * @param userEmail the new email address for the user
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * Returns the user's phone number.
     *
     * @return the user's phone number
     */
    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    /**
     * Sets the user's phone number.
     *
     * @param userPhoneNumber the new phone number for the user
     */
    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    /**
     * Returns the user's unique ID.
     *
     * @return the user's unique ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * sets user id
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * gets users rank in leaderboard
     * @return
     */
    public long getPosition() {
        return position;
    }

    /**
     * sets users position in leaderboard
     * @param position
     */
    public void setPosition(long position) {
        this.position = position;
    }


    /**
     * gets users name
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * sets users name
     * @param user
     */
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
