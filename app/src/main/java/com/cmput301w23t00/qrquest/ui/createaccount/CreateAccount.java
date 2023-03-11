package com.cmput301w23t00.qrquest.ui.createaccount;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cmput301w23t00.qrquest.MainActivity;
import com.cmput301w23t00.qrquest.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText addNameField, addEmailField, addPhoneField;
    Button addCreateButton;
    ImageView addProfileImage;

    private static final String TAG = "CreateAccount";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Create Account");

        // gets fid from LoadingScreen
        Intent intentFromLoadingScreen = getIntent();
        String fid = Objects.requireNonNull(intentFromLoadingScreen).getStringExtra("fid");

        addNameField = findViewById(R.id.addNameField);
        addEmailField = findViewById(R.id.addEmailField);
        addPhoneField = findViewById(R.id.addPhoneField);
        addCreateButton = findViewById(R.id.addCreateButton);
        addProfileImage = findViewById(R.id.addProfileImage);

        // TODO
        addProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pop up upload option
                // store photo to send to db

                // update profile image view
                //addProfileImage.setImageResource();
            }
        });

        addCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // manipulate name, email, phone, profile image in db
                String name = addNameField.getText().toString();
                String email = addEmailField.getText().toString();
                String phoneNum = addPhoneField.getText().toString();

                // VALIDATION CHECKS
                if (TextUtils.isEmpty(name)){
                    addNameField.setError("Please enter your name.");
                    return;
                }

                if (TextUtils.isEmpty(phoneNum)){
                    addPhoneField.setError("Please enter your phone number.");
                    return;
                }

                if (TextUtils.isEmpty(email)){
                    addEmailField.setError("Please enter your email address.");
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    addEmailField.setError("Please enter a valid email address.");
                    return;
                }

/*              FOR REFERENCE:
                Matcher matcher = pattern.matcher(phoneNum);
                if(!matcher.matches()){
*/

                String patternString = "^\\d{3}-\\d{3}-\\d{4}$";
                Pattern pattern = Pattern.compile(patternString);
                if(!pattern.matcher(phoneNum).matches()){
                    addPhoneField.setError("Please enter a valid phone number, i.e: 123-456-7890.");
                    return;
                }

                Map<String, Object> userValue = new HashMap<>();
                userValue.put("name", name);
                userValue.put("email", email);
                userValue.put("phoneNumber", phoneNum);
                userValue.put("aboutMe", "");
                userValue.put("recordGeoLocationByDefault", true);
                userValue.put("identifierId", fid);

                db.collection("users")
                        .add(userValue)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });

                // Goes to home screen.
                Intent intentAccountCreated = new Intent(CreateAccount.this, MainActivity.class);
                startActivity(intentAccountCreated);

                finish();
            }
        });
    }
}
