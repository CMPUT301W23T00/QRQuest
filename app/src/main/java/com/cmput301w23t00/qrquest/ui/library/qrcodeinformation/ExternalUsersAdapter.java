package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.externaluserpage.ExternalUserProfile;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class ExternalUsersAdapter extends ArrayAdapter<ExternalUserProfile> {
    public ExternalUsersAdapter(Context context, ArrayList<ExternalUserProfile> users) {
        super(context, 0, users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) { // if layout is not inflated, inflate
            view = LayoutInflater.from(getContext()).inflate(R.layout.externalusers_list_content,
                    parent, false);
        } else {
            view = convertView;
        }

        ExternalUserProfile externalUser = getItem(position);
        TextView userName = view.findViewById(R.id.externaluser_list_name);
        TextView userAboutMe = view.findViewById(R.id.externaluser_list_aboutme);

        userName.setText(externalUser.getName());
        userAboutMe.setText(externalUser.getAboutMe());
        return view;
    }
}
