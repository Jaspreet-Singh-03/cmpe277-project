package com.jaspreet.lab3;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jaspreet.firebaselogin.R;

public class RecyclerListViewHolder extends RecyclerView.ViewHolder {

    TextView tv_customer_name;
    TextView tv_food_type;
    TextView tv_food_quantity;
    TextView tv_pickup_date;
    TextView tv_phone_number;
    TextView tv_address;
    TextView tv_distance;

    public RecyclerListViewHolder(@NonNull View itemView){
        super(itemView);
        tv_distance = itemView.findViewById(R.id.list_tv_distance);
        tv_customer_name = itemView.findViewById(R.id.list_tv_name);
        tv_food_type = itemView.findViewById(R.id.list_tv_foodType);
        tv_food_quantity = itemView.findViewById(R.id.list_tv_quantity);
        tv_pickup_date = itemView.findViewById(R.id.list_tv_pickupDate);
        tv_phone_number = itemView.findViewById(R.id.list_tv_phoneNumber);
        tv_address = itemView.findViewById(R.id.list_tv_addr);
    }

    public TextView getTv_customer_name() {
        return tv_customer_name;
    }

    public TextView getTv_food_type() {
        return tv_food_type;
    }

    public TextView getTv_food_quantity() {
        return tv_food_quantity;
    }

    public TextView getTv_pickup_date() {
        return tv_pickup_date;
    }

    public TextView getTv_phone_number() {
        return tv_phone_number;
    }

    public TextView getTv_address() {
        return tv_address;
    }

    public TextView getTv_distance() {
        return tv_distance;
    }
}
