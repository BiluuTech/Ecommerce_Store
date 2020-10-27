package com.biluutech.ztshopping.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.biluutech.ztshopping.Adapters.AdminSubcategoryAdapter;
import com.biluutech.ztshopping.Adapters.SubcategoryAdapter;
import com.biluutech.ztshopping.Models.SubcategoryModel;
import com.biluutech.ztshopping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminAddProductSelectSubcategoryActivity extends AppCompatActivity {

    private RecyclerView adminSubcategoryRecyclerview;
    private AdminSubcategoryAdapter adminSubcategoryAdapter;
    private List<SubcategoryModel> subcategoryModelList;
    private Context mContext;

    private DatabaseReference subcategoryRef;

    private String CategoryName;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_product_select_subcategory);

        toolbar = findViewById(R.id.adminAddProductSelectSubcategoryToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CategoryName = getIntent().getExtras().get("category").toString();

        adminSubcategoryRecyclerview = findViewById(R.id.adminSubcategoryRecyclerview);

        adminSubcategoryRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        adminSubcategoryRecyclerview.setHasFixedSize(true);

        subcategoryModelList = new ArrayList<>();

        subcategoryRef = FirebaseDatabase.getInstance().getReference().child(CategoryName);

        subcategoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    for (DataSnapshot npsnapshot : snapshot.getChildren()) {
                        SubcategoryModel l = npsnapshot.getValue(SubcategoryModel.class);
                        subcategoryModelList.add(l);
                    }

                    adminSubcategoryAdapter = new AdminSubcategoryAdapter(AdminAddProductSelectSubcategoryActivity.this,subcategoryModelList);
                    adminSubcategoryRecyclerview.setAdapter(adminSubcategoryAdapter);
                }
                else
                {

                    Toast.makeText(AdminAddProductSelectSubcategoryActivity.this, "NO Data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}