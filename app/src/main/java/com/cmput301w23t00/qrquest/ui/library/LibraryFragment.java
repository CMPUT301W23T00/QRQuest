package com.cmput301w23t00.qrquest.ui.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cmput301w23t00.qrquest.databinding.FragmentLibraryBinding;

public class LibraryFragment extends Fragment {

    private FragmentLibraryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LibraryViewModel libraryViewModel =
                new ViewModelProvider(this).get(LibraryViewModel.class);

        binding = FragmentLibraryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textLibrary;
        //libraryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}