package com.cmput301w23t00.qrquest.ui.editaccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.cmput301w23t00.qrquest.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

        // ADD USER VALUES
        // FIRST GET USER VALUES
        //editNameField.setText();
        //editAboutMeField.setText();
        //editEmailField.setText();
        //editPhoneField.setText();

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
                // add profile picture check

                // Go back to settings.
                // IMPORTANT: add settings class.
                //Intent intentEditCancelled = new Intent(EditAccount.this, .class);
                //startActivity(intentEditCancelled);
                finish();
            }
        });

        editConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // manipulate name, email, phone, about me, profile image in db
                String name = editNameField.getText().toString();
                String email = editEmailField.getText().toString();
                String phoneNum = editPhoneField.getText().toString();
                String aboutMe = editAboutMeField.getText().toString();

                Map<String, Object> userValue = new HashMap<>();
                userValue.put("name", name);
                userValue.put("email", email);
                userValue.put("phoneNumber", phoneNum);
                userValue.put("aboutMe", aboutMe);


                // replace userid with a system that queries

                // Go back to settings.
                // IMPORTANT: add settings class.
                //Intent intentEditConfirmed = new Intent(EditAccount.this, .class);
                //startActivity(intentEditConfirmed);
                finish();
            }
        });
    }
}
