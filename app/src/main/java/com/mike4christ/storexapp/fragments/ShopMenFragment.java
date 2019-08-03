package com.mike4christ.storexapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.adapters.ProductAdapter;
import com.mike4christ.storexapp.models.customer.ErrorModel.APIError;
import com.mike4christ.storexapp.models.customer.ErrorModel.ErrorUtils;
import com.mike4christ.storexapp.models.customer.Product.ProductModel;
import com.mike4christ.storexapp.models.customer.Product.ProductModel_Obj;
import com.mike4christ.storexapp.retrofit_interface.ApiInterface;
import com.mike4christ.storexapp.retrofit_interface.ServiceGenerator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.facebook.FacebookSdk.getApplicationContext;


public class ShopMenFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.men_wear_layout)
    LinearLayout mMenWearLayout;
    @BindView(R.id.men_btn)
    LinearLayout mMenBtn;
    @BindView(R.id.all_product_txt)
    TextView mAllProductTxt;
    @BindView(R.id.jean_search_txt)
    TextView mJeanSearchTxt;
    @BindView(R.id.tshirt_search_txt)
    TextView mTshirtSearchTxt;
    @BindView(R.id.shoe_search_txt)
    TextView mShoeSearchTxt;
    @BindView(R.id.accessories_search_txt)
    TextView mAccessoriesSearchTxt;
    @BindView(R.id.recycler_men_wear)
    RecyclerView mRecyclerMenWear;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    ProductAdapter productAdapter;
    LinearLayoutManager layoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_shop_men, container, false);
        ButterKnife.bind(this,view);
        getProduct();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        getProduct();
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        getProduct();
    }



    private void getProduct(){


        //get client and call object for request
        ApiInterface client= ServiceGenerator.createService(ApiInterface.class);
        Call<ProductModel_Obj> call=client.product(2);
        call.enqueue(new Callback<ProductModel_Obj>() {
            @Override
            public void onResponse(Call<ProductModel_Obj> call, Response<ProductModel_Obj> response) {

                if(!response.isSuccessful()){
                    try {
                        APIError apiError = ErrorUtils.parseError(response);

                        showMessage("Fetch Failed: " + apiError.getMessage());
                        Log.i("Invalid Fetch", apiError.getMessage());
                        //Log.i("Invalid Entry", response.errorBody().toString());

                    } catch (Exception e) {
                        Log.i("Fetch Failed", e.getMessage());
                        showMessage("Fetch Failed");

                    }

                    return;
                }
                swipeRefreshLayout.setRefreshing(false);

                List<ProductModel> modelList=response.body().getRows();
                Log.i("Re-Success1",modelList.toString());

                Log.i("Re-SuccessSize", String.valueOf(modelList.size()));


                layoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerMenWear.setLayoutManager(layoutManager);
                productAdapter = new ProductAdapter(getContext(),modelList);
                mRecyclerMenWear.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();

                Log.i("Success",response.body().toString());

                 }

            @Override
            public void onFailure(Call<ProductModel_Obj> call, Throwable t) {
                showMessage("Fetch failed, please try again "+t.getMessage());
                Log.i("GEtError",t.getMessage());
            }
        });


    }

    public void showMessage(String s) {
        Snackbar.make(mMenWearLayout, s, Snackbar.LENGTH_LONG).show();
    }
}
