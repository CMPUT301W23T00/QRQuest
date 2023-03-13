package com.cmput301w23t00.qrquest.ui.editaccount;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.createaccount.CreateAccount;
import com.cmput301w23t00.qrquest.ui.loadingscreen.LoadingScreen;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EditAccountTest {
    @Test
    public void testEditAccountExists() {
        ActivityScenario<EditAccount> scenario = ActivityScenario.launch(EditAccount.class);
        // Check that the activity is launched and not null
        scenario.onActivity(Assert::assertNotNull);
        scenario.close();
    }

    @Before
    public void setUp() {
        ActivityScenario<EditAccount> activityScenario = ActivityScenario.launch(EditAccount.class);
    }

    @Test
    public void testEditAccountLayoutDisplayed() {
        onView(withId(R.id.edit_account_parent)).check(matches(isDisplayed()));
    }


    // Elements of EditAccount displayed.
    @Test
    public void testEditAccountProfileImageDisplayed() {
        onView(withId(R.id.editProfileImage)).check(matches(isDisplayed()));
    }

    @Test
    public void testEditAccountProfileMaskDisplayed() {
        onView(withId(R.id.editProfileImageMask)).check(matches(isDisplayed()));
    }

    @Test
    public void testEditAccountNameFieldDisplayed() {
        onView(withId(R.id.editNameField)).check(matches(isDisplayed()));
    }

    @Test
    public void testEditAccountAboutMeFieldDisplayed() {
        onView(withId(R.id.editAboutMeField)).check(matches(isDisplayed()));
    }

    @Test
    public void testEditAccountPhoneFieldDisplayed() {
        onView(withId(R.id.editPhoneField)).check(matches(isDisplayed()));
    }

    @Test
    public void testEditAccountEmailFieldDisplayed() {
        onView(withId(R.id.editEmailField)).check(matches(isDisplayed()));
    }

    @Test
    public void testEditAccountCancelButtonDisplayed() {
        onView(withId(R.id.editCancelButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testEditAccountConfirmButtonDisplayed() {
        onView(withId(R.id.editConfirmButton)).check(matches(isDisplayed()));
    }

}
