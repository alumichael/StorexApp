package com.mike4christ.storexapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.actvities.OrderStatusActivity;
import com.mike4christ.storexapp.actvities.PaymentSettingActivity;
import com.mike4christ.storexapp.actvities.SettingActivity;
import com.mike4christ.storexapp.util.UserPreferences;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AccountFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.account_layout)
    FrameLayout mAccountLayout;
    @BindView(R.id.avi1)
    AVLoadingIndicatorView mAvi1;
    @BindView(R.id.order_count)
    TextView mOrderCount;
    @BindView(R.id.lastOrderdate)
    TextView mLastOrderdate;
    @BindView(R.id.order_id)
    TextView mOrderId;
    @BindView(R.id.order_btn)
    LinearLayout mOrderBtn;
    @BindView(R.id.payment_info_btn)
    MaterialCardView mPersonalInfoBtn;
    @BindView(R.id.setting_btn)
    MaterialCardView mSettingBtn;
    UserPreferences userPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this,view);
        userPreferences=new UserPreferences(getContext());
        String myOrderCount= String.valueOf(userPreferences.getUserOrderSize());
        String lastOrder= String.valueOf(userPreferences.getUserLastOrder());
        String lastOrderDate= String.valueOf(userPreferences.getUserLastOrderId());
        mOrderCount.setText(myOrderCount+" Order in Progress");
        mLastOrderdate.setText("Date: "+lastOrderDate);
        mOrderId.setText("Last Order: "+lastOrder);
        setAction();
        return view;
    }

    private void setAction(){
        mSettingBtn.setOnClickListener(this);
        mPersonalInfoBtn.setOnClickListener(this);
        mOrderBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.payment_info_btn:
                startActivity(new Intent(getContext(), PaymentSettingActivity.class));
                break;
            case R.id.setting_btn:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;

            case R.id.order_btn:
                startActivity(new Intent(getContext(), OrderStatusActivity.class));
                break;
        }
    }
}
