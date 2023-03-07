package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.databinding.FragmentQrcodeinformationBinding;


/**
 * The class  QR code information fragment extends fragment
 */
public class QRCodeInformationFragment extends Fragment {

    private FragmentQrcodeinformationBinding binding;

    /**
     *
     * On create view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        QRCodeInformationViewModel qrCodeInformationViewModel =
                new ViewModelProvider(this).get(QRCodeInformationViewModel.class);

        binding = FragmentQrcodeinformationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.qrCodeDescription;
        qrCodeInformationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }


    /**
     *
     * On create
     *
     * @param savedInstanceState  the saved instance state
     */
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override

    /**
     *
     * On create options menu
     *
     * @param menu  the menu
     * @param inflater  the inflater
     */
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // adds buttons to the top navigation bar for navigation and to delete the QR Code
        inflater.inflate(R.menu.qr_code_information_top_nav_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override

    /**
     *
     * On destroy view
     *
     */
    public void onDestroyView() {

        super.onDestroyView();
        binding = null;
    }
}
