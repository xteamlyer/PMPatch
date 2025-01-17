package com.v7878.hooks.pmpatch;

import static com.v7878.zygisk.ZygoteLoader.PACKAGE_SYSTEM_SERVER;

import android.util.Log;

import com.v7878.r8.annotations.DoNotObfuscate;
import com.v7878.r8.annotations.DoNotObfuscateType;
import com.v7878.r8.annotations.DoNotShrink;
import com.v7878.r8.annotations.DoNotShrinkType;
import com.v7878.zygisk.ZygoteLoader;

@DoNotShrinkType
@DoNotObfuscateType
public class Main {
    public static String TAG = "PM_PATCH";

    @SuppressWarnings({"unused", "ConfusingMainMethod"})
    @DoNotShrink
    @DoNotObfuscate
    public static void main() throws Throwable {
        Log.i(TAG, "Injected into " + ZygoteLoader.getPackageName());
        try {
            if (BuildConfig.RUN_FOR_SYSTEM_SERVER &&
                    PACKAGE_SYSTEM_SERVER.equals(ZygoteLoader.getPackageName())) {
                MethodAndArgsCallerHook.init();
            }
            if (BuildConfig.RUN_FOR_APPLICATIONS) {
                LoadedApkHook.init();
            }
            EntryPoint.mainPreliminary();
        } catch (Throwable th) {
            Log.e(TAG, "Exception", th);
        }
        Log.i(TAG, "Done");
    }
}
