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

import com.biluutech.ztshopping.Activities.ProductsActivity;
import com.biluutech.ztshopping.Models.SubcategoryModel;
import com.biluutech.ztshopping.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserSubcategoryAdapter extends RecyclerView.Adapter<UserSubcategoryAdapter.ViewHolder> {

    private Context mContext;
    private List<SubcategoryModel> subcategoryModelList;

    public UserSubcategoryAdapter(Context mContext, List<SubcategoryModel> subcategoryModelList) {
        this.mContext = mContext;
        this.subcategoryModelList = subcategoryModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_subcategory_item_list, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.productName.setText(subcategoryModelList.get(position).getScname());

        Picasso.get().load(subcategoryModelList.get(position).getScimage()).into(holder.productImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String subname = subcategoryModelList.get(position).getScname();

                Intent intent = new Intent(mContext, ProductsActivity.class);
                intent.putExtra("subname", subname);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return subcategoryModelList.size();
    }

    public void filterList(ArrayList<SubcategoryModel> filteredList) {
        subcategoryModelList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.admin_subcategory_IV);
            productName = itemView.findViewById(R.id.admin_subcategory_TV);

        }
    }
}
