package com.mike4christ.storexapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.actvities.InspirationFashion;
import com.mike4christ.storexapp.actvities.InspirationLife;
import com.mike4christ.storexapp.actvities.InspirationVideo;

import butterknife.BindView;
import butterknife.ButterKnife;


public class InspirationFragment extends Fragment implements View.OnClickListener{


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

    String lifeStg="LIFE",fashionstg="FASHION",videoStg="VIDEO";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_inspire, container, false);
        ButterKnife.bind(this,view);
        setAction();
        return  view;
    }
    private  void setAction(){
        mLifeLayout.setOnClickListener(this);
        mFashionLayout.setOnClickListener(this);
        mVideoLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fashion_layout:
                startActivity(new Intent(getContext(), InspirationFashion.class));
                break;

            case R.id.life_layout:
                startActivity(new Intent(getContext(), InspirationLife.class));
                break;

            case R.id.video_layout:
                startActivity(new Intent(getContext(), InspirationVideo.class));
                break;
        }

    }
}
