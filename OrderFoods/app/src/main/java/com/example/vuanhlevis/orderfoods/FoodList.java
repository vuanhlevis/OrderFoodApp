package com.example.vuanhlevis.orderfoods;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.vuanhlevis.orderfoods.Interface.ItemClickListener;
import com.example.vuanhlevis.orderfoods.models.Food;
import com.example.vuanhlevis.orderfoods.viewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodList extends AppCompatActivity {

    RecyclerView rv_food;
    RecyclerView.LayoutManager layoutManager;
    // firebase

    FirebaseDatabase database;
    DatabaseReference foodlist;
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    //search funtion
    FirebaseRecyclerAdapter<Food, FoodViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
//    MaterialSearchBar searchBar;

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

        if (getIntent() != null) {
            categoryId = getIntent().getStringExtra("CategoryId");
        }

        if (!categoryId.isEmpty() && categoryId != null) {
            loadListFoods(categoryId);
        }

        //search
//        searchBar = findViewById(R.id.search_bar);
//        searchBar.setHint("Enter Your Food");
//        loadSuggest();
//
//        searchBar.setLastSuggestions(suggestList);
//        searchBar.setCardViewElevation(9);
//        searchBar.addTextChangeListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                //when user type -> change suggest
//
//                List<String> suggest = new ArrayList<>();
//                for (String search : suggestList) {
//                    if (search.toLowerCase().contains(searchBar.getText().toLowerCase()))
//                        suggest.add(search);
//                }
//
//                searchBar.setLastSuggestions(suggest);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
//            @Override
//            public void onSearchStateChanged(boolean enabled) {
//                // close search bar when not use
//                if (!enabled) {
//                    rv_food.setAdapter(adapter);
//                }
//            }
//
//            @Override
//            public void onSearchConfirmed(CharSequence text) {
//                // when search finish -> show
//                startSearch(text);
//            }
//
//            @Override
//            public void onButtonClicked(int buttonCode) {
//
//            }
//        });

    }

//    private void startSearch(CharSequence text) {
//        searchAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
//                Food.class,
//                R.layout.food_item,
//                FoodViewHolder.class,
//                foodlist.orderByChild("MenuId").equalTo(text.toString())
//        ) {
//            @Override
//            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
//                viewHolder.tv_food_name.setText(model.getName());
//                Picasso.with(FoodList.this).load(model.getImage()).into(viewHolder.iv_food);
//
//                final Food local = model;
//                viewHolder.setItemClickListener(new ItemClickListener() {
//                    @Override
//                    public void Onclick(View view, int position, boolean islongClick) {
//                        // start details food activity
//
//                        Intent intentFoodDetails = new Intent(FoodList.this, FoodDetails.class);
//                        // send food id to new activity
//                        intentFoodDetails.putExtra("FoodId", adapter.getRef(position).getKey()); //
//                        startActivity(intentFoodDetails);
//                    }
//                });
//            }
//        };
//        rv_food.setAdapter(searchAdapter);
//    }

//    private void loadSuggest() {
//        foodlist.orderByChild("MenuId").equalTo(categoryId)
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            Food item = snapshot.getValue(Food.class);
//                            suggestList.add(item.getName());
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//    }

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
