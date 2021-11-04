package com.jaspreet.foodsharing;

import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "model")
public class ElementModel {
	
	@Id
	String id;
	String username;
	String name;
    String foodType;
    int quantity;
    String pickupDate;
    String phoneNumber;
    String address;
    Byte[] location;
    
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Byte[] getLocation() {
		return location;
	}
	public void setLocation(Byte[] location) {
		this.location = location;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String toString() {
		return "ElementModel [id=" + id + ", username=" + username + ", name=" + name + ", foodType=" + foodType
				+ ", quantity=" + quantity + ", pickupDate=" + pickupDate + ", phoneNumber=" + phoneNumber
				+ ", address=" + address + ", location=" + Arrays.toString(location) + "]";
	}
	
}
