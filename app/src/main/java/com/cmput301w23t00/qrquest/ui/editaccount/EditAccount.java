package com.cmput301w23t00.qrquest.ui.editaccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.cmput301w23t00.qrquest.R;

public class EditAccount extends AppCompatActivity {

    EditText editNameField, editAboutMeField, editEmailField, editPhoneField;
    Button editCancelButton, editConfirmButton;
    ImageView editProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_account);

        editNameField = findViewById(R.id.editNameField);
        editAboutMeField = findViewById(R.id.editAboutMeField);
        editEmailField = findViewById(R.id.editEmailField);
        editPhoneField = findViewById(R.id.editPhoneField);
        editCancelButton = findViewById(R.id.editCancelButton);
        editConfirmButton = findViewById(R.id.editConfirmButton);
        editProfileImage = findViewById(R.id.editProfileImage);

        editCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go back to settings.
                // IMPORTANT: add settings class.
                Intent intentEditCancelled = new Intent(EditAccount.this, .class);
                startActivity(intentEditCancelled);
                finish();
            }
        });

        editConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // manipulate name, email, phone, about me, profile image in db

                // Go back to settings.
                // IMPORTANT: add settings class.
                Intent intentEditConfirmed = new Intent(EditAccount.this, .class);
                startActivity(intentEditConfirmed);
                finish();
            }
        });
    }
}
