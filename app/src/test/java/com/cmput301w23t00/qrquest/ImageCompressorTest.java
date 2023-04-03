package com.cmput301w23t00.qrquest;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;

import com.cmput301w23t00.qrquest.ui.addqrcode.qrnameactivity.ImageCompressor;

@RunWith(AndroidJUnit4.class)
@Config(manifest = Config.NONE)
public class ImageCompressorTest {

    @Test
    public void testCompressImage() {
        Context context = ApplicationProvider.getApplicationContext();
        Resources resources = context.getResources();

        int drawableId = resources.getIdentifier("closedeyes.png", "drawable", context.getPackageName());
        System.out.println(drawableId);
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + drawableId);


        File originalFile = new File(uri.getPath());

        File compressedFile = ImageCompressor.compressImage(context, originalFile);
        assertNotNull(compressedFile);
    }
}