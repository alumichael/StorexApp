package com.mike4christ.storexapp.adapters;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.models.customer.Attribute;
import com.mike4christ.storexapp.retrofit_interface.ItemClickListener;
import com.mike4christ.storexapp.util.UserPreferences;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.MyViewHolder> {

    private Context context;
    private List<Attribute> attrList;

    public SizeAdapter(Context context, List<Attribute> attrList) {
        this.context = context;
        this.attrList = attrList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attr_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Attribute attrOption = attrList.get(i);
        UserPreferences userPreferences=new UserPreferences(context);

//        bind data to view

         holder.mAttrTxt.setText(attrOption.getValue());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.mAttrThumnail.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        }else {
            holder.mAttrThumnail.setBackgroundColor(Color.BLACK);
        }


        holder.setItemClickListener(pos -> {

           String value=attrList.get(pos).getValue();
           userPreferences.setSizeAttribute(value);
            Toast.makeText(context.getApplicationContext(),"Size Attribute: "+value+" is picked",Toast.LENGTH_SHORT).show();
            Log.i("AttributeClicked ",value);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.mAttrThumnail.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            }else {
                holder.mAttrThumnail.setBackgroundColor(Color.GREEN);
            }


        });
    }


    @Override
    public int getItemCount() {
        return attrList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        @BindView(R.id.attr_thubmnail)
        FrameLayout mAttrThumnail;
        @BindView(R.id.attr_txt)
        TextView mAttrTxt;

        ItemClickListener itemClickListener;

        MyViewHolder(View itemView) {
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
