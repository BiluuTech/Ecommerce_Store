package com.biluutech.ztshopping.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.biluutech.ztshopping.Models.ProductModelClass;
import com.biluutech.ztshopping.Adapters.ProductsAdapter;
import com.biluutech.ztshopping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ProductModelClass> productModelClassList;
    private ProductsAdapter productsAdapter;
    private DatabaseReference databaseReference;

    private String subname;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        toolbar = findViewById(R.id.productToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        subname = getIntent().getStringExtra("subname");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Products").child(subname);

        productModelClassList = new ArrayList<>();

        recyclerView = findViewById(R.id.products_recyclerview);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    for (DataSnapshot spshot:snapshot.getChildren()){

                        ProductModelClass m = spshot.getValue(ProductModelClass.class);
                        productModelClassList.add(m);

                    }
                    productsAdapter = new ProductsAdapter(ProductsActivity.this,productModelClassList);
                    recyclerView.setAdapter(productsAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}