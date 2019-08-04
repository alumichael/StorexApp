package com.mike4christ.storexapp.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mike4christ.storexapp.Constant;
import com.mike4christ.storexapp.R;
import com.mike4christ.storexapp.actvities.OrderDetail;
import com.mike4christ.storexapp.actvities.ProductDetail;
import com.mike4christ.storexapp.models.customer.OrderModel.GetOrderList;
import com.mike4christ.storexapp.retrofit_interface.ItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private Context context;
    private List<GetOrderList> orderList;
    String currency="$";

    public OrderAdapter(Context context, List<GetOrderList> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list, parent, false);
        ButterKnife.bind(this, view);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        GetOrderList orderOption = orderList.get(i);

//        bind data to view

        if(orderOption.getOrderId()==null){
            Log.i("BindError", String.valueOf(orderOption.getOrderId()));
        }else{
            Log.i("SuccessAdapt",String.valueOf(orderOption.getOrderId()));
        }

        holder.orderId.setText(orderOption.getOrderId().toString());
        holder.amountTxt.setText(currency+orderOption.getTotalAmount());
        holder.placeDate.setText(orderOption.getCreatedOn());

        holder.setItemClickListener(pos -> {

            nextActivity(String.valueOf(orderList.get(pos).getOrderId()), OrderDetail.class);
            Log.i("ProdID", String.valueOf(orderList.get(pos).getOrderId()));

        });
    }

    private void nextActivity(String id, Class productActivityClass) {
        Intent i = new Intent(context, productActivityClass);
        i.putExtra(Constant.ORDER_ID, id);
        context.startActivity(i);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.order_id)
        TextView orderId;
        @BindView(R.id.amount_txt)
        TextView amountTxt;
        @BindView(R.id.item_txt)
        TextView itemTxt;
        @BindView(R.id.place_date)
        TextView placeDate;
        @BindView(R.id.order_placed_img)
        ImageView orderPlacedImg;
        @BindView(R.id.quality_check_img)
        ImageView qualityCheckImg;
        @BindView(R.id.detail_btn)
        Button detailBtn;

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
