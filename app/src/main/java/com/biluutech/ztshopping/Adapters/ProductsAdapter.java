package com.biluutech.ztshopping.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.biluutech.ztshopping.Activities.ProductDescriptionActivity;
import com.biluutech.ztshopping.Models.ProductModelClass;
import com.biluutech.ztshopping.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private Context mContext;
    private List<ProductModelClass> productModelClassList;

    public ProductsAdapter(Context mContext, List<ProductModelClass> productModelClassList) {
        this.mContext = mContext;
        this.productModelClassList = productModelClassList;
    }

    @NonNull
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_subcategory_item_list,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ViewHolder holder, final int position) {

        holder.pname.setText(productModelClassList.get(position).getPname());

        Picasso.get().load(productModelClassList.get(position).getImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pid = productModelClassList.get(position).getPid();
                String subcategory = productModelClassList.get(position).getSubcategory();

                Intent intent = new Intent(mContext, ProductDescriptionActivity.class);
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView pname;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pname = itemView.findViewById(R.id.admin_subcategory_TV);
            image = itemView.findViewById(R.id.admin_subcategory_IV);

        }
    }
}
