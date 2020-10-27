package com.biluutech.ztshopping.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.biluutech.ztshopping.Activities.CartActivity;
import com.biluutech.ztshopping.Admin.AdminEditProductActivity;
import com.biluutech.ztshopping.Models.ProductModelClass;
import com.biluutech.ztshopping.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminAllProductsAdapter extends RecyclerView.Adapter<AdminAllProductsAdapter.Viewholder> {

    private Context mContext;
    private List<ProductModelClass> productModelClassList;

    public AdminAllProductsAdapter(Context mContext, List<ProductModelClass> productModelClassList) {
        this.mContext = mContext;
        this.productModelClassList = productModelClassList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_product_item_list, parent, false);
        return new Viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {

        holder.productName.setText(productModelClassList.get(position).getPname());
        holder.productPrice.setText(productModelClassList.get(position).getPrice());

        Picasso.get().load(productModelClassList.get(position).getImage()).into(holder.productImage);

        holder.removeProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pid = productModelClassList.get(position).getPid();
                String sbname = productModelClassList.get(position).getSubcategory();

                productModelClassList.clear();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("AllProducts");
                databaseReference.child(pid).removeValue();

                DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Products");
                databaseReference2.child(sbname).child(pid).removeValue();
                productModelClassList.clear();


            }
        });

        holder.editProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pid = productModelClassList.get(position).getPid();
                String subcategory = productModelClassList.get(position).getSubcategory();

                Intent intent = new Intent(mContext, AdminEditProductActivity.class);
                intent.putExtra("pid", pid);
                intent.putExtra("subcategory", subcategory);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return productModelClassList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView productName, productPrice;
        private Button removeProductBtn;
        private ImageView productImage;
        private ImageButton editProductBtn;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.admin_product_name_TV);
            productPrice = itemView.findViewById(R.id.admin_product_price_TV);
            productImage = itemView.findViewById(R.id.admin_product_IV);
            removeProductBtn = itemView.findViewById(R.id.admin_remove_product_btn);
            editProductBtn = itemView.findViewById(R.id.admin_edit_product_btn);

        }
    }
}
