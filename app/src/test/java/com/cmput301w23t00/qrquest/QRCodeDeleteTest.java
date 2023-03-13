package com.cmput301w23t00.qrquest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Config.OLDEST_SDK, Config.NEWEST_SDK})
public class QRCodeDeleteTest {

    @Before
    public void setup() {
//        testDeleteQRCodeDuplicates();
    }

    @Test
    public void testDeleteQRCodeDuplicates() {
        // expected result: only delete one qr code

    }
}
