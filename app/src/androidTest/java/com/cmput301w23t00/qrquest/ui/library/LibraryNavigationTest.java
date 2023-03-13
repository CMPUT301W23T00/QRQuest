package com.cmput301w23t00.qrquest.ui.library;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import com.cmput301w23t00.qrquest.R;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cmput301w23t00.qrquest.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for navigation of LibraryFragment
 */
@RunWith(AndroidJUnit4.class)
public class LibraryNavigationTest {
    /**
     * Sets up tests
     * @throws Exception if activity cannot be started
     */
    @Before
    public void setUp() throws Exception {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
    }

    /**
     * Tests that navigation to QrCodeSummaryStatisticsFragment works correctly
     */
    @Test
    public void testLibraryFragmentsNavigation() {
        // Navigate to LibraryFragment
        onView(withId(R.id.navigation_qrcode_library)).perform(click());
        // Verify navigation to LibraryFragment occurred
        onView(withId(R.id.fragment_library_parent)).check(matches(isDisplayed()));
        // Navigate to QrCodeSummaryStatisticsFragment
        onView(withId(R.id.view_personal_qr_stats_button)).perform(click());
        // Verify navigation to QrCodeSummaryStatisticsFragment occurred
        onView(withId(R.id.fragment_qr_code_summary_statistics_parent)).check(matches(isDisplayed()));

    }
}
