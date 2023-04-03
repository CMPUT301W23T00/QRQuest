package com.cmput301w23t00.qrquest;
import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;


import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cmput301w23t00.qrquest.ui.addqrcode.qrnameactivity.ImageCompressor;

/**
 * Tests ImageCompressor
 */

@RunWith(AndroidJUnit4.class)
public class ImageCompressorTest {

    @Test
    public void compressImageTest1() throws IOException {
        Context context = InstrumentationRegistry.getTargetContext();
        InputStream inputStream = context.getResources().openRawResource(R.drawable.test_image);
        File originalFile = File.createTempFile("test_image", ".jpg", context.getCacheDir());
        OutputStream outputStream = new FileOutputStream(originalFile);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.close();
        inputStream.close();

        File compressedFile = ImageCompressor.compressImage(context, originalFile);
        assertEquals(originalFile.length(),8790776);
        assertEquals(compressedFile.length(),145011);
    }

    @Test
    public void compressImageTest2() throws IOException {
        Context context1 = InstrumentationRegistry.getTargetContext();
        InputStream inputStream1 = context1.getResources().openRawResource(R.drawable.test_image2);
        File originalFile1 = File.createTempFile("test_image2", ".jpg", context1.getCacheDir());
        OutputStream outputStream1 = new FileOutputStream(originalFile1);
        byte[] buffer = new byte[1024];
        int length1;
        while ((length1 = inputStream1.read(buffer)) > 0) {
            outputStream1.write(buffer, 0, length1);
        }
        outputStream1.close();
        inputStream1.close();

        File compressedFile1 = ImageCompressor.compressImage(context1, originalFile1);
        assertEquals(originalFile1.length(),115111);
        assertEquals(compressedFile1.length(),77279);
    }

    @Test
    public void compressImageTest3() throws IOException {
        Context context2 = InstrumentationRegistry.getTargetContext();
        InputStream inputStream2 = context2.getResources().openRawResource(R.drawable.test_image3);
        File originalFile2 = File.createTempFile("test_image3", ".jpg", context2.getCacheDir());
        OutputStream outputStream2 = new FileOutputStream(originalFile2);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream2.read(buffer)) > 0) {
            outputStream2.write(buffer, 0, length);
        }
        outputStream2.close();
        inputStream2.close();

        File compressedFile2 = ImageCompressor.compressImage(context2, originalFile2);
        assertEquals(originalFile2.length(),125127);
        assertEquals(compressedFile2.length(),84641);
    }
}
