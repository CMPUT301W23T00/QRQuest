package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.pictures;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.databinding.FragmentCommentsBinding;
import com.cmput301w23t00.qrquest.databinding.FragmentPicturesBinding;
import com.cmput301w23t00.qrquest.ui.library.LibraryQRCode;
import com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.comments.CommentFragment;
import com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.pictures.PictureAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * The class  QR code information fragment extends fragment
 *
 * The QRCodeInformationFragment class extends the Fragment class and provides a fragment that displays information about a QR code.
 */
public class PictureFragment extends Fragment {

    private @NonNull FragmentPicturesBinding binding; // view binding object for the fragment
    Bundle qrCodeInformationBundle;
    private static final String TAG = "PictureFragment";


    /**
     * onCreateView is called when the view is first created.
     * It inflates the view and sets up the QRCodeInformationViewModel to display the QR code information.
     *
     * @param inflater the LayoutInflater object that can be used to inflate any views in the fragment
     * @param container the parent view that the fragment's UI should be attached to
     * @param savedInstanceState the previously saved instance state
     * @return the view for the fragment's UI
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        qrCodeInformationBundle = getArguments();
        LibraryQRCode qrCodeData = qrCodeInformationBundle.getParcelable("selectedQRCode");
        String userID = qrCodeInformationBundle.getString("userID");
        System.out.println("Ran Picture Fragment");

        // Inflate the fragment_qrcodeinformation.xml layout for this fragment.

        binding = FragmentPicturesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.pictures.PictureData> list = new ArrayList<com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.pictures.PictureData>();
        PictureAdapter NewAdapter = new PictureAdapter(getContext(),list);
        binding.picturesList.setAdapter(NewAdapter);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef = storage.getReference().child("images");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usersQRCodes").whereEqualTo("qrCodeData", qrCodeData.getData()).get().addOnCompleteListener(new OnCompleteListener<>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult().getDocuments().size() != 0) {
                    for (QueryDocumentSnapshot doc1 : task.getResult()) {
                            String ID = doc1.getString("identifierId");
                            db.collection("users").whereEqualTo("identifierId", ID).get().addOnCompleteListener(task2 -> {
                                int Profile = 0;
                                if (task2.isSuccessful() && task2.getResult().getDocuments().size() != 0) {
                                    String iconId = (task2.getResult().getDocuments().get(0).getString("avatarId") == null) ? "0" : task2.getResult().getDocuments().get(0).getString("avatarId");
                                    iconId = (iconId.equals("")) ? "0" : iconId;
                                    Profile = Integer.parseInt(iconId);

                                    int finalProfile = Profile;
                                    imageRef.child(ID + '-' + qrCodeData.getData()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Date DateCreated = qrCodeData.getDate();

                                            final PictureData NewPicture = new PictureData(task2.getResult().getDocuments().get(0).getString("name"), DateCreated, finalProfile, uri);
                                            NewAdapter.add(NewPicture);
                                            NewAdapter.notifyDataSetChanged();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            Log.e(TAG, "onFailure: Feed image failed to load", exception);
                                        }
                                    });
                                }
                            });

                    }
                }
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(PictureFragment.this).navigate(R.id.pictureFragment_to_action_qrCodeInformationFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        return root;
    }

    /**
     * onCreate is called to do initial creation of the fragment.
     *
     * @param savedInstanceState the previously saved instance state
     */
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Creates this fragment's menu.
        setHasOptionsMenu(true);

        
    }

    /**
     * onOptionsItemSelected is called when a menu item is selected.
     *
     * @param item The menu item that was selected
     * @return True if the menu item was handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Back arrow
        if (item.getItemId() == android.R.id.home) {
            // Navigate back to the previous fragment
            NavHostFragment.findNavController(PictureFragment.this).navigate(R.id.pictureFragment_to_action_qrCodeInformationFragment);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * onDestroyView is called when the view is destroyed.
     * It cleans up any references to the binding to prevent memory leaks.
     */
    @Override
    public void onDestroyView() {

        super.onDestroyView();
        binding = null;
    }

}
