package com.mike4christ.storexapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;
import com.google.android.material.card.MaterialCardView;
import com.mike4christ.storexapp.Constant;
import com.mike4christ.storexapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShopFragment extends Fragment implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener,View.OnClickListener {

    @BindView(R.id.shop_layout)
    FrameLayout mShopLayout;
    @BindView(R.id.promotxt1)
    TextView mPromotxt1;
    @BindView(R.id.promotxt2)
    TextView mPromotxt2;
    @BindView(R.id.shop_txt)
    TextView mShopTxt;
    @BindView(R.id.men_btn)
    MaterialCardView mMenBtn;
    @BindView(R.id.wommen_btn)
    MaterialCardView mWommenBtn;
    @BindView(R.id.shop_tag_btn)
    Button mShopTagBtn;
    @BindView(R.id.slider)
    SliderLayout mSlider;
    Fragment fragment;


    String image1="congo-flower-thumbnail.gif",image2="birds-thumbnail.gif";
    String image3="thrilling-love-thumbnail.gif",image4="mistletoe-thumbnail.gif";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_shop, container, false);
        ButterKnife.bind(this,view);
        setAction();
        //Note that this are temporal slides
        SLide(image1,image2,image3,image4);
        return view;
    }
    private void setAction(){
        mMenBtn.setOnClickListener(this);
        mWommenBtn.setOnClickListener(this);
        mShopTagBtn.setOnClickListener(this);
       // mShopTxt.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.men_btn:
                fragment = new ShopMenFragment();
                showFragment(fragment);
                break;

            case R.id.wommen_btn:
                fragment = new ShopWomenFragment();
                showFragment(fragment);
                break;

            case R.id.shop_tag_btn:
                fragment = new ShopMenFragment();
                showFragment(fragment);
                break;
        }

    }


    @SuppressLint("CheckResult")
    private void SLide(String image1, String image2, String image3,String image4){
        Log.i("SlideImage1",image1);

        ArrayList<String> listUrl = new ArrayList<>();
        ArrayList<String> listName = new ArrayList<>();

        listUrl.add(Constant.IMAGE_BASE_URL+image1);
        listName.add("Congo-flower");

        listUrl.add(Constant.IMAGE_BASE_URL+image2);
        listName.add("Birds Hobby");

        listUrl.add(Constant.IMAGE_BASE_URL+image3);
        listName.add("Thrilling-love");

        listUrl.add(Constant.IMAGE_BASE_URL+image4);
        listName.add("Mistletoe");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.img_empty);
        //.placeholder(R.drawable.placeholder)


        for (int i = 0; i < listUrl.size(); i++) {
            TextSliderView sliderView = new TextSliderView(getContext());
            // initialize SliderLayout
            sliderView
                    .image(listUrl.get(i))
                    .description(listName.get(i))
                    .setRequestOption(requestOptions)
                    .setProgressBarVisible(true)
                    .setOnSliderClickListener(this);

            //add your extra information
            sliderView.bundle(new Bundle());
            sliderView.getBundle().putString("extra", listName.get(i));
            mSlider.addSlider(sliderView);
        }

        // set Slider Transition Animation
        // mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);

        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
        mSlider.addOnPageChangeListener(this);

    }



    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    @Override
    public void onStop() {
        mSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getContext(), slider.getBundle().get("extra") + " Order yours!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
