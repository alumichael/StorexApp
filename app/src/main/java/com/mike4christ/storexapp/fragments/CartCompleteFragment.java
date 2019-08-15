package com.mike4christ.storexapp.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.mike4christ.storexapp.Constant;
import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.actvities.Dashboard;
import com.mike4christ.storexapp.util.UserPreferences;
import com.shuhart.stepview.StepView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CartCompleteFragment extends AppCompatActivity {

    @BindView(R.id.cart_complete_layout)
    LinearLayout mCartAddrLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.step_view)
    StepView mStepView;
    @BindView(R.id.bag_logo)
    ImageView mBagLogo;
    @BindView(R.id.payment_completed_msg)
    TextView mPaymentCompletedMsg;
    @BindView(R.id.order_id)
    TextView mOrderId;
    @BindView(R.id.back_btn)
    Button mBackBtn;
    private int currentStep=2;
    UserPreferences userPreferences;
    String order_id="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_complete_cart);
        ButterKnife.bind(this);
        userPreferences = new UserPreferences(this);

        Intent intent = getIntent();
        order_id = intent.getStringExtra(Constant.ORDER_ID);

        mOrderId.setText(order_id);

        mStepView.go(currentStep, true);
        customizeToolbar(toolbar);

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartCompleteFragment.this, Dashboard.class));
                finish();
            }
        });



    }

    public void customizeToolbar(androidx.appcompat.widget.Toolbar toolbar){

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        //setting Elevation for > API 21
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(10f);
        }
        // Save current title and subtitle
        final CharSequence originalTitle = toolbar.getTitle();

        // Temporarily modify title and subtitle to help detecting each
        toolbar.setTitle("PAYMENT");

        for(int i = 0; i < toolbar.getChildCount(); i++){
            View view = toolbar.getChildAt(i);

            if(view instanceof TextView){
                TextView textView = (TextView) view;


                if(textView.getText().equals("PAYMENT")){
                    // Customize title's TextView
                    androidx.appcompat.widget.Toolbar.LayoutParams params = new androidx.appcompat.widget.Toolbar.LayoutParams(androidx.appcompat.widget.Toolbar.LayoutParams.WRAP_CONTENT, androidx.appcompat.widget.Toolbar.LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.CENTER_HORIZONTAL;
                    textView.setLayoutParams(params);
                    textView.setTextColor(getResources().getColor(R.color.colorPrimary));


                }
            }
        }

        // Restore title and subtitle
        toolbar.setTitle(originalTitle);
    }

    public void showMessage(String s) {
        Toast.makeText(this, s, Snackbar.LENGTH_LONG).show();
    }

}
