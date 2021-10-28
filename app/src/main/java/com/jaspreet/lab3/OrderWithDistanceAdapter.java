package com.jaspreet.lab3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaspreet.lab2.ElementModel;
import com.jaspreet.lab2.OrderWithDistance;
import com.jaspreet.firebaselogin.R;

import java.util.List;

public class OrderWithDistanceAdapter extends RecyclerView.Adapter<RecyclerListViewHolder> {

    List<OrderWithDistance> viewOrders;

    public OrderWithDistanceAdapter(List<OrderWithDistance> orders) { viewOrders = orders; }

    @NonNull
    @Override
    public RecyclerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false);
        return new RecyclerListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerListViewHolder holder, int position) {
        ElementModel model = viewOrders.get(position).getOrder();
        Long distance = Math.round((viewOrders.get(position).getDistance()*100))/100;

        holder.getTv_distance().setText(String.valueOf(distance)+" mi");
        holder.getTv_customer_name().setText(String.valueOf(model.getName()));
        holder.getTv_food_type().setText(String.valueOf(model.getFoodType()));
        holder.getTv_food_quantity().setText(String.valueOf(model.getQuantity()));
        holder.getTv_phone_number().setText(String.valueOf(model.getPhoneNumber()));
        holder.getTv_pickup_date().setText(String.valueOf(model.getPickupDate()));
        holder.getTv_address().setText(String.valueOf(model.getAddress()));
    }

    @Override
    public int getItemCount() {
        return viewOrders.size();
    }

    @Override
    public int getItemViewType(final int position){ return R.layout.list_recycler_view_item_layout; }
}
