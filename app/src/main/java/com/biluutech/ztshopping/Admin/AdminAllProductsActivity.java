package com.biluutech.ztshopping.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.biluutech.ztshopping.Adapters.AdminAllProductsAdapter;
import com.biluutech.ztshopping.Models.ProductModelClass;
import com.biluutech.ztshopping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminAllProductsActivity extends AppCompatActivity {

    private RecyclerView adminAllProductsRecyclerview;
    private List<ProductModelClass> productModelClassList;
    private AdminAllProductsAdapter adminAllProductsAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_products);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("AllProducts");

        adminAllProductsRecyclerview = findViewById(R.id.adminAllProductsRecyclerview);
        adminAllProductsRecyclerview.setHasFixedSize(true);
        adminAllProductsRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        productModelClassList = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot sp: snapshot.getChildren()){

                    ProductModelClass k = sp.getValue(ProductModelClass.class);

                    productModelClassList.add(k);

                }

                adminAllProductsAdapter = new AdminAllProductsAdapter(AdminAllProductsActivity.this,productModelClassList);
                adminAllProductsRecyclerview.setAdapter(adminAllProductsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}