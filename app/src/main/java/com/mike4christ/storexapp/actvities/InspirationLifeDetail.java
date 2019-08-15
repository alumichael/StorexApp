package com.mike4christ.storexapp.actvities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mike4christ.storexapp.R;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class InspirationLifeDetail extends AppCompatActivity {


    @BindView(R.id.detail_layout)
    LinearLayout mDetailLayout;
    @BindView(R.id.date_txt)
    TextView mDateTxt;
    @BindView(R.id.avi1)
    AVLoadingIndicatorView mAvi1;
    @BindView(R.id.topic_txt)
    TextView mTopicTxt;
    @BindView(R.id.img_view)
    ImageView mImgView;
    @BindView(R.id.detail_txt)
    TextView mDetailTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspire_life_detal);
        ButterKnife.bind( this);

    }
}
