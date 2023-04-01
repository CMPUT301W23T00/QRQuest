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
import com.cmput301w23t00.qrquest.ui.addqrcode.QRCodeProcessor;
import com.cmput301w23t00.qrquest.ui.profile.UserProfile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

/**
 * This class represents an ArrayAdapter for LibraryQRCodes
 */
public class leaderboardQRCodeAdapter extends ArrayAdapter<leaderboardQRCode> {
    /**
     * Constructor for LibraryQRCodeAdapter
     * @param context information about the application environment
     * @param QRCodes list of QR codes
     */
    public leaderboardQRCodeAdapter(Context context, ArrayList<leaderboardQRCode> QRCodes) {
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
        leaderboardQRCode QRObject = getItem(position);

        String CurrentUserID = UserProfile.getUserId();
        String CurrentUserName = UserProfile.getName();

        if (Objects.equals(QRObject.getUserId(), CurrentUserID) && Objects.equals(QRObject.getUser(), CurrentUserName)) {
            view.setBackgroundResource(R.drawable.leaderboard_current_user);
        } else {
            view.setBackgroundColor (Color.TRANSPARENT); // default color
        }

        // Get textviews for QR code information
        TextView userName = view.findViewById(R.id.leaderboard_user_name);
        TextView QRDate = view.findViewById(R.id.leaderboard_qr_code_date);
        TextView UserPosition = view.findViewById(R.id.leaderboard_qr_code_position);
        TextView QRScore = view.findViewById(R.id.leaderboard_qr_code_score);
        ImageView qrImage = view.findViewById(R.id.leaderboard_qr_code_image);

        // Format date
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.CANADA);
        // Set QR code information on textviews
        userName.setText(QRObject.getUser());
        QRCodeProcessor qrCodeProcessor = new QRCodeProcessor(QRObject.getData());
        UserPosition.setText(String.format(Locale.CANADA, "%d",QRObject.getPosition()));
        QRDate.setText(formatter.format(QRObject.getDate()));
        QRScore.setText(String.format(Locale.CANADA, "%d", qrCodeProcessor.getScore()));

        if(Objects.equals(QRObject.getPosition(), (long) 1)) {
            UserPosition.setBackgroundColor(Color.parseColor("#FFD700"));
        } else if(Objects.equals(QRObject.getPosition(), (long) 2)) {
            UserPosition.setBackgroundColor(Color.parseColor("#C0C0C0"));
        } else if(Objects.equals(QRObject.getPosition(), (long) 3)) {
            UserPosition.setBackgroundColor(Color.parseColor("#CD7F32"));
        } else {
            UserPosition.setBackgroundColor(Color.parseColor("#CC333333")); // default color
        }

        qrImage.setImageBitmap(qrCodeProcessor.getBitmap(getContext()));
        return view;
    }
}

