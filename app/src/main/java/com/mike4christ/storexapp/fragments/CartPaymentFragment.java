package com.mike4christ.storexapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.mike4christ.storexapp.R;
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

import butterknife.BindView;
import butterknife.ButterKnife;


public class CartPaymentFragment extends Fragment {
    private static final String REGION_ID = "region_id";

    /** ButterKnife Code **/
    @BindView(R.id.cart_addr_layout)
    LinearLayout mCartAddrLayout;
    @BindView(R.id.step_view)
    StepView mStepView;
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

    private int currentStep=1;


    Fragment fragment;
    ApiInterface client= ServiceGenerator.createService(ApiInterface.class);
    NetworkConnection networkConnection=new NetworkConnection();
    UserPreferences userPreferences;
    String region_id;


    public static CartPaymentFragment newInstance(String region_id) {
        CartPaymentFragment fragment = new CartPaymentFragment();
        Bundle args = new Bundle();
        args.putString(REGION_ID, region_id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            region_id = getArguments().getString(REGION_ID);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_payment_cart, container, false);
        ButterKnife.bind(this,view);
        mStepView.go(currentStep, true);

        Card cardToSave = mCardInputWidget.getCard();
        if (cardToSave == null) {
            showMessage("Invalid Card Data");
           // mErrorDialogHandler.showError("Invalid Card Data");
        }

        // The Card class will normalize the card number
        final Card card = Card.create("4242-4242-4242-4242", 12, 2020, "123");
        if (!card.validateCard()) {
            showMessage("Invalid Card");
        }

        final Stripe stripe = new Stripe(
                getContext(),
                "pk_test_v8bGWjpUZYpi5yV40CJy4tWE00D8mhCFP3"
        );
        stripe.createToken(
                card,
                new TokenCallback() {
                    public void onSuccess(@NonNull Token token) {
                        // Send token to your server

                        showMessage("Token: "+token.toString());

                    }

                    public void onError(@NonNull Exception error) {
                        // Show localized error message
                        showMessage(error.getLocalizedMessage());
                    }
                }
        );

        return  view;
    }

    public void showMessage(String s) {
        Snackbar.make(mCartAddrLayout, s, Snackbar.LENGTH_LONG).show();
    }
}
