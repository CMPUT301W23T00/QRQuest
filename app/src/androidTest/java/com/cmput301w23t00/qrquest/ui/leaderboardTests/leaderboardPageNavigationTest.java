/*
 * This class contains tests for QR code deletion functionality.
 * Two tests are present - one to verify that deletion is cancelled
 * and another to verify that deletion is confirmed.
 */

package com.cmput301w23t00.qrquest.ui.leaderboardTests;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cmput301w23t00.qrquest.MainActivity;
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
        //onView(withId(R.id.circle_button_frame)).perform(click());
        // Verify that the libraryFragment is displayed
//        Thread.sleep(20000); // Wait for 10 seconds
//        onView(withId(R.id.fragment_leaderboard_parent)).check(matches(isDisplayed()));
//
//        // Click on the first item in the QR code list
//        Thread.sleep(20000); // Wait for 10 seconds
//
//        onData(anything())
//                .inAdapterView(withId(R.id.leaderboard_qr_codes_list))
//                .atPosition(0)
//                .perform(click());
//
//        // Verify that the qr_code_info Fragment is displayed
//        onView(withId(R.id.fragment_qr_code_info_parent)).check(matches(isDisplayed()));
        }
    
}