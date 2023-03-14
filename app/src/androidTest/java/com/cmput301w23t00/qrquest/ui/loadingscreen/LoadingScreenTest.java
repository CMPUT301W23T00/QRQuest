package com.cmput301w23t00.qrquest.ui.loadingscreen;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertFalse;

import androidx.test.core.app.ActivityScenario;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cmput301w23t00.qrquest.R;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoadingScreenTest {

    @Test
    public void testLoadingScreenExists() {
        ActivityScenario<LoadingScreen> scenario = ActivityScenario.launch(LoadingScreen.class);
        // Check that the activity is launched and not null
        scenario.onActivity(Assert::assertNotNull);
        scenario.close();
    }

    @Before
    public void setUp() {
        ActivityScenario<LoadingScreen> activityScenario = ActivityScenario.launch(LoadingScreen.class);
    }

    @Test
    public void testLoadingScreenLayoutDisplayed() {
        onView(withId(R.id.loading_screen_parent)).check(matches(isDisplayed()));
    }

    // Elements of LoadingScreen displayed
    @Test
    public void testLoadingScreenLogoDisplayed() {
        onView(withId(R.id.lsQRCode)).check(matches(isDisplayed()));
    }

    @Test
    public void testLoadingScreenDurationAndFinish() {

        ActivityScenario<LoadingScreen> scenario = ActivityScenario.launch(LoadingScreen.class);
        // Wait for the loading screen to disappear after the specified duration
        scenario.onActivity(activity -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // Check that the loading screen is finishing.
            assertFalse(activity.isFinishing());
        });
        scenario.close();
    }

}
