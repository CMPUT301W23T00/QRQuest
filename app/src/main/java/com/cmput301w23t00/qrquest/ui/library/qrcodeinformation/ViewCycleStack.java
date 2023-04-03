package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Stack;

/**
 * ViewCycleStack used as a fragment save instance stack to store bundles
 */
public class ViewCycleStack {
    private static ArrayList<Bundle> stack = new ArrayList<>();

    /**
     * regular stack pop for stack bundle
     * @return
     */
    public static Bundle pop() {
        Bundle bundle = stack.get(stack.size() - 1);
        stack.remove(stack.size() - 1);
        return bundle;
    }

    /**
     * regular stack push to add bundle
     * @param bundle
     */
    public static void push(Bundle bundle) {
        stack.add(bundle);
    }

    /**
     * reset stack
     */
    public static void reset() {
        stack = new ArrayList<>();
    }
}
