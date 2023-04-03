package com.cmput301w23t00.qrquest.ui.addqrcode.qrnameactivity;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

public class ImageCompressor {
    /**
     * Compress an image and return the compressed file object
     *
     * @param context      the application context
     * @param originalFile the file object of the original image
     * @return the file object of the compressed image
     */
    public static File compressImage(Context context, File originalFile) {
        try {
            System.out.println(context);
            System.out.println(originalFile);
            return new Compressor(context).compressToFile(originalFile);
        } catch (IOException e) {
            Log.e("ImageCompressor", "Error compressing image", e);
            return null;
        }
    }
}
