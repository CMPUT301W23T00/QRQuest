package com.cmput301w23t00.qrquest;

import org.junit.Test;

import static org.junit.Assert.*;

import com.cmput301w23t00.qrquest.ui.profile.UserProfile;

public class UserProfileClass {
    private UserProfile createMockUser() {
        UserProfile userProfile = new UserProfile(true);
        return userProfile;
    }

    @Test
    public void testGetAboutMe() {
        createMockUser();
        UserProfile.setAboutMe("test");
        assertEquals(UserProfile.getAboutMe(), "test");
    }

    @Test
    public void testSetAboutMe() {
        createMockUser();
        UserProfile.setAboutMe("test2");
        assertEquals(UserProfile.getAboutMe(), "test2");
    }

    @Test
    public void testGetPhoneNumber() {
        createMockUser();
        UserProfile.setPhoneNumber("777-777-7777");
        assertEquals(UserProfile.getPhoneNumber(), "777-777-7777");
    }

    @Test
    public void testSetPhoneNumber() {
        createMockUser();
        UserProfile.setPhoneNumber("777-777-7778");
        assertEquals(UserProfile.getPhoneNumber(), "777-777-7778");
    }

    @Test
    public void testGetEmail() {
        createMockUser();
        UserProfile.setEmail("test@mail.com");
        assertEquals(UserProfile.getEmail(), "test@mail.com");
    }

    @Test
    public void testSetEmail() {
        createMockUser();
        UserProfile.setEmail("test2@mail.com");
        assertEquals(UserProfile.getEmail(), "test2@mail.com");
    }

    @Test
    public void testGetName() {
        createMockUser();
        UserProfile.setName("bob");
        assertEquals(UserProfile.getName(), "bob");
    }

    @Test
    public void testSetName() {
        createMockUser();
        UserProfile.setName("bob2");
        assertEquals(UserProfile.getName(), "bob2");
    }

    @Test
    public void testGetUserId() {
        createMockUser();
        UserProfile.setUserId("1234");
        assertEquals(UserProfile.getName(), "bob");
    }
}
