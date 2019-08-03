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
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.mike4christ.storexapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentSettingActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolBar;
    
    @BindView(R.id.payment_setting_layout)
    LinearLayout mPaymentSettingLayout;
    @BindView(R.id.payment_source_spinner)
    Spinner mPaymentSourceSpinner;
    @BindView(R.id.inputLayoutCardNo)
    TextInputLayout mInputLayoutCardNo;
    @BindView(R.id.card_no_editxt)
    EditText mCardNoEditxt;
    @BindView(R.id.inputLayoutExpDate)
    TextInputLayout mInputLayoutExpDate;
    @BindView(R.id.exp_date_editxt)
    EditText mExpDateEditxt;
    @BindView(R.id.inputLayoutCVV_Code)
    TextInputLayout mInputLayoutCVVCode;
    @BindView(R.id.cvveditxt)
    EditText mCvveditxt;
    @BindView(R.id.inputLayoutShipCountry)
    TextInputLayout mInputLayoutShipCountry;
    @BindView(R.id.ship_country_editxt)
    EditText mShipCountryEditxt;
    @BindView(R.id.update_payment_detail_btn)
    Button mUpdatePaymentDetailBtn;


    String modePaymentString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_setting);
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
        toolbar.setTitle("PAYMENT INFORMATION");

        for(int i = 0; i < toolbar.getChildCount(); i++){
            View view = toolbar.getChildAt(i);

            if(view instanceof TextView){
                TextView textView = (TextView) view;


                if(textView.getText().equals("PAYMENT INFORMATION")){
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
