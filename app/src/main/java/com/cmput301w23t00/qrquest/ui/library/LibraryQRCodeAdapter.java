package com.cmput301w23t00.qrquest.ui.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.addqrcode.QRCodeProcessor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class LibraryQRCodeAdapter extends ArrayAdapter<LibraryQRCode> {

    public LibraryQRCodeAdapter(Context context, ArrayList<LibraryQRCode> QRCodes) {
        super(context, 0,QRCodes);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.library_qr_list_content,
                    parent, false);
        } else {
            view = convertView;
        }
        // Get QR code
        LibraryQRCode QRObject = getItem(position);
        // Get textviews for QR code information
        TextView QRData = view.findViewById(R.id.library_qr_code_data);
        TextView QRDate = view.findViewById(R.id.library_qr_code_date);
        TextView QRScore = view.findViewById(R.id.library_qr_code_score);
        // Format date
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.CANADA);
        // Set QR code information on textviews
        QRData.setText(QRObject.getData());
        QRCodeProcessor qrCodeProcessor = new QRCodeProcessor(QRObject.getData());
        QRData.setText(qrCodeProcessor.getName());
        QRDate.setText(formatter.format(QRObject.getDate()));
        QRScore.setText(String.format(Locale.CANADA, "%d", qrCodeProcessor.getScore()));
        return view;
    }
}

