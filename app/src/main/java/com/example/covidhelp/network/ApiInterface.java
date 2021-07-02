package com.example.covidhelp.network;

import com.example.covidhelp.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/data.json")
    Call<statewisedata> getStatewisedata();

    @GET("/data.json")
     Call<statewisedata> getStateByName(@Query("state") String state);

   @GET("/v2/everything?q=COVID&apiKey=60a0d6e4e24c4f17a473bc952b4f7236")
    Call<ResponseModel> getAllnews();


}
