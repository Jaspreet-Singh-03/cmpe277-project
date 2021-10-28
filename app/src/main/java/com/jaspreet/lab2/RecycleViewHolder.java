package com.jaspreet.lab2;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jaspreet.firebaselogin.R;

public class RecycleViewHolder extends RecyclerView.ViewHolder {

    TextView tv_id;
    TextView tv_name;
    TextView tv_foodType;
    TextView tv_quantity;
    TextView tv_pickupDate;
    TextView tv_phoneNumber;
    TextView tv_addr;


    public RecycleViewHolder(@NonNull View itemView){
        super(itemView);
        tv_id = itemView.findViewById(R.id.tv_id);
        tv_name = itemView.findViewById(R.id.tv_name);
        tv_foodType = itemView.findViewById(R.id.tv_foodType);
        tv_quantity = itemView.findViewById(R.id.tv_quantity);
        tv_pickupDate = itemView.findViewById(R.id.tv_pickupDate);
        tv_phoneNumber = itemView.findViewById(R.id.tv_phoneNumber);
        tv_addr = itemView.findViewById(R.id.tv_addr);
    }

    public TextView getTv_id() {
        return tv_id;
    }

    public TextView getTv_name() {
        return tv_name;
    }

    public TextView getTv_foodType() {
        return tv_foodType;
    }

    public TextView getTv_quantity() {
        return tv_quantity;
    }

    public TextView getTv_pickupDate() {
        return tv_pickupDate;
    }

    public TextView getTv_phoneNumber() {
        return tv_phoneNumber;
    }

    public TextView getTv_addr() { return tv_addr; }

 }

