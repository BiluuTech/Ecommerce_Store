package com.biluutech.ztshopping.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.biluutech.ztshopping.Adapters.CartAdapter;
import com.biluutech.ztshopping.Models.ProductModelClass;
import com.biluutech.ztshopping.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.biluutech.ztshopping.Activities.LoginActivity.PREFS_NAME;

public class CartActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button buyProductsBtn;
    private RecyclerView cartProductsRecyclerview;

    private CartAdapter cartAdapter;
    private List<ProductModelClass> productModelClassList;

    private DatabaseReference databaseReference, orderRef, userRef;
    private FirebaseAuth mAuth;

    private String name, ph, pid, sbname, image, pname;

    private Integer total = 0;

    private CardView cartListEmptyCardview;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        toolbar = findViewById(R.id.cartToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        final String value = settings.getString("phoneNumberKey", "");

        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Cart").child(mAuth.getCurrentUser().getUid());

        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(value);

        cartListEmptyCardview = findViewById(R.id.cart_list_empty_cardview);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                name = snapshot.child("name").getValue().toString();
                ph = snapshot.child("phone").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        buyProductsBtn = findViewById(R.id.buy_products_btn);

        cartProductsRecyclerview = findViewById(R.id.cart_products_recylerview);

        productModelClassList = new ArrayList<>();

        cartProductsRecyclerview.setHasFixedSize(true);
        cartProductsRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    cartListEmptyCardview.setVisibility(View.GONE);
                    buyProductsBtn.setVisibility(View.VISIBLE);

                    for (DataSnapshot cpshot : snapshot.getChildren()) {

                        ProductModelClass cm = cpshot.getValue(ProductModelClass.class);
                        productModelClassList.add(cm);

                    }

                    cartAdapter = new CartAdapter(CartActivity.this, productModelClassList);
                    cartProductsRecyclerview.setAdapter(cartAdapter);

                } else {

                    cartListEmptyCardview.setVisibility(View.VISIBLE);
                    buyProductsBtn.setVisibility(View.GONE);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productModelClassList.clear();


                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ProductModelClass ct = ds.getValue(ProductModelClass.class);
                    Integer price = Integer.valueOf(ct.getPrice());
                    total = total + price;
                    productModelClassList.add(ct);

                }
                cartAdapter = new CartAdapter(CartActivity.this, productModelClassList);
                cartProductsRecyclerview.setAdapter(cartAdapter);
                Log.d("TAG", total + "");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        buyProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(CartActivity.this, OrderPlacingActivity.class);
                intent.putExtra("totalprice", total + "");
                startActivity(intent);
                finish();


                HashMap<String, Object> orderMap = new HashMap<>();
                orderMap.put("name", name);
                orderMap.put("phone", ph);
                orderMap.put("totalBill", total);
                orderRef.child(value).updateChildren(orderMap);


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dds : snapshot.getChildren()) {
                            ProductModelClass cdt = dds.getValue(ProductModelClass.class);
                            pid = cdt.getPid();
                            sbname = cdt.getSubcategory();
                            pname = cdt.getPname();
                            image = cdt.getImage();

                            HashMap<String, Object> dMap = new HashMap<>();

                            dMap.put("pid", pid);
                            dMap.put("subcategory", sbname);
                            dMap.put("image", image);
                            dMap.put("pname", pname);

                            orderRef.child(value).child("orderProducts").child(pid).updateChildren(dMap);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                databaseReference.removeValue();

            }
        });

    }
}