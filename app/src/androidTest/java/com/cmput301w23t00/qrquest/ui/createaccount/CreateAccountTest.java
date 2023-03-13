package com.cmput301w23t00.qrquest.ui.createaccount;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertFalse;

import androidx.test.core.app.ActivityScenario;
import androidx.test.uiautomator.UiDevice;

import com.cmput301w23t00.qrquest.MainActivity;
import com.cmput301w23t00.qrquest.R;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class CreateAccountTest {
    @Test
    public void testCreateAccountExists() {
        ActivityScenario<CreateAccount> scenario = ActivityScenario.launch(CreateAccount.class);
        // Check that the activity is launched and not null
        scenario.onActivity(Assert::assertNotNull);
        scenario.close();
    }

    @Before
    public void setUp() {
        ActivityScenario<CreateAccount> activityScenario = ActivityScenario.launch(CreateAccount.class);
    }

    @Test
    public void testCreateAccountLayoutDisplayed() {
        onView(withId(R.id.create_account_parent)).check(matches(isDisplayed()));
    }

    // Elements of EditAccount displayed.
    @Test
    public void testCreateAccountProfileImageDisplayed() {
        onView(withId(R.id.addProfileImage)).check(matches(isDisplayed()));
    }

    @Test
    public void testCreateAccountProfileMaskDisplayed() {
        onView(withId(R.id.addProfileImageMask)).check(matches(isDisplayed()));
    }

    @Test
    public void testCreateAccountNameFieldDisplayed() {
        onView(withId(R.id.addNameField)).check(matches(isDisplayed()));
    }

    @Test
    public void testCreateAccountPhoneFieldDisplayed() {
        onView(withId(R.id.addPhoneField)).check(matches(isDisplayed()));
    }

    @Test
    public void testCreateAccountEmailFieldDisplayed() {
        onView(withId(R.id.addEmailField)).check(matches(isDisplayed()));
    }

    @Test
    public void testCreateAccountCreateButtonDisplayed() {
        onView(withId(R.id.addCreateButton)).check(matches(isDisplayed()));
    }

    @Before
    public void turnOffDeviceAnimations() throws IOException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        device.executeShellCommand("settings put global window_animation_scale 0");
        device.executeShellCommand("settings put global transition_animation_scale 0");
        device.executeShellCommand("settings put global animator_duration_scale 0");
    }
    @Test
    public void testCreateAccountSuccess() throws InterruptedException {
        // Set up test data

        ActivityScenario<CreateAccount> activityScenario = ActivityScenario.launch(CreateAccount.class);
        String name = "TestCreateAccountSuccess";
        String phone = "123-456-7890";
        String email = "test@gmail.com";

        // Input data into UI fields
        onView(withId(R.id.addNameField)).perform(typeText(name));
        onView(withId(R.id.addPhoneField)).perform(typeText(phone));
        onView(withId(R.id.addEmailField)).perform(typeText(email));

        // Click the "Create Account" button
        onView(withId(R.id.addCreateButton)).perform(click());

        activityScenario.onActivity(activity -> {
            assertFalse(activity.isFinishing());
        });
        intended(hasComponent(MainActivity.class.getName()));
    }


    @After
    public void turnOnDeviceAnimations() throws IOException {
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        device.executeShellCommand("settings put global window_animation_scale 1");
        device.executeShellCommand("settings put global transition_animation_scale 1");
        device.executeShellCommand("settings put global animator_duration_scale 1");
    }
}
