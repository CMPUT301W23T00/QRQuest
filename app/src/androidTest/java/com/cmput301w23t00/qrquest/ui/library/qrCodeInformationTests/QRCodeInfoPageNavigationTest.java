package com.cmput301w23t00.qrquest.ui.library.qrCodeInformationTests;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cmput301w23t00.qrquest.MainActivity;
import com.cmput301w23t00.qrquest.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class QRCodeInfoPageNavigationTest {
    @Before
    public void setUp() throws Exception {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void testQRCodeInfoPageFragmentsNavigation() throws InterruptedException {
        // nav libraryFragment
        onView(withId(R.id.navigation_qrcode_library)).perform(click());
        // verify
        onView(withId(R.id.fragment_library_parent)).check(matches(isDisplayed()));

        // click on the first item in the qr code list
        Thread.sleep(5000); // wait for 5 seconds
        onData(anything())
                .inAdapterView(withId(R.id.library_qr_codes_list))
                .atPosition(0)
                .perform(click());
        // verify
        onView(withId(R.id.fragment_qr_code_info_parent)).check(matches(isDisplayed()));

    }

}
