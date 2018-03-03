package com.example.vuanhlevis.orderfoods;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.vuanhlevis.orderfoods.models.Food;
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
//        bt_cart = findViewById(R.id.bt_cart);
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

    private void getDetailsFoods(final String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Food food = dataSnapshot.getValue(Food.class);
                //
                Picasso.with(FoodDetails.this).load(food.getImage())
                        .into(iv_food_details);

                collapsingToolbarLayout.setTitle(food.getName());

                tv_food_name.setText(food.getName());
                tv_food_price.setText(food.getPrice());
                tv_food_description.setText(food.getDescription());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
