package com.cmput301w23t00.qrquest.ui.library.qrCodeSummaryStatistics;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavHostFragment.findNavController(this).navigate(R.id.action_qrCodeSummaryStatisticsFragment2_to_navigation_qrcode_library2);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}