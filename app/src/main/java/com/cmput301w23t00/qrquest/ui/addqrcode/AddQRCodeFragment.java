package com.cmput301w23t00.qrquest.ui.addqrcode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cmput301w23t00.qrquest.databinding.FragmentAddqrcodeBinding;

public class AddQRCodeFragment extends Fragment {

    private FragmentAddqrcodeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddQRCodeViewModel addQRCodeViewModel =
                new ViewModelProvider(this).get(AddQRCodeViewModel.class);

        binding = FragmentAddqrcodeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAddQRCode;
        addQRCodeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}