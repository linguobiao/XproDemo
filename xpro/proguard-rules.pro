# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
#-dontoptimize
-dontpreverify

-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

-keep class BASE64Encoder{*;}
-keep class CharacterEncoder{*;}

-dontwarn java.awt.**
-dontwarn javax.**
-keep class java.lang.Throwable.**

-keep class android.support.**{*;}
-dontwarn android.support.**

#--------------- BEGIN: Gson防混淆 ----------
-keep class sun.misc.Unsafe { *; }
-dontnote sun.misc.**
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.**{*;}
-keep class com.google.gson.**
-keep class com.google.gson.internal.UnsafeAllocator.**
-keep class android.os.ServiceManager.**
