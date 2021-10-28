package com.jaspreet.lab2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaspreet.firebaselogin.R;
import java.util.List;

public class ElementListAdapter extends RecyclerView.Adapter<RecycleViewHolder> {

    List<ElementModel> viewAllData;

    public ElementListAdapter(List<ElementModel> data){
        viewAllData = data;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {
        holder.getTv_id().setText(String.valueOf(viewAllData.get(position).getId()));
        holder.getTv_name().setText(viewAllData.get(position).getName());
        holder.getTv_foodType().setText(viewAllData.get(position).getFoodType());
        holder.getTv_quantity().setText(String.valueOf(viewAllData.get(position).getQuantity()));
        holder.getTv_phoneNumber().setText(viewAllData.get(position).getPhoneNumber());
        holder.getTv_pickupDate().setText(viewAllData.get(position).getPickupDate());
        holder.getTv_addr().setText(viewAllData.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return viewAllData.size();
    }

    @Override
    public int getItemViewType(final int position){ return R.layout.recycler_view_item_layout; }

}
