package com.mike4christ.storexapp;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.mike4christ.storexapp.util.UserPreferences;

@SuppressLint({"Registered"})
public class BaseActivity extends AppCompatActivity {

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionExit();
    }


    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
