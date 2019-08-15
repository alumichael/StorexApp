package com.mike4christ.storexapp.actvities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;


import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.fragments.AccountFragment;
import com.mike4christ.storexapp.fragments.CartFragment;
import com.mike4christ.storexapp.fragments.InspirationFragment;
import com.mike4christ.storexapp.fragments.MoreFragment;
import com.mike4christ.storexapp.fragments.ShopFragment;
import com.mike4christ.storexapp.fragments.StoreFragment;
import com.mike4christ.storexapp.util.UserPreferences;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


/*
 * Hi! here my Dashboard Activity Class, it the Main Board functions for
 * the Whole mobile operations
 * Functions Like Shopping,Inspiration,Store,Cart and many more...
 * It Implements View.OnClickListener which is used for the triggering several operation
 * as described above
 *
 *
 *
 *
 * */

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

//Defining widget and binding to
// view using ButterKnife Library
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content_dash_layout)
    LinearLayout mContentDashLayout;
    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    @BindView(R.id.shop_btn_lay)
    LinearLayout mShopBtnLay;
    @BindView(R.id.inspire_btn_lay)
    LinearLayout mInspireBtnLay;
    @BindView(R.id.cart_btn_lay)
    LinearLayout mCartBtnLay;
    @BindView(R.id.image_btn_layout)
    ImageButton mImageBtnLayout;
    @BindView(R.id.count_tv_layout)
    TextView mCountTvLayout;
    @BindView(R.id.store_btn_lay)
    LinearLayout mStoreBtnLay;
    @BindView(R.id.more_btn_lay)
    LinearLayout mMoreBtnLay;
    String cart_count;

    UserPreferences userPreferences;

    Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        userPreferences=new UserPreferences(this);
        cart_count= String.valueOf(userPreferences.getUserCartSize());
        mCountTvLayout.setText(cart_count);
        customizeToolbar(toolbar);
        setClick();
        fragment = new ShopFragment();
        showFragment(fragment);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }
    private  void setClick(){
        //Onclick Method initiated
        mShopBtnLay.setOnClickListener(this);
        mInspireBtnLay.setOnClickListener(this);
        mCartBtnLay.setOnClickListener(this);
        mStoreBtnLay.setOnClickListener(this);
        mMoreBtnLay.setOnClickListener(this);
    }


    //customizing the toolbar to fit middle of appbar
    public void customizeToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        //setting Elevation for > API 21
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(10f);
        }
        // Save current title and subtitle
        final CharSequence originalTitle = toolbar.getTitle();

        // Temporarily modify title and subtitle to help detecting each
        toolbar.setTitle("storex");

        for(int i = 0; i < toolbar.getChildCount(); i++){
            View view = toolbar.getChildAt(i);

            if(view instanceof TextView){
                TextView textView = (TextView) view;


                if(textView.getText().equals("storex")){
                    // Customize title's TextView
                    Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.CENTER_HORIZONTAL;
                    textView.setLayoutParams(params);
                    textView.setTextColor(getResources().getColor(R.color.colorPrimary));


                }
            }
        }

        // Restore title and subtitle
        toolbar.setTitle(originalTitle);
    }

    //Method to set fragment immediately Onclick
    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }


    //Overriding Back Press button to close an already open Navigation
    //View
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(Dashboard.this,SettingActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_shop) {
            //Shopping Fragment Onclick
            fragment = new ShopFragment();
            showFragment(fragment);

        } else if (id == R.id.cart) {
            //Cart Fragment Onclick
            fragment = new CartFragment();
            showFragment(fragment);

        } else if (id == R.id.nav_inspire) {
            //Inspiration Fragment Onclick
            fragment = new InspirationFragment();
            showFragment(fragment);

        } else if (id == R.id.nav_store) {
            //Store Fragment Onclick
            startActivity(new Intent(Dashboard.this,StoreActivity.class));

        } else if (id == R.id.nav_share) {
            //Share Mobile App Onclick

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");

            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Storex Mobile");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "Pick your wears!");

            startActivity(Intent.createChooser(sharingIntent, "Share via"));


        } else if (id == R.id.nav_account) {
            //User Account Fragment Onclick
            fragment = new AccountFragment();
            showFragment(fragment);


        } else if (id == R.id.nav_cutomer_suppot) {
            CustomerSupportAlert();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shop_btn_lay:
                fragment = new ShopFragment();
                showFragment(fragment);
                break;
            case R.id.inspire_btn_lay:
                fragment = new InspirationFragment();
                showFragment(fragment);
                break;

            case R.id.cart_btn_lay:
                fragment = new CartFragment();
                showFragment(fragment);
                break;

            case R.id.store_btn_lay:
                startActivity(new Intent(Dashboard.this,StoreActivity.class));
                break;

            case R.id.more_btn_lay:
                fragment = new MoreFragment();
                showFragment(fragment);
                break;

        }
    }


    private  void CustomerSupportAlert(){


        new AlertDialog.Builder(this)
                .setTitle("Customer Support")
                .setMessage("Hi "+userPreferences.getUserName()+"\n"+"You can always contact us through 010000000")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                })
                .show();

    }
}
