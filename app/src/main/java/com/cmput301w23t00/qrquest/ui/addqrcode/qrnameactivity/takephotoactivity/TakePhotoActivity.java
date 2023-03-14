package com.cmput301w23t00.qrquest.ui.addqrcode.qrnameactivity.takephotoactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cmput301w23t00.qrquest.R;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/**
 The TakePhotoActivity class allows the user to capture a photo using the device camera.

 It utilizes the CameraX API to set up the camera preview and capture an image when the capture button is clicked.

 The captured image is saved to the device's MediaStore and the URI of the saved image is returned to the calling activity.
 */
public class TakePhotoActivity extends AppCompatActivity implements ImageAnalysis.Analyzer, View.OnClickListener {
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    PreviewView previewView;
    private ImageCapture imageCapture;
    private Button bCapture;

    /**
     * Initializes the activity
     *
     * @param savedInstanceState The saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_preview_activity);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Photo");

        previewView = findViewById(R.id.previewView);
        bCapture = findViewById(R.id.bCapture);

        bCapture.setOnClickListener(this);

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                startCameraX(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, getExecutor());

    }

    /**
     * Returns an executor that runs on the main thread.
     *
     * @return An Executor instance.
     */
    Executor getExecutor() {
        return ContextCompat.getMainExecutor(this);
    }

    /**
     * Configures the camera preview and capture use cases using the CameraX API.
     *
     * @param cameraProvider The ProcessCameraProvider
     */
    @SuppressLint("RestrictedApi")
    private void startCameraX(ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        Preview preview = new Preview.Builder()
                .build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        // Image capture use case
        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

        // Image analysis use case
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        imageAnalysis.setAnalyzer(getExecutor(), this);

        //bind to lifecycle:
        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageCapture);
    }


    /**
     * Processes the frame and logs the timestamp.
     *
     * @param image The ImageProxy object
     */
    @Override
    public void analyze(@NonNull ImageProxy image) {
        // image processing here for the current frame
        Log.d("TAG", "analyze: got the frame at: " + image.getImageInfo().getTimestamp());
        image.close();
    }

    /**
     * On click for a view
     *
     * @param view a view
     */
    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.bCapture) {
                capturePhoto();
        }
    }

    /**
     * Captures a photo and saves it
     */
    private void capturePhoto() {
        long timestamp = System.currentTimeMillis();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timestamp);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");



        imageCapture.takePicture(
                new ImageCapture.OutputFileOptions.Builder(
                        getContentResolver(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                ).build(),
                getExecutor(),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    /**
                     * On Image save creates an intent to return the image
                     */
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("ImageDataUri", outputFileResults.getSavedUri().toString());
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    }

                    @Override
                    /**
                     * Shows an error message
                     */
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(TakePhotoActivity.this, "Error saving photo: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "Error saving photo: " + exception.getMessage());
                    }
                }
        );

    }
}

