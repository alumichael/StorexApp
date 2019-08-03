package com.mike4christ.storexapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.adapters.ColorAdapter;
import com.mike4christ.storexapp.adapters.ShippingAdapter;
import com.mike4christ.storexapp.models.customer.CartModels.CartList;
import com.mike4christ.storexapp.models.customer.CartModels.ShippingRegion;
import com.mike4christ.storexapp.models.customer.CartModels.ShippingRegionDetail;
import com.mike4christ.storexapp.models.customer.ErrorModel.APIError;
import com.mike4christ.storexapp.models.customer.ErrorModel.ErrorUtils;
import com.mike4christ.storexapp.models.customer.OrderModel.CreateOrder;
import com.mike4christ.storexapp.models.customer.OrderModel.GetOrderAddress;
import com.mike4christ.storexapp.models.customer.OrderModel.GetOrderId;
import com.mike4christ.storexapp.models.customer.OrderModel.PutOrderAddress;
import com.mike4christ.storexapp.models.customer.OrderModel.taxModel;
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
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderAddrFragment extends Fragment implements View.OnClickListener{
    
  
    @BindView(R.id.cart_addr_layout)
    LinearLayout mCartAddrLayout;
    @BindView(R.id.step_view)
    StepView mStepView;
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
    List<ShippingRegionDetail> shippingRegionDetailsList;
    ShippingAdapter shippingAdapter;
    LinearLayoutManager layoutManager;


    ApiInterface client= ServiceGenerator.createService(ApiInterface.class);
    private int currentStep = 0;
    String shippingRegionString;
    List<ShippingRegion> shipping_region;
    ArrayList<String> region_Obj=new ArrayList<>();
    int region_Id;
    Fragment fragment;

    NetworkConnection networkConnection=new NetworkConnection();
    UserPreferences userPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_address_cart, container, false);
        ButterKnife.bind(this,view);
        mStepView.go(currentStep, true);
        userPreferences=new UserPreferences(getContext());

        if(networkConnection.isNetworkConnected(getContext())) {
            init();
            regionSpinner();
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

        return view;
    }

    private  void init(){

        non_empty_layout.setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);
        UserPreferences userPreferences = new UserPreferences(getContext());

        //Temporal save and go to next Operation
        mFirstnameEditxt.setText(userPreferences.getUserFirstname());

        mLastnmaeEditxt.setText(userPreferences.getUserLastname());

        mAddr1Editxt.setText(userPreferences.getUserAddr1());

        mAddr2Editxt.setText(userPreferences.getUserAddr2());

        mCityEditxt.setText(userPreferences.getUserCity());

        region_editxt.setText(userPreferences.getUserRegion());

        mStateEditxt.setText(userPreferences.getUserState());

        mCountryEditxt.setText(userPreferences.getUserCountry());
        mCityEditxt.setText(userPreferences.getUserCity());
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
                .setAdapter(new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        region_Obj));
        ship_region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String regionText = (String) parent.getItemAtPosition(position);
                region_Id=position+1;

               /* //Fetching for Shipping Data
                progressbar.setVisibility(View.VISIBLE);
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
                });

*/


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
                    mAddr2Editxt.getText().toString(),mCityEditxt.getText().toString(),
                    region_editxt.getText().toString(),mZipcodeEditxt.getText().toString(),mCountryEditxt.getText().toString(),
                    region_Id);

            Call<GetOrderAddress> call = client.putOrder_addr(userPreferences.getUserAccessToken(),putOrderAddress);
            call.enqueue(new Callback<GetOrderAddress>() {
                @Override
                public void onResponse(Call<GetOrderAddress> call, Response<GetOrderAddress> response) {

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

                    userPreferences.setUserFirstname(mFirstnameEditxt.getText().toString());
                    userPreferences.setUserLastname(mLastnmaeEditxt.getText().toString());
                    userPreferences.setUserAddr1(response.body().getAddress1());
                    userPreferences.setUserAddr2(response.body().getAddress2());
                    userPreferences.setUserCity(response.body().getCity());
                    userPreferences.setUserRegion(response.body().getRegion());
                    userPreferences.setUserPostalCode(response.body().getPostalCode());
                    userPreferences.setUserCountry(response.body().getCountry());
                    userPreferences.setUserShippingRegionId(response.body().getShippingRegionId());

                }

                @Override
                public void onFailure(Call<GetOrderAddress> call, Throwable t) {
                    showMessage("Fetch failed, please try again " + t.getMessage());
                    Log.i("GEtError", t.getMessage());
                }
            });


           //creating Order
            if(!userPreferences.getUserCartId().equals("")) {
                CreateOrder createOrder=new CreateOrder(userPreferences.getUserCartId(),region_Id,1);

                Call<GetOrderId> call2 = client.order_id(createOrder);
                call2.enqueue(new Callback<GetOrderId>() {
                    @Override
                    public void onResponse(Call<GetOrderId> call, Response<GetOrderId> response) {

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

                        userPreferences.setUserOrderId(String.valueOf(response.body().getOrderId()));

                        progressbar.setVisibility(View.GONE);

                        if (currentStep < mStepView.getStepCount() - 1) {
                            currentStep++;
                            mStepView.go(currentStep, true);

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            ft.replace(R.id.fragment_container, CartPaymentFragment.newInstance(String.valueOf(region_Id)));
                            ft.commit();
                            Log.i("RegionID", String.valueOf(region_Id));
                        } else {
                            mStepView.done(true);
                        }

                    }

                    @Override
                    public void onFailure(Call<GetOrderId> call, Throwable t) {
                        showMessage("Fetch failed, please try again " + t.getMessage());
                        Log.i("GEtError", t.getMessage());
                    }
                });
            }else{
                showMessage("Cart is Empty");
            }



        }
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
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        //ColorAdapter setup
        shippingAdapter=new ShippingAdapter(getContext(),shippingRegionDetailsList);

        layoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recycler_ship_detail.setLayoutManager(layoutManager);
        recycler_ship_detail.setAdapter(shippingAdapter);

    }*/



    public void showMessage(String s) {
        Snackbar.make(mCartAddrLayout, s, Snackbar.LENGTH_LONG).show();
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    @Override
    public void onClick(View view) {

    }
}
