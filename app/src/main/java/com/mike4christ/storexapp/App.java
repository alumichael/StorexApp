package com.mike4christ.storexapp;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.mike4christ.storexapp.util.UserPreferences;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class App extends Application {


UserPreferences userPreferences;
    @Override
    public void onCreate() {
        super.onCreate();
        userPreferences=new UserPreferences(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        AppEventsLogger.activateApp(this);




    }



}
