package com.mike4christ.storexapp.actvities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.adapters.OrderAdapter;
import com.mike4christ.storexapp.adapters.ProductAdapter;
import com.mike4christ.storexapp.models.customer.ErrorModel.APIError;
import com.mike4christ.storexapp.models.customer.ErrorModel.ErrorUtils;
import com.mike4christ.storexapp.models.customer.OrderModel.GetOrderList;
import com.mike4christ.storexapp.models.customer.Product.ProductModel;
import com.mike4christ.storexapp.models.customer.Product.ProductModel_Obj;
import com.mike4christ.storexapp.retrofit_interface.ApiInterface;
import com.mike4christ.storexapp.retrofit_interface.ServiceGenerator;
import com.mike4christ.storexapp.util.UserPreferences;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class OrderStatusActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolBar;

    @BindView(R.id.order_status_layout)
    LinearLayout mOrderStatusLayout;
    @BindView(R.id.recycler_order)
    RecyclerView mRecyclerOrder;
    UserPreferences userPreferences;
    LinearLayoutManager layoutManager;
    OrderAdapter orderAdapter;
    ApiInterface client= ServiceGenerator.createService(ApiInterface.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder_status);
        userPreferences=new UserPreferences(this);
        ButterKnife.bind( this);
        getOderList();
        customizeToolbar(toolBar);
    }

    private void getOderList(){

        //get client and call object for request

        Call<List<GetOrderList>> call=client.getOrder_info(userPreferences.getUserAccessToken());
        call.enqueue(new Callback<List<GetOrderList>>() {
            @Override
            public void onResponse(Call<List<GetOrderList>> call, Response<List<GetOrderList>> response) {

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

                List<GetOrderList> orderList=response.body();
                int ordersize=orderList.size();
                userPreferences.setUserOrderSize(ordersize);
                Log.i("Re-Success1",orderList.toString());

                Log.i("Re-SuccessSize", String.valueOf(orderList.size()));


                layoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerOrder.setLayoutManager(layoutManager);
                orderAdapter = new OrderAdapter(getBaseContext(),orderList);
                mRecyclerOrder.setAdapter(orderAdapter);
                orderAdapter.notifyDataSetChanged();

                Log.i("Success",response.body().toString());

            }

            @Override
            public void onFailure(Call<List<GetOrderList>> call, Throwable t) {
                showMessage("Fetch failed, please try again "+t.getMessage());
                Log.i("GEtError",t.getMessage());
            }
        });


    }

    public void customizeToolbar(Toolbar toolbar){

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
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

    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this, Dashboard.class));
            return super.onOptionsItemSelected(item);


    }


    public void onBackPressed() {
        startActivity(new Intent(this, Dashboard.class));
        super.onBackPressed();
    }

    public void showMessage(String s) {
        Snackbar.make(mOrderStatusLayout, s, Snackbar.LENGTH_LONG).show();
    }
}
