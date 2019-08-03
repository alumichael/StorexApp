package com.mike4christ.storexapp.actvities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.mike4christ.storexapp.Constant;
import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.adapters.ColorAdapter;
import com.mike4christ.storexapp.adapters.SizeAdapter;
import com.mike4christ.storexapp.models.customer.Attribute;
import com.mike4christ.storexapp.models.customer.CartModels.AddCartResponse;
import com.mike4christ.storexapp.models.customer.CartModels.AddtoCart;
import com.mike4christ.storexapp.models.customer.CartModels.CartId;
import com.mike4christ.storexapp.models.customer.ErrorModel.APIError;
import com.mike4christ.storexapp.models.customer.ErrorModel.ErrorUtils;
import com.mike4christ.storexapp.models.customer.OrderModel.Order_Detail;
import com.mike4christ.storexapp.models.customer.Product.ProductDetailModel;
import com.mike4christ.storexapp.retrofit_interface.ApiInterface;
import com.mike4christ.storexapp.retrofit_interface.ServiceGenerator;
import com.mike4christ.storexapp.util.NetworkConnection;
import com.mike4christ.storexapp.util.UserPreferences;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OrderDetail extends AppCompatActivity{

    @BindView(R.id.order_detail_layout)
    LinearLayout orderDetailLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.avi1)
    AVLoadingIndicatorView avi1;
    @BindView(R.id.detail_layout)
    LinearLayout detailLayout;
    @BindView(R.id.oder_id_txt)
    TextView oderIdTxt;
    @BindView(R.id.quantity_id_txt)
    TextView quantity_id_txt;
    @BindView(R.id.product_id_txt)
    TextView productIdTxt;
    @BindView(R.id.attributes_txt)
    TextView attributesTxt;
    @BindView(R.id.product_name_txt)
    TextView productNameTxt;
    @BindView(R.id.unit_cost_txt)
    TextView unitCostTxt;
    @BindView(R.id.subtotal_txt)
    TextView subtotalTxt;

    String id="",cart_id;
    String currency="$";
    UserPreferences userPreferences;

    ApiInterface client= ServiceGenerator.createService(ApiInterface.class);
    NetworkConnection networkConnection=new NetworkConnection();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind( this);
        userPreferences=new UserPreferences(this);
        customizeToolbar(toolbar);
        Intent intent = getIntent();
        id=intent.getStringExtra(Constant.ORDER_ID);

        if(networkConnection.isNetworkConnected(this)) {
            avi1.setVisibility(View.VISIBLE);
            detailLayout.setVisibility(View.GONE);
            init();
        }else{
            showMessage("Network Connection failed");
        }

    }

    private void init(){

        getOrderDetail();
        avi1.setVisibility(View.GONE);
        detailLayout.setVisibility(View.VISIBLE);

    }


    private void getOrderDetail(){

        Call<List<Order_Detail>> call=client.getOrder_detail(userPreferences.getUserAccessToken(),Integer.parseInt(id));
        call.enqueue(new Callback<List<Order_Detail>>() {
            @Override
            public void onResponse(Call<List<Order_Detail>> call, Response<List<Order_Detail>> response) {


                List<Order_Detail> modelList=response.body();
                Log.i("Re-Success1",modelList.toString());
                Log.i("Re-SuccessSize", String.valueOf(modelList.size()));


                String order_idtxt= "Order ID: "+modelList.get(0).getOrderId();
                String product_idtxt= "Product ID: "+modelList.get(0).getProductId();
                String attribute= "Attribute : "+modelList.get(0).getAttributes();
                String product_name= "Product Name: "+modelList.get(0).getProductName();
                String unit_cost= "Unit Cost: "+modelList.get(0).getUnitCost();
                String subtotal= "Sub Total: "+modelList.get(0).getSubtotal();
                String quantity= "Quantity: "+modelList.get(0).getQuantity();


                oderIdTxt.setText(order_idtxt);
                productIdTxt.setText(product_idtxt);
                attributesTxt.setText(attribute);
                productNameTxt.setText(product_name);
                unitCostTxt.setText(unit_cost);
                subtotalTxt.setText(subtotal);
                quantity_id_txt.setText(quantity);


            }

            @Override
            public void onFailure(Call<List<Order_Detail>> call, Throwable t) {
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


    public void showMessage(String s) {
        Snackbar.make(detailLayout, s, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }


}
