package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.databinding.FragmentQrcodeinformationBinding;

/**
 * The class  QR code information fragment extends fragment
 *
 * The QRCodeInformationFragment class extends the Fragment class and provides a fragment that displays information about a QR code.
 */
public class QRCodeInformationFragment extends Fragment {

    private FragmentQrcodeinformationBinding binding;

    /**
     *
     * onCreateView is called when the view is first created.
     * It inflates the view and sets up the QRCodeInformationViewModel to display the QR code information.
     *
     * @param inflater the LayoutInflater object that can be used to inflate any views in the fragment
     * @param container the parent view that the fragment's UI should be attached to
     * @param savedInstanceState the previously saved instance state
     * @return the view for the fragment's UI
     */
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Create a QRCodeInformationViewModel to display the information about the QR code.
        QRCodeInformationViewModel qrCodeInformationViewModel =
                new ViewModelProvider(this).get(QRCodeInformationViewModel.class);

        // Inflate the fragment_qrcodeinformation.xml layout for this fragment.
        binding = FragmentQrcodeinformationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // uncomment this after library is complete
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            LibraryQRCode qrCode = bundle.getParcelable("selectedQRCode");
//            if (qrCode != null) {
//                qrCodeInformationViewModel.setQRCodeInfo(qrCode.getData(), "test description");
//            }
//        }
//
//        binding.setViewModel(qrCodeInformationViewModel);

        return root;
    }

    /**
     *
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
     *
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.qr_comments) {
//            // Start a new activity to see comments on this QR code
//            Intent intent1 = new Intent(this,MyActivity.class);
//            this.startActivity(intent1);
//            return true;
//        }
//
//        if (id == R.id.qr_visual_representation) {
//            // Start a new activity to see a text visual representation of the QR code
//            Intent intent1 = new Intent(this,MyActivity.class);
//            this.startActivity(intent1);
//            return true;
//        }
//
//        if (id == R.id.qr_same_code) {
//            // Start a new activity to see other players with the same QR code
//            Intent intent1 = new Intent(this,MyActivity.class);
//            this.startActivity(intent1);
//            return true;
//        }

        // Back arrow
        if (item.getItemId() == android.R.id.home) {
            // Handle the back arrow click here
            // Navigate back to the previous fragment
            NavHostFragment.findNavController(this).popBackStack();
            return true;
        }

        if (id == R.id.qr_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyAlertDialogTheme);
            builder.setCancelable(true);
            builder.setTitle("Are you sure you want to delete this QR Code?");
            builder.setPositiveButton("Confirm",
                    (dialog, which) -> {
                        // Add code to delete the QR code here
                    });
            builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                // Code to handle the cancel button here
                dialog.dismiss();
            });

            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * onDestroyView is called when the view is destroyed.
     * It cleans up any references to the binding to prevent memory leaks.
     */
    @Override
    public void onDestroyView() {

        super.onDestroyView();
        binding = null;
    }
}
