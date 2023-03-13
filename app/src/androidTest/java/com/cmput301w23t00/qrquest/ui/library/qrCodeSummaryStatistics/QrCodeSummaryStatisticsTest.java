package com.cmput301w23t00.qrquest.ui.library.qrCodeSummaryStatistics;

import static android.app.PendingIntent.getActivity;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import com.cmput301w23t00.qrquest.R;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.Navigation;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cmput301w23t00.qrquest.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class QrCodeSummaryStatisticsTest {
    private FragmentScenario<QrCodeSummaryStatisticsFragment> fragmentScenario;

    @Test
    public void testStatisticsDisplay() {
        // create bundle
        Bundle bundle = new Bundle();
        bundle.putLong("highestScore", 0);
        bundle.putLong("lowestScore", 0);
        bundle.putLong("sumOfScores", 0);
        bundle.putLong("totalScanned", 0);
        // navigate
        fragmentScenario = FragmentScenario.launchInContainer(QrCodeSummaryStatisticsFragment.class, bundle);
        // verify
        onView(withId(R.id.fragment_qr_code_summary_statistics_parent)).check(matches(isDisplayed()));
        onView(withId(R.id.summary_highest_score)).check(matches(withText("0")));
        onView(withId(R.id.summary_lowest_score)).check(matches(withText("0")));
        onView(withId(R.id.summary_sum_of_scores)).check(matches(withText("0")));
        onView(withId(R.id.summary_total_scanned)).check(matches(withText("0")));
    }
}
