package com.cmput301w23t00.qrquest.ui.editaccount;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.profile.UserProfile;
import com.cmput301w23t00.qrquest.ui.updateavatar.AvatarUtility;
import com.cmput301w23t00.qrquest.ui.updateavatar.EditAvatarDialogListener;
import com.cmput301w23t00.qrquest.ui.updateavatar.EditAvatarFragment;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 *  This activity lets the user edit their account and save their information in the Firestore database. The user
 *  must input have a name, email, and a phone number to proceed, all of which can be edited, along with an optional About Me section.
 *  Likewise to CreateAccount, a pop up dialog to let the user choose an avatar as a display image is available too.
 */
public class EditAccount extends AppCompatActivity implements EditAvatarDialogListener {
    EditText editNameField, editAboutMeField, editEmailField, editPhoneField;
    Button editCancelButton, editConfirmButton;
    ImageView editProfileImage;
    int profilePictureID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_account);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Profile");

        editNameField = findViewById(R.id.editNameField);
        editAboutMeField = findViewById(R.id.editAboutMeField);
        editEmailField = findViewById(R.id.editEmailField);
        editPhoneField = findViewById(R.id.editPhoneField);
        editCancelButton = findViewById(R.id.editCancelButton);
        editConfirmButton = findViewById(R.id.editConfirmButton);
        editProfileImage = findViewById(R.id.editProfileImage);

        // Gets relevant values from UserProfile.
        String name = UserProfile.getName();
        String aboutMe = UserProfile.getAboutMe();
        String phoneNum = UserProfile.getPhoneNumber();
        String email = UserProfile.getEmail();
        String avatarId = UserProfile.getAvatarId();

        // Sets user value in input field.
        editNameField.setText(name);
        editAboutMeField.setText(aboutMe);
        editEmailField.setText(email);
        editPhoneField.setText(phoneNum);
        editProfileImage.setImageResource(AvatarUtility.getAvatarImageResource(Integer.parseInt(avatarId)));

        editProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EditAvatarFragment(profilePictureID).show(getSupportFragmentManager(), "Change Profile");
            }
        });

        editCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameInput = editNameField.getText().toString();
                String emailInput = editEmailField.getText().toString();
                String phoneNumInput = editPhoneField.getText().toString();
                String aboutMeInput = editAboutMeField.getText().toString();

                // VALIDATION CHECKS
                if (TextUtils.isEmpty(nameInput)){
                    editNameField.setError("Please enter your name.");
                    return;
                }

                if (TextUtils.isEmpty(phoneNumInput)){
                    editPhoneField.setError("Please enter your phone number.");
                    return;
                }

                if (TextUtils.isEmpty(emailInput)){
                    editEmailField.setError("Please enter your email address.");
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                    editEmailField.setError("Please enter a valid email address.");
                    return;
                }

                String patternString = "^\\d{3}-\\d{3}-\\d{4}$";
                Pattern pattern = Pattern.compile(patternString);
                if(!pattern.matcher(phoneNumInput).matches()){
                    editPhoneField.setError("Please enter a valid phone number, i.e: 123-456-7890.");
                    return;
                }

                UserProfile.setName(nameInput);
                UserProfile.setAboutMe(aboutMeInput);
                UserProfile.setPhoneNumber(phoneNumInput);
                UserProfile.setEmail(emailInput);
                UserProfile.setAvatarId(String.valueOf(profilePictureID));

                finish();
            }
        });
    }

    /**
     * Updates image displayed in Profile Picture to avatar referred by 'id'.
     * @param id is the avatarId of the user.
     */
    @Override
    public void updateProfilePicture(int id) {
        profilePictureID = id;
        editProfileImage.setImageResource(AvatarUtility.getAvatarImageResource(id));
    }
}