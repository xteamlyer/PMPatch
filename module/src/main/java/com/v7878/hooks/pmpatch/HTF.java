package com.v7878.hooks.pmpatch;

import static com.v7878.hooks.pmpatch.Main.TAG;
import static com.v7878.unsafe.invoke.EmulatedStackFrame.RETURN_VALUE_IDX;

import android.util.Log;

import com.v7878.unsafe.invoke.EmulatedStackFrame;
import com.v7878.vmtools.Hooks.HookTransformer;

public class HTF {
    public static class StackException extends Exception {
    }

    public static void printStackTrace(EmulatedStackFrame frame) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, frame.toString(), new StackException());
        }
    }

    public static final HookTransformer NOP = (original, frame) -> {
        printStackTrace(frame);
    };
    public static final HookTransformer FALSE = (original, frame) -> {
        printStackTrace(frame);
        frame.accessor().setBoolean(RETURN_VALUE_IDX, false);
    };
    public static final HookTransformer TRUE = (original, frame) -> {
        printStackTrace(frame);
        frame.accessor().setBoolean(RETURN_VALUE_IDX, true);
    };

    public static HookTransformer return_constant(Object value) {
        return (original, frame) -> {
            printStackTrace(frame);
            frame.accessor().setValue(RETURN_VALUE_IDX, value);
        };
    }
}
