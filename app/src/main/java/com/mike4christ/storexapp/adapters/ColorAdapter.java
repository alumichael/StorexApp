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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.card.MaterialCardView;
import com.mike4christ.storexapp.Constant;
import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.actvities.ProductDetail;
import com.mike4christ.storexapp.models.customer.Attribute;
import com.mike4christ.storexapp.models.customer.Attribute;
import com.mike4christ.storexapp.retrofit_interface.ItemClickListener;
import com.mike4christ.storexapp.util.UserPreferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.MyColorViewHolder> {

    private Context context;
    private List<Attribute> attrList;

    public ColorAdapter(Context context, List<Attribute> attrList) {
        this.context = context;
        this.attrList = attrList;
    }

    @NonNull
    @Override
    public MyColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attr_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyColorViewHolder holder, int i) {
        Attribute attrOption = attrList.get(i);
        UserPreferences userPreferences=new UserPreferences(context);

//        bind data to view
         String colorAlphabet=attrOption.getValue().substring(0,2).toUpperCase();
         holder.mAttrTxt.setText(colorAlphabet);
         switch (attrOption.getValue()){
             case "White":
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                     holder.mAttrThumnail.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorWhiteGrey)));

                 }else {
                     holder.mAttrThumnail.setBackgroundColor(Color.WHITE);
                 }
                 holder.mAttrTxt.setTextColor(Color.BLACK);
                 break;
             case "Purple":
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                     holder.mAttrThumnail.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#800080")));
                 }else{
                     holder.mAttrThumnail.setBackgroundColor(Color.parseColor("#800080"));
                 }
                 break;
             case "Indigo":

                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                     holder.mAttrThumnail.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4b0082")));
                 }else {
                     holder.mAttrThumnail.setBackgroundColor(Color.parseColor("#4b0082"));
                 }

                 break;
             case "Blue":
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                     holder.mAttrThumnail.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));
                 }else {
                     holder.mAttrThumnail.setBackgroundColor(Color.BLUE);
                 }


                 break;
             case "Green":
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                     holder.mAttrThumnail.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                 }else {
                     holder.mAttrThumnail.setBackgroundColor(Color.GREEN);
                 }

                 break;
             case "Yellow":
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                     holder.mAttrThumnail.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
                 }else {
                     holder.mAttrThumnail.setBackgroundColor(Color.YELLOW);
                 }
                 holder.mAttrTxt.setTextColor(Color.BLACK);
                 break;
             case "Orange":
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                     holder.mAttrThumnail.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFA500")));
                 }else {
                     holder.mAttrThumnail.setBackgroundColor(Color.parseColor("#FFA500"));
                 }

                 break;

             case "Black":

                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                     holder.mAttrThumnail.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                 }else {
                     holder.mAttrThumnail.setBackgroundColor(Color.BLACK);
                 }

                 break;

             case "Red":
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                     holder.mAttrThumnail.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                 }else {
                     holder.mAttrThumnail.setBackgroundColor(Color.RED);
                 }

                 break;

         }

        holder.setItemClickListener(pos -> {

           String value=attrOption.getValue();
           userPreferences.setColorAtrribute(value);
            Toast.makeText(context.getApplicationContext(),"Color Attribute: "+value+" is picked",Toast.LENGTH_LONG).show();


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.mAttrThumnail.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.colorDeepGreen)));
            }else {
                holder.mAttrThumnail.setBackgroundColor(context.getResources().getColor(R.color.colorDeepGreen));
            }

        });
    }
    

    @Override
    public int getItemCount() {
        return attrList.size();
    }

    public class MyColorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        @BindView(R.id.attr_thubmnail)
        FrameLayout mAttrThumnail;
        @BindView(R.id.attr_txt)
        TextView mAttrTxt;

        ItemClickListener itemClickListener;

        MyColorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }
    }
}
