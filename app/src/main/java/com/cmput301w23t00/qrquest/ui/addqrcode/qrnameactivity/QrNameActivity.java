package com.cmput301w23t00.qrquest.ui.addqrcode.qrnameactivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cmput301w23t00.qrquest.MainActivity;
import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.addqrcode.QRCodeProcessor;
import com.cmput301w23t00.qrquest.ui.addqrcode.qrnameactivity.takephotoactivity.TakePhotoActivity;
import com.cmput301w23t00.qrquest.ui.createaccount.CreateAccount;
import com.cmput301w23t00.qrquest.ui.profile.UserProfile;
import com.cmput301w23t00.qrquest.ui.profile.UserSettings;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.time.LocalDate;
import java.util.UUID;


import static android.Manifest.permission.ACCESS_FINE_LOCATION;


/**
 * This is the main activity for adding a name, image and location to a QR code. The user can take a photo
 * with the device's camera. The image info
 * details are saved to Firebase Firestore.
 */
public class QrNameActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Uri picturesUri = Uri.EMPTY;
    public static final String REQUEST_RESULT = "REQUEST_RESULT";
    private UserSettings userSettings = new UserSettings();


    String qrCodeData;

    /**
     * Called when the activity is starting. Inflates the layout, initializes UI components,
     * hides the action bar, and retrieves the QR code data from the intent that started the activity.
     *
     * @param savedInstanceState the saved state of the activity
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takephoto);

        getSupportActionBar().hide();

        Intent qrCodeIntent = getIntent();
        this.qrCodeData = QRCodeProcessor.sha256(Objects.requireNonNull(qrCodeIntent).getStringExtra("qrCodeData"));

        ImageView imgview = findViewById(R.id.main_backgroundImage);
        Button canButton = findViewById(R.id.take_photo_cancel_button);
        Button conButton = findViewById(R.id.take_photo_confirm_button);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch leaveLocationSwitch = findViewById(R.id.leaveLocationSwitch);
        leaveLocationSwitch.setChecked(userSettings.getGeoLocation());
        FusedLocationProviderClient client;
        client = LocationServices.getFusedLocationProviderClient(this);
        String locationCord = "";

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView pointsGeneratedTextView = findViewById(R.id.pointsGeneratedTextView);
        int pointsGenerated = new QRCodeProcessor(this.qrCodeData).getScore();
        pointsGeneratedTextView.setText("Points Generated: " + String.valueOf(pointsGenerated));

        // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    /**
                     * Gets picture from the Uri
                     *
                     * @param result an ActivityResult
                     */
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            assert data != null;
                            Uri uri = Uri.parse(data.getStringExtra("ImageDataUri"));
                            picturesUri = uri;
                            File f = new File(getRealPathFromURI(picturesUri));
                            Drawable d = Drawable.createFromPath(f.getAbsolutePath());
                            imgview.setBackground(d);
                        }
                    }
                });

        imgview.setOnClickListener(new View.OnClickListener() {
            /**
             * On click for a view
             *
             * @param view a view
             */
            @Override
            public void onClick(View view) {
                // call new fragment to take picture
                Intent intentNoUID = new Intent(QrNameActivity.this, TakePhotoActivity.class);
                someActivityResultLauncher.launch(intentNoUID);
            }
        });
        canButton.setOnClickListener(new View.OnClickListener() {
            /**
             * On click for a view
             *
             * @param view a view
             */
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        conButton.setOnClickListener(new View.OnClickListener() {
            /**
             * On click for a view
             *
             * @param view a view
             */
            @Override
            public void onClick(View view) {
                // manipulate name, email, phone, profile image in db
                EditText editComment = findViewById(R.id.editComment);
                @SuppressLint("UseSwitchCompatOrMaterialCode")
                Switch leaveLocationSwitch = findViewById(R.id.leaveLocationSwitch);
                String comment = editComment.getText().toString();
                Boolean leaveLocation = leaveLocationSwitch.isChecked();

                GeoPoint point = new GeoPoint(0, 0);
                if (leaveLocation) {
                    if (ContextCompat.checkSelfPermission(QrNameActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, 100);
                    }

                    if (ContextCompat.checkSelfPermission(QrNameActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Criteria criteria = new Criteria();
                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        String bestProvider = locationManager.getBestProvider(criteria, true);
                        if (bestProvider == null) {
                            Toast.makeText(QrNameActivity.this, "Location is disabled can't record location", Toast.LENGTH_SHORT).show();
                        } else {
                            Location location = locationManager.getLastKnownLocation(bestProvider);
                            point = new GeoPoint(location.getLatitude(), location.getLongitude());
                        }
                    } else {
                        Toast.makeText(QrNameActivity.this, "Don't have location permissions can't record location", Toast.LENGTH_SHORT).show();
                    }
                }


                String fid = UserProfile.getUserId();

                @SuppressLint("SimpleDateFormat")
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();


                Map<String, Object> usersQRCodesValue = new HashMap<>();
                usersQRCodesValue.put("comment", comment);
                usersQRCodesValue.put("dateScanned",date);
                usersQRCodesValue.put("location", point);
                usersQRCodesValue.put("qrCodeData", qrCodeData);
                usersQRCodesValue.put("identifierId", fid);

                db.collection("usersQRCodes")
                        .add(usersQRCodesValue)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            /**
                             * On success of a Firebase upload
                             *
                             * @param documentReference a DocumentReference
                             */
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            /**
                             * On failure of a Firebase upload
                             *
                             * @param e an Exception
                             */
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error adding document", e);
                            }
                        });

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference().child("images/" + fid+"-"+qrCodeData);

                storageRef.putFile(picturesUri)
                        .addOnFailureListener(new OnFailureListener() {
                            /**
                             * On failure of a Firebase upload
                             *
                             * @param e an Exception
                             */
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle the error
                                Log.e("TAG", "Error uploading image to Firebase Storage", e);
                            }
                        });
                finish();
            }
        });
    }

    /**
     * get path from Uri
     *
     * @param contentURI a Uri
     * @return String
     */
    private String getRealPathFromURI(Uri contentURI) {
        @SuppressLint("Recycle") Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
}
