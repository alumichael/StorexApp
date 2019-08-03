package com.mike4christ.storexapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.adapters.CartAdapter;
import com.mike4christ.storexapp.adapters.ProductAdapter;
import com.mike4christ.storexapp.models.customer.CartModels.CartList;
import com.mike4christ.storexapp.models.customer.CartModels.GetTotalAmount;
import com.mike4christ.storexapp.models.customer.ErrorModel.APIError;
import com.mike4christ.storexapp.models.customer.ErrorModel.ErrorUtils;
import com.mike4christ.storexapp.models.customer.Product.ProductModel;
import com.mike4christ.storexapp.models.customer.Product.ProductModel_Obj;
import com.mike4christ.storexapp.retrofit_interface.ApiInterface;
import com.mike4christ.storexapp.retrofit_interface.ServiceGenerator;
import com.mike4christ.storexapp.util.NetworkConnection;
import com.mike4christ.storexapp.util.UserPreferences;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;


public class CartFragment extends Fragment {

    @BindView(R.id.cart_layout)
    LinearLayout mCartLayout;
    @BindView(R.id.cart_empty_layout)
    LinearLayout cart_empty_layout;
    @BindView(R.id.cart_non_empty_Layout)
    LinearLayout cart_non_empty_Layout;
    @BindView(R.id.cart_count)
    TextView mCartCount;
    @BindView(R.id.checkout_btn)
    Button mCheckoutBtn;
    @BindView(R.id.avi1)
    AVLoadingIndicatorView mAvi1;
    @BindView(R.id.recycler_cart)
    RecyclerView mRecyclerCart;
    @BindView(R.id.subtotal_txt)
    TextView mSubtotalTxt;
    @BindView(R.id.shipping_cost_txt)
    TextView mShippingCostTxt;
    @BindView(R.id.total_cost_txt)
    TextView mTotalCostTxt;
    @BindView(R.id.progress_init)
    AVLoadingIndicatorView progressInit;

