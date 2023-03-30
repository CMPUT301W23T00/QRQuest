package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.comments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.cmput301w23t00.qrquest.R;
import com.google.api.Distribution;

import java.util.List;

public class CommentAdapter extends ArrayAdapter<CommentData> {

    private Context mContext;
    private List<CommentData> mData;
    private LinearLayout mlayout;

    public CommentAdapter(@NonNull Context context, List<CommentData> Data, LinearLayout layout) {
        super(context, 0, Data);
        this.mContext = context;
        this.mData = Data;
        this.mlayout = layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the UI layout for each list item
        View listItemView;
        if (convertView == null) {
            listItemView = LayoutInflater.from(super.getContext()).inflate(R.layout.comment, parent, false);
        } else {
            listItemView = convertView;
        }

        // Set the text of the TextView in the layout to the current string in the list
        TextView textView1 = listItemView.findViewById(R.id.comment_text);
        String currentString = mData.get(position).getText();
        textView1.setText(currentString);

        TextView textView2 = listItemView.findViewById(R.id.comment_username);
        currentString = mData.get(position).getUser();
        textView2.setText(currentString);

        AppCompatImageView imageView = listItemView.findViewById(R.id.comment_user_picture);
        Drawable currentImage = mData.get(position).getProfile();
        imageView.setImageDrawable(currentImage);

        return listItemView;
    }
}
