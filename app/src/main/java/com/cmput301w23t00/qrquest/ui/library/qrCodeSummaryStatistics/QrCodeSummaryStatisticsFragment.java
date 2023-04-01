package com.cmput301w23t00.qrquest.ui.library.qrCodeSummaryStatistics;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.databinding.FragmentQrCodeSummaryStatisticsBinding;


import java.util.Locale;

/**
 * The QrCodeSummaryStatisticsFragment class extends the Fragment class and provides a
 * fragment that displays all of the summary statistics about the users QR codes.
 */
public class QrCodeSummaryStatisticsFragment extends Fragment {

    private FragmentQrCodeSummaryStatisticsBinding binding; // View binding for the library fragment

    /**
     * Creates a new instance of the QrCodeSummaryStatistics fragment
     * @return QrCodeSummaryStatisticsFragment
     */
    public static QrCodeSummaryStatisticsFragment newInstance() {
        return new QrCodeSummaryStatisticsFragment();
    }
    /**
     * onCreateView inflates the view, showing the summary statistics of a users QR codes
     * @param inflater the LayoutInflater object that can be used to inflate any views in the fragment
     * @param container the parent view that the fragment's UI should be attached to
     * @param savedInstanceState the previously saved instance state
     * @return the view for the fragment's UI
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentQrCodeSummaryStatisticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get textviews for summary statistics
        TextView highestScoreText = binding.summaryHighestScore;
        TextView lowestScoreText = binding.summaryLowestScore;
        TextView sumOfScoresText = binding.summarySumOfScores;
        TextView totalScannedText = binding.summaryTotalScanned;
        TextView highestUniqueRankText = binding.summaryHighestScoreRank;
        // Get summary statistics from bundle
        long highestScore = getArguments().getLong("highestScore");
        long lowestScore = getArguments().getLong("lowestScore");
        long sumOfScores = getArguments().getLong("sumOfScores");
        long totalScanned = getArguments().getLong("totalScanned");
        long highestUniqueRank = getArguments().getLong("highestUniqueRank");
        // Set textview texts with summary statistics from library fragment bundle
        highestScoreText.setText(String.format(Locale.CANADA,"%d", highestScore));
        lowestScoreText.setText(String.format(Locale.CANADA,"%d", lowestScore));
        sumOfScoresText.setText(String.format(Locale.CANADA,"%d", sumOfScores));
        totalScannedText.setText(String.format(Locale.CANADA,"%d", totalScanned));
        highestUniqueRankText.setText(String.format(Locale.CANADA,"%d", highestUniqueRank));
        return root;
    }

    /**
     * onCreate is called to do initial creation of the fragment.
     * @param savedInstanceState the previously saved instance state
     */
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Creates this fragment's menu.
        setHasOptionsMenu(true);
    }
    /**
     * onOptionsItemSelected is called when the user clicks on the Menu
     * @param item the clicked item
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) { // Navigate to library fragment if back button is pressed
            NavHostFragment.findNavController(this).navigate(R.id.action_qrCodeSummaryStatisticsFragment2_to_navigation_qrcode_library2);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}