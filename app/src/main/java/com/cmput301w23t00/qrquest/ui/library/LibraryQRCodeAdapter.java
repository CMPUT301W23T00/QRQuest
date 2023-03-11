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
        LibraryQRCode QRObject = getItem(position);
        TextView QRData = view.findViewById(R.id.library_qr_code_data);
        TextView QRDate = view.findViewById(R.id.library_qr_code_date);
        TextView QRScore = view.findViewById(R.id.library_qr_code_score);

        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.CANADA);
        QRData.setText(QRObject.getData());
        QRDate.setText(formatter.format(QRObject.getDate()));
        QRScore.setText(String.format(Locale.CANADA, "%d", QRObject.getScore()));
        return view;
    }
}

