package com.example.vuanhlevis.orderfoods.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.vuanhlevis.orderfoods.Interface.ItemClickListener;
import com.example.vuanhlevis.orderfoods.R;

/**
 * Created by vuanhlevis on 04/03/2018.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tv_order_id, tv_order_status, tv_order_phone, tv_order_address;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public OrderViewHolder(View itemView) {
        super(itemView);

        tv_order_address = itemView.findViewById(R.id.tv_order_item_Address);
        tv_order_id = itemView.findViewById(R.id.tv_order_item_id);
        tv_order_phone = itemView.findViewById(R.id.tv_order_item_Phone);
        tv_order_status = itemView.findViewById(R.id.tv_order_item_Status);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.Onclick(view, getAdapterPosition(), false);
    }
}
