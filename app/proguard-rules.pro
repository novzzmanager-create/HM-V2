-dontoptimize
-dontpreverify
-dontwarn **

-keep class * extends android.app.Activity
-keep class * extends android.app.Service

-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# OBFUSCATE
-repackageclasses ''
-overloadaggressively
-flattenpackagehierarchy ''

-keep class com.cyrust.protection.** { *; }

-keep class com.hypers.hm.adb.AdbPairingClient { *; }
-keep class com.hypers.hm.adb.AdbPairingClient$PairingContext { *; }
-keep class com.hypers.hm.adb.AdbPairingClient$PairingContext$Companion { *; }

-keep class com.hypers.hm.adb.** { *; }
-keep class com.hypers.hm.rish.** { *; }

-keepclasseswithmembernames class * {
    native <methods>;
}
