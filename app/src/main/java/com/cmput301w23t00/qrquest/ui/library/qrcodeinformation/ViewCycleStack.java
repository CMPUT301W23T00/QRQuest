package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Stack;

public class ViewCycleStack {
    private static ArrayList<Bundle> stack = new ArrayList<>();

    public static Bundle pop() {
        Bundle bundle = stack.get(stack.size() - 1);
        stack.remove(stack.size() - 1);
        return bundle;
    }

    public static void push(Bundle bundle) {
        stack.add(bundle);
    }
}
