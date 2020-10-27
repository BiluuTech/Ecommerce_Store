package com.biluutech.ztshopping.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.biluutech.ztshopping.Activities.AboutusActivity;
import com.biluutech.ztshopping.Activities.ElectronicsSubcategoryActivity;
import com.biluutech.ztshopping.Activities.LoginActivity;
import com.biluutech.ztshopping.Activities.SignupActivity;
import com.biluutech.ztshopping.Adapters.AdminUserAdapter;
import com.biluutech.ztshopping.Adapters.UserSubcategoryAdapter;
import com.biluutech.ztshopping.HomeActivity;
import com.biluutech.ztshopping.Models.SubcategoryModel;
import com.biluutech.ztshopping.Models.UserModel;
import com.biluutech.ztshopping.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private NavigationView navigationView;

    private RecyclerView adminCustomerRecyclerview;

    private DatabaseReference databaseReference;

    private List<UserModel> userModelList;

    private AdminUserAdapter adminUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        toolbar = findViewById(R.id.adminHomeToolbar);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Orders");

        userModelList = new ArrayList<>();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adminCustomerRecyclerview = findViewById(R.id.admin_user_order_recyclerview);
        adminCustomerRecyclerview.setHasFixedSize(true);
        adminCustomerRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.admin_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_admin_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    for (DataSnapshot npshot:snapshot.getChildren()){

                        UserModel l = npshot.getValue(UserModel.class);
                        userModelList.add(l);

                    }
                    adminUserAdapter = new AdminUserAdapter(AdminHomeActivity.this,userModelList);
                    adminCustomerRecyclerview.setAdapter(adminUserAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.admin_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_admin_home) {


        }
        else if (id == R.id.nav_all_products) {

            Intent intenta = new Intent(AdminHomeActivity.this, AdminAllProductsActivity.class);
            startActivity(intenta);
        }
        else if (id == R.id.nav_all_subcategories) {

            Intent intentq = new Intent(AdminHomeActivity.this, AdminAllSubcategoryActivity.class);
            startActivity(intentq);
        }
        else if (id == R.id.nav_add_subcategory) {

            Intent intentb = new Intent(AdminHomeActivity.this, AdminAddNewSubcategoryMainActivity.class);
            startActivity(intentb);
        }
        else if (id == R.id.nav_add_product) {

            Intent intentc = new Intent(AdminHomeActivity.this, AdminAddProductsMainActivity.class);
            startActivity(intentc);
        }
        else if (id == R.id.nav_admin_logout) {

            Intent intentd = new Intent(AdminHomeActivity.this, LoginActivity.class);
            startActivity(intentd);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.admin_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
