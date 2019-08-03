package com.mike4christ.storexapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mike4christ.storexapp.R;

import butterknife.BindView;


public class CartCompleteFragment extends Fragment {

    @BindView(R.id.cart_addr_layout)
    LinearLayout mCartAddrLayout;
    @BindView(R.id.step_view)
    com.shuhart.stepview.StepView mStepView;
    @BindView(R.id.bag_logo)
    ImageView mBagLogo;
    @BindView(R.id.payment_completed_msg)
    TextView mPaymentCompletedMsg;
    @BindView(R.id.order_id)
    TextView mOrderId;
    @BindView(R.id.back_btn)
    Button mBackBtn;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_complete_cart, container, false);
    }
}
