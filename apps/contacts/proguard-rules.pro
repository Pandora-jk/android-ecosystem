# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.

# Keep Room entities
-keep class com.jimknopf.contacts.data.model.** { *; }

# Keep Kotlin metadata
-keepAttributes *Annotation*
-keepclassmembers class * {
    @org.jetbrains.annotations.NotNull *;
}
