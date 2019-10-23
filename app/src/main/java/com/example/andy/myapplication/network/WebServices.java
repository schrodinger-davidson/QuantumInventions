package com.example.andy.myapplication.network;

import com.example.andy.myapplication.model.dictionary.Dictionary;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Url;

public interface WebServices {


    @GET
    Call<Dictionary> call_GetHourlyDataResponse(@Url String url, @Header("Content-Type") String header);
}