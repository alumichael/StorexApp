package com.mike4christ.storexapp.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.mike4christ.storexapp.Constant;
import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.actvities.LoginActivity;
import com.mike4christ.storexapp.actvities.RegistrationActivity;
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


public class CartEditFragment extends AppCompatActivity {


    @BindView(R.id.edit_cart_layout)
    LinearLayout mEditCartLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.product_thumnail)
    ImageView mProductThumnail;
    @BindView(R.id.product_name)
    TextView mProductName;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_cart);
        userPreferences = new UserPreferences(this);
        ButterKnife.bind(this);
        customizeToolbar(toolbar);

        Intent intent = getIntent();
        id=intent.getStringExtra(Constant.ITEM_ID);
        price=intent.getStringExtra(Constant.ITEM_PRICE);
        qty=intent.getStringExtra(Constant.ITEM_QTY);
        name=intent.getStringExtra(Constant.ITEM_NAME);
        color=intent.getStringExtra(Constant.ITEM_COLOR);
        size=intent.getStringExtra(Constant.ITEM_SIZE);

        if(networkConnection.isNetworkConnected(this)) {
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



    }

    public void customizeToolbar(Toolbar toolbar){

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        //setting Elevation for > API 21
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(10f);
        }
        // Save current title and subtitle
        final CharSequence originalTitle = toolbar.getTitle();

        // Temporarily modify title and subtitle to help detecting each
        toolbar.setTitle("ORDER STATUS");

        for(int i = 0; i < toolbar.getChildCount(); i++){
            View view = toolbar.getChildAt(i);

            if(view instanceof TextView){
                TextView textView = (TextView) view;


                if(textView.getText().equals("ORDER STATUS")){
                    // Customize title's TextView
                    Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.CENTER_HORIZONTAL;
                    textView.setLayoutParams(params);
                    textView.setTextColor(getResources().getColor(R.color.colorPrimary));


                }
            }
        }

        // Restore title and subtitle
        toolbar.setTitle(originalTitle);
    }


    private void getAttributeColor(){

        //To create retrofit instance

        Call<List<Attribute>> call=client.attribute(2);
        call.enqueue(new Callback<List<Attribute>>() {
            @Override
            public void onResponse(Call<List<Attribute>> call, Response<List<Attribute>> response) {

               attriColorlList =response.body();
                Log.i("Re-Successattri",attriColorlList.toString());
                Log.i("Re-Successattr", String.valueOf(attriColorlList.size()));
                progressbar.setVisibility(View.GONE);

                //ColorAdapter setup
                colorAdapter=new ColorAdapter(getApplicationContext(),attriColorlList);

                linearLayoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false);
                recycler_color.setLayoutManager(linearLayoutManager);
                recycler_color.setAdapter(colorAdapter);


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

                //SizeAdapter Setup
                sizeAdapter=new SizeAdapter(getApplicationContext(),attriSizeList);
                linearLayoutManager2=new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false);
                recycler_sizes.setLayoutManager(linearLayoutManager2);
                recycler_sizes.setAdapter(sizeAdapter);


            }

            @Override
            public void onFailure(Call<List<Attribute>> call, Throwable t) {
                showMessage("Fetch failed, please try again "+t.getMessage());
                Log.i("GEtError",t.getMessage());
            }
        });


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


                        return;
                    }
                    showMessage("Item Quantity Updated, you can reload cart");

                }

                @Override
                public void onFailure(Call<List<CartList>> call, Throwable t) {
                    showMessage("Fetch failed, please try again "+t.getMessage());
                    Log.i("GEtError",t.getMessage());
                }
            });

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }


    public void showMessage(String s) {
        Snackbar.make(mEditCartLayout, s, Snackbar.LENGTH_LONG).show();
    }



}
