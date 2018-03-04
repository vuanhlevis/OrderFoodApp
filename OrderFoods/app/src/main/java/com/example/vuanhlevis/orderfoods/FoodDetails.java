package com.example.vuanhlevis.orderfoods;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.vuanhlevis.orderfoods.databases.Database;
import com.example.vuanhlevis.orderfoods.models.Food;
import com.example.vuanhlevis.orderfoods.models.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetails extends AppCompatActivity {
    TextView tv_food_name, tv_food_description, tv_food_price;
    ImageView iv_food_details;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton bt_cart;
    ElegantNumberButton numberButton;

    String foodId = "";

    // firebase
    FirebaseDatabase database;
    DatabaseReference foods;
    Food currFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        setupUI();


    }

    private void setupUI() {
        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");

        //
        numberButton = findViewById(R.id.number_button);
        bt_cart = findViewById(R.id.bt_cart);
        bt_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionCart();
//                Toast.makeText(FoodDetails.this, "lol", Toast.LENGTH_SHORT).show();
            }
        });

        tv_food_description = findViewById(R.id.tv_food_description);
        tv_food_name = findViewById(R.id.tv_food_name);
        tv_food_price = findViewById(R.id.tv_food_price);
        iv_food_details = findViewById(R.id.iv_food_details);
        // config collasing
        collapsingToolbarLayout = findViewById(R.id.collapsing_details);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsingAppbar);

        // get Intent
        if (getIntent() != null) {
            foodId = getIntent().getStringExtra("FoodId");
        }
        if (!foodId.isEmpty()) {
            getDetailsFoods(foodId);
        }


    }

    private void actionCart() {
        new Database(this).addToCart(new Order(
                foodId,
                currFood.getName(),
                numberButton.getNumber(),
                currFood.getPrice(),
                currFood.getDiscount()
        ));

        Toast.makeText(this, "added to cart", Toast.LENGTH_SHORT).show();
    }

    private void getDetailsFoods(final String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currFood = dataSnapshot.getValue(Food.class);
                //
                Picasso.with(FoodDetails.this).load(currFood.getImage())
                        .into(iv_food_details);

                collapsingToolbarLayout.setTitle(currFood.getName());

                tv_food_name.setText(currFood.getName());
                tv_food_price.setText(currFood.getPrice());
                tv_food_description.setText(currFood.getDescription());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
