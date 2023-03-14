/*
 * This class contains tests for QR code deletion functionality.
 * Two tests are present - one to verify that deletion is cancelled
 * and another to verify that deletion is confirmed.
 */

package com.cmput301w23t00.qrquest.ui.library.qrCodeInformationTests;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cmput301w23t00.qrquest.MainActivity;
import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.profile.UserProfile;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class QRCodeDeletionTest {

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
    public void testQRCodeDeletionFragmentsNavigation_cancel() throws InterruptedException {
        UserProfile.setUserId("com.google.android.gms.tasks.zzw@9bae679");

        // Navigate to libraryFragment
        onView(withId(R.id.navigation_qrcode_library)).perform(click());
        // Verify that the libraryFragment is displayed
        onView(withId(R.id.fragment_library_parent)).check(matches(isDisplayed()));

        // Click on the first item in the QR code list
        Thread.sleep(5000); // Wait for 5 seconds
        onData(anything())
                .inAdapterView(withId(R.id.library_qr_codes_list))
                .atPosition(0)
                .perform(click());

        // Verify that the qr_code_info Fragment is displayed
        onView(withId(R.id.fragment_qr_code_info_parent)).check(matches(isDisplayed()));

        Thread.sleep(5000); // Wait for 5 seconds

        // Delete QR code
        onView(withId(R.id.qr_delete)).perform(click());

        // Confirm deletion is cancelled
        onView(withText("Cancel"))
                .perform(click());

        Thread.sleep(5000); // Wait for 5 seconds

        // Verify that the qr_code_info Fragment is still displayed
        onView(withId(R.id.fragment_qr_code_info_parent)).check(matches(isDisplayed()));
    }

    /**
     * This method tests the fragment navigation and confirmation of QR code deletion.
     * @throws InterruptedException
     */
    @Test
    public void testQRCodeDeletionFragmentsNavigation_confirm() throws InterruptedException {
        UserProfile.setUserId("com.google.android.gms.tasks.zzw@9bae679");

        // Navigate to libraryFragment
        onView(withId(R.id.navigation_qrcode_library)).perform(click());
        // Verify that the libraryFragment is displayed
        onView(withId(R.id.fragment_library_parent)).check(matches(isDisplayed()));

        // Click on the first item in the QR code list
        Thread.sleep(5000); // Wait for 5 seconds
        onData(anything())
                .inAdapterView(withId(R.id.library_qr_codes_list))
                .atPosition(0)
                .perform(click());

        // Verify that the qr_code_info Fragment is displayed
        onView(withId(R.id.fragment_qr_code_info_parent)).check(matches(isDisplayed()));

        // Delete QR code
        onView(withId(R.id.qr_delete)).perform(click());
    }
}