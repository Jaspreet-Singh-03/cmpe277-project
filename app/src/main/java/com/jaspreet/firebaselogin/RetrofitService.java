package com.jaspreet.firebaselogin;

import com.jaspreet.lab2.ElementModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitService {

        @GET("/data/getAll/{username}")
        Call<List<HttpModel>> getAll(@Path("username") String username);

        @GET("/data/getUserData/{username}")
        Call<List<HttpModel>> getByUsername(@Path("username") String username);

        @POST("/data/add")
        Call<HttpModel> add(@Body HttpModel entity);

}
