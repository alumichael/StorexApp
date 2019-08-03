package com.mike4christ.storexapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mike4christ.storexapp.R;

import butterknife.BindView;


public class InspirationFragment extends Fragment {


    @BindView(R.id.inspire_layout)
    LinearLayout mInspireLayout;
    @BindView(R.id.life_layout)
    LinearLayout mLifeLayout;
    @BindView(R.id.life_txt)
    TextView mLifeTxt;
    @BindView(R.id.fashion_layout)
    LinearLayout mFashionLayout;
    @BindView(R.id.fashion_txt)
    TextView mFashionTxt;
    @BindView(R.id.video_layout)
    LinearLayout mVideoLayout;
    @BindView(R.id.video_txt)
    TextView mVideoTxt;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inspire, container, false);
    }
}
