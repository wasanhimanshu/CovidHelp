package com.example.covidhelp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetofitClientInstance {
    private static Retrofit retrofit=null;
    private static final String BASE_URL="https://newsapi.org/";
    public static Retrofit getRetrofitInstane(){
        if(retrofit==null){
            retrofit= new Retrofit.Builder().baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).
                    build();
        }
        return retrofit;
    }
}
