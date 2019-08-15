package com.mike4christ.storexapp.actvities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.models.customer.CartModels.ShippingRegion;
import com.mike4christ.storexapp.models.customer.ErrorModel.APIError;
import com.mike4christ.storexapp.models.customer.ErrorModel.ErrorUtils;
import com.mike4christ.storexapp.models.customer.OrderModel.GetOrderAddress;
import com.mike4christ.storexapp.models.customer.OrderModel.PutOrderAddress;
import com.mike4christ.storexapp.retrofit_interface.ApiInterface;
import com.mike4christ.storexapp.retrofit_interface.ServiceGenerator;
import com.mike4christ.storexapp.util.NetworkConnection;
import com.mike4christ.storexapp.util.UserPreferences;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.account_setting_layout)
    LinearLayout accountSettingLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.avi1)
    AVLoadingIndicatorView avi1;
    @BindView(R.id.non_empty_layout)
    LinearLayout nonEmptyLayout;
    @BindView(R.id.inputLayoutFirstName)
    TextInputLayout inputLayoutFirstName;
    @BindView(R.id.firstname_editxt)
    EditText firstnameEditxt;
    @BindView(R.id.inputLayoutLastName)
    TextInputLayout inputLayoutLastName;
    @BindView(R.id.lastnmae_editxt)
    EditText lastnmaeEditxt;
    @BindView(R.id.inputLayoutLastAddr1)
    TextInputLayout inputLayoutLastAddr1;
    @BindView(R.id.addr1_editxt)
    EditText addr1Editxt;
    @BindView(R.id.inputLayoutLastAddr2)
    TextInputLayout inputLayoutLastAddr2;
    @BindView(R.id.addr2_editxt)
    EditText addr2Editxt;
    @BindView(R.id.inputLayoutLastRegion)
    TextInputLayout inputLayoutLastRegion;
    @BindView(R.id.region_editxt)
    EditText regionEditxt;
    @BindView(R.id.inputLayoutCity)
    TextInputLayout inputLayoutCity;
    @BindView(R.id.city_editxt)
    EditText cityEditxt;
    @BindView(R.id.inputLayoutState)
    TextInputLayout inputLayoutState;
    @BindView(R.id.state_editxt)
    EditText stateEditxt;
    @BindView(R.id.inputLayoutZipCode)
    TextInputLayout inputLayoutZipCode;
    @BindView(R.id.zipcode_editxt)
    EditText zipcodeEditxt;
    @BindView(R.id.inputLayoutCountry)
    TextInputLayout inputLayoutCountry;
    @BindView(R.id.country_editxt)
    EditText countryEditxt;
    @BindView(R.id.ship_region)
    Spinner shipRegion;
    @BindView(R.id.update_profile_btn)
    Button updateProfileBtn;
    
    UserPreferences userPreferences;
    NetworkConnection networkConnection=new NetworkConnection();
    ApiInterface client= ServiceGenerator.createService(ApiInterface.class);
    String shippingRegionString;
    List<ShippingRegion> shipping_region;
    ArrayList<String> region_Obj=new ArrayList<>();
    int region_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        userPreferences=new UserPreferences(this);
        ButterKnife.bind( this);
        customizeToolbar(toolbar);

        if(networkConnection.isNetworkConnected(this)) {
            init();

            updateProfileBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    avi1.setVisibility(View.VISIBLE);
                    validateUserInputs();
                }
            });


        }else{
            showMessage("No Internet Connection");
        }

    }

    private  void init(){
        nonEmptyLayout.setVisibility(View.GONE);
        avi1.setVisibility(View.VISIBLE);
        UserPreferences userPreferences = new UserPreferences(this);

        //Temporal save and go to next Operation
        firstnameEditxt.setText(userPreferences.getUserFirstname());

        lastnmaeEditxt.setText(userPreferences.getUserLastname());

        addr1Editxt.setText(userPreferences.getUserAddr1());

        addr2Editxt.setText(userPreferences.getUserAddr2());

        cityEditxt.setText(userPreferences.getUserCity());

        regionEditxt.setText(userPreferences.getUserRegion());

        stateEditxt.setText(userPreferences.getUserState());

        countryEditxt.setText(userPreferences.getUserCountry());

        zipcodeEditxt.setText(userPreferences.getUserPostalCode());


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
                nonEmptyLayout.setVisibility(View.VISIBLE);
                avi1.setVisibility(View.GONE);

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
        shipRegion
                .setAdapter(new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_dropdown_item,
                        region_Obj));
        shipRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String regionText = (String) parent.getItemAtPosition(position);
                region_Id=position+1;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                shipRegion.getItemAtPosition(0);


            }
        });

    }


    private void validateUserInputs() {


        boolean isValid = true;

        if (firstnameEditxt.getText().toString().isEmpty()) {
            inputLayoutFirstName.setError("Your FirstName is required!");
            isValid = false;
        } else if (lastnmaeEditxt.getText().toString().isEmpty()) {
            inputLayoutLastName.setError("Your LastName is required!");
            isValid = false;
        } else if (addr1Editxt.getText().toString().isEmpty()) {
            inputLayoutLastAddr1.setError("Your Address is required!");
            isValid = false;
        } else if (cityEditxt.getText().toString().isEmpty()) {
            inputLayoutCity.setError("Your City is required!");
            isValid = false;
        } else if (countryEditxt.getText().toString().isEmpty()) {
            inputLayoutCountry.setError("Your Country is required!");
            isValid = false;
        }
        else if (stateEditxt.getText().toString().isEmpty()) {
            inputLayoutState.setError("Your State is required!");
            isValid = false;
        }
        else if (zipcodeEditxt.getText().toString().isEmpty()) {
            inputLayoutZipCode.setError("Your Zip Code is required!");
            isValid = false;
        }else {
            inputLayoutFirstName.setErrorEnabled(false);
            inputLayoutLastName.setErrorEnabled(false);
            inputLayoutLastAddr1.setErrorEnabled(false);
            inputLayoutCity.setErrorEnabled(false);
            inputLayoutCountry.setErrorEnabled(false);
            inputLayoutState.setErrorEnabled(false);
            inputLayoutZipCode.setErrorEnabled(false);
        }


        //Region Spinner
        shippingRegionString = shipRegion.getSelectedItem().toString();
        if (shippingRegionString.equals("Please Select")) {
            showMessage("Select Region");
            isValid = false;
        }


        if (isValid) {
            // Update address

            userPreferences.setUserFirstname(firstnameEditxt.getText().toString());
            userPreferences.setUserLastname(lastnmaeEditxt.getText().toString());
            userPreferences.setUserAddr1(addr1Editxt.getText().toString());
            userPreferences.setUserAddr2(addr2Editxt.getText().toString());
            userPreferences.setUserCity(cityEditxt.getText().toString());
            userPreferences.setUserRegion(regionEditxt.getText().toString());
            userPreferences.setUserPostalCode(zipcodeEditxt.getText().toString());
            userPreferences.setUserCountry(countryEditxt.getText().toString());
            userPreferences.setUserShippingRegionId(region_Id);


            PutOrderAddress putOrderAddress=new PutOrderAddress(addr1Editxt.getText().toString(),
                    addr2Editxt.getText().toString(),cityEditxt.getText().toString(),regionEditxt.getText().toString(),
                    zipcodeEditxt.getText().toString(),cityEditxt.getText().toString(),region_Id);

            String AccTk=userPreferences.getUserAccessToken();
            Log.i("TokenTemp",AccTk);
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
                            avi1.setVisibility(View.GONE);
                        } catch (Exception e) {
                            Log.i("Fetch Failed", e.getMessage());
                            showMessage("Fetch " + " " + e.getMessage());
                            avi1.setVisibility(View.GONE);

                        }

                        return;
                    }

                    userPreferences.setUserFirstname(firstnameEditxt.getText().toString());
                    userPreferences.setUserLastname(lastnmaeEditxt.getText().toString());
                    userPreferences.setUserAddr1(response.body().getAddress1());
                    userPreferences.setUserAddr2(response.body().getAddress2());
                    userPreferences.setUserCity(response.body().getCity());
                    userPreferences.setUserRegion(response.body().getRegion());
                    userPreferences.setUserPostalCode(response.body().getPostalCode());
                    userPreferences.setUserCountry(response.body().getCountry());
                    userPreferences.setUserShippingRegionId(response.body().getShippingRegionId());
                    avi1.setVisibility(View.GONE);
                    showMessage("Information saved");

                }

                @Override
                public void onFailure(Call<GetOrderAddress> call, Throwable t) {
                    showMessage("Fetch failed, please try again " + t.getMessage());
                    avi1.setVisibility(View.GONE);
                    Log.i("GEtError", t.getMessage());
                }
            });


        }
    }

    public void showMessage(String s) {
        Snackbar.make(accountSettingLayout, s, Snackbar.LENGTH_LONG).show();
    }

    public void customizeToolbar(Toolbar toolbar){

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
       // getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        //setting Elevation for > API 21
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(10f);
        }
        // Save current title and subtitle
        final CharSequence originalTitle = toolbar.getTitle();

        // Temporarily modify title and subtitle to help detecting each
        toolbar.setTitle("SETTINGS");

        for(int i = 0; i < toolbar.getChildCount(); i++){
            View view = toolbar.getChildAt(i);

            if(view instanceof TextView){
                TextView textView = (TextView) view;


                if(textView.getText().equals("SETTINGS")){
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

    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this, Dashboard.class));
            return super.onOptionsItemSelected(item);


    }


    public void onBackPressed() {
        startActivity(new Intent(this, Dashboard.class));
        super.onBackPressed();
    }
}
