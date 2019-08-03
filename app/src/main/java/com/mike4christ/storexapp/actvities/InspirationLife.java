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


public class InspirationLife extends AppCompatActivity {


    @BindView(R.id.inspire_life_layout)
    LinearLayout mInspireLifeLayout;
    @BindView(R.id.recycler_inspire_life)
    RecyclerView mRecyclerInspireLife;
    @BindView(R.id.read_more_btn)
    Button mReadMoreBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspire_life);
        ButterKnife.bind( this);

    }
}
