package com.cmput301w23t00.qrquest.ui.library.qrCodeSummaryStatistics;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.cmput301w23t00.qrquest.databinding.FragmentQrCodeSummaryStatisticsBinding;

public class QrCodeSummaryStatisticsFragment extends Fragment {

    private QrCodeSummaryStatisticsViewModel mViewModel;
    private FragmentQrCodeSummaryStatisticsBinding binding;
    public static QrCodeSummaryStatisticsFragment newInstance() {
        return new QrCodeSummaryStatisticsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentQrCodeSummaryStatisticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(QrCodeSummaryStatisticsViewModel.class);
        // TODO: Use the ViewModel
    }

}