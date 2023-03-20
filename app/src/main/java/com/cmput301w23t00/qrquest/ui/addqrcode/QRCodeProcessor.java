package com.cmput301w23t00.qrquest.ui.addqrcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.cmput301w23t00.qrquest.R;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class QRCodeProcessor {
    // Instance variable to store the QR code data
    private String QRCodeData;

    // Two lists of strings used for generating the name
    List<String> ZeroBit = new ArrayList<>() {{
        add("cool ");
        add("Fro");
        add("Mo");
        add("Mega");
        add("Spectral");
        add("Crab");
    }};

    List<String> OneBit = new ArrayList<>() {{
        add("hot ");
        add("Glo");
        add("Lo");
        add("Ultra");
        add("Sonic");
        add("Shark");
    }};

    List<Integer> ZeroBitImage = new ArrayList<>() {{
        add(R.drawable.closedeyes);
        add(R.drawable.eyebrows);
        add(R.drawable.round);
        add(R.drawable.nose);
        add(R.drawable.smile);
        add(R.drawable.freckles);
    }};

    List<Integer> OneBitImage = new ArrayList<>() {{
        add(R.drawable.open);
        add(-1);
        add(R.drawable.square);
        add(-1);
        add(R.drawable.frown);
        add(-1);
    }};

    // Constructor that initializes the QR code data instance variable
    public QRCodeProcessor(String Data) {
        this.QRCodeData = Data;
    }

    /** Generates human-readable name for a QR Code
     *
     * @return Human-readable name of the QR Code
     */
    public String getName() {
        String res = this.QRCodeData;
        // Take the first two characters of the hash
        String FirstTwo = res.substring(0,2);
        // Convert the first two characters to an integer
        int IntString = Integer.parseInt(FirstTwo, 16);
        // Convert the integer to a binary string and take the first six characters
        String bin = Integer.toBinaryString(IntString);
        if (bin.length() < 8) {
            bin = String.format("%s%0" + (8 - bin.length()) + "d", bin, 0);
        }
        bin = bin.substring(0,6);

        // Create a string by combining the strings from the ZeroBit and OneBit lists
        String Final = "";
        for (int i = 0; i < bin.length(); i++) {
            if (bin.charAt(i) == '0') {
                Final = Final + ZeroBit.get(i);
            } else {
                Final = Final + OneBit.get(i);
            }
        }

        // Return the final string
        return Final;
    }

    /** Generates the score for a QR Code
     *
     * @return An integer score for the QR Code
     */
    public int getScore() {
        String res = this.QRCodeData;
        // Calculate the score based on the hash
        double Final = 0;
        int Current = 0;
        int Repeats = 0;
        for (int i = 0; i < res.length(); i++) {
            int IntString = Integer.parseInt(String.valueOf(res.charAt(i)), 16);
            if (IntString == 0) {
                IntString = 20;
            }
            if (Current != IntString && Repeats != 0) {
                Final += Math.pow((double) Current,(double) (Repeats-1));

                Current = IntString;
                Repeats = 0;
            }
            Repeats++;
        }
        Final += Math.pow((double) Current,(double) (Repeats-1));

        return ((int) Final);
    }

    /** Generates the visual representation of a QR Code
     *
     * @return An Bitmap for the QR code
     */
    public Bitmap getBitmap(Context context) {
        String res = this.QRCodeData;
        // Take the first two characters of the hash
        String FirstTwo = res.substring(0,2);
        // Convert the first two characters to an integer
        int IntString = Integer.parseInt(FirstTwo, 16);
        // Convert the integer to a binary string and take the first six characters
        String bin = Integer.toBinaryString(IntString);
        if (bin.length() < 8) {
            bin = String.format("%s%0" + (8 - bin.length()) + "d", bin, 0);
        }
        bin = bin.substring(0,6);

        // Create a string by combining the strings from the ZeroBit and OneBit lists

        // TODO: Change the Size thing
        Bitmap Size = BitmapFactory.decodeResource(context.getResources(), ZeroBitImage.get(0));
        Bitmap Result = Bitmap.createBitmap(Size.getWidth(),Size.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(Result);
        for (int i = 0; i < bin.length(); i++) {
            if (bin.charAt(i) == '0') {
                if (ZeroBitImage.get(i) != -1) {
                    Bitmap map = BitmapFactory.decodeResource(context.getResources(), ZeroBitImage.get(i));
                    canvas.drawBitmap(map, 0, 0, null);
                }
            } else {
                if (OneBitImage.get(i) != -1) {
                    Bitmap map = BitmapFactory.decodeResource(context.getResources(), OneBitImage.get(i));
                    canvas.drawBitmap(map, 0, 0, null);
                }
            }
        }

        // Return the final bitmap
        return Result;
    }

    /** Generates a sha-256 hash from a given string input
     * from http://www.java2s.com/example/android-utility-method/sha256-hash-create-index-0.html#:~:text=Generate%20a%20SHA256%20checksum%20of,update(string.
     *
     * @return A sha-256 hash
     */
    public static String sha256(String input) {
        try {
            // Creates a MessageDigest object with SHA-256 algorithm.
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Generates the hash of the input string.
            byte[] hash = md.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            // Appends the hash bytes as hex string to the StringBuilder.
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            // Returns the hex string representation of the hash.
            return sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) { // Handles possible exceptions.
            // Throws a runtime exception if any exception occurs.
            throw new RuntimeException(ex);
        }
    }

}
