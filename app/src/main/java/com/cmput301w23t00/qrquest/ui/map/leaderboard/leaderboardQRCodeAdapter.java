package com.cmput301w23t00.qrquest.ui.map.leaderboard;

import android.content.Context;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

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
            view = LayoutInflater.from(getContext()).inflate(R.layout.library_qr_list_content,
                    parent, false);
        } else {
            view = convertView;
        }
        // Get QR code
        leaderboardQRCode QRObject = getItem(position);
        // Get textviews for QR code information
        TextView QRData = view.findViewById(R.id.library_qr_code_data);
        TextView QRDate = view.findViewById(R.id.library_qr_code_date);
        TextView QRScore = view.findViewById(R.id.library_qr_code_score);
        ImageView qrImage = view.findViewById(R.id.library_qr_code_image);
        // Format date
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.CANADA);
        // Set QR code information on textviews
        QRData.setText(QRObject.getData());
        QRCodeProcessor qrCodeProcessor = new QRCodeProcessor(QRObject.getData());
        QRData.setText(qrCodeProcessor.getName());
        QRDate.setText(formatter.format(QRObject.getDate()));
        QRScore.setText(String.format(Locale.CANADA, "%d", qrCodeProcessor.getScore()));

        qrImage.setImageBitmap(qrCodeProcessor.getBitmap(getContext()));
        return view;
    }
}

