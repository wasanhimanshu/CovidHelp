package com.example.covidhelp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientInstance {
private static Retrofit retrofit=null;
private static final String BASE_URL="https://api.covid19india.org";

public static Retrofit getRetrofitInstance(){
    if(retrofit==null){
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    return retrofit;
}

}
