package com.mike4christ.storexapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
import com.mike4christ.storexapp.R;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;


public class AccountFragment extends Fragment {


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
    @BindView(R.id.personal_info_btn)
    MaterialCardView mPersonalInfoBtn;
    @BindView(R.id.setting_btn)
    MaterialCardView mSettingBtn;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }
}
