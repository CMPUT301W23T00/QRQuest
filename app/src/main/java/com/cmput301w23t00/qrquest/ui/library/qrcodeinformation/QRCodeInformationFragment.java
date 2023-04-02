package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.databinding.FragmentQrcodeinformationBinding;
import com.cmput301w23t00.qrquest.ui.addqrcode.QRCodeProcessor;
import com.cmput301w23t00.qrquest.ui.library.LibraryQRCode;
import com.cmput301w23t00.qrquest.ui.map.leaderboard.leaderboardUser;
import com.cmput301w23t00.qrquest.ui.profile.UserProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * The class  QR code information fragment extends fragment
 *
 * The QRCodeInformationFragment class extends the Fragment class and provides a fragment that displays information about a QR code.
 */
public class QRCodeInformationFragment extends Fragment {

    private FragmentQrcodeinformationBinding binding; // view binding object for the fragment
    String userID; // a string to hold the current user's ID
    String docID; // the qr code document id
    Boolean isMap; // determines which page to return to
    Boolean isLeaderboard;
    FirebaseFirestore db; // Firestore database instance
    LibraryQRCode libraryQRCode;
    leaderboardUser _leaderboardUser;

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
        if (libraryQRCode != null) {
            //Log.d("Test","Random Chars" + libraryQRCode.toString());
        }
        // Create a QRCodeInformationViewModel to display the information about the QR code.
        QRCodeInformationViewModel qrCodeInformationViewModel =
                new ViewModelProvider(this).get(QRCodeInformationViewModel.class);

        // Inflate the fragment_qrcodeinformation.xml layout for this fragment.
        binding = FragmentQrcodeinformationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Retrieve data passed in from the previous fragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            LibraryQRCode qrCode = bundle.getParcelable("selectedQRCode");
            userID = bundle.getString("userID");
            docID = bundle.getString("documentID");
            isMap = bundle.getBoolean("isMap");
            if (qrCode != null) {
                // Update the ViewModel with the information of the selected QR code
                libraryQRCode = qrCode;
                QRCodeProcessor qrCodeProcessor = new QRCodeProcessor(qrCode.getData());
                qrCodeInformationViewModel.setQRCodeInfo(qrCodeProcessor.getName(), "test description");
                Bitmap Image = qrCodeProcessor.getBitmap(getActivity());
                ImageView TempImage = root.findViewById(R.id.qr_code_image);
                TempImage.setImageBitmap(Image);
            }
        }

        binding.setViewModel(qrCodeInformationViewModel);

        return root;
    }


    /**
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isMap", isMap);
        outState.putString("userID", userID);
        outState.putString("docID", docID);
        outState.putParcelable("libraryQRCode", libraryQRCode);
    } **/

    /**
     * onCreate is called to do initial creation of the fragment.
     *
     * @param savedInstanceState the previously saved instance state
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Creates this fragment's menu.
        setHasOptionsMenu(true);

        /**
        if (savedInstanceState != null) {
            isMap = savedInstanceState.getBoolean("isMap");
            userID = savedInstanceState.getString("userID");
            docID = savedInstanceState.getString("docID");
            libraryQRCode = savedInstanceState.getParcelable("libraryQRCode");
        } **/
    }

    /**
     * onCreateOptionsMenu initializes the contents of the Activity's standard options menu.
     * @param menu the options menu in which you place your items
     * @param inflater the MenuInflater object that can be used to inflate any views in the menu
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {

        // adds buttons to the top navigation bar for navigation and to delete the QR Code
        inflater.inflate(R.menu.qr_code_information_top_nav_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
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

        if (id == R.id.qr_comments) {
            // Create a bundle to store data that will be passed to the QR code information fragment
            Bundle bundle = new Bundle();
            // Add the selected QR code object and the user ID to the bundle
            bundle.putParcelable("selectedQRCode", libraryQRCode);
            bundle.putString("userID", userID);
            bundle.putString("documentID", docID);
            bundle.putBoolean("isMap", isMap);

            // Start a new activity to see comments on this QR code
            NavHostFragment.findNavController(QRCodeInformationFragment.this).navigate(R.id.qrCodeInformationFragment_to_action_commentFragment, bundle);
            return true;
        }
//
//        if (id == R.id.qr_same_code) {
//            // Start a new activity to see other players with the same QR code
//            Intent intent1 = new Intent(this,MyActivity.class);
//            this.startActivity(intent1);
//            return true;
//        }

        // Back arrow
        if (id == android.R.id.home) {
            // Navigate back to the previous fragment
            if (isMap) {
                NavHostFragment.findNavController(QRCodeInformationFragment.this).navigate(R.id.qrCodeInformationFragment_to_action_mapFragment);
            } if (isLeaderboard) {
                NavHostFragment.findNavController(QRCodeInformationFragment.this).navigate(R.id.qrCodeInformationFragment_to_action_leaderboard);
            } else {
                NavHostFragment.findNavController(QRCodeInformationFragment.this).navigate(R.id.qrCodeInformationFragment_to_action_libraryFragment);
            }

            return true;
        }

        // delete qr code button
        if (id == R.id.qr_delete) {
            deleteQRCode();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Deletes a QR Code
     * @return
     */
    public boolean deleteQRCode() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyAlertDialogTheme);
        builder.setCancelable(true);
        builder.setTitle("Are you sure you want to delete this QR Code?");
        builder.setPositiveButton("Confirm",
                (dialog, which) -> {
                    if (userID == UserProfile.getUserId()) {
                        // Add code to delete the QR code here
                        db = FirebaseFirestore.getInstance();
                        final CollectionReference usersQRCodesCollectionReference = db.collection("usersQRCodes");
                        // Get the document reference
                        DocumentReference qr_code = usersQRCodesCollectionReference.document(docID);

                        // Delete the document
                        qr_code.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if (isMap) {
                                    NavHostFragment.findNavController(QRCodeInformationFragment.this).navigate(R.id.qrCodeInformationFragment_to_action_mapFragment);
                                } else {
                                    NavHostFragment.findNavController(QRCodeInformationFragment.this).navigate(R.id.qrCodeInformationFragment_to_action_libraryFragment);
                                }
                                dialog.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("ERROR!", "Error deleting QR Code", e);
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(),"Can't delete QR Code since it isn't yours",Toast.LENGTH_SHORT).show();
                    }
                });

        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
            // Code to handle the cancel button here
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        return true;
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isMap) {
                    NavHostFragment.findNavController(QRCodeInformationFragment.this).navigate(R.id.qrCodeInformationFragment_to_action_mapFragment);
                } else {
                    NavHostFragment.findNavController(QRCodeInformationFragment.this).navigate(R.id.qrCodeInformationFragment_to_action_libraryFragment);
                }
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }
}
