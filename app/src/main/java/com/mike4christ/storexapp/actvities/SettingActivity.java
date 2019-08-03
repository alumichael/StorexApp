package com.mike4christ.storexapp.actvities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.mike4christ.storexapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolBar;
    @BindView(R.id.account_setting_layout)
    LinearLayout mAccountSettingLayout;
    @BindView(R.id.inputLayoutFirstName)
    TextInputLayout mInputLayoutFirstName;
    @BindView(R.id.firstname_editxt)
    EditText mFirstnameEditxt;
    @BindView(R.id.inputLayoutLastName)
    TextInputLayout mInputLayoutLastName;
    @BindView(R.id.lastnmae_editxt)
    EditText mLastnmaeEditxt;
    @BindView(R.id.inputLayoutLastAddr1)
    TextInputLayout mInputLayoutLastAddr1;
    @BindView(R.id.addr1_editxt)
    EditText mAddr1Editxt;
    @BindView(R.id.inputLayoutLastAddr2)
    TextInputLayout mInputLayoutLastAddr2;
    @BindView(R.id.addr2_editxt)
    EditText mAddr2Editxt;
    @BindView(R.id.inputLayoutCity)
    TextInputLayout mInputLayoutCity;
    @BindView(R.id.city_editxt)
    EditText mCityEditxt;
    @BindView(R.id.inputLayoutState)
    TextInputLayout mInputLayoutState;
    @BindView(R.id.state_editxt)
    EditText mStateEditxt;
    @BindView(R.id.inputLayoutZipCode)
    TextInputLayout mInputLayoutZipCode;
    @BindView(R.id.zipcode_editxt)
    EditText mZipcodeEditxt;
    @BindView(R.id.inputLayoutCountry)
    TextInputLayout mInputLayoutCountry;
    @BindView(R.id.country_editxt)
    EditText mCountryEditxt;
    @BindView(R.id.update_profile_btn)
    Button mUpdateProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind( this);
        customizeToolbar(toolBar);
    }

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
        toolbar.setTitle("SETTINGS");

        for(int i = 0; i < toolbar.getChildCount(); i++){
            View view = toolbar.getChildAt(i);

            if(view instanceof TextView){
                TextView textView = (TextView) view;


                if(textView.getText().equals("SETTINGS")){
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

    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this, Dashboard.class));
            return super.onOptionsItemSelected(item);


    }


    public void onBackPressed() {
        startActivity(new Intent(this, Dashboard.class));
        super.onBackPressed();
    }
}
