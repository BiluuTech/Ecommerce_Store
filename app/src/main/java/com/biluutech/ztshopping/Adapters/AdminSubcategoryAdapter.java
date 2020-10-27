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
import com.biluutech.ztshopping.Admin.AdminAddNewProductActivity;
import com.biluutech.ztshopping.Admin.AdminAddProductSelectSubcategoryActivity;
import com.biluutech.ztshopping.Models.SubcategoryModel;
import com.biluutech.ztshopping.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminSubcategoryAdapter extends RecyclerView.Adapter<AdminSubcategoryAdapter.ViewHolder> {
    private Context context;
    private List<SubcategoryModel> subcategoryModelList;

    public AdminSubcategoryAdapter(Context context, List<SubcategoryModel> subcategoryModelList) {
        this.context = context;
        this.subcategoryModelList = subcategoryModelList;
    }

    @NonNull
    @Override
    public AdminSubcategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_subcat_major_item_layout,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdminSubcategoryAdapter.ViewHolder holder, final int position) {

        holder.subcatNameTV.setText(subcategoryModelList.get(position).getScname());
        Picasso.get().load(subcategoryModelList.get(position).getScimage()).into(holder.subcatIV);
//
//        holder.subcatRemoveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                String pid = subcategoryModelList.get(position).getPid();
//
//                subcategoryModelList.clear();
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                databaseReference.child(pid).removeValue();
//                subcategoryModelList.clear();
//
//                context.startActivity(new Intent(context, AdminAddProductSelectSubcategoryActivity.class));
//
//
//            }
//        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String subname = subcategoryModelList.get(position).getScname();

                Intent intent = new Intent(context, AdminAddNewProductActivity.class);
                intent.putExtra("subname", subname);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return subcategoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView subcatNameTV;
        private Button subcatRemoveBtn;
        private ImageView subcatIV;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            subcatIV = itemView.findViewById(R.id.cart_product_IV);
            subcatRemoveBtn = itemView.findViewById(R.id.subcat_remove_btn);
            subcatNameTV = itemView.findViewById(R.id.subcat_name_TV);

        }
    }
}
