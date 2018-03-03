package com.example.vuanhlevis.orderfoods.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vuanhlevis.orderfoods.Interface.ItemClickListener;
import com.example.vuanhlevis.orderfoods.R;

/**
 * Created by vuanhlevis on 03/03/2018.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView tv_food_name;
    public ImageView iv_food;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoodViewHolder(View itemView) {
        super(itemView);

        tv_food_name = itemView.findViewById(R.id.tv_food_name);
        iv_food = itemView.findViewById(R.id.iv_food_image);

        itemView.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        itemClickListener.Onclick(view, getAdapterPosition(), false);
    }
}
