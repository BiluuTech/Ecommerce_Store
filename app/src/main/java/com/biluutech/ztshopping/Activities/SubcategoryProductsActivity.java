package com.biluutech.ztshopping.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.biluutech.ztshopping.Models.SubcategoryModel;
import com.biluutech.ztshopping.Adapters.UserSubcategoryAdapter;
import com.biluutech.ztshopping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SubcategoryProductsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private TextView categoryNameTV;

    private RecyclerView productsRecyclerView;

    private DatabaseReference productsReference;
    private List<SubcategoryModel> subcategoryModelList;
    private UserSubcategoryAdapter userSubcategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory_products);

        productsReference = FirebaseDatabase.getInstance().getReference().child("Category");

        subcategoryModelList = new ArrayList<>();

        toolbar = findViewById(R.id.categoryProductsToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        categoryNameTV = findViewById(R.id.categoryNameTV);

        productsRecyclerView = findViewById(R.id.productsRecyclerView);

        productsRecyclerView.setHasFixedSize(true);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String categoryName = getIntent().getStringExtra("category");

        categoryNameTV.setText(categoryName);

        productsReference.child(categoryName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                    SubcategoryModel p = dataSnapshot.getValue(SubcategoryModel.class);
                    subcategoryModelList.add(p);

                }
                userSubcategoryAdapter = new UserSubcategoryAdapter(SubcategoryProductsActivity.this,subcategoryModelList);
                productsRecyclerView.setAdapter(userSubcategoryAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}