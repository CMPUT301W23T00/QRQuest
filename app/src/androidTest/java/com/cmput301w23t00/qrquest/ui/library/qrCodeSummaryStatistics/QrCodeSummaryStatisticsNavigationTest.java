package com.cmput301w23t00.qrquest.ui.library.qrCodeSummaryStatistics;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import com.cmput301w23t00.qrquest.R;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cmput301w23t00.qrquest.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class QrCodeSummaryStatisticsNavigationTest {
    @Before
    public void setUp() throws Exception {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
    }
    @Test
    public void testBackNavigation() {
        // nav libraryFragment
        onView(withId(R.id.navigation_qrcode_library)).perform(click());
        // verify
        onView(withId(R.id.fragment_library_parent)).check(matches(isDisplayed()));
        // nav qrCodeSummaryStatisticsFragment
        onView(withId(R.id.view_personal_qr_stats_button)).perform(click());
        // verify
        onView(withId(R.id.fragment_qr_code_summary_statistics_parent)).check(matches(isDisplayed()));
        // back
        onView(withContentDescription("Navigate up")).perform(click());
        //verify
        onView(withId(R.id.fragment_library_parent)).check(matches(isDisplayed()));
    }

    @Test
    public void testHome() {
        // nav libraryFragment
        onView(withId(R.id.navigation_qrcode_library)).perform(click());
        // verify
        onView(withId(R.id.fragment_library_parent)).check(matches(isDisplayed()));
        // nav qrCodeSummaryStatisticsFragment
        onView(withId(R.id.view_personal_qr_stats_button)).perform(click());
        // verify
        onView(withId(R.id.fragment_qr_code_summary_statistics_parent)).check(matches(isDisplayed()));
        // nav to other fragment then back
        onView(withId(R.id.navigation_profile)).perform(click());
        onView(withId(R.id.navigation_qrcode_library)).perform(click());
        //verify
        onView(withId(R.id.fragment_library_parent)).check(matches(isDisplayed()));
    }


}
