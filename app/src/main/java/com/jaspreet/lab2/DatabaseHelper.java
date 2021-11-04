package com.jaspreet.lab2;

import android.location.Location;

import com.google.firebase.auth.FirebaseAuth;
import com.jaspreet.firebaselogin.HttpModel;
import com.jaspreet.firebaselogin.RetrofitClient;
import com.jaspreet.firebaselogin.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatabaseHelper {

    private static DatabaseHelper instance;
    private static RetrofitService service;
    private static List<HttpModel> allDataList;
    private static List<HttpModel> userDataList;

    private DatabaseHelper(){
    }

    public static DatabaseHelper getInstance(){
        if(instance==null){
            instance = new DatabaseHelper();
            service = RetrofitClient.getInstance().create(RetrofitService.class);
            allDataList = new ArrayList<HttpModel>();
            userDataList = new ArrayList<HttpModel>();
        }
        return instance;
    }

    public void addOne(ElementModel entity) {
        HttpModel model = new HttpModel();
        model.setUsername(entity.getUsername());
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setFoodType(entity.getFoodType());
        model.setPhoneNumber(entity.getPhoneNumber());
        model.setPickupDate(entity.getPickupDate());
        model.setQuantity(entity.getQuantity());
        model.setAddress(entity.getAddress());
        model.setLocation(ParcelableUtil.marshall(entity.getLocation()));
        postCall(model);
    }

    public List<ElementModel> getAllData() {
        getAllDataCall();
        List<ElementModel> result = new ArrayList<ElementModel>();
        for(HttpModel model : allDataList){
            ElementModel item = new ElementModel(
                    model.getUsername(), model.getId(), model.getName(), model.getFoodType(),
                    model.getQuantity(), model.getPickupDate(), model.getPhoneNumber(),
                    model.getAddress(), ParcelableUtil.unmarshall(model.getLocation(), Location.CREATOR)
            );
            result.add(item);
        }
        return result;
    }

    private static void getAllDataCall() {
        Call<List<HttpModel>> data = service.getAll(getUser());
        data.enqueue(new Callback<List<HttpModel>>() {
            @Override
            public void onResponse(Call<List<HttpModel>> call, Response<List<HttpModel>> response) {
                allDataList = response.body();
            }

            @Override
            public void onFailure(Call<List<HttpModel>> call, Throwable t) {
                System.out.println(t.toString());
                // Toast.makeText(SignOutActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public List<ElementModel> getUserData() {
        getByUsernameCall();
        List<ElementModel> result = new ArrayList<ElementModel>();
        for(HttpModel model : userDataList){
            ElementModel item = new ElementModel(
                    model.getUsername(), model.getId(), model.getName(), model.getFoodType(),
                    model.getQuantity(), model.getPickupDate(), model.getPhoneNumber(),
                    model.getAddress(), ParcelableUtil.unmarshall(model.getLocation(), Location.CREATOR)
            );
            result.add(item);
        }
        return result;
    }

    private static void getByUsernameCall() {
        Call<List<HttpModel>> data = service.getByUsername(getUser());
        data.enqueue(new Callback<List<HttpModel>>() {
            @Override
            public void onResponse(Call<List<HttpModel>> call, Response<List<HttpModel>> response) {
                userDataList = response.body();
            }

            @Override
            public void onFailure(Call<List<HttpModel>> call, Throwable t) {
                System.out.println(t.toString());
                // Toast.makeText(SignOutActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postCall(HttpModel entity){
        service.add(entity).enqueue(new Callback<HttpModel>() {
            @Override
            public void onResponse(Call<HttpModel> call, Response<HttpModel> response) {
               // heading.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<HttpModel> call, Throwable t) {
                t.toString();
            }
        });
    }

    public static String getUser(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String username = mAuth.getCurrentUser().getEmail().toString();
        return username;
    }
}

