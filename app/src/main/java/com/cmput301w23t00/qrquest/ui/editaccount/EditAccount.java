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

import java.util.Objects;
import java.util.regex.Pattern;

public class EditAccount extends AppCompatActivity {
    EditText editNameField, editAboutMeField, editEmailField, editPhoneField;
    Button editCancelButton, editConfirmButton;
    ImageView editProfileImage;

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

        // Sets user value in input field.
        editNameField.setText(name);
        editAboutMeField.setText(aboutMe);
        editEmailField.setText(email);
        editPhoneField.setText(phoneNum);

        editProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pop up upload option
                // store photo to send to db

                // update profile image view
                //editProfileImage.setImageResource();
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
                if(!pattern.matcher(phoneNum).matches()){
                    editPhoneField.setError("Please enter a valid phone number, i.e: 123-456-7890.");
                    return;
                }

                UserProfile.setName(nameInput);
                UserProfile.setAboutMe(aboutMeInput);
                UserProfile.setPhoneNumber(phoneNumInput);
                UserProfile.setEmail(emailInput);

                finish();
            }
        });
    }
}