package com.mike4christ.storexapp.actvities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.google.android.material.textfield.TextInputLayout;
import com.mike4christ.storexapp.R;
import com.stripe.android.view.CardInputWidget;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentSettingActivity extends AppCompatActivity {

    @BindView(R.id.payment_setting_layout)
    LinearLayout paymentSettingLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
   /* @BindView(R.id.card_input_widget)
    CardInputWidget cardInputWidget;*/
    @BindView(R.id.local_design_layout)
    LinearLayout localDesignLayout;
    @BindView(R.id.paypal_btn)
    ImageView paypalBtn;
    @BindView(R.id.visa_btn)
    ImageView visaBtn;
    @BindView(R.id.inputLayoutCardNo)
    TextInputLayout inputLayoutCardNo;
    @BindView(R.id.card_no_editxt)
    EditText cardNoEditxt;
    @BindView(R.id.inputLayoutExpDate)
    TextInputLayout inputLayoutExpDate;
    @BindView(R.id.exp_date_editxt)
    EditText expDateEditxt;
    @BindView(R.id.inputLayoutCVV_Code)
    TextInputLayout inputLayoutCVVCode;
    @BindView(R.id.cvveditxt)
    EditText cvveditxt;
    @BindView(R.id.inputLayoutShipCountry)
    TextInputLayout inputLayoutShipCountry;
    @BindView(R.id.ship_country_editxt)
    EditText shipCountryEditxt;
    @BindView(R.id.avi1)
    com.wang.avi.AVLoadingIndicatorView avi1;
    @BindView(R.id.update_payment_detail_btn)
    Button updatePaymentDetailBtn;
  


    String modePaymentString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_setting);
        ButterKnife.bind( this);
        customizeToolbar(toolbar);
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
