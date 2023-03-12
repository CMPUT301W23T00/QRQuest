package com.cmput301w23t00.qrquest.ui.addqrcode.qrnameactivity;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.addqrcode.qrnameactivity.takephotoactivity.TakePhotoActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class QrNameActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takephoto);

        Objects.requireNonNull(getSupportActionBar()).setTitle("QR Name");

        Intent qrCodeIntent = getIntent();
        String qrCodeData = Objects.requireNonNull(qrCodeIntent).getStringExtra("qrCodeData");

        ImageView imgview =  findViewById(R.id.main_backgroundImage);

        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call new fragment to take picture
                Intent intentNoUID = new Intent(QrNameActivity.this, TakePhotoActivity.class);
                startActivity(intentNoUID);
            }
        });
    }
}
