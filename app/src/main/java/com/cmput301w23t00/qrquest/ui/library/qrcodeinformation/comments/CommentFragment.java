package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.comments;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.databinding.FragmentQrcodeinformationBinding;
import com.cmput301w23t00.qrquest.ui.addqrcode.QRCodeProcessor;
import com.cmput301w23t00.qrquest.ui.library.LibraryQRCode;
import com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.QRCodeInformationViewModel;
import com.cmput301w23t00.qrquest.ui.profile.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * The class  QR code information fragment extends fragment
 *
 * The QRCodeInformationFragment class extends the Fragment class and provides a fragment that displays information about a QR code.
 */
public class CommentFragment extends Fragment {

    private FragmentQrcodeinformationBinding binding; // view binding object for the fragment
    String userID; // a string to hold the current user's ID
    String docID; // the qr code document id
    Boolean isMap; // determines which page to return to
    FirebaseFirestore db; // Firestore database instance
    LibraryQRCode libraryQRCode;

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

        // Create a QRCodeInformationViewModel to display the information about the QR code.
        QRCodeInformationViewModel qrCodeInformationViewModel =
                new ViewModelProvider(this).get(QRCodeInformationViewModel.class);

        // Inflate the fragment_qrcodeinformation.xml layout for this fragment.
        binding = FragmentQrcodeinformationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.setViewModel(qrCodeInformationViewModel);

        FirebaseFirestore.getInstance().collection("usersQRCodes").whereEqualTo("identifierId", "1234");.get().addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                FirebaseFirestore.getInstance().collection("users").getDocuments().addOnCompleteListener(task2 -> {
                    if (task2.isSuccessful()) {

                    }
                });
            };
        });

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

        // Back arrow
        if (item.getItemId() == android.R.id.home) {
            // Navigate back to the previous fragment
            NavHostFragment.findNavController(CommentFragment.this).navigate(R.id.commentFragment_to_action_qrCodeInformationFragment);
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
