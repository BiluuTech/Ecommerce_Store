package com.biluutech.ztshopping.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.biluutech.ztshopping.Models.AdminModel;
import com.biluutech.ztshopping.Models.ProductModelClass;
import com.biluutech.ztshopping.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminUserProductsAdapter extends RecyclerView.Adapter<AdminUserProductsAdapter.ViewHolder> {

    private Context mContext;
    private List<AdminModel> adminModelList;

    public AdminUserProductsAdapter(Context mContext, List<AdminModel> adminModelList) {
        this.mContext = mContext;
        this.adminModelList = adminModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_subcategory_item_list, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.pNameText.setText(adminModelList.get(position).getPname());

        Picasso.get().load(adminModelList.get(position).getImage()).into(holder.pImage);

    }

    @Override
    public int getItemCount() {
        return adminModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

         TextView pNameText;
         ImageView pImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pNameText = itemView.findViewById(R.id.admin_subcategory_TV);
            pImage = itemView.findViewById(R.id.admin_subcategory_IV);

        }
    }
}
