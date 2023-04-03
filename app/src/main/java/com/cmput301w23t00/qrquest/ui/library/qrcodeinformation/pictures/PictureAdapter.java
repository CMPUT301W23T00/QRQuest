package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.pictures;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.cmput301w23t00.qrquest.R;

import java.util.ArrayList;
import java.util.List;

public class PictureAdapter extends ArrayAdapter<com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.pictures.PictureData> {

    private Context mContext;
    private List<com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.pictures.PictureData> mData;
    private LinearLayout mlayout;

    public PictureAdapter(@NonNull Context context, ArrayList<com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.pictures.PictureData> Data) {
        super(context, 0, Data);
        this.mContext = context;
        this.mData = Data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the UI layout for each list item
        View listItemView;
        if (convertView == null) {
            listItemView = LayoutInflater.from(super.getContext()).inflate(R.layout.picture, parent, false);
        } else {
            listItemView = convertView;
        }

        // Set the text of the TextView in the layout to the current string in the list
        TextView textView1 = listItemView.findViewById(R.id.picture_date);
        String currentString = mData.get(position).getDate();
        textView1.setText(currentString);

        TextView textView2 = listItemView.findViewById(R.id.picture_username);
        currentString = mData.get(position).getUser();
        textView2.setText(currentString);

        AppCompatImageView imageView1 = listItemView.findViewById(R.id.pictures_user_picture);
        Drawable profileImg = mData.get(position).getProfile();
        imageView1.setBackgroundDrawable(profileImg);

        AppCompatImageView imageView2 = listItemView.findViewById(R.id.pictures_location_img);
        Bitmap FeedImg = mData.get(position).getPicture();
        imageView2.setImageBitmap(FeedImg);

        return listItemView;
    }
}
