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
import android.widget.TextView;


import com.cmput301w23t00.qrquest.databinding.FragmentQrCodeSummaryStatisticsBinding;

import org.w3c.dom.Text;

import java.util.Locale;

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

        TextView highestScoreText = binding.summaryHighestScore;
        TextView lowestScoreText = binding.summaryLowestScore;
        TextView sumOfScoresText = binding.summarySumOfScores;
        TextView totalScannedText = binding.summaryTotalScanned;

        highestScoreText.setText(String.format(Locale.CANADA,"%d", getArguments().getLong("highestScore")));
        lowestScoreText.setText(String.format(Locale.CANADA,"%d", getArguments().getLong("lowestScore")));
        sumOfScoresText.setText(String.format(Locale.CANADA,"%d", getArguments().getLong("sumOfScores")));
        totalScannedText.setText(String.format(Locale.CANADA,"%d", getArguments().getLong("totalScanned")));
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(QrCodeSummaryStatisticsViewModel.class);
        // TODO: Use the ViewModel
    }

}