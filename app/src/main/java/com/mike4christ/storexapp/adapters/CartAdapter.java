package com.mike4christ.storexapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.mike4christ.storexapp.Constant;
import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.actvities.Dashboard;
import com.mike4christ.storexapp.actvities.ProductDetail;
import com.mike4christ.storexapp.fragments.CartEditFragment;
import com.mike4christ.storexapp.fragments.CartFragment;
import com.mike4christ.storexapp.fragments.CartPaymentFragment;
import com.mike4christ.storexapp.fragments.OrderAddrFragment;
import com.mike4christ.storexapp.models.customer.CartModels.CartList;
import com.mike4christ.storexapp.models.customer.ErrorModel.APIError;
import com.mike4christ.storexapp.models.customer.ErrorModel.ErrorUtils;
import com.mike4christ.storexapp.retrofit_interface.ApiInterface;
import com.mike4christ.storexapp.retrofit_interface.ItemClickListener;
import com.mike4christ.storexapp.retrofit_interface.ServiceGenerator;
import com.mike4christ.storexapp.util.UserPreferences;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private Context context;
    private List<CartList> cartList;
    Fragment fragment;
    UserPreferences userPreferences;


    public CartAdapter(Context context, List<CartList> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        CartList cartOption = cartList.get(i);
         userPreferences=new UserPreferences(context);
        
        String[] both=cartOption.getAttributes().split(",");
        String attriSize=both[0];
        String attriColor=both[1];
        
        // bind data to viewholder

        holder.mProductName.setText(cartOption.getName());
        holder.mDiscountPrice.setText(cartOption.getPrice());
        holder.mAttributeSize.setText(attriSize);
        holder.quantity.setText(String.valueOf(cartOption.getQuantity()));
        
        switch (attriColor){
            case "White":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.mAttributeColor.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorWhiteGrey)));

                }else {
                    holder.mAttributeColor.setBackgroundColor(Color.WHITE);
                }
                break;
            case "Purple":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.mAttributeColor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#800080")));
                }else{
                    holder.mAttributeColor.setBackgroundColor(Color.parseColor("#800080"));
                }
                break;
            case "Indigo":

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.mAttributeColor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4b0082")));
                }else {
                    holder.mAttributeColor.setBackgroundColor(Color.parseColor("#4b0082"));
                }

                break;
            case "Blue":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.mAttributeColor.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));
                }else {
                    holder.mAttributeColor.setBackgroundColor(Color.BLUE);
                }


                break;
            case "Green":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.mAttributeColor.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                }else {
                    holder.mAttributeColor.setBackgroundColor(Color.GREEN);
                }

                break;
            case "Yellow":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.mAttributeColor.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
                }else {
                    holder.mAttributeColor.setBackgroundColor(Color.YELLOW);
                }
                break;
            case "Orange":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.mAttributeColor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFA500")));
                }else {
                    holder.mAttributeColor.setBackgroundColor(Color.parseColor("#FFA500"));
                }

                break;

            case "Black":

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.mAttributeColor.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                }else {
                    holder.mAttributeColor.setBackgroundColor(Color.BLACK);
                }

                break;

            case "Red":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.mAttributeColor.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                }else {
                    holder.mAttributeColor.setBackgroundColor(Color.RED);
                }

                break;

        }


        holder.mEditCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                nextActivity(String.valueOf(cartList.get(i).getItemId()),
                        String.valueOf(cartList.get(i).getQuantity()),cartOption.getPrice(),
                        attriColor,attriSize,cartOption.getName(),CartEditFragment.class);

            }
        });

        holder.delete_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ApiInterface client= ServiceGenerator.createService(ApiInterface.class);
                Call<ResponseBody> call2 = client.delete_item(cartOption.getItemId());
                call2.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

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

                        showMessage("Item is Removed");
                        //When item is deleted refresh the pref and the fragment
                        int currentSize=userPreferences.getUserCartSize()-1;
                        userPreferences.setUserCartSize(currentSize);
                        fragment=new CartFragment();
                        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.fragment_container,fragment);
                        ft.commit();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        showMessage("Fetch failed, please try again " + t.getMessage());
                        Log.i("GEtError", t.getMessage());
                    }
                });


            }
        });
    }

    private void nextActivity(String id,String qty,String price, String attriColor,String attriSize,String name, Class productActivityClass) {
        Intent i = new Intent(context, productActivityClass);
        i.putExtra(Constant.ITEM_ID, id);
        i.putExtra(Constant.ITEM_QTY, qty);
        i.putExtra(Constant.ITEM_PRICE, price);
        i.putExtra(Constant.ITEM_COLOR, attriColor);
        i.putExtra(Constant.ITEM_SIZE, attriSize);
        i.putExtra(Constant.ITEM_NAME, name);
        context.startActivity(i);
    }
    public void showMessage(String s) {
        Toast.makeText(context, s, Snackbar.LENGTH_LONG).show();
    }




    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_thumnail)
        ImageView mProductThumnail;
        @BindView(R.id.product_name)
        TextView mProductName;
        @BindView(R.id.discount_price)
        TextView mDiscountPrice;
        @BindView(R.id.attribute_color)
        LinearLayout mAttributeColor;
        @BindView(R.id.attribute_size)
        TextView mAttributeSize;
        @BindView(R.id.quantity)
        TextView quantity;
        @BindView(R.id.edit_cart)
        ImageView mEditCart;
        @BindView(R.id.delete_cart)
        ImageView delete_cart;


        ItemClickListener itemClickListener;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

           // itemView.setOnClickListener(this);
        }

       /* public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }*/

       /* @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }*/
        
    }
}
