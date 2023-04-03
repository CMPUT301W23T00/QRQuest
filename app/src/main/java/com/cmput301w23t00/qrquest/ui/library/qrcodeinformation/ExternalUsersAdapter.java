package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation;

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
import com.cmput301w23t00.qrquest.ui.externaluserpage.ExternalUserProfile;
import com.cmput301w23t00.qrquest.ui.updateavatar.AvatarUtility;

import java.util.ArrayList;
import java.util.Objects;

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
        ImageView userAvatar = view.findViewById(R.id.externeal_users_list_icon);

        userName.setText(externalUser.getName());
        userAboutMe.setText(externalUser.getAboutMe());
        String iconId = (externalUser.getAvatarId() == null) ? "0" :  externalUser.getAvatarId();
        iconId = (Objects.equals(iconId, "")) ? "0" : iconId;
        userAvatar.setImageResource(AvatarUtility.getAvatarImageResource(Integer.parseInt(iconId)));
        return view;
    }
}
