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

import com.biluutech.ztshopping.Admin.AdminAddNewProductActivity;
import com.biluutech.ztshopping.Models.SubcategoryModel;
import com.biluutech.ztshopping.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubcategoryAdapter extends RecyclerView.Adapter<SubcategoryAdapter.ViewHolder> {

    private Context context;
    private List<SubcategoryModel> subcategoryModelList;

    public SubcategoryAdapter(Context context, List<SubcategoryModel> subcategoryModelList) {
        this.context = context;
        this.subcategoryModelList = subcategoryModelList;
    }

    @NonNull
    @Override
    public SubcategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_subcategory_item_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubcategoryAdapter.ViewHolder holder, final int position) {


        holder.adminSubcategoryTV.setText(subcategoryModelList.get(position).getScname());

        Picasso.get().load(subcategoryModelList.get(position).getScimage()).into(holder.adminSubcategoryIV);

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView adminSubcategoryTV;
        ImageView adminSubcategoryIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            adminSubcategoryIV = itemView.findViewById(R.id.admin_subcategory_IV);
            adminSubcategoryTV = itemView.findViewById(R.id.admin_subcategory_TV);

        }
    }
}
