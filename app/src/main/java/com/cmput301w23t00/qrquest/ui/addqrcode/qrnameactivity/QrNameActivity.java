package com.cmput301w23t00.qrquest.ui.addqrcode.qrnameactivity;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.addqrcode.qrnameactivity.takephotoactivity.TakePhotoActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.util.Objects;

public class QrNameActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Uri picturesUri = Uri.EMPTY;
    public static final String REQUEST_RESULT="REQUEST_RESULT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takephoto);

        getSupportActionBar().hide();

        Intent qrCodeIntent = getIntent();
        String qrCodeData = Objects.requireNonNull(qrCodeIntent).getStringExtra("qrCodeData");

        ImageView imgview =  findViewById(R.id.main_backgroundImage);

        // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
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
                    // do some stuff
                    Log.d("bob", "cappybara: " + uri.toString());
                }
            }
        });

        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call new fragment to take picture
                Intent intentNoUID = new Intent(QrNameActivity.this, TakePhotoActivity.class);
                someActivityResultLauncher.launch(intentNoUID);
            }
        });
    }

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
