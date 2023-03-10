package com.cmput301w23t00.qrquest.ui.library.qrCodeSummaryStatistics;

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

import com.cmput301w23t00.qrquest.MainActivity;
import com.cmput301w23t00.qrquest.R;

public class QrCodeSummaryStatisticsFragment extends Fragment {

    private QrCodeSummaryStatisticsViewModel mViewModel;

    public static QrCodeSummaryStatisticsFragment newInstance() {
        return new QrCodeSummaryStatisticsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qr_code_summary_statistics, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(QrCodeSummaryStatisticsViewModel.class);
        // TODO: Use the ViewModel
    }

}