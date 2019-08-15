package com.mike4christ.storexapp.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.mike4christ.storexapp.Constant;
import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.actvities.OrderDetail;
import com.mike4christ.storexapp.models.customer.CartModels.CartList;
import com.mike4christ.storexapp.models.customer.CartModels.ShippingRegion;
import com.mike4christ.storexapp.models.customer.ErrorModel.APIError;
import com.mike4christ.storexapp.models.customer.ErrorModel.ErrorUtils;
import com.mike4christ.storexapp.models.customer.OrderModel.CreateOrder;
import com.mike4christ.storexapp.models.customer.OrderModel.GetOrderAddress;
import com.mike4christ.storexapp.models.customer.OrderModel.GetOrderId;
import com.mike4christ.storexapp.models.customer.OrderModel.PutOrderAddress;
import com.mike4christ.storexapp.retrofit_interface.ApiInterface;
import com.mike4christ.storexapp.retrofit_interface.ServiceGenerator;
import com.mike4christ.storexapp.util.NetworkConnection;
import com.mike4christ.storexapp.util.UserPreferences;
import com.shuhart.stepview.StepView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderAddrFragment extends AppCompatActivity {
    
  
    @BindView(R.id.cart_addr_layout)
    LinearLayout mCartAddrLayout;
    @BindView(R.id.step_view)
    StepView mStepView;
    @BindView(R.id.toolbar)
    Toolbar toolBar;
    @BindView(R.id.inputLayoutFirstName)
    TextInputLayout mInputLayoutFirstName;
    @BindView(R.id.firstname_editxt)
    EditText mFirstnameEditxt;
    @BindView(R.id.inputLayoutLastName)
    TextInputLayout mInputLayoutLastName;
    @BindView(R.id.lastnmae_editxt)
    EditText mLastnmaeEditxt;
    @BindView(R.id.inputLayoutLastAddr1)
    TextInputLayout mInputLayoutLastAddr1;
    @BindView(R.id.addr1_editxt)
    EditText mAddr1Editxt;
    @BindView(R.id.inputLayoutLastAddr2)
    TextInputLayout mInputLayoutLastAddr2;
    @BindView(R.id.addr2_editxt)
    EditText mAddr2Editxt;
    @BindView(R.id.inputLayoutCity)
    TextInputLayout mInputLayoutCity;
    @BindView(R.id.city_editxt)
    EditText mCityEditxt;
    @BindView(R.id.inputLayoutLastRegion)
    TextInputLayout inputLayoutLastRegion;
    @BindView(R.id.region_editxt)
    EditText region_editxt;
    @BindView(R.id.inputLayoutState)
    TextInputLayout mInputLayoutState;
    @BindView(R.id.state_editxt)
    EditText mStateEditxt;
    @BindView(R.id.inputLayoutZipCode)
    TextInputLayout mInputLayoutZipCode;
    @BindView(R.id.zipcode_editxt)
    EditText mZipcodeEditxt;
    @BindView(R.id.inputLayoutCountry)
    TextInputLayout mInputLayoutCountry;
    @BindView(R.id.country_editxt)
    EditText mCountryEditxt;
    @BindView(R.id.update_profile_btn)
    Button mUpdateProfileBtn;
    @BindView(R.id.ship_region)
    Spinner ship_region;
    @BindView(R.id.avi1)
    AVLoadingIndicatorView progressbar;
    @BindView(R.id.non_empty_layout)
    LinearLayout non_empty_layout;
  /*  List<ShippingRegionDetail> shippingRegionDetailsList;
    LinearLayoutManager layoutManager;*/



    private int currentStep = 0;
    String shippingRegionString;
    List<ShippingRegion> shipping_region;
    ArrayList<String> region_Obj=new ArrayList<>();
    int region_Id;
    Fragment fragment;

    NetworkConnection networkConnection=new NetworkConnection();
    UserPreferences userPreferences;
    ApiInterface client= ServiceGenerator.createService(ApiInterface.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_address_cart);
        ButterKnife.bind(this);
        customizeToolbar(toolBar);
        mStepView.go(currentStep, true);
        userPreferences=new UserPreferences(this);

        if(networkConnection.isNetworkConnected(this)) {
            init();

            mUpdateProfileBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressbar.setVisibility(View.VISIBLE);
                    validateUserInputs();

                }
            });


        }else{
            showMessage("No Internet Connection");
        }

    }

    public void customizeToolbar(androidx.appcompat.widget.Toolbar toolbar){

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
                    androidx.appcompat.widget.Toolbar.LayoutParams params = new androidx.appcompat.widget.Toolbar.LayoutParams(androidx.appcompat.widget.Toolbar.LayoutParams.WRAP_CONTENT, androidx.appcompat.widget.Toolbar.LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.CENTER_HORIZONTAL;
                    textView.setLayoutParams(params);
                    textView.setTextColor(getResources().getColor(R.color.colorPrimary));


                }
            }
        }

        // Restore title and subtitle
        toolbar.setTitle(originalTitle);
    }


    private  void init(){

        non_empty_layout.setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);
        UserPreferences userPreferences = new UserPreferences(this);

        //Temporal save and go to next Operation
        mFirstnameEditxt.setText(userPreferences.getUserFirstname());

        mLastnmaeEditxt.setText(userPreferences.getUserLastname());

        mAddr1Editxt.setText(userPreferences.getUserAddr1());

        mAddr2Editxt.setText(userPreferences.getUserAddr2());

        mCityEditxt.setText(userPreferences.getUserCity());

        region_editxt.setText(userPreferences.getUserRegion());

        mStateEditxt.setText(userPreferences.getUserState());

        mCountryEditxt.setText(userPreferences.getUserCountry());

        mZipcodeEditxt.setText(userPreferences.getUserPostalCode());


        Call<List<ShippingRegion>> call = client.regions();
        call.enqueue(new Callback<List<ShippingRegion>>() {
            @Override
            public void onResponse(Call<List<ShippingRegion>> call, Response<List<ShippingRegion>> response) {

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

                shipping_region=response.body();
                for(int i=0; i<shipping_region.size();i++){
                    region_Obj.add(shipping_region.get(i).getShippingRegion());
                }
                regionSpinner();
                non_empty_layout.setVisibility(View.VISIBLE);
                progressbar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<ShippingRegion>> call, Throwable t) {
                showMessage("Fetch failed, please try again " + t.getMessage());
                Log.i("GEtError", t.getMessage());
            }
        });

    }

    private void regionSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner
        ship_region
                .setAdapter(new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_dropdown_item,
                        region_Obj));
        ship_region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String regionText = (String) parent.getItemAtPosition(position);
                region_Id=position+1;

                Log.i("RegionP", String.valueOf(region_Id));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ship_region.getItemAtPosition(0);


            }
        });

    }



    private void validateUserInputs() {


        boolean isValid = true;

        if (mFirstnameEditxt.getText().toString().isEmpty()) {
            mInputLayoutFirstName.setError("Your FirstName is required!");
            isValid = false;
        } else if (mLastnmaeEditxt.getText().toString().isEmpty()) {
            mInputLayoutLastName.setError("Your LastName is required!");
            isValid = false;
        } else if (mAddr1Editxt.getText().toString().isEmpty()) {
            mInputLayoutLastAddr1.setError("Your Address is required!");
            isValid = false;
        } else if (mCityEditxt.getText().toString().isEmpty()) {
            mInputLayoutCity.setError("Your City is required!");
            isValid = false;
        } else if (mCountryEditxt.getText().toString().isEmpty()) {
            mInputLayoutCountry.setError("Your Country is required!");
            isValid = false;
        }
        else if (mStateEditxt.getText().toString().isEmpty()) {
            mInputLayoutState.setError("Your State is required!");
            isValid = false;
        }
        else if (mZipcodeEditxt.getText().toString().isEmpty()) {
            mInputLayoutZipCode.setError("Your Zip Code is required!");
            isValid = false;
        }else {
            mInputLayoutFirstName.setErrorEnabled(false);
            mInputLayoutLastName.setErrorEnabled(false);
            mInputLayoutLastAddr1.setErrorEnabled(false);
            mInputLayoutCity.setErrorEnabled(false);
            mInputLayoutCountry.setErrorEnabled(false);
            mInputLayoutState.setErrorEnabled(false);
            mInputLayoutZipCode.setErrorEnabled(false);
        }


        //Region Spinner
        shippingRegionString = ship_region.getSelectedItem().toString();
        if (shippingRegionString.equals("Please Select")) {
            showMessage("Select Region");
            isValid = false;
        }

        if (isValid) {
            // Update address

            PutOrderAddress putOrderAddress=new PutOrderAddress(mAddr1Editxt.getText().toString(),
                    mAddr2Editxt.getText().toString(),mCityEditxt.getText().toString(),region_editxt.getText().toString(),
                    mZipcodeEditxt.getText().toString(),mCountryEditxt.getText().toString(),region_Id);

            Call<GetOrderAddress> call = client.putOrder_addr(userPreferences.getUserAccessToken(),putOrderAddress);
            call.enqueue(new Callback<GetOrderAddress>() {
                @Override
                public void onResponse(Call<GetOrderAddress> call, Response<GetOrderAddress> response) {

                    if (!response.isSuccessful()) {
                        try {
                            APIError apiError = ErrorUtils.parseError(response);

                            showMessage("Fetch Failed: " + apiError.getMessage());
                            Log.i("Invalid Fetch", apiError.getMessage());
                            progressbar.setVisibility(View.GONE);

                        } catch (Exception e) {
                            Log.i("Fetch Failed", e.getMessage());
                            showMessage("Fetch " + " " + e.getMessage());
                            progressbar.setVisibility(View.GONE);

                        }

                        return;
                    }

                    userPreferences.setUserFirstname(mFirstnameEditxt.getText().toString());
                    userPreferences.setUserLastname(mLastnmaeEditxt.getText().toString());
                    userPreferences.setUserAddr1(response.body().getAddress1());
                    userPreferences.setUserAddr2(response.body().getAddress2());
                    userPreferences.setUserCity(response.body().getCity());
                    userPreferences.setUserRegion(response.body().getRegion());
                    userPreferences.setUserPostalCode(response.body().getPostalCode());
                    userPreferences.setUserCountry(response.body().getCountry());
                    userPreferences.setUserShippingRegionId(response.body().getShippingRegionId());
                    generateOrderId();

                }

                @Override
                public void onFailure(Call<GetOrderAddress> call, Throwable t) {
                    showMessage("Fetch failed, please try again " + t.getMessage());
                    progressbar.setVisibility(View.GONE);
                    Log.i("GEtErrorPut", t.getMessage());
                }
            });


            }

        }

        private void generateOrderId(){
            //creating Order
            if(!userPreferences.getUserCartId().equals("")) {
                CreateOrder createOrder = new CreateOrder(userPreferences.getUserCartId(), region_Id, 1);
                Log.i("CartI",userPreferences.getUserCartId());

                Call<GetOrderId> call = client.order_id(userPreferences.getUserAccessToken(), createOrder);
                call.enqueue(new Callback<GetOrderId>() {
                    @Override
                    public void onResponse(Call<GetOrderId> call, Response<GetOrderId> response) {

                        if (!response.isSuccessful()) {
                            try {
                                APIError apiError = ErrorUtils.parseError(response);

                                showMessage("Fetch Failed: " + apiError.getMessage());
                                Log.i("Invalid Fetch", apiError.getMessage());
                                //Log.i("Invalid Entry", response.errorBody().toString());
                                progressbar.setVisibility(View.GONE);

                            } catch (Exception e) {
                                Log.i("Fetch Failed", e.getMessage());
                                showMessage("Fetch " + " " + e.getMessage());
                                progressbar.setVisibility(View.GONE);

                            }

                            return;
                        }

                        emptyCart();

                        String OrderId=String.valueOf(response.body().getOrderId());
                        userPreferences.setUserOrderId(OrderId);
                        Log.i("OrderId",OrderId);

                        progressbar.setVisibility(View.GONE);

                        if (currentStep < mStepView.getStepCount() - 1) {
                            currentStep++;
                            mStepView.go(currentStep, true);

                            Intent i = new Intent(OrderAddrFragment.this, CartPaymentFragment.class);
                            i.putExtra("REGION_ID", String.valueOf(region_Id));
                            startActivity(i);


                            Log.i("RegionID", String.valueOf(region_Id));
                        } else {
                            mStepView.done(true);
                        }

                    }

                    @Override
                    public void onFailure(Call<GetOrderId> call, Throwable t) {
                        showMessage("Fetch failed, please try again " + ""+t.getMessage());
                        showMessage("GEtCartId"+userPreferences.getUserCartId());
                        progressbar.setVisibility(View.GONE);
                        Log.i("GEtError", t.getMessage());
                    }
                });
            }else {
                showMessage("Cart Empty");
            }

        }


        private  void emptyCart(){

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

                    showMessage("Cart Checked Out");
                    userPreferences.setUserCartId("");
                    userPreferences.setUserCartSize(0);

                }

                @Override
                public void onFailure(Call<List<CartList>> call, Throwable t) {
                    showMessage("Fetch failed, please try again " + t.getMessage());
                    Log.i("GEtError", t.getMessage());
                }
            });
        }


     /*progressbar.setVisibility(View.VISIBLE);
            Call<List<ShippingRegionDetail>> call = client.shipping_detail(region_Id);
            call.enqueue(new Callback<List<ShippingRegionDetail>>() {
                @Override
                public void onResponse(Call<List<ShippingRegionDetail>> call, Response<List<ShippingRegionDetail>> response) {

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

                    shippingRegionDetailsList=response.body();
                    progressbar.setVisibility(View.GONE);


                }

                @Override
                public void onFailure(Call<List<ShippingRegionDetail>> call, Throwable t) {
                    showMessage("Fetch failed, please try again " + t.getMessage());
                    Log.i("GEtError", t.getMessage());
                }
            });*/


    /*@OnClick(R.id.update_profile_btn)
    public void updateAddr(){
        validateUserInputs();

        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_ship_info, null);
        final RecyclerView recycler_ship_detail = (RecyclerView) view.findViewById(R.id.recycler_ship_detail);
        BottomSheetDialog dialog = new BottomSheetDialog(this());
        //ColorAdapter setup
        shippingAdapter=new ShippingAdapter(this(),shippingRegionDetailsList);

        layoutManager=new LinearLayoutManager(this(),RecyclerView.VERTICAL,false);
        recycler_ship_detail.setLayoutManager(layoutManager);
        recycler_ship_detail.setAdapter(shippingAdapter);

    }*/



    public void showMessage(String s) {
        Toast.makeText(this, s, Snackbar.LENGTH_LONG).show();
    }


}
