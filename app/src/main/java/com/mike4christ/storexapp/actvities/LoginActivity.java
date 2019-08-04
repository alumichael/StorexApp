package com.mike4christ.storexapp.actvities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.mike4christ.storexapp.BaseActivity;
import com.mike4christ.storexapp.BuildConfig;
import com.mike4christ.storexapp.Constant;
import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.models.customer.AppEntryModel.FbTokenData;
import com.mike4christ.storexapp.models.customer.AppEntryModel.LoginGetData;
import com.mike4christ.storexapp.models.customer.AppEntryModel.LoginPostData;
import com.mike4christ.storexapp.models.customer.ErrorModel.APIError;
import com.mike4christ.storexapp.models.customer.ErrorModel.ErrorUtils;
import com.mike4christ.storexapp.retrofit_interface.ApiInterface;
import com.mike4christ.storexapp.retrofit_interface.ServiceGenerator;
import com.mike4christ.storexapp.util.NetworkConnection;
import com.mike4christ.storexapp.util.UserPreferences;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Hi! here my Login Activity Class, Please Note that the
 * Fire Auth is still pending for now
 * as i have requested for app_secret code
 *
 *
 *
 *
 * */


public class LoginActivity extends BaseActivity implements View.OnClickListener{


    @BindView(R.id.email_editxt)
    EditText emailEditxt;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.fblogin_button)
    LoginButton fblogin_button;
    @BindView(R.id.password_editxt)
    EditText passwordEditxt;
    @BindView(R.id.avi1)
    AVLoadingIndicatorView progressView;
    @BindView(R.id.signup_txt)
    TextView txtSignup;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.inputLayoutEmail)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.inputLayoutPassword)
    TextInputLayout inputLayoutPassword;

    @BindView(R.id.login_layout)
    LinearLayout login_layout;
    UserPreferences userPreferences;
    CallbackManager callbackManager;
    String accesstoken;
    AccessToken access;
    NetworkConnection networkConnection=new NetworkConnection();
    ApiInterface client= ServiceGenerator.createService(ApiInterface.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        userPreferences = new UserPreferences(this);

        callbackManager = CallbackManager.Factory.create();

        txtSignup.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        fblogin_button.setOnClickListener(this);
        //applyToolbar("SIGN IN");

        customizeToolbar(toolbar);


       /*if(userPreferences.getUserAccessToken()!=null&&!userPreferences.getUserAccessToken().equals("")) {

            userPreferences.setUserLogged(true);
            startActivity(new Intent(this, Dashboard.class));

        }*/
    }



    public void customizeToolbar(Toolbar toolbar){

        setSupportActionBar(toolbar);
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



    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.signup_txt){
            startActivity(new Intent(this, RegistrationActivity.class));
        }else if(view.getId()==R.id.login_btn){

            Validation();

        }else if(view.getId()==R.id.fblogin_button){

            fblogin_button.setReadPermissions(Arrays.asList("public_profile","email"));

            fblogin_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    if (BuildConfig.DEBUG) {
                        FacebookSdk.setIsDebugEnabled(true);
                        FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
                    }

                    // App code
                    alert();

                   String accessStrg=loginResult.getAccessToken().getToken();
                   // userPreferences.setFacebookToken(accesstoken.toString());
                   // Log.i("AccessToken",access.toString());
                 /*   //FbTokenData fbTokenData=new FbTokenData(accesstoken);
                    //sentNetworkRequestFbLogin(fbTokenData);
//HmacSHA256 implementation
                        String app_secret="5db18069d811865ffca93291f1ec9b27";
                    //$appsecret_proof= hash_hmac('sha256', $access_token, $app_secret);
                    try {
                        String p=hmacSHA256(accessStrg,app_secret);
                        Log.i("AccessTokenP",p);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
*/
                }

                @Override
                public void onCancel() {
                    // App code
                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                    Log.i("FacebookTokenError",exception.getMessage());
                    showMessage(exception.getMessage());


                        try {
                            PackageInfo info = getPackageManager().getPackageInfo("com.mike4christ.storexapp", PackageManager.GET_SIGNATURES);
                            for (Signature signature : info.signatures) {
                                MessageDigest md = MessageDigest.getInstance("SHA");
                                md.update(signature.toByteArray());
                                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            Log.e("KeyHash:", e.toString());
                        } catch (NoSuchAlgorithmException e) {
                            Log.e("KeyHash:", e.toString());
                        }



                }
            });




        }


    }
    private String hmacSHA256(String data,String accessStrg) throws UnsupportedEncodingException {

        SecretKeySpec secretKey = null;
        try {
            secretKey = new SecretKeySpec(accessStrg.getBytes("UTF-8"), "HmacSHA256");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            mac.init(secretKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] hmacData = mac.doFinal(data.getBytes("UTF-8"));

        return  String.format("%064x", new java.math.BigInteger(1, hmacData));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    private  void alert(){
        new AlertDialog.Builder(this)
                .setTitle("Developer Notification")
                .setMessage("Please be aware the facebook auth needs app_secret code on server side not client side\nTherefore you can use" +
                        "the normal Login for now(strictly for app reviewer")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                       showMessage("Thank you");
                    }
                })
                .show();

    }
    private void Validation(){
        Boolean isValid=true;
        if(emailEditxt.getText().toString().isEmpty()){
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
        loginBtn.setVisibility(View.GONE);
        progressView.setVisibility(View.VISIBLE);
        LoginPostData loginPostData=new LoginPostData(
                emailEditxt.getText().toString(),
                passwordEditxt.getText().toString());

        sentNetworkRequestLogin(loginPostData);

    }

    private  void sentNetworkRequestLogin(LoginPostData loginPostData){

        Call<LoginGetData> call=client.login(loginPostData);
        call.enqueue(new Callback<LoginGetData>() {
            @Override
            public void onResponse(Call<LoginGetData> call, Response<LoginGetData> response) {

                if (!response.isSuccessful()) {
                    try {
                        APIError apiError = ErrorUtils.parseError(response);

                        showMessage("Fetch Failed: " + apiError.getMessage());
                        Log.i("Invalid Fetch", apiError.getMessage());
                        //Log.i("Invalid Entry", response.errorBody().toString());
                        loginBtn.setVisibility(View.VISIBLE);
                        progressView.setVisibility(View.GONE);

                    } catch (Exception e) {
                        Log.i("Fetch Failed", e.getMessage());
                        loginBtn.setVisibility(View.VISIBLE);
                        progressView.setVisibility(View.GONE);
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
                showMessage(userPreferences.getUserAccessToken());
                Log.i("AccessTken",userPreferences.getUserAccessToken());

                //userPreferences.setUserLogged(true);
                loginBtn.setVisibility(View.VISIBLE);
                progressView.setVisibility(View.GONE);
                startActivity(new Intent(LoginActivity.this, Dashboard.class));
                finish();

            }

            @Override
            public void onFailure(Call<LoginGetData> call, Throwable t) {
                showMessage("Login Failed "+t.getMessage());
                Log.i("GEtError",t.getMessage());
                loginBtn.setVisibility(View.VISIBLE);
                progressView.setVisibility(View.GONE);
            }
        });



    }

    /*private  void sentNetworkRequestFbLogin(FbTokenData fbTokenData){
        //To create retrofit instance
        Retrofit.Builder builder =new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit=builder.build();

        //get client and call object for request
        ApiInterface client=retrofit.create(ApiInterface.class);
        Call<RegGetData> call=client.fblogin(fbTokenData);
        call.enqueue(new Callback<RegGetData>() {
            @Override
            public void onResponse(Call<RegGetData> call, Response<RegGetData> response) {

                Log.i("ResponseD", response.toString());

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
                showMessage(userPreferences.getUserAccessToken());

                userPreferences.setUserLogged(true);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));

            }

            @Override
            public void onFailure(Call<RegGetData> call, Throwable t) {
                showMessage("Login Failed "+t.getMessage());
                Log.i("GEtError",t.getMessage());
            }
        });



    }
*/


    public void showMessage(String s) {
        Snackbar.make(login_layout, s, Snackbar.LENGTH_LONG).show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
