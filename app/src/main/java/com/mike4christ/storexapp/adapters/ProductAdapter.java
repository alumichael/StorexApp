package com.mike4christ.storexapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.card.MaterialCardView;
import com.mike4christ.storexapp.Constant;
import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.actvities.ProductDetail;
import com.mike4christ.storexapp.models.customer.Product.ProductModel;
import com.mike4christ.storexapp.retrofit_interface.ItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private Context context;
    private List<ProductModel> menList;
    String t_percentage_diff;
    String currency="$";

    public ProductAdapter(Context context, List<ProductModel> menList) {
        this.context = context;
        this.menList = menList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        ProductModel menOption = menList.get(i);

        holder.mProductName.setText(menOption.getName());
        //Underline CLick to Login

        holder.mProductPrice.setText(currency+menOption.getPrice());
        double percent_diff=Double.parseDouble(menOption.getPrice())-Double.parseDouble(menOption.getDiscountedPrice());
        double total=percent_diff/100;
        t_percentage_diff=String.valueOf(total);
        if(t_percentage_diff.length()>=6){
            t_percentage_diff=String.valueOf(total).substring(0,5);
        }
        holder.mDiscountPrice.setText(currency+menOption.getDiscountedPrice());
        holder.mDiscountPercent.setText(t_percentage_diff.concat("%"));
        holder.setThumbnail(menOption.getThumbnail());

        holder.setItemClickListener(pos -> {

            nextActivity(String.valueOf(menList.get(pos).getProductId()), ProductDetail.class);
            Log.i("ProdID", String.valueOf(menList.get(pos).getProductId()));

        });
    }

    private void nextActivity(String id, Class productActivityClass) {
        Intent i = new Intent(context, productActivityClass);
        i.putExtra(Constant.PRODUCT_ID, id);
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return menList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.product_thumnail)
        ImageView mProductThumnail;
        @BindView(R.id.product_name)
        TextView mProductName;
        @BindView(R.id.product_price)
        TextView mProductPrice;
        @BindView(R.id.discount_price)
        TextView mDiscountPrice;
        @BindView(R.id.discount_percent)
        TextView mDiscountPercent;
        @BindView(R.id.product_tag)
        TextView mProductTag;
        @BindView(R.id.product_card)
        MaterialCardView product_card;

        ItemClickListener itemClickListener;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mProductPrice.setPaintFlags(mProductPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }
        public void setThumbnail(String url) {
            String url_append=Constant.IMAGE_BASE_URL+url;
            ImageView imageView = this.mProductThumnail;
            if (imageView != null) {
                Glide.with(imageView.getContext()).load(url_append).apply(new RequestOptions().fitCenter().circleCrop()).into(this.mProductThumnail);
            }
        }
    }
}
