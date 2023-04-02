package com.cmput301w23t00.qrquest.ui.leaderboardTests;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cmput301w23t00.qrquest.MainActivity;
import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.profile.UserProfile;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class leaderboardPageNavigationTest {

    /**
     * This method is called before each test and sets up the ActivityScenario for MainActivity.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
    }

    /**
     * This method tests the fragment navigation and cancellation of QR code deletion.
     * @throws InterruptedException
     */
    @Test
    public void testLeaderboardUI() throws InterruptedException {
        UserProfile.setUserId("com.google.android.gms.tasks.zzw@9bae679");

        // Navigate to libraryFragment
        onView(withId(R.id.circle_button_frame)).perform(click());

        // Verify that the leaderboardFragment is displayed
        Thread.sleep(20000); // Wait for 20 seconds
        onView(withId(R.id.fragment_leaderboard_parent)).check(matches(isDisplayed()));

        // Click on the first item in the User list
        Thread.sleep(20000); // Wait for 20 seconds
        onData(anything())
                .inAdapterView(withId(R.id.leaderboard_users_list))
                .atPosition(0)
                .perform(click());

        // Verify that the user profile Fragment is displayed
        //onView(withId(R.id.)).check(matches(isDisplayed()));
    }
}
