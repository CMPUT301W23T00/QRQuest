package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.pictures;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.updateavatar.AvatarUtility;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        // Format date
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.CANADA);
        textView1.setText(formatter.format(mData.get(position).getDate()));

        TextView textView2 = listItemView.findViewById(R.id.picture_username);
        String currentString = mData.get(position).getUser();
        textView2.setText(currentString);

        AppCompatImageView imageView1 = listItemView.findViewById(R.id.location_user_picture);
        int profileImg = mData.get(position).getProfile();
        //imageView1.setBackgroundDrawable(profileImg);
        imageView1.setImageResource(AvatarUtility.getAvatarImageResource(profileImg));

        AppCompatImageView imageView2 = listItemView.findViewById(R.id.pictures_user_picture);
        Uri FeedImg = mData.get(position).getPicture();
        Picasso.with(getContext()).load(FeedImg).into(imageView2);

        return listItemView;
    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "Error getting bitmap", e);
        }
        return bm;
    }
}
