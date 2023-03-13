package com.cmput301w23t00.qrquest;

import org.junit.Test;

import static org.junit.Assert.*;
 import static java.lang.Boolean.FALSE;
 import static java.lang.Boolean.TRUE;

 import com.cmput301w23t00.qrquest.ui.addqrcode.QRCodeProcessor;
 import com.cmput301w23t00.qrquest.ui.profile.UserSettings;

/**
 * Tests QR code processor methods
 */
public class UserSettingsTest {

    @Test
    public void testGetGeoLocation() {
        UserSettings userSettings = new UserSettings(true);
        assertEquals(userSettings.getGeoLocation(), true);
    }

    @Test
    public void testSetGeoLocation() {
        UserSettings userSettings = new UserSettings(true);
        userSettings.setGeoLocation(false);
        assertEquals(userSettings.getGeoLocation(), false);
    }

    @Test
    public void testGetPushNotifications() {
        UserSettings userSettings = new UserSettings(true);
        assertEquals(userSettings.getPushNotifications(), true);
    }

    @Test
    public void testSetPushNotifications() {
        UserSettings userSettings = new UserSettings(true);
        userSettings.setPushNotifications(true);
        assertEquals(userSettings.getPushNotifications(), true);
    }
}


