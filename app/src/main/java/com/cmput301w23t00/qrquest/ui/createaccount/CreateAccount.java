package com.cmput301w23t00.qrquest.ui.createaccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;

import com.cmput301w23t00.qrquest.MainActivity;
import com.cmput301w23t00.qrquest.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {

    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference users_dbRef = dbRef.child("users");
    String userId = users_dbRef.push().getKey();

    EditText addNameField, addEmailField, addPhoneField;
    Button addCreateButton;
    ImageView addProfileImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        addNameField = findViewById(R.id.addNameField);
        addEmailField = findViewById(R.id.addEmailField);
        addPhoneField = findViewById(R.id.addPhoneField);
        addCreateButton = findViewById(R.id.addCreateButton);
        addProfileImage = findViewById(R.id.addProfileImage);

        addProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pop up upload option
                // send photo to db
                // update profile image view
            }
        });

        addCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // manipulate name, email, phone, profile image in db
                String name = addNameField.getText().toString();
                String email = addEmailField.getText().toString();
                String phoneNum = addPhoneField.getText().toString();

                Map<String, Object> userValue = new HashMap<>();
                userValue.put("name", name);
                userValue.put("email", email);
                userValue.put("phoneNumber", phoneNum);
                userValue.put("aboutMe", "");

                users_dbRef.child(userId).setValue(userValue);

                // Goes back to home screen.
                Intent intentAccountCreated = new Intent(CreateAccount.this, MainActivity.class);
                startActivity(intentAccountCreated);

                finish();
            }
        });
    }
}
