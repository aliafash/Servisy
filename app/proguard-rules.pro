# Add project specific ProGuard rules here.
# You can control keep rules to ensure dynamic layouts, SQLite helpers or Retrofit models remain intact.

-keepattributes Signature, InnerClasses, EnclosingMethod
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
