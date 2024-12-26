package com.v7878.hooks.pmpatch;

import static com.v7878.hooks.pmpatch.Main.TAG;

import android.util.Log;

import com.v7878.r8.annotations.DoNotShrink;

public class SystemServerHook {
    @DoNotShrink
    public static void init(ClassLoader loader) throws Throwable {
        Log.w(TAG, "loader: " + loader);

        var hooks = new BulkHooker();
        HookList.init(hooks, true);
        hooks.apply(loader);
    }
}
