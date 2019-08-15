package com.mike4christ.storexapp.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.mike4christ.storexapp.Constant;
import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.models.customer.CartModels.CartList;
import com.mike4christ.storexapp.models.customer.ErrorModel.APIError;
import com.mike4christ.storexapp.models.customer.ErrorModel.ErrorUtils;
import com.mike4christ.storexapp.models.customer.StripePayment.SendpaymentData;
import com.mike4christ.storexapp.retrofit_interface.ApiInterface;
import com.mike4christ.storexapp.retrofit_interface.ServiceGenerator;
import com.mike4christ.storexapp.util.NetworkConnection;
import com.mike4christ.storexapp.util.UserPreferences;
import com.shuhart.stepview.StepView;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;
import com.wang.avi.AVLoadingIndicatorView;

import org.xml.sax.ErrorHandler;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartPaymentFragment extends AppCompatActivity {


    @BindView(R.id.cart_payment_layout)
    LinearLayout mCartPaymentLayout;
    @BindView(R.id.step_view)
    StepView mStepView;
    @BindView(R.id.toolbar)
    Toolbar toolBar;
    @BindView(R.id.paypal_btn)
    ImageView mPaypalBtn;
    @BindView(R.id.visa_btn)
    ImageView mVisaBtn;
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
    @BindView(R.id.avi1)
    AVLoadingIndicatorView mAvi1;
    @BindView(R.id.pay_now_btn)
    Button mPayNowBtn;
    @BindView(R.id.card_input_widget)
    CardInputWidget mCardInputWidget;
    //ErrorDialogHandler mErrorDialogHandler;
    Fragment fragment;

    private int currentStep=1;

    ApiInterface client= ServiceGenerator.createService(ApiInterface.class);
    NetworkConnection networkConnection=new NetworkConnection();
    UserPreferences userPreferences;
    String region_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_payment_cart);
        ButterKnife.bind(this);
        userPreferences=new UserPreferences(this);
        customizeToolbar(toolBar);

        Intent intent = getIntent();
        region_id = intent.getStringExtra("REGION_ID");

        mStepView.go(currentStep, true);
        mPayNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isValid = true;
                if (mShipCountryEditxt.getText().toString().isEmpty()) {
                    mInputLayoutShipCountry.setError("Invalid entry!");
                    showMessage("Invalid Shipping Description entry!");
                    isValid = false;

                }
                if (isValid) {


                    if (networkConnection.isNetworkConnected(getBaseContext())) {
                        Card cardToSave = mCardInputWidget.getCard();
                        if (cardToSave == null) {
                            showMessage("Invalid Card Data");
                            // mErrorDialogHandler.showError("Invalid Card Data");
                        }

                        // The Card class will normalize the card number
                        final Card card = Card.create("4242-4242-4242-4242", 12, 2020, "123");
                        if (!card.validateCard()) {
                            showMessage("Invalid Card");
                            return;
                        }

                        final Stripe stripe = new Stripe(
                                getBaseContext(),
                                "pk_test_v8bGWjpUZYpi5yV40CJy4tWE00D8mhCFP3"
                        );
                        stripe.createToken(
                                card,
                                new TokenCallback() {
                                    public void onSuccess(@NonNull Token token) {
                                        // Send token to your server


                                        showMessage("Token: " + token.toString());
                                        Log.i("TokenStrripe", token.toString());

                                        Log.i("OrderIddd", userPreferences.getUserOrderId());
                                        Log.i("OrderAmount", userPreferences.getTotalAmount());
                                        int amount = Math.round(Float.parseFloat(userPreferences.getTotalAmount()));

                                        SendpaymentData sendpaymentData = new SendpaymentData(token.getId(), Integer.parseInt(userPreferences.getUserOrderId()),
                                                mShipCountryEditxt.getText().toString(), amount, "USD");

                                        Call<ResponseBody> call = client.stripe_response(sendpaymentData);
                                        call.enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                                if (!response.isSuccessful()) {
                                                    try {
                                                        APIError apiError = ErrorUtils.parseError(response);

                                                        showMessage("Fetch Failed: " + apiError.getMessage());
                                                        Log.i("Invalid Fetch", apiError.getMessage());
                                                        //Log.i("Invalid Entry", response.errorBody().toString());

                                                    } catch (Exception e) {
                                                        Log.i("Fetch Failed", e.getMessage());
                                                        showMessage("Fetch " + " " + e.getMessage());

                                                    }

                                                    return;
                                                }


                                                showMessage("Ordered Placed");
                                                Intent i = new Intent(CartPaymentFragment.this, CartCompleteFragment.class);
                                                i.putExtra(Constant.ORDER_ID, userPreferences.getUserOrderId());
                                                startActivity(i);
                                                finish();


                                            }

                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                showMessage("Fetch failed, please try again " + t.getMessage());
                                                Log.i("GEtError", t.getMessage());
                                            }
                                        });

                                    }

                                    public void onError(@NonNull Exception error) {
                                        // Show localized error message
                                        showMessage(error.getLocalizedMessage());
                                    }
                                }
                        );

                    }
                }else{
                    showMessage("No Internet Connection");
                }
            }
        });



    }

    //Method to set fragment immediately Onclick
    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
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
