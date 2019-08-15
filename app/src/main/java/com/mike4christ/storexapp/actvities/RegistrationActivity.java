package com.mike4christ.storexapp.actvities;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.mike4christ.storexapp.BaseActivity;
import com.mike4christ.storexapp.Constant;
import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.models.customer.AppEntryModel.RegGetData;
import com.mike4christ.storexapp.models.customer.AppEntryModel.RegPostData;
import com.mike4christ.storexapp.models.customer.ErrorModel.APIError;
import com.mike4christ.storexapp.models.customer.ErrorModel.ErrorUtils;
import com.mike4christ.storexapp.retrofit_interface.ApiInterface;
import com.mike4christ.storexapp.retrofit_interface.ServiceGenerator;
import com.mike4christ.storexapp.util.NetworkConnection;
import com.mike4christ.storexapp.util.UserPreferences;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Hi! here is my User registration class defined for user registration on the
 * platform
 *
 *
 *
 *
 *
 * */

public class RegistrationActivity extends BaseActivity implements View.OnClickListener{


    @BindView(R.id.email_editxt)
    EditText emailEditxt;
    @BindView(R.id.signup_btn)
    Button signup_btn;
    @BindView(R.id.password_editxt)
    EditText passwordEditxt;

    @BindView(R.id.name_editxt)
    EditText name_editxt;


    @BindView(R.id.avi1)
    AVLoadingIndicatorView progressView;
    @BindView(R.id.signin_txt)
    TextView txtSignin;

    @BindView(R.id.signup_layout)
    LinearLayout signup_layout;

    @BindView(R.id.inputLayoutName)
    TextInputLayout inputLayoutName;
    @BindView(R.id.inputLayoutEmail)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.inputLayoutPassword)
    TextInputLayout inputLayoutPassword;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    NetworkConnection networkConnection=new NetworkConnection();
    UserPreferences userPreferences;
    ApiInterface client= ServiceGenerator.createService(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        userPreferences=new UserPreferences(this);

        txtSignin.setOnClickListener(this);
        signup_btn.setOnClickListener(this);

        customizeToolbar(toolbar);
    }

    public void customizeToolbar(Toolbar toolbar){

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        //setting Elevation for > API 21
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(10f);
        }
        // Save current title and subtitle
        final CharSequence originalTitle = toolbar.getTitle();

        // Temporarily modify title and subtitle to help detecting each
        toolbar.setTitle("SIGN IN");

        for(int i = 0; i < toolbar.getChildCount(); i++){
            View view = toolbar.getChildAt(i);

            if(view instanceof TextView){
                TextView textView = (TextView) view;


                if(textView.getText().equals("SIGN IN")){
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


    //startActivity(new Intent(this, Dashboard.class));


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.signup_btn){
            Validation();
        }else if(view.getId()==R.id.signin_txt){
            startActivity(new Intent(this, LoginActivity.class));

        }


    }

    private void Validation(){
       Boolean isValid=true;
       if(name_editxt.getText().toString().isEmpty()){
           inputLayoutName.setError("Invalid Name entry!");
           showMessage("Invalid Name entry!");
           isValid=false;
       }else if(emailEditxt.getText().toString().isEmpty()){
           inputLayoutEmail.setError("Invalid mail address entry!");
           showMessage("Invalid mail address entry!");
           isValid=false;

       }else if(passwordEditxt.getText().toString().isEmpty()){

           inputLayoutPassword.setError("Invalid Password entry!");
           showMessage("Invalid Password entry!");
           isValid=false;

       }else if(passwordEditxt.getText().length()<6){

           inputLayoutPassword.setError("Minimum of 6 Characters of Password");
           showMessage("Minimum of 6 Characters of Password");
           isValid=false;

       }else{
           inputLayoutName.setErrorEnabled(false);
           inputLayoutEmail.setErrorEnabled(false);
           inputLayoutPassword.setErrorEnabled(false);
       }

       if(isValid){
           if(networkConnection.isNetworkConnected(this)) {
               sendData();
           }else{
               showMessage("Please Check your Internet connection");
           }
       }


    }



    private void sendData(){
        signup_btn.setVisibility(View.GONE);
        progressView.setVisibility(View.VISIBLE);
        RegPostData regPostData=new RegPostData(name_editxt.getText().toString(),
                emailEditxt.getText().toString(),
                passwordEditxt.getText().toString());

        sentNetworkRequest(regPostData);

    }

    private  void sentNetworkRequest(RegPostData regPostData){

        Call<RegGetData> call=client.register(regPostData);
        call.enqueue(new Callback<RegGetData>() {
            @Override
            public void onResponse(Call<RegGetData> call, Response<RegGetData> response) {

                if (!response.isSuccessful()) {
                    try {
                        APIError apiError = ErrorUtils.parseError(response);

                        showMessage("Registration failed");
                        Log.i("Invalid Fetch", apiError.getMessage());
                        //Log.i("Invalid Entry", response.errorBody().toString());

                    } catch (Exception e) {
                        Log.i("Fetch Failed", e.getMessage());
                        showMessage("Fetch " + " " + e.getMessage());

                    }

                    return;
                }


                userPreferences.setCustomerId(response.body().getCustomer().getCustomerId());
                userPreferences.setUserName(response.body().getCustomer().getName());
                userPreferences.setUserEmail(response.body().getCustomer().getEmail());
                userPreferences.setUserAddr1(response.body().getCustomer().getAddress1());
                userPreferences.setUserAddr2(response.body().getCustomer().getAddress2());
                userPreferences.setUserCity(response.body().getCustomer().getCity());
                userPreferences.setUserRegion(response.body().getCustomer().getRegion());
                userPreferences.setUserPostalCode(response.body().getCustomer().getPostalCode());
                userPreferences.setUserShippingRegionId(response.body().getCustomer().getShippingRegionId());
                userPreferences.setUserDayPhone(response.body().getCustomer().getDayPhone());
                userPreferences.setUserEvePhone(response.body().getCustomer().getEvePhone());
                userPreferences.setUserMobPhone(response.body().getCustomer().getMobPhone());
                userPreferences.setUserAccessToken(response.body().getAccessToken());
                userPreferences.setUserExpiresIn(response.body().getExpiresIn());
               // showMessage(userPreferences.getUserAccessToken());

                //userPreferences.setUserLogged(true);
                signup_btn.setVisibility(View.VISIBLE);
                progressView.setVisibility(View.GONE);
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();

            }

            @Override
            public void onFailure(Call<RegGetData> call, Throwable t) {
                showMessage("Registration Failed "+t.getMessage());
                Log.i("GEtError",t.getMessage());
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
        return super.onOptionsItemSelected(item);
    }

    public void showMessage(String s) {
        Snackbar.make(signup_layout, s, Snackbar.LENGTH_LONG).show();
    }
}
