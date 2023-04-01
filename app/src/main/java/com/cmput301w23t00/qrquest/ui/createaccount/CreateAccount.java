package com.cmput301w23t00.qrquest.ui.createaccount;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cmput301w23t00.qrquest.MainActivity;
import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.profile.UserProfile;
import com.cmput301w23t00.qrquest.ui.profile.UserSettings;
import com.cmput301w23t00.qrquest.ui.updateavatar.AvatarSetter;
import com.cmput301w23t00.qrquest.ui.updateavatar.UpdateAvatar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText addNameField, addEmailField, addPhoneField;
    Button addCreateButton;
    ImageView addProfileImage;

    private static final String TAG = "CreateAccount";

    private ActivityResultLauncher<Intent> updateAvatarLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    String selectedAvatarId = data.getStringExtra("selectedAvatarId");
                    //AvatarSetter.setImageResource(selectedAvatarId,addProfileImage);
                }
            });


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
                /* AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccount.this);
                builder.setPositiveButton("Choose Avatar", (dialogInterface, i) -> {
                    Intent intentForUpdateAvatar = new Intent(CreateAccount.this, UpdateAvatar.class);
                    startActivity(intentForUpdateAvatar);
                });

                builder.setNegativeButton("Remove Avatar", (dialogInterface, i) -> addProfileImage.setImageResource(R.drawable.avatar2));

                AlertDialog dialog = builder.create();
                dialog.show(); */

                Intent intentForUpdateAvatar = new Intent(CreateAccount.this, UpdateAvatar.class);
                updateAvatarLauncher.launch(intentForUpdateAvatar);

            }
        });

        addCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                String patternString = "^\\d{3}-\\d{3}-\\d{4}$";
                Pattern pattern = Pattern.compile(patternString);
                if(!pattern.matcher(phoneNum).matches()){
                    addPhoneField.setError("Please enter a valid phone number, i.e: 123-456-7890.");
                    return;
                }

                // transfer to db.
                Map<String, Object> userValue = new HashMap<>();
                userValue.put("name", name);
                userValue.put("email", email);
                userValue.put("phoneNumber", phoneNum);
                userValue.put("aboutMe", "");
                userValue.put("recordGeoLocationByDefault", true);
                userValue.put("identifierId", fid);

                UserProfile userProfile = new UserProfile();
                userProfile.setAboutMe("");
                userProfile.setPhoneNumber(phoneNum);
                userProfile.setEmail(email);
                userProfile.setName(name);

                db.collection("users")
                        .add(userValue)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                UserSettings.setCreated(true);
                                UserProfile.setCreated(true);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });

                // Goes to home screen.
                Intent intentWithUID = new Intent(CreateAccount.this, MainActivity.class);
                startActivity(intentWithUID);

                finish();
            }
        });
    }
}
