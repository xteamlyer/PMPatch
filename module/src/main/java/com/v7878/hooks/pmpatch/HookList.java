package com.v7878.hooks.pmpatch;

import static android.content.pm.PackageManager.SIGNATURE_MATCH;

public class HookList {

    public static void init(BulkHooker hooks) {
        if (BuildConfig.PATCH_1) {
            hooks.add(HTF.TRUE, "java.security.Signature", "verify", "boolean", "byte[]");
            hooks.add(HTF.TRUE, "java.security.Signature", "verify", "boolean", "byte[]", "int", "int");

            hooks.add(HTF.TRUE, "com.android.org.conscrypt.OpenSSLSignature", "engineVerify", "boolean", "byte[]");
        }

        if (BuildConfig.PATCH_2) {
            hooks.add(HTF.TRUE, "java.security.MessageDigest", "isEqual", "boolean", "byte[]", "byte[]");
        }

        if (BuildConfig.PATCH_3) {
            hooks.add(HTF.TRUE, "android.content.pm.SigningDetails", "checkCapability", "boolean", "android.content.pm.SigningDetails", "int");
            hooks.add(HTF.TRUE, "android.content.pm.PackageParser$SigningDetails", "checkCapability", "boolean", "android.content.pm.PackageParser$SigningDetails", "int");

            hooks.add(HTF.return_constant(SIGNATURE_MATCH), "com.android.server.pm.PackageManagerServiceUtils", "compareSignatures", "int", "android.content.pm.Signature[]", "android.content.pm.Signature[]");
            hooks.add(HTF.return_constant(SIGNATURE_MATCH), "com.android.server.pm.PackageManagerService", "compareSignatures", "int", "android.content.pm.Signature[]", "android.content.pm.Signature[]");

            hooks.add(HTF.FALSE, "com.android.server.pm.PackageManagerServiceUtils", "verifySignatures", "boolean", "com.android.server.pm.PackageSetting", "com.android.server.pm.SharedUserSetting", "com.android.server.pm.PackageSetting", "android.content.pm.SigningDetails", "boolean", "boolean", "boolean");
            hooks.add(HTF.FALSE, "com.android.server.pm.PackageManagerServiceUtils", "verifySignatures", "boolean", "com.android.server.pm.PackageSetting", "com.android.server.pm.PackageSetting", "android.content.pm.PackageParser$SigningDetails", "boolean", "boolean", "boolean");

            hooks.add(HTF.NOP, "com.android.server.pm.PackageManagerServiceUtils", "checkDowngrade", "void", "com.android.server.pm.parsing.pkg.AndroidPackage", "android.content.pm.PackageInfoLite");
            hooks.add(HTF.NOP, "com.android.server.pm.PackageManagerServiceUtils", "checkDowngrade", "void", "com.android.server.pm.pkg.AndroidPackage", "android.content.pm.PackageInfoLite");

            hooks.add(HTF.TRUE, "com.android.server.pm.InstallPackageHelper", "doesSignatureMatchForPermissions", "boolean", "java.lang.String", "com.android.server.pm.parsing.pkg.ParsedPackage", "int");
            hooks.add(HTF.TRUE, "com.android.server.pm.PackageManagerServiceUtils", "isDowngradePermitted", "boolean", "int", "boolean");

            hooks.add(HTF.NOP, "com.android.server.pm.PackageManagerService", "checkDowngrade", "void", "android.content.pm.PackageParser$Package", "android.content.pm.PackageInfoLite");
            hooks.add(HTF.NOP, "com.android.server.pm.PackageManagerService", "checkDowngrade", "void", "com.android.server.pm.parsing.pkg.AndroidPackage", "android.content.pm.PackageInfoLite");

            hooks.add(HTF.NOP, "com.android.server.pm.PackageManagerService", "assertPackageIsValid", "void", "android.content.pm.PackageParser$Package", "android.content.pm.PackageInfoLite");
            hooks.add(HTF.NOP, "com.android.server.pm.InstallPackageHelper", "assertPackageIsValid", "void", "com.android.server.pm.pkg.AndroidPackage", "int", "int");
            hooks.add(HTF.NOP, "com.android.server.pm.InstallPackageHelper", "assertPackageIsValid", "void", "com.android.server.pm.parsing.pkg.AndroidPackage", "int", "int");

            // android oreo
            //hooks.add(HTF.TODO, "com.android.server.pm.PackageManagerService", "scanPackageDirtyLI", "android.content.pm.PackageParser$Package", "android.content.pm.PackageParser$Package", "int", "int", "long", "android.os.UserHandle");
        }
    }
}
