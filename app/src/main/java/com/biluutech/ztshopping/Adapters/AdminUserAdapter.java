package com.biluutech.ztshopping.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.biluutech.ztshopping.Admin.AdminUserProductsActivity;
import com.biluutech.ztshopping.Models.ProductModelClass;
import com.biluutech.ztshopping.Models.UserModel;
import com.biluutech.ztshopping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.ViewHolder> {

    private Context mContext;
    private List<UserModel> userModelList;

    public AdminUserAdapter(Context mContext, List<UserModel> userModelList) {
        this.mContext = mContext;
        this.userModelList = userModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_user_item_layout,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.adminCustomerNameTV.setText(userModelList.get(position).getName());
        holder.adminCustomerPhoneTV.setText(userModelList.get(position).getPhone());
        holder.adminCustomerBillTV.setText("Rs. " + String.valueOf(userModelList.get(position).getTotalBill()) + "/-");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pho = userModelList.get(position).getPhone();

                Intent intent = new Intent(mContext, AdminUserProductsActivity.class);
                intent.putExtra("phone", pho);
                mContext.startActivity(intent);
//
//                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Orders");
//
//                ref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        String pho = snapshot.child("phone").getValue().toString();
//
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView adminCustomerPhoneTV, adminCustomerNameTV, adminCustomerBillTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            adminCustomerNameTV = itemView.findViewById(R.id.admin_customer_name_TV);
            adminCustomerPhoneTV = itemView.findViewById(R.id.admin_customer_phone_TV);
            adminCustomerBillTV = itemView.findViewById(R.id.admin_customer_bill_TV);

        }
    }
}
