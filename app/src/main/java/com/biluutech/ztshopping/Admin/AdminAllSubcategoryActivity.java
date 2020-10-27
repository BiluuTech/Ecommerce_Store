package com.biluutech.ztshopping.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.biluutech.ztshopping.Adapters.AdminAllProductsAdapter;
import com.biluutech.ztshopping.Adapters.AdminAllSubcategoriesAdapter;
import com.biluutech.ztshopping.Models.ProductModelClass;
import com.biluutech.ztshopping.Models.SubcategoryModel;
import com.biluutech.ztshopping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminAllSubcategoryActivity extends AppCompatActivity {

    private RecyclerView adminAllSubcategoriesRecyclerview;
    private List<SubcategoryModel> subcategoryModelList;
    private AdminAllSubcategoriesAdapter adminAllSubcategoriesAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_subcategory);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("AllCategories");

        adminAllSubcategoriesRecyclerview = findViewById(R.id.adminAllSubcategoriesRecyclerview);
        adminAllSubcategoriesRecyclerview.setHasFixedSize(true);
        adminAllSubcategoriesRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        subcategoryModelList = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot sp: snapshot.getChildren()){

                    SubcategoryModel k = sp.getValue(SubcategoryModel.class);

                    subcategoryModelList.add(k);

                }

                adminAllSubcategoriesAdapter = new AdminAllSubcategoriesAdapter(AdminAllSubcategoryActivity.this,subcategoryModelList);
                adminAllSubcategoriesRecyclerview.setAdapter(adminAllSubcategoriesAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}