package com.jaspreet.lab3;

import android.location.Location;

import com.jaspreet.lab2.ElementModel;
import com.jaspreet.lab2.FoodType;
import com.jaspreet.lab2.OrderWithDistance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class LoadData {

    private static LoadData instance;
    private static List<OrderWithDistance> orders;

    private LoadData(){ }

    public static LoadData getInstance(){
        if(instance == null){
            instance = new LoadData();
            orders = new ArrayList<OrderWithDistance>();
        }
        return instance;
    }

    public List<OrderWithDistance> getOrders() {
        return this.orders;
    }

    public List<OrderWithDistance> getOrders(List<ElementModel> list, String query, Location currentLocation) {
        final String QUERY = query.toUpperCase();
        List<OrderWithDistance> orders = new ArrayList<OrderWithDistance>();
        for(ElementModel item : list){
            double dist = Math.abs(distance(currentLocation, item.getLocation()));
            orders.add(new OrderWithDistance(item,dist));
        }
        Collections.sort(orders);

        HashSet<String> items = new HashSet<String>(Arrays.asList(FoodType.names()));
        if(!items.contains(QUERY)){
            this.orders = orders;
            return orders;
        }
        List<OrderWithDistance> filteredList = new ArrayList<OrderWithDistance>();
        for(OrderWithDistance order : orders){
            String type = order.getOrder().getFoodType();
            if(type.equalsIgnoreCase(QUERY)){
                filteredList.add(order);
            }
        }
        this.orders = filteredList;
        return filteredList;
    }

    private static double distance(Location l1, Location l2) {
        double lat1 = l1.getLatitude();
        double lon1 = l1.getLongitude();
        double lat2 = l2.getLatitude();
        double lon2 = l2.getLongitude();
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            return (dist);
        }
    }
}
