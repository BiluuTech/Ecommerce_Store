package com.biluutech.ztshopping.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.biluutech.ztshopping.HomeActivity;
import com.biluutech.ztshopping.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static com.biluutech.ztshopping.Activities.LoginActivity.PREFS_NAME;

public class ProductDescriptionActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String pid, subcategory;
    private DatabaseReference databaseReference, cartRef, userRef;

    private FirebaseAuth firebaseAuth;

    private ImageView productImageIV;
    private TextView productPriceTV, productDescTV, productNameTV;
    private Button addCartBtn;

    private String name, phone, pname, image, description, price, sbname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        final String value = settings.getString("phoneNumberKey", "");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Products");

        firebaseAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.productDescriptionToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        productImageIV = findViewById(R.id.product_image_IV);
        productPriceTV = findViewById(R.id.product_price_TV);
        productDescTV = findViewById(R.id.product_desc_TV);
        productNameTV = findViewById(R.id.product_name_TV);
        addCartBtn = findViewById(R.id.add_cart_btn);

        pid = getIntent().getStringExtra("pid");
        subcategory = getIntent().getStringExtra("subcategory");

            userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(value);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                name = snapshot.child("name").getValue().toString();
                phone = snapshot.child("phone").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child(subcategory).child(pid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                 pname = snapshot.child("pname").getValue().toString();
                 description = snapshot.child("description").getValue().toString();
                 sbname = snapshot.child("subcategory").getValue().toString();
                 price = snapshot.child("price").getValue().toString();
                 image = snapshot.child("image").getValue().toString();

                Picasso.get().load(image).into(productImageIV);

                productPriceTV.setText(price);
                productDescTV.setText(description);
                productNameTV.setText(pname);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cartRef = FirebaseDatabase.getInstance().getReference().child("Cart").child(firebaseAuth.getCurrentUser().getUid()).child(pid);

                HashMap<String, Object> orderMap = new HashMap<>();
                orderMap.put("uid", firebaseAuth.getCurrentUser().getUid());
                orderMap.put("name", name);
                orderMap.put("phone", phone);
                orderMap.put("pid", pid);
                orderMap.put("pname", pname);
                orderMap.put("subcategory", sbname);
                orderMap.put("price", price);
                orderMap.put("image", image);

                cartRef.updateChildren(orderMap);
                Toast.makeText(ProductDescriptionActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(ProductDescriptionActivity.this, ThanksCartActivity.class));


            }
        });

    }
}