package com.biluutech.ztshopping.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.biluutech.ztshopping.Adapters.AdminUserProductsAdapter;
import com.biluutech.ztshopping.Models.AdminModel;
import com.biluutech.ztshopping.Models.ProductModelClass;
import com.biluutech.ztshopping.Models.UserModel;
import com.biluutech.ztshopping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminUserProductsActivity extends AppCompatActivity {

    private DatabaseReference ref;
    private String pho;

    private RecyclerView adminUserProductsRecyclerview;
    private List<AdminModel> adminModelList;
    private AdminUserProductsAdapter adminUserProductsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);

        adminUserProductsRecyclerview = findViewById(R.id.adminUserProductsRecyclerview);
        adminUserProductsRecyclerview.setHasFixedSize(true);
        adminUserProductsRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        adminModelList = new ArrayList<>();

        pho = getIntent().getStringExtra("phone");


        ref = FirebaseDatabase.getInstance().getReference().child("Orders");

        ref.child(pho).child("orderProducts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds:snapshot.getChildren()){

                    AdminModel m = ds.getValue(AdminModel.class);
                    adminModelList.add(m);


                }

                adminUserProductsAdapter = new AdminUserProductsAdapter(AdminUserProductsActivity.this,adminModelList);
                adminUserProductsRecyclerview.setAdapter(adminUserProductsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}