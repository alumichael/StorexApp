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
import com.mike4christ.storexapp.adapters.ProductAdapter;
import com.mike4christ.storexapp.adapters.SizeAdapter;
import com.mike4christ.storexapp.models.customer.Attribute;
import com.mike4christ.storexapp.models.customer.CartModels.AddCartResponse;
import com.mike4christ.storexapp.models.customer.CartModels.AddtoCart;
import com.mike4christ.storexapp.models.customer.CartModels.CartId;
import com.mike4christ.storexapp.models.customer.ErrorModel.APIError;
import com.mike4christ.storexapp.models.customer.ErrorModel.ErrorUtils;
import com.mike4christ.storexapp.models.customer.Product.ProductDetailModel;
import com.mike4christ.storexapp.models.customer.Product.ProductModel;
import com.mike4christ.storexapp.models.customer.Product.ProductModel_Obj;
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

import static com.facebook.FacebookSdk.getApplicationContext;


public class ProductDetail extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener,View.OnClickListener{

    @BindView(R.id.produt_detail_layout)
    LinearLayout mProdutDetailLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.product_name_txt)
    TextView mProductNameTxt;
    @BindView(R.id.slider)
    SliderLayout mSlider;
    @BindView(R.id.detail_txt)
    TextView mDetailTxt;
    @BindView(R.id.product_price)
    TextView mProductPrice;
    @BindView(R.id.discount_price)
    TextView mDiscountPrice;
    @BindView(R.id.addto_cart_btn)
    Button mAddtoCartBtn;

    @BindView(R.id.avi1)
    AVLoadingIndicatorView progressbar;
    @BindView(R.id.detail_layout)
    LinearLayout detail_layout;


    String image1="",image2="";
    String id="",cart_id;
    ColorAdapter colorAdapter;
    SizeAdapter sizeAdapter;
    List<Attribute> attriColorlList;
    List<Attribute> attriSizeList;
    String attribute,currency="$";
    LinearLayoutManager linearLayoutManager,linearLayoutManager2;
    UserPreferences userPreferences;

    ApiInterface client= ServiceGenerator.createService(ApiInterface.class);
    NetworkConnection networkConnection=new NetworkConnection();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind( this);
        userPreferences=new UserPreferences(this);
        customizeToolbar(mToolbar);
        Intent intent = getIntent();
        id=intent.getStringExtra(Constant.PRODUCT_ID);

        if(networkConnection.isNetworkConnected(this)) {
            progressbar.setVisibility(View.VISIBLE);
            detail_layout.setVisibility(View.GONE);
            init();
        }else{
            showMessage("Network Connection failed");
        }

    }

    private void init(){

        getProduct();
        setActions();
        getAttributeColor();
        getAttributeSize();
        progressbar.setVisibility(View.GONE);
        detail_layout.setVisibility(View.VISIBLE);

    }

    private void setActions(){
        mAddtoCartBtn.setOnClickListener(this);
    }


    private void getProduct(){

        //To create retrofit instance
        Retrofit.Builder builder =new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit=builder.build();

        //get client and call object for request
        ApiInterface client=retrofit.create(ApiInterface.class);
        Call<List<ProductDetailModel>> call=client.product_detail(Integer.parseInt(id));
        call.enqueue(new Callback<List<ProductDetailModel>>() {
            @Override
            public void onResponse(Call<List<ProductDetailModel>> call, Response<List<ProductDetailModel>> response) {


                List<ProductDetailModel> modelList=response.body();
                Log.i("Re-Success1",modelList.toString());
                Log.i("Re-SuccessSize", String.valueOf(modelList.size()));
                mProductNameTxt.setText(modelList.get(0).getName());
                mProductPrice.setText(currency+modelList.get(0).getPrice());
                mDetailTxt.setText(modelList.get(0).getDescription());
                mDiscountPrice.setText(currency+modelList.get(0).getDiscountedPrice());
                SLide(modelList.get(0).getImage(),modelList.get(0).getImage2());


            }

            @Override
            public void onFailure(Call<List<ProductDetailModel>> call, Throwable t) {
                showMessage("Fetch failed, please try again "+t.getMessage());
                Log.i("GEtError",t.getMessage());
            }
        });


    }


    @SuppressLint("CheckResult")
    private void SLide(String imagevalue1, String imagevalue2){

        progressbar.setVisibility(View.VISIBLE);
        Log.i("SlideImage1",image1);

        ArrayList<String> listUrl = new ArrayList<>();
        ArrayList<String> listName = new ArrayList<>();

        listUrl.add(Constant.IMAGE_BASE_URL+imagevalue1);
        listName.add("Image 1");


        listUrl.add(Constant.IMAGE_BASE_URL+imagevalue2);
        listName.add("Image 2");
        Log.i("SlideImage2",image2);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.fitCenter()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .error(R.drawable.no_image_available);
        //.placeholder(R.drawable.placeholder)


        for (int i = 0; i < listUrl.size(); i++) {
            TextSliderView sliderView = new TextSliderView(this);
            // initialize SliderLayout
            sliderView
                    .image(listUrl.get(i))
                    .description(listName.get(i))
                    .setRequestOption(requestOptions)
                    .setProgressBarVisible(true)
                    .setOnSliderClickListener(this);

            //add your extra information
            sliderView.bundle(new Bundle());
            sliderView.getBundle().putString("extra", listName.get(i));
            mSlider.addSlider(sliderView);
        }

        // set Slider Transition Animation
        // mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        progressbar.setVisibility(View.GONE);
        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);

        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
        mSlider.addOnPageChangeListener(this);

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
        Snackbar.make(mProdutDetailLayout, s, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void getAttributeColor(){

        progressbar.setVisibility(View.VISIBLE);
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


    @OnClick(R.id.customize_btn)
    public void showBottom() {
        View view = getLayoutInflater().inflate(R.layout.fragment_cutomize_product, null);
        final RecyclerView recycler_color = (RecyclerView) view.findViewById(R.id.recycler_color);
        final RecyclerView recycler_size = (RecyclerView) view.findViewById(R.id.recycler_sizes);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        //ColorAdapter setup
        colorAdapter=new ColorAdapter(this,attriColorlList);

        linearLayoutManager=new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        recycler_color.setLayoutManager(linearLayoutManager);
        recycler_color.setAdapter(colorAdapter);

        //SizeAdapter Setup
        sizeAdapter=new SizeAdapter(this,attriSizeList);
        linearLayoutManager2=new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        recycler_size.setLayoutManager(linearLayoutManager2);
        recycler_size.setAdapter(sizeAdapter);
        dialog.setContentView(view);
        dialog.show();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.addto_cart_btn:
                addtoCart();
                break;


        }

    }

    private void addtoCart(){
        progressbar.setVisibility(View.VISIBLE);
        //get client and call object for request
       if(userPreferences.getUserCartId().equals("")){
           getCarId();
       }

        //Add to Cart
        //Combine attribute
        if(!userPreferences.getSizeAttribute().equals("") && !userPreferences.getColorAtrribute().equals("")){
            attribute=userPreferences.getSizeAttribute()+","+userPreferences.getColorAtrribute();
            userPreferences.setSizeAttribute("");
            userPreferences.setColorAtrribute("");
        }else{
            showMessage("Please pick Color and Size");
            return;
        }

        AddtoCart addtoCart=new AddtoCart(userPreferences.getUserCartId(),Integer.parseInt(id),attribute);
        Call<List<AddCartResponse>> call=client.addCartResponse(addtoCart);

        call.enqueue(new Callback<List<AddCartResponse>>() {
            @Override
            public void onResponse(Call<List<AddCartResponse>> call, Response<List<AddCartResponse>> response) {

                if(!response.isSuccessful()){

                        APIError apiError = ErrorUtils.parseError(response);
                        showMessage("Fetch to add to cart "+""+apiError.getMessage());

                    return;
                }
                  int len=response.body().size();
                 String name=response.body().get(len-1).getName();
                showMessage("Item is saved to Cart(Bag)");
                Log.i("ProductName",name);

                progressbar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<AddCartResponse>> call, Throwable t) {
                showMessage("Failed to Add to Cart "+t.getMessage());
                Log.i("GEtError",t.getMessage());
            }
        });

    }

    private void getCarId(){

        //Generate Uniqui ID for Cart
        Call<CartId> call=client.cartUniquieId();
        call.enqueue(new Callback<CartId>() {
            @Override
            public void onResponse(Call<CartId> call, Response<CartId> response) {

                if(!response.isSuccessful()){
                    try {
                        APIError apiError = ErrorUtils.parseError(response);
                        showMessage("CartIdFetch Failed: " + apiError.getMessage());
                        Log.i("Invalid Fetch", apiError.getMessage());
                        //Log.i("Invalid Entry", response.errorBody().toString());

                    } catch (Exception e) {
                        Log.i("Fetch Failed", e.getMessage());
                        showMessage("CartIdFetch Failed");

                    }

                    return;
                }
                cart_id=response.body().getCartId();
                userPreferences.setUserCartId(cart_id);

                Log.i("CardId",cart_id);

            }

            @Override
            public void onFailure(Call<CartId> call, Throwable t) {
                showMessage("Failed to getCart ID "+t.getMessage());
                Log.i("GEtError",t.getMessage());
            }
        });

    }


}
