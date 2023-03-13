package com.cmput301w23t00.qrquest.ui.createaccount;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.editaccount.EditAccount;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

}