    ApiInterface client= ServiceGenerator.createService(ApiInterface.class);
    UserPreferences userPreferences;
    int cartListSize;
    CartAdapter cartAdapter;
    LinearLayoutManager layoutManager;
    String subtotalAmount;
    double shipping=0.0;
    double total_amount=0.0;
    String currency="$";
    Fragment fragment;
    NetworkConnection networkConnection = new NetworkConnection();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this,view);
        userPreferences=new UserPreferences(getContext());
        progressInit.setVisibility(View.VISIBLE);
        cart_non_empty_Layout.setVisibility(View.GONE);

        if(networkConnection.isNetworkConnected(getContext())) {
            getCartList();
            mCheckoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkout();
                }
            });

        }else {
            showMessage("No Internet Connection");
        }

        return view;
    }


    private void getCartList(){

        if(!userPreferences.getUserCartId().equals("")) {
            //get client and call object for request
            Call<List<CartList>> call = client.cartlist(userPreferences.getUserCartId());
            call.enqueue(new Callback<List<CartList>>() {
                @Override
                public void onResponse(Call<List<CartList>> call, Response<List<CartList>> response) {

                    if (!response.isSuccessful()) {
                        try {
                            APIError apiError = ErrorUtils.parseError(response);

                            showMessage("Fetch Failed: " + apiError.getMessage());
                            Log.i("Invalid Fetch", apiError.getMessage());
                            //Log.i("Invalid Entry", response.errorBody().toString());

                        } catch (Exception e) {
                            Log.i("Fetch Failed", e.getMessage());
                            showMessage("Fetch " + " " + e.getMessage());

                        }

                        return;
                    }
                    List<CartList> cartList = response.body();
                    cartListSize = cartList.size();
                    userPreferences.setUserCartSize(cartListSize);
                    if (cartListSize > 1) {
                        String item_txt = " items";
                        String cart_countStr = String.valueOf(cartListSize);
                        mCartCount.setText("You have " + cart_countStr +item_txt+" in your\n" +
                                "shopping bag");
                    } else if (cartListSize == 1) {
                        String item_txt = "item";
                        String cart_countStr = String.valueOf(cartListSize);
                        mCartCount.setText("You have " + cart_countStr +item_txt+" in your\n" +
                                "shopping bag");
                    } else {
                        cart_empty_layout.setVisibility(View.VISIBLE);
                        cart_non_empty_Layout.setVisibility(View.GONE);

                    }

                    Log.i("Re-Success1", cartList.toString());
                    Log.i("Re-SuccessSize", String.valueOf(cartList.size()));


                    layoutManager = new LinearLayoutManager(getApplicationContext());
                    mRecyclerCart.setLayoutManager(layoutManager);
                    cartAdapter = new CartAdapter(getContext(), cartList);

                    mRecyclerCart.setAdapter(cartAdapter);
                    cartAdapter.notifyDataSetChanged();
                    //progressbar control
                    progressInit.setVisibility(View.GONE);
                    cart_non_empty_Layout.setVisibility(View.VISIBLE);
                    Log.i("Success", response.body().toString());

                }

                @Override
                public void onFailure(Call<List<CartList>> call, Throwable t) {
                    showMessage("Fetch failed, please try again " + t.getMessage());
                    Log.i("GEtError", t.getMessage());
                }
            });

            //Getting total amount on cart
            //get client and call object for request
            Call<GetTotalAmount> call2 = client.cart_total_amt(userPreferences.getUserCartId());
            call2.enqueue(new Callback<GetTotalAmount>() {
                @Override
                public void onResponse(Call<GetTotalAmount> call, Response<GetTotalAmount> response) {

                    if (!response.isSuccessful()) {
                        try {
                            APIError apiError = ErrorUtils.parseError(response);

                            showMessage("Fetch Failed: " + apiError.getMessage());
                            Log.i("Invalid Fetch", apiError.getMessage());
                            //Log.i("Invalid Entry", response.errorBody().toString());

                        } catch (Exception e) {
                            Log.i("Fetch Failed", e.getMessage());
                            showMessage("Fetch " + " " + e.getMessage());

                        }

                        return;
                    }
                    subtotalAmount = response.body().getTotalAmount();
                    String subtotalAmount_txt=String.valueOf(subtotalAmount);
                    mSubtotalTxt.setText(currency+subtotalAmount_txt);
                    String shipping_txt=String.valueOf(shipping);
                    mShippingCostTxt.setText(currency+shipping_txt);
                    double total_amount= Integer.parseInt(subtotalAmount+shipping);
                    String total_amt_txt=String.valueOf(total_amount);
                    mTotalCostTxt.setText(currency+total_amt_txt);

                }

                @Override
                public void onFailure(Call<GetTotalAmount> call, Throwable t) {
                    showMessage("Fetch failed, please try again " + t.getMessage());
                    Log.i("GEtError", t.getMessage());
                }
            });


        }else {
            showMessage("Cart Empty");
            cart_empty_layout.setVisibility(View.VISIBLE);
            cart_non_empty_Layout.setVisibility(View.GONE);

        }

    }

    private void checkout(){
        mCheckoutBtn.setVisibility(View.GONE);
        mAvi1.setVisibility(View.VISIBLE);
        Call<List<CartList>> call2 = client.empty_cart(userPreferences.getUserCartId());
        call2.enqueue(new Callback<List<CartList>>() {
            @Override
            public void onResponse(Call<List<CartList>> call, Response<List<CartList>> response) {

                if (!response.isSuccessful()) {
                    try {
                        APIError apiError = ErrorUtils.parseError(response);

                        showMessage("Fetch Failed: " + apiError.getMessage());
                        Log.i("Invalid Fetch", apiError.getMessage());
                        //Log.i("Invalid Entry", response.errorBody().toString());

                    } catch (Exception e) {
                        Log.i("Fetch Failed", e.getMessage());
                        showMessage("Fetch " + " " + e.getMessage());

                    }

                    return;
                }
                mCheckoutBtn.setVisibility(View.VISIBLE);
                mAvi1.setVisibility(View.GONE);

                showMessage("Cart Checked Out");

                fragment=new OrderAddrFragment();
                showFragment(fragment);

            }

            @Override
            public void onFailure(Call<List<CartList>> call, Throwable t) {
                showMessage("Fetch failed, please try again " + t.getMessage());
                Log.i("GEtError", t.getMessage());
            }
        });

    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    public void showMessage(String s) {
        Snackbar.make(mCartLayout, s, Snackbar.LENGTH_LONG).show();
    }


}
