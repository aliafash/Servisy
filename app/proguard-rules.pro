# Add project specific ProGuard rules here.
# You can control keep rules to ensure dynamic layouts, SQLite helpers or Retrofit models remain intact.

-keepattributes Signature, InnerClasses, EnclosingMethod
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Keep our data model classes intact to prevent mapping/casting crashes
-keep class com.example.data.** { *; }

# Keep Firebase/Firestore SDK classes
-keep class com.google.firebase.** { *; }
-keep class com.google.firestore.** { *; }
-keep class com.google.android.gms.** { *; }

