package com.jaspreet.firebaselogin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitService {

        @GET("/data/getAll")
        Call<List<RetroModel>> getAll();

        @POST("/data/add")
        Call<RetroModel> add(@Body RetroModel entity);

}
