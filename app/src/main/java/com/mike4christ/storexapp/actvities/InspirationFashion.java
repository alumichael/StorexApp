package com.mike4christ.storexapp.actvities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mike4christ.storexapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
/*
 * Hi! here is my Fashion Inspiration Activity
 *
 *
 *
 *
 *
 * */

public class InspirationFashion extends AppCompatActivity {

    @BindView(R.id.fashion_layout)
    LinearLayout mFashionLayout;
    @BindView(R.id.recycler_inspire_fashion)
    RecyclerView mRecyclerInspireFashion;
    @BindView(R.id.read_more_btn)
    Button mReadMoreBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspire_fashion);
        ButterKnife.bind( this);

    }

    private  void init(){

    }

}
