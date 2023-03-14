package com.cmput301w23t00.qrquest.ui.editaccount;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertTrue;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.uiautomator.UiDevice;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.createaccount.CreateAccount;
import com.cmput301w23t00.qrquest.ui.profile.UserProfile;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

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
        Intents.init();
        UserProfile userProfile = new UserProfile();
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

    // checks if activity is finished when back button is pressed.
    @Test
    public void testBackButtonFinishesEditAccount() {
        ActivityScenario<CreateAccount> activityScenario = ActivityScenario.launch(CreateAccount.class);
        // Simulate a press of the system back button
        onView(isRoot()).perform(ViewActions.pressBackUnconditionally());
        // Check that the activity is finishing
        assertTrue(activityScenario.getState().isAtLeast(Lifecycle.State.DESTROYED));
    }
    /*
    TODO:
        * if account info is edited with valid data
        * if account info is not edited with empty data
        * if account info is not edited with invalid data
        * if activity finishes when either cancel or confirm button is press
        * if previous values of account is set on fields
     */

    @Test
    public void testEditAccountSuccess() throws InterruptedException {
        // Set up test data

        ActivityScenario<EditAccount> activityScenario = ActivityScenario.launch(EditAccount.class);
        String name = "TestCreateAccountSuccess";
        String phone = "123-456-7890";
        String email = "test@gmail.com";
        String aboutMe = "I live to walk";

        // Input data into UI fields

        onView(withId(R.id.editNameField)).perform(click(), typeText(name), closeSoftKeyboard());
        onView(withId(R.id.editNameField)).check(matches(withText(name)));
        onView(withId(R.id.editPhoneField)).perform(click(), replaceText(phone), closeSoftKeyboard());
        onView(withId(R.id.editPhoneField)).check(matches(withText(phone)));
        onView(withId(R.id.editEmailField)).perform(click(), typeText(email), closeSoftKeyboard());
        onView(withId(R.id.editEmailField)).check(matches(withText(email)));
        onView(withId(R.id.editAboutMeField)).perform(click(), typeText(aboutMe), closeSoftKeyboard());
        onView(withId(R.id.editAboutMeField)).check(matches(withText(aboutMe)));

        // Click the "Create Account" button
        //onView(withId(R.id.editConfirmButton)).perform(click());
        //intended(hasComponent(MainActivity.class.getName()));
    }

    @After
    public void turnOnDeviceAnimations() throws IOException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        device.executeShellCommand("settings put global window_animation_scale 1");
        device.executeShellCommand("settings put global transition_animation_scale 1");
        device.executeShellCommand("settings put global animator_duration_scale 1");
        Intents.release();
    }
}
