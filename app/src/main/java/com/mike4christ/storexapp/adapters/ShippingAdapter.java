package com.mike4christ.storexapp.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.fragments.CartEditFragment;
import com.mike4christ.storexapp.fragments.CartPaymentFragment;
import com.mike4christ.storexapp.models.customer.CartModels.ShippingRegionDetail;
import com.mike4christ.storexapp.retrofit_interface.ItemClickListener;
import com.mike4christ.storexapp.util.UserPreferences;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShippingAdapter extends RecyclerView.Adapter<ShippingAdapter.MyViewHolder> {

    private Context context;
    private List<ShippingRegionDetail> shipList;

    public ShippingAdapter(Context context, List<ShippingRegionDetail> shipList) {
        this.context = context;
        this.shipList = shipList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipping_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        ShippingRegionDetail shipOption = shipList.get(i);
        UserPreferences userPreferences=new UserPreferences(context);

//        bind data to view

         holder.shipping_type.setText(shipOption.getShippingType());
         holder.shipping_cost.setText(shipOption.getShippingCost());

        holder.setItemClickListener(pos -> {

           String type=shipList.get(pos).getShippingType();
           String cost=shipList.get(pos).getShippingCost();
           userPreferences.setShippingCost(cost);


            new AlertDialog.Builder(context)
                    .setTitle(type)
                    .setMessage("Your shipping cost is $"+cost)
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
                            FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            ft.replace(R.id.fragment_container, CartPaymentFragment.newInstance(String.valueOf(shipList.get(i).getShippingCost())));
                            ft.commit();


                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        });
    }
    

    @Override
    public int getItemCount() {
        return shipList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        @BindView(R.id.shipping_type)
        TextView shipping_type;
        @BindView(R.id.shipping_cost)
        TextView shipping_cost;

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
