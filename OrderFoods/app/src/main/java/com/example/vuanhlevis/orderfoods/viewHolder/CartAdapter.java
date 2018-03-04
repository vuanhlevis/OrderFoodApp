package com.example.vuanhlevis.orderfoods.viewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.vuanhlevis.orderfoods.Interface.ItemClickListener;
import com.example.vuanhlevis.orderfoods.R;
import com.example.vuanhlevis.orderfoods.models.Order;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by vuanhlevis on 04/03/2018.
 */

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView tv_cart_item_name, tv_cart_item_Price;
    public ImageView iv_cart_count;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CartViewHolder(View itemView) {
        super(itemView);
        tv_cart_item_name = itemView.findViewById(R.id.tv_cart_item_name);
        tv_cart_item_Price = itemView.findViewById(R.id.tv_cart_item_Price);
        iv_cart_count = itemView.findViewById(R.id.iv_cart_item_count);
    }

    @Override
    public void onClick(View view) {

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> listdata = new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemview = inflater.inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        TextDrawable drawable = TextDrawable.builder()
                .buildRound("" + listdata.get(position).getQuantity(), Color.RED);
        holder.iv_cart_count.setImageDrawable(drawable);
        Locale locale = new Locale("en", "US");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        int price = Integer.parseInt(listdata.get(position).getPrice())
                * Integer.parseInt(listdata.get(position).getQuantity());
        holder.tv_cart_item_Price.setText(format.format(price));
        holder.tv_cart_item_name.setText(listdata.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }
}
