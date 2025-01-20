package com.v7878.hooks.pmpatch;

import static android.os.Build.VERSION.SDK_INT;
import static com.v7878.hooks.pmpatch.Main.TAG;
import static com.v7878.unsafe.Reflection.getDeclaredMethod;

import android.annotation.SuppressLint;
import android.util.Log;

import com.v7878.r8.annotations.DoNotShrink;
import com.v7878.unsafe.invoke.EmulatedStackFrame;
import com.v7878.unsafe.invoke.Transformers;
import com.v7878.vmtools.Hooks;
import com.v7878.vmtools.Hooks.EntryPointType;

@SuppressLint("PrivateApi")
public class MethodAndArgsCallerHook {
    private static final String SYSTEM_SERVER = "com.android.server.SystemServer";
    private static final String RUNTIME_INIT = "com.android.internal.os.RuntimeInit";

    private static void runForSystemServer(EmulatedStackFrame frame) throws Throwable {
        var accessor = frame.accessor();
        if (SYSTEM_SERVER.equals(accessor.getReference(0))) {
            ClassLoader loader = accessor.getReference(2);
            EntryPoint.mainSystem(loader);
        }
    }

    @DoNotShrink
    public static void init() throws Throwable {
        Class<?> init_class = Class.forName(RUNTIME_INIT);

        String method_name = SDK_INT == 26 ? "invokeStaticMain" : "findStaticMain";
        var method = getDeclaredMethod(init_class, method_name,
                String.class, String[].class, ClassLoader.class);

        Hooks.hook(method, EntryPointType.CURRENT, (original, frame) -> {
            try {
                runForSystemServer(frame);
            } catch (Throwable th) {
                Log.e(TAG, "Exception", th);
            }
            Transformers.invokeExactWithFrame(original, frame);
        }, EntryPointType.DIRECT);
    }
}
