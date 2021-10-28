package com.jaspreet.lab2;

public class OrderWithDistance implements Comparable<OrderWithDistance> {
    ElementModel order;
    Double distance;

    public OrderWithDistance(ElementModel order, Double distance){
        this.order = order;
        this.distance = distance;
    }

    @Override
    public int compareTo(OrderWithDistance that) {
        double dist = this.distance - that.distance;
        if(dist==0)
            return 0;
        return dist<0?-1:1;
    }

    public ElementModel getOrder() {
        return order;
    }

    public void setOrder(ElementModel order) {
        this.order = order;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
