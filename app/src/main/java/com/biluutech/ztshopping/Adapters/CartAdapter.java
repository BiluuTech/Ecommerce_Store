package com.biluutech.ztshopping.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.biluutech.ztshopping.Activities.CartActivity;
import com.biluutech.ztshopping.Models.ProductModelClass;
import com.biluutech.ztshopping.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context mContext;
    private List<ProductModelClass> productModelClassList;

    public CartAdapter(Context mContext, List<ProductModelClass> productModelClassList) {
        this.mContext = mContext;
        this.productModelClassList = productModelClassList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.cartProductNameTV.setText(productModelClassList.get(position).getPname());
        holder.cartProductPriceTV.setText("Rs " + productModelClassList.get(position).getPrice());

        Picasso.get().load(productModelClassList.get(position).getImage()).into(holder.cartProductIV);

        holder.cartRemoveProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pid = productModelClassList.get(position).getPid();

                productModelClassList.clear();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                databaseReference.child(pid).removeValue();
                productModelClassList.clear();

                mContext.startActivity(new Intent(mContext, CartActivity.class));


            }
        });

    }

    @Override
    public int getItemCount() {
        return productModelClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView cartProductNameTV, cartProductPriceTV;
        private Button cartRemoveProductBtn;
        private ImageView cartProductIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cartProductNameTV = itemView.findViewById(R.id.cart_product_name_TV);
            cartProductPriceTV = itemView.findViewById(R.id.cart_product_price_TV);
            cartProductIV = itemView.findViewById(R.id.cart_product_IV);
            cartRemoveProductBtn = itemView.findViewById(R.id.cart_remove_product_btn);

        }
    }
}
