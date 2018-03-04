package com.example.vuanhlevis.orderfoods;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vuanhlevis.orderfoods.common.Common;
import com.example.vuanhlevis.orderfoods.databases.Database;
import com.example.vuanhlevis.orderfoods.models.Order;
import com.example.vuanhlevis.orderfoods.models.Request;
import com.example.vuanhlevis.orderfoods.viewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {
    RecyclerView rv_cart;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    private TextView tv_totalPrice;
    private Button bt_placeOrder;

    private List<Order> list_cart = new ArrayList<>();
    CartAdapter cartAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setupUI();

        bt_placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeOrderDialog();
            }
        });
    }

    private void placeOrderDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Cart.this);
        dialog.setTitle("Get Your Foods");
        dialog.setMessage("Enter Your Address");

        final EditText etaddress = new EditText(Cart.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        etaddress.setLayoutParams(layoutParams);
        dialog.setView(etaddress);
        dialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //creat request
                Request request = new Request(
                        Common.currUser.getName(),
                        Common.currUser.getPhone(),
                        etaddress.getText().toString(),
                        tv_totalPrice.getText().toString(),
                        list_cart
                );

                // submit to firebase, use milisecon to key

                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);

                // after submit -> delete cart
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this, "Thank You, Order Placed", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.show();

    }

    private void setupUI() {
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        rv_cart = findViewById(R.id.rv_cart);
        rv_cart.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv_cart.setLayoutManager(layoutManager);

        tv_totalPrice = findViewById(R.id.tv_total);
        bt_placeOrder = findViewById(R.id.bt_placeOrder);

        loadListFood();
    }

    private void loadListFood() {
        list_cart = new Database(this).getCart();
        cartAdapter = new CartAdapter(list_cart, this);
        rv_cart.setAdapter(cartAdapter);

        // Total price
        int total = 0;
        for (Order order : list_cart) {
            total+= (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
        }

        Locale locale = new Locale("en", "US");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        tv_totalPrice.setText(format.format(total));

    }
}
