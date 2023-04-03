package com.cmput301w23t00.qrquest.ui.map.leaderboard;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.profile.UserProfile;
import com.cmput301w23t00.qrquest.ui.updateavatar.AvatarUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

/**
 * This class represents an ArrayAdapter for LibraryUsers
 */
public class leaderboardUserAdapter extends ArrayAdapter<leaderboardUser> {
    /**
     * Constructor for LibraryQRCodeAdapter
     * @param context information about the application environment
     * @param Users list of Users
     */
    public leaderboardUserAdapter(Context context, ArrayList<leaderboardUser> Users) {
        super(context, 0,Users);
    }

    /**
     * getView inflates the view, showing all the users
     * and returns the modified view
     * @param position position of the User in the list
     * @param convertView view to be converted to display User item
     * @param parent parent view containing group of views
     * @return view of the new QR code item
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) { // if layout is not inflated, inflate
            view = LayoutInflater.from(getContext()).inflate(R.layout.leaderboard_qr_list_content,
                    parent, false);
        } else {
            view = convertView;
        }

        // Get User
        leaderboardUser UserObject = getItem(position);
        String CurrentUserID = UserProfile.getUserId();
        String CurrentUserName = UserProfile.getName();

        if (Objects.equals(UserObject.getUserId(), CurrentUserID) && Objects.equals(UserObject.getUserName(), CurrentUserName)) {
            view.setBackgroundResource(R.drawable.leaderboard_current_user);
        } else {
            view.setBackgroundColor (Color.TRANSPARENT); // default color
        }

        // Get textviews for User information
        TextView userName = view.findViewById(R.id.leaderboard_user_name);
        TextView UserPosition = view.findViewById(R.id.leaderboard_qr_code_position);
        TextView QRScore = view.findViewById(R.id.leaderboard_total_score);
        AppCompatImageView UserProfileImage = view.findViewById(R.id.leaderboard_user_picture);

        // Format date
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.CANADA);

        // Set QR code information on textviews
        userName.setText(UserObject.getUserName());
        UserPosition.setText(String.format(Locale.CANADA, "%d",UserObject.getPosition()));
        QRScore.setText(String.format(Locale.CANADA, "%d", UserObject.getScore()));

        if (Objects.equals(UserObject.getPosition(), (long) 1)) {
            UserPosition.setBackgroundColor(Color.parseColor("#FFD700"));
        } else if (Objects.equals(UserObject.getPosition(), (long) 2)) {
            UserPosition.setBackgroundColor(Color.parseColor("#C0C0C0"));
        } else if (Objects.equals(UserObject.getPosition(), (long) 3)) {
            UserPosition.setBackgroundColor(Color.parseColor("#CD7F32"));
        } else {
            UserPosition.setBackgroundColor(Color.parseColor("#CC333333")); // default color
        }

        // set the UserProfileImage to the correct image after implemented
        try {
            UserProfileImage.setImageResource(AvatarUtility.getAvatarImageResource(Integer.parseInt(UserObject.getUserAvatarId())));
        } catch (Exception e) {
            UserProfileImage.setImageResource(AvatarUtility.getAvatarImageResource(0));
        }

        return view;
    }
}

