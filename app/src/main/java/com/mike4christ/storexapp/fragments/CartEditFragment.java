package com.mike4christ.storexapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.adapters.ColorAdapter;
import com.mike4christ.storexapp.adapters.SizeAdapter;
import com.mike4christ.storexapp.models.customer.Attribute;
import com.mike4christ.storexapp.models.customer.CartModels.CartList;
import com.mike4christ.storexapp.models.customer.ErrorModel.APIError;
import com.mike4christ.storexapp.models.customer.ErrorModel.ErrorUtils;
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


public class CartEditFragment extends Fragment {

    private static final String ITEM_ID = "item_id";
    private static final String ITEM_NAME = "item_name";
    private static final String ITEM_COLOR = "item_color";
    private static final String ITEM_SIZE = "item_size";
    private static final String ITEM_PRICE = "item_price";
    private static final String ITEM_QTY = "item_qty";

    @BindView(R.id.edit_cart_layout)
    LinearLayout mEditCartLayout;
    @BindView(R.id.product_thumnail)
    ImageView mProductThumnail;
    @BindView(R.id.product_name)
    TextView mProductName;
    @BindView(R.id.product_price)
    TextView mProductPrice;
    @BindView(R.id.discount_price)
    TextView mDiscountPrice;
    @BindView(R.id.quantity)
    TextView quantity;
    @BindView(R.id.inputLayoutQuanity)
    TextInputLayout inputLayoutQuanity;
    @BindView(R.id.quantity_editxt)
    EditText quantity_editxt;
    @BindView(R.id.recycler_color)
    RecyclerView recycler_color;
    @BindView(R.id.recycler_sizes)
    RecyclerView recycler_sizes;

    @BindView(R.id.progressbar)
    AVLoadingIndicatorView progressbar;
    @BindView(R.id.edit_target_layout)
    LinearLayout edit_target_layout;

    @BindView(R.id.edit_cart_btn)
    Button edit_cart_btn;

    ColorAdapter colorAdapter;
    SizeAdapter sizeAdapter;
    List<Attribute> attriColorlList;
    List<Attribute> attriSizeList;


    LinearLayoutManager linearLayoutManager, linearLayoutManager2;
    String name, price, qty, color, size, id;
    int qty_int;
    UserPreferences userPreferences;

    ApiInterface client = ServiceGenerator.createService(ApiInterface.class);
    NetworkConnection networkConnection = new NetworkConnection();

    public CartEditFragment() {
        // Required empty public constructor
    }

    public static CartEditFragment newInstance(String id, String quantity, String price,
                                               String attriColor, String attriSize, String name) {
        CartEditFragment fragment = new CartEditFragment();
        Bundle args = new Bundle();
        args.putString(ITEM_ID, id);
        args.putString(ITEM_QTY, quantity);
        args.putString(ITEM_PRICE, price);
        args.putString(ITEM_SIZE, attriSize);
        args.putString(ITEM_COLOR, attriColor);
        args.putString(ITEM_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ITEM_ID);
            price = getArguments().getString(ITEM_PRICE);
            size = getArguments().getString(ITEM_SIZE);
            color = getArguments().getString(ITEM_COLOR);
            qty = getArguments().getString(ITEM_QTY);
            name = getArguments().getString(ITEM_NAME);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_cart, container, false);
        userPreferences = new UserPreferences(getContext());
        ButterKnife.bind(this, view);
        if(networkConnection.isNetworkConnected(getContext())) {
            Init();
            edit_cart_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateQuantity();
                }
            });


        }else{
            showMessage("Network Connection failed");
        }


        return view;
    }

    private void Init(){
        edit_target_layout.setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);
        quantity_editxt.setText(qty);
        mDiscountPrice.setText(price);
        quantity.setText("Qty: "+qty);
        mProductName.setText(name);
        getAttributeColor();
        getAttributeSize();

        //ColorAdapter setup
        colorAdapter=new ColorAdapter(getContext(),attriColorlList);

        linearLayoutManager=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recycler_color.setLayoutManager(linearLayoutManager);
        recycler_color.setAdapter(colorAdapter);

        //SizeAdapter Setup
        sizeAdapter=new SizeAdapter(getContext(),attriSizeList);
        linearLayoutManager2=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recycler_sizes.setLayoutManager(linearLayoutManager2);
        recycler_sizes.setAdapter(sizeAdapter);

        edit_target_layout.setVisibility(View.VISIBLE);
        progressbar.setVisibility(View.GONE);



    }

    private void updateQuantity(){
        userPreferences.setColorAtrribute("");
        userPreferences.setSizeAttribute("");

        Boolean isValid=true;
        if(quantity_editxt.getText().toString().isEmpty()){
            inputLayoutQuanity.setError("Invalid entry!");
            showMessage("Invalid entry!");
            isValid=false;

        }
        if(isValid){
            try {
                String qty_input = quantity_editxt.getText().toString();
                int qty_int = Integer.parseInt(qty_input);
            }catch (Exception e){
                showMessage("Invalid entry!");
            }
            String qty_input = quantity_editxt.getText().toString();
            int qty_int = Integer.parseInt(qty_input);

            Call<List<CartList>> call=client.cartedit(Integer.parseInt(id),qty_int);
            call.enqueue(new Callback<List<CartList>>() {
                @Override
                public void onResponse(Call<List<CartList>> call, Response<List<CartList>> response) {
                    if (!response.isSuccessful()) {
                        try {
                            APIError apiError = ErrorUtils.parseError(response);

                            showMessage("Update Failed: " + apiError.getMessage());
                            Log.i("Update Failed", apiError.getMessage());
                            //Log.i("Invalid Entry", response.errorBody().toString());

                        } catch (Exception e) {
                            Log.i("Fetch Failed", e.getMessage());
                            showMessage("Fetch " + " " + e.getMessage());

                        }
                        showMessage("Item Quantity Updated");

                        return;
                    }

                }

                @Override
                public void onFailure(Call<List<CartList>> call, Throwable t) {
                    showMessage("Fetch failed, please try again "+t.getMessage());
                    Log.i("GEtError",t.getMessage());
                }
            });

        }
    }


    private void getAttributeColor(){

        //To create retrofit instance

        Call<List<Attribute>> call=client.attribute(2);
        call.enqueue(new Callback<List<Attribute>>() {
            @Override
            public void onResponse(Call<List<Attribute>> call, Response<List<Attribute>> response) {


                attriColorlList=response.body();
                Log.i("Re-Successattri",attriColorlList.toString());
                Log.i("Re-Successattr", String.valueOf(attriColorlList.size()));
                progressbar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<Attribute>> call, Throwable t) {
                showMessage("Fetch failed, please try again "+t.getMessage());
                Log.i("GEtError",t.getMessage());
            }
        });


    }


    private void getAttributeSize(){


        Call<List<Attribute>> call=client.attribute(1);
        call.enqueue(new Callback<List<Attribute>>() {
            @Override
            public void onResponse(Call<List<Attribute>> call, Response<List<Attribute>> response) {


                attriSizeList=response.body();
                Log.i("Re-Successattri",attriSizeList.toString());
                Log.i("Re-Successattr", String.valueOf(attriSizeList.size()));



            }

            @Override
            public void onFailure(Call<List<Attribute>> call, Throwable t) {
                showMessage("Fetch failed, please try again "+t.getMessage());
                Log.i("GEtError",t.getMessage());
            }
        });


    }



    public void showMessage(String s) {
        Snackbar.make(mEditCartLayout, s, Snackbar.LENGTH_LONG).show();
    }

}
