package com.cmput301w23t00.qrquest.ui.updateavatar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.profile.UserProfile;

import java.util.Objects;

public class UpdateAvatar extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_profile_avatar);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Choose Avatar");

        Intent intentFromCaller = getIntent();
        String name = intentFromCaller.getStringExtra("name");
        String aboutMe = intentFromCaller.getStringExtra("aboutMe");
        String email = intentFromCaller.getStringExtra("email");
        String phone = intentFromCaller.getStringExtra("phone");

        GridLayout gridLayout = findViewById(R.id.updateAvatarGrid);
        int childCount = gridLayout.getChildCount();
        for (int i = 0; i < childCount; i++){
            FrameLayout avatar = (FrameLayout) gridLayout.getChildAt(i);
            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    @SuppressLint("ResourceType") int avatarId = avatar.getId() + 1;
                    Intent intentToCaller = new Intent();
                    intentToCaller.putExtra("name", name);
                    intentToCaller.putExtra("aboutMe", aboutMe);
                    intentToCaller.putExtra("email", email);
                    intentToCaller.putExtra("phone", phone);

                    setResult(Activity.RESULT_OK, intentToCaller);

                    UserProfile.setAvatarId(Integer.toString(avatarId));
                    finish();
                }
            });
        }

        /*

        updateAvatarConfirmButton = findViewById(R.id.updateAvatarConfirmButton);
        updateAvatarCancelButton = findViewById(R.id.updateAvatarCancelButton);


        updateAvatarConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = profile.edit();
                editor.putInt("user_avatar", id);
                editor.apply();
            }
        });

        updateAvatarCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

         */

    }
}
