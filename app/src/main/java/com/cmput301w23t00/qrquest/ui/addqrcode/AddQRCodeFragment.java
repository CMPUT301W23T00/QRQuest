package com.cmput301w23t00.qrquest.ui.addqrcode;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.addqrcode.qrnameactivity.QrNameActivity;
import com.cmput301w23t00.qrquest.ui.profile.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.Result;

public class AddQRCodeFragment extends Fragment {
    private CodeScanner mCodeScanner;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState the saved state of the activity
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final Activity activity = getActivity();
        View root = inflater.inflate(R.layout.fragment_addqrcode, container, false);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.CAMERA }, 100);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
            mCodeScanner = new CodeScanner(activity, scannerView);
            mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                /**
                 * Creates Intent On run
                 *
                 * param: Result a result object
                 */
                public void onDecoded(@NonNull final Result result) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        /**
                         * Creates Intent On run
                         *
                         */
                        public void run() {
                            String qrCodeDataString = QRCodeProcessor.sha256(result.getText());
                            FirebaseFirestore.getInstance().collection("usersQRCodes").whereEqualTo("identifierId", UserProfile.getUserId()).whereEqualTo("qrCodeData", qrCodeDataString).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.getResult().size() == 0) {
                                        Intent intentNoUID = new Intent(getActivity(), QrNameActivity.class);
                                        // sends fid to CreateAccount
                                        intentNoUID.putExtra("qrCodeData", qrCodeDataString);
                                        startActivity(intentNoUID);
                                    } else {
                                        Toast.makeText(getActivity(), "Can't scan a QR Code you already scanned, click screen to try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
            });
            scannerView.setOnClickListener(new View.OnClickListener() {
                /**
                 * On click for a view
                 *
                 * @param view a view
                 */
                @Override
                public void onClick(View view) {
                    mCodeScanner.startPreview();
                }
            });
        }

        return root;
    }

    @Override
    /**
     * On resuming the preview
     */
    public void onResume() {
        super.onResume();
        if (mCodeScanner != null) {
            mCodeScanner.startPreview();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    /**
     * On pause the preview
     */
    @Override
    public void onPause() {
        if (mCodeScanner != null) {
            mCodeScanner.releaseResources();
        }
        super.onPause();
    }
}