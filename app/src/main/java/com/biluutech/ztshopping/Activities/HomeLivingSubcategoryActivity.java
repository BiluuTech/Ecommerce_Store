package com.biluutech.ztshopping.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.biluutech.ztshopping.R;
import com.biluutech.ztshopping.Models.SubcategoryModel;
import com.biluutech.ztshopping.Adapters.UserSubcategoryAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeLivingSubcategoryActivity extends AppCompatActivity {

    private RecyclerView toysSubcatRecyclerview;
    private UserSubcategoryAdapter userSubcategoryAdapter;
    private List<SubcategoryModel> subcategoryModelList;

    private DatabaseReference toysRef;
    private EditText searchEditText;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_living_subcategory);
        toolbar = findViewById(R.id.toysSubcatToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toysRef = FirebaseDatabase.getInstance().getReference().child("homeliving");

        toysSubcatRecyclerview = findViewById(R.id.toys_subcat_recyclerview);
        toysSubcatRecyclerview.setHasFixedSize(true);
        toysSubcatRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        subcategoryModelList = new ArrayList<>();

        searchEditText = findViewById(R.id.search_homeliving_edittext);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        toysRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    for (DataSnapshot npshot:snapshot.getChildren()){

                        SubcategoryModel l = npshot.getValue(SubcategoryModel.class);
                        subcategoryModelList.add(l);

                    }
                    userSubcategoryAdapter = new UserSubcategoryAdapter(HomeLivingSubcategoryActivity.this,subcategoryModelList);
                    toysSubcatRecyclerview.setAdapter(userSubcategoryAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void filter(String text) {
        ArrayList<SubcategoryModel> filteredList = new ArrayList<>();
        for (SubcategoryModel item : subcategoryModelList) {
            if (item.getScname().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        userSubcategoryAdapter.filterList(filteredList);
    }
}