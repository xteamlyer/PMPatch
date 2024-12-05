package com.v7878.hooks.pmpatch;

import static com.v7878.hooks.pmpatch.Main.TAG;
import static com.v7878.unsafe.Reflection.getDeclaredMethods;
import static com.v7878.unsafe.Utils.nothrows_run;

import android.annotation.SuppressLint;
import android.util.Log;

import com.v7878.unsafe.invoke.Transformers;
import com.v7878.vmtools.Hooks;
import com.v7878.vmtools.Hooks.EntryPointType;
import com.v7878.vmtools.Hooks.HookTransformer;
import com.v7878.zygisk.ZygoteLoader;

@SuppressLint("PrivateApi")
public class ZygoteInitHook {
    private static final String ZYGOTE_INIT = "com.android.internal.os.ZygoteInit";
    private static final Class<?> ZYGOTE_INIT_CLASS = nothrows_run(() -> Class.forName(ZYGOTE_INIT));

    private static final HookTransformer hooker = (original, stack) -> {
        Transformers.invokeExactWithFrame(original, stack);

        Log.i(TAG, "ZygoteInit#handleSystemServerProcess() starts");

        // get system_server classLoader
        var loader = Thread.currentThread().getContextClassLoader();

        try {
            ApplicationHook.init(ZygoteLoader.PACKAGE_SYSTEM_SERVER, loader);
        } catch (Throwable th) {
            Log.e(TAG, "Exception", th);
        }
    };

    public static void init() throws Throwable {
        var methods = getDeclaredMethods(ZYGOTE_INIT_CLASS);
        for (var method : methods) {
            if ("handleSystemServerProcess".equals(method.getName())) {
                Hooks.hook(method, EntryPointType.CURRENT, hooker, EntryPointType.DIRECT);
            }
        }
    }
}
