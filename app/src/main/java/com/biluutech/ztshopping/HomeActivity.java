package com.biluutech.ztshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.biluutech.ztshopping.Activities.AboutusActivity;
import com.biluutech.ztshopping.Activities.BabySubcategoryActivity;
import com.biluutech.ztshopping.Activities.CartActivity;
import com.biluutech.ztshopping.Activities.ElectronicsSubcategoryActivity;
import com.biluutech.ztshopping.Activities.EducationSubcategoryActivity;
import com.biluutech.ztshopping.Activities.LoginActivity;
import com.biluutech.ztshopping.Activities.MenSubcategoryActivity;
import com.biluutech.ztshopping.Activities.HomeLivingSubcategoryActivity;
import com.biluutech.ztshopping.Activities.WomenSubcategoryActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<SlidingModel> imageModelArrayList;

    private int[] myImageList = new int[]{R.drawable.fa, R.drawable.fb, R.drawable.fc, R.drawable.fd};

    private CardView electronicsCardview, menCardview, womenCardview, babyCardview, toysCardview, groceryCardview;
    private NavigationView navigationView;
    private ImageView cartCheckBtn;

    private DatabaseReference countRef;

    private int count = 0;

    private TextView cartCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cartCounter = findViewById(R.id.cart_counter);

        countRef = FirebaseDatabase.getInstance().getReference().child("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        countRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    count = (int) snapshot.getChildrenCount();

                    cartCounter.setText(Integer.toString(count));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        toolbar = findViewById(R.id.homeToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();
        init();

        electronicsCardview = findViewById(R.id.electronics_cardview);
        menCardview = findViewById(R.id.men_cardview);
        womenCardview = findViewById(R.id.women_cardview);
        babyCardview = findViewById(R.id.baby_cardview);
        toysCardview = findViewById(R.id.toys_cardview);
        groceryCardview = findViewById(R.id.grocery_cardview);
        cartCheckBtn = findViewById(R.id.cart_check_btn);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        Toast.makeText(this, phoneNumberModel.getPhoneNumber(), Toast.LENGTH_SHORT).show();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);

        cartCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomeActivity.this, CartActivity.class));

            }
        });

        electronicsCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ElectronicsSubcategoryActivity.class);
                intent.putExtra("category", "electronics");
                startActivity(intent);
            }
        });

        menCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MenSubcategoryActivity.class);
                intent.putExtra("category", "men");
                startActivity(intent);
            }
        });

        womenCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, WomenSubcategoryActivity.class);
                intent.putExtra("category", "women");
                startActivity(intent);
            }
        });

        babyCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, BabySubcategoryActivity.class);
                intent.putExtra("category", "baby");
                startActivity(intent);
            }
        });

        toysCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, HomeLivingSubcategoryActivity.class);
                intent.putExtra("category", "toys");
                startActivity(intent);
            }
        });

        groceryCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, EducationSubcategoryActivity.class);
                intent.putExtra("category", "grocery");
                startActivity(intent);
            }
        });


    }

    private ArrayList<SlidingModel> populateList() {
        ArrayList<SlidingModel> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SlidingModel imageModel = new SlidingModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }
        return list;
    }

    private void init() {
        mPager = findViewById(R.id.pager);
        mPager.setAdapter(new SlidingAdapter(HomeActivity.this, imageModelArrayList));

        NUM_PAGES = imageModelArrayList.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, false);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4000, 4000);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        if (id == R.id.nav_home) {


        }
        else if (id == R.id.nav_aboutus) {

            Intent abouIntent = new Intent(HomeActivity.this, AboutusActivity.class);
            startActivity(abouIntent);
        } else if (id == R.id.nav_logout) {

            FirebaseAuth.getInstance().signOut();
            Intent logoutIntent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(logoutIntent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}