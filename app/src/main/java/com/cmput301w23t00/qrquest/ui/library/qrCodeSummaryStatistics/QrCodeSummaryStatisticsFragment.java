package com.cmput301w23t00.qrquest.ui.library.qrCodeSummaryStatistics;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.databinding.FragmentQrCodeSummaryStatisticsBinding;


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
        Log.d("loaded2", "111");
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            findNavController(this).navigate(R.id.action_qrCodeSummaryStatisticsFragment2_to_navigation_qrcode_library2);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}