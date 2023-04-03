package com.cmput301w23t00.qrquest.ui.updateavatar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.profile.UserProfile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

// this is a dialog window where we ask the user to input a EditAvatarFragment or edit one, the design decision was done my the lab instructions in lab 3
public class EditAvatarFragment extends DialogFragment {
    private EditAvatarDialogListener listener;
    int avatarId = 0;
    public EditAvatarFragment() {
    }

    public EditAvatarFragment(int avatarId) {
        this.avatarId = avatarId;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditAvatarDialogListener) {
            listener = (EditAvatarDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_edit_avatar, null);

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Change Avatar")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Confirm", null)
                .create();

        if (UserProfile.getAvatarId() != null) {
            avatarId = Integer.parseInt(UserProfile.getAvatarId());
        }
        ArrayList<Integer> nonUsedAvatars = AvatarUtility.getNonUseAvatarIds(avatarId);

        ImageView edit_avatar_0 = view.findViewById(R.id.edit_avatar_0);
        edit_avatar_0.setImageResource(AvatarUtility.getAvatarImageResource(nonUsedAvatars.get(0)));
        edit_avatar_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.updateProfilePicture(nonUsedAvatars.get(0));
                dialog.dismiss();
            }
        });

        ImageView edit_avatar_1 = view.findViewById(R.id.edit_avatar_1);
        edit_avatar_1.setImageResource(AvatarUtility.getAvatarImageResource(nonUsedAvatars.get(1)));
        edit_avatar_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.updateProfilePicture(nonUsedAvatars.get(1));
                dialog.dismiss();
            }
        });

        ImageView edit_avatar_2 = view.findViewById(R.id.edit_avatar_2);
        edit_avatar_2.setImageResource(AvatarUtility.getAvatarImageResource(nonUsedAvatars.get(2)));
        edit_avatar_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.updateProfilePicture(nonUsedAvatars.get(2));
                dialog.dismiss();
            }
        });

        ImageView edit_avatar_3 = view.findViewById(R.id.edit_avatar_3);
        edit_avatar_3.setImageResource(AvatarUtility.getAvatarImageResource(nonUsedAvatars.get(3)));
        edit_avatar_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.updateProfilePicture(nonUsedAvatars.get(3));
                dialog.dismiss();
            }
        });

        ImageView edit_avatar_4 = view.findViewById(R.id.edit_avatar_4);
        edit_avatar_4.setImageResource(AvatarUtility.getAvatarImageResource(nonUsedAvatars.get(4)));
        edit_avatar_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.updateProfilePicture(nonUsedAvatars.get(4));
                dialog.dismiss();
            }
        });

        ImageView edit_avatar_5 = view.findViewById(R.id.edit_avatar_5);
        edit_avatar_5.setImageResource(AvatarUtility.getAvatarImageResource(nonUsedAvatars.get(5)));
        edit_avatar_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.updateProfilePicture(nonUsedAvatars.get(5));
                dialog.dismiss();
            }
        });

        ImageView edit_avatar_6 = view.findViewById(R.id.edit_avatar_6);
        edit_avatar_6.setImageResource(AvatarUtility.getAvatarImageResource(nonUsedAvatars.get(6)));
        edit_avatar_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.updateProfilePicture(nonUsedAvatars.get(6));
                dialog.dismiss();
            }
        });

        ImageView edit_avatar_7 = view.findViewById(R.id.edit_avatar_7);
        edit_avatar_7.setImageResource(AvatarUtility.getAvatarImageResource(nonUsedAvatars.get(7)));
        edit_avatar_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.updateProfilePicture(nonUsedAvatars.get(7));
                dialog.dismiss();
            }
        });

        ImageView edit_avatar_8 = view.findViewById(R.id.edit_avatar_8);
        edit_avatar_8.setImageResource(AvatarUtility.getAvatarImageResource(nonUsedAvatars.get(8)));
        edit_avatar_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.updateProfilePicture(nonUsedAvatars.get(8));
                dialog.dismiss();
            }
        });

        ImageView edit_avatar_9 = view.findViewById(R.id.edit_avatar_9);
        edit_avatar_9.setImageResource(AvatarUtility.getAvatarImageResource(nonUsedAvatars.get(9)));
        edit_avatar_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.updateProfilePicture(nonUsedAvatars.get(9));
                dialog.dismiss();
            }
        });

        ImageView edit_avatar_10 = view.findViewById(R.id.edit_avatar_10);
        edit_avatar_10.setImageResource(AvatarUtility.getAvatarImageResource(nonUsedAvatars.get(10)));
        edit_avatar_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.updateProfilePicture(nonUsedAvatars.get(10));
                dialog.dismiss();
            }
        });

        ImageView edit_avatar_11 = view.findViewById(R.id.edit_avatar_11);
        edit_avatar_11.setImageResource(AvatarUtility.getAvatarImageResource(nonUsedAvatars.get(11)));
        edit_avatar_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.updateProfilePicture(nonUsedAvatars.get(11));
                dialog.dismiss();
            }
        });

        return dialog;
    }
}


