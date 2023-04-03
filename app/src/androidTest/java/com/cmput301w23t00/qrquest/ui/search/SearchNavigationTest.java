package com.cmput301w23t00.qrquest.ui.search;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;

import com.cmput301w23t00.qrquest.R;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cmput301w23t00.qrquest.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for navigation of SearchFragment
 */
@RunWith(AndroidJUnit4.class)
public class SearchNavigationTest {
    /**
     * Sets up tests
     * @throws Exception if activity cannot be started
     */
    @Before
    public void setUp() throws Exception {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
    }

    /**
     * Tests that navigation to SearchFragment works correctly
     */
    @Test
    public void testLibraryFragmentsNavigation() throws InterruptedException {
        // Navigate to SearchFragment
        onView(withId(R.id.navigation_search)).perform(click());
        // Verify navigation to SearchFragment occurred
        onView(withId(R.id.fragment_search_parent)).check(matches(isDisplayed()));
        // Click on the first item in the user list
        Thread.sleep(8000); // wait for 5 seconds
        onData(anything())
                .inAdapterView(withId(R.id.search_users_list))
                .atPosition(0)
                .perform(click());
        // Verify navigation to external user profile occurred
        onView(withId(R.id.fragment_profile_parent)).check(matches(isDisplayed()));
        // Navigate up
        onView(withContentDescription("Navigate up")).perform(click());
        // Verify navigation to SearchFragment occurred
        onView(withId(R.id.fragment_search_parent)).check(matches(isDisplayed()));

    }
}
