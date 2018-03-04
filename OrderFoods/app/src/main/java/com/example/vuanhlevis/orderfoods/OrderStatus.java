package com.example.vuanhlevis.orderfoods;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vuanhlevis.orderfoods.common.Common;
import com.example.vuanhlevis.orderfoods.models.Request;
import com.example.vuanhlevis.orderfoods.viewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView rv_order;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;

    //
    FirebaseDatabase database;
    DatabaseReference requests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        setupUI();
    }

    private void setupUI() {
        //firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        // UI
        rv_order = findViewById(R.id.rv_listorder);
        rv_order.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(OrderStatus.this);
        rv_order.setLayoutManager(layoutManager);

        loadOrder(Common.currUser.getPhone());

    }

    private void loadOrder(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_item,
                OrderViewHolder.class,
                requests.orderByChild("phone").equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
                viewHolder.tv_order_id.setText(adapter.getRef(position).getKey());

                viewHolder.tv_order_status.setText(convertToStatus(model.getStatus()));
                viewHolder.tv_order_address.setText(model.getAddress());
                viewHolder.tv_order_phone.setText(model.getPhone());
            }
        };

        rv_order.setAdapter(adapter);
    }

    private String convertToStatus(String status) {
        if (status.equals("0")) {
            return "Placed";
        } else if (status.equals("1")) {
            return "On My Way";
        } else {
            return "Done!";
        }

    }
}
