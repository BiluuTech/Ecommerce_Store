package com.biluutech.ztshopping.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.biluutech.ztshopping.Models.ProductModelClass;
import com.biluutech.ztshopping.Models.SubcategoryModel;
import com.biluutech.ztshopping.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminAllSubcategoriesAdapter extends RecyclerView.Adapter<AdminAllSubcategoriesAdapter.ViewHolder> {

    private Context mContext;
    private List<SubcategoryModel> subcategoryModelList;

    public AdminAllSubcategoriesAdapter(Context mContext, List<SubcategoryModel> subcategoryModelList) {
        this.mContext = mContext;
        this.subcategoryModelList = subcategoryModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_subcategory_item_list,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.sbName.setText(subcategoryModelList.get(position).getScname());
        Picasso.get().load(subcategoryModelList.get(position).getScimage()).into(holder.sbImage);


        holder.removeSubcategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String catname = subcategoryModelList.get(position).getCatname();
                String sbname = subcategoryModelList.get(position).getScname();

                subcategoryModelList.clear();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("AllCategories");
                databaseReference.child(sbname).removeValue();

                DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child(catname);
                databaseReference2.child(sbname).removeValue();
                subcategoryModelList.clear();

                DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Products");
                databaseReference3.child(sbname).removeValue();
                subcategoryModelList.clear();


            }
        });

    }

    @Override
    public int getItemCount() {
        return subcategoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView sbName;
        private ImageView sbImage;

        private Button removeSubcategoryBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sbName = itemView.findViewById(R.id.subcategory_name_TV);
            sbImage = itemView.findViewById(R.id.subcategory_IV);
            removeSubcategoryBtn = itemView.findViewById(R.id.remove_subcategory_btn);

        }
    }
}
