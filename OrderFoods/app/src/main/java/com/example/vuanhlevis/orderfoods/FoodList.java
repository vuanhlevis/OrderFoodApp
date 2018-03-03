package com.example.vuanhlevis.orderfoods;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.vuanhlevis.orderfoods.Interface.ItemClickListener;
import com.example.vuanhlevis.orderfoods.models.Food;
import com.example.vuanhlevis.orderfoods.viewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    RecyclerView rv_food;
    RecyclerView.LayoutManager layoutManager;
    // firebase

    FirebaseDatabase database;
    DatabaseReference foodlist;
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    String categoryId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        setupUI();
    }

    private void setupUI() {
        database = FirebaseDatabase.getInstance();
        foodlist = database.getReference("Foods");

        rv_food = findViewById(R.id.rv_food);
        rv_food.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(FoodList.this);
        rv_food.setLayoutManager(layoutManager);

        // get intent

        if (getIntent()!= null) {
            categoryId = getIntent().getStringExtra("CategoryId");
        }

        if (!categoryId.isEmpty() && categoryId != null) {
            loadListFoods(categoryId);
        }
    }

    private void loadListFoods(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodlist.orderByChild("MenuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.tv_food_name.setText(model.getName());
                Picasso.with(FoodList.this).load(model.getImage()).into(viewHolder.iv_food);

                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void Onclick(View view, int position, boolean islongClick) {
                        // start details food activity

                        Intent intentFoodDetails = new Intent(FoodList.this, FoodDetails.class);
                        // send food id to new activity
                        intentFoodDetails.putExtra("FoodId", adapter.getRef(position).getKey()); //
                        startActivity(intentFoodDetails);
                    }
                });
            }
        };

        rv_food.setAdapter(adapter);
    }
}
