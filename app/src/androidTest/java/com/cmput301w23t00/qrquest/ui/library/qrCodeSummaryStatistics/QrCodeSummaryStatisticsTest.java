package com.cmput301w23t00.qrquest.ui.library.qrCodeSummaryStatistics;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.library.LibraryQRCode;

import android.os.Bundle;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * UI tests for QRSummaryStatisticFragment
 */
@RunWith(AndroidJUnit4.class)
public class QrCodeSummaryStatisticsTest {
    private FragmentScenario<QrCodeSummaryStatisticsFragment> fragmentScenario;

    /**
     * Tests that QRSummaryStatisticFragment displays the correct information when passed a bundle
     */
    @Test
    public void testHighestScoreNullDisplay() {
        // Create bundle to be passed to QrCodeSummaryStatisticsFragment
        Bundle bundle = new Bundle();
        bundle.putParcelable("highestQRCode", null);
        // Navigate to QrCodeSummaryStatisticsFragment
        fragmentScenario = FragmentScenario.launchInContainer(QrCodeSummaryStatisticsFragment.class, bundle);
        // Verify navigation is correct and QrCodeSummaryStatisticsFragment displays correct information
        onView(withId(R.id.fragment_qr_code_summary_statistics_parent)).check(matches(isDisplayed()));
        onView(withId(R.id.summary_highest_score)).check(matches(withText("0")));
    }
    @Test
    public void testHighestScoreDisplay() {
        // Create bundle to be passed to QrCodeSummaryStatisticsFragment
        Bundle bundle = new Bundle();
        bundle.putParcelable("highestQRCode", new LibraryQRCode("BFG5DGW54",1,null));
        // Navigate to QrCodeSummaryStatisticsFragment
        fragmentScenario = FragmentScenario.launchInContainer(QrCodeSummaryStatisticsFragment.class, bundle);
        // Verify navigation is correct and QrCodeSummaryStatisticsFragment displays correct information
        onView(withId(R.id.fragment_qr_code_summary_statistics_parent)).check(matches(isDisplayed()));
        onView(withId(R.id.summary_highest_score)).check(matches(withText("1")));
    }
    @Test
    public void testLowestScoreNullDisplay() {
        // Create bundle to be passed to QrCodeSummaryStatisticsFragment
        Bundle bundle = new Bundle();
        bundle.putParcelable("lowestQRCode", null);
        // Navigate to QrCodeSummaryStatisticsFragment
        fragmentScenario = FragmentScenario.launchInContainer(QrCodeSummaryStatisticsFragment.class, bundle);
        // Verify navigation is correct and QrCodeSummaryStatisticsFragment displays correct information
        onView(withId(R.id.fragment_qr_code_summary_statistics_parent)).check(matches(isDisplayed()));
        onView(withId(R.id.summary_lowest_score)).check(matches(withText("0")));
    }
    @Test
    public void testLowestScoreDisplay() {
        // Create bundle to be passed to QrCodeSummaryStatisticsFragment
        Bundle bundle = new Bundle();
        bundle.putParcelable("lowestQRCode", new LibraryQRCode("BFG5DGW54", 1, null));
        // Navigate to QrCodeSummaryStatisticsFragment
        fragmentScenario = FragmentScenario.launchInContainer(QrCodeSummaryStatisticsFragment.class, bundle);
        // Verify navigation is correct and QrCodeSummaryStatisticsFragment displays correct information
        onView(withId(R.id.summary_lowest_score)).check(matches(withText("1")));
    }
    @Test
    public void sumOfScoresDisplay() {
        // Create bundle to be passed to QrCodeSummaryStatisticsFragment
        Bundle bundle = new Bundle();
        bundle.putLong("sumOfScores", 0);
        // Navigate to QrCodeSummaryStatisticsFragment
        fragmentScenario = FragmentScenario.launchInContainer(QrCodeSummaryStatisticsFragment.class, bundle);
        // Verify navigation is correct and QrCodeSummaryStatisticsFragment displays correct information
        onView(withId(R.id.summary_sum_of_scores)).check(matches(withText("0")));
    }
    @Test
    public void totalScannedDisplay() {
        // Create bundle to be passed to QrCodeSummaryStatisticsFragment
        Bundle bundle = new Bundle();
        bundle.putLong("totalScanned", 0);
        // Navigate to QrCodeSummaryStatisticsFragment
        fragmentScenario = FragmentScenario.launchInContainer(QrCodeSummaryStatisticsFragment.class, bundle);
        // Verify navigation is correct and QrCodeSummaryStatisticsFragment displays correct information
        onView(withId(R.id.summary_total_scanned)).check(matches(withText("0")));
    }
    @Test
    public void highestUniqueRankDisplay() {
        // Create bundle to be passed to QrCodeSummaryStatisticsFragment
        Bundle bundle = new Bundle();
        bundle.putInt("highestUniqueRank", 0);
        // Navigate to QrCodeSummaryStatisticsFragment
        fragmentScenario = FragmentScenario.launchInContainer(QrCodeSummaryStatisticsFragment.class, bundle);
        // Verify navigation is correct and QrCodeSummaryStatisticsFragment displays correct information
        onView(withId(R.id.summary_highest_score_rank)).check(matches(withText("0")));
    }
}
