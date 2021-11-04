package com.jaspreet.lab2;

import android.location.Location;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class ElementModel {

    @SerializedName("id")
    String id;
    @SerializedName("username")
    String username;
    @SerializedName("name")
    String name;
    @SerializedName("foodType")
    String foodType;
    @SerializedName("quantity")
    int quantity;
    @SerializedName("pickupDate")
    String pickupDate;
    @SerializedName("phoneNumber")
    String phoneNumber;
    @SerializedName("address")
    String address;
    @SerializedName("location")
    Location location;

    public ElementModel(String username, String id, String name, String foodType, int quantity, String pickupDate, String phoneNumber, String address, Location location){
        this.username = username;
        this.id = id;
        this.name = name;
        this.foodType = foodType;
        this.quantity = quantity;
        this.pickupDate = pickupDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.location = location;
    }
    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public Location getLocation() { return location; }

    public void setLocation(Location location) { this.location = location; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementModel that = (ElementModel) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "ElementModel{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", foodType='" + foodType + '\'' +
                ", quantity=" + quantity +
                ", pickupDate='" + pickupDate + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", location=" + location +
                '}';
    }
}
