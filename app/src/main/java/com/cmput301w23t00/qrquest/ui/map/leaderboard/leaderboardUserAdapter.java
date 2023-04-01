package com.cmput301w23t00.qrquest.ui.map.leaderboard;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.profile.UserProfile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

/**
 * This class represents an ArrayAdapter for LibraryQRCodes
 */
public class leaderboardUserAdapter extends ArrayAdapter<leaderboardUser> {
    /**
     * Constructor for LibraryQRCodeAdapter
     * @param context information about the application environment
     * @param QRCodes list of QR codes
     */
    public leaderboardUserAdapter(Context context, ArrayList<leaderboardUser> QRCodes) {
        super(context, 0,QRCodes);
    }

    /**
     * getView inflates the view, showing a user's collection of QR codes
     * and returns the modified view
     * @param position position of the QR code in the list
     * @param convertView view to be converted to display QR code item
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

        // Get QR code
        leaderboardUser UserObject = getItem(position);
        String CurrentUserID = UserProfile.getUserId();
        String CurrentUserName = UserProfile.getName();

        if (Objects.equals(UserObject.getUserId(), CurrentUserID) && Objects.equals(UserObject.getUser(), CurrentUserName)) {
            view.setBackgroundResource(R.drawable.leaderboard_current_user);
        } else {
            view.setBackgroundColor (Color.TRANSPARENT); // default color
        }

        // Get textviews for QR code information
        TextView userName = view.findViewById(R.id.leaderboard_user_name);
        TextView UserPosition = view.findViewById(R.id.leaderboard_qr_code_position);
        TextView QRScore = view.findViewById(R.id.leaderboard_total_score);
        ImageView UserProfileImage = view.findViewById(R.id.leaderboard_user_profile_image);

        // Format date
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.CANADA);

        // Set QR code information on textviews
        userName.setText(UserObject.getUser());
        UserPosition.setText(String.format(Locale.CANADA, "%d",UserObject.getPosition()));
        QRScore.setText(String.format(Locale.CANADA, "%d", UserObject.getScore()));

        if(Objects.equals(UserObject.getPosition(), (long) 1)) {
            UserPosition.setBackgroundColor(Color.parseColor("#FFD700"));
        } else if(Objects.equals(UserObject.getPosition(), (long) 2)) {
            UserPosition.setBackgroundColor(Color.parseColor("#C0C0C0"));
        } else if(Objects.equals(UserObject.getPosition(), (long) 3)) {
            UserPosition.setBackgroundColor(Color.parseColor("#CD7F32"));
        } else {
            UserPosition.setBackgroundColor(Color.parseColor("#CC333333")); // default color
        }

        //qrImage.setImageBitmap(qrCodeProcessor.getBitmap(getContext()));
        return view;
    }
}

