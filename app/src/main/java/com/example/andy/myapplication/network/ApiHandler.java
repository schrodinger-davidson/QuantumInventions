package com.example.andy.myapplication.network;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


// Create Retrofit client API service
public class ApiHandler {

    private static final long HTTP_TIMEOUT = TimeUnit.SECONDS.toMillis(120);
    private static WebServices apiService;


    public static WebServices getApiService() {



        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(160, TimeUnit.SECONDS)
                .readTimeout(160, TimeUnit.SECONDS)
                .writeTimeout(160, TimeUnit.SECONDS)
                .build();


        if (apiService == null) {


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(UtilsApi.BASE_URL_API)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(WebServices.class);


            return apiService;
        } else {
            return apiService;
        }


    }


}
