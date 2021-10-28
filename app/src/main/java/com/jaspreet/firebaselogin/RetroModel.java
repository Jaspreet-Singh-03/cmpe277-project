package com.jaspreet.firebaselogin;

import com.google.gson.annotations.SerializedName;

public class RetroModel {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    public RetroModel(){
    }

    public RetroModel(String id, String name){
        this.id = id;
        this.name = name;
    }

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

    @Override
    public String toString() {
        return "RetroModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
